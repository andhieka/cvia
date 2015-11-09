package cvia.parser;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by andhieka on 10/11/15.
 */
public class UniversityBankTest {
    UniversityBank universityBank = UniversityBank.getInstance();

    @Test
    public void testGetNames() throws Exception {
        List<String> universityNames = universityBank.getUniversityNames();
        assertEquals(8271, universityNames.size());
    }

    @Test
    public void testGetAcronyms() throws Exception {
        List<String> universityAcronyms = universityBank.getUniversityAcronyms();
        assertEquals(8271, universityAcronyms.size());
    }

    @Test
    public void testAcronymToName() throws Exception {
        assertEquals("National University of Singapore", universityBank.universityNamesWithAcronym("NUS").get(1));
        assertEquals("National University of Singapore", universityBank.universityNamesWithAcronym("nus").get(1));
        assertEquals("California Institute of Technology", universityBank.universityNamesWithAcronym("CalTech").get(0));
    }
}
