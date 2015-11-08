package cvia.ui;

import cvia.model.CV;
import cvia.model.Skill;
import cvia.parser.PDFCVParser;
import cvia.reader_writer.PDFTextReader;

import java.util.List;

/**
 * Created by Michael Limantara on 10/13/2015.
 */
public class TextUI {

    public static void main(String args[]) {
        PDFTextReader pdfToTextReader = new PDFTextReader();
        PDFCVParser cvParser = new PDFCVParser();
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
