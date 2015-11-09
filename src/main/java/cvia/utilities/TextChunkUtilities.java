package cvia.utilities;

import cvia.reader_writer.TextChunk;
import cvia.reader_writer.TextLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andhieka on 9/11/15.
 */
public class TextChunkUtilities {
    public static ArrayList<TextLine> combineLines(List<TextChunk> textChunks) {
        ArrayList<TextLine> result = new ArrayList<TextLine>();
        ArrayList<TextChunk> currentLine = new ArrayList<TextChunk>();
        TextChunk previousTextChunk = null;
        for (TextChunk textChunk: textChunks) {
            if (previousTextChunk == null) {
                currentLine.add(textChunk);
            } else if (previousTextChunk.sameLine(textChunk)) {
                currentLine.add(textChunk);
            } else {
                result.add(new TextLine(currentLine));
                currentLine.clear();
                currentLine.add(textChunk);
            }
            previousTextChunk = textChunk;
        }
        if (!currentLine.isEmpty()) {
            result.add(new TextLine(currentLine));
        }
        return result;
    }
}
