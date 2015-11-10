package cvia.parser;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by andhieka on 10/11/15.
 */
public class UniversityMajorParser {
    private static final String UNIVERSITY_MAJORS_FILENAME = "university_majors.json";
    private ArrayList<String> keywords = new ArrayList<>();

    public UniversityMajorParser() {
        loadKeywords();
    }

    public List<String> universityMajorsInLine(String textLine) {
        return null;
    }

    // Private methods

    private void loadKeywords() {
        try {
            InputStream inputStream = new FileInputStream(UNIVERSITY_MAJORS_FILENAME);
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONArray array = new JSONArray(tokener);
            for (int i = 0; i < array.length(); i++) {
                String keyword = array.getString(i);
                keywords.add(keyword);
            }
        } catch (FileNotFoundException e) {
            Logger logger = Logger.getGlobal();
            logger.log(Level.SEVERE, String.format("Cannot find keyword list (%s)", UNIVERSITY_MAJORS_FILENAME), e);
        }
    }
}
