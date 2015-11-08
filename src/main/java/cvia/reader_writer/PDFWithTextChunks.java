package cvia.reader_writer;

import java.io.File;
import java.util.List;

/**
 * Created by andhieka on 4/11/15.
 */
public class PDFWithTextChunks {
    private File file;
    private List<TextChunk> textChunks;

    PDFWithTextChunks(File file, List<TextChunk> textChunks) {
        this.file = file;
        this.textChunks = textChunks;
    }

    public File getFile() {
        return file;
    }

    public List<TextChunk> getTextChunks() {
        return textChunks;
    }
}
