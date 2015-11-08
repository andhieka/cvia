package cvia.ui;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Michael Limantara on 11/7/2015.
 */
public class PdfBoxTest {

    @FXML
    private Pane pane;

    @FXML
    private void initialize() throws IOException {

        PDDocument document = PDDocument.load("resume.pdf");
        PDPage page1 = (PDPage) document.getDocumentCatalog().getAllPages().get(0);
        BufferedImage bufferedImage = page1.convertToImage(BufferedImage.TYPE_INT_RGB, 50);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(image.getHeight());
        imageView.setFitWidth(image.getWidth());
        imageView.setImage(image);
        pane.getChildren().addAll(imageView);
    }
}
