package com.diasafenight.diasafenight.Model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

/**
 * Created by D.Blazhevsky on 17.01.2017.
 */

public class Injection implements Serializable {
    public final static String TableName = "Injection";
    public final static String[] TableColumns = new String[] {"Id", "InputOn", "Value","InjectionTypeId"};
    public final static DateTimeFormatter DateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");

    public Injection() {
    }

    public int Id;
    public DateTime InputOn;
    public double Value;
    public InjectionType InjectionType;

    @Override
    public String toString() {
        String space = "    ";
        return DateFormat.print(this.InputOn) + space + Double.valueOf(this.Value).intValue() + space +  this.InjectionType.toString();

    }

}
