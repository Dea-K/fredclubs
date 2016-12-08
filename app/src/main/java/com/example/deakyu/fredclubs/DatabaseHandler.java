package com.example.deakyu.fredclubs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
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
        _dayList[0] = "monday";
        _dayList[1] = "tuesday";
        _dayList[2] = "wednesday";
        _dayList[3] = "thursday";
        _dayList[4] = "friday";
        _dayList[5] = "saturday";
        _dayList[6] = "sunday";

        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "username TEXT, password TEXT, firstName TEXT, lastName TEXT, club TEXT, position TEXT, "
        + "academicYear TEXT)");

        db.execSQL("CREATE TABLE schedules (id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "author TEXT, date TEXT, time TEXT, title TEXT, club TEXT, user_id INTEGER, "
        + "detail TEXT)");
    }
    // '2007-01-01 10:00:00' datetime format

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
}
