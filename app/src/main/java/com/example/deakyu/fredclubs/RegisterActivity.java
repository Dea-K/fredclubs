package com.example.deakyu.fredclubs;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.GregorianCalendar;
import android.provider.CalendarContract;
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

        // catch values from edittexts
        etClub = (EditText) findViewById(R.id.etClub);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etConfirmPW = (EditText) findViewById(R.id.etConfirmPW);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etUsername = (EditText) findViewById(R.id.etUsername);

        // catch values from spinners
        yearSpinner = (Spinner) findViewById(R.id.year_spinner);
        positionSpinner = (Spinner) findViewById(R.id.position_spinner);

        // set adapter for spinners
        ArrayAdapter Yadapter = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter Padapter = ArrayAdapter.createFromResource(this, R.array.position, android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(Yadapter);
        yearSpinner.setOnItemSelectedListener(this);
        positionSpinner.setAdapter(Padapter);
        positionSpinner.setOnItemSelectedListener(this);
    }

    public void registerUser(View view) {

        String clubValue = etClub.getText().toString();
        String lastNameValue = etLastName.getText().toString();
        String firstNameValue = etFirstName.getText().toString();
        String confirmPWValue = etConfirmPW.getText().toString();
        String passwordValue = etPassword.getText().toString();
        String usernameValue = etUsername.getText().toString();
        String yearValue = yearSpinner.getSelectedItem().toString();
        String positionValue = positionSpinner.getSelectedItem().toString();

        // confirm if the username already exists
        boolean isUsernameExist = db.isExistingUsername(usernameValue);
        if(isUsernameExist) {
            Toast.makeText(this, "That username already exists", Toast.LENGTH_SHORT).show();
        } else {
            // confirm the passwords from both fields match
            if(etPassword.getText().toString() != etConfirmPW.getText().toString()) {
                // Create User
                try {
                    UserHelper.CreateUser(usernameValue, passwordValue, confirmPWValue, firstNameValue, lastNameValue, clubValue,
                            positionValue, yearValue);
                    setResult(Activity.RESULT_OK, getIntent());
                    this.finish();
                } catch (Exception e) {
                    // Error occurred
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                // make toast
                Toast.makeText(this, "Passwords do not match with the confirm, Try Again!", Toast.LENGTH_LONG).show();
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
