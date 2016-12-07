package com.example.deakyu.fredclubs;

/**
 * Created by Deakyu on 11/23/2016.
 */

public class User {
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String academicYear;
    public String position;
    public User(String username, String password, String firstName, String lastName,
                String academicYear, String position) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.academicYear = academicYear;
        this.position = position;
    }
}
