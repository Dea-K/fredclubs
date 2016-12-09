package com.example.deakyu.fredclubs;

/**
 * Created by Deakyu on 12/8/2016.
 */

public class Schedule {
    public int id;
    public String username;
    public int user_id;
    public int year;
    public int month;
    public int day;
    public String dayofweek;
    public int minute;
    public int hour;
    public String title;
    public String detail;
    public String club;
    public Schedule(int id, String username, int user_id, int year, int month, int day, String dayofweek,
                    int minute, int hour, String title, String detail, String club) {
        this.id = id;
        this.username = username;
        this.user_id = user_id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayofweek = dayofweek;
        this.minute = minute;
        this.hour = hour;
        this.title = title;
        this.detail = detail;
        this.club = club;
    }
}
