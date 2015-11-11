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
 * Created by andhieka on 11/11/15.
 */
public class SkillBank {
    private static final String SKILL_KEYWORDS_FILENAME = "skills.json";
    private ArrayList<String> skillKeywords = new ArrayList<>();
    private static SkillBank instance = null;

    private SkillBank() {
        loadKeywords();
    }

    public static SkillBank getInstance() {
        if (instance == null) {
            instance = new SkillBank();
        }
        return instance;
    }

    public List<String> getSkillKeywords() {
        return skillKeywords;
    }

    // Private methods

    private void loadKeywords() {
        try {
            InputStream inputStream = new FileInputStream(SKILL_KEYWORDS_FILENAME);
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONArray array = new JSONArray(tokener);
            for (int i = 0; i < array.length(); i++) {
                String keyword = array.getString(i);
                skillKeywords.add(keyword);
            }
        } catch (FileNotFoundException e) {
            Logger logger = Logger.getGlobal();
            logger.log(Level.SEVERE, String.format("Cannot find keyword list (%s)", SKILL_KEYWORDS_FILENAME), e);
        }
    }
    
}
