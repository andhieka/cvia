package cvia.parser;

import cvia.model.CV;
import cvia.reader_writer.TextChunk;
import cvia.reader_writer.TextLine;
import cvia.utilities.TextChunkUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andhieka on 9/11/15.
 */
public class WorkExperienceParser implements MiniParser {
    private CV cv;
    private ArrayList<TextChunk> textChunks = new ArrayList<TextChunk>();

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
        assert(cv != null);

        List<TextLine> textLines = TextChunkUtilities.combineLines(textChunks);
        for (TextLine textLine:textLines) {
            System.out.println(textLine.getText());
        }
    }

    @Override
    public void reset() {
        cv = null;
        textChunks.clear();
    }
}
