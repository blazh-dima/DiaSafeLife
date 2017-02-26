package com.diasafenight.diasafenight.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;

import com.diasafenight.diasafenight.R;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by SL on 15.12.2016.
 */

public class DbContext extends SQLiteOpenHelper {

    private final static DateTimeFormatter DateTFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
    private final static DateTimeFormatter TimeFormat = DateTimeFormat.forPattern("HH:mm");
    private final static DateTimeFormatter DateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");

    //database name
    final private static String DbName = "DiaSafeNight.db";

    //database version
    final private static int DbVersion = 1;

    //database
    private SQLiteDatabase db;

    //context
    private Context context;

    public DbContext(Context context) {
        super(context, DbName, null, DbVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open("DiaSafeNight.sql");
            String queries = new Scanner(inputStream,"UTF-8").useDelimiter("\\A").next();
            for (String query : queries.split(";")) {
                sqLiteDatabase.execSQL(query);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void open() {
        db = this.getReadableDatabase();
    }
    @Override
    public synchronized void close() {
        if (db != null) {
            db.close();

        }
        super.close();
    }
    public void clear_db(){
        this.open();
        db.delete("MeasurementInput", null, null);
        db.delete("Prediction", null, null);
        db.delete("InjectionList", null, null);
        this.close();
    }


    //MeasurementType
    public ArrayList<MeasurementType> getMeasurementTypeAll()
    {
        ArrayList <MeasurementType> list = new ArrayList <MeasurementType>();
        this.open();
        try {

            Cursor c = db.rawQuery("select * from MeasurementType", null);
            while(c.moveToNext())
            {
                MeasurementType m = new MeasurementType();
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.Name = c.getString(c.getColumnIndexOrThrow("Name"));
                list.add(m);
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return list;
    }
    public MeasurementType getMeasurementByName(String name)
    {
        MeasurementType m = new MeasurementType();
        this.open();
        try {

            Cursor c = db.query(true, MeasurementType.TableName, MeasurementType.TableColumns, "Name" + "='" + String.valueOf(name)+"'",
                    null, null, null, null, null);

            if(c.moveToFirst()){
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.Name = c.getString(c.getColumnIndexOrThrow("Name"));
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return m;
    }
    public MeasurementType getMeasurementTypeById(int id)
    {
        MeasurementType m = new MeasurementType();
        this.open();
        try {

            Cursor c = db.query(true, MeasurementType.TableName, MeasurementType.TableColumns, "Id" + "=" + String.valueOf(id),
                    null, null, null, null, null);

            if(c.moveToFirst()){
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.Name = c.getString(c.getColumnIndexOrThrow("Name"));
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return m;
    }


    //Tag
    public ArrayList<Tag> getTagAll()
    {
        ArrayList <Tag> list = new ArrayList <Tag>();
        this.open();
        try {

            Cursor c = db.rawQuery("select * from Tag", null);
            while(c.moveToNext())
            {
                Tag m = new Tag();
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.Name = c.getString(c.getColumnIndexOrThrow("Name"));
                list.add(m);
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return list;
    }
    public Tag getTagName(String name)
    {
        Tag m = new Tag();
        this.open();
        try {

            Cursor c = db.query(true, Tag.TableName, Tag.TableColumns, "Name" + "='" + String.valueOf(name)+"'",
                    null, null, null, null, null);

            if(c.moveToFirst()){
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.Name = c.getString(c.getColumnIndexOrThrow("Name"));
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return m;
    }
    public Tag getTagById(int id)
    {
        Tag m = new Tag();
        this.open();
        try {

            Cursor c = db.query(true, Tag.TableName, Tag.TableColumns, "Id" + "=" + String.valueOf(id),
                    null, null, null, null, null);

            if(c.moveToFirst()){
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.Name = c.getString(c.getColumnIndexOrThrow("Name"));
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return m;
    }


    //InjectionType
    public ArrayList<InjectionType> getInjectionTypeAll()
    {
        ArrayList <InjectionType> list = new ArrayList <InjectionType>();
        this.open();
        try {

            Cursor c = db.rawQuery("select * from InjectionType", null);
            while(c.moveToNext())
            {
                InjectionType m = new InjectionType();
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.Name = c.getString(c.getColumnIndexOrThrow("Name"));
                list.add(m);
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return list;
    }
    public InjectionType getInjectionTypeName(String name)
    {
        InjectionType m = new InjectionType();
        this.open();
        try {

            Cursor c = db.query(true, InjectionType.TableName, InjectionType.TableColumns, "Name" + "='" + String.valueOf(name)+"'",
                    null, null, null, null, null);

            if(c.moveToFirst()){
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.Name = c.getString(c.getColumnIndexOrThrow("Name"));
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return m;
    }
    public InjectionType getInjectionTypeById(int id)
    {
        InjectionType m = new InjectionType();
        this.open();
        try {

            Cursor c = db.query(true, InjectionType.TableName, InjectionType.TableColumns, "Id" + "=" + String.valueOf(id),
                    null, null, null, null, null);

            if(c.moveToFirst()){
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.Name = c.getString(c.getColumnIndexOrThrow("Name"));
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return m;
    }
    public void addInjectionType(InjectionType i)
    {
        this.open();
        ContentValues cv = new ContentValues();
        cv.put("Name", i.Name);
        db.insert(InjectionType.TableName, null, cv);
        this.close();
    }
    public void updateInjectionType(InjectionType i)
    {
        this.open();
        ContentValues cv = new ContentValues();
        cv.put("Name", i.Name);
        db.update(InjectionType.TableName, cv, "Id" + "=" + String.valueOf(i.Id), null);
        this.close();
    }
    public void deleteInjectionType(InjectionType i)
    {
        this.open();
        db.delete("InjectionType", "Id = " + i.Id, null);
        this.close();
    }


    //Prediction
    public void addPrediction(Prediction i)
    {
        this.open();
        try {

            ContentValues cv = new ContentValues();
            cv.put("CreatedOn", DateFormat.print(i.CreatedOn));
            cv.put("Value", i.Value);
            db.insert(Prediction.TableName, null, cv);
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
    }
    public ArrayList<Prediction> getPredictionAll()
    {
        ArrayList <Prediction> list = new ArrayList <Prediction>();
        this.open();
        try {

            Cursor c = db.rawQuery("select * from Prediction", null);
            while(c.moveToNext())
            {
                Prediction m = new Prediction();
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.CreatedOn = DateFormat.parseLocalDate(c.getString(c.getColumnIndexOrThrow("CreatedOn")));
                m.Value = c.getInt(c.getColumnIndexOrThrow("Value"));
                list.add(m);
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return list;
    }
    public Prediction getFirstPredictionByDay(LocalDate day)
    {
        Prediction p = null;
        this.open();
        try {

            Cursor c = db.query(true, Prediction.TableName, Prediction.TableColumns, "CreatedOn" + "='" + DateFormat.print(day)+"'",
                    null, null, null, null, null);
            if(c.moveToFirst())
            {
                p = new Prediction();
                p.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                p.CreatedOn = DateFormat.parseLocalDate(c.getString(c.getColumnIndexOrThrow("CreatedOn")));
                p.Value = c.getInt(c.getColumnIndexOrThrow("Value"));
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return p;
    }

    public void updatePrediction(Prediction i)
    {
        this.open();
        try {

            ContentValues cv = new ContentValues();
            cv.put("CreatedOn", DateFormat.print(i.CreatedOn));
            cv.put("Value", i.Value);
            db.update(Prediction.TableName, cv, "Id" + "=" + String.valueOf(i.Id), null);
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
    }
    public void deletePrediction(Prediction i)
    {
        this.open();
        db.delete(Prediction.TableName, "Id = " + i.Id, null);
        this.close();
    }

    //MeasurementPeriod
    public ArrayList<MeasurementPeriod> getMeasurementPeriodAll()
    {
        ArrayList <MeasurementPeriod> list = new ArrayList <MeasurementPeriod>();
        this.open();
        try {
            Cursor c = db.rawQuery("select * from MeasurementPeriod", null);
            while(c.moveToNext())
            {
                MeasurementPeriod m = new MeasurementPeriod();
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.Name = c.getString(c.getColumnIndexOrThrow("Name"));
                m.StartTime =  TimeFormat.parseLocalTime(c.getString(c.getColumnIndexOrThrow("StartTime")));
                m.EndTime =  TimeFormat.parseLocalTime(c.getString(c.getColumnIndexOrThrow("EndTime")));
                m.ReminderTime =  TimeFormat.parseLocalTime(c.getString(c.getColumnIndexOrThrow("ReminderTime")));
                int mid = c.getInt(c.getColumnIndexOrThrow("TagId"));
                m.Tag = this.getTagById(mid);
                list.add(m);
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return list;
    }
    public MeasurementPeriod getMeasurementPeriodById(int id)
    {

        MeasurementPeriod m = new MeasurementPeriod();
        this.open();
        try {
            Cursor c = db.query(true, MeasurementPeriod.TableName, MeasurementPeriod.TableColumns, "Id" + "=" + String.valueOf(id),
                    null, null, null, null, null);
            if(c.moveToFirst())
            {

                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.Name = c.getString(c.getColumnIndexOrThrow("Name"));
                m.StartTime =  TimeFormat.parseLocalTime(c.getString(c.getColumnIndexOrThrow("StartTime")));
                m.EndTime =  TimeFormat.parseLocalTime(c.getString(c.getColumnIndexOrThrow("EndTime")));
                m.ReminderTime =  TimeFormat.parseLocalTime(c.getString(c.getColumnIndexOrThrow("ReminderTime")));
                int mid = c.getInt(c.getColumnIndexOrThrow("TagId"));
                m.Tag = this.getTagById(mid);
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return m;
    }


    //MeasurementInput
    public void addMeasurementInput(MeasurementInput i)
    {
        this.open();
        try {

            ContentValues cv = new ContentValues();
            cv.put("InputOn", DateTFormat.print(i.InputOn));
            cv.put("Value", i.Value);
            if(i.Tag != null)
                cv.put("TagId", i.Tag.Id);
            cv.put("SleepComfortLevel", i.SleepComfortLevel);
            cv.put("IsPreventStepsTaken", i.IsPreventStepsTaken ? 1 : 0);
            db.insert(MeasurementInput.TableName, null, cv);
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
    }
    public void updateMeasurementInput(MeasurementInput i)
    {
        this.open();
        try {

            ContentValues cv = new ContentValues();
            cv.put("InputOn", DateTFormat.print(i.InputOn));
            cv.put("Value", i.Value);
            cv.put("TagId", i.Tag.Id);
            cv.put("SleepComfortLevel", i.SleepComfortLevel);
            cv.put("IsPreventStepsTaken", i.IsPreventStepsTaken ? 1 : 0);

            db.update(MeasurementInput.TableName, cv, "Id" + "=" + String.valueOf(i.Id), null);
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
    }
    public MeasurementInput getMeasurementInputById(int id)
    {
        MeasurementInput m = new MeasurementInput();
        m.InputOn = DateTime.now();
        this.open();
        try {

            Cursor c = db.query(true, MeasurementInput.TableName, MeasurementInput.TableColumns, "Id" + "=" + String.valueOf(id),
                    null, null, null, null, null);
            if(c.moveToFirst())
            {
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.InputOn = DateTFormat.parseDateTime(c.getString(c.getColumnIndexOrThrow("InputOn")));
                m.Value = c.getDouble(c.getColumnIndexOrThrow("Value"));
                int mid = c.getInt(c.getColumnIndexOrThrow("TagId"));
                m.Tag = this.getTagById(mid);
                m.SleepComfortLevel = c.getInt(c.getColumnIndexOrThrow("SleepComfortLevel"));
                m.IsPreventStepsTaken = c.getInt(c.getColumnIndexOrThrow("IsPreventStepsTaken")) != 0;
            };

            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return m;
    }
    public ArrayList<MeasurementInput> getMeasurementInputByDay(LocalDate day)
    {
        ArrayList <MeasurementInput> list = new ArrayList <MeasurementInput>();

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

        this.open();
        try {
            String sql =
                    "select * from MeasurementInput " +
                            "where InputOn like '" +
                            day.toString(fmt)+ "%"+
                            "'";

            Cursor c = db.rawQuery(sql, null );
            while(c.moveToNext())
            {
                MeasurementInput m = new MeasurementInput();
                m.InputOn = DateTime.now();
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.InputOn = DateTFormat.parseDateTime(c.getString(c.getColumnIndexOrThrow("InputOn")));
                m.Value = c.getDouble(c.getColumnIndexOrThrow("Value"));
                int mid = c.getInt(c.getColumnIndexOrThrow("TagId"));
                m.Tag = this.getTagById(mid);
                m.SleepComfortLevel = c.getInt(c.getColumnIndexOrThrow("SleepComfortLevel"));
                m.IsPreventStepsTaken = c.getInt(c.getColumnIndexOrThrow("IsPreventStepsTaken")) != 0;
                list.add(m);
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return list;
    }
    public void deleteMeasurementInput(MeasurementInput i)
    {
        this.open();
        db.delete("MeasurementInput", "Id = " + i.Id, null);
        this.close();
    }


    //Injection
    public void addInjection(Injection i)
    {
        this.open();
        try {

            ContentValues cv = new ContentValues();
            cv.put("InputOn", DateTFormat.print(i.InputOn));
            cv.put("Value", i.Value);
            if(i.InjectionType != null)
                cv.put("InjectionTypeId", i.InjectionType.Id);


            db.insert(Injection.TableName, null, cv);
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
    }
    public void updateInjection(Injection i)
    {
        this.open();
        try {

            ContentValues cv = new ContentValues();
            cv.put("InputOn", DateTFormat.print(i.InputOn));
            cv.put("Value", i.Value);
            if(i.InjectionType !=null)
                cv.put("InjectionTypeId", i.InjectionType.Id);

            db.update(Injection.TableName, cv, "Id" + "=" + String.valueOf(i.Id), null);
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
    }
    public Injection getInjectionById(int id)
    {
        Injection m = new Injection();
        m.InputOn = DateTime.now();
        this.open();
        try {

            Cursor c = db.query(true, Injection.TableName, Injection.TableColumns, "Id" + "=" + String.valueOf(id),
                    null, null, null, null, null);
            if(c.moveToFirst())
            {
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.InputOn = DateTFormat.parseDateTime(c.getString(c.getColumnIndexOrThrow("InputOn")));
                m.Value = c.getDouble(c.getColumnIndexOrThrow("Value"));
                int mid = c.getInt(c.getColumnIndexOrThrow("InjectionTypeId"));
                m.InjectionType = this.getInjectionTypeById(mid);
            };



            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return m;
    }
    public ArrayList<Injection> getInjectionByDay(LocalDate day)
    {
        ArrayList <Injection> list = new ArrayList <Injection>();

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

        this.open();
        try {
            String sql =
                    "select * from Injection " +
                            "where InputOn like '" +
                            day.toString(fmt)+ "%"+
                            "'";

            Cursor c = db.rawQuery(sql, null );
            while(c.moveToNext())
            {
                Injection m = new Injection();
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.InputOn = DateTFormat.parseDateTime(c.getString(c.getColumnIndexOrThrow("InputOn")));
                m.Value = c.getDouble(c.getColumnIndexOrThrow("Value"));
                int mid = c.getInt(c.getColumnIndexOrThrow("InjectionTypeId"));
                m.InjectionType = this.getInjectionTypeById(mid);
                list.add(m);
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return list;
    }
    public ArrayList<Injection> getInjectionAll()
    {
        ArrayList <Injection> list = new ArrayList <Injection>();

        this.open();
        try {
            String sql = "select * from Injection";

            Cursor c = db.rawQuery(sql, null );
            while(c.moveToNext())
            {
                Injection m = new Injection();
                m.Id = c.getInt(c.getColumnIndexOrThrow("Id"));
                m.InputOn = DateTFormat.parseDateTime(c.getString(c.getColumnIndexOrThrow("InputOn")));
                m.Value = c.getDouble(c.getColumnIndexOrThrow("Value"));
                int mid = c.getInt(c.getColumnIndexOrThrow("InjectionTypeId"));
                m.InjectionType = this.getInjectionTypeById(mid);
                list.add(m);
            }
            c.close();
        }
        catch (Exception e)
        {
            Log.e("DbError", e.getMessage());
        }
        this.close();
        return list;
    }
    public void deleteInjection(Injection i)
    {
        this.open();
        db.delete("Injection", "Id = " + i.Id, null);
        this.close();
    }


}
