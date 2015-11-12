package cvia.parser;

import cvia.model.CV;
import cvia.model.WorkExperience;
import cvia.reader_writer.TextChunk;
import cvia.reader_writer.TextLine;
import cvia.utilities.StringUtilities;
import cvia.utilities.TextChunkUtilities;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by andhieka on 9/11/15.
 */
public class WorkExperienceParser implements MiniParser {
    private static final String POSITION_KEYWORDS_FILENAME = "business_positions.json";
    private static final String COMPANY_KEYWORDS_FILENAME = "company.json";
    private CV cv;
    private ArrayList<TextChunk> textChunks = new ArrayList<TextChunk>();

    private DateRangeParser dateRangeParser = new DateRangeParser();
    private ArrayList<String> positionKeywords = new ArrayList<String>();
    private ArrayList<String> companyKeywords = new ArrayList<String>();

    private ArrayList<WorkExperience> workExperiences;
    private DateRange dateRange;
    private String position;
    private String company;
    private StringBuilder description = new StringBuilder();

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
        return;

//        List<TextLine> textLines = TextChunkUtilities.combineLines(textChunks);
//        for (TextLine textLine: textLines) {
//            String line = textLine.getText();
//            if (!StringUtilities.beginsWithBullet(line)) {
//                saveWorkExperience();
//            }
//            boolean hasMatch = false;
//            if (position == null) {
//                position = findPosition(line);
//                if (position != null) {
//                    line = line.replace(position, "");
//                    hasMatch = true;
//                }
//            }
//            if (company == null) {
//                company = findCompany(line);
//                if (company != null) {
//                    line = line.replace(company, "");
//                    hasMatch = true;
//                }
//            }
//            if (dateRange == null) {
//                dateRange = dateRangeParser.parse(line);
//                if (dateRange.getStartDate() != null || dateRange.getEndDate() != null) {
//                    ParseEvidence evidence = dateRangeParser.getEvidence();
//                    if (!line.trim().isEmpty()) {
//                        line = line.replace(evidence.getText(), "");
//                    }
//                    hasMatch = true;
//                }
//            }
//            if (!hasMatch) {
//                description.append(line).append('\n');
//            }
//        }
//        saveWorkExperience();
//
//        cv.setWorkExperienceList(workExperiences);
    }

    @Override
    public void reset() {
        cv = null;
        textChunks.clear();
        workExperiences = new ArrayList<>();
        dateRange = null;
        description.setLength(0);
        position = null;
        company = null;
    }

    // for testing purpose
    public CV parseString(String input) {
        String[] lines = input.split("\n");
        for (String line: lines) {
            if (!StringUtilities.beginsWithBullet(line)) {
                saveWorkExperience();
            }
            boolean hasMatch = false;
            if (position == null) {
                position = findPosition(line);
                if (position != null) {
                    line = line.replace(position, "");
                    hasMatch = true;
                }
            }
            if (company == null) {
                company = findCompany(line);
                if (company != null) {
                    line = line.replace(company, "");
                    hasMatch = true;
                }
            }
            if (dateRange == null) {
                dateRange = dateRangeParser.parse(line);
                if (dateRange.getStartDate() != null || dateRange.getEndDate() != null) {
                    ParseEvidence evidence = dateRangeParser.getEvidence();
                    if (!line.trim().isEmpty()) {
                        line = line.replace(evidence.getText(), "");
                    }
                    hasMatch = true;
                }
            }
            if (!hasMatch) {
                description.append(line).append('\n');
            }
        }
        saveWorkExperience();

        cv.setWorkExperienceList(workExperiences);
        return cv;
    }

    private void saveWorkExperience() {
        if (company != null || position != null || description.length() > 0) {
            WorkExperience workExperience = new WorkExperience();
            workExperience.setCompany(company == null ? "" : company);
            workExperience.setPosition(position == null ? "" : position);
            workExperience.setDescription(description.toString());
            if (dateRange != null) {
                workExperience.setStartDate(dateRange.getStartDate());
                workExperience.setEndDate(dateRange.getEndDate());
            }
            workExperiences.add(workExperience);
        }
        dateRange = null;
        description.setLength(0);
        position = null;
        company = null;
    }

    private String findCompany(String line) {
        loadCompanyKeywords();
        String company = null;
        Integer lengthOfMatch = 0;

        for (String companyKeyword: companyKeywords) {
            if (line.contains(companyKeyword) && lengthOfMatch < companyKeyword.length()) {
                company = companyKeyword;
                lengthOfMatch = companyKeyword.length();
            }
        }

        return company;
    }

    private String findPosition(String line) {
        loadPositionKeywords();
        String position = null;
        Integer lengthOfMatch = 0;

        for (String positionKeyword: positionKeywords) {
            if (line.contains(positionKeyword) && lengthOfMatch < positionKeyword.length()) {
                position = positionKeyword;
                lengthOfMatch = positionKeyword.length();
            }
        }
        return position;
    }

    private void loadPositionKeywords() {
        try {
            InputStream inputStream = new FileInputStream(POSITION_KEYWORDS_FILENAME);
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONArray array = new JSONArray(tokener);
            for (int i = 0; i < array.length(); i++) {
                String keyword = array.getString(i);
                positionKeywords.add(keyword);
            }
        } catch (FileNotFoundException e) {
            Logger logger = Logger.getGlobal();
            logger.log(Level.SEVERE, String.format("Cannot find keyword list (%s)", POSITION_KEYWORDS_FILENAME), e);
        }
    }

    private void loadCompanyKeywords() {
        try {
            InputStream inputStream = new FileInputStream(COMPANY_KEYWORDS_FILENAME);
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONArray array = new JSONArray(tokener);
            for (int i = 0; i < array.length(); i++) {
                String keyword = array.getString(i);
                companyKeywords.add(keyword);
            }
        } catch (FileNotFoundException e) {
            Logger logger = Logger.getGlobal();
            logger.log(Level.SEVERE, String.format("Cannot find keyword list (%s)", COMPANY_KEYWORDS_FILENAME), e);
        }
    }
}
