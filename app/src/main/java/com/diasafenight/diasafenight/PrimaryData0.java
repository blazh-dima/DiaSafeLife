package com.diasafenight.diasafenight;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.diasafenight.diasafenight.Helpers.AlarmReceiver;
import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.MeasurementPeriod;
import com.diasafenight.diasafenight.Model.MeasurementType;
import com.diasafenight.diasafenight.Model.User;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class PrimaryData0 extends AppCompatActivity implements View.OnClickListener {

    private DbContext context = null;
    private Spinner editDTForm = null;
    private Spinner editLanguage = null;
    private Spinner munit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_data0);

        context = new DbContext(this);
        munit = (Spinner) findViewById(R.id.munit);
        editLanguage = (Spinner)findViewById(R.id.editLanguage);
        editDTForm = (Spinner)findViewById(R.id.editDTFormat);

        ArrayAdapter<String> languageAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.languages));
        editLanguage.setAdapter(languageAdapter);

        ArrayAdapter<String> TFAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.timeFormats));
        editDTForm.setAdapter(TFAdapter);


        ArrayAdapter<MeasurementType> muinits =
                new ArrayAdapter<MeasurementType>(this, android.R.layout.simple_spinner_dropdown_item, context.getMeasurementTypeAll() );
        munit.setAdapter(muinits);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.OkPrimary0Input){
            User u = new User();
            u.Language = editLanguage.getSelectedItem().toString();
            u.TimeFormat = editDTForm.getSelectedItem().toString();
            u.MeasurementTypeId = ((MeasurementType)munit.getSelectedItem()).Id;
            User.CreateOrUpdate(u, this);
            Toast.makeText(this,"Saved", Toast.LENGTH_LONG).show();
        }
        this.setNotifications();
        Intent i = new Intent(this, PrimaryData.class);
        startActivity(i);
    }
    public void setNotifications()
    {
        ArrayList<MeasurementPeriod> periods = context.getMeasurementPeriodAll();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        for (MeasurementPeriod p : periods)
        {
            DateTime time = DateTime.now().withTime(p.ReminderTime);
            Intent alarmIntent = new Intent(this, AlarmReceiver.class); // AlarmReceiver = broadcast receiver
            alarmIntent.putExtra("MeasurementPeriod", p.Id);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(  this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, time.getMillis(), pendingIntent);
            else
                alarmManager.set(AlarmManager.RTC_WAKEUP, time.getMillis(), pendingIntent);

        }
    }
}
