package com.diasafenight.diasafenight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.diasafenight.diasafenight.Helpers.IconBar;
import com.diasafenight.diasafenight.Helpers.Comparators.InjectionComparator;
import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.Injection;
import com.diasafenight.diasafenight.Model.MeasurementInput;


import java.util.ArrayList;
import java.util.Collections;

public class InjectionList extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    DbContext context = null;
    ListView lvMain;
    Button injectionAddBtn;
    Button injectionTypeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injection);
        IconBar i = new IconBar(this);
        i.registerIconBar();
        context = new DbContext(this);

        injectionAddBtn = (Button) this.findViewById(R.id.addInjectionBtn);
        injectionTypeBtn = (Button) this.findViewById(R.id.injectionTypeBtn);
        injectionAddBtn.setOnClickListener(this);
        injectionTypeBtn.setOnClickListener(this);


        lvMain = (ListView) findViewById(R.id.injectionListView);
        lvMain.setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.setList();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == injectionAddBtn.getId())
        {
            Intent n = new Intent(this, InjectionInput.class);
            this.startActivity(n);
        }
        else if(v.getId() == injectionTypeBtn.getId())
        {
            Intent n = new Intent(this, InjectionTypeList.class);
            this.startActivity(n);
        }

    }
    public void setList()
    {

        ArrayList<Injection> list = context.getInjectionAll();
        Collections.sort(list, new InjectionComparator());
        ArrayAdapter<Injection> adapter = new ArrayAdapter<>(this,
                R.layout.input_list_item, list);
        lvMain.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Injection value = (Injection)adapterView.getItemAtPosition(position);
        Intent n = new Intent(this, InjectionEdit.class);
        n.putExtra("Injection", value);
        startActivityForResult(n, RESULT_OK);
    }
}
