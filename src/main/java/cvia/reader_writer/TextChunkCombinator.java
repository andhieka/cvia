package cvia.reader_writer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andhieka on 8/11/15.
 */
public class TextChunkCombinator {
    List<TextChunk> textChunks;

    public List<TextLine> combineTextChunks(List<TextChunk> originalTextChunks) {
        this.textChunks = originalTextChunks;
        // combine chunks that do not have space AND on the same row
        combineChunksWithoutSpaceOnTheSameRow();
        List<TextLine> textLines = combineChunksOnTheSameRow();
        return textLines;
    }

    private void combineChunksWithoutSpaceOnTheSameRow() {
        ArrayList<TextChunk> result = new ArrayList<TextChunk>();
        TextChunk currentTextChunk = null;
        for (TextChunk textChunk: textChunks) {
            if (currentTextChunk == null) {
                currentTextChunk = textChunk;
            } else if ((!textChunk.hasSpace()) && currentTextChunk.sameLine(textChunk)) {
                currentTextChunk = currentTextChunk.mergedWith(textChunk);
            } else {
                result.add(currentTextChunk);
                currentTextChunk = textChunk;
            }
        }
        if (currentTextChunk != null) {
            result.add(currentTextChunk);
            currentTextChunk = null;
        }
        this.textChunks = result;
    }

    private List<TextLine> combineChunksOnTheSameRow() {
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
