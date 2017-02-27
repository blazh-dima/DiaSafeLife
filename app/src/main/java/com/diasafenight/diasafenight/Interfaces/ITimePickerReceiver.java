package com.diasafenight.diasafenight.Interfaces;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * Created by SL on 27.02.2017.
 */

public interface ITimePickerReceiver {
    void OnTimePicked(LocalTime time);
}
