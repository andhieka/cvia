package cvia.ui;

import cvia.analyzer.Report;
import cvia.model.CV;
import cvia.model.JobDescription;
import cvia.model.PersonalInfo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Michael Limantara on 11/10/2015.
 */
public class ReportController {
    private static final Double BTN_VIEW_SIZE = 15.0;

    @FXML
    TableView<Report> tableScore;
    @FXML
    TableColumn<Report, String> columnName;
    @FXML
    TableColumn<Report, String> columnEducationScore;
    @FXML
    TableColumn<Report, String> columnWorkScore;
    @FXML
    TableColumn<Report, String> columnSkillScore;
    @FXML
    TableColumn<Report, String> columnLanguageScore;
    @FXML
    TableColumn<Report, String> columnTotalScore;
    @FXML
    TableColumn columnView;

    private List<Report> reportList;
    private JobDescription jobDescription;

    public void populateData(List<Report> reportList) {
        this.reportList = reportList;
        Collections.sort(reportList);
        tableScore.setItems(FXCollections.observableList(reportList));
    }

    @FXML
    private void initialize() {
        setUpTableView();
    }

    private void setUpTableView() {
        tableScore.setFixedCellSize(25);
        columnName.setStyle("-fx-alignment: CENTER");
        columnName.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getBiodata().getName()));
        columnEducationScore.setStyle("-fx-alignment: CENTER");
        columnEducationScore.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getEducationScore() + ""));
        columnWorkScore.setStyle("-fx-alignment: CENTER");
        columnWorkScore.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getWorkScore() + ""));
        columnSkillScore.setStyle("-fx-alignment: CENTER");
        columnSkillScore.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getSkillScore() + ""));
        columnLanguageScore.setStyle("-fx-alignment: CENTER");
        columnLanguageScore.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getLanguageScore() + ""));
        columnTotalScore.setStyle("-fx-alignment: CENTER");
        columnTotalScore.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getScore() + ""));
        columnView.setStyle("-fx-alignment: CENTER");
        columnView.setCellFactory(new Callback<TableColumn, TableCell>() {
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
                            ImageButton btnViewCV = new ImageButton("/search.png", BTN_VIEW_SIZE);
                            btnViewCV.setOnAction(event -> {
                                Report selectedReport = (Report) getTableView().getItems().get(getIndex());
                                CV selectedCV = selectedReport.getParsedCV();
                                showCVDetailLayout(selectedCV);

                            });

                            setGraphic(btnViewCV);
                            setText(null);
                        }
                    }
                };

                return cell;
            }
        });
    }

    private void showCVDetailLayout(CV cv) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CviaApp.class.getResource("/CVDetail.fxml"));
            SplitPane splitPane = loader.load();
            CVDetailController2 cvDetailController = loader.getController();
            cvDetailController.populateForm(cv);
            cvDetailController.setController(null);

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
}
