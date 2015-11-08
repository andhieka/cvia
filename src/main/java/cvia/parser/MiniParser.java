package cvia.parser;

import cvia.model.CV;
import cvia.reader_writer.TextChunk;

/**
 * Created by andhieka on 8/11/15.
 */
interface MiniParser {
    void parseAndSaveResultToCV(TextChunk textChunk, CV cv);
    void reset();
}
