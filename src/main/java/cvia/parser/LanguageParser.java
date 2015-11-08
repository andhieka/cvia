package cvia.parser;

import cvia.model.CV;
import cvia.model.Language;
import cvia.reader_writer.TextChunk;
import cvia.utilities.StringUtilities;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Parser to detect human languages possessed by the candidate.
 * By default, language proficiency is Advanced.
 * Created by andhieka on 9/11/15.
 */
public class LanguageParser implements MiniParser {
    public static final String LANGUAGES_LIST_FILENAME = "languages.json";
    public static final String KEY_LANGUAGE_NAME = "name";
    public static final String KEY_LANGUAGE_NATIVE_NAME = "nativeName";

    private final ArrayList<String> languageKeywords = new ArrayList<String>();

    private CV cv;
    private ArrayList<TextChunk> textChunks = new ArrayList<TextChunk>();

    public LanguageParser() {
        loadKeywords();
    }

    @Override
    public void reset() {
        this.cv = null;
        this.textChunks.clear();
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
        assert (cv != null);
        TextChunk combinedTextChunk = null;
        for (TextChunk textChunk: textChunks) {
            if (combinedTextChunk == null) {
                combinedTextChunk = textChunk;
            } else {
                combinedTextChunk = combinedTextChunk.mergedWith(textChunk);
            }
        }
        String stringInput = combinedTextChunk.getText();
        List<Language> languages = detectedLanguagesInString(stringInput);
        cv.setLanguages(languages);
    }

    public List<Language> detectedLanguagesInString(String input) {
        ArrayList<Language> languages = new ArrayList<Language>();
        for (String keyword: languageKeywords) {
            if (StringUtilities.containsIgnoreCase(input, keyword)) {
                Language language = new Language(keyword);
                languages.add(language);
            }
        }
        return languages;
    }

    // Private methods
    private void loadKeywords() {
        try {
            InputStream inputStream = new FileInputStream(LANGUAGES_LIST_FILENAME);
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONObject languagesDict = new JSONObject(tokener);
            for (String languageCode: languagesDict.keySet()) {
                JSONObject languageInfo = languagesDict.getJSONObject(languageCode);
                String languageName = languageInfo.getString(KEY_LANGUAGE_NAME);
                String languageNativeName = languageInfo.getString(KEY_LANGUAGE_NATIVE_NAME);
                languageKeywords.add(languageName);
            }
        } catch (FileNotFoundException e) {
            Logger logger = Logger.getGlobal();
            logger.log(Level.SEVERE, String.format("Cannot find languages list (%s)", LANGUAGES_LIST_FILENAME), e);
        }
    }
}
