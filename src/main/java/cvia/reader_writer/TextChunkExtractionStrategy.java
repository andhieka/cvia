package cvia.reader_writer;

import com.itextpdf.text.pdf.parser.*;

import java.util.List;

/**
 * Created by andhieka on 4/11/15.
 */
public class TextChunkExtractionStrategy implements TextExtractionStrategy {

    public List<TextChunk> getResultantTextChunks() {
        return null;
    }
    
    @Override
    public String getResultantText() {
        return null;
    }

    @Override
    public void beginTextBlock() {

    }

    @Override
    public void renderText(TextRenderInfo textRenderInfo) {
        //import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy

    }

    @Override
    public void endTextBlock() {

    }

    @Override
    public void renderImage(ImageRenderInfo imageRenderInfo) {

    }

}
