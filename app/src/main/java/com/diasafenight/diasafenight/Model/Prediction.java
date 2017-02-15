package com.diasafenight.diasafenight.Model;

import org.joda.time.DateTime;

import java.util.Calendar;

/**
 * Created by SL on 07.01.2017.
 */

public class Prediction {
    public final static String TableName = "Prediction";
    public final static String[] TableColumns = new String[] {"Id", "Value", "CreatedOn"};

    public Prediction() { }

    public Prediction(DateTime createdOn, int value)
    {
        this.CreatedOn = createdOn;
        this.Value = value;
    }


    public int Id;
    public DateTime CreatedOn;
    public int Value;
}