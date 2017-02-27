package com.diasafenight.diasafenight;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diasafenight.diasafenight.Helpers.DatePickerFragment;
import com.diasafenight.diasafenight.Helpers.TimePickerFragment;
import com.diasafenight.diasafenight.Helpers.Utils;
import com.diasafenight.diasafenight.Helpers.Validator;
import com.diasafenight.diasafenight.Interfaces.IDatePickeReceiver;
import com.diasafenight.diasafenight.Interfaces.ITimePickerReceiver;
import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.MeasurementInput;
import com.diasafenight.diasafenight.Model.MeasurementType;
import com.diasafenight.diasafenight.Model.Prediction;
import com.diasafenight.diasafenight.Model.Tag;
import com.diasafenight.diasafenight.Model.User;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class GlucoseEdit extends AppCompatActivity implements View.OnClickListener, ITimePickerReceiver, IDatePickeReceiver {

    public Button timeChoose;
    public Button dateChoose;
    public TextView savebtn;
    public TextView deleteBtn;
    public EditText editTime;
    public EditText editDate;
    public TextView ratingText;
    RatingBar mBar;
    Spinner tag;
    EditText editGlucose;
    DbContext context;
    Validator validator;

    MeasurementInput modelOld, modelNew = null;
    MeasurementType type;
    public TextView GlUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glucose_edit);
        context = new DbContext(this);
        validator = new Validator(this);

        type = context.getMeasurementTypeById(User.GetCurrent(this).MeasurementTypeId);

        tag = (Spinner) findViewById(R.id.editTage);
        ArrayAdapter<Tag> tagAdapter
                = new ArrayAdapter<Tag>(this, android.R.layout.simple_spinner_dropdown_item, context.getTagAll());
        tag.setAdapter(tagAdapter);

        editGlucose = (EditText) this.findViewById(R.id.editGlucose);
        timeChoose = (Button) this.findViewById(R.id.timeChooseBtne);
        dateChoose = (Button) this.findViewById(R.id.dateChooseBtne);
        savebtn = (TextView) this.findViewById(R.id.updateGlucoseInput);
        deleteBtn = (TextView) this.findViewById(R.id.deleteBtn);
        editTime = (EditText) this.findViewById(R.id.editTimee);
        editDate = (EditText) this.findViewById(R.id.editDatee);
        GlUnits = (TextView) this.findViewById(R.id.GlUnits);

        timeChoose.setOnClickListener(this);
        dateChoose.setOnClickListener(this);
        savebtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);


        if (this.getIntent().hasExtra("MeasurementInput")) {
            modelOld = (MeasurementInput) this.getIntent().getSerializableExtra("MeasurementInput");
            tag.setSelection(modelOld.Tag.Id - 1);
            editTime.setText(DateTimeFormat.forPattern("HH:mm").print(modelOld.InputOn));
            editDate.setText(DateTimeFormat.forPattern("yyyy-MM-dd").print(modelOld.InputOn));
            this.setValue(editGlucose, modelOld.Value);
        }

        GlUnits.setText("(" + type.Name + ")");
    }

    public void setValue(EditText t, double value) {
        if (type.Id == 1)
            t.setText(String.valueOf((int) value));
        else if (type.Id == 2)
            t.setText(String.valueOf(value));
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dateChooseBtne) {
            DialogFragment newFragment = new DatePickerFragment(this);
            newFragment.show(this.getFragmentManager(), "datePicker");

        }
        if (view.getId() == R.id.timeChooseBtne) {
            DialogFragment newFragment = new TimePickerFragment(this);
            newFragment.show(this.getFragmentManager(), "timePicker");

        }
        if (view.getId() == R.id.deleteBtn) {
            context.deleteMeasurementInput(modelOld);
            Utils.RefreshPrediction(modelOld.InputOn.toLocalDate(), context, type);
            finish();
        }

        if (view.getId() == R.id.updateGlucoseInput) {
            modelNew = new MeasurementInput(modelOld);

            modelNew.InputOn = DbContext.DateTFormat.parseDateTime(editDate.getText().toString() + " " + editTime.getText().toString());
            modelNew.Value = Double.valueOf(editGlucose.getText().toString());
            modelNew.Tag = (Tag) tag.getSelectedItem();

            if (validator.ValidateMeasurementInput(modelNew, type)) {
                context.updateMeasurementInput(modelNew);
                Utils.RefreshPrediction(modelNew.InputOn.toLocalDate(), context, type);

                if (!modelNew.InputOn.toLocalDate().equals(modelOld.InputOn.toLocalDate()))
                    Utils.RefreshPrediction(modelOld.InputOn.toLocalDate(), context, type);

                finish();
            }

        }
    }

    @Override
    public void OnDatePicked(LocalDate date) {
        editDate.setText(DbContext.DateFormat.print(date));
    }

    @Override
    public void OnTimePicked(LocalTime time) {
        editTime.setText(DbContext.TimeFormat.print(time));
    }
}
