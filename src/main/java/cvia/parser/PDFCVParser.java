package cvia.parser;

import cvia.model.CV;
import cvia.reader_writer.PDFReadResult;
import cvia.reader_writer.TextChunk;
import cvia.reader_writer.TextLine;
import cvia.parser.MiniParser;

import java.util.HashMap;

/**
 * Created by Michael Limantara on 9/23/2015.
 */
public class PDFCVParser {
    enum ParseMode {
        PERSONAL_INFO, EDUCATION, WORK_EXPERIENCE, LANGUAGE,
        SKILL, PUBLICATION, UNKNOWN
    }

    private ParseModeTrigger parseModeTrigger = new ParseModeTrigger();
    private HashMap<ParseMode, MiniParser> miniParsers = new HashMap<ParseMode, MiniParser>();
    private PDFReadResult rawCV;
    private CV parsedCV;
    private ParseMode parseMode;

    // Public methods

    public PDFCVParser() {
        setupMiniParsers();
    }

    public CV parse(PDFReadResult rawCV) {
        reset();
        this.rawCV = rawCV;
        parsedCV = new CV();
        for (MiniParser miniParser: miniParsers.values()) {
            miniParser.setCV(parsedCV);
        }
        runParseLoop();
        miniParsers.values().forEach(MiniParser::parseAndSave);
        attachFullTextToCV();
        return parsedCV;
    }

    // Private methods

    private void setupMiniParsers() {
        miniParsers.put(ParseMode.PERSONAL_INFO, new PersonalInfoParser());
        miniParsers.put(ParseMode.EDUCATION, new EducationParser());
        miniParsers.put(ParseMode.WORK_EXPERIENCE, new WorkExperienceParser());
        miniParsers.put(ParseMode.LANGUAGE, new LanguageParser());
        miniParsers.put(ParseMode.SKILL, new SkillParser());
        miniParsers.put(ParseMode.PUBLICATION, new PublicationParser());
        miniParsers.put(ParseMode.UNKNOWN, new UnknownParser());
    }

    private void reset() {
        this.rawCV = null;
        this.parsedCV = null;
        this.parseMode = ParseMode.PERSONAL_INFO;
        miniParsers.values().forEach(MiniParser::reset);
    }

    private void runParseLoop() {
        for (TextLine textLine: rawCV.getTextLines()) {
            ParseMode triggeredParseMode = parseModeTrigger.triggeredParseMode(textLine.getText());
            if (triggeredParseMode != null) {
                this.parseMode = triggeredParseMode;
            } else {
                parseLine(textLine);
            }
        }
    }

    private void parseLine(TextLine textLine) {
        for (TextChunk textChunk: textLine.getTextChunks()) {
            MiniParser selectedParser = miniParsers.get(parseMode);
            selectedParser.appendTextChunk(textChunk);
        }
    }

    private void attachFullTextToCV() {
        StringBuilder fullTextBuilder = new StringBuilder();
        for (TextLine textLine : rawCV.getTextLines()) {
            fullTextBuilder.append(textLine.getText()).append('\n');
        }
        parsedCV.setFullText(fullTextBuilder.toString());
    }
}
