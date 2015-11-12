package cvia.ui;

import cvia.model.*;
import cvia.model.EducationInfo.EducationLevel;
import cvia.model.Skill.SkillProficiency;
import cvia.model.Language.LanguageProficiency;
import cvia.utilities.UIPair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Michael Limantara on 11/9/2015.
 */
public class JobDescriptionDetailController {
    public enum JDDetailMode {
        ADD, EDIT
    }

    class InvalidJDException extends Exception {
        public InvalidJDException(String string) {
            super(string);
        }
        public InvalidJDException() {

        }
    }

    private static final Double FORM_TITLE_OFFSET_TOP = 10.0;
    private static final Double FORM_TITLE_HEIGHT = 30.0;
    private static final Double BTN_SAVE_WIDTH = 100.0;
    private static final Double BTN_SAVE_HEIGHT = 30.0;
    private static final String BTN_SAVE_STYLE = "-fx-font-size: 13px; -fx-font-weight: bold";
    private static final Double FIELD_HEIGHT = 25.0;
    private static final String HEADER_LABEL_STYLE = "-fx-font-size: 13px; -fx-font-weight: bold";
    private static final Double HEADER_LABEL_OFFSET_TOP = 10.0;
    private static final Double HEADER_LABEL_OFFSET_LEFT = 10.0;
    private static final Double HEADER_LABEL_SPACING = 3.0;
    private static final String FIELD_LABEL_STYLE = "-fx-font-size: 14px";
    private static final Double FIELD_LABEL_OFFSET_LEFT = 20.0;
    private static final Double FIELD_LABEL_SPACING = 2.0;
    private static final Double FIELD_OFFSET_LEFT = 20.0;
    private static final Double FIELD_WIDTH_GENERAL = 140.0;
    private static final Double FIELD_SPACING_IN_A_ROW = 10.0;
    private static final Double FIELD_SPACING = 5.0;
    private static final Double OFFSET_BOT = 30.0;
    private static final Double ADD_ICON_SIZE = 15.0;
    private static final String ADD_ICON_PATH = "/add.png";

    private Stage jdDetailStage;
    private JobDescriptionListController jdListController;

    @FXML
    private AnchorPane formPane;

    private Pane generalPane = new Pane();
    private Pane educationPane = new Pane();
    private Pane workPane = new Pane();
    private Pane skillPane = new Pane();
    private Pane languagePane = new Pane();
    private Pane weightagePane = new Pane();

    private JDDetailMode mode;
    private JobDescription jobDescription;
    private Label formTitle;
    private Button btnSave;
    private TextField txtTitle;
    private TextField txtVacancy;
    private ComboBox minimumEducationLevelComboBox;
    private TextField txtMajor;
    private TextField txtGrade;
    private TextField txtYearsOfExp;
    private TextField txtKeyword;
    private List<TextField> txtResponsibilityList = new ArrayList<TextField>();
    private List<UIPair> txtSkillList = new ArrayList<UIPair>();
    private List<UIPair> txtLanguageList = new ArrayList<UIPair>();
    private List<TextField> txtWeightageList = new ArrayList<TextField>();
    private ImageButton btnAddResponsibility;
    private ImageButton btnAddSkill;
    private ImageButton btnAddLanguage;
    private DecimalFormat numberOfYearsFormat = new DecimalFormat("#.#");

    private Double xGeneral = 0.0;
    private Double yGeneral = HEADER_LABEL_OFFSET_TOP + FORM_TITLE_OFFSET_TOP + FORM_TITLE_HEIGHT;
    private Double xEducation = 0.0;
    private Double yEducation = HEADER_LABEL_OFFSET_TOP;
    private Double xWork = 0.0;
    private Double yWork = HEADER_LABEL_OFFSET_TOP;
    private Double xSkill = 0.0;
    private Double ySkill = HEADER_LABEL_OFFSET_TOP;
    private Double xLanguage = 0.0;
    private Double yLanguage = HEADER_LABEL_OFFSET_TOP;
    private Double xWeightage = 0.0;
    private Double yWeightage = HEADER_LABEL_OFFSET_TOP;

    private ObservableList<String> minimumEducationOption;
    private ObservableList<String> skillLevelOption;
    private ObservableList<String> languageLevelOption;

    public void setStage(Stage stage) {
        this.jdDetailStage = stage;
    }

    public void setJdListController(JobDescriptionListController jdListController) {
        this.jdListController = jdListController;
    }

    @FXML
    private void initialize() {
        btnSave = new Button();
    }

    public void generateForm(JobDescription jobDescription, JDDetailMode mode) {
        this.jobDescription = jobDescription;
        this.mode = mode;
        
        setUpOptions();
        setFormTitle();
        setUpGeneralSection();
        setUpEducationSection();
        setUpWorkSection();
        setUpSkillSection();
        setUpLanguageSection();
        setUpWeightageSection();
        setSaveButton();
        populateForm();
        fitToContent();
    }

    private void setUpOptions() {
        List<String> educationOptions = new ArrayList<String>();
        educationOptions.add("");
        for (EducationLevel level: EducationLevel.values()) {
            String currentLevel = level.toString();
            currentLevel = currentLevel.substring(0,1).toUpperCase() + currentLevel.substring(1).toLowerCase();
            educationOptions.add(currentLevel);
        }
        minimumEducationOption = FXCollections.observableList(educationOptions);

        List<String> skillOptions = new ArrayList<String>();
        skillOptions.add("");
        for (SkillProficiency level: SkillProficiency.values()) {
            String currentLevel = level.toString();
            currentLevel = currentLevel.substring(0,1).toUpperCase() + currentLevel.substring(1).toLowerCase();
            skillOptions.add(currentLevel);
        }
        skillLevelOption = FXCollections.observableList(skillOptions);

        List<String> languageOption = new ArrayList<String>();
        languageOption.add("");
        for (LanguageProficiency level: LanguageProficiency.values()) {
            String currentLevel = level.toString();
            currentLevel = currentLevel.substring(0,1).toUpperCase() + currentLevel.substring(1).toLowerCase();
            languageOption.add(currentLevel);
        }
        languageLevelOption = FXCollections.observableList(languageOption);
    }

    private void setFormTitle() {
        formTitle = new Label();
        formTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold");
        formTitle.setPrefHeight(FORM_TITLE_HEIGHT);
        formTitle.setLayoutX(HEADER_LABEL_OFFSET_LEFT);
        formTitle.setLayoutY(FORM_TITLE_OFFSET_TOP);
        if (mode == JDDetailMode.ADD) {
            formTitle.setText("Add Job Description");
        } else if (mode == JDDetailMode.EDIT) {
            formTitle.setText("Edit Job Description");
        }
        formPane.getChildren().add(formTitle);
    }

    private void setSaveButton() {
        btnSave.setText("Save");
        btnSave.setStyle("-fx-background-color: lightgreen;-fx-font-size: 14");
        btnSave.prefWidth(BTN_SAVE_WIDTH);
        btnSave.prefHeight(BTN_SAVE_HEIGHT);
        btnSave.setLayoutX(150);
        btnSave.setLayoutY(yGeneral + yEducation + yWork + ySkill + yLanguage + yWeightage + OFFSET_BOT);
        
        btnSave.setOnAction(event -> {
            saveJobDescription();
            jdDetailStage.close();
        });
        
        formPane.getChildren().add(btnSave);
    }

    private void saveJobDescription() {
        try {
            constructJDFromForm();
        } catch (InvalidJDException e) {
            System.out.println(e.getMessage());
        }

        if (mode == JDDetailMode.ADD) {
            JobDescription addedJd = LogicController.getInstance().addJD(jobDescription);
            jdListController.addJD(addedJd);
            jdListController.refresh();
        } else if (mode == JDDetailMode.EDIT) {
            LogicController.getInstance().editJD(jobDescription.getId(), jobDescription);
            jdListController.refresh();
        }
    }

    private void fitToContent() {
        formPane.setPrefHeight(yGeneral + yEducation + yWork + ySkill + yLanguage +
                yWeightage + BTN_SAVE_HEIGHT + 2 * OFFSET_BOT);
    }

    private void setUpGeneralSection() {
        Label generalHeaderLabel = createHeaderLabelAtPosition(HEADER_LABEL_OFFSET_LEFT, yGeneral, "General");
        yGeneral += (FIELD_HEIGHT + HEADER_LABEL_SPACING);
        Label titleFieldLabel = createFieldLabelAtPosition(FIELD_LABEL_OFFSET_LEFT, yGeneral, "Title");
        titleFieldLabel.setPrefWidth(240);
        xGeneral += FIELD_OFFSET_LEFT + 240 + FIELD_SPACING_IN_A_ROW;
        Label vacancyFieldLabel = createFieldLabelAtPosition(xGeneral, yGeneral, "Vacancy");
        yGeneral += (FIELD_HEIGHT + FIELD_LABEL_SPACING);
        txtTitle = new TextField();
        txtTitle.setLayoutX(FIELD_OFFSET_LEFT);
        txtTitle.setLayoutY(yGeneral);
        txtTitle.setPrefWidth(240);
        xGeneral = (FIELD_OFFSET_LEFT + 240 + FIELD_SPACING_IN_A_ROW);
        txtVacancy = new TextField();
        txtVacancy.setLayoutX(xGeneral);
        txtVacancy.setLayoutY(yGeneral);
        txtVacancy.setPrefWidth(50);
        yGeneral += (FIELD_HEIGHT + FIELD_SPACING);
        Label responsibilityFieldLabel = createFieldLabelAtPosition(FIELD_LABEL_OFFSET_LEFT, yGeneral, "Responsibilities");
        btnAddResponsibility = createAddButtonAtPosition(125.0, yGeneral);
        yGeneral += (FIELD_HEIGHT + FIELD_LABEL_SPACING);

        addResponsibility();

        btnAddResponsibility.setOnAction(event -> {
            addResponsibility();
        });

        generalPane.getChildren().addAll(generalHeaderLabel, titleFieldLabel, vacancyFieldLabel, txtTitle, txtVacancy,
                responsibilityFieldLabel, btnAddResponsibility);
        formPane.getChildren().add(generalPane);
    }

    private void setUpEducationSection() {
        Label educationHeaderLabel = createHeaderLabelAtPosition(HEADER_LABEL_OFFSET_LEFT,
                yEducation, "Education Requirements");
        yEducation += (FIELD_HEIGHT + HEADER_LABEL_SPACING);
        Label minimumEducationFieldLabel = createFieldLabelAtPosition(FIELD_LABEL_OFFSET_LEFT, yEducation,
                "Minimum Education");
        minimumEducationFieldLabel.setPrefWidth(145);
        xEducation += FIELD_OFFSET_LEFT + 145 + FIELD_SPACING_IN_A_ROW;
        Label majorFieldLabel = createFieldLabelAtPosition(xEducation, yEducation, "Accepted Majors");
        yEducation += (FIELD_HEIGHT + FIELD_LABEL_SPACING);

        minimumEducationLevelComboBox = createComboBoxAtPosition(FIELD_OFFSET_LEFT, yEducation,
                minimumEducationOption);
        minimumEducationLevelComboBox.setPrefWidth(145);
        xEducation = (FIELD_OFFSET_LEFT + 145 + FIELD_SPACING_IN_A_ROW);
        txtMajor = new TextField();
        txtMajor.setPrefWidth(145);
        txtMajor.setPrefHeight(FIELD_HEIGHT);
        txtMajor.setLayoutX(xEducation);
        txtMajor.setLayoutY(yEducation);
        yEducation += (FIELD_HEIGHT + FIELD_SPACING);

        Label gradeFieldLabel = createFieldLabelAtPosition(FIELD_LABEL_OFFSET_LEFT, yEducation, "Min Grade");
        gradeFieldLabel.setPrefWidth(100);
        yEducation += (FIELD_HEIGHT + FIELD_LABEL_SPACING);
        txtGrade = new TextField();
        txtGrade.setPrefWidth(100);
        txtGrade.setLayoutX(FIELD_OFFSET_LEFT);
        txtGrade.setLayoutY(yEducation);
        yEducation += (FIELD_HEIGHT + FIELD_SPACING);

        educationPane.getChildren().addAll(educationHeaderLabel, minimumEducationFieldLabel, majorFieldLabel,
                minimumEducationLevelComboBox, txtMajor, gradeFieldLabel, txtGrade);
        educationPane.setLayoutY(yGeneral);

        formPane.getChildren().add(educationPane);
    }

    private void setUpWorkSection() {
        Label workHeaderLabel = createHeaderLabelAtPosition(HEADER_LABEL_OFFSET_LEFT,
                yWork, "Work Requirements");
        yWork += (FIELD_HEIGHT + HEADER_LABEL_SPACING);
        Label yearsOfExperienceFieldLabel = createFieldLabelAtPosition(FIELD_LABEL_OFFSET_LEFT, yWork,
                "Years of Exp.");
        yearsOfExperienceFieldLabel.setPrefWidth(145);
        xWork += FIELD_OFFSET_LEFT + 145 + FIELD_SPACING_IN_A_ROW;
        Label keywordFieldLabel = createFieldLabelAtPosition(xEducation, yWork, "Relevant Keywords");
        yWork += (FIELD_HEIGHT + FIELD_LABEL_SPACING);
        txtYearsOfExp = new TextField();
        txtYearsOfExp.setLayoutX(FIELD_OFFSET_LEFT);
        txtYearsOfExp.setLayoutY(yWork);
        txtYearsOfExp.setPrefWidth(145);
        xWork = (FIELD_OFFSET_LEFT + 145 + FIELD_SPACING_IN_A_ROW);
        txtKeyword = new TextField();
        txtKeyword.setLayoutX(xWork);
        txtKeyword.setLayoutY(yWork);
        txtKeyword.setPrefWidth(145);
        yWork += (FIELD_HEIGHT + FIELD_SPACING);

        workPane.getChildren().addAll(workHeaderLabel, yearsOfExperienceFieldLabel, keywordFieldLabel,
                txtYearsOfExp, txtKeyword);
        workPane.setLayoutY(yGeneral + yEducation);

        formPane.getChildren().add(workPane);

    }

    private void setUpSkillSection() {
        Label skillHeaderLabel = createHeaderLabelAtPosition(HEADER_LABEL_OFFSET_LEFT, ySkill, "Skill Requirements");
        ImageButton btnAddSkills = createAddButtonAtPosition(135.0, ySkill);
        ySkill += (FIELD_HEIGHT + HEADER_LABEL_SPACING);
        Label skillNameFieldLabel = createFieldLabelAtPosition(FIELD_LABEL_OFFSET_LEFT, ySkill, "Skill Name");
        xSkill = (FIELD_LABEL_OFFSET_LEFT + FIELD_WIDTH_GENERAL + FIELD_SPACING_IN_A_ROW);
        Label skillLevelFieldLabel = createFieldLabelAtPosition(xSkill, ySkill, "Proficiency Level");
        ySkill += (FIELD_HEIGHT + FIELD_LABEL_SPACING);

        addSkill();

        btnAddSkills.setOnAction(event -> {
            addSkill();
        });

        skillPane.getChildren().addAll(skillHeaderLabel, btnAddSkills, skillNameFieldLabel, skillLevelFieldLabel);
        skillPane.setLayoutY(yGeneral + yEducation + yWork);
        formPane.getChildren().add(skillPane);
    }

    private void setUpLanguageSection() {
        Label languageHeaderLabel = createHeaderLabelAtPosition(HEADER_LABEL_OFFSET_LEFT, yLanguage, "Language Requirements");
        btnAddLanguage = createAddButtonAtPosition(165.0, yLanguage);
        yLanguage += (FIELD_HEIGHT + HEADER_LABEL_SPACING);
        Label languageNameFieldLabel = createFieldLabelAtPosition(FIELD_LABEL_OFFSET_LEFT, yLanguage, "Language");
        xLanguage = (FIELD_LABEL_OFFSET_LEFT + FIELD_WIDTH_GENERAL + FIELD_SPACING_IN_A_ROW);
        Label languageLevelFieldLabel = createFieldLabelAtPosition(xLanguage, yLanguage, "Proficiency Level");
        yLanguage += (FIELD_HEIGHT + FIELD_LABEL_SPACING);

        addLanguage();

        btnAddLanguage.setOnAction(event -> {
            addLanguage();
        });

        languagePane.getChildren().addAll(languageHeaderLabel, btnAddLanguage, languageNameFieldLabel, languageLevelFieldLabel);
        languagePane.setLayoutY(yGeneral + yEducation + yWork + ySkill);
        formPane.getChildren().add(languagePane);
    }

    private void setUpWeightageSection() {
        String[] fieldLabels = {"Education", "Work", "Skill", "Language"};
        Label weightageHeaderLabel = createHeaderLabelAtPosition(HEADER_LABEL_OFFSET_LEFT, yWeightage, "Weightage");
        yWeightage += (FIELD_HEIGHT + HEADER_LABEL_SPACING);
        for (int i = 0; i < fieldLabels.length; i++) {
            Label weightageFieldLabel = createFieldLabelAtPosition(FIELD_LABEL_OFFSET_LEFT, yWeightage, fieldLabels[i]);
            weightageFieldLabel.setPrefWidth(150);
            TextField textField = new TextField();
            textField.setPrefWidth(30);
            textField.setPrefHeight(FIELD_HEIGHT);
            textField.setLayoutX(150 + FIELD_SPACING_IN_A_ROW);
            textField.setLayoutY(yWeightage);
            txtWeightageList.add(textField);
            weightagePane.getChildren().addAll(weightageFieldLabel, textField);
            yWeightage += (FIELD_HEIGHT + HEADER_LABEL_SPACING);
        }

        weightagePane.getChildren().addAll(weightageHeaderLabel);
        weightagePane.setLayoutY(yGeneral + yEducation + yWork + ySkill + yLanguage);

        formPane.getChildren().add(weightagePane);
    }

    private void populateForm() {
        if (mode == JDDetailMode.ADD || jobDescription == null) {
            return;
        }

        txtTitle.setText(jobDescription.getTitle());
        txtVacancy.setText(jobDescription.getVacancy() + "");
        List<String> responsibilities = jobDescription.getResponsibilities();
        for (int i = 0; i < responsibilities.size(); i++) {
            txtResponsibilityList.get(i).setText(responsibilities.get(i));
            addResponsibility();
        }

        EducationRequirement educationRequirement = jobDescription.getMinimumEducation();
        if (educationRequirement != null &&
                educationRequirement.getMinimumEducation() != null) {
            String minimumLevel = educationRequirement.getMinimumEducation().toString();
            minimumLevel = minimumLevel.substring(0,1).toUpperCase() + minimumLevel.substring(1).toLowerCase();
            minimumEducationLevelComboBox.setValue(minimumLevel);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jobDescription.getMinimumEducation().getAcceptedMajors().size(); i++) {
            if (i == jobDescription.getMinimumEducation().getAcceptedMajors().size() - 1) {
                sb.append(jobDescription.getMinimumEducation().getAcceptedMajors().get(i));
            } else {
                sb.append(jobDescription.getMinimumEducation().getAcceptedMajors().get(i) + ", ");
            }
        }
        txtMajor.setText(sb.toString());
        Grade grade = jobDescription.getMinimumEducation().getMinimumGrade();
        if (grade != null) {
            String gradeString = jobDescription.getMinimumEducation().getMinimumGrade().getGrade() + "/" +
                    jobDescription.getMinimumEducation().getMinimumGrade().getMaxGrade();
            txtGrade.setText(gradeString);
        }

        Double expInYear = jobDescription.getWorkRequirement().getDuration() / 12.0;
        txtYearsOfExp.setText(numberOfYearsFormat.format(expInYear));
        sb = new StringBuilder();
        for (int i = 0; i < jobDescription.getWorkRequirement().getKeywords().size(); i++) {
            if (i == jobDescription.getWorkRequirement().getKeywords().size() - 1) {
                sb.append(jobDescription.getWorkRequirement().getKeywords().get(i));
            } else {
                sb.append(jobDescription.getWorkRequirement().getKeywords().get(i) + ", ");
            }
        }
        txtKeyword.setText(sb.toString());
        List<Skill> skillList = jobDescription.getRequiredSkills();
        for (int i = 0; i < skillList.size(); i++) {
            txtSkillList.get(i).getTextField().setText(skillList.get(i).getName());
            txtSkillList.get(i).getComboBox().setValue(skillList.get(i).getProficiencyLevel().toString());
            addSkill();
        }
        List<Language> languageList = jobDescription.getRequiredLanguages();
        for (int i = 0; i < languageList.size(); i++) {
            txtLanguageList.get(i).getTextField().setText(languageList.get(i).getName());
            txtLanguageList.get(i).getComboBox().setValue(languageList.get(i).getProficiencyLevel().toString());
            addLanguage();
        }

        for (int i = 0; i < txtWeightageList.size(); i++) {
            txtWeightageList.get(i).setText(jobDescription.getWeightage().get(i) + "");
        }
    }

    private void constructJDFromForm() throws InvalidJDException {
        if (txtTitle.getText().isEmpty()) {
            throw new InvalidJDException("Job description must have a title");
        }

        if (mode == JDDetailMode.ADD) {
            jobDescription = new JobDescription();
        }

        jobDescription.setTitle(txtTitle.getText());
        if (txtVacancy.getText().isEmpty()) {
            jobDescription.setVacancy(0);
        } else {
            jobDescription.setVacancy(Integer.parseInt(txtVacancy.getText()));
        }

        List<String> responsibilities = new ArrayList<String>();
        for (TextField txtResponsibility: txtResponsibilityList) {
            if (!txtResponsibility.getText().isEmpty()) {
                responsibilities.add(txtResponsibility.getText());
            }
        }
        jobDescription.setResponsibilities(responsibilities);

        EducationRequirement educationRequirement = new EducationRequirement();
        if (minimumEducationLevelComboBox.getValue() != null &&
                !minimumEducationLevelComboBox.getValue().toString().isEmpty()) {
            educationRequirement.setMinimumEducation(EducationLevel.valueOf(
                    minimumEducationLevelComboBox.getValue().toString().toUpperCase()));
        }
        if (!txtMajor.getText().isEmpty()) {
            List<String> majors = splitAndExtractParts(txtMajor.getText());
            educationRequirement.setAcceptedMajors(majors);
        } else {
            educationRequirement.setAcceptedMajors(new ArrayList<String>());
        }
        if (!txtGrade.getText().isEmpty()) {
            String[] gradeString = txtGrade.getText().split("/");
            System.out.println(gradeString[0]);
            System.out.println(gradeString[1]);
            Grade grade = new Grade();
            grade.setGrade(Float.parseFloat(gradeString[0].trim()));
            grade.setMaxGrade(Float.parseFloat(gradeString[1].trim()));
            educationRequirement.setMinimumGrade(grade);
        }
        jobDescription.setMinimumEducation(educationRequirement);

        WorkRequirement workRequirement = new WorkRequirement();
        workRequirement.setDuration(0);
        if (!txtYearsOfExp.getText().isEmpty()) {
            Integer expInYear = Integer.parseInt(txtYearsOfExp.getText());
            workRequirement.setDuration(expInYear * 12);
        }
        if (!txtKeyword.getText().isEmpty()) {
            List<String> keywords = splitAndExtractParts(txtKeyword.getText());
            workRequirement.setKeywords(keywords);
        } else {
            workRequirement.setKeywords(new ArrayList<String>());
        }
        jobDescription.setWorkRequirement(workRequirement);

        List<Skill> skillList = new ArrayList<Skill>();
        for (UIPair pair: txtSkillList) {
            TextField txtSkillName = pair.getTextField();
            ComboBox comboBoxSkillLevel = pair.getComboBox();
            if (!txtSkillName.getText().isEmpty()) {
                Skill skill = new Skill();
                skill.setName(txtSkillName.getText());
                // If skill proficiency unspecified, automatically becomes intermediate
                if (comboBoxSkillLevel.getValue().toString().isEmpty()) {
                    skill.setProficiencyLevel(SkillProficiency.INTERMEDIATE);
                } else {
                    skill.setProficiencyLevel(SkillProficiency.valueOf(
                            comboBoxSkillLevel.getValue().toString().toUpperCase()));
                }

                skillList.add(skill);
            }
        }
        jobDescription.setRequiredSkills(skillList);

        List<Language> languageList = new ArrayList<Language>();
        for (UIPair pair: txtLanguageList) {
            TextField txtLanguage = pair.getTextField();
            ComboBox comboBoxLanguageLevel = pair.getComboBox();
            if (!txtLanguage.getText().isEmpty()) {
                Language language = new Language();
                language.setName(txtLanguage.getText());
                // If language proficiency unspecified, automatically becomes advanced
                if (comboBoxLanguageLevel.getValue().toString().isEmpty()) {
                    language.setProficiencyLevel(LanguageProficiency.ADVANCED);
                } else {
                    language.setProficiencyLevel(LanguageProficiency.valueOf(
                            comboBoxLanguageLevel.getValue().toString().toUpperCase()));
                }

                languageList.add(language);
            }
        }
        jobDescription.setRequiredLanguages(languageList);

        List<Integer> weightage = new ArrayList<Integer>();
        for (int i = 0; i < txtWeightageList.size(); i++) {
            if (txtWeightageList.get(i).getText().isEmpty()) {
                weightage.add(1);
            } else {
                weightage.add(Math.max(0, Integer.parseInt(txtWeightageList.get(i).getText())));
            }
        }
        jobDescription.setWeightage(weightage);
    }

    private void addResponsibility() {
        TextField textField = new TextField();
        textField.setPrefWidth(300);
        textField.setPrefHeight(FIELD_HEIGHT);
        textField.setLayoutX(FIELD_OFFSET_LEFT);
        textField.setLayoutY(yGeneral);
        yGeneral += FIELD_HEIGHT + FIELD_SPACING;
        generalPane.getChildren().add(textField);
        txtResponsibilityList.add(textField);
        educationPane.setLayoutY(educationPane.getLayoutY() + FIELD_HEIGHT + FIELD_SPACING);
        workPane.setLayoutY(workPane.getLayoutY() + FIELD_HEIGHT + FIELD_SPACING);
        skillPane.setLayoutY(skillPane.getLayoutY() + FIELD_HEIGHT + FIELD_SPACING);
        languagePane.setLayoutY(languagePane.getLayoutY() + FIELD_HEIGHT + FIELD_SPACING);
        weightagePane.setLayoutY(weightagePane.getLayoutY() + FIELD_HEIGHT + FIELD_SPACING);
        btnSave.setLayoutY(btnSave.getLayoutY() + FIELD_HEIGHT + FIELD_SPACING);
        fitToContent();
    }

    private void addSkill() {
        TextField txtSkillName = new TextField();
        txtSkillName.setPrefWidth(145);
        txtSkillName.setPrefHeight(FIELD_HEIGHT);
        txtSkillName.setLayoutX(FIELD_OFFSET_LEFT);
        txtSkillName.setLayoutY(ySkill);
        xSkill = (FIELD_OFFSET_LEFT + FIELD_WIDTH_GENERAL + FIELD_SPACING_IN_A_ROW);
        ComboBox comboBoxSkillLevel = createComboBoxAtPosition(xSkill, ySkill, skillLevelOption);
        comboBoxSkillLevel.setPrefWidth(145);
        ySkill += (FIELD_HEIGHT + FIELD_LABEL_SPACING);

        UIPair pair = new UIPair(txtSkillName, comboBoxSkillLevel);
        txtSkillList.add(pair);

        skillPane.getChildren().addAll(txtSkillName, comboBoxSkillLevel);
        languagePane.setLayoutY(languagePane.getLayoutY() + FIELD_HEIGHT + FIELD_SPACING);
        weightagePane.setLayoutY(weightagePane.getLayoutY() + FIELD_HEIGHT + FIELD_SPACING);
        btnSave.setLayoutY(btnSave.getLayoutY() + FIELD_HEIGHT + FIELD_SPACING);
        fitToContent();
    }

    private void addLanguage() {
        TextField txtLanguage = new TextField();
        txtLanguage.setPrefWidth(145);
        txtLanguage.setPrefHeight(FIELD_HEIGHT);
        txtLanguage.setLayoutX(FIELD_OFFSET_LEFT);
        txtLanguage.setLayoutY(yLanguage);
        xLanguage = (FIELD_OFFSET_LEFT + FIELD_WIDTH_GENERAL + FIELD_SPACING_IN_A_ROW);
        ComboBox comboBoxLanguageLevel = createComboBoxAtPosition(xLanguage, yLanguage, languageLevelOption);
        comboBoxLanguageLevel.setPrefWidth(145);
        yLanguage += (FIELD_HEIGHT + FIELD_LABEL_SPACING);

        UIPair pair = new UIPair(txtLanguage, comboBoxLanguageLevel);
        txtLanguageList.add(pair);

        languagePane.getChildren().addAll(txtLanguage, comboBoxLanguageLevel);
        weightagePane.setLayoutY(weightagePane.getLayoutY() + FIELD_HEIGHT + FIELD_SPACING);
        btnSave.setLayoutY(btnSave.getLayoutY() + FIELD_HEIGHT + FIELD_SPACING);
        fitToContent();
    }

    private List<String> splitAndExtractParts(String text) {
        List<String> parts = Arrays.asList(text.split(","));
        for (int i = 0; i < parts.size(); i++) {
            parts.get(i).trim();
        }
        return parts;
    }

    // Factory
    private Label createHeaderLabelAtPosition(Double x, Double y, String header) {
        Label headerLabel = new Label();
        headerLabel.setPrefHeight(FIELD_HEIGHT);
        headerLabel.setStyle(HEADER_LABEL_STYLE);
        headerLabel.setText(header);
        headerLabel.setLayoutX(x);
        headerLabel.setLayoutY(y);
        return headerLabel;
    }

    private Label createFieldLabelAtPosition(Double x, Double y, String field) {
        Label fieldLabel = new Label();
        fieldLabel.setPrefWidth(FIELD_WIDTH_GENERAL);
        fieldLabel.setPrefHeight(FIELD_HEIGHT);
        fieldLabel.setStyle(FIELD_LABEL_STYLE);
        fieldLabel.setText(field);
        fieldLabel.setLayoutX(x);
        fieldLabel.setLayoutY(y);
        return fieldLabel;
    }

    private TextField createTextField() {
        TextField textField = new TextField();
        textField.setPrefHeight(FIELD_HEIGHT);
        return textField;
    }

    private ImageButton createAddButtonAtPosition(Double x, Double y) {
        ImageButton imageButton = new ImageButton(ADD_ICON_PATH, ADD_ICON_SIZE);
        imageButton.setLayoutX(x);
        imageButton.setLayoutY(y);
        return imageButton;
    }

    private ComboBox createComboBoxAtPosition(Double x, Double y, ObservableList items) {
        ComboBox comboBox = new ComboBox(items);
        comboBox.setPrefHeight(FIELD_HEIGHT);
        comboBox.setLayoutX(x);
        comboBox.setLayoutY(y);
        return comboBox;
    }

}
