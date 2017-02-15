package com.diasafenight.diasafenight.Helpers.Comparators;

import com.diasafenight.diasafenight.Model.MeasurementInput;

import java.util.Comparator;

public class MeasurementInputComparator implements Comparator<MeasurementInput> {
    @Override
    public int compare(MeasurementInput measurementInput, MeasurementInput t1) {
        if(measurementInput.InputOn.isBefore(t1.InputOn)){
            return -1;
        }
        else if(measurementInput.InputOn.isAfter(t1.InputOn))
        {
            return 1;
        }
        else
            return 0;
    }
}
