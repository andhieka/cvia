package cvia.resources;

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
 * Created by andhieka on 12/11/15.
 */
public class KeywordBank {
    private String sourceFilename;
    private ArrayList<String> skillKeywords = new ArrayList<>();
    private static SkillBank instance = null;

    public KeywordBank(String sourceFilename) {
        this.sourceFilename = sourceFilename;
        loadKeywords();
    }

    public List<String> getSkillKeywords() {
        return skillKeywords;
    }

    // Private methods

    private void loadKeywords() {
        try {
            InputStream inputStream = new FileInputStream(sourceFilename);
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONArray array = new JSONArray(tokener);
            for (int i = 0; i < array.length(); i++) {
                String keyword = array.getString(i);
                skillKeywords.add(keyword);
            }
        } catch (FileNotFoundException e) {
            Logger logger = Logger.getGlobal();
            logger.log(Level.SEVERE, String.format("Cannot find keyword list (%s)", sourceFilename), e);
        }
    }

}
