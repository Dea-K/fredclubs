package com.example.deakyu.fredclubs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner yearSpinner, positionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        yearSpinner = (Spinner) findViewById(R.id.year_spinner);
        positionSpinner = (Spinner) findViewById(R.id.position_spinner);

        ArrayAdapter Yadapter = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter Padapter = ArrayAdapter.createFromResource(this, R.array.position, android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(Yadapter);
        yearSpinner.setOnItemSelectedListener(this);
        positionSpinner.setAdapter(Padapter);
        positionSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView)view;
        Toast.makeText(this, tv.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
