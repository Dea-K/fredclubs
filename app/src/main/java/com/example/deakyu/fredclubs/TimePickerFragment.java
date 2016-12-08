package com.example.deakyu.fredclubs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import static com.example.deakyu.fredclubs.MeetingRegisterActivity._hourInput;
import static com.example.deakyu.fredclubs.MeetingRegisterActivity._minuteInput;

/**
 * Created by Deakyu on 12/7/2016.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        EditText et = (EditText) getActivity().findViewById(R.id.etMeetingTime);
        et.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));

        _minuteInput = minute;
        _hourInput = hourOfDay;
    }
}
