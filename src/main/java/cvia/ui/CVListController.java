package cvia.ui;

import cvia.model.CV;
import cvia.model.PersonalInfo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Michael Limantara on 11/3/2015.
 */
public class CVListController {
    private static final Double SIZE_ADD_CV_BUTTON = 25.0;
    private static final Double SIZE_EDIT_CV_BUTTON = 15.0;
    private static final Double SIZE_DELETE_CV_BUTTON = 15.0;
    private static final String POPUP_DELETE_CONFIRMATION_TITLE = "Delete Confirmation";

    @FXML
    private Pane pane;
    @FXML
    private HBox searchBox;
    @FXML
    private TextField searchTextField;

    private Button btnAddCv;

    @FXML
    private TableView<CV> cvTable;
    @FXML
    private TableColumn columnNo;
    @FXML
    private TableColumn<CV, String> columnName;
    @FXML
    private TableColumn<CV, String> columnContactNumber;
    @FXML
    private TableColumn<CV, String> columnEmail;
    @FXML
    private TableColumn<CV, String> columnCurrentTitle;
    @FXML
    private TableColumn columnAction;

    private LogicController logicController;
    private ObservableList<CV> displayedCVData;
    private List<CV> allCVData;

    public void refreshData(CV newCv) {
        CV oldCv = null;
        for (CV cv: allCVData) {
            if (cv.getId() == newCv.getId()) {
                oldCv = cv;
            }
        }

        allCVData.remove(oldCv);
        allCVData.add(newCv);

        populateCVTable(allCVData);
    }

    @FXML
    private void initialize() {
        logicController = LogicController.getInstance();
        setUpBtnAddCv();
        setUpSearchBox();
        setUpCVTable();
        loadInitialData();
    }

    private void loadInitialData() {
        allCVData = logicController.listCV();
        populateCVTable(allCVData);
    }

    private void setUpBtnAddCv() {
        btnAddCv = new Button();
        btnAddCv.setLayoutX(50);
        btnAddCv.setLayoutY(42);
        ImageView imageView = new ImageView("/add.png");
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        btnAddCv.setGraphic(imageView);
        btnAddCv.setText("Insert New CV/Resume");
        btnAddCv.setStyle("-fx-background-color: gold; -fx-font-size: 14");

        btnAddCv.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Import new CV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            List<File> filesSelected = fileChooser.showOpenMultipleDialog(btnAddCv.getScene().getWindow());

            if (filesSelected != null) {
                // Process uploaded CVs (files)
                for (File cvFile : filesSelected) {
                    try {
                        CV cv = logicController.addCV(cvFile);
                        allCVData.add(cv);
                    } catch (IOException e) {
                        //TODO: Trigger alert box
                    }
                }

                // Refresh table view
                populateCVTable(allCVData);
            }
        });

        pane.getChildren().add(btnAddCv);
    }

    private void setUpSearchBox() {
        addShadowEffectOnFocusForSearchBox();
        searchTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                performSearch(searchTextField.getText());
            }
        });
    }

    private void addShadowEffectOnFocusForSearchBox() {
        searchTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setRadius(3.0);
                    searchBox.setEffect(dropShadow);
                } else {
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setRadius(0.0);
                    searchBox.setEffect(dropShadow);
                }
            }
        });
    }

    private void performSearch(String keyword) {
        List<CV> searchResult = new ArrayList<CV>();
        for (CV cv : allCVData) {
            PersonalInfo personalInfo = cv.getPersonalInfo();
            if (personalInfo.getName().toLowerCase().contains(keyword.toLowerCase())) {
                searchResult.add(cv);
            } else if (personalInfo.getEmail().contains(keyword)) {
                searchResult.add(cv);
            }
        }

        populateCVTable(searchResult);
    }

    private void setUpCVTable() {
        cvTable.setFixedCellSize(25);
        columnNo.setSortable(false);
        columnNo.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setText("");
                        } else {
                            setText(getTableRow().getIndex() + 1 + "");
                        }
                    }
                };
            }
        });
        columnNo.setStyle("-fx-alignment: CENTER");
        columnName.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getPersonalInfo().getName()));
        columnContactNumber.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getPersonalInfo().getContactNumber()));
        columnEmail.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getPersonalInfo().getEmail()));

//        columnCurrentTitle.setCellValueFactory(cellData ->
//                new ReadOnlyStringWrapper(cellData.getValue().getPersonalInfo().getContactNumber()));
        columnAction.setSortable(false);
        columnAction.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                final TableCell cell = new TableCell() {
                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            final HBox hbox = new HBox();

                            final ImageButton editBtn = new ImageButton("/edit.png", SIZE_EDIT_CV_BUTTON);
                            final ImageButton deleteBtn = new ImageButton("/delete.png", SIZE_DELETE_CV_BUTTON);

                            Tooltip edit = new Tooltip("Edit");
                            Tooltip.install(editBtn, edit);

                            Tooltip delete = new Tooltip("Delete");
                            Tooltip.install(deleteBtn, delete);

                            hbox.setSpacing(5);
                            hbox.setAlignment(Pos.CENTER);
                            hbox.getChildren().addAll(editBtn, deleteBtn);

                            editBtn.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    CV selectedCV = (CV) getTableView().getItems().get(getIndex());
                                    editCV(selectedCV);
                                    populateCVTable(allCVData);
                                }
                            });

                            deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    CV selectedCV = (CV) getTableView().getItems().get(getIndex());
                                    showConfirmDeleteCVPopup(selectedCV);
                                }
                            });

                            setGraphic(hbox);
                            setText(null);
                        }
                    }
                };
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
    }

    private void editCV(CV cv) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CviaApp.class.getResource("/CVDetail.fxml"));
            SplitPane splitPane = loader.load();
            CVDetailController2 cvDetailController = loader.getController();
            cvDetailController.populateForm(cv);
            cvDetailController.setController(this);

            Scene cvDetailScene = new Scene(splitPane);
            Stage cvDetailStage = new Stage();
            cvDetailStage.setScene(cvDetailScene);
            cvDetailStage.setResizable(false);
            cvDetailStage.sizeToScene();
            cvDetailStage.show();
            cvDetailController.setStage(cvDetailStage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showConfirmDeleteCVPopup(CV selectedCV) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(POPUP_DELETE_CONFIRMATION_TITLE);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete this CV?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            deleteCV(selectedCV);
            populateCVTable(allCVData);
        }
    }

    private void deleteCV(CV cv) {
        LogicController.getInstance().deleteCV(cv.getId());
        allCVData.remove(cv);
        populateCVTable(allCVData);
    }

    private void populateCVTable(List<CV> cvList) {
        displayedCVData = FXCollections.observableList(cvList);
        cvTable.setItems(displayedCVData);
    }
}
