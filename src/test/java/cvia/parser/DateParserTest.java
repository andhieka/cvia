package cvia.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

/**
 * Created by andhieka on 10/11/15.
 */
public class DateParserTest {
    private DateRangeParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new DateRangeParser();
    }

    @After
    public void tearDown() throws  Exception {
        parser = null;
    }

    @Test
    public void testDateOne() throws Exception {
        DateRange dateRange = parser.parse("Jul 2014 to May 2016");
        assertEquals(LocalDate.of(2014, 7, 1), dateRange.getStartDate());
        assertEquals(LocalDate.of(2016, 5, 1), dateRange.getEndDate());
    }

    @Test
    public void testDateNow() throws Exception {
        DateRange dateRange = parser.parse("Jul 2014 Until Now");
        assertEquals(LocalDate.of(2014, 7, 1), dateRange.getStartDate());
        assertEquals(LocalDate.now().withDayOfMonth(1), dateRange.getEndDate());
    }

    @Test
    public void testDatePresent() throws Exception {
        DateRange dateRange = parser.parse("Jul 2014 - Present");
        assertEquals(LocalDate.of(2014, 7, 1), dateRange.getStartDate());
        assertEquals(LocalDate.now().withDayOfMonth(1), dateRange.getEndDate());
    }

}
