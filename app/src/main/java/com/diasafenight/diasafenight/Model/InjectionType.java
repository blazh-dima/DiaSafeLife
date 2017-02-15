package com.diasafenight.diasafenight.Model;

import java.io.Serializable;

/**
 * Created by D.Blazhevsky on 17.01.2017.
 */

public class InjectionType implements Serializable {
    public final static String TableName = "InjectionType";
    public final static String[] TableColumns = new String[] {"Id", "Name"};

    public InjectionType() { }
    public InjectionType(String name )
    {
        this.Name = name;
    }

    public int Id;
    public String Name;

    @Override
    public String toString()
    {
        return this.Name == null ? "" : this.Name;
    }
}
