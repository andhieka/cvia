package cvia.ui;

import cvia.analyzer.Report;
import cvia.model.*;
import cvia.ui.JobDescriptionDetailController.JDDetailMode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Michael Limantara on 11/5/2015.
 */
public class JobDescriptionListController {
    private static final Double JOB_DESCRIPTION_Y_OFFSET = 100.0;
    private static final Double JOB_DESCRIPTION_X_SPACING = 50.0;
    private static final Double JOB_DESCRIPTION_Y_SPACING = 40.0;
    private static final Double JOB_DESCRIPTION_WIDTH = 200.0;
    private static final Double JOB_DESCRIPTION_HEIGHT = 115.0;
    private static final Double JOB_TITLE_WIDTH  = 170.0;
    private static final Double JOB_TITLE_HEIGHT = 40.0;
    private static final Double JOB_TITLE_X_OFFSET = 15.0;
    private static final Double JOB_TITLE_Y_OFFSET = 5.0;
    private static final Double JOB_VACANCY_WIDTH = 200.0;
    private static final Double JOB_VACANCY_HEIGHT = 25.0;
    private static final Double JOB_VACANCY_Y_OFFSET = 50.0;
    private static final Double JOB_ACTION_BUTTON_WIDTH = 60.0;
    private static final Double JOB_ACTION_BUTTON_HEIGHT = 30.0;
    private static final Double JOB_ACTION_BUTTON_SPACING = 5.0;
    private static final Double JOB_ACTION_BUTTON_Y_OFFSET = 80.0;
    private static final String POPUP_DELETE_CONFIRMATION_TITLE = "Delete Confirmation";

    @FXML
    private Pane pane;
    @FXML
    private HBox searchBox;
    @FXML
    private TextField searchTextField;

    private LogicController logicController;

    private List<JobDescription> allJDData;
    private List<JobDescription> displayedJDData;
    private List<Pane> jobItemPanes = new ArrayList<Pane>();
    private Button btnAddJD;

    public void setJDData(List<JobDescription> jdData) {
        this.allJDData = jdData;
    }

    public void addJD(JobDescription jd) {
        allJDData.add(jd);
    }

    public void refresh() {
        refreshData(allJDData);
    }

    private void refreshData(List<JobDescription> jobList) {
        pane.getChildren().removeAll(jobItemPanes);
        jobItemPanes.clear();
        setDisplayedJDData(jobList);
        setUpJobDescriptionView();
    }

    @FXML
    private void initialize() {
        logicController = LogicController.getInstance();
        loadInitialData();
        setUpButton();
        setUpSearchBox();
        setUpJobDescriptionView();
    }

    private void loadInitialData() {
        allJDData = logicController.listJobDescription();
        refreshData(allJDData);
    }

    private void setUpButton() {
        btnAddJD = new Button();
        btnAddJD.setLayoutX(50);
        btnAddJD.setLayoutY(42);
        ImageView imageView = new ImageView("/add.png");
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        btnAddJD.setGraphic(imageView);
        btnAddJD.setText("Create New Job");
        btnAddJD.setStyle("-fx-background-color: gold; -fx-font-size: 14");

        btnAddJD.setOnAction(event -> {
            addJobDescription();
        });

        pane.getChildren().add(btnAddJD);
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
        List<JobDescription> searchResult = new ArrayList<JobDescription>();
        for (JobDescription jobDescription: allJDData) {
            if (jobDescription.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                searchResult.add(jobDescription);
            }
        }

        refreshData(searchResult);
    }

    private void setDisplayedJDData(List<JobDescription> jobList) {
        displayedJDData = jobList;
    }

    private void setUpJobDescriptionView() {
        int colIndex = 0;
        int rowIndex = 0;
        for (JobDescription jobDescription: displayedJDData) {
            Pane jdItem = createJobDescriptionItemView(jobDescription, rowIndex, colIndex);
            jdItem.setLayoutX(JOB_DESCRIPTION_X_SPACING +
                    colIndex * (JOB_DESCRIPTION_WIDTH + JOB_DESCRIPTION_X_SPACING));
            jdItem.setLayoutY(JOB_DESCRIPTION_Y_OFFSET + rowIndex * (JOB_DESCRIPTION_Y_SPACING + JOB_DESCRIPTION_HEIGHT));
            colIndex += 1;
            if (colIndex >= 3) {
                colIndex = 0;
                rowIndex += 1;
            }

            pane.getChildren().add(jdItem);
            jobItemPanes.add(jdItem);
        }
    }

    private Pane createJobDescriptionItemView(JobDescription jd, Integer rowIndex, Integer colIndex) {
        Pane jdItemPane = new Pane();
        jdItemPane.setPrefSize(JOB_DESCRIPTION_WIDTH, JOB_DESCRIPTION_HEIGHT);
        jdItemPane.setStyle("-fx-background-color: #6698c8");
        jdItemPane.setEffect(new DropShadow());

        Label jobTitleLabel = new Label(jd.getTitle());
        jobTitleLabel.setMaxSize(JOB_TITLE_WIDTH, Control.USE_COMPUTED_SIZE);
        jobTitleLabel.setPrefSize(JOB_TITLE_WIDTH, JOB_TITLE_HEIGHT);
        jobTitleLabel.setLayoutX(JOB_TITLE_X_OFFSET);
        jobTitleLabel.setLayoutY(JOB_TITLE_Y_OFFSET);
        jobTitleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-wrap-text: true;" +
                "-fx-alignment: center; -fx-text-alignment: center; -fx-content-display: center");

        Label jobVacancyLabel = new Label("Vacancies: " + jd.getVacancy());
        jobVacancyLabel.setPrefSize(JOB_VACANCY_WIDTH, JOB_VACANCY_HEIGHT);
        jobVacancyLabel.setLayoutY(JOB_VACANCY_Y_OFFSET);
        jobVacancyLabel.setAlignment(Pos.CENTER);

        //TODO: Style button
        Button viewBtn = new Button("View");
        viewBtn.setPrefSize(JOB_ACTION_BUTTON_WIDTH, JOB_ACTION_BUTTON_HEIGHT);
        viewBtn.setLayoutX(JOB_ACTION_BUTTON_SPACING);
        viewBtn.setLayoutY(JOB_ACTION_BUTTON_Y_OFFSET);
        viewBtn.setOnAction(event ->  {
            List<CV> cvList = getAllCV();
            JobDescription selectedJobDescription = getJobDescriptionFromPosition(rowIndex, colIndex);
            List<Report> reportList = logicController.processMatchingAndGenerateReport(cvList, selectedJobDescription);
//            List<Report> reportList = new ArrayList<Report>();
//            Report report = new Report(cvList.get(0).getPersonalInfo());
//            report.setEducationScore(100);
//            report.setLanguageScore(100);
//            report.setSkillScore(100);
//            report.setWorkScore(100);
//            report.setScore(100);
//            report.setJobDescription(selectedJobDescription);
//            report.setParsedCV(cvList.get(0));
//            reportList.add(report);
            showReportLayout(reportList);
        });

        Button editBtn = new Button("Edit");
        editBtn.setPrefSize(JOB_ACTION_BUTTON_WIDTH, JOB_ACTION_BUTTON_HEIGHT);
        editBtn.setLayoutX(2 * JOB_ACTION_BUTTON_SPACING + JOB_ACTION_BUTTON_WIDTH);
        editBtn.setLayoutY(JOB_ACTION_BUTTON_Y_OFFSET);
        editBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                JobDescription selectedJobDescription = getJobDescriptionFromPosition(rowIndex, colIndex);
                editJobDescription(selectedJobDescription);
            }
        });

        Button deleteBtn = new Button("Delete");
        deleteBtn.setPrefSize(JOB_ACTION_BUTTON_WIDTH, JOB_ACTION_BUTTON_HEIGHT);
        deleteBtn.setLayoutX(3 * JOB_ACTION_BUTTON_SPACING + 2 * JOB_ACTION_BUTTON_WIDTH);
        deleteBtn.setLayoutY(JOB_ACTION_BUTTON_Y_OFFSET);
        deleteBtn.setOnAction(event -> showConfirmDeleteJDPopup(jd));

        jdItemPane.getChildren().addAll(jobTitleLabel, jobVacancyLabel, viewBtn, editBtn, deleteBtn);
        return jdItemPane;
    }

    private List<CV> getAllCV() {
        return logicController.listCV();
    }

    private void showReportLayout(List<Report> reportList) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CviaApp.class.getResource("/Report.fxml"));
            Pane pane = loader.load();
            ReportController reportController = loader.getController();
            reportController.populateData(reportList);

            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private JobDescription getJobDescriptionFromPosition(Integer row, Integer col) {
        return displayedJDData.get(row * 3 + col);
    }

    private void addJobDescription() {
        showJobDescriptionDetailForm(null, JDDetailMode.ADD);
    }

    private void editJobDescription(JobDescription jobDescription) {
        showJobDescriptionDetailForm(jobDescription, JDDetailMode.EDIT);
    }

    private void showJobDescriptionDetailForm(JobDescription jobDescription, JDDetailMode mode) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CviaApp.class.getResource("/JobDescriptionDetail.fxml"));
            ScrollPane scrollPane = loader.load();
            JobDescriptionDetailController jdDetailController = loader.getController();
            jdDetailController.generateForm(jobDescription, mode);

            Stage jdDetailStage = new Stage();
            Scene scene = new Scene(scrollPane);
            jdDetailStage.setScene(scene);
            jdDetailStage.sizeToScene();
            jdDetailStage.setResizable(false);
            jdDetailStage.show();
            jdDetailController.setStage(jdDetailStage);
            jdDetailController.setJdListController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showConfirmDeleteJDPopup(JobDescription jobDescription) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(POPUP_DELETE_CONFIRMATION_TITLE);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete this job description?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            deleteJD(jobDescription);
        }
    }

    private void deleteJD(JobDescription jobDescription) {
        LogicController.getInstance().deleteJD(jobDescription.getId());
        allJDData.remove(jobDescription);
        refreshData(allJDData);
    }
}
