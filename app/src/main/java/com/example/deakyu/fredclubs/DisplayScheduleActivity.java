package com.example.deakyu.fredclubs;

import android.app.Activity;
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
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.deakyu.fredclubs.MeetingRegisterActivity._dayOfWeekFormatter;
import static com.example.deakyu.fredclubs.User._loggedUser;
import static com.example.deakyu.fredclubs.UserHelper.db;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 2 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Meeting Registered!", Toast.LENGTH_SHORT).show();
            final Calendar ca = Calendar.getInstance();
            List<Schedule> sch = db.getSchedulesOfWeekByDate(ca, ca.get(Calendar.YEAR));
            removeFormerMeetings();
            populateButtons(sch, ca);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateViews();
        txtUsername.setText( _loggedUser.username);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerSchedule = new Intent(context_this, MeetingRegisterActivity.class);
                startActivityForResult(registerSchedule, 2);
                // 2 is the id for MeetingRegisterActivity
            }
        });
        final Calendar ca = Calendar.getInstance();
        List<Schedule> sch = db.getSchedulesOfWeekByDate(ca, ca.get(Calendar.YEAR));
        populateButtons(sch, ca);       // set texts for buttons as current week
        setVisiblePropertyForButtons(); // set onclicklisteners for the buttons

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ca.add(Calendar.DAY_OF_YEAR, -7);
                List<Schedule> sch = db.getSchedulesOfWeekByDate(ca, ca.get(Calendar.YEAR));
                getPreviousWeek(sch, ca);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ca.add(Calendar.DAY_OF_YEAR, +7);
                List<Schedule> sch = db.getSchedulesOfWeekByDate(ca, ca.get(Calendar.YEAR));
                getNextWeek(sch, ca);
            }
        });

        // TODO
        // Populate update/delete button only for the user who registered the meeting

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
            _loggedUser = null;
            this.finish();
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

        btnPrevious = (Button) findViewById(R.id.previous_week);
        btnNext = (Button) findViewById(R.id.next_week);

        txtMonday = (TextView) findViewById(R.id.monday_detail);
        txtTuesday = (TextView) findViewById(R.id.tuesday_detail);
        txtWednesday = (TextView) findViewById(R.id.wednesday_detail);
        txtThursday = (TextView) findViewById(R.id.thursday_detail);
        txtFriday = (TextView) findViewById(R.id.friday_detail);
        txtSaturday = (TextView) findViewById(R.id.saturday_detail);
        txtSunday = (TextView) findViewById(R.id.sunday_detail);

        txtUsername = (TextView) findViewById(R.id.display_username);
    }

    public void setVisiblePropertyForButtons() {
        btnMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtMonday.isShown()) {
                    txtMonday.setVisibility(View.GONE);
                } else {
                    txtMonday.setVisibility(View.VISIBLE);
                }
            }
        });

        btnTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtTuesday.isShown()) {
                    txtTuesday.setVisibility(View.GONE);
                } else {
                    txtTuesday.setVisibility(View.VISIBLE);
                }
            }
        });

        btnWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtWednesday.isShown()) {
                    txtWednesday.setVisibility(View.GONE);
                } else {
                    txtWednesday.setVisibility(View.VISIBLE);
                }
            }
        });

        btnThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtThursday.isShown()) {
                    txtThursday.setVisibility(View.GONE);
                } else {
                    txtThursday.setVisibility(View.VISIBLE);
                }
            }
        });

        btnFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtFriday.isShown()) {
                    txtFriday.setVisibility(View.GONE);
                } else {
                    txtFriday.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtSaturday.isShown()) {
                    txtSaturday.setVisibility(View.GONE);
                } else {
                    txtSaturday.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtSunday.isShown()) {
                    txtSunday.setVisibility(View.GONE);
                } else {
                    txtSunday.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void getPreviousWeek(List<Schedule> sch, Calendar currentDate) {
        removeFormerMeetings();
        populateButtons(sch, currentDate);
    }

    public void getNextWeek(List<Schedule> sch, Calendar currentDate) {
        removeFormerMeetings();
        populateButtons(sch, currentDate);
    }

    public void removeFormerMeetings() {
        txtMonday.setText("");
        txtMonday.setVisibility(View.GONE);
        txtTuesday.setText("");
        txtTuesday.setVisibility(View.GONE);
        txtWednesday.setText("");
        txtWednesday.setVisibility(View.GONE);
        txtThursday.setText("");
        txtThursday.setVisibility(View.GONE);
        txtFriday.setText("");
        txtFriday.setVisibility(View.GONE);
        txtSaturday.setText("");
        txtSaturday.setVisibility(View.GONE);
        txtSunday.setText("");
        txtSunday.setVisibility(View.GONE);
    }

    public void populateButtons(List<Schedule> sch, Calendar currentDate) {
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        btnMonday.setText((currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Monday)");
        for(int i=0 ; i < sch.size() ; i++) {
            if(sch.get(i).day == currentDate.get(Calendar.DAY_OF_MONTH)) {
                txtMonday.setText(txtMonday.getText().toString() + "Meeting Title: " + sch.get(i).title + "\nDate: " +
                        String.valueOf(sch.get(i).month) + "/" + String.valueOf(sch.get(i).day) + " (" + sch.get(i).dayofweek + ")\n"
                        + "Time: " + String.valueOf(sch.get(i).hour) + ":" + String.valueOf(sch.get(i).minute) + "\nClub: "
                        + sch.get(i).club + "\nAuthor: " + sch.get(i).username + "\nDetail: " + sch.get(i).detail + "\n\n");
                txtMonday.setVisibility(View.VISIBLE);
//                if(sch.get(i).user_id == _loggedUser.id) {
//                    txtMonday.setText(txtMonday.getText().toString() + ": by " + sch.get(i).username);
//                }
            }
        }
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        btnTuesday.setText((currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Tuesday)");
        for(int i=0 ; i < sch.size() ; i++) {
            if(sch.get(i).day == currentDate.get(Calendar.DAY_OF_MONTH)) {
                txtTuesday.setText(txtTuesday.getText().toString() + "Meeting Title: " + sch.get(i).title + "\nDate: " +
                        String.valueOf(sch.get(i).month) + "/" + String.valueOf(sch.get(i).day) + " (" + sch.get(i).dayofweek + ")\n"
                        + "Time: " + String.valueOf(sch.get(i).hour) + ":" + String.valueOf(sch.get(i).minute) + "\nClub: "
                        + sch.get(i).club + "\nAuthor: " + sch.get(i).username + "\nDetail: " + sch.get(i).detail + "\n\n");
                txtTuesday.setVisibility(View.VISIBLE);
            }
        }
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        btnWednesday.setText((currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Wednesday)");
        for(int i=0 ; i < sch.size() ; i++) {
            if(sch.get(i).day == currentDate.get(Calendar.DAY_OF_MONTH)) {
                txtWednesday.setText(txtWednesday.getText().toString() + "Meeting Title: " + sch.get(i).title + "\nDate: " +
                        String.valueOf(sch.get(i).month) + "/" + String.valueOf(sch.get(i).day) + " (" + sch.get(i).dayofweek + ")\n"
                        + "Time: " + String.valueOf(sch.get(i).hour) + ":" + String.valueOf(sch.get(i).minute) + "\nClub: "
                        + sch.get(i).club + "\nAuthor: " + sch.get(i).username + "\nDetail: " + sch.get(i).detail + "\n\n");
                txtWednesday.setVisibility(View.VISIBLE);
            }
        }
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        btnThursday.setText((currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Thursday)");
        for(int i=0 ; i < sch.size() ; i++) {
            if(sch.get(i).day == currentDate.get(Calendar.DAY_OF_MONTH)) {
                txtThursday.setText(txtThursday.getText().toString() + "Meeting Title: " + sch.get(i).title + "\nDate: " +
                        String.valueOf(sch.get(i).month) + "/" + String.valueOf(sch.get(i).day) + " (" + sch.get(i).dayofweek + ")\n"
                        + "Time: " + String.valueOf(sch.get(i).hour) + ":" + String.valueOf(sch.get(i).minute) + "\nClub: "
                        + sch.get(i).club + "\nAuthor: " + sch.get(i).username + "\nDetail: " + sch.get(i).detail + "\n\n");
                txtThursday.setVisibility(View.VISIBLE);
            }
        }
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        btnFriday.setText((currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Friday)");
        for(int i=0 ; i < sch.size() ; i++) {
            if(sch.get(i).day == currentDate.get(Calendar.DAY_OF_MONTH)) {
                txtFriday.setText(txtFriday.getText().toString() + "Meeting Title: " + sch.get(i).title + "\nDate: " +
                        String.valueOf(sch.get(i).month) + "/" + String.valueOf(sch.get(i).day) + " (" + sch.get(i).dayofweek + ")\n"
                        + "Time: " + String.valueOf(sch.get(i).hour) + ":" + String.valueOf(sch.get(i).minute) + "\nClub: "
                        + sch.get(i).club + "\nAuthor: " + sch.get(i).username + "\nDetail: " + sch.get(i).detail + "\n\n");
                txtFriday.setVisibility(View.VISIBLE);
            }
        }
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        btnSaturday.setText((currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Saturday)");
        for(int i=0 ; i < sch.size() ; i++) {
            if(sch.get(i).day == currentDate.get(Calendar.DAY_OF_MONTH)) {
                txtSaturday.setText(txtSaturday.getText().toString() + "Meeting Title: " + sch.get(i).title + "\nDate: " +
                        String.valueOf(sch.get(i).month) + "/" + String.valueOf(sch.get(i).day) + " (" + sch.get(i).dayofweek + ")\n"
                        + "Time: " + String.valueOf(sch.get(i).hour) + ":" + String.valueOf(sch.get(i).minute) + "\nClub: "
                        + sch.get(i).club + "\nAuthor: " + sch.get(i).username + "\nDetail: " + sch.get(i).detail + "\n\n");
                txtSaturday.setVisibility(View.VISIBLE);
            }
        }
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        btnSunday.setText((currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Sunday)");
        for(int i=0 ; i < sch.size() ; i++) {
            if(sch.get(i).day == currentDate.get(Calendar.DAY_OF_MONTH)) {
                txtSunday.setText(txtSunday.getText().toString() + "Meeting Title: " + sch.get(i).title + "\nDate: " +
                        String.valueOf(sch.get(i).month) + "/" + String.valueOf(sch.get(i).day) + " (" + sch.get(i).dayofweek + ")\n"
                        + "Time: " + String.valueOf(sch.get(i).hour) + ":" + String.valueOf(sch.get(i).minute) + "\nClub: "
                        + sch.get(i).club + "\nAuthor: " + sch.get(i).username + "\nDetail: " + sch.get(i).detail + "\n\n");
                txtSunday.setVisibility(View.VISIBLE);
            }
        }
    }

}
