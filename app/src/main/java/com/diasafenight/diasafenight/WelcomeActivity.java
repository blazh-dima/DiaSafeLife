package com.diasafenight.diasafenight;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.MeasurementType;
import com.diasafenight.diasafenight.Model.User;

public class WelcomeActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    private DbContext context = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        context = new DbContext(this);
        final WelcomeActivity activity = this;

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                User n = User.GetCurrent(activity.getApplicationContext());
                Intent i = null;
                if(n == null )
                    i = new Intent(WelcomeActivity.this, TermsActivity.class);
                else
                    i = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                context.close();

            }
        }, SPLASH_TIME_OUT);

    }
}
