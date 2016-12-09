package com.example.deakyu.fredclubs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Deakyu on 11/23/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static String[] _dayList = new String[7];


    public DatabaseHandler(Context context) {super(context, "FredClubs", null, 1);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        // populate static day array
        _dayList[0] = "Monday";
        _dayList[1] = "Tuesday";
        _dayList[2] = "Wednesday";
        _dayList[3] = "Thursday";
        _dayList[4] = "Friday";
        _dayList[5] = "Saturday";
        _dayList[6] = "Sunday";

        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "username TEXT, password TEXT, firstName TEXT, lastName TEXT, club TEXT, position TEXT, "
        + "academicYear TEXT)");

        db.execSQL("CREATE TABLE schedules (id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "username TEXT, user_id INTEGER, year INTEGER, month INTEGER, day INTEGER, "
        + "dayofweek TEXT, minute INTEGER, hour INTEGER, title TEXT, detail TEXT, club TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void createUser(String username, String password, String firstName, String lastName,
                           String club, String position, String academicYear) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("firstName", firstName);
        cv.put("lastName", lastName);
        cv.put("club", club);
        cv.put("position", position);
        cv.put("academicYear", academicYear);

        db.insert("users", null, cv);
        db.close();
    }

    public void createSchedule(String username, int userId, int year, int month, int day, String dayofweek,
                               int minute, int hour, String title, String detail, String club) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("user_id", userId);
        cv.put("year", year);
        cv.put("month", month);
        cv.put("day", day);
        cv.put("dayofweek", dayofweek);
        cv.put("minute", minute);
        cv.put("hour", hour);
        cv.put("title", title);
        cv.put("detail", detail);
        cv.put("club", club);

        db.insert("schedules", null, cv);
        db.close();
    }

    public User getUserByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = '"
        + name + "'", null);
        if(cursor.moveToFirst()) {
            User user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)
                    , cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            return user;
        } else {
            return null;
        }
    }

    public boolean isExistingUsername(String username) {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username FROM users", null);
        if(cursor.moveToFirst()) {
                do {
                    if (cursor.getString(0) == username) {
                        result = true;
                    }
                } while(cursor.moveToNext());
            }
        cursor.close();
        return result;
    }

    public List<Schedule> getSchedulesOfWeekByDate(Calendar cal, int year) {
        List<Schedule> scheduleList = new ArrayList<Schedule>();
        SQLiteDatabase db = this.getReadableDatabase();


        // Monday
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        int _day = cal.get(Calendar.DAY_OF_MONTH);
        int _month = cal.get(Calendar.MONTH)+1;
        int _year = year;
        Cursor cursor = db.rawQuery("SELECT * FROM schedules WHERE year=" + _year + " AND month=" + _month + " AND day=" + _day + ";", null);
        if(cursor.moveToFirst()) {
            do{
            Schedule sch = new Schedule(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),
                    cursor.getInt(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getString(10),
                    cursor.getString(11));
            scheduleList.add(sch);
            } while(cursor.moveToNext());
        }
        // Tuesday
        cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        _day = cal.get(Calendar.DAY_OF_MONTH);
        _month = cal.get(Calendar.MONTH)+1;
        cursor = db.rawQuery("SELECT * FROM schedules WHERE year=" + _year + " AND month=" + _month + " AND day=" + _day + ";", null);
        if(cursor.moveToFirst()) {
            do{
                Schedule sch = new Schedule(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),
                        cursor.getInt(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11));
                scheduleList.add(sch);
            } while(cursor.moveToNext());
        }
        // Wednesday
        cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        _day = cal.get(Calendar.DAY_OF_MONTH);
        _month = cal.get(Calendar.MONTH)+1;
        cursor = db.rawQuery("SELECT * FROM schedules WHERE year=" + _year + " AND month=" + _month + " AND day=" + _day + ";", null);
        if(cursor.moveToFirst()) {
            do{
                Schedule sch = new Schedule(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),
                        cursor.getInt(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11));
                scheduleList.add(sch);
            } while(cursor.moveToNext());
        }
        // Thursday
        cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        _day = cal.get(Calendar.DAY_OF_MONTH);
        _month = cal.get(Calendar.MONTH)+1;
        cursor = db.rawQuery("SELECT * FROM schedules WHERE year=" + _year + " AND month=" + _month + " AND day=" + _day + ";", null);
        if(cursor.moveToFirst()) {
            do{
                Schedule sch = new Schedule(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),
                        cursor.getInt(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11));
                scheduleList.add(sch);
            } while(cursor.moveToNext());
        }
        // Friday
        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        _day = cal.get(Calendar.DAY_OF_MONTH);
        _month = cal.get(Calendar.MONTH)+1;
        cursor = db.rawQuery("SELECT * FROM schedules WHERE year=" + _year + " AND month=" + _month + " AND day=" + _day + ";", null);
        if(cursor.moveToFirst()) {
            do{
                Schedule sch = new Schedule(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),
                        cursor.getInt(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11));
                scheduleList.add(sch);
            } while(cursor.moveToNext());
        }
        // Saturday
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        _day = cal.get(Calendar.DAY_OF_MONTH);
        _month = cal.get(Calendar.MONTH)+1;
        cursor = db.rawQuery("SELECT * FROM schedules WHERE year=" + _year + " AND month=" + _month + " AND day=" + _day + ";", null);
        if(cursor.moveToFirst()) {
            do{
                Schedule sch = new Schedule(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),
                        cursor.getInt(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11));
                scheduleList.add(sch);
            } while(cursor.moveToNext());
        }
        // Sunday
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        _day = cal.get(Calendar.DAY_OF_MONTH);
        _month = cal.get(Calendar.MONTH)+1;
        cursor = db.rawQuery("SELECT * FROM schedules WHERE year=" + _year + " AND month=" + _month + " AND day=" + _day + ";", null);
        if(cursor.moveToFirst()) {
            do{
                Schedule sch = new Schedule(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),
                        cursor.getInt(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11));
                scheduleList.add(sch);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return scheduleList;
    }

    public List<Schedule> getAllSchedules() {
        List<Schedule> scheduleList = new ArrayList<Schedule>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM schedules", null);
        if(cursor.moveToFirst()) {
            do{
                Schedule schedule = new Schedule(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),
                        cursor.getInt(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11));
                scheduleList.add(schedule);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return scheduleList;
    }
}
