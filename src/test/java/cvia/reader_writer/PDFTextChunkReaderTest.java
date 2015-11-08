package cvia.reader_writer;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Created by andhieka on 8/11/15.
 */
public class PDFTextChunkReaderTest {
    PDFTextChunkReader pdfTextChunkReader;

    @Before
    public void setUp() throws Exception {
        pdfTextChunkReader = new PDFTextChunkReader();
    }

    @After
    public void tearDown() throws Exception {
        pdfTextChunkReader = null;
    }

    @Test
    public void testOne() throws Exception {
        File file = new File("resume.pdf");
        assertTrue(file.exists());
        PDFWithTextChunk pdfWithTextChunk = pdfTextChunkReader.readPDFFromFile(file);
        List<TextChunk> textChunks = pdfWithTextChunk.getTextChunks();
        assertNotNull(textChunks);
        assertFalse("Result must not be empty", textChunks.isEmpty());
        for (TextChunk textChunk: textChunks) {
            textChunk.printDiagnostics();
        }
    }


}
