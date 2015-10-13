package cvia.reader_writer;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.IOException;

/**
 * Created by Michael Limantara on 10/13/2015.
 */
public class PdfToTextReader implements Reader {

    @Override
    public String read(String path) throws FileFormatNotSupportedException, IOException {
        PdfReader reader = new PdfReader(path);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        TextExtractionStrategy strategy = parser.processContent(1, new SimpleTextExtractionStrategy());
        reader.close();
        return strategy.getResultantText();
    }
}
