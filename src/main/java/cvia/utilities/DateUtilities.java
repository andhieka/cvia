package cvia.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Michael Limantara on 11/8/2015.
 */
public class DateUtilities {
    public static LocalDate dateFromString(String dateString, DateTimeFormatter dateTimeFormatter) {
        return LocalDate.parse(dateString, dateTimeFormatter);
    }

    public static String getDateString(LocalDate date, DateTimeFormatter dateTimeFormatter) {
        return date.format(dateTimeFormatter);
    }
}
