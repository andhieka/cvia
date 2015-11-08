package cvia.reader_writer;

import java.io.File;
import java.util.List;

/**
 * Created by andhieka on 4/11/15.
 */
public class PDFReadResult {
    private File file;
    private List<TextChunk> textChunks;
    private List<TextLine> textLines;

    PDFReadResult(File file, List<TextChunk> textChunks) {
        this.file = file;
        this.textChunks = textChunks;
        TextChunkCombinator combinator = new TextChunkCombinator();
        this.textLines = combinator.combineTextChunks(textChunks);
    }

    public File getFile() {
        return file;
    }

    public List<TextLine> getTextLines() {
        return textLines;
    }

    public List<TextChunk> getTextChunks() {
        return textChunks;
    }
}
