package com.example.deakyu.fredclubs;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deakyu on 11/23/2016.
 */

public class UserHelper {
    public static List<String> usernames = new ArrayList<String>();
    public static List<String> passwords = new ArrayList<String>();
    public static List<String> firstNames = new ArrayList<String>();
    public static List<String> lastNames = new ArrayList<String>();
    public static List<String> academicYears = new ArrayList<String>();
    public static List<String> positions = new ArrayList<String>();

    public static DatabaseHandler db;

    public static void openDB(Context context) {
        if(db == null) {
            db = new DatabaseHandler(context);
        }
    }

    public static void CreateUser(String username, String password, String confirm, String firstName,
                                  String lastName, String position, String academicYear) throws Exception{
        if(username.length() < 5) {
            throw new Exception("Username must be at least 5 characters long");
        } else if(password.length() <4) {
            throw new Exception("Password must be at least 5 characters long");
        } else if(password.equals(username)) {
            throw new Exception("Username and Password CANNOT be the same");
        } else if(!password.equals(confirm)) {
            throw new Exception("Password does not match the confirm");
        }
        db.createUser(username, password, firstName, lastName, position, academicYear);
    }


}
