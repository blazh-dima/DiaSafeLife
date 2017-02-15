package com.diasafenight.diasafenight;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;

import com.diasafenight.diasafenight.Helpers.Comparators.MeasurementInputComparator;
import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.MeasurementInput;
import com.diasafenight.diasafenight.Model.Prediction;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Comparator.comparing;

public class Statistic_prob extends AppCompatActivity {
    LineChart lineChart;
    LineChart inputChart;
    DbContext context = new DbContext(this);

    protected ArrayList<MeasurementInput> getWeekData()
    {
        ArrayList<MeasurementInput> preds = new ArrayList<MeasurementInput>();
        for(int i = 6;i>=0;i--)
        {
            ArrayList<MeasurementInput> items = context.getMeasurementInputByDay(LocalDate.now().minusDays(i));
            if(items.size()>0)
            {
                for(int j = 0;j<items.size();j++)
                {
                    preds.add(items.get(j));
                }
                //preds.add(items.get(items.size()-1));
            }
        }
        Collections.sort(preds, new MeasurementInputComparator());

        return preds;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_prob);

        DbContext conn = new DbContext(this);
        ArrayList<MeasurementInput> preds = getWeekData();
//        for(int i = 6algo;i>=0;i--)
//        {
//            ArrayList<MeasurementInput> items = conn.getMeasurementInputByDay(LocalDate.now().minusDays(i));
//            if(items.size()>0)
//            {
//                for(int j = 0;j<items.size();j++)
//                {
//                    preds.add(items.get(j));
//                }
//                //preds.add(items.get(items.size()-1));
//            }
//        }
        lineChart = (LineChart) findViewById(R.id.probChart);

        ArrayList<String> xAXES = new ArrayList<>();
        ArrayList<Entry> yAXESsin = new ArrayList<>();
        ArrayList<Entry> yAXEScos = new ArrayList<>();

        double x = 0 ;
        int numDataPoints = 10;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        formatter.setLenient(false);
        for(int i=preds.size()-1;i>=0;i--){
//            float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
//            x = x + 10;
//            DateTime d = preds.get(i).InputOn;
////            long tmp = d.getMillis();
////            String oldTime = d.toString ().replace ( "T", " " );
//
//            String ds = d.toString ().replace ( "T", " " );
//            String[] items = ds.split(" ")[0].split("-");
//            //String df = (items[3]+"/"+items[1]+"/"+items[0]);
//            String df = (items[1]+"."+items[2]);
//
//            //float date = Float.parseFloat(formatter.format(preds.get(i).InputOn));
//            float date = Float.parseFloat(df);
//            //Entry item = new Entry( date,(float) preds.get(i).Value);
            long tmp = preds.get(i).InputOn.getMillis() - preds.get(preds.size()-1).InputOn.getMillis();
            Entry item = new Entry( tmp,(float) preds.get(i).Value);

            yAXEScos.add(item);

        }




        LineDataSet lineDataSet1 = new LineDataSet(yAXEScos,"Glucose 7 days");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.rgb(105,189,217));
        lineDataSet1.setDrawFilled(true);
        lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);


//        lineDataSet1Set
//        //lineDataSet1.setValueFormatter(new IAxisValueFormatter() {
//
//
//            @Override
//            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//                return DateUtils.formatDateTime(getApplicationContext(), (long) value, DateUtils.FORMAT_SHOW_DATE);
//                //return xValues[(int)value % xValues.length];
//            }
//        });



        //LineData lineData = new LineData(lineDataSet1);
        //lineData.setDrawValues(true); //


        LineData data = new LineData(lineDataSet1);
        data.setDrawValues(true);

        lineChart.setData(data);

        XAxis mxasis1 = lineChart.getXAxis();
        mxasis1.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        mxasis1.setDrawGridLines(false);
        mxasis1.setGranularity(1f); // only intervals of 1 day
        //mxasis.setTypeface(mTfLight);
        mxasis1.setTextSize(8);
        //mxasis.setTextColor(ContextCompat.getColor(this, R.color.colorYellow));
        mxasis1.setValueFormatter(
                new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        ArrayList<MeasurementInput> preds = getWeekData();
                        long startDate = preds.get(0).InputOn.getMillis();
                        DateTime plusDate = new DateTime(startDate + (long) value);
                        String ds = plusDate.toString ().replace ( "T", " " );
                        //String ds = LocalDate.now().toString ().replace ( "T", " " );
                        String[] items = ds.split(" ")[0].split("-");
                        //String df = (items[3]+"/"+items[1]+"/"+items[0]);
                        String df = (items[2]+":"+items[1]);
                        return df;

                    }
                }
        );

        lineChart.setVisibleXRangeMaximum(200000000000f);
        //lineChart.setVisibleXRangeMinimum(preds.get(preds.size()-1).InputOn.getMillis()-preds.get(0).InputOn.getMillis()+40000f);
        lineChart.setVisibleXRangeMinimum(400000000f);

//        lineChart.setVisibleYRangeMinimum(90f, YAxis.AxisDependency.LEFT);
//        lineChart.setVisibleYRangeMaximum(220f, YAxis.AxisDependency.LEFT);


        lineChart.setVisibleXRangeMaximum(30f);
        lineChart.setVisibleYRangeMaximum(240f, YAxis.AxisDependency.LEFT);

        lineChart.invalidate();

        ///INPUT DATA
        ArrayList<Entry> entrs_inp = new ArrayList<>();
        ArrayList<MeasurementInput> datapDay = conn.getMeasurementInputByDay(LocalDate.now());
        Collections.sort(datapDay, new MeasurementInputComparator());

        inputChart = (LineChart) findViewById(R.id.inputChart);
        DateTime bac_d = DateTime.now();
        for(int i=datapDay.size()-1;i>=0;i--){

            //long tmp = datapDay.get(i).InputOn.getMillis() - datapDay.get(0).InputOn.getMillis();
            //long tmp = datapDay.get(i).InputOn.getMillis() - LocalDate.now().toDateTimeAtStartOfDay().getMillis();
            long tmp = datapDay.get(i).InputOn.getMillis() - datapDay.get(datapDay.size()-1).InputOn.getMillis();

            //float cosFunction = Float.parseFloat(String.valueOf(Math.cos(x)));
            //x = x + 10;
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
                        ArrayList<MeasurementInput> datapDay = context.getMeasurementInputByDay(LocalDate.now());
                        long startDate = datapDay.get(0).InputOn.getMillis();
                        DateTime plusDate = new DateTime(startDate + (long) value);
                        String ds = plusDate.toString ().replace ( "T", " " );
                        //String ds = LocalDate.now().toString ().replace ( "T", " " );
                        String[] items = ds.split(" ")[1].split(":");
                        //String df = (items[3]+"/"+items[1]+"/"+items[0]);
                        return (items[0]+":"+items[1]);

                    }
                }
        );

        inputChart.setVisibleXRangeMaximum(2000000000f);
        //inputChart.setVisibleXRangeMinimum(datapDay.get(datapDay.size()-1).InputOn.getMillis()-datapDay.get(0).InputOn.getMillis()+100f);
        inputChart.setVisibleXRangeMinimum(2000000f);

        inputChart.setVisibleYRangeMaximum(220f, YAxis.AxisDependency.LEFT);
        inputChart.setVisibleYRangeMinimum(12f, YAxis.AxisDependency.LEFT);
        inputChart.invalidate();

        /////////////
    }
}
