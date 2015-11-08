package cvia.ui;/**
 * Created by Michael Limantara on 11/2/2015.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class CviaApp extends Application {
    private static final String APPLICATION_NAME = "CVIA";

    private Stage primaryStage;
    private Pane rootPane;
    private RootWindowController rootWindowController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(APPLICATION_NAME);

        initializeMainLayout();
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
}
