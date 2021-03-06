package cvia.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by Michael Limantara on 11/3/2015.
 */
public class RootWindowController {
    private static final String MENU_NORMAL_STYLE = "-fx-background-color: #2f3e4d; -fx-text-fill: #a59d8e";
    private static final String MENU_HOVER_STYLE = "-fx-background-color: #2b3749; -fx-text-fill: #ffffff";
    private static final String MENU_SELECTED_STYLE = "-fx-background-color: #6698c8; -fx-text-fill: #ffffff";

    @FXML
    private Pane rootPane;
    @FXML
    private Button manageCVMenu;
    @FXML
    private Button manageJobMenu;
//    @FXML
//    private Button searchMenu;

    private enum MenuPosition {
        CV, JOB
    }
    private MenuPosition selectedPosition;

    private Pane cvPane;
    private ScrollPane jdPane;

    @FXML
    private void initialize() {
        setUpHoverEffectOnMenu();
        setOnActionMenuHandler();
        showManageCVLayout();

    }

    private void setUpHoverEffectOnMenu() {
        manageCVMenu.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selectedPosition != MenuPosition.CV) {
                    manageCVMenu.setStyle(MENU_HOVER_STYLE);
                }
            }
        });
        manageCVMenu.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selectedPosition != MenuPosition.CV) {
                    manageCVMenu.setStyle(MENU_NORMAL_STYLE);
                }
            }
        });

        manageJobMenu.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selectedPosition != MenuPosition.JOB) {
                    manageJobMenu.setStyle(MENU_HOVER_STYLE);
                }
            }
        });
        manageJobMenu.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selectedPosition != MenuPosition.JOB) {
                    manageJobMenu.setStyle(MENU_NORMAL_STYLE);
                }
            }
        });

//        searchMenu.setOnMouseEntered(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                searchMenu.setStyle(MENU_HOVER_STYLE);
//            }
//        });
//        searchMenu.setOnMouseExited(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                searchMenu.setStyle(MENU_NORMAL_STYLE);
//            }
//        });
    }

    private void setOnActionMenuHandler() {
        manageCVMenu.setOnAction(event -> {
            selectedPosition = MenuPosition.CV;
            giveEffectOnSelectedMenu();
            // Open up ManageCV pane on the right
            showManageCVLayout();
        });

        manageJobMenu.setOnAction(event -> {
            selectedPosition = MenuPosition.JOB;
            giveEffectOnSelectedMenu();
            // Open up ManageJob pane on the right
            showManageJobLayout();
        });

//        searchMenu.setOnAction(event -> {
//            showSearchLayout();
//        });
    }

    private void giveEffectOnSelectedMenu() {
        if (selectedPosition == MenuPosition.CV) {
            manageCVMenu.setStyle(MENU_SELECTED_STYLE);
            manageJobMenu.setStyle(MENU_NORMAL_STYLE);
        } else {
            manageJobMenu.setStyle(MENU_SELECTED_STYLE);
            manageCVMenu.setStyle(MENU_NORMAL_STYLE);
        }
    }

    private void showManageCVLayout() {
        clearAllLayout();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CviaApp.class.getResource("/CVList.fxml"));

            cvPane = loader.load();
            CVListController cvListController = loader.getController();
            cvListController.loadInitialData();

            cvPane.setLayoutX(200);
            cvPane.setLayoutY(0);
            rootPane.getChildren().add(cvPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showManageJobLayout() {
        clearAllLayout();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CviaApp.class.getResource("/JobDescriptionList.fxml"));

            jdPane = (ScrollPane) loader.load();
            JobDescriptionListController jdListController = loader.getController();

            jdPane.setLayoutX(200);
            jdPane.setLayoutY(0);
            jdPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
            jdPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
            rootPane.getChildren().add(jdPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showSearchLayout() {
        //TODO: Remove in sidebar?
    }

    private void clearAllLayout() {
        rootPane.getChildren().removeAll(cvPane, jdPane);
    }
}
