package cvia.reader_writer;

import com.itextpdf.text.pdf.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by andhieka on 4/11/15.
 */
public class TextChunkExtractionStrategy implements TextExtractionStrategy {
    public static final String MESSAGE_IMAGE_NOT_IMPLEMENTED = "Ignoring image in the CV...";
    private ArrayList<TextChunk> textChunks = new ArrayList<TextChunk>();

    public List<TextChunk> getResultantTextChunks() {
        return textChunks;
    }
    
    @Override
    public String getResultantText() {
        Collections.sort(this.textChunks);
        StringBuffer sb = new StringBuffer();
        TextChunk lastChunk = null;

        TextChunk chunk;
        for(Iterator<TextChunk> it = this.textChunks.iterator(); it.hasNext(); lastChunk = chunk) {
            chunk = it.next();
            if (lastChunk == null) {
                sb.append(chunk.text);
            } else if (chunk.sameLine(lastChunk)) {
                float dist = chunk.distanceFromEndOf(lastChunk);
                if (dist < -chunk.charSpaceWidth) {
                    sb.append(' ');
                } else if (dist > chunk.charSpaceWidth / 2.0F && chunk.text.charAt(0) != 32 && lastChunk.text.charAt(lastChunk.text.length() - 1) != 32) {
                    sb.append(' ');
                }
                sb.append(chunk.text);
            } else {
                sb.append('\n');
                sb.append(chunk.text);
            }
        }

        return sb.toString();
    }

    @Override
    public void beginTextBlock() {
        // do nothing
    }

    @Override
    public void endTextBlock() {
        // do nothing
    }

    @Override
    public void renderText(TextRenderInfo textRenderInfo) {
        LineSegment segment = textRenderInfo.getBaseline();
        TextChunk location = new TextChunk(textRenderInfo.getText(), segment.getStartPoint(), segment.getEndPoint(), textRenderInfo.getSingleSpaceWidth());
        textChunks.add(location);
    }

    @Override
    public void renderImage(ImageRenderInfo imageRenderInfo) {
        System.out.println(MESSAGE_IMAGE_NOT_IMPLEMENTED);
    }


    private void dumpState() {
        for(TextChunk textChunk: textChunks) {
            textChunk.printDiagnostics();
            System.out.println();
        }

    }
}
