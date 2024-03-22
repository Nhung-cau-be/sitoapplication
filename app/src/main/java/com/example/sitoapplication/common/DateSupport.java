package com.example.sitoapplication.common;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateSupport {
    private static DateSupport single_instance = null;

    public long getRemainDays(Date beforeDate, Date afterDate) {
        LocalDateTime beforeLocalDateTime = beforeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime afterLocalDateTime = afterDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        return  Duration.between(beforeLocalDateTime, afterLocalDateTime).toDays();
    }

    public static synchronized DateSupport getInstance()
    {
        if (single_instance == null)
            single_instance = new DateSupport();

        return single_instance;
    }
}
