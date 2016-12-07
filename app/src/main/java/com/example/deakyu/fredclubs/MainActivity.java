package com.example.deakyu.fredclubs;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    public void startMeeting(View view) {
        Intent intent = new Intent(this, MeetingRegisterActivity.class);
        startActivity(intent);
    }
}
