package cvia.reader_writer;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andhieka on 4/11/15.
 */
public class PDFTextChunksReader {
    public PDFWithTextChunks readPDFFromFile(File file) throws IOException {
        PdfReader reader = new PdfReader(file.getAbsolutePath());
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        ArrayList<TextChunk> textChunks = new ArrayList<TextChunk>();
        for (int i = 0; i < reader.getNumberOfPages(); i++) {
            TextChunkExtractionStrategy strategy = parser.processContent(i, new TextChunkExtractionStrategy());
            List<TextChunk> textChunksInPage = strategy.getResultantTextChunks();
            textChunks.addAll(textChunksInPage);
        }
        reader.close();
        return new PDFWithTextChunks(file, textChunks);
    }
}
