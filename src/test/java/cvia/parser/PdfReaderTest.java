package cvia.parser;


import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.ContentByteUtils;
import com.itextpdf.text.pdf.parser.PdfContentStreamProcessor;
import com.itextpdf.text.pdf.parser.RenderListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by andhieka on 12/10/15.
 */
public class PdfReaderTest {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testReadPDF() throws Exception {
        PdfReader reader = new PdfReader("Andhieka_Resume.pdf");
        extractText("Andhieka_Resume.pdf", "andhieka_resume.txt");
    }

    /**
     * Extracts text from a PDF document.
     * @param src  the original PDF document
     * @param dest the resulting text file
     * @throws IOException
     */
    public void extractText(String src, String dest) throws IOException {
        PrintWriter out = new PrintWriter(new FileOutputStream(dest));
        PdfReader reader = new PdfReader(src);
        RenderListener listener = new MyTextRenderListener(out);
        PdfContentStreamProcessor processor = new PdfContentStreamProcessor(listener);
        PdfDictionary pageDic = reader.getPageN(1);
        PdfDictionary resourcesDic = pageDic.getAsDict(PdfName.RESOURCES);
        processor.processContent(ContentByteUtils.getContentBytesForPage(reader, 1), resourcesDic);
        out.flush();
        out.close();
        reader.close();
    }

    public static void showEntries(PdfDictionary dict){
        for (PdfName key: dict.getKeys()) {
            System.out.print(key + ": ");
            System.out.println(dict.get(key));
        }
    }
    public static void showObject(PdfObject obj) {
        System.out.println(obj.getClass().getName() + ":");
        System.out.println("-> type: " + obj.type());
        System.out.println("-> toString: " + obj.toString());
    }

}
