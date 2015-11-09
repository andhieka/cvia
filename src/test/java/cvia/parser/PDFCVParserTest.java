package cvia.parser;

import cvia.model.CV;
import cvia.reader_writer.PDFReadResult;
import cvia.reader_writer.PDFTextChunkReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by andhieka on 9/11/15.
 */
public class PDFCVParserTest {

    PDFCVParser parser;
    PDFTextChunkReader reader;

    @Before
    public void setUp() throws Exception {
        reader = new PDFTextChunkReader();
        parser = new PDFCVParser();
    }

    @After
    public void tearDown() throws Exception {
        reader = null;
        parser = null;
    }

    @Test
    public void testDesmondLimCV() throws Exception {
        File file = new File("resume.pdf");
        PDFReadResult readResult = reader.readPDFFromFile(file);
        CV cv = parser.parse(readResult);
    }

    @Test
    public void testYaminiCV() throws Exception {
        File file = new File("yamini.pdf");
        PDFReadResult readResult = reader.readPDFFromFile(file);
        CV cv = parser.parse(readResult);
    }

    @Test
    public void testMichaelLimantaraCV() throws Exception {
        File file = new File("Resume Michael Limantara.pdf");
        PDFReadResult readResult = reader.readPDFFromFile(file);
        CV cv = parser.parse(readResult);
    }

    @Test
    public void testDesmondLim2CV() throws Exception {
        File file = new File("desmond2.pdf");
        PDFReadResult readResult = reader.readPDFFromFile(file);
        CV cv = parser.parse(readResult);
    }
}
