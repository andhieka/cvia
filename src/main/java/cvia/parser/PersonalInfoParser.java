package cvia.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Michael Limantara on 10/12/2015.
 */
public class PersonalInfoParser {
    private String PATTERN_PHONE_NUMBER = "(.*?)(\\+[1-9]{1}[0-9 ]{3,14})(.*)";
    private String PATTERN_EMAIL_ADDRESS = "(.*?)([_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+" +
            "(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))(.*)";
    private String PATTERN_ADDRESS = "(.*?)(\\d+[A-Za-z ]+ #[\\d-]* [A-Za-z ]+(\\d{6}(\\D|$))?)";

    String parseName() {
        return null;
    }

    // Find the contact number in the CV, and returns null if not found
    String parseContactNumber(String cv) {
        Pattern phoneNumberPattern = Pattern.compile(PATTERN_PHONE_NUMBER);
        Matcher matcher = phoneNumberPattern.matcher(cv);
        if (matcher.find()) {
            return matcher.group(2);
        }

        return null;
    }

    String parseEmail(String cv) {
        Pattern emailPattern = Pattern.compile(PATTERN_EMAIL_ADDRESS);
        Matcher matcher = emailPattern.matcher(cv);
        if (matcher.find()) {
            return matcher.group(2);
        }

        return null;
    }

    String parseAddress(String cv) {
        Pattern addressPattern = Pattern.compile(PATTERN_ADDRESS);
        Matcher matcher = addressPattern.matcher(cv);
        if (matcher.find()) {
            return matcher.group(2).trim();
        }
        return null;
    }
}
