package cvia.reader_writer;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.IOException;
import java.util.List;

/**
 * Created by Michael Limantara on 10/13/2015.
 */
public class PDFTextReader {
    public String read(String path) throws IOException, Exception {
        PdfReader reader = new PdfReader(path);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < reader.getNumberOfPages(); i++) {
            TextExtractionStrategy strategy = parser.processContent(i, new LocationTextExtractionStrategy());
            stringBuilder.append(strategy.getResultantText());
        }
        reader.close();
        return stringBuilder.toString();
    }
}
