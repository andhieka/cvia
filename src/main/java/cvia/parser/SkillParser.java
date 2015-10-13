
package cvia.parser;

import cvia.model.Skill;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andhieka on 13/10/15.
 */

public class SkillParser {

    private static String SKILL_KEYWORDS_FILENAME = "skills.json";

    private ArrayList<String> keywords = new ArrayList<String>();
    private ArrayList<Pattern> patterns = new ArrayList<Pattern>();

    public SkillParser() {
        loadKeywords();
    }

    private void loadKeywords() {
        try {
            InputStream inputStream = new FileInputStream(SKILL_KEYWORDS_FILENAME);
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONArray array = new JSONArray(tokener);
            for (int i = 0; i < array.length(); i++) {
                String keyword = array.getString(i);
                keywords.add(keyword);
            }
        } catch (FileNotFoundException e) {
            Logger logger = Logger.getGlobal();
            logger.log(Level.SEVERE, String.format("Cannot find keyword list (%s)", SKILL_KEYWORDS_FILENAME), e);
        }
    }

    public List<String> findSkillKeywords(String input) {
        ArrayList<String> presentKeywords = new ArrayList<String>();
        for (int i = 0; i < keywords.size(); i++) {
            String keyword = keywords.get(i);
            if (containsIgnoreCase(input, keyword)) {
                presentKeywords.add(keyword);
            }
        }
        return presentKeywords;
    }

    public List<Skill> findSkills(String input) {
        ArrayList<Skill> skills= new ArrayList<Skill>();
        for (int i = 0; i < keywords.size(); i++) {
            String keyword = keywords.get(i);
            if (containsIgnoreCase(input, keyword)) {
                Skill skill = new Skill(keyword);
                skills.add(skill);
            }
        }
        return skills;
    }

    // Case Insensitive String contains method
    // Modified from http://stackoverflow.com/questions/86780/is-the-contains-method-in-java-lang-string-case-sensitive
    private boolean containsIgnoreCase(final String input, final String keyword) {
        if (keyword.length() == 0) {
            return true;
        }
        final char firstLo = Character.toLowerCase(keyword.charAt(0));
        final char firstUp = Character.toUpperCase(keyword.charAt(0));

        for (int i = input.length() - keyword.length(); i >= 0; i--) {
            final char ch = input.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (input.regionMatches(true, i, keyword, 0, keyword.length()))
                return true;
        }

        return false;
    }

}

