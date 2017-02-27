package com.diasafenight.diasafenight;

import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.diasafenight.diasafenight.Algorithm.AlgPred;
import com.diasafenight.diasafenight.Helpers.AlarmReceiver;
import com.diasafenight.diasafenight.Helpers.Comparators.PredicitionComparator;
import com.diasafenight.diasafenight.Helpers.IconBar;
import com.diasafenight.diasafenight.Helpers.MeasurementPeriodAdapter;
import com.diasafenight.diasafenight.Helpers.Utils;
import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.InjectionType;
import com.diasafenight.diasafenight.Model.MeasurementInput;
import com.diasafenight.diasafenight.Model.MeasurementPeriod;
import com.diasafenight.diasafenight.Model.Prediction;
import com.diasafenight.diasafenight.Model.User;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button enterDataBtn;
    TextView percText;
    TextView riskTxt;
    TextView textViewInforesult;
    TextView algIngobtn;
    ListView lv;
    DbContext dbase;
    private boolean ShowDecimals = false;
    private String riskPopupMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbase = new DbContext(this);

        IconBar i = new IconBar(this);
        i.registerIconBar();

        ShowDecimals = User.GetCurrent(this).MeasurementTypeId != 1; //mgdl
        lv = (ListView) findViewById(R.id.mainInputList);
        enterDataBtn = (Button) this.findViewById(R.id.enterDataBtn);
        algIngobtn = (TextView) this.findViewById(R.id.algIngobtn);
        percText = (TextView) this.findViewById(R.id.percantageTextView);
        riskTxt = (TextView)findViewById(R.id.riskTxt);
        textViewInforesult = (TextView) findViewById(R.id.textViewInforesult);

        riskTxt.setOnClickListener(this);
        percText.setOnClickListener(this);
        algIngobtn.setOnClickListener(this);
        enterDataBtn.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        percText = (TextView) this.findViewById(R.id.percantageTextView);
        ((TextView)findViewById(R.id.todayTxt))
                .setText("Today " + DateTimeFormat.forPattern("yyyy-MM-dd").print(DateTime.now()));
        this.setList();
        this.showPrediction();
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

       if(id == R.id.enterDataBtn){
           Intent intent = new Intent(this, GlucoseInput.class);
           this.startActivityForResult(intent, 200);
       }
        if(id == R.id.algIngobtn)
            this.showPopupInfo(getResources().getString(R.string.algorithm_info_message));

        if(id == R.id.riskTxt || id == percText.getId())
            this.showPopupInfo(riskPopupMessage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void showPopupInfo(String message)
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_popup_alginf);
        ((TextView)dialog.findViewById(R.id.textViewMessage)).setText(message);
        dialog.show();

        dialog.findViewById(R.id.alginfCloseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void startCountAnimation(int valueTo, final TextView textView) {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, valueTo);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(animation.getAnimatedValue().toString() + "%");
            }
        });
        animator.start();
    }
    public void setList()
    {
        ArrayList<MeasurementPeriod> list = dbase.getMeasurementPeriodAll();
        lv.setAdapter(new MeasurementPeriodAdapter(list, ShowDecimals, this));

    }
    public void showPrediction()
    {
        Prediction p = dbase.getFirstPredictionByDay(LocalDate.now());
        if(p != null) {
            percText.setBackgroundResource(0);
            int res = p.Value;
            if(res <= getResources().getInteger(R.integer.low_risk)) {
                riskTxt.setText(getResources().getString(R.string.low_risk));
                riskPopupMessage = getResources().getString(R.string.low_risk_message);
            }
            else if(res <= getResources().getInteger(R.integer.moderate_risk))
            {
                riskTxt.setText(getResources().getString(R.string.moderate_risk));
                riskPopupMessage = getResources().getString(R.string.moderate_risk_message);
            }
            else if(res <= getResources().getInteger(R.integer.high_risk))
            {
                riskTxt.setText(getResources().getString(R.string.high_risk));
                riskPopupMessage = getResources().getString(R.string.high_risk_message);
            }
            else if(res <= getResources().getInteger(R.integer.extreme_risk)) {
                riskTxt.setText(getResources().getString(R.string.extreme_risk));
                riskPopupMessage = getResources().getString(R.string.extreme_risk_message);
            }
            this.startCountAnimation(res, percText);
        }
        else
        {
            percText.setText("");
            riskTxt.setText(getResources().getString(R.string.no_data_message));
            percText.setBackgroundResource(R.drawable.waiting);
            riskPopupMessage = getResources().getString(R.string.no_data_message);
        }

    }

}

