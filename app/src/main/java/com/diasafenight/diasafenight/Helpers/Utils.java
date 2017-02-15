package com.diasafenight.diasafenight.Helpers;

import android.support.v7.app.AppCompatActivity;

import com.diasafenight.diasafenight.Algorithm.AlgPred;
import com.diasafenight.diasafenight.Helpers.Comparators.MeasurementInputValueComparator;
import com.diasafenight.diasafenight.Helpers.Comparators.PredicitionComparator;
import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.MeasurementInput;
import com.diasafenight.diasafenight.Model.MeasurementPeriod;
import com.diasafenight.diasafenight.Model.MeasurementType;
import com.diasafenight.diasafenight.Model.Prediction;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by SL on 15.01.2017.
 */

public  class Utils {
    public static MeasurementPeriod determineMeasurementPeriod(DbContext context, LocalTime time)
    {
        ArrayList<MeasurementPeriod> list = context.getMeasurementPeriodAll();
        for(MeasurementPeriod m : list)
        {
            if((m.StartTime.isBefore(time) && m.EndTime.isAfter(time)
                || m.StartTime.isEqual(time)
                || m.EndTime.isEqual(time)))
            {
                return  m;
            }
        }
        return null;
    }
    public static MeasurementInput getValidMinMeasurementInput(LocalDate date, MeasurementPeriod period, DbContext context)
    {
        ArrayList<MeasurementInput> list = context.getMeasurementInputByDay(date);
        ArrayList<MeasurementInput> list2 = new ArrayList<>();
        for(MeasurementInput m: list)
        {
            if((period.StartTime.isBefore(m.InputOn.toLocalTime()) && period.EndTime.isAfter(m.InputOn.toLocalTime())
                    || period.StartTime.isEqual(m.InputOn.toLocalTime())
                    || period.EndTime.isEqual(m.InputOn.toLocalTime()))) {
                if(period.Id == 4)
                {
                    if(period.Tag.Id == m.Tag.Id)
                        list2.add(m);
                }
                else
                    list2.add(m);
            }
        }
        if(list2.size() > 0)
        {
            Collections.sort(list2, new MeasurementInputValueComparator());
            return list2.get(0);
        }
        else
            return null;

    }
    public static Boolean IsValidMeasurementInputExists(LocalDate date, MeasurementPeriod period, DbContext context)
    {
        ArrayList<MeasurementInput> list = context.getMeasurementInputByDay(date);
        for(MeasurementInput m: list)
        {
            if((period.StartTime.isBefore(m.InputOn.toLocalTime()) && period.EndTime.isAfter(m.InputOn.toLocalTime())
                    || period.StartTime.isEqual(m.InputOn.toLocalTime())
                    || period.EndTime.isEqual(m.InputOn.toLocalTime()))) {
                if(period.Id == 4)
                {
                    if(period.Tag.Id == m.Tag.Id)
                        return true;
                }
                else
                    return true;
            }
        }
        return false;
    }

    public static Boolean IsDayDataCompleted(LocalDate date, DbContext context)
    {
        ArrayList<MeasurementPeriod> list = context.getMeasurementPeriodAll();
        ArrayList<MeasurementInput> inputs = context.getMeasurementInputByDay(date);
        int counter = 0;
        for(MeasurementPeriod period : list)
        {
            for(MeasurementInput m: inputs)
            {
                if((period.StartTime.isBefore(m.InputOn.toLocalTime()) && period.EndTime.isAfter(m.InputOn.toLocalTime())
                        || period.StartTime.isEqual(m.InputOn.toLocalTime())
                        || period.EndTime.isEqual(m.InputOn.toLocalTime())))
                {
                    if(period.Id == 4)
                    {
                        if(period.Tag.Id == m.Tag.Id)
                        {
                            ++counter;
                            break;
                        }

                    }
                    else
                    {
                        ++counter;
                        break;
                    }

                }
            }
        }
        return counter > 3;

    }

    public static Boolean IsLastPredictionDangerous(LocalDate date, DbContext context, int low, int top)
    {
        ArrayList<Prediction> list = context.getPredictionByDay(date);
        if(list.size() > 0)
        {
            Collections.sort(list, new PredicitionComparator());
            return list.get(0).Value >= low && list.get(0).Value <= top;
        }

        return false;

    }

    public static void showIntegers(ArrayList<MeasurementInput> list)
    {
        for(MeasurementInput i : list)
            i.ShowDecimal = false;

    }
    public static int MakePrediction(LocalDate date, DbContext context, MeasurementType type)
    {

        if(IsDayDataCompleted(date, context))
        {
            ArrayList<MeasurementInput> tmp = context.getMeasurementInputByDay(date);
            AlgPred algor = new AlgPred();
            return algor.getProbGipoglik(tmp, type.Name);
        }
        return -1;
    }

}
