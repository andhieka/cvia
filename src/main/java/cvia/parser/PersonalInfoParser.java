package cvia.parser;

import cvia.model.CV;
import cvia.model.PersonalInfo;
import cvia.reader_writer.TextChunk;
import cvia.reader_writer.TextLine;
import cvia.utilities.StringUtilities;
import cvia.utilities.TextChunkUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Michael Limantara on 10/12/2015.
 */
public class PersonalInfoParser implements MiniParser {
    private String PATTERN_PHONE_NUMBER = "(.*?)(\\+[1-9]{1}[0-9 ]{3,14})(.*)";
    private String PATTERN_EMAIL_ADDRESS = "(.*?)([_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+" +
            "(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))(.*)";
    private String PATTERN_ADDRESS = "(.*?)(\\d+[A-Za-z ]+ #[\\d-]* [A-Za-z ]+(\\d{6}(\\D|$))?)";

    private CV cv;
    private ArrayList<TextChunk> textChunks = new ArrayList<TextChunk>();

    @Override
    public void reset() {
        cv = null;
        textChunks.clear();
    }

    @Override
    public void setCV(CV cv) {
        this.cv = cv;
    }

    @Override
    public void appendTextChunk(TextChunk textChunk) {
        textChunks.add(textChunk);
    }

    @Override
    public void parseAndSave() {
        assert(this.cv != null);

        PersonalInfo personalInfo = new PersonalInfo();
        List<TextLine> textLines = TextChunkUtilities.combineLines(textChunks);
        String cvContent = TextChunkUtilities.stringFromTextLines(textLines);

        String name = parseName(textLines);
        String email = parseEmail(cvContent);
        String contactNumber = parseContactNumber(cvContent);
        String address = parseAddress(cvContent);
        personalInfo.setName(name);
        personalInfo.setEmail(email);
        personalInfo.setContactNumber(contactNumber);
        personalInfo.setAddress(address);

        cv.setPersonalInfo(personalInfo);
    }

    String parseName(List<TextLine> textLines) {
        for (TextLine textLine: textLines) {
            String text = textLine.getText();
            text = text.trim();
            if (text.isEmpty()) continue;
            if (text.matches("(.*?)(page[\\s0-9]*)(.*)")) continue;
            return StringUtilities.removeRedundantSpaces(text);
        }
        return "";
    }

    // Find the contact number in the CV, and returns null if not found
    String parseContactNumber(String cv) {
        Pattern phoneNumberPattern = Pattern.compile(PATTERN_PHONE_NUMBER);
        Matcher matcher = phoneNumberPattern.matcher(cv);
        if (matcher.find()) {
            return matcher.group(2);
        }

        return null;
    }

    String parseEmail(String cv) {
        Pattern emailPattern = Pattern.compile(PATTERN_EMAIL_ADDRESS);
        Matcher matcher = emailPattern.matcher(cv);
        if (matcher.find()) {
            return matcher.group(2);
        }

        return null;
    }

    String parseAddress(String cv) {
        Pattern addressPattern = Pattern.compile(PATTERN_ADDRESS);
        Matcher matcher = addressPattern.matcher(cv);
        if (matcher.find()) {
            return matcher.group(2).trim();
        }
        return null;
    }

}
