package cvia.parser;

import cvia.parser.PDFCVParser.ParseMode;
import cvia.utilities.StringUtilities;

import java.util.regex.Pattern;

/**
 * Created by andhieka on 8/11/15.
 */
public class ParseModeTrigger {
    private static final String PERSONAL_INFO_TRIGGER_STRING = "(.*?)(particulars|personal)(.*)";
    private static final String EDUCATION_TRIGGER_STRING = "(.*?)(education|award)(.*)";
    private static final String WORK_EXPERIENCE_TRIGGER_STRING = "(.*?)(work|experience)(.*)";
    private static final String LANGUAGE_TRIGGER_STRING = "^(?!.*(program{1,2}ing)).*language(.*)";
    private static final String SKILL_TRIGGER_STRING = "(.*?)(skill|expertise|programming)(.*)";
    private static final String PUBLICATION_TRIGGER_STRING = "(.*?)(publication|journal|conference)(.*)";
    private static final String UNKNOWN_TRIGGER_STRING = "";

    private static final Pattern PERSONAL_INFO_TRIGGER_PATTERN = Pattern.compile(PERSONAL_INFO_TRIGGER_STRING, Pattern.CASE_INSENSITIVE);
    private static final Pattern EDUCATION_TRIGGER_PATTERN = Pattern.compile(EDUCATION_TRIGGER_STRING, Pattern.CASE_INSENSITIVE);
    private static final Pattern WORK_EXPERIENCE_TRIGGER_PATTERN = Pattern.compile(WORK_EXPERIENCE_TRIGGER_STRING, Pattern.CASE_INSENSITIVE);
    private static final Pattern LANGUAGE_TRIGGER_PATTERN = Pattern.compile(LANGUAGE_TRIGGER_STRING, Pattern.CASE_INSENSITIVE);
    private static final Pattern SKILL_TRIGGER_PATTERN = Pattern.compile(SKILL_TRIGGER_STRING, Pattern.CASE_INSENSITIVE);
    private static final Pattern PUBLICATION_TRIGGER_PATTERN = Pattern.compile(PUBLICATION_TRIGGER_STRING, Pattern.CASE_INSENSITIVE);
    private static final Pattern UNKNOWN_TRIGGER_PATTERN = Pattern.compile(UNKNOWN_TRIGGER_STRING, Pattern.CASE_INSENSITIVE);


    ParseMode triggeredParseMode(String textLine) {
        // long sentences can't trigger parse mode
        if (StringUtilities.numberOfWords(textLine) > 5) return null;

        if (EDUCATION_TRIGGER_PATTERN.matcher(textLine).matches()) {
            return ParseMode.EDUCATION;
        }
        if (WORK_EXPERIENCE_TRIGGER_PATTERN.matcher(textLine).matches()) {
            return ParseMode.WORK_EXPERIENCE;
        }
        if (LANGUAGE_TRIGGER_PATTERN.matcher(textLine).matches()) {
            return ParseMode.LANGUAGE;
        }
        if (SKILL_TRIGGER_PATTERN.matcher(textLine).matches()) {
            return ParseMode.SKILL;
        }
        if (PUBLICATION_TRIGGER_PATTERN.matcher(textLine).matches()) {
            return ParseMode.PUBLICATION;
        }
        if (PERSONAL_INFO_TRIGGER_PATTERN.matcher(textLine).matches()) {
            return ParseMode.PERSONAL_INFO;
        }
        return null;
    }

}
