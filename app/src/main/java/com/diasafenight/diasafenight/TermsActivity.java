package com.diasafenight.diasafenight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Toast;

import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.User;

public class TermsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

    }
    @Override
    public void onClick(View v){
        if(v.getId() == R.id.acceptTerms){
            Intent n = new Intent(this, PrimaryData0.class);
            startActivity(n);
        }
        else if(v.getId() == R.id.declineTerms){
            finish();
        }

    }
}
