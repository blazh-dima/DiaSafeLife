package com.diasafenight.diasafenight;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.diasafenight.diasafenight.Model.DbContext;
import com.diasafenight.diasafenight.Model.InjectionType;

import java.util.ArrayList;

public class InjectionTypeList extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    DbContext context = null;
    ListView lvMain;
    ArrayAdapter<InjectionType> adapter;
    public Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injection_type_list);

        context = new DbContext(this);

        add = (Button) this.findViewById(R.id.InjectionTypeAddBtn);
        add.setOnClickListener(this);

        lvMain = (ListView) findViewById(R.id.injectionTypeList);
        //lvMain.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        this.setList();
        lvMain.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == add.getId())
        {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.injection_type_add_popup);
            dialog.show();

            dialog.findViewById(R.id.injTypeAddBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String value = ((EditText) dialog.findViewById(R.id.editInjectionType)).getText().toString();
                    InjectionType t = new InjectionType();
                    t.Name = value;
                    context.addInjectionType(t);
                    dialog.dismiss();
                    setList();
                }
            });
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final InjectionType model = (InjectionType)parent.getItemAtPosition(position);

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.injection_type_edit_popup);
        final EditText e = (EditText) dialog.findViewById(R.id.editInjectionType);
        e.setText(model.Name);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.injTypeEditBtn)
                {
                    model.Name = e.getText().toString();
                    context.updateInjectionType(model);
                }
                else if(v.getId() == R.id.injTypeDeleteBtn)
                {
                    context.deleteInjectionType(model);
                }
                dialog.dismiss();
                setList();
            }

        };
        dialog.findViewById(R.id.injTypeEditBtn).setOnClickListener(onClickListener);
        dialog.findViewById(R.id.injTypeDeleteBtn).setOnClickListener(onClickListener);
        dialog.show();


    }

    public void setList()
    {
        ArrayList<InjectionType> list = context.getInjectionTypeAll();
        ArrayAdapter<InjectionType> adapter = new ArrayAdapter<InjectionType>(this,
                android.R.layout.simple_list_item_1, list);
        lvMain.setAdapter(adapter);
    }



}
