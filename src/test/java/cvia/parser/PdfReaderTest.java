package cvia.parser;


import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

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
        PdfReader reader = new PdfReader("Resume Michael Limantara.pdf");
        HashMap<String, String> infos = reader.getInfo();
        for (String key : infos.keySet()) {
            String value = infos.get(key);
            System.out.println(key + ": " + value);
        }

        System.out.println();

        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        TextExtractionStrategy strategy = parser.processContent(1, new SimpleTextExtractionStrategy());
        System.out.println(strategy.getResultantText());




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
