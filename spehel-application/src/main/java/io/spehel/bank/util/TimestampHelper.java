package io.spehel.bank.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class TimestampHelper {

    public static LocalDate convertDate(Long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp * 1000L);
        Date time = calendar.getTime();
        return time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
