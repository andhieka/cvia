package cvia.parser;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by andhieka on 13/10/15.
 */
public class SkillParserTest {
    static SkillParser skillParser;

    @BeforeClass
    public static void setupOnce() {
        skillParser = new SkillParser();
    }

    @Before
    public void setup() {

    }

    @Test
    public void testReadSkillKeywords() {
        String input = "I can cook cookies and code Java and C++";
        System.out.println(skillParser.findSkillKeywords(input));
    }
}
