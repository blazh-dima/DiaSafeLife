package com.diasafenight.diasafenight;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.diasafenight.diasafenight.Helpers.DatePickerFragment;
import com.diasafenight.diasafenight.Helpers.IconBar;
import com.diasafenight.diasafenight.Helpers.Comparators.MeasurementInputComparator;
import com.diasafenight.diasafenight.Helpers.Utils;
import com.diasafenight.diasafenight.Interfaces.IDatePickeReceiver;
import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.MeasurementInput;
import com.diasafenight.diasafenight.Model.User;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class GlucoseList
        extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener, IDatePickeReceiver {
    DbContext context = null;
    ListView lvMain;
    public LocalDate viewDate;

    ArrayAdapter<MeasurementInput> adapter;
    public Button dateChoose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_list);
        context = new DbContext(this);
        IconBar iconBar = new IconBar(this);
        iconBar.registerIconBar();

        dateChoose = (Button) this.findViewById(R.id.inputListBtn);
        dateChoose.setOnClickListener(this);

        lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setOnItemClickListener(this);

        viewDate = LocalDate.now();
        dateChoose.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.inputListBtn) {
            DialogFragment newFragment = new DatePickerFragment(this);
            newFragment.show(this.getFragmentManager(), "datePicker");
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        MeasurementInput value = (MeasurementInput)adapterView.getItemAtPosition(i);
        Intent n = new Intent(this, GlucoseEdit.class);
        n.putExtra("MeasurementInput", value);
        startActivityForResult(n, RESULT_OK);

    }
    public void setList()
    {
        ArrayList<MeasurementInput> list = context.getMeasurementInputByDay(viewDate);
        if(User.GetCurrent(this).MeasurementTypeId == 1) //mgdl
            Utils.showIntegers(list);
        Collections.sort(list, new MeasurementInputComparator());
        ArrayAdapter<MeasurementInput> adapter = new ArrayAdapter<MeasurementInput>(this,
                R.layout.input_list_item, list);
        lvMain.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.setList();
    }

    @Override
    public void OnDatePicked(LocalDate date) {
        viewDate = date;
        this.setList();
        dateChoose.setText(DbContext.DateFormat.print(date));
    }
}
