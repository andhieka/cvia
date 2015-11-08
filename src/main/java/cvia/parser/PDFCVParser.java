package cvia.parser;

import cvia.model.CV;
import cvia.model.PersonalInfo;
import cvia.reader_writer.PDFReadResult;
import cvia.reader_writer.TextChunk;

/**
 * Created by Michael Limantara on 9/23/2015.
 */
public class PDFCVParser {

    enum ParseMode {
        PERSONAL_INFO, SKILL, PAGE_NUMBER, UNKNOWN
    }

    private PersonalInfoParser personalInfoParser = new PersonalInfoParser();
    private SkillParser skillParser = new SkillParser();
    private ParseModeTrigger parseModeTrigger = new ParseModeTrigger();
    private PDFReadResult rawCV;
    private CV parsedCV;
    private ParseMode parseMode;

    // Public methods

    public CV parse(PDFReadResult rawCV) {
        reset();
        this.rawCV = rawCV;
        this.parsedCV = new CV();
        runParseLoop();
        return parsedCV;
    }

    // Private methods

    private void reset() {
        this.rawCV = null;
        this.parsedCV = null;
        this.parseMode = ParseMode.UNKNOWN;
    }

    private void runParseLoop() {
        for (TextChunk textChunk: rawCV.getTextChunks()) {
            String text = textChunk.getText();
            ParseMode triggeredParseMode = parseModeTrigger.triggeredParseMode(text);
            if (triggeredParseMode != null) {
                this.parseMode = triggeredParseMode;
            }
            processTextChunk(textChunk);
        }

    }

    private void processTextChunk(TextChunk textChunk) {
        if (parseMode == ParseMode.PAGE_NUMBER) {
            // skip
        } else if (parseMode == ParseMode.SKILL) {
            skillParser.parseAndSaveResultToCV(textChunk, parsedCV);
        } else if (parseMode == ParseMode.PERSONAL_INFO) {
            personalInfoParser.parseAndSaveResultToCV(textChunk, parsedCV);
        }
    }

//    private PersonalInfo parsePersonalInfo() {
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
