package com.diasafenight.diasafenight;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diasafenight.diasafenight.Helpers.DatePickerFragment;
import com.diasafenight.diasafenight.Helpers.IconBar;
import com.diasafenight.diasafenight.Helpers.TimePickerFragment;
import com.diasafenight.diasafenight.Helpers.Utils;
import com.diasafenight.diasafenight.Helpers.Validator;
import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.MeasurementInput;
import com.diasafenight.diasafenight.Model.MeasurementPeriod;
import com.diasafenight.diasafenight.Model.MeasurementType;
import com.diasafenight.diasafenight.Model.Prediction;
import com.diasafenight.diasafenight.Model.Tag;
import com.diasafenight.diasafenight.Model.User;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.regex.Pattern;


public class GlucoseInput extends AppCompatActivity implements View.OnClickListener {
    public Button timeChoose;
    public Button dateChoose;
    public TextView savebtn;
    public TextView backbtn;
    public EditText editTime;
    public EditText editDate;
    Spinner tag;
    EditText editGlucose;
    public final static DateTimeFormatter DateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
    DbContext context;
    Validator validator;
    public TextView GlUnits;
    int SleepComfortLevel = 0;
    int IsPreventStepsTaken = -1;
    MeasurementType type;

    private Boolean IsMorning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glucose_input);
        context = new DbContext(this);
        validator = new Validator(this);
        type = context.getMeasurementTypeById(User.GetCurrent(this).MeasurementTypeId);

        IconBar bar = new IconBar(this);
        bar.registerIconBar();

        IsMorning = this.getIsMorning();

        tag = (Spinner)findViewById(R.id.editTag);
        ArrayAdapter<Tag> tagAdapter
                = new ArrayAdapter<Tag>(this, android.R.layout.simple_spinner_dropdown_item, context.getTagAll());
        tag.setAdapter(tagAdapter);
        this.setDefaultTag();


        editGlucose = (EditText) this.findViewById(R.id.editGlucose);
        timeChoose = (Button) this.findViewById(R.id.timeChooseBtn);
        dateChoose = (Button) this.findViewById(R.id.dateChooseBtn);
        savebtn = (TextView) this.findViewById(R.id.saveGlucoseInput);
        backbtn = (TextView) this.findViewById(R.id.backGlucoseInput);
        editTime = (EditText) this.findViewById(R.id.editTime);
        editDate = (EditText) this.findViewById(R.id.editDate);


        GlUnits = (TextView) findViewById(R.id.GlUnits);
        GlUnits.setText("("+type.Name+")");

        timeChoose.setOnClickListener(this);
        dateChoose.setOnClickListener(this);
        savebtn.setOnClickListener(this);
        backbtn.setOnClickListener(this);

        this.setCurrentDateTime(editTime, editDate);
        this.setValue(editGlucose, 0);

        if(Utils.IsLastPredictionDangerous(LocalDate.now().minusDays(1), context, 70, 100) && context.getMeasurementInputByDay(LocalDate.now()).size() == 0)
            this.showPreventStepsPopup();
        else if(IsMorning)
            this.showRatingPopup();


    }
    private void setDefaultTag()
    {
        MeasurementPeriod m = Utils.determineMeasurementPeriod(context, LocalTime.now());
        if(m != null)
            tag.setSelection(m.Tag.Id - 1);

    }

    private void setCurrentDateTime(TextView t, TextView d)
    {
        t.setText(DateTimeFormat.forPattern("HH:mm").print(DateTime.now()));
        d.setText(DateTimeFormat.forPattern("yyyy-MM-dd").print(DateTime.now()));
    }

    private Boolean getIsMorning() {
        MeasurementPeriod m = Utils.determineMeasurementPeriod(context, LocalTime.now());
        return m != null && m.Id == 1 && !Utils.IsValidMeasurementInputExists(LocalDate.now(), m, context);

    }
    public void showRatingPopup()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rating_popup);
        dialog.show();
        ((TextView)dialog.findViewById(R.id.popupBtnOk)).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RatingBar mBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
            SleepComfortLevel = Float.valueOf(mBar.getRating()).intValue() * 2;
            dialog.dismiss();
        }
        });

    }
    public void showPreventStepsPopup()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.prevent_steps_popup);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.preventYesBtn)
                    IsPreventStepsTaken = 1;
                else if(v.getId() == R.id.preventNoBtn)
                    IsPreventStepsTaken = 0;
                dialog.dismiss();

            }

        };
        dialog.findViewById(R.id.preventYesBtn).setOnClickListener(onClickListener);
        dialog.findViewById(R.id.preventNoBtn).setOnClickListener(onClickListener);

        if(IsMorning)
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    showRatingPopup();
                }
            });
        dialog.show();
    }
    public void setValue(EditText t, double value)
    {
        if(type.Id == 1)
            t.setText(String.valueOf((int)value));
        else if(type.Id == 2)
            t.setText(String.valueOf(value));
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dateChooseBtn) {
            DialogFragment newFragment = new DatePickerFragment(editDate);
            newFragment.show(this.getFragmentManager(), "datePicker");

        }
        if (view.getId() == R.id.timeChooseBtn) {
            DialogFragment newFragment = new TimePickerFragment(editTime);
            newFragment.show(this.getFragmentManager(), "timePicker");

        }
        if (view.getId() == R.id.backGlucoseInput) {
            setResult(RESULT_CANCELED, new Intent());
            finish();
        }

        if (view.getId() == R.id.saveGlucoseInput) {
            MeasurementInput i = new MeasurementInput();
            i.InputOn = DateFormat.parseDateTime(editDate.getText().toString() + " "+ editTime.getText().toString());
            i.Value = Double.valueOf(editGlucose.getText().toString());
            i.Tag = (Tag)tag.getSelectedItem();
            if(IsMorning)
                i.SleepComfortLevel = SleepComfortLevel;
            if(IsPreventStepsTaken != -1)
                i.IsPreventStepsTaken = IsPreventStepsTaken == 1;
            if(validator.ValidateMeasurementInput(i, type))
            {
                context.addMeasurementInput(i);
                Utils.RefreshPrediction(i.InputOn.toLocalDate(),context, type);
                finish();
            }
        }
    }

}

