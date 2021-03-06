package cvia.parser;

import cvia.model.CV;
import cvia.reader_writer.TextChunk;

import java.util.ArrayList;

/**
 * Created by andhieka on 9/11/15.
 */
public class UnknownParser implements MiniParser {
    private CV cv;
    private ArrayList<TextChunk> textChunks = new ArrayList<TextChunk>();

    @Override
    public void setCV(CV cv) {
        this.cv = cv;
    }

    @Override
    public void appendTextChunk(TextChunk textChunk) {
        System.out.printf("UNKNOWN CHUNK: %s\n", textChunk.getText());
        textChunks.add(textChunk);
    }

    @Override
    public void parseAndSave() {
        assert (cv != null);
    }

    @Override
    public void reset() {
        cv = null;
        textChunks.clear();
    }
}
