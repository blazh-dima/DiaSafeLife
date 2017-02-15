package com.diasafenight.diasafenight.Helpers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.diasafenight.diasafenight.GlucoseList;

import org.joda.time.LocalDate;

import java.util.Calendar;

/**
 * Created by SL on 07.01.2017.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    EditText editDate = null;
    Button editDateBtn = null;
    GlucoseList parent = null;
    public DatePickerFragment(){ }
    public DatePickerFragment(EditText b)
    {
        editDate = b;
    }
    public DatePickerFragment(GlucoseList parent_, Button b)
    {
        parent = parent_;
        editDateBtn = b;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(),DatePickerDialog.THEME_HOLO_DARK, this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String text = String.format("%04d-%02d-%02d", year, month + 1, day);
        if(editDate != null){
            editDate.setText(text);
        }
        if(editDateBtn != null){
            parent.viewDate = new LocalDate(year, month + 1, day);
            parent.setList();
            editDateBtn.setText(text);
        }
    }
}
