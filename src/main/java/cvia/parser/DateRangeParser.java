package cvia.parser;

import com.mdimension.jchronic.Chronic;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by andhieka on 10/11/15.
 */
public class DateRangeParser {
    private static final Pattern PATTERN_YEAR = Pattern.compile("\\b[12]{1}[0-9]{3}\\b");
    private static final List<String> MONTH_ABBREVIATED = Arrays.asList("jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec");
    private static final List<String> MONTH_FULL = Arrays.asList("january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december");

    private static final Pattern PATTERN_NOW = Pattern.compile("\\b(present|now|current)\\b", Pattern.CASE_INSENSITIVE);


    private class DateDescriptor {
        public int month;
        public int year;

        public LocalDate toLocalDate() {
            try {
                return LocalDate.of(year, Math.max(1, Math.min(12, month)), 1);
            } catch (DateTimeException e) {
                return null;
            }
        }
    }

    public boolean isDateToken(String token) {
        return isMonth(token) || isYear(token);
    }

    public DateRange parse(String sentence) {
        String[] tokens = sentence.split("\\s+");
        DateDescriptor startDateDescriptor = new DateDescriptor();
        DateDescriptor endDateDescriptor = new DateDescriptor();
        DateDescriptor targetDateDescriptor = startDateDescriptor;
        for (int i = 0; i < tokens.length; i++) {
            if (isMonth(tokens[i])) {
                targetDateDescriptor.month = readMonth(tokens[i]);
            } else if (isYear(tokens[i])) {
                targetDateDescriptor.year = readYear(tokens[i]);
            } else if (isPresent(tokens[i])) {
                LocalDate present = LocalDate.now();
                targetDateDescriptor.month = present.getMonthValue();
                targetDateDescriptor.year = present.getYear();
            } else if (isRangeMarker(tokens[i])) {
                targetDateDescriptor = endDateDescriptor;
            }
        }
        LocalDate startDate = startDateDescriptor.toLocalDate();
        LocalDate endDate = endDateDescriptor.toLocalDate();
        return new DateRange(startDate, endDate);
    }

    private boolean isPresent(String token) {
        if (PATTERN_NOW.matcher(token).matches()) return true;
        return false;
    }

    private int readMonth(String token) {
        token = token.toLowerCase();
        if (MONTH_ABBREVIATED.contains(token)) {
            return MONTH_ABBREVIATED.indexOf(token) + 1;
        } else if (MONTH_FULL.contains(token)) {
            return MONTH_FULL.indexOf(token) + 1;
        } else {
            return -1;
        }
    }

    private int readYear(String token) {
        return Integer.parseInt(token);
    }

    private boolean isMonth(String token) {
        if (MONTH_ABBREVIATED.contains(token.toLowerCase())) return true;
        if (MONTH_FULL.contains(token.toLowerCase())) return true;
        return false;
    }

    private boolean isYear(String token) {
        return PATTERN_YEAR.matcher(token).matches();
    }

    private boolean isRangeMarker(String token) {
        switch (token.toLowerCase()) {
            case "-":
            case "to":
            case "until":
                return true;
            default:
                return false;
        }
    }
}
