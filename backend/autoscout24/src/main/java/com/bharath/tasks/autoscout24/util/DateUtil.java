package com.bharath.tasks.autoscout24.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter MONTH_YEAR_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM.yyyy")
            .withZone(ZoneId.from(ZoneOffset.UTC));

    private static final DateTimeFormatter DAY_MONTH_YEAR_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            .withZone(ZoneId.from(ZoneOffset.UTC));

    public static String formatDate(Instant contactDate) {
        return MONTH_YEAR_DATE_FORMATTER.format(contactDate);
    }

    public static LocalDate convertToDate(String dateStr) {
        return LocalDate.parse("01." + dateStr, DAY_MONTH_YEAR_DATE_FORMATTER);
    }
}
