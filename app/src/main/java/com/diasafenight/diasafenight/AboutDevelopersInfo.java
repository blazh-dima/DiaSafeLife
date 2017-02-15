package com.diasafenight.diasafenight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.diasafenight.diasafenight.Helpers.IconBar;

public class AboutDevelopersInfo extends AppCompatActivity {
    TextView devInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developers_info);

        IconBar iconBar = new IconBar(this);
        iconBar.registerIconBar();

        devInfo = (TextView) findViewById(R.id.devInfo);
        devInfo.setText("-Version 0.06b\n\n-Developers:\n" +
                "\tDmytro Blazhevsky\nVlad Petrus\n\n\n\n\n\n" +
                "\tThe prediction algorithm is based on scientifically " +
                "substantiated results obtained within EU projects \n\nDIAdvisor \n" +
                "cordis.europa.eu/project/rcn/85459_en.html\n" +
                "and " +
                "cordis.europa.eu/project/rcn/194356_en.html.");
    }
}
