package cvia.parser;

import cvia.model.CV;
import cvia.reader_writer.TextChunk;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
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
    }

    @Override
    public void reset() {
        cv = null;
        textChunks.clear();
    }

    private DateRange findDateRange(String line) {
        DateRange dateRange = dateRangeParser.parse(line);
        return dateRange;
    }

    private String findCompany(String line) {
        loadCompanyKeywords();
        String company = null;
        Integer lengthOfMatch = 0;

        for (String companyKeyword: companyKeywords) {
            if (line.contains(company) && lengthOfMatch < companyKeyword.length()) {
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
