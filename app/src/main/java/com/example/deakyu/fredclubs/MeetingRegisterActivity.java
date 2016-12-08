package com.example.deakyu.fredclubs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.R.attr.onClick;
import static android.R.attr.start;
import static com.example.deakyu.fredclubs.User._loggedUser;
import static com.example.deakyu.fredclubs.UserHelper.db;

public class MeetingRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText meetingDate;
    private EditText meetingTime;
    private DatePickerDialog meetingDatePicker;
    public static SimpleDateFormat _dateFomatter;
    public static SimpleDateFormat _dayOfWeekFormatter;
    private EditText meetingDetail;
    private EditText meetingClub;
    private EditText meetingTitle;
    private TextView meetingUsername;

    // Values to be used for DB interactions
    protected int yearInput;
    protected int dayInput;
    protected int monthInput;
    public static int _minuteInput;
    public static int _hourInput;
    protected String dayOfWeekInput;
    protected String detailInput;
    protected String titleInput;
    protected String clubnameInput;
    protected User registeringUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _dateFomatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        _dayOfWeekFormatter = new SimpleDateFormat("EEEE", Locale.US);

        meetingDate = (EditText) findViewById(R.id.etMeetingDate);
        meetingDate.setInputType(InputType.TYPE_NULL);
        meetingDate.requestFocus();

        meetingTime = (EditText) findViewById(R.id.etMeetingTime);
        meetingTime.setOnClickListener(this);

        meetingDetail = (EditText) findViewById(R.id.register_detail);
        meetingClub = (EditText) findViewById(R.id.register_meetingClub);
        meetingTitle = (EditText) findViewById(R.id.register_meetingTitle);
        meetingUsername = (TextView) findViewById(R.id.register_author);
        meetingUsername.setText(_loggedUser.username);

        setDateTimeField();

        // Not Using.. Saving for later just in case
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void setDateTimeField() {
        meetingDate.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        meetingDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                // set day of week
                Date dayRegistered = new Date(year, month, dayOfMonth);
                // Day of week as String with the first letter capitalized
                String dayOfWeek = _dayOfWeekFormatter.format(dayRegistered);


                // Store them in protected variables
                yearInput = year;
                monthInput = month;
                dayInput = dayOfMonth;
                dayOfWeekInput = dayOfWeek;


                meetingDate.setText(_dateFomatter.format(newDate.getTime()));
                meetingDate.setText(meetingDate.getText() + " (" + dayOfWeek + ")");
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
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

        // Sign Out User
        if(id == R.id.action_signout) {
            Intent intent = new Intent(this, MainActivity.class);
            _loggedUser = null;
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view == meetingDate) {
         meetingDatePicker.show();
        }
        if(view == meetingTime) {
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getFragmentManager(), "TimePicker");
        }
    }

    public void cancelRegister(View view) {
        Intent intent = new Intent(this, DisplayScheduleActivity.class);
        startActivity(intent);
    }

    public void registerMeeting(View view) {
        detailInput = meetingDetail.getText().toString();
        titleInput = meetingTitle.getText().toString();
        clubnameInput = meetingClub.getText().toString();
        registeringUser = _loggedUser;

        // save them into db
        db.createSchedule(registeringUser.username, registeringUser.id, yearInput, monthInput, dayInput, dayOfWeekInput,
                            _minuteInput, _hourInput, titleInput, detailInput, clubnameInput);


    }
}
