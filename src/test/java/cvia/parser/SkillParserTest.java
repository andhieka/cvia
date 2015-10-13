package cvia.parser;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by andhieka on 13/10/15.
 */
public class SkillParserTest {
    static SkillParser skillParser;

    @BeforeClass
    public static void setupOnce() {
        skillParser = new SkillParser();
    }

    @Test
    public void testReadSkillKeywords() {
        String input = "I can cook cookies and code Java and C++";
        List<String> skillKeywords = skillParser.findSkillKeywords(input);
        assertTrue(skillKeywords.contains("Java"));
        assertTrue(skillKeywords.contains("C++"));
        assertTrue(skillKeywords.contains("Cookies"));
    }
}
