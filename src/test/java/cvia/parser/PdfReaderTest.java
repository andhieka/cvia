package cvia.parser;


import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by andhieka on 12/10/15.
 */
public class PdfReaderTest {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testReadPDF() throws Exception {
        PdfReader reader = new PdfReader("resume.pdf");
        HashMap<String, String> infos = reader.getInfo();
        for (String key : infos.keySet()) {
            String value = infos.get(key);
            System.out.println(key + ": " + value);
        }

        System.out.println();

        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        TextExtractionStrategy strategy = parser.processContent(1, new SimpleTextExtractionStrategy());
        System.out.println(strategy.getResultantText());
    }
}
