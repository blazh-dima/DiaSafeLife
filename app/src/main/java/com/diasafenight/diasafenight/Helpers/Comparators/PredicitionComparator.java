package com.diasafenight.diasafenight.Helpers.Comparators;

import com.diasafenight.diasafenight.Model.MeasurementInput;
import com.diasafenight.diasafenight.Model.Prediction;

import java.util.Comparator;

/**
 * Created by D.Blazhevsky on 19.01.2017.
 */

public class PredicitionComparator implements Comparator<Prediction> {

    @Override
    public int compare(Prediction o1, Prediction o2) {
        if(o1.CreatedOn.isBefore(o2.CreatedOn)){
            return 1;
        }
        else if(o1.CreatedOn.isAfter(o2.CreatedOn))
        {
            return -1;
        }
        else
            return 0;
    }
}

