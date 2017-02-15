package com.diasafenight.diasafenight.Helpers;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.diasafenight.diasafenight.Model.MeasurementInput;
import com.diasafenight.diasafenight.Model.MeasurementType;
import com.diasafenight.diasafenight.R;

import java.util.regex.Pattern;


/**
 * Created by D.Blazhevsky on 26.01.2017.
 */

public class Validator {
    AppCompatActivity activity = null;

    public Validator(AppCompatActivity a)
    {
        activity = a;
    }

    public boolean ValidateMeasurementInput( MeasurementInput m, MeasurementType type)
    {
        if(type.Id == 1) //mg/dl
        {
            if(m.Value % 1 == 0 && m.Value >= 10 && m.Value < 900)
                return true;
            else
                Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.incorrect_mi_value_message), Toast.LENGTH_LONG).show();
        }
        else if(type.Id == 2) //mmol/l
        {

            if(m.Value >= 1 && m.Value < 100 )
            {
                //string n = String.valueOf(m.Value).split("//.")[1].length()<2)
                String[] val = String.valueOf(m.Value).split(Pattern.quote("."));
                if(val.length < 2)
                    return true;
                else
                {
                    if( val[1].length() < 2)
                        return true;
                    else
                        Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.incorrect_mi_value_message), Toast.LENGTH_LONG).show();
                }
            }
            else
                Toast.makeText(activity.getApplicationContext(), activity.getResources().getString(R.string.incorrect_mi_value_message), Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
