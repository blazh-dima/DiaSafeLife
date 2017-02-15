package com.diasafenight.diasafenight.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by D.Blazhevsky on 19.01.2017.
 */

public class MeasurementInputAdapter extends ArrayAdapter<MeasurementInput> {


    public MeasurementInputAdapter(Context context, int resource, List<MeasurementInput> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MeasurementInput m = (MeasurementInput)this.getItem(position);

        return super.getView(position, convertView, parent);
    }
}
