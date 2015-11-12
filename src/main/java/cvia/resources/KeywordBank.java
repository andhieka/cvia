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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andhieka on 12/11/15.
 */
public class KeywordBank {
    private String sourceFilename;
    private ArrayList<String> keywords = new ArrayList<>();

    public KeywordBank(String sourceFilename) {
        this.sourceFilename = sourceFilename;
        loadKeywords();
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public List<String> matchedKeywordsInString(String input) {
        ArrayList<String> results = new ArrayList<>();
        for (String keyword: keywords) {
            if (input.contains(keyword)) {
                results.add(keyword);
            }
        }
        return results;
    }

    public String longestMatchedKeywordInString(String input) {
        List<String> results = matchedKeywordsInString(input);
        String ans = null;
        for (String result: results) {
            if (ans == null || ans.length() < result.length()) {
                ans = result;
            }
        }
        return ans;
    }

    // Private methods

    private void loadKeywords() {
        try {
            InputStream inputStream = new FileInputStream(sourceFilename);
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONArray array = new JSONArray(tokener);
            for (int i = 0; i < array.length(); i++) {
                String keyword = array.getString(i);
                keywords.add(keyword);
            }
        } catch (FileNotFoundException e) {
            Logger logger = Logger.getGlobal();
            logger.log(Level.SEVERE, String.format("Cannot find keyword list (%s)", sourceFilename), e);
        }
    }

}
