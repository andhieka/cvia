package cvia.parser;

import cvia.model.CV;
import cvia.model.EducationInfo;
import cvia.model.EducationInfo.EducationLevel;
import cvia.reader_writer.TextChunk;
import cvia.reader_writer.TextLine;
import cvia.utilities.StringUtilities;
import cvia.utilities.TextChunkUtilities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by andhieka on 9/11/15.
 */
public class EducationParser implements MiniParser {
    private CV cv;
    private ArrayList<TextChunk> textChunks = new ArrayList<TextChunk>();
    private String major;
    private EducationLevel educationLevel;
    private String institutionName;
    private DateRange dateRange;
    private HashMap<String, Pattern> cachedPatterns = new HashMap<>();
    private ArrayList<EducationInfo> educationInfos;

    // smaller parsers
    private DateRangeParser dateRangeParser = new DateRangeParser();
    private EducationLevelParser educationLevelParser = new EducationLevelParser();

    @Override
    public void setCV(CV cv) {
        this.cv = cv;
    }

    @Override
    public void appendTextChunk(TextChunk textChunk) {
        textChunks.add(textChunk);
    }

    @Override
    public void parseAndSave() {
        assert(cv != null);

        ArrayList<TextLine> textLines = TextChunkUtilities.combineLines(textChunks);
        for (TextLine textLine: textLines) {
            String line = textLine.getText();
            if (!StringUtilities.beginsWithBullet(line)) {
                saveEducationInfo();
            }
            if (major == null) {
                major = findMajor(line);
            }
            if (educationLevel == null) {
                educationLevel = findLevel(line);
            }
            if (institutionName == null) {
                institutionName = findInstitution(line);
            }
            if (dateRange == null) {
                dateRange = findDateRange(line);
            }
        }
        saveEducationInfo();

        cv.setEducationInfoList(educationInfos);
    }

    private void saveEducationInfo() {
        if (major != null || educationLevel != null ||
                institutionName != null || dateRange != null) {
            EducationInfo educationInfo = new EducationInfo();
            educationInfo.setMajor(major == null ? "" : major);
            educationInfo.setEducationLevel(educationLevel);
            educationInfo.setStartDate(dateRange.getStartDate());
            educationInfo.setEndDate(dateRange.getEndDate());
            educationInfo.setInstitutionName(institutionName == null ? "" : institutionName);
            educationInfos.add(educationInfo);
        }
        major = null;
        educationLevel = null;
        institutionName = null;
        dateRange = null;
    }

    @Override
    public void reset() {
        cv = null;
        textChunks.clear();
        educationInfos = new ArrayList<>();
        major = null;
        educationLevel = null;
        institutionName = null;
        dateRange = null;
    }

    public String findMajor(String line) {
        UniversityMajorBank majorBank = UniversityMajorBank.getInstance();
        List<String> universityMajors = majorBank.getUniversityMajors();
        String result = "";
        for (String majorName: universityMajors) {
            if (matchesWholeWord(line, majorName)) {
                if (result.length() < majorName.length()) {
                    result = majorName;
                }
            }
        }
        return result;
    }

    public EducationLevel findLevel(String line) {
        return educationLevelParser.parse(line);
    }

    public String findInstitution(String line) {
        String normalizedSpacing = StringUtilities.removeRedundantSpaces(line);
        UniversityBank universityBank = UniversityBank.getInstance();
        String result = "";
        for (String institutionName: universityBank.getUniversityNames()) {
            if (matchesWholeWord(normalizedSpacing, institutionName)) {
                if (institutionName.length() > result.length()) {
                    result = institutionName;
                }
            }
        }
        if (!result.equals("")) return result;
        for (String institutionAcronym: universityBank.getUniversityAcronyms()) {
            if (matchesWholeWord(normalizedSpacing, institutionAcronym)) {
                if (institutionAcronym.length() > result.length()) {
                    result = institutionAcronym;
                }
            }
        }
        return result.equals("") ? null : result;
    }

    private DateRange findDateRange(String line) {
        DateRange dateRange = dateRangeParser.parse(line);
        return dateRange;
    }

    private boolean matchesWholeWord(String line, String keyword) {
        keyword = keyword.toLowerCase();
        if (!cachedPatterns.containsKey(keyword)) {
            cachedPatterns.put(keyword, Pattern.compile(String.format("(.*?)\\b%s\\b(.*)", keyword), Pattern.CASE_INSENSITIVE));
        }
        Pattern pattern = cachedPatterns.get(keyword);
        return pattern.matcher(line).matches();
    }
}
