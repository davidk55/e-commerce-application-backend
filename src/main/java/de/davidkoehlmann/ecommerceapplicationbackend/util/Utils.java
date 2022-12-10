package de.davidkoehlmann.ecommerceapplicationbackend.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Utils {
    private static final int SEC_IN_MILISEC = 1000;
    private static final int MIN_IN_SEC = 60;
    private static final int HOURS_IN_MIN = 60;
    private static final int DAYS_IN_HOURS = 24;

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static int daysToSeconds(int days) {
        return days * DAYS_IN_HOURS * HOURS_IN_MIN * MIN_IN_SEC;
    }

    public static Date getCurrentDatePlusMinutes(Integer minutes) {
        long currentTimeInMs = Timestamp.valueOf(LocalDateTime.now()).getTime();
        return new Date(currentTimeInMs + minutes * MIN_IN_SEC * SEC_IN_MILISEC);
    }

    public static Date getCurrentDatePlusDays(Integer days) {
        long currentTimeInMs = Timestamp.valueOf(LocalDateTime.now()).getTime();
        return new Date(currentTimeInMs + days * DAYS_IN_HOURS * HOURS_IN_MIN * MIN_IN_SEC * SEC_IN_MILISEC);
    }
}
