package com.diasafenight.diasafenight.Helpers;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.diasafenight.diasafenight.Interfaces.IDatePickeReceiver;
import com.diasafenight.diasafenight.Interfaces.ITimePickerReceiver;

import org.joda.time.LocalTime;

import java.util.Calendar;

/**
 * Created by SL on 23.11.2016.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    ITimePickerReceiver receiver;

    public TimePickerFragment() {
    }

    public TimePickerFragment(ITimePickerReceiver r)
    {
        receiver = r;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), TimePickerDialog.THEME_TRADITIONAL, this, hour, minute, true);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        LocalTime t = new LocalTime(hourOfDay, minute);
        receiver.OnTimePicked(t);
    }
}
