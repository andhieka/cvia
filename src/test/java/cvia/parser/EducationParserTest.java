package cvia.parser;

import cvia.model.CV;
import cvia.reader_writer.TextChunk;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by andhieka on 10/11/15.
 */
public class EducationParserTest {
    EducationParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new EducationParser();
    }

    @After
    public void tearDown() throws Exception {
        parser = null;
    }

    @Test
    public void testParseMajor() throws Exception {
        String major = parser.findMajor("National University of Singapore Bachelor of Computing (Computer Science), Honours");
        assertEquals("Computer Science", major);
    }

    @Test
    public void testParseUniversity() throws Exception {
        String university = parser.findInstitution("National University of Singapore Bachelor of Computing (Computer Science), Honours");
        assertEquals("National University of Singapore", university);
    }
}
