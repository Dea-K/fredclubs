package com.example.deakyu.fredclubs;

import org.junit.Test;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.deakyu.fredclubs.UserHelper.db;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        System.out.println("4");
    }

    @Test
    public void DateTesting() throws Exception {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date mondayOfWeek = cal.getTime();
        String currentDay = DateFormat.getDateTimeInstance().format(mondayOfWeek);
        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
        System.out.println(currentDay);

        cal.add(Calendar.DAY_OF_YEAR, -7);
        Date LastWeek = cal.getTime();
        System.out.println(DateFormat.getDateTimeInstance().format(LastWeek));
        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
        System.out.println(cal.get(Calendar.MONTH));
    }
}