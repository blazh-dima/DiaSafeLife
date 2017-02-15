package com.diasafenight.diasafenight;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diasafenight.diasafenight.Helpers.DatePickerFragment;
import com.diasafenight.diasafenight.Helpers.IconBar;
import com.diasafenight.diasafenight.Helpers.TimePickerFragment;
import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.Injection;
import com.diasafenight.diasafenight.Model.InjectionType;
import com.diasafenight.diasafenight.Model.MeasurementInput;
import com.diasafenight.diasafenight.Model.Tag;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class InjectionInput extends AppCompatActivity implements View.OnClickListener {
    public Button timeChoose;
    public Button dateChoose;
    public TextView savebtn;
    public TextView backbtn;
    public EditText editTime;
    public EditText editDate;
    Spinner injectionType;
    EditText editInsuline;
    public final static DateTimeFormatter DateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
    DbContext context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injection_input);

        IconBar i = new IconBar(this);
        i.registerIconBar();

        context = new DbContext(this);

        injectionType = (Spinner)findViewById(R.id.editInjectionType);
        ArrayAdapter<InjectionType> injectAdapter
                = new ArrayAdapter<InjectionType>(this, android.R.layout.simple_spinner_dropdown_item, context.getInjectionTypeAll());
        injectionType.setAdapter(injectAdapter);


        editInsuline = (EditText) this.findViewById(R.id.editInsuline);
        timeChoose = (Button) this.findViewById(R.id.timeChooseBtni);
        dateChoose = (Button) this.findViewById(R.id.dateChooseBtni);
        savebtn = (TextView) this.findViewById(R.id.saveInsulineInput);
        backbtn = (TextView) this.findViewById(R.id.backInsulineInput);
        editTime = (EditText) this.findViewById(R.id.editTimei);
        editDate = (EditText) this.findViewById(R.id.editDatei);

        timeChoose.setOnClickListener(this);
        dateChoose.setOnClickListener(this);
        savebtn.setOnClickListener(this);
        backbtn.setOnClickListener(this);

        this.setCurrentDateTime(editTime, editDate);

    }
    private void setCurrentDateTime(TextView t, TextView d)
    {
        Date now = new Date();
        t.setText(new SimpleDateFormat("HH:mm").format(now));
        d.setText(new SimpleDateFormat("yyyy-MM-dd").format(now));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dateChooseBtni) {
            DialogFragment newFragment = new DatePickerFragment(editDate);
            newFragment.show(this.getFragmentManager(), "datePicker");

        }
        if (view.getId() == R.id.timeChooseBtni) {
            DialogFragment newFragment = new TimePickerFragment(editTime);
            newFragment.show(this.getFragmentManager(), "timePicker");

        }
        if (view.getId() == R.id.backInsulineInput) {
            setResult(RESULT_CANCELED, new Intent());
            finish();
        }

        if (view.getId() == R.id.saveInsulineInput) {

            Injection i = new Injection();
            i.InputOn = DateFormat.parseDateTime(editDate.getText().toString() + " "+ editTime.getText().toString());

            i.Value = Double.valueOf(editInsuline.getText().toString());
            i.InjectionType = (InjectionType) injectionType.getSelectedItem();
            context.addInjection(i);
            finish();
            }

        }

    }

