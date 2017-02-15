package com.diasafenight.diasafenight;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.User;

import org.joda.time.LocalDate;

import java.util.ArrayList;

public class PrimaryData extends AppCompatActivity implements View.OnClickListener{
    private DbContext context = null;
    private Spinner editGender = null;
    private Spinner birthyear = null;
    private Spinner illFrom = null;
    private EditText a1c = null;
    private TextView ok = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_data);

        context = new DbContext(this);
        editGender = (Spinner)findViewById(R.id.editGender);
        birthyear = (Spinner)findViewById(R.id.birthyear);
        illFrom = (Spinner)findViewById(R.id.illfrom);
        a1c = (EditText)findViewById(R.id.editA1C);
        ok = (TextView)findViewById(R.id.OkPrimaryInput);
        ok.setOnClickListener(this);

        ArrayAdapter<String> genderAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.genders));
        editGender.setAdapter(genderAdapter);

        ArrayAdapter<String> birthyearAdapter =
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item, this.getYearRange(1900,
                        LocalDate.now().getYear()));
        birthyear.setAdapter(birthyearAdapter);

        ArrayAdapter<String> illFromAdapter =
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item, this.getYearRange(1900,
                        LocalDate.now().getYear()));
        illFrom.setAdapter(illFromAdapter);


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.OkPrimaryInput){
            User u = User.GetCurrent(this);

            if(u != null)
            {
                String a = a1c.getText().toString();
                u.Gender = editGender.getSelectedItem().toString();
                u.BirthYear = birthyear.getSelectedItem().toString();
                u.IllFrom = illFrom.getSelectedItem().toString();
                u.A1C = a.isEmpty() ? 0 : Integer.parseInt(a);
                User.CreateOrUpdate(u, this);
            }

        }

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public ArrayList<String> getYearRange(int from, int to)
    {
        ArrayList<String> list = new ArrayList<>();
        for(int i = to; i>= from; i--)
            list.add(String.valueOf(i));
        return  list;
    }
}
