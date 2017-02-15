package com.diasafenight.diasafenight.Model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

/**
 * Created by SL on 14.12.2016.
 */

public class MeasurementInput implements Serializable {
    public final static DateTimeFormatter DateFormat = DateTimeFormat.forPattern("HH:mm");
    public final static String TableName = "MeasurementInput";
    public final static String[] TableColumns = new String[] {"Id", "InputOn", "Value", "SleepComfortLevel", "IsPreventStepsTaken", "TagId"};

    public MeasurementInput() {;
    }

    public MeasurementInput(DateTime inputOn, double value, int sleepComfortLevel, boolean isPreventStepsTaken, Tag tag)
    {
        this.InputOn = inputOn;
        this.Value = value;
        this.SleepComfortLevel = sleepComfortLevel;
        this.IsPreventStepsTaken = isPreventStepsTaken;
        this.Tag = tag;
    }


    public int Id;
    public DateTime InputOn;
    public double Value;
    public int SleepComfortLevel;
    public boolean IsPreventStepsTaken;
    public Tag Tag;

    //use for viewing
    public boolean ShowDecimal = true;
    String space = "    ";

    @Override
    public String toString() {

        String value = ShowDecimal ? Double.valueOf(this.Value).toString() : Integer.valueOf((int)this.Value).toString();
        return DateFormat.print(this.InputOn) + space + value + space +  this.Tag.toString();

    }
    public String getFormattedValue()
    {
        return this.ShowDecimal ? String.valueOf(this.Value) : String.valueOf((int) this.Value);
    }
}
