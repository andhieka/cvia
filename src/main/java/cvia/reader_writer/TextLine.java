package cvia.reader_writer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andhieka on 8/11/15.
 */
public class TextLine {
    private String text;
    private List<TextChunk> textChunks;

    public TextLine(List<TextChunk> textChunks) {
        this.textChunks = new ArrayList<TextChunk>(textChunks);
        StringBuilder sb = new StringBuilder();
        for(TextChunk textChunk: textChunks) {
            sb.append(textChunk.text);
        }
        text = sb.toString();
    }

    public String getText() {
        return text;
    }

    public List<TextChunk> getTextChunks() {
        return textChunks;
    }
}
