package com.diasafenight.diasafenight.Model;

import android.content.Context;
import android.content.SharedPreferences;

import org.joda.time.DateTime;

/**
 * Created by SL on 14.12.2016.
 */

public class User {

    public static final String PREFS_NAME = "User";

    private static SharedPreferences settings;
    public User() { }

    public String Language;
    public String BirthYear;
    public int MeasurementTypeId;
    public String TimeFormat;
    public String IllFrom;
    public String Gender;
    public int A1C;
    public String ExternalId;

    public static void CreateOrUpdate(User u, Context context)
    {
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("IfExists", true);

        editor.putString("Language", u.Language);
        editor.putString("BirthYear", u.BirthYear);
        editor.putInt("MeasurementTypeId", u.MeasurementTypeId);
        editor.putString("TimeFormat", u.TimeFormat);
        editor.putString("IllFrom", u.IllFrom);
        editor.putString("Gender", u.Gender);
        editor.putInt("A1C", u.A1C);
        editor.putString("ExternalId", u.ExternalId);

        editor.commit();
    }
    public static User GetCurrent(Context context)
    {
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        boolean IfExists = settings.getBoolean("IfExists", false);
        if(IfExists)
        {
            User u = new User();
            u.Language = settings.getString("Language", "");
            u.BirthYear = settings.getString("BirthYear", "");
            u.MeasurementTypeId = settings.getInt("MeasurementTypeId", 0);
            u.TimeFormat = settings.getString("TimeFormat", "");
            u.IllFrom = settings.getString("IllFrom", "");
            u.Gender = settings.getString("Gender","");
            u.A1C = settings.getInt("A1C", 0);
            u.ExternalId = settings.getString("ExternalId", "");
            return u;
        }
        else
            return null;
    }
}
