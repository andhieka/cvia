package cvia.parser;

import cvia.model.CV;
import cvia.reader_writer.TextChunk;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser to detect human languages possessed by the candidate.
 * By default, language proficiency is Advanced.
 * Created by andhieka on 9/11/15.
 */
public class LanguageParser implements MiniParser {
    private CV cv;
    private ArrayList<TextChunk> textChunks = new ArrayList<TextChunk>();

    @Override
    public void reset() {
        this.cv = null;
        this.textChunks.clear();
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
        assert (cv != null);

    }
}
