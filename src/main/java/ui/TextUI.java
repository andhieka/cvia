package ui;

import cvia.model.CVParseResult;
import cvia.parser.CVParser;
import cvia.reader_writer.PdfToTextReader;

/**
 * Created by Michael Limantara on 10/13/2015.
 */
public class TextUI {

    public static void main(String args[]) {
        PdfToTextReader pdfToTextReader = new PdfToTextReader();
        CVParser cvParser = new CVParser();
        String pathToPdf = "Resume Michael Limantara.pdf";

        try {
            String cv = pdfToTextReader.read(pathToPdf);
            CVParseResult cvParseResult = cvParser.parse(cv);
            System.out.println(cvParseResult.getPersonalInfo().toString());
        } catch (Exception e) {

        }
    }
}
