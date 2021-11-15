package io.spehel.bank.util;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

public class TimestampHelper {

    public static long createTimestamp(int yearFrom, int monthFrom, int dayFrom) {
        Date dateFrom = new Date();
        Calendar calendar = new Calendar.Builder().setDate(yearFrom, monthFrom, dayFrom).build();

        System.out.println(calendar.getTime());
        return calendar.getTimeInMillis();
    }

    public static void createTimestamp(int yearFrom, int monthFrom, int dayFrom, int today) {

    }

}
