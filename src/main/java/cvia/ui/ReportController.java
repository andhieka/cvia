package cvia.ui;

import cvia.analyzer.Report;
import cvia.model.PersonalInfo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.List;

/**
 * Created by Michael Limantara on 11/10/2015.
 */
public class ReportController {
    private static final Double BTN_VIEW_SIZE = 25.0;

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

    public void populateData(List<Report> reportList) {
        this.reportList = reportList;
        tableScore.setItems(FXCollections.observableList(reportList));
    }

    @FXML
    private void initialize() {
        setUpTableView();
    }

    private void setUpTableView() {
        tableScore.setFixedCellSize(25);
        columnName.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getBiodata().getName()));
        columnEducationScore.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getEducationScore() + ""));
        columnWorkScore.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getWorkScore() + ""));
        columnSkillScore.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getSkillScore() + ""));
        columnLanguageScore.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getLanguageScore() + ""));
        columnTotalScore.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getScore() + ""));
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
                                System.out.println("asdf");
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

}
