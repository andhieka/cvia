package cvia.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Michael Limantara on 11/8/2015.
 */
public class DateUtilities {
    public static LocalDate dateFromString(String dateString, DateTimeFormatter dateTimeFormatter) {
        if (dateString.isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateString, dateTimeFormatter);
    }

    public static String getDateString(LocalDate date, DateTimeFormatter dateTimeFormatter) {
        if (date == null) {
            return "";
        }
        return date.format(dateTimeFormatter);
    }
}
