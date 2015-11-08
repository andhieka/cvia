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
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
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

    private ImageButton btnAddCv;

    @FXML
    private TableView<CV> cvTable;
    @FXML
    private TableColumn columnNo;
    @FXML
    private TableColumn<CV, String> columnName;
    @FXML
    private TableColumn<CV, String> columnEmail;
    @FXML
    private TableColumn<CV, String> columnCurrentTitle;
    @FXML
    private TableColumn columnAction;

    private ObservableList<CV> displayedCVData;
    private List<CV> allCVData;

    public void setCVData(List<CV> allCVData) {
        this.allCVData = allCVData;
    }

    @FXML
    private void initialize() {
        PersonalInfo user1 = new PersonalInfo();
        user1.setName("user 2");
        user1.setContactNumber("+658123456");
        user1.setEmail("user1@testing.com");
        CV cv1 = new CV();
        cv1.setId(1L);
        cv1.setPersonalInfo(user1);

        PersonalInfo user2 = new PersonalInfo();
        user2.setName("user 1");
        user2.setContactNumber("+658123456");
        user2.setEmail("user2@testing.com");
        CV cv2 = new CV();
        cv2.setId(2L);
        cv2.setPersonalInfo(user2);

        List<CV> sampleData = new ArrayList<CV>();
        sampleData.add(cv1);
        sampleData.add(cv2);

        setUpBtnAddCv();
        setUpSearchBox();
        setUpCVTable();
        setCVData(sampleData);
        populateCVTable(sampleData);
    }

    private void setUpBtnAddCv() {
        btnAddCv = new ImageButton("/add.png", SIZE_ADD_CV_BUTTON);
        btnAddCv.setLayoutX(30);
        btnAddCv.setLayoutY(40);
        btnAddCv.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Import new CV");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
                List<File> filesSelected = fileChooser.showOpenMultipleDialog(btnAddCv.getScene().getWindow());
                // TODO: Process uploaded CVs (files)
                try {
                    Desktop.getDesktop().open(filesSelected.get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                            setText(getTableRow().getIndex() +  1 + "");
                        }
                    }
                };
            }
        });
        columnNo.setStyle("-fx-alignment: CENTER");
        columnName.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getPersonalInfo().getName()));
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
                            final ImageButton editBtn = new ImageButton("/add.png", SIZE_EDIT_CV_BUTTON);
                            final ImageButton deleteBtn = new ImageButton("/add.png", SIZE_DELETE_CV_BUTTON);
                            hbox.setSpacing(5);
                            hbox.setAlignment(Pos.CENTER);
                            hbox.getChildren().addAll(editBtn, deleteBtn);

                            editBtn.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    CV selectedCV = (CV) getTableView().getItems().get(getIndex());
                                    editCV(selectedCV);
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
            CVDetailController cvDetailController = loader.getController();
            cvDetailController.setCV(cv);

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
