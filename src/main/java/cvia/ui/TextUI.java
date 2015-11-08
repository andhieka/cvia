package cvia.ui;

import cvia.model.CV;
import cvia.model.Skill;
import cvia.parser.CVParser;
import cvia.reader_writer.PdfToTextReader;

import java.util.List;

/**
 * Created by Michael Limantara on 10/13/2015.
 */
public class TextUI {

    public static void main(String args[]) {
        PdfToTextReader pdfToTextReader = new PdfToTextReader();
        CVParser cvParser = new CVParser();
        String pathToPdf = "resume.pdf";

        try {
            String cv = pdfToTextReader.read(pathToPdf);
            CV cvParseResult = cvParser.parse(cv);
            System.out.println("# PERSONAL PARTICULARS");
            System.out.println(cvParseResult.getPersonalInfo().toString());
            System.out.println("# SKILLS");
            List<Skill> skills = cvParseResult.getSkills();
            for(Skill s: skills) {
                System.out.println(" - " + s);
            }
        } catch (Exception e) {

        }
    }
}
