package com.diasafenight.diasafenight.Helpers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.diasafenight.diasafenight.AboutDevelopersInfo;
import com.diasafenight.diasafenight.InjectionList;
import com.diasafenight.diasafenight.GlucoseList;
import com.diasafenight.diasafenight.MainActivity;
import com.diasafenight.diasafenight.R;
import com.diasafenight.diasafenight.Statistic_prob;

/**
 * Created by D.Blazhevsky on 18.01.2017.
 */

public class IconBar implements View.OnClickListener {
    public AppCompatActivity activity = null;

    private Button graphBtn;
    private Button devInfoBtn;
    private Button glucoseListBtn;
    private Button injectionBtn;
    private Button homeBtn;

    public IconBar(AppCompatActivity act)
    {
        activity = act;
        graphBtn = (Button)activity.findViewById(R.id.graphBtn);
        devInfoBtn = (Button)activity.findViewById(R.id.devInfoBtn);
        glucoseListBtn = (Button)activity.findViewById(R.id.glucoseListBtn);
        injectionBtn = (Button)activity.findViewById(R.id.insulineBtn);
        homeBtn = (Button)activity.findViewById(R.id.homeBtn);
    }
    public void registerIconBar()
    {
        Class cl = activity.getClass();

        if(cl != Statistic_prob.class)
            graphBtn.setOnClickListener(this);
        if(cl != AboutDevelopersInfo.class)
            devInfoBtn.setOnClickListener(this);
        if(cl != GlucoseList.class)
            glucoseListBtn.setOnClickListener(this);
        if(cl != InjectionList.class)
            injectionBtn.setOnClickListener(this);
        if(cl != MainActivity.class)
            homeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == graphBtn.getId())
            activity.startActivity(new Intent(activity, Statistic_prob.class));
        else if(id == devInfoBtn.getId())
            activity.startActivity(new Intent(activity, AboutDevelopersInfo.class));
        else if(id == glucoseListBtn.getId())
            activity.startActivity(new Intent(activity, GlucoseList.class));
        else if(id == injectionBtn.getId())
            activity.startActivity(new Intent(activity, InjectionList.class));
        else if(id == homeBtn.getId()){
            Intent intent = new Intent(activity, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);
        }

    }
}
