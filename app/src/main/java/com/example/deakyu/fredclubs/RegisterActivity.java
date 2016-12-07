package com.example.deakyu.fredclubs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.deakyu.fredclubs.UserHelper.db;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner yearSpinner, positionSpinner;
    EditText etClub, etLastName, etFirstName, etConfirmPW, etPassword, etUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // catch views from layout
        etClub = (EditText) findViewById(R.id.etClub);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etConfirmPW = (EditText) findViewById(R.id.etConfirmPW);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etUsername = (EditText) findViewById(R.id.etUsername);

        // catch values from spinners
        yearSpinner = (Spinner) findViewById(R.id.year_spinner);
        String yearValue = yearSpinner.getSelectedItem().toString();
        positionSpinner = (Spinner) findViewById(R.id.position_spinner);
        String positionValue = positionSpinner.getSelectedItem().toString();

        // set adapter for spinners
        ArrayAdapter Yadapter = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter Padapter = ArrayAdapter.createFromResource(this, R.array.position, android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(Yadapter);
        yearSpinner.setOnItemSelectedListener(this);
        positionSpinner.setAdapter(Padapter);
        positionSpinner.setOnItemSelectedListener(this);

        // confirm if the username already exists
        if(db.isExistingUsername(etUsername.getText().toString())) {
            Toast.makeText(this, "That username already exists", Toast.LENGTH_SHORT).show();
        } else {
            // confirm the passwords from both fields match
            if(etPassword.getText().toString() == etConfirmPW.getText().toString()) {
                // Create User
            } else {
                // make toast
            }
        }

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
