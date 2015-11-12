package cvia.parser;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
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

    private ParseEvidence parseEvidence;

    private enum CharType {
        ALPHABET, DIGIT, OTHERS
    }

    private class DateDescriptor {
        public int month;
        public int year = -1;

        public LocalDate toLocalDate() {
            if (year == -1) return null;
            try {
                return LocalDate.of(year, Math.max(1, Math.min(12, month)), 1);
            } catch (DateTimeException e) {
                return null;
            }
        }
    }

    public boolean isDateToken(String token) {
        return isMonth(token) || isYear(token) || isRangeMarker(token) || isPresent(token);
    }

    public DateRange parse(String line) {
        parseEvidence = new ParseEvidence();
        String sentence = getRelevantPart(line);
        parseEvidence.setText(sentence);

        String[] tokens = split(sentence);
        if (tokens.length == 0) return null;

        DateDescriptor startDateDescriptor = new DateDescriptor();
        DateDescriptor endDateDescriptor = new DateDescriptor();
        DateDescriptor targetDateDescriptor = startDateDescriptor;
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].trim().isEmpty()) {
                // do nothing
            } else if (isMonth(tokens[i])) {
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
        if (startDateDescriptor.month != 0 && endDateDescriptor.month != 0) {
            if (startDateDescriptor.year == -1) {
                if (endDateDescriptor.year == -1) {
                    startDateDescriptor.year = LocalDate.now().getYear();
                    endDateDescriptor.year = LocalDate.now().getYear();
                } else {
                    startDateDescriptor.year = endDateDescriptor.year;
                }
            }
        }

        LocalDate startDate = startDateDescriptor.toLocalDate();
        LocalDate endDate = endDateDescriptor.toLocalDate();
        return new DateRange(startDate, endDate);
    }

    private String getRelevantPart(String line) {
        String[] tokens = split(line);
        int startIdx = -1, endIdx = -1;
        for (int i = 0; i < tokens.length; i++) {
            if (isDateToken(tokens[i])) {
                if (startIdx == -1) {
                    startIdx = i;
                    endIdx = i;
                } else {
                    endIdx = i;
                }
            }
        }
        if (startIdx == -1) return "";
        StringBuilder timeDescription = new StringBuilder();
        for (int i = startIdx; i <= endIdx; i++) {
            timeDescription.append(tokens[i]).append(' ');
        }
        return timeDescription.toString();
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
            case "–":
            case "to":
            case "until":
                return true;
            default:
                return false;
        }
    }

    private String[] split(String input) {
        if (input.isEmpty()) return new String[0];

        ArrayList<String> result = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        CharType prevCharType = getCharType(input.charAt(0));
        currentToken.append(input.charAt(0));

        for (int i = 1; i < input.length(); i++) {
            char c = input.charAt(i);
            CharType charType = getCharType(c);
            if (charType != prevCharType) {
                result.add(currentToken.toString().trim());
                currentToken.setLength(0);
            }
            currentToken.append(c);
            prevCharType = charType;
        }

        if (currentToken.length() > 0) {
            result.add(currentToken.toString().trim());
        }

        return result.toArray(new String[result.size()]);
    }

    private CharType getCharType(char x) {
        if (Character.isDigit(x)) {
            return CharType.DIGIT;
        } else if (Character.isAlphabetic(x)) {
            return CharType.ALPHABET;
        } else {
            return CharType.OTHERS;
        }
    }

    public ParseEvidence getEvidence() {
        return null;
    }
}
