package cvia.ui;/**
 * Created by Michael Limantara on 11/2/2015.
 */

import cvia.model.*;
import cvia.resources.CVSeed;
import cvia.resources.JobDescriptionSeed;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CviaApp extends Application {
    private static final String APPLICATION_NAME = "CVestigator";

    private Stage primaryStage;
    private Pane rootPane;
    private RootWindowController rootWindowController;

    private LogicController logicController = LogicController.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(APPLICATION_NAME);

        loadSeedData();
        initializeMainLayout();
    }

    private void loadSeedData() {
        try {
            seedCV();
            seedJD();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeMainLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CviaApp.class.getResource("/RootWindow.fxml"));

            rootPane = loader.load();
            rootWindowController = loader.getController();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootPane);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.sizeToScene();
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // For testing
    private void seedCV() throws IOException {
        CVSeed.getInstance().execute();
    }

    private void seedJD() {
        JobDescriptionSeed.getInstance().execute();
    }
}
