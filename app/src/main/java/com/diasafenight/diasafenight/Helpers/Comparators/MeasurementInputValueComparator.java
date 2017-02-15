package com.diasafenight.diasafenight.Helpers.Comparators;

import com.diasafenight.diasafenight.Model.MeasurementInput;

import java.util.Comparator;

/**
 * Created by SL on 20.01.2017.
 */

public class MeasurementInputValueComparator implements Comparator<MeasurementInput> {
    @Override
    public int compare(MeasurementInput measurementInput, MeasurementInput t1) {
        if(measurementInput.Value < t1.Value)
            return -1;
        else if(measurementInput.Value > t1.Value)
            return 1;
        else
            return 0;
    }
}
