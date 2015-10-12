package cvia.parser;

/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

import com.itextpdf.text.pdf.parser.TextRenderInfo;

import java.io.PrintWriter;

import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.RenderListener;

public class MyTextRenderListener implements RenderListener {

    /** The print writer to which the information will be written. */
    protected PrintWriter out;

    /**
     * Creates a RenderListener that will look for text.
     */
    public MyTextRenderListener(PrintWriter out) {
        this.out = out;
    }

    /**
     * @see com.itextpdf.text.pdf.parser.RenderListener#beginTextBlock()
     */
    public void beginTextBlock() {
        out.print("<");
    }

    /**
     * @see com.itextpdf.text.pdf.parser.RenderListener#endTextBlock()
     */
    public void endTextBlock() {
        out.println(">");
    }

    /**
     * @see com.itextpdf.text.pdf.parser.RenderListener#renderImage(
     *     com.itextpdf.text.pdf.parser.ImageRenderInfo)
     */
    public void renderImage(ImageRenderInfo renderInfo) {
    }

    /**
     * @see com.itextpdf.text.pdf.parser.RenderListener#renderText(
     *     com.itextpdf.text.pdf.parser.TextRenderInfo)
     */
    public void renderText(TextRenderInfo renderInfo) {
        out.print("<");
        out.print(renderInfo.getText());
        out.print(">");
    }
}
