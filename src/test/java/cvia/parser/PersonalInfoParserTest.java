package cvia.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Michael Limantara on 10/13/2015.
 */
public class PersonalInfoParserTest {
    private PersonalInfoParser personalInfoParser;
    private String[] cvTestCases = {"22 College Avenue East #10-100 Singapore 138595 Michael Limantara\n+6581234567\nabc.def@gmail.com",
            "+65 8123 4567\n12z_d-ef@yahoo.com\n22 College Avenue East #10-100 S138595",
            "Michael Limantara\n123@hotmail.com\n+65 812 345 67\n461 Upper East Coast Road #04-08 The Summit Singapore 466506",
            "Michael Limantara123\n461 Upper East Coast Road #04-08 The Summit Singapore 4665066",
            ""
    };

    @Before
    public void setUp() throws Exception {
        personalInfoParser = new PersonalInfoParser();
    }

    @After
    public void tearDown() throws Exception {
        personalInfoParser = null;
    }

    @Test
    public void testParseName() throws Exception {

    }

    @Test
    public void testParseContactNumber() throws Exception {
        String[] expected = {"+6581234567", "+65 8123 4567", "+65 812 345 67"};
        for (int i = 0; i < cvTestCases.length; i++) {
            String output = personalInfoParser.parseContactNumber(cvTestCases[i]);
            if (output == null) {
                assertEquals(null, output);
            } else {
                assertEquals(expected[i], output);
            }
        }
    }

    @Test
    public void testParseEmail() throws Exception {
        String[] expected = {"abc.def@gmail.com", "12z_d-ef@yahoo.com", "123@hotmail.com"};
        for (int i = 0; i < cvTestCases.length; i++) {
            String output = personalInfoParser.parseEmail(cvTestCases[i]);
            if (output == null) {
                assertEquals(null, output);
            } else {
                assertEquals(expected[i], output);
            }
        }
    }

    @Test
    public void testParseAddress() throws Exception {
        String[] expected = {"22 College Avenue East #10-100 Singapore 138595",
                "22 College Avenue East #10-100 S138595",
                "461 Upper East Coast Road #04-08 The Summit Singapore 466506",
                "461 Upper East Coast Road #04-08 The Summit Singapore"
        };
        for (int i = 0; i < cvTestCases.length; i++) {
            String output = personalInfoParser.parseAddress(cvTestCases[i]);
            if (output == null) {
                assertEquals(null, output);
            } else {
                assertEquals(expected[i], output);
            }
        }
    }
}