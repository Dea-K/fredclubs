package com.example.deakyu.fredclubs;

/**
 * Created by Deakyu on 11/23/2016.
 */

public class User {
    public int id;
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String club;
    public String academicYear;
    public String position;
    public User(int id, String username, String password, String firstName, String lastName,
                String club, String position, String academicYear) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.club = club;
        this.academicYear = academicYear;
        this.position = position;
    }
}
