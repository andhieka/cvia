package cvia.parser;

import cvia.model.CV;
import cvia.model.EducationInfo.EducationLevel;
import cvia.reader_writer.TextChunk;
import cvia.reader_writer.TextLine;
import cvia.utilities.StringUtilities;
import cvia.utilities.TextChunkUtilities;

import java.time.LocalDate;
import java.util.ArrayList;

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
        ArrayList<TextLine> textLines = TextChunkUtilities.combineLines(textChunks);
        for (TextLine textLine: textLines) {
            String line = textLine.getText();
            String major = findMajor(line);
            EducationLevel level = findLevel(line);
            String institution = findInstitution(line);
            LocalDate startDate = findStartDate(line);
            LocalDate endDate = findEndDate(line);
        }
    }

    @Override
    public void reset() {
        cv = null;
        textChunks.clear();
    }

    private String findMajor(String line) {
        return "";
    }

    private EducationLevel findLevel(String line) {
        return null;
    }

    private String findInstitution(String line) {
        String normalizedSpacing = StringUtilities.removeRedundantSpaces(line);
        UniversityBank universityBank = UniversityBank.getInstance();
        for (String institutionName: universityBank.getUniversityNames()) {

        }
        for (String institutionAcronym: universityBank.getUniversityAcronyms()) {

        }
        return "";
    }

    private LocalDate findStartDate(String line) {
        return null;
    }

    private LocalDate findEndDate(String line) {
        return null;
    }
}
