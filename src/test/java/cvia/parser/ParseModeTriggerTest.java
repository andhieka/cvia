package cvia.parser;

import cvia.parser.PDFCVParser.ParseMode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by andhieka on 9/11/15.
 */
public class ParseModeTriggerTest {
    private ParseModeTrigger parseModeTrigger;

    @Before
    public void setUp() throws Exception {
        parseModeTrigger = new ParseModeTrigger();
    }

    @After
    public void tearDown() throws Exception {
        parseModeTrigger = null;
    }

    @Test
    public void testCaseInsensitive() throws Exception {
        assertEquals("Capitalized Case", ParseMode.EDUCATION, parseModeTrigger.triggeredParseMode("Education and Awards"));
        assertEquals("ALL CAPS", ParseMode.EDUCATION, parseModeTrigger.triggeredParseMode("EDUCATION AND AWARDS"));
        assertEquals("all small case", ParseMode.EDUCATION, parseModeTrigger.triggeredParseMode("education and awards"));
    }

    @Test
    public void testPersonalInfoTrigger() throws Exception {
        assertEquals(ParseMode.PERSONAL_INFO, parseModeTrigger.triggeredParseMode("Personal Details"));
        assertEquals(ParseMode.PERSONAL_INFO, parseModeTrigger.triggeredParseMode("Particulars"));
    }

    @Test
    public void testWorkExperienceTrigger() throws Exception {
        assertEquals(ParseMode.WORK_EXPERIENCE, parseModeTrigger.triggeredParseMode("Work"));
        assertEquals(ParseMode.WORK_EXPERIENCE, parseModeTrigger.triggeredParseMode("Working Experiences"));
        assertEquals(ParseMode.WORK_EXPERIENCE, parseModeTrigger.triggeredParseMode("Working"));
        assertEquals(ParseMode.WORK_EXPERIENCE, parseModeTrigger.triggeredParseMode("Experience"));
    }

    @Test
    public void testLanguageTrigger() throws Exception {
        assertEquals(ParseMode.LANGUAGE, parseModeTrigger.triggeredParseMode("Languages"));
        assertEquals(ParseMode.LANGUAGE, parseModeTrigger.triggeredParseMode("Language"));
        assertNotEquals(ParseMode.LANGUAGE, parseModeTrigger.triggeredParseMode("Programming Languages"));
    }

    @Test
    public void testSkillTrigger() throws Exception {
        assertEquals(ParseMode.SKILL, parseModeTrigger.triggeredParseMode("Skillset"));
        assertEquals(ParseMode.SKILL, parseModeTrigger.triggeredParseMode("Programming Languages"));
        assertEquals(ParseMode.SKILL, parseModeTrigger.triggeredParseMode("Technical Skills"));
        assertEquals(ParseMode.SKILL, parseModeTrigger.triggeredParseMode("Expertise"));
    }

    @Test
    public void testPublicationTrigger() throws Exception {
        assertEquals(ParseMode.PUBLICATION, parseModeTrigger.triggeredParseMode("Publications"));
        assertEquals(ParseMode.PUBLICATION, parseModeTrigger.triggeredParseMode("Publication"));
        assertEquals(ParseMode.PUBLICATION, parseModeTrigger.triggeredParseMode("Journal Submissions"));
        assertEquals(ParseMode.PUBLICATION, parseModeTrigger.triggeredParseMode("Journal Publications"));
        assertEquals(ParseMode.PUBLICATION, parseModeTrigger.triggeredParseMode("Conferences"));
    }

    @Test
    public void testNonTrigger() throws Exception {
        assertNull(parseModeTrigger.triggeredParseMode("A senior software engineer"));
        assertNull(parseModeTrigger.triggeredParseMode("I am proficient in Ruby"));
    }

}
