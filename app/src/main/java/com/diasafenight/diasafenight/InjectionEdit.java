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

public class InjectionEdit extends AppCompatActivity implements View.OnClickListener {
    public Button timeChoose;
    public Button dateChoose;
    public TextView updateBtn;
    public TextView deletBtn;
    public EditText editTime;
    public EditText editDate;
    Spinner injectionType;
    EditText editInsuline;
    public final static DateTimeFormatter DateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
    DbContext context;
    Injection model = null;
    ArrayAdapter<InjectionType> injectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injection_edit);

        context = new DbContext(this);

        injectionType = (Spinner)findViewById(R.id.editInjectionTypee);
        injectAdapter  = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, context.getInjectionTypeAll());
        injectionType.setAdapter(injectAdapter);


        editInsuline = (EditText) this.findViewById(R.id.editInsulinee);
        timeChoose = (Button) this.findViewById(R.id.timeChooseBtnie);
        dateChoose = (Button) this.findViewById(R.id.dateChooseBtnie);
        updateBtn = (TextView) this.findViewById(R.id.updateInjectionBtn);
        deletBtn = (TextView) this.findViewById(R.id.deleteInjectionBtn);
        editTime = (EditText) this.findViewById(R.id.editTimeie);
        editDate = (EditText) this.findViewById(R.id.editDateie);

        timeChoose.setOnClickListener(this);
        dateChoose.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        deletBtn.setOnClickListener(this);

        if(this.getIntent().hasExtra("Injection"))
        {
            model = (Injection)this.getIntent().getSerializableExtra("Injection");
            if(model.InjectionType != null)
                injectionType.setSelection(model.InjectionType.Id -1 );

            
            editTime.setText(DateTimeFormat.forPattern("HH:mm").print(model.InputOn));
            editDate.setText(DateTimeFormat.forPattern("yyyy-MM-dd").print(model.InputOn));
            editInsuline.setText(String.valueOf(model.Value));

        }

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
        if (view.getId() == R.id.deleteInjectionBtn) {
            context.deleteInjection(model);
            Toast.makeText(this,"Deleted", Toast.LENGTH_LONG).show();
        }

        if (view.getId() == R.id.updateInjectionBtn) {
            model.InputOn = DateFormat.parseDateTime(editDate.getText().toString() + " "+ editTime.getText().toString());
            model.Value = Double.valueOf(editInsuline.getText().toString());
            model.InjectionType = (InjectionType) injectionType.getSelectedItem();
            context.updateInjection(model);
            finish();
        }

    }

}

