
package cvia.parser;

import cvia.model.CV;
import cvia.model.Skill;
import cvia.reader_writer.TextChunk;
import cvia.resources.SkillBank;
import cvia.utilities.StringUtilities;
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

public class SkillParser implements MiniParser {
    private CV cv;
    private ArrayList<TextChunk> textChunks = new ArrayList<TextChunk>();

    private static String SKILL_KEYWORDS_FILENAME = "skills.json";

    private List<String> keywords = new ArrayList<String>();
    private ArrayList<Pattern> patterns = new ArrayList<Pattern>();

    public SkillParser() {
        loadKeywords();
    }

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
        TextChunk combinedTextChunks = null;
        for (TextChunk textChunk: textChunks) {
            if (combinedTextChunks == null) {
                combinedTextChunks = textChunk;
            } else {
                combinedTextChunks = combinedTextChunks.mergedWith(textChunk);
            }
        }
        String inputText = (combinedTextChunks == null) ? "" : combinedTextChunks.getText();
        List<Skill> skills = findSkills(inputText);
        cv.setSkills(skills);
    }

    @Override
    public void reset() {
        cv = null;
        textChunks.clear();
    }

    // private methods

    private void loadKeywords() {
        SkillBank skillBank = SkillBank.getInstance();
        keywords = skillBank.getSkillKeywords();
    }

    public List<String> findSkillKeywords(String input) {
        ArrayList<String> presentKeywords = new ArrayList<String>();
        for (int i = 0; i < keywords.size(); i++) {
            String keyword = keywords.get(i);
            if (StringUtilities.containsIgnoreCase(input, keyword)) {
                presentKeywords.add(keyword);
            }
        }
        return presentKeywords;
    }

    public List<Skill> findSkills(String input) {
        ArrayList<Skill> skills= new ArrayList<Skill>();
        for (int i = 0; i < keywords.size(); i++) {
            String keyword = keywords.get(i);
            if (StringUtilities.containsIgnoreCase(input, keyword)) {
                Skill skill = new Skill(keyword);
                skill.setProficiencyLevel(Skill.SkillProficiency.INTERMEDIATE);
                skills.add(skill);
            }
        }
        return skills;
    }

}

