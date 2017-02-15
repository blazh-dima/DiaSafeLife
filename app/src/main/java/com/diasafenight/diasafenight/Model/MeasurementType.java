package com.diasafenight.diasafenight.Model;

/**
 * Created by SL on 14.12.2016.
 */

public class MeasurementType {
    public final static String TableName = "MeasurementType";
    public final static String[] TableColumns = new String[] {"Id", "Name"};

    public MeasurementType() { }
    public MeasurementType(String name )
    {
        this.Name = name;
    }

    public int Id;
    public String Name;

    @Override
    public String toString() {
        return this.Name;
    }
}
