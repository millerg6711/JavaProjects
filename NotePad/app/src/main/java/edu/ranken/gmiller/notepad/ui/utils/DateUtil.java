package edu.ranken.gmiller.notepad.ui.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class DateUtil {
    public static Calendar parseDate(String str) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("M/dd/yyyy", Locale.US);
        TimeZone tz = TimeZone.getDefault();
        format.setTimeZone(tz);

        Date parsedDate = format.parse(str);
        Calendar parsedCal = Calendar.getInstance(tz);
        parsedCal.setTime(parsedDate);
        return parsedCal;
    }

    public static String formatDate(Calendar cal) {
        SimpleDateFormat format = new SimpleDateFormat("M/dd/yyyy", Locale.US);
        format.setTimeZone(cal.getTimeZone());
        return format.format(cal.getTime());
    }
}
