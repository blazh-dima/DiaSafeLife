package com.diasafenight.diasafenight;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Color;

import com.diasafenight.diasafenight.Helpers.Comparators.MeasurementInputComparator;
import com.diasafenight.diasafenight.Helpers.IconBar;
import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.MeasurementInput;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;

public class Statistic_prob extends AppCompatActivity {
    LineChart lineChart;
    LineChart inputChart;

    protected ArrayList<MeasurementInput> getWeekData()
    {
        DbContext conn = new DbContext(this);
        ArrayList<MeasurementInput> preds = new ArrayList<MeasurementInput>();
        for(int i = 6;i>=0;i--)
        {
            ArrayList<MeasurementInput> items = conn.getMeasurementInputByDay(LocalDate.now().minusDays(i));
            if(items.size()>0)
            {
                for(int j = 0;j<items.size();j++)
                {
                    preds.add(items.get(j));
                }
            }
        }
        return preds;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_prob);

        IconBar icons = new IconBar(this);
        icons.registerIconBar();

        DbContext conn = new DbContext(this);
        ArrayList<MeasurementInput> preds = getWeekData();
        Collections.sort(preds, new MeasurementInputComparator());

        lineChart = (LineChart) findViewById(R.id.probChart);

        ArrayList<Entry> yAXEScos = new ArrayList<>();

        double x = 0 ;
        int numDataPoints = 10;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        formatter.setLenient(false);

        for(int i=0;i<preds.size();i++){
            //long tmp = preds.get(i).InputOn.getMillis() - preds.get(0).InputOn.getMillis();
            long tmp = preds.get(i).InputOn.getMillis() - new LocalDate().toDateTimeAtCurrentTime().getMillis();
            //LocalDate().toDateTimeAtCurrentTime().minusDays(7).getMillis();
            Entry item = new Entry( tmp,(float) preds.get(i).Value);
            yAXEScos.add(item);
        }




        LineDataSet lineDataSet1 = new LineDataSet(yAXEScos,"Glucose 7 days");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.rgb(105,189,217));
        lineDataSet1.setDrawFilled(true);
        lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);


        LineData data = new LineData(lineDataSet1);
        data.setDrawValues(true);

        if(preds.size()>0)
            lineChart.setData(data);

        XAxis mxasis1 = lineChart.getXAxis();
        mxasis1.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        mxasis1.setDrawGridLines(false);
        mxasis1.setTextSize(8);
        mxasis1.setValueFormatter(
                new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        //ArrayList<MeasurementInput> preds = getWeekData();
                        //long startDate = preds.get(0).InputOn.getMillis();
                        long startDate = new LocalDate().toDateTimeAtCurrentTime().getMillis();
                        DateTime plusDate = new DateTime(startDate + (long) value);
                        String ds = plusDate.toString ().replace ( "T", " " );
                        String[] items = ds.split(" ")[0].split("-");
                        String df = (items[2]+":"+items[1]);
                        return df;
                    }
                }
        );

        lineChart.setVisibleXRangeMaximum(200000000000f);
        lineChart.setVisibleXRangeMinimum(400000000f);

        lineChart.setVisibleXRangeMaximum(30f);
        lineChart.setVisibleYRangeMaximum(240f, YAxis.AxisDependency.LEFT);

        lineChart.setNoDataText("No data");
        lineChart.invalidate();

        ///INPUT DATA
        ArrayList<Entry> entrs_inp = new ArrayList<>();
        ArrayList<MeasurementInput> datapDay = conn.getMeasurementInputByDay(LocalDate.now());
        Collections.sort(datapDay, new MeasurementInputComparator());

        inputChart = (LineChart) findViewById(R.id.inputChart);
        DateTime bac_d = DateTime.now();
        for(int i=0;i<datapDay.size();i++){
            //long tmp = datapDay.get(i).InputOn.getMillis() - datapDay.get(0).InputOn.getMillis();
            long tmp = datapDay.get(i).InputOn.getMillis() - new LocalDate().toDateTimeAtCurrentTime().minusDays(7).getMillis();
            //new LocalDate().toDateTimeAtCurrentTime()
            Entry item = new Entry(tmp, (float)datapDay.get(i).Value);
            entrs_inp.add(item);
        }
        LineDataSet lineDataSet2 = new LineDataSet(entrs_inp,"Glucose today");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.rgb(241,140,155));
        lineDataSet2.setDrawFilled(true);
        lineDataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData data2 = new LineData(lineDataSet2);
        data2.setDrawValues(true);
        if(datapDay.size()>0)
            inputChart.setData(data2);

        inputChart.setDescription( new Description());

        XAxis mxasis = inputChart.getXAxis();
        mxasis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        mxasis.setDrawGridLines(false);
        mxasis.setGranularity(1f); // only intervals of 1 day
        //mxasis.setTypeface(mTfLight);
        mxasis.setTextSize(8);
        //mxasis.setTextColor(ContextCompat.getColor(this, R.color.colorYellow));
        mxasis.setValueFormatter(
                new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        //DbContext conn = new DbContext(null);
                        //ArrayList<MeasurementInput> datapDay = conn.getMeasurementInputByDay(LocalDate.now());
                        //long startDate = datapDay.get(0).InputOn.getMillis();
                        long startDate = new LocalDate().toDateTimeAtCurrentTime().minusDays(7).getMillis();
                        DateTime plusDate = new DateTime(startDate + (long) value);
                        String ds = plusDate.toString ().replace ( "T", " " );
                        String[] items = ds.split(" ")[1].split(":");
                        return  (items[0]+":"+items[1]);
                        //return df;

                    }
                }
        );

        inputChart.setVisibleXRangeMaximum(2000000000f);
        inputChart.setVisibleXRangeMinimum(2000000f);
        inputChart.setVisibleYRangeMaximum(220f, YAxis.AxisDependency.LEFT);
        inputChart.setVisibleYRangeMinimum(12f, YAxis.AxisDependency.LEFT);
        inputChart.setNoDataText("No data");
        inputChart.invalidate();

        /////////////
    }
}