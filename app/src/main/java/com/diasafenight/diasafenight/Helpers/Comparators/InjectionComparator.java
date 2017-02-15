package com.diasafenight.diasafenight.Helpers.Comparators;

import com.diasafenight.diasafenight.Model.Injection;
import com.diasafenight.diasafenight.Model.MeasurementInput;


import java.util.Comparator;

/**
 * Created by D.Blazhevsky on 18.01.2017.
 */

public class InjectionComparator implements Comparator<Injection> {

    @Override
    public int compare(Injection o1, Injection o2) {
        if(o1.InputOn.isBefore(o2.InputOn)){
            return 1;
        }
        else if(o1.InputOn.isAfter(o2.InputOn))
        {
            return -1;
        }
        else
            return 0;
    }
}
