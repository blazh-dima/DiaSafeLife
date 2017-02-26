package com.diasafenight.diasafenight.Model;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Calendar;

/**
 * Created by SL on 07.01.2017.
 */

public class Prediction {
    public final static String TableName = "Prediction";
    public final static String[] TableColumns = new String[] {"Id", "Value", "CreatedOn"};

    public Prediction() { }

    public Prediction(LocalDate createdOn, int value)
    {
        this.CreatedOn = createdOn;
        this.Value = value;
    }

    public int Id;

    // TODO: 26.02.2017
    // Change in database. Now datetime
    public LocalDate CreatedOn;

    public int Value;
}