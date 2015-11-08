package cvia.parser;

import cvia.model.CV;
import cvia.reader_writer.PDFWithTextChunks;

/**
 * Created by Michael Limantara on 9/23/2015.
 */
public class PDFCVParser {
    private PersonalInfoParser personalInfoParser;
    private SkillParser skillParser;
    private PDFWithTextChunks rawCV;

    // Public methods

    public PDFCVParser() {
    }

    public CV parse(PDFWithTextChunks rawCV) {
        this.rawCV = rawCV;

        CV cvParseResult = new CV();

        return cvParseResult;
    }

    // Private methods

//    private PersonalInfo parsePersonalInfo() {
//        if (personalInfoParser == null) {
//            personalInfoParser = new PersonalInfoParser();
//        }
//
//        // Extract personal information from cvContent
//        // String name = personalInfoParser.parseName();
//        String contactNumber = personalInfoParser.parseContactNumber(cvContent);
//        String email = personalInfoParser.parseEmail(cvContent);
//        String address = personalInfoParser.parseAddress(cvContent);
//
//        // Based on the information extracted, construct personal information object
//        PersonalInfo personalInfo = new PersonalInfo();
//        personalInfo.setContactNumber(contactNumber);
//        personalInfo.setEmail(email);
//        personalInfo.setAddress(address);
//
//        return personalInfo;
//    }
}
