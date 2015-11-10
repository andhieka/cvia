package cvia.ui;/**
 * Created by Michael Limantara on 11/2/2015.
 */

import cvia.model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CviaApp extends Application {
    private static final String APPLICATION_NAME = "CVIA";

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

        initializeMainLayout();

        CV seedCV = new CV();
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setName("Michael");
        personalInfo.setContactNumber("83677234");
        personalInfo.setEmail("mike.bdg@gmail.com");
        personalInfo.setAddress("");
        seedCV.setPersonalInfo(personalInfo);
        List<EducationInfo> educationInfoList = new ArrayList<EducationInfo>();
        EducationInfo educationInfo = new EducationInfo();
        educationInfo.setEducationLevel(EducationInfo.EducationLevel.UNDERGRADUATE);
        educationInfo.setInstitutionName("National University of Singapore");
        educationInfo.setMajor("Computer Science");
        Grade grade = new Grade();
        grade.setGrade(4.7f);
        grade.setMaxGrade(5.0f);
        educationInfo.setGrade(grade);
        educationInfo.setStartDate(LocalDate.now());
        educationInfo.setEndDate(LocalDate.now());
        educationInfoList.add(educationInfo);
        seedCV.setEducationInfoList(educationInfoList);
        List<WorkExperience> workExperienceList = new ArrayList<WorkExperience>();
        WorkExperience workExperience = new WorkExperience();
        workExperience.setCompany("Company 1");
        workExperience.setPosition("Position 1");
        workExperience.setStartDate(LocalDate.now());
        workExperience.setEndDate(LocalDate.now());
        workExperience.setDescription("");
        workExperienceList.add(workExperience);
        workExperienceList.add(workExperience);
        seedCV.setWorkExperienceList(workExperienceList);
        List<Skill> skillList = new ArrayList<Skill>();
        skillList.add(new Skill("Javascript"));
        skillList.add(new Skill("Ruby"));
        skillList.add(new Skill("Java"));
        seedCV.setSkills(skillList);
        List<Language> languageList = new ArrayList<Language>();
        languageList.add(new Language("English"));
        languageList.add(new Language("Indonesian"));
        seedCV.setLanguages(languageList);

        logicController.saveCV(seedCV);
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
