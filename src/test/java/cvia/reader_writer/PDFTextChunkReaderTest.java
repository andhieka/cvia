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
        File file = new File("Resume Michael Limantara.pdf");
        assertTrue(file.exists());
        PDFReadResult pdfReadResult = pdfTextChunkReader.readPDFFromFile(file);
        List<TextChunk> textChunks = pdfReadResult.getTextChunks();
        assertNotNull(textChunks);
        assertFalse("Result must not be empty", textChunks.isEmpty());
        for (TextChunk textChunk: textChunks) {
            textChunk.printDiagnostics();
        }
    }

    @Test
    public void testTextChunkCombinator() throws Exception {
        File file = new File("desmond2.pdf");
        assertTrue(file.exists());
        PDFReadResult pdfReadResult = pdfTextChunkReader.readPDFFromFile(file);
        List<TextChunk> textChunks = pdfReadResult.getTextChunks();
        TextChunkCombinator combinator = new TextChunkCombinator();
        List<TextLine> combinedTextChunks = combinator.combineTextChunks(textChunks);
        for (TextLine textLine: combinedTextChunks) {
            System.out.println(textLine.getText());
        }
    }

    @Test
    public void testPDFReadResult() throws Exception {
        File file = new File("yamini.pdf");
        assertTrue(file.exists());
        PDFReadResult pdfReadResult = pdfTextChunkReader.readPDFFromFile(file);
        List<TextLine> textLines = pdfReadResult.getTextLines();
        for (TextLine textLine: textLines) {
            System.out.println(textLine.getText());
        }
    }
}
