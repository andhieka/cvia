package cvia.parser;

import cvia.model.Language;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 * Created by andhieka on 9/11/15.
 */
public class LanguageParserTest {
    LanguageParser languageParser;

    @Before
    public void setUp() throws Exception {
        languageParser = new LanguageParser();
    }

    @After
    public void tearDown() throws Exception {
        languageParser = null;
    }

    @Test
    public void testLanguageParsing() throws Exception {
        String testString = "Proficient in German, Indonesian and Dutch. Beginner in Chinese and Vietnamese.";
        List<Language> detectedLanguages = languageParser.detectedLanguagesInString(testString);
        Language german = new Language("German");
        Language indonesian = new Language("Indonesian");
        Language dutch = new Language("Dutch");
        Language chinese = new Language("Chinese");
        Language vietnamese = new Language("Vietnamese");
        assertEquals("There must be 5 languages detected", 5, detectedLanguages.size());
        assertTrue(detectedLanguages.contains(german));
        assertTrue(detectedLanguages.contains(dutch));
        assertTrue(detectedLanguages.contains(indonesian));
        assertTrue(detectedLanguages.contains(chinese));
        assertTrue(detectedLanguages.contains(vietnamese));
    }

}
