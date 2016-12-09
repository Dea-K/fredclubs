package com.example.deakyu.fredclubs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

    private LinearLayout llSunday;
    private LinearLayout llMonday;
    private LinearLayout llTuesday;
    private LinearLayout llWednesday;
    private LinearLayout llThursday;
    private LinearLayout llFriday;
    private LinearLayout llSaturday;

    private TextView txtUsername;
    final Context context_this = this;

    protected ViewGroup.LayoutParams tvParams;
    protected ViewGroup.MarginLayoutParams marginParams;

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

        // LinearLayouts
        llMonday = (LinearLayout) findViewById(R.id.ll_monday_detail);
        llTuesday = (LinearLayout) findViewById(R.id.ll_tuesday_detail);
        llWednesday = (LinearLayout) findViewById(R.id.ll_wednesday_detail);
        llThursday = (LinearLayout) findViewById(R.id.ll_thursday_detail);
        llFriday = (LinearLayout) findViewById(R.id.ll_friday_detail);
        llSaturday = (LinearLayout) findViewById(R.id.ll_saturday_detail);
        llSunday = (LinearLayout) findViewById(R.id.ll_sunday_detail);

        txtUsername = (TextView) findViewById(R.id.display_username);
        tvParams = txtMonday.getLayoutParams();
        marginParams = new ViewGroup.MarginLayoutParams(tvParams);
        marginParams.setMargins(0, 0, 0, 10);
    }

    public void setVisiblePropertyForButtons() {
        btnMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llMonday.isShown()) {
                    llMonday.setVisibility(View.GONE);
                } else {
                    llMonday.setVisibility(View.VISIBLE);
                }
            }
        });

        btnTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llTuesday.isShown()) {
                    llTuesday.setVisibility(View.GONE);
                } else {
                    llTuesday.setVisibility(View.VISIBLE);
                }
            }
        });

        btnWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llWednesday.isShown()) {
                    llWednesday.setVisibility(View.GONE);
                } else {
                    llWednesday.setVisibility(View.VISIBLE);
                }
            }
        });

        btnThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llThursday.isShown()) {
                    llThursday.setVisibility(View.GONE);
                } else {
                    llThursday.setVisibility(View.VISIBLE);
                }
            }
        });

        btnFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llFriday.isShown()) {
                    llFriday.setVisibility(View.GONE);
                } else {
                    llFriday.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llSaturday.isShown()) {
                    llSaturday.setVisibility(View.GONE);
                } else {
                    llSaturday.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llSunday.isShown()) {
                    llSunday.setVisibility(View.GONE);
                } else {
                    llSunday.setVisibility(View.VISIBLE);
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
        llMonday.removeAllViews();
        llTuesday.removeAllViews();
        llWednesday.removeAllViews();
        llThursday.removeAllViews();
        llFriday.removeAllViews();
        llSaturday.removeAllViews();
        llSunday.removeAllViews();
    }

    public void populateButtons(List<Schedule> sch, Calendar currentDate) {
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        btnMonday.setText((currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Monday)");
        for(int i=0 ; i < sch.size() ; i++) {
            if(sch.get(i).day == currentDate.get(Calendar.DAY_OF_MONTH)) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(marginParams);
                tv.setText("Meeting Title: \t" + sch.get(i).title + "\nDate: \t" +
                        String.valueOf(sch.get(i).month) + "/" + String.valueOf(sch.get(i).day) + " (" + sch.get(i).dayofweek + ")\n"
                        + "Time: \t" + String.valueOf(sch.get(i).hour) + ":" + String.valueOf(sch.get(i).minute) + "\nClub: \t"
                        + sch.get(i).club + "\nAuthor: \t" + sch.get(i).username + "\nDetail: \t" + sch.get(i).detail);
                tv.setVisibility(View.VISIBLE);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setBackgroundColor(getColor(R.color.colorPrimary));
                tv.setTextColor(getColor(R.color.white));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                llMonday.addView(tv);
                if(sch.get(i).user_id == _loggedUser.id) {
                    populateReviseDeleteButtons(sch.get(i).user_id, sch.get(i).detail, llMonday, currentDate);
                }
                llMonday.setVisibility(View.VISIBLE);
            }
        }
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        btnTuesday.setText((currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Tuesday)");
        for(int i=0 ; i < sch.size() ; i++) {
            if(sch.get(i).day == currentDate.get(Calendar.DAY_OF_MONTH)) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(marginParams);
                tv.setText("Meeting Title: \t" + sch.get(i).title + "\nDate: \t" +
                        String.valueOf(sch.get(i).month) + "/" + String.valueOf(sch.get(i).day) + " (" + sch.get(i).dayofweek + ")\n"
                        + "Time: \t" + String.valueOf(sch.get(i).hour) + ":" + String.valueOf(sch.get(i).minute) + "\nClub: \t"
                        + sch.get(i).club + "\nAuthor: \t" + sch.get(i).username + "\nDetail: \t" + sch.get(i).detail);
                tv.setVisibility(View.VISIBLE);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setBackgroundColor(getColor(R.color.colorPrimary));
                tv.setTextColor(getColor(R.color.white));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                llTuesday.addView(tv);
                if(sch.get(i).user_id == _loggedUser.id) {
                    populateReviseDeleteButtons(sch.get(i).user_id, sch.get(i).detail, llTuesday, currentDate);
                }
                llTuesday.setVisibility(View.VISIBLE);
            }

        }
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        btnWednesday.setText((currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Wednesday)");
        for(int i=0 ; i < sch.size() ; i++) {
            if(sch.get(i).day == currentDate.get(Calendar.DAY_OF_MONTH)) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(marginParams);
                tv.setText("Meeting Title: \t" + sch.get(i).title + "\nDate: \t" +
                        String.valueOf(sch.get(i).month) + "/" + String.valueOf(sch.get(i).day) + " (" + sch.get(i).dayofweek + ")\n"
                        + "Time: \t" + String.valueOf(sch.get(i).hour) + ":" + String.valueOf(sch.get(i).minute) + "\nClub: \t"
                        + sch.get(i).club + "\nAuthor: \t" + sch.get(i).username + "\nDetail: \t" + sch.get(i).detail);
                tv.setVisibility(View.VISIBLE);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setBackgroundColor(getColor(R.color.colorPrimary));
                tv.setTextColor(getColor(R.color.white));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                llWednesday.addView(tv);
                if(sch.get(i).user_id == _loggedUser.id) {
                    populateReviseDeleteButtons(sch.get(i).user_id, sch.get(i).detail, llWednesday, currentDate);
                }
                llWednesday.setVisibility(View.VISIBLE);
            }
        }
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        btnThursday.setText((currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Thursday)");
        for(int i=0 ; i < sch.size() ; i++) {
            if(sch.get(i).day == currentDate.get(Calendar.DAY_OF_MONTH)) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(marginParams);
                tv.setText("Meeting Title: \t" + sch.get(i).title + "\nDate: \t" +
                        String.valueOf(sch.get(i).month) + "/" + String.valueOf(sch.get(i).day) + " (" + sch.get(i).dayofweek + ")\n"
                        + "Time: \t" + String.valueOf(sch.get(i).hour) + ":" + String.valueOf(sch.get(i).minute) + "\nClub: \t"
                        + sch.get(i).club + "\nAuthor: \t" + sch.get(i).username + "\nDetail: \t" + sch.get(i).detail);
                tv.setVisibility(View.VISIBLE);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setBackgroundColor(getColor(R.color.colorPrimary));
                tv.setTextColor(getColor(R.color.white));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                llThursday.addView(tv);
                if(sch.get(i).user_id == _loggedUser.id) {
                    populateReviseDeleteButtons(sch.get(i).user_id, sch.get(i).detail, llThursday, currentDate);
                }
                llThursday.setVisibility(View.VISIBLE);
            }
        }
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        btnFriday.setText((currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Friday)");
        for(int i=0 ; i < sch.size() ; i++) {
            if(sch.get(i).day == currentDate.get(Calendar.DAY_OF_MONTH)) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(marginParams);
                tv.setText("Meeting Title: \t" + sch.get(i).title + "\nDate: \t" +
                        String.valueOf(sch.get(i).month) + "/" + String.valueOf(sch.get(i).day) + " (" + sch.get(i).dayofweek + ")\n"
                        + "Time: \t" + String.valueOf(sch.get(i).hour) + ":" + String.valueOf(sch.get(i).minute) + "\nClub: \t"
                        + sch.get(i).club + "\nAuthor: \t" + sch.get(i).username + "\nDetail: \t" + sch.get(i).detail);
                tv.setVisibility(View.VISIBLE);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setBackgroundColor(getColor(R.color.colorPrimary));
                tv.setTextColor(getColor(R.color.white));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                llFriday.addView(tv);
                if(sch.get(i).user_id == _loggedUser.id) {
                    populateReviseDeleteButtons(sch.get(i).user_id, sch.get(i).detail, llFriday, currentDate);
                }
                llFriday.setVisibility(View.VISIBLE);
            }
        }
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        btnSaturday.setText((currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Saturday)");
        for(int i=0 ; i < sch.size() ; i++) {
            if(sch.get(i).day == currentDate.get(Calendar.DAY_OF_MONTH)) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(marginParams);
                tv.setText("Meeting Title: \t" + sch.get(i).title + "\nDate: \t" +
                        String.valueOf(sch.get(i).month) + "/" + String.valueOf(sch.get(i).day) + " (" + sch.get(i).dayofweek + ")\n"
                        + "Time: \t" + String.valueOf(sch.get(i).hour) + ":" + String.valueOf(sch.get(i).minute) + "\nClub: \t"
                        + sch.get(i).club + "\nAuthor: \t" + sch.get(i).username + "\nDetail: \t" + sch.get(i).detail);
                tv.setVisibility(View.VISIBLE);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setBackgroundColor(getColor(R.color.colorPrimary));
                tv.setTextColor(getColor(R.color.white));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                llSaturday.addView(tv);
                if(sch.get(i).user_id == _loggedUser.id) {
                    populateReviseDeleteButtons(sch.get(i).user_id, sch.get(i).detail, llSaturday, currentDate);
                }
                llSaturday.setVisibility(View.VISIBLE);
            }
        }
        /////////////////////////////////////////////////////////////////////////////////
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        btnSunday.setText((currentDate.get(Calendar.MONTH)+1) + "/" + currentDate.get(Calendar.DAY_OF_MONTH)
                + " (Sunday)");
        for(int i=0 ; i < sch.size() ; i++) {
            if(sch.get(i).day == currentDate.get(Calendar.DAY_OF_MONTH)) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(marginParams);
                tv.setText("Meeting Title: \t" + sch.get(i).title + "\nDate: " +
                        String.valueOf(sch.get(i).month) + "/" + String.valueOf(sch.get(i).day) + " (" + sch.get(i).dayofweek + ")\n"
                        + "Time: \t" + String.valueOf(sch.get(i).hour) + ":" + String.valueOf(sch.get(i).minute) + "\nClub: \t"
                        + sch.get(i).club + "\nAuthor: \t" + sch.get(i).username + "\nDetail: \t" + sch.get(i).detail);
                tv.setVisibility(View.VISIBLE);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setBackgroundColor(getColor(R.color.colorPrimary));
                tv.setTextColor(getColor(R.color.white));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                llSunday.addView(tv);
                if(sch.get(i).user_id == _loggedUser.id) {
                    populateReviseDeleteButtons(sch.get(i).user_id, sch.get(i).detail, llSunday, currentDate);
                }
                llSunday.setVisibility(View.VISIBLE);
            }
        }
    }

    public void populateReviseDeleteButtons(final int userId, final String detail, LinearLayout tobepopulated,
                                            final Calendar currentDate) {
        LinearLayout.LayoutParams RDButtonsParam = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT,
                0.5f);
        LinearLayout.LayoutParams paddingParam = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT,
                0.25f);
        Button btnDelete = new Button(this);
        TextView pad = new TextView(this);
        TextView pad2 = new TextView(this);
        pad.setLayoutParams(paddingParam);
        pad2.setLayoutParams(paddingParam);
        btnDelete.setLayoutParams(RDButtonsParam);
        btnDelete.setText("Delete");
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context_this, "Delete Attemped", Toast.LENGTH_SHORT).show();
                db.deleteByUserIdAndDetail(userId, detail);
                // refresh the schedules
                List<Schedule> newsch = db.getSchedulesOfWeekByDate(currentDate, currentDate.get(Calendar.YEAR));
                removeFormerMeetings();
                populateButtons(newsch, currentDate);
            }
        });

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.addView(pad);
        ll.addView(btnDelete);
        ll.addView(pad2);
        tobepopulated.addView(ll);
    }
}
