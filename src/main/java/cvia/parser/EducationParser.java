package cvia.parser;

import cvia.model.CV;
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
    private LocalDate startDate, endDate;
    private HashMap<String, Pattern> cachedPatterns = new HashMap<>();

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
            String major = findMajor(line);
            EducationLevel level = findLevel(line);
            String institution = findInstitution(line);
            DateRange dateRange = findDateRange(line);
        }
    }

    @Override
    public void reset() {
        cv = null;
        textChunks.clear();
    }

    public String findMajor(String line) {
        UniversityMajorBank majorBank = UniversityMajorBank.getInstance();
        List<String> universityMajors = majorBank.getUniversityMajors();
        for (String majorName: universityMajors) {
            if (matchesWholeWord(line, majorName)) {
                return majorName;
            }
        }
        return "";
    }

    public EducationLevel findLevel(String line) {
        return educationLevelParser.parse(line);
    }

    public String findInstitution(String line) {
        String normalizedSpacing = StringUtilities.removeRedundantSpaces(line);
        UniversityBank universityBank = UniversityBank.getInstance();
        for (String institutionName: universityBank.getUniversityNames()) {
            if (matchesWholeWord(normalizedSpacing, institutionName)) {
                return institutionName;
            }
        }
        for (String institutionAcronym: universityBank.getUniversityAcronyms()) {
            if (matchesWholeWord(normalizedSpacing, institutionAcronym)) {
                return institutionAcronym;
            }
        }
        return null;
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
