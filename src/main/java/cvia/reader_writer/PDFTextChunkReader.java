package cvia.reader_writer;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andhieka on 4/11/15.
 */
public class PDFTextChunkReader {
    public PDFReadResult readPDFFromFile(File file) throws IOException {
        PdfReader reader = new PdfReader(file.getAbsolutePath());
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        ArrayList<TextChunk> textChunks = new ArrayList<TextChunk>();
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            TextChunkExtractionStrategy strategy = new TextChunkExtractionStrategy(i);
            strategy = parser.processContent(i, strategy);
            List<TextChunk> textChunksInPage = strategy.getResultantTextChunks();
            textChunks.addAll(textChunksInPage);
        }
        reader.close();
        return new PDFReadResult(file, textChunks);
    }
}
