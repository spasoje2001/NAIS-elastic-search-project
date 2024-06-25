package com.veljko121.backend.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final String DATE_FORMAT = "dd.MM.yyyy."; // Define the date format

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    public static Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            // Log the exception, handle it, or throw an unchecked exception
            throw new IllegalArgumentException("Invalid date format. Please use this pattern: " + DATE_FORMAT, e);
        }
    }
}
