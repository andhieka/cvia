package cvia.parser;

import cvia.model.EducationInfo.EducationLevel;

import java.util.regex.Pattern;

/**
 * Created by andhieka on 10/11/15.
 */
public class EducationLevelParser {
    private static final Pattern PATTERN_PHD = Pattern.compile("(.*?)(\\b(ph\\.?d|dr|d\\.?phil)\\b)(.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_GRADUATE = Pattern.compile("(.*?)(\\b(M\\.?[A-Z]+[a-z]*|Master[s]*)\\b)(.*)");
    private static final Pattern PATTERN_UNDERGRADUATE = Pattern.compile("(.*?)(\\b(Bachelor|B\\.?[A-Z]+[a-z]*)\\b)(.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_DIPLOMA = Pattern.compile("(.*?)(\\b(Diploma|D[1-3])\\b)(.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_HIGHSCHOOL = Pattern.compile("(.*?)(\\b(high\\s*school|junior\\s+college)\\b)(.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_SECONDARY = Pattern.compile("(.*?)(\\b(secondary|junior\\s+high)\\b)(.*)", Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_PRIMARY = Pattern.compile("(.*?)(\\b(primary|sekolah dasar|SD)\\b)(.*)", Pattern.CASE_INSENSITIVE);

    public EducationLevel parse(String line) {
        if (PATTERN_SECONDARY.matcher(line).matches()) {
            return EducationLevel.SECONDARY;
        } else if (PATTERN_PHD.matcher(line).matches()) {
            return EducationLevel.PHD;
        } else if (PATTERN_GRADUATE.matcher(line).matches()) {
            return EducationLevel.GRADUATE;
        } else if (PATTERN_UNDERGRADUATE.matcher(line).matches()) {
            return EducationLevel.UNDERGRADUATE;
        } else if (PATTERN_PRIMARY.matcher(line).matches()) {
            return EducationLevel.PRIMARY;
        } else if (PATTERN_DIPLOMA.matcher(line).matches()) {
            return EducationLevel.DIPLOMA;
        } else if (PATTERN_HIGHSCHOOL.matcher(line).matches()) {
            return EducationLevel.HIGHSCHOOL;
        }
        return null;
    }
}
