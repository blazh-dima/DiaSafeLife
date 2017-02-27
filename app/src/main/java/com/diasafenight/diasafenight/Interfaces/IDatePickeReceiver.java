package com.diasafenight.diasafenight.Interfaces;

import android.app.DatePickerDialog;

import org.joda.time.LocalDate;

public interface IDatePickeReceiver {
    void OnDatePicked(LocalDate date);
}
