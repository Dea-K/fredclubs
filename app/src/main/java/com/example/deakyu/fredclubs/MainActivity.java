package com.example.deakyu.fredclubs;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserHelper.openDB(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Acount Created!", Toast.LENGTH_SHORT).show();
        }

    }

    public void startRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, 1);
        // 1 -> id for RegisterActivity
    }

    public void login(View view) {
        EditText usernameField = (EditText) findViewById(R.id.username_login);
        EditText passwordField = (EditText) findViewById(R.id.password_login);
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        if(UserHelper.authenticateUser(username, password)) {
            Intent display = new Intent(this, DisplayScheduleActivity.class);
            usernameField.setText("");
            passwordField.setText("");
            startActivity(display);
        } else {
//            Toast.makeText(this, "Login Failed, Please Try Agin", Toast.LENGTH_LONG).show();
            Snackbar.make(view, "Login Failed, Please Try Again", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
