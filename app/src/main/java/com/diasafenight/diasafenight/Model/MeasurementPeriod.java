package com.diasafenight.diasafenight.Model;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SL on 14.12.2016.
 */

public class MeasurementPeriod {

    public final static String TableName = "MeasurementPeriod";
    public final static String[] TableColumns = new String[] {"Id", "Name", "StartTime", "EndTime", "ReminderTime", "TagId"};

    public int Id;
    public String Name;
    public LocalTime StartTime;
    public LocalTime EndTime;
    public LocalTime ReminderTime;
    public Tag Tag;

}
