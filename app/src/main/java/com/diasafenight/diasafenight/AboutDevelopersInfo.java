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
        devInfo.setText(getResources().getString(R.string.about_developers));
    }
}
