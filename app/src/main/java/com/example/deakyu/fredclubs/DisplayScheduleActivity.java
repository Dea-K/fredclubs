package com.example.deakyu.fredclubs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.deakyu.fredclubs.MeetingRegisterActivity._dayOfWeekFormatter;
import static com.example.deakyu.fredclubs.User._loggedUser;

public class DisplayScheduleActivity extends AppCompatActivity {

    // get views
    private Button btnMonday;
    private Button btnTuesday;
    private Button btnWednesday;
    private Button btnThursday;
    private Button btnFriday;
    private Button btnSaturday;
    private Button btnSunday;
    private Button btnPrevious;
    private Button btnNext;

    private TextView txtMonday;
    private TextView txtTuesday;
    private TextView txtWednesday;
    private TextView txtThursday;
    private TextView txtFriday;
    private TextView txtSaturday;
    private TextView txtSunday;

    private TextView txtUsername;
    final Context context_this = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateViews();
        txtUsername.setText( _loggedUser.username);
        populateButtons();  // set texts for buttons as current week

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerSchedule = new Intent(context_this, MeetingRegisterActivity.class);
                startActivity(registerSchedule);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_signout) {
            Intent intent = new Intent(this, MainActivity.class);
            _loggedUser = null;
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void populateViews() {
        btnMonday = (Button) findViewById(R.id.monday);
        btnTuesday = (Button) findViewById(R.id.tuesday);
        btnWednesday = (Button) findViewById(R.id.wednesday);
        btnThursday = (Button) findViewById(R.id.thursday);
        btnFriday = (Button) findViewById(R.id.friday);
        btnSaturday = (Button) findViewById(R.id.saturday);
        btnSunday = (Button) findViewById(R.id.sunday);

//        btnPrevious = (Button) findViewById(R.id.previous_week);
//        btnNext = (Button) findViewById(R.id.next_week);

        txtMonday = (TextView) findViewById(R.id.monday_detail);
        txtTuesday = (TextView) findViewById(R.id.tuesday_detail);
        txtWednesday = (TextView) findViewById(R.id.wednesday_detail);
        txtThursday = (TextView) findViewById(R.id.thursday_detail);
        txtFriday = (TextView) findViewById(R.id.friday_detail);
        txtSaturday = (TextView) findViewById(R.id.saturday_detail);
        txtSunday = (TextView) findViewById(R.id.sunday_detail);

        txtUsername = (TextView) findViewById(R.id.display_username);
    }

    public void populateButtons() {
        // get current week
        Calendar currentDate = Calendar.getInstance();

        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        btnMonday.setText(currentDate.get(Calendar.MONTH) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Monday)");
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        btnTuesday.setText(currentDate.get(Calendar.MONTH) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Tuesday)");
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        btnWednesday.setText(currentDate.get(Calendar.MONTH) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Wednesday)");
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        btnThursday.setText(currentDate.get(Calendar.MONTH) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Thursday)");
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        btnFriday.setText(currentDate.get(Calendar.MONTH) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Friday)");
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        btnSaturday.setText(currentDate.get(Calendar.MONTH) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Saturday)");
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        btnSunday.setText(currentDate.get(Calendar.MONTH) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Sunday)");
    }

    public void populateSchedules() {
        
    }

}
