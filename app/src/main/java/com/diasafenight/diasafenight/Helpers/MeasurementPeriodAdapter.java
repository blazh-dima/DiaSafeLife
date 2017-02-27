package com.diasafenight.diasafenight.Helpers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.diasafenight.diasafenight.MainActivity;
import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.MeasurementInput;
import com.diasafenight.diasafenight.Model.MeasurementPeriod;
import com.diasafenight.diasafenight.R;

import org.joda.time.LocalDate;

import java.util.ArrayList;

/**
 * Created by SL on 21.01.2017.
 */

public class MeasurementPeriodAdapter extends ArrayAdapter<MeasurementPeriod> {

    private LayoutInflater mInflater;
    private DbContext context;
    private boolean ShowDecimals;
    private LocalDate ViewDate;

    public MeasurementPeriodAdapter(ArrayList<MeasurementPeriod> list, boolean ShowDecimals, LocalDate ViewDate, AppCompatActivity activity) {
        super(activity.getApplicationContext(), R.layout.input_list_main_activity_item,  list);
        mInflater = LayoutInflater.from(activity.getApplicationContext());
        context = new DbContext(activity.getApplicationContext());
        this.ShowDecimals = ShowDecimals;
        this.ViewDate = ViewDate;
    }

    @NonNull
    public View getView(int position, View convertView,
                        @NonNull ViewGroup parent) {
        MeasurementPeriodAdapter.ViewHolder holder;
        View row=convertView;
        if(row==null){

            row = mInflater.inflate(R.layout.input_list_main_activity_item, parent, false);
            holder = new ViewHolder();
            holder.nameView = (TextView) row.findViewById(R.id.mainListperiodName);
            holder.value = (TextView) row.findViewById(R.id.mainLisValue);
            holder.tag = (TextView) row.findViewById(R.id.mainListTag);
            row.setTag(holder);
        }
        else
            holder = (MeasurementPeriodAdapter.ViewHolder)row.getTag();

        MeasurementPeriod period = getItem(position);
        MeasurementInput m = Utils.getValidMinMeasurementInput(ViewDate, period, context);
        if(m != null)
        {
            m.ShowDecimal = ShowDecimals;
            holder.nameView.setText(period.Name);
            holder.value.setText(m.getFormattedValue());
            holder.tag.setText(m.Tag.Name);
            holder.tag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.main_activity_list_ok12, 0);
        }
        else
        {
            holder.nameView.setText(period.Name);
            holder.tag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.main_activity_list_not12, 0);
        }
        return row;
    }

    class ViewHolder {;
        public TextView nameView;
        public TextView value;
        public TextView tag;
    }
}
