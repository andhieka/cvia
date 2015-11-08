package cvia.parser;

import cvia.model.CV;
import cvia.reader_writer.TextChunk;

/**
 * Created by andhieka on 9/11/15.
 */
public class UnknownParser implements MiniParser {
    @Override
    public void parseAndSaveResultToCV(TextChunk textChunk, CV cv) {
        System.out.printf("UNKNOWN CHUNK: %s\n", textChunk.getText());
    }

    @Override
    public void reset() {
        // do nothing
    }
}
