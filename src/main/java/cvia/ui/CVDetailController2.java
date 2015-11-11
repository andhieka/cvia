package cvia.ui;

import cvia.model.*;
import cvia.utilities.DateUtilities;
import cvia.utilities.Pair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Michael Limantara on 11/10/2015.
 */
public class CVDetailController2 {
    private static final Double OFFSET_TOP = 10.0;
    private static final String HEADER_LABEL_STYLE = "-fx-font-size: 13px; -fx-font-weight: bold";
    private static final Double HEADER_LABEL_OFFSET_LEFT = 10.0;
    private static final Double HEADER_LABEL_SPACING = 3.0;
    private static final Double LABEL_FIELD_SPACING = 2.0;
    private static final Double FIELD_HEIGHT = 25.0;
    private static final String FIELD_LABEL_STYLE = "-fx-font-size: 14px";
    private static final Double FIELD_OFFSET_LEFT = 15.0;
    private static final Double FIELD_SPACING = 5.0;
    private static final Double FIELD_SPACING_IN_A_ROW = 25.0;
    private static final String NEXT_ICON_PATH = "/next.png";
    private static final String PREV_ICON_PATH = "/prev.png";
    private static final String ADD_ICON_PATH = "/add.png";
    private static final Double ADD_ICON_SIZE = 15.0;
    private static final Double BTN_SIZE = 25.0;
    private static final Double BTN_OFFSET_BOTTOM = 10.0;
    private static final Double PERSONAL_TAB_FIELD_WIDTH = 200.0;
    private static final Double PERSONAL_TAB_FIELD_AREA_WIDTH = 300.0;
    private static final Double PERSONAL_TAB_FIELD_AREA_HEIGHT = 100.0;
    private static final Double EDUCATION_TAB_FIELD_WIDTH = 200.0;
    private static final Double WORK_TAB_FIELD_WIDTH = 200.0;
    private static final Double SKILl_TAB_FIELD_WIDTH = 200.0;
    private static final Double OFFSET_BOTTOM = 50.0;

    @FXML
    private SplitPane splitPane;
    @FXML
    private Pane imagePane;
    @FXML
    private Pane rightPane;
    @FXML
    private TabPane tabPane;
    @FXML
    private ImageView pdfImageView;
    @FXML
    private ScrollPane educationScrollPane;
    @FXML
    private ScrollPane workScrollPane;
    @FXML
    private ScrollPane skillScrollPane;
    @FXML
    private Pane personalInfoPane;
    @FXML
    private Pane educationPane;
    @FXML
    private Pane workPane;
    @FXML
    private Pane skillPane;

    private Pane skillSection = new Pane();
    private Pane languageSection = new Pane();

    private ObservableList<String> educationLevelOption;
    private ObservableList<String> skillLevelOption;
    private ObservableList<String> languageLevelOption;

    private TextField txtName;
    private TextField txtContact;
    private TextField txtEmail;
    private TextArea txtAddress;

    private ImageButton btnEducationAdd;
    private ImageButton btnWorkAdd;
    private ImageButton btnSkillAdd;
    private ImageButton btnLanguageAdd;
    private Button btnSave;
    private ImageButton btnPrev;
    private ImageButton btnNext;

    private List<HashMap<String, TextField>> txtEducationList = new ArrayList<HashMap<String, TextField>>();
    private List<ComboBox> comboBoxEducationLevelList = new ArrayList<ComboBox>();

    private List<HashMap<String, TextField>> txtWorkList = new ArrayList<HashMap<String, TextField>>();
    private List<TextArea> textAreaWorkList = new ArrayList<TextArea>();

    private List<Pair> txtSkillList = new ArrayList<Pair>();
    private List<Pair> txtLanguageList = new ArrayList<Pair>();

    private Double yEducation = OFFSET_TOP + BTN_SIZE + BTN_OFFSET_BOTTOM;
    private Double yWork = OFFSET_TOP + BTN_SIZE + BTN_OFFSET_BOTTOM;
    private Double ySkill = OFFSET_TOP;
    private Double yLanguage = OFFSET_TOP;

    private CV cv;
    private DateTimeFormatter inFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private DateTimeFormatter outFormatter = DateTimeFormatter.ofPattern("MMM yyyy");
    private Stage stage;
    private CVListController controller;

    private int pageNumber = 0;
    private int numberOfPages = 0;
    private PDDocument pdfDocument;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setController(CVListController controller) {
        this.controller = controller;
    }

    @FXML
    private void initialize() {
        setUpOptions();
        setUpButtons();
        setUpPersonalForm();
        setUpEducationForm();
        setUpWorkForm();
        setUpSkillSection();
        setUpLanguageSection();
    }

    private void setUpOptions() {
        List<String> educationOptions = new ArrayList<String>();
        educationOptions.add("");
        for (EducationInfo.EducationLevel level: EducationInfo.EducationLevel.values()) {
            String currentLevel = level.toString();
            currentLevel = currentLevel.substring(0,1).toUpperCase() + currentLevel.substring(1).toLowerCase();
            educationOptions.add(currentLevel);
        }
        educationLevelOption = FXCollections.observableList(educationOptions);

        List<String> skillOptions = new ArrayList<String>();
        skillOptions.add("");
        for (Skill.SkillProficiency level: Skill.SkillProficiency.values()) {
            String currentLevel = level.toString();
            currentLevel = currentLevel.substring(0,1).toUpperCase() + currentLevel.substring(1).toLowerCase();
            skillOptions.add(currentLevel);
        }
        skillLevelOption = FXCollections.observableList(skillOptions);

        List<String> languageOption = new ArrayList<String>();
        languageOption.add("");
        for (Language.LanguageProficiency level: Language.LanguageProficiency.values()) {
            String currentLevel = level.toString();
            currentLevel = currentLevel.substring(0,1).toUpperCase() + currentLevel.substring(1).toLowerCase();
            languageOption.add(currentLevel);
        }
        languageLevelOption = FXCollections.observableList(languageOption);
    }

    private void setUpButtons() {
        btnSave = new Button("Save");
        btnSave.setPrefHeight(25.0);
        btnSave.setLayoutX(450.0);
        btnSave.setLayoutY(5.0);

        btnSave.setOnAction(event -> {
            constructCVFromForm();
            LogicController.getInstance().editCV(cv.getId(), cv);
            controller.refreshData();
            stage.close();
        });

        btnPrev = new ImageButton(PREV_ICON_PATH, BTN_SIZE);
        btnPrev.setLayoutX(10);
        Tooltip prev = new Tooltip("Previous Page");
        Tooltip.install(btnPrev, prev);
        btnNext = new ImageButton(NEXT_ICON_PATH, BTN_SIZE);
        btnNext.setLayoutX(40);
        Tooltip next = new Tooltip("Next Page");
        Tooltip.install(btnNext, next);

        btnPrev.setOnAction(event -> {
            pageNumber = Math.max(0, pageNumber - 1);
            showImageAtPageNumber();
        });
        btnNext.setOnAction(event -> {
            pageNumber = Math.min(pageNumber + 1, numberOfPages - 1);
            showImageAtPageNumber();
        });

        rightPane.getChildren().addAll(btnSave, btnPrev, btnNext);
    }

    private void setUpPersonalForm() {
        Double y = OFFSET_TOP;
        Label nameFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT, y, "Name");
        y += FIELD_HEIGHT + LABEL_FIELD_SPACING;
        txtName = createTextFieldAtPosition(FIELD_OFFSET_LEFT, y, PERSONAL_TAB_FIELD_WIDTH);
        y += FIELD_HEIGHT + FIELD_SPACING;
        Label contactFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT, y, "Contact Number");
        y += FIELD_HEIGHT + LABEL_FIELD_SPACING;
        txtContact = createTextFieldAtPosition(FIELD_OFFSET_LEFT, y, PERSONAL_TAB_FIELD_WIDTH);
        y += FIELD_HEIGHT + FIELD_SPACING;
        Label emailFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT, y, "Email Address");
        y += FIELD_HEIGHT + LABEL_FIELD_SPACING;
        txtEmail = createTextFieldAtPosition(FIELD_OFFSET_LEFT, y, PERSONAL_TAB_FIELD_WIDTH);
        y += FIELD_HEIGHT + FIELD_SPACING;
        Label addressFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT, y, "Address");
        y += FIELD_HEIGHT + LABEL_FIELD_SPACING;
        txtAddress = createTextAreaAtPosition(FIELD_OFFSET_LEFT, y,
                PERSONAL_TAB_FIELD_AREA_WIDTH, PERSONAL_TAB_FIELD_AREA_HEIGHT);
        y += PERSONAL_TAB_FIELD_AREA_HEIGHT + FIELD_SPACING;

        personalInfoPane.getChildren().addAll(nameFieldLabel, contactFieldLabel, emailFieldLabel, addressFieldLabel,
                txtName, txtContact, txtEmail, txtAddress);
    }

    private void setUpEducationForm() {
        btnEducationAdd = new ImageButton(ADD_ICON_PATH, BTN_SIZE);
        btnEducationAdd.setLayoutX(20.0);
        btnEducationAdd.setLayoutY(OFFSET_TOP);
        btnEducationAdd.setOnAction(event -> {
            addEducation();
        });

        educationPane.getChildren().add(btnEducationAdd);

        addEducation();
    }

    private void setUpWorkForm() {
        btnWorkAdd = new ImageButton(ADD_ICON_PATH, BTN_SIZE);
        btnWorkAdd.setLayoutX(20.0);
        btnWorkAdd.setLayoutY(OFFSET_TOP);
        btnWorkAdd.setOnAction(event -> {
            addWork();
        });

        workPane.getChildren().add(btnWorkAdd);

        addWork();
    }

    private void setUpSkillSection() {
        Double x;
        Label skillHeaderLabel = createHeaderLabelAtPosition(HEADER_LABEL_OFFSET_LEFT, ySkill, "Skills");
        btnSkillAdd = createAddButtonAtPosition(60.0, ySkill);
        ySkill += (FIELD_HEIGHT + HEADER_LABEL_SPACING);
        Label skillNameFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT, ySkill, "Skill Name");
        x = (FIELD_OFFSET_LEFT + SKILl_TAB_FIELD_WIDTH + FIELD_SPACING_IN_A_ROW);
        Label skillLevelFieldLabel = createFieldLabelAtPosition(x, ySkill, "Proficiency Level");
        ySkill += (FIELD_HEIGHT + LABEL_FIELD_SPACING);

        addSkill();

        btnSkillAdd.setOnAction(event -> {
            addSkill();
        });

        skillSection.getChildren().addAll(skillHeaderLabel, btnSkillAdd, skillNameFieldLabel, skillLevelFieldLabel);
        skillPane.getChildren().add(skillSection);
    }

    private void setUpLanguageSection() {
        Double x;
        Label languageHeaderLabel = createHeaderLabelAtPosition(HEADER_LABEL_OFFSET_LEFT, yLanguage, "Languages");
        btnLanguageAdd = createAddButtonAtPosition(85.0, yLanguage);
        yLanguage += (FIELD_HEIGHT + HEADER_LABEL_SPACING);
        Label languageNameFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT, yLanguage, "Language");
        x = (FIELD_OFFSET_LEFT + SKILl_TAB_FIELD_WIDTH + FIELD_SPACING_IN_A_ROW);
        Label languageLevelFieldLabel = createFieldLabelAtPosition(x, yLanguage, "Proficiency Level");
        yLanguage += (FIELD_HEIGHT + LABEL_FIELD_SPACING);

        addLanguage();

        btnLanguageAdd.setOnAction(event -> {
            addLanguage();
        });

        languageSection.getChildren().addAll(languageHeaderLabel, btnLanguageAdd, languageNameFieldLabel, languageLevelFieldLabel);
        languageSection.setLayoutY(ySkill);
        skillPane.getChildren().add(languageSection);
    }

    private void addEducation() {
        int index = txtEducationList.size() + 1;
        Label sectionLabel = createHeaderLabelAtPosition(HEADER_LABEL_OFFSET_LEFT, yEducation, "Education " + index);
        yEducation += FIELD_HEIGHT + HEADER_LABEL_SPACING;
        Label institutionNameFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT, yEducation, "Institution Name");
        yEducation += FIELD_HEIGHT + LABEL_FIELD_SPACING;
        TextField txtInstitutionName = createTextFieldAtPosition(FIELD_OFFSET_LEFT, yEducation, EDUCATION_TAB_FIELD_WIDTH);
        yEducation += FIELD_HEIGHT + FIELD_SPACING;
        Label educationLevelFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT, yEducation, "Education Level");
        Label majorFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT + EDUCATION_TAB_FIELD_WIDTH + FIELD_SPACING_IN_A_ROW,
                yEducation, "Major");
        yEducation += FIELD_HEIGHT + LABEL_FIELD_SPACING;
        ComboBox educationLevelComboBox = createComboBoxAtPosition(FIELD_OFFSET_LEFT, yEducation, educationLevelOption);
        educationLevelComboBox.setPrefWidth(EDUCATION_TAB_FIELD_WIDTH);
        TextField txtMajor = createTextFieldAtPosition(FIELD_OFFSET_LEFT + EDUCATION_TAB_FIELD_WIDTH + FIELD_SPACING_IN_A_ROW,
                yEducation, EDUCATION_TAB_FIELD_WIDTH);
        yEducation += FIELD_HEIGHT + FIELD_SPACING;

        Label startDateFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT, yEducation, "Start Date");
        Label endDateFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT + EDUCATION_TAB_FIELD_WIDTH + FIELD_SPACING_IN_A_ROW,
                yEducation, "End Date");
        yEducation += FIELD_HEIGHT + LABEL_FIELD_SPACING;
        TextField txtStartDate = createTextFieldAtPosition(FIELD_OFFSET_LEFT, yEducation, EDUCATION_TAB_FIELD_WIDTH);
        TextField txtEndDate = createTextFieldAtPosition(FIELD_OFFSET_LEFT + EDUCATION_TAB_FIELD_WIDTH + FIELD_SPACING_IN_A_ROW,
                yEducation, EDUCATION_TAB_FIELD_WIDTH);
        yEducation += FIELD_HEIGHT + FIELD_SPACING;

        Label gradeFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT, yEducation, "Grade");
        yEducation += FIELD_HEIGHT + LABEL_FIELD_SPACING;
        TextField txtGrade = createTextFieldAtPosition(FIELD_OFFSET_LEFT, yEducation, EDUCATION_TAB_FIELD_WIDTH);
        yEducation += FIELD_HEIGHT + FIELD_SPACING;

        HashMap<String, TextField> hmap = new HashMap<String, TextField>();
        hmap.put("institution_name", txtInstitutionName);
        hmap.put("major", txtMajor);
        hmap.put("start_date", txtStartDate);
        hmap.put("end_date", txtEndDate);
        hmap.put("grade", txtGrade);
        txtEducationList.add(hmap);
        comboBoxEducationLevelList.add(educationLevelComboBox);

        educationPane.getChildren().addAll(sectionLabel, institutionNameFieldLabel, educationLevelFieldLabel,
                majorFieldLabel, startDateFieldLabel, endDateFieldLabel, gradeFieldLabel, txtInstitutionName,
                txtMajor, txtStartDate, txtEndDate, educationLevelComboBox, txtGrade);
        fitContent();
    }

    private void addWork() {
        int index = txtWorkList.size() + 1;
        Label sectionLabel = createHeaderLabelAtPosition(HEADER_LABEL_OFFSET_LEFT, yWork, "Work " + index);
        yWork += FIELD_HEIGHT + HEADER_LABEL_SPACING;

        Label companyFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT, yWork, "Company");
        Label positionFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT + WORK_TAB_FIELD_WIDTH + FIELD_SPACING_IN_A_ROW,
                yWork, "Position");
        yWork += FIELD_HEIGHT + LABEL_FIELD_SPACING;
        TextField txtCompany = createTextFieldAtPosition(FIELD_OFFSET_LEFT, yWork, WORK_TAB_FIELD_WIDTH);
        TextField txtPosition = createTextFieldAtPosition(FIELD_OFFSET_LEFT + WORK_TAB_FIELD_WIDTH + FIELD_SPACING_IN_A_ROW,
                yWork, WORK_TAB_FIELD_WIDTH);
        yWork += FIELD_HEIGHT + FIELD_SPACING;

        Label startDateFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT, yWork, "Start Date");
        Label endDateFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT + WORK_TAB_FIELD_WIDTH + FIELD_SPACING_IN_A_ROW,
                yWork, "End Date");
        yWork += FIELD_HEIGHT + LABEL_FIELD_SPACING;
        TextField txtStartDate = createTextFieldAtPosition(FIELD_OFFSET_LEFT, yWork, WORK_TAB_FIELD_WIDTH);
        TextField txtEndDate = createTextFieldAtPosition(FIELD_OFFSET_LEFT + WORK_TAB_FIELD_WIDTH + FIELD_SPACING_IN_A_ROW,
                yWork, WORK_TAB_FIELD_WIDTH);
        yWork += FIELD_HEIGHT + FIELD_SPACING;

        Label descriptionFieldLabel = createFieldLabelAtPosition(FIELD_OFFSET_LEFT, yWork, "Description");
        yWork += FIELD_HEIGHT + LABEL_FIELD_SPACING;
        TextArea descriptionTextArea = createTextAreaAtPosition(FIELD_OFFSET_LEFT, yWork,
                WORK_TAB_FIELD_WIDTH * 2 + FIELD_SPACING_IN_A_ROW, 100.0);
        yWork += 100.0 + FIELD_SPACING;

        HashMap<String, TextField> hmap = new HashMap<String, TextField>();
        hmap.put("company", txtCompany);
        hmap.put("position", txtPosition);
        hmap.put("start_date", txtStartDate);
        hmap.put("end_date", txtEndDate);
        txtWorkList.add(hmap);
        textAreaWorkList.add(descriptionTextArea);

        workPane.getChildren().addAll(sectionLabel, companyFieldLabel, positionFieldLabel, startDateFieldLabel,
                endDateFieldLabel, descriptionFieldLabel, txtCompany, txtPosition, txtStartDate, txtEndDate,
                descriptionTextArea);
        fitContent();
    }

    private void addSkill() {
        Double x;
        TextField txtSkillName = new TextField();
        txtSkillName.setPrefWidth(200);
        txtSkillName.setPrefHeight(FIELD_HEIGHT);
        txtSkillName.setLayoutX(FIELD_OFFSET_LEFT);
        txtSkillName.setLayoutY(ySkill);
        x = (FIELD_OFFSET_LEFT + SKILl_TAB_FIELD_WIDTH + FIELD_SPACING_IN_A_ROW);
        ComboBox comboBoxSkillLevel = createComboBoxAtPosition(x, ySkill, skillLevelOption);
        comboBoxSkillLevel.setPrefWidth(200);
        ySkill += (FIELD_HEIGHT + LABEL_FIELD_SPACING);

        Pair pair = new Pair(txtSkillName, comboBoxSkillLevel);
        txtSkillList.add(pair);

        skillSection.getChildren().addAll(txtSkillName, comboBoxSkillLevel);
        languageSection.setLayoutY(languageSection.getLayoutY() + FIELD_HEIGHT + FIELD_SPACING);
        fitContent();
    }

    private void addLanguage() {
        Double x;
        TextField txtLanguage = new TextField();
        txtLanguage.setPrefWidth(200);
        txtLanguage.setPrefHeight(FIELD_HEIGHT);
        txtLanguage.setLayoutX(FIELD_OFFSET_LEFT);
        txtLanguage.setLayoutY(yLanguage);
        x = (FIELD_OFFSET_LEFT + SKILl_TAB_FIELD_WIDTH + FIELD_SPACING_IN_A_ROW);
        ComboBox comboBoxLanguageLevel = createComboBoxAtPosition(x, yLanguage, languageLevelOption);
        comboBoxLanguageLevel.setPrefWidth(200);
        yLanguage += (FIELD_HEIGHT + LABEL_FIELD_SPACING);

        Pair pair = new Pair(txtLanguage, comboBoxLanguageLevel);
        txtLanguageList.add(pair);

        languageSection.getChildren().addAll(txtLanguage, comboBoxLanguageLevel);
        fitContent();
    }

    private void fitContent() {
        educationPane.setPrefHeight(yEducation + OFFSET_BOTTOM);
        workPane.setPrefHeight(yWork + OFFSET_BOTTOM);
        skillPane.setPrefHeight(ySkill + yLanguage + OFFSET_BOTTOM);
    }

    public void populateForm(CV cv) {
        this.cv = cv;

        setPdfView();

        //Personal Info
        if (cv.getPersonalInfo() != null) {
            PersonalInfo personalInfo = cv.getPersonalInfo();
            if (personalInfo.getName() != null) {
                txtName.setText(personalInfo.getName());
            }
            if (personalInfo.getContactNumber() != null) {
                txtContact.setText(personalInfo.getContactNumber());
            }
            if (personalInfo.getEmail() != null) {
                txtEmail.setText(personalInfo.getEmail());
            }
            if (personalInfo.getAddress() != null) {
                txtAddress.setText(personalInfo.getAddress());
            }
        }

        //Education Info
        if (cv.getEducationInfoList() != null) {
            List<EducationInfo> educationInfoList = cv.getEducationInfoList();
            for (int i = 0; i < educationInfoList.size(); i++) {
                EducationInfo educationInfo = educationInfoList.get(i);
                if (educationInfo.getInstitutionName() != null) {
                    txtEducationList.get(i).get("institution_name").setText(educationInfo.getInstitutionName());
                }
                if (educationInfo.getEducationLevel() != null) {
                    String educationLevel = educationInfo.getEducationLevel().toString();
                    educationLevel = educationLevel.substring(0,1).toUpperCase() + educationLevel.substring(1).toLowerCase();
                    comboBoxEducationLevelList.get(i).setValue(educationLevel);
                }
                if (educationInfo.getMajor() != null) {
                    txtEducationList.get(i).get("major").setText(educationInfo.getMajor());
                }
                if (educationInfo.getStartDate() != null) {
                    txtEducationList.get(i).get("start_date").setText(educationInfo.getStartDate().format(outFormatter));
                }
                if (educationInfo.getEndDate() != null) {
                    txtEducationList.get(i).get("end_date").setText(educationInfo.getEndDate().format(outFormatter));
                }
                if (educationInfo.getGrade() != null) {
                    txtEducationList.get(i).get("grade").setText(educationInfo.getGrade().getGrade() + "/" +
                        educationInfo.getGrade().getMaxGrade());
                }
                addEducation();
            }
        }

        //Work
        if (cv.getWorkExperienceList() != null) {
            List<WorkExperience> workExperienceList = cv.getWorkExperienceList();
            for (int i = 0; i < workExperienceList.size(); i++) {
                WorkExperience workExperience = workExperienceList.get(i);
                if (workExperience.getCompany() != null) {
                    txtWorkList.get(i).get("company").setText(workExperience.getCompany());
                }
                if (workExperience.getPosition() != null) {
                    txtWorkList.get(i).get("position").setText(workExperience.getPosition());
                }
                if (workExperience.getStartDate() != null) {
                    txtWorkList.get(i).get("start_date").setText(workExperience.getStartDate().format(outFormatter));
                }
                if (workExperience.getEndDate() != null) {
                    txtWorkList.get(i).get("end_date").setText(workExperience.getEndDate().format(outFormatter));
                }
                if (workExperience.getDescription() != null) {
                    textAreaWorkList.get(i).setText(workExperience.getDescription());
                }
                addWork();
            }
        }

        //Skills
        if (cv.getSkills() != null) {
            List<Skill> skillList = cv.getSkills();
            for (int i = 0; i < skillList.size(); i++) {
                Skill skill = skillList.get(i);
                if (skill.getName() != null) {
                    txtSkillList.get(i).getTextField().setText(skill.getName());
                }
                if (skill.getProficiencyLevel() != null) {
                    String level = skill.getProficiencyLevel().toString();
                    level = level.substring(0,1).toUpperCase() + level.substring(1).toLowerCase();
                    txtSkillList.get(i).getComboBox().setValue(level);
                }
                addSkill();
            }
        }

        //Language
        if (cv.getLanguages() != null) {
            List<Language> languageList = cv.getLanguages();
            for (int i = 0; i < languageList.size(); i++) {
                Language language = languageList.get(i);
                if (language.getName() != null) {
                    txtLanguageList.get(i).getTextField().setText(language.getName());
                }
                if (language.getProficiencyLevel() != null) {
                    String level = language.getProficiencyLevel().toString();
                    level = level.substring(0,1).toUpperCase() + level.substring(1).toLowerCase();
                    txtLanguageList.get(i).getComboBox().setValue(level);
                }
                addLanguage();
            }
        }
    }

    private void setPdfView() {
        pdfImageView.setFitWidth(500);
        pdfImageView.setFitHeight(600);
        pdfImageView.setCache(true);

        // byte[] cvRawContent = cv.getRawSource().getRawContent();

        try {
            // ByteArrayInputStream inStream = new ByteArrayInputStream(cvRawContent);
            pdfDocument = PDDocument.load(new File(cv.getRawSource().getFilename()));
            numberOfPages = pdfDocument.getNumberOfPages();
            showImageAtPageNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showImageAtPageNumber() {
        try {
            PDPage pdfPage = (PDPage) pdfDocument.getDocumentCatalog().getAllPages().get(pageNumber);
            BufferedImage bufferedImage = pdfPage.convertToImage(BufferedImage.TYPE_INT_RGB, 100);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            pdfImageView.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void constructCVFromForm() {
        //Personal Info
        PersonalInfo personalInfo = new PersonalInfo();
        if (!txtName.getText().isEmpty()) {
            personalInfo.setName(txtName.getText());
        }
        if (!txtContact.getText().isEmpty()) {
            personalInfo.setContactNumber(txtContact.getText());
        }
        if (!txtEmail.getText().isEmpty()) {
            personalInfo.setEmail(txtEmail.getText());
        }
        if (!txtAddress.getText().isEmpty()) {
            personalInfo.setAddress(txtAddress.getText());
        }
        cv.setPersonalInfo(personalInfo);

        //Education
        List<EducationInfo> educationInfoList = new ArrayList<EducationInfo>();
        for (int i = 0; i < txtEducationList.size(); i++) {
            HashMap<String, TextField> hmap = txtEducationList.get(i);
            TextField txtInstitutionName = hmap.get("institution_name");
            TextField txtMajor = hmap.get("major");
            TextField txtStartDate = hmap.get("start_date");
            TextField txtEndDate = hmap.get("end_date");
            TextField txtGrade = hmap.get("grade");
            ComboBox comboBoxEducationLevel = comboBoxEducationLevelList.get(i);

            if (txtInstitutionName.getText().isEmpty() &&
                    txtMajor.getText().isEmpty() &&
                    txtStartDate.getText().isEmpty() &&
                    txtEndDate.getText().isEmpty() &&
                    txtGrade.getText().isEmpty() &&
                    comboBoxEducationLevel.getValue() == null) {
                break;
            }

            EducationInfo educationInfo = new EducationInfo();
            if (!txtInstitutionName.getText().isEmpty()) {
                educationInfo.setInstitutionName(txtInstitutionName.getText());
            }
            if (!txtMajor.getText().isEmpty()) {
                educationInfo.setMajor(txtMajor.getText());
            }
            if (!txtStartDate.getText().isEmpty()) {
                educationInfo.setStartDate(DateUtilities.dateFromString("01 " + txtStartDate.getText().toString(),
                        inFormatter));
            }
            if (!txtEndDate.getText().isEmpty()) {
                educationInfo.setEndDate(DateUtilities.dateFromString("01 " + txtEndDate.getText().toString(),
                        inFormatter));
            }
            if (!txtGrade.getText().isEmpty()) {
                String[] gradeString = txtGrade.getText().split("/");
                Grade grade = new Grade();
                grade.setGrade(Float.parseFloat(gradeString[0]));
                grade.setMaxGrade(Float.parseFloat(gradeString[1]));
                educationInfo.setGrade(grade);
            }
            if (comboBoxEducationLevel.getValue() != null &&
                    !comboBoxEducationLevel.getValue().toString().isEmpty()) {
                educationInfo.setEducationLevel(EducationInfo.EducationLevel
                        .valueOf(comboBoxEducationLevel.getValue().toString().toUpperCase()));
            }
            educationInfoList.add(educationInfo);
        }
        cv.setEducationInfoList(educationInfoList);

        //Work
        List<WorkExperience> workExperienceList = new ArrayList<WorkExperience>();
        for (int i = 0; i < txtWorkList.size(); i++) {
            HashMap<String, TextField> hmap = txtWorkList.get(i);
            TextField txtCompany = hmap.get("company");
            TextField txtPosition = hmap.get("position");
            TextField txtStartDate = hmap.get("start_date");
            TextField txtEndDate = hmap.get("end_date");
            TextArea txtDescription = textAreaWorkList.get(i);

            if (txtCompany.getText().isEmpty() &&
                    txtPosition.getText().isEmpty() &&
                    txtStartDate.getText().isEmpty() &&
                    txtEndDate.getText().isEmpty() &&
                    txtDescription.getText().isEmpty()) {
                break;
            }

            WorkExperience workExperience = new WorkExperience();
            if (!txtCompany.getText().isEmpty()) {
                workExperience.setCompany(txtCompany.getText());
            }
            if (!txtPosition.getText().isEmpty()) {
                workExperience.setPosition(txtPosition.getText());
            }
            if (!txtStartDate.getText().isEmpty()) {
                workExperience.setStartDate(DateUtilities.dateFromString("01 " + txtStartDate.getText(), inFormatter));
            }
            if (!txtEndDate.getText().isEmpty()) {
                workExperience.setEndDate(DateUtilities.dateFromString("01 " + txtEndDate.getText(), inFormatter));
            }
            if (!txtDescription.getText().isEmpty()) {
                workExperience.setDescription(txtDescription.getText());
            }
            workExperienceList.add(workExperience);
        }
        cv.setWorkExperienceList(workExperienceList);

        //Skill
        List<Skill> skillList = new ArrayList<Skill>();
        for (int i = 0; i < txtSkillList.size(); i++) {
            TextField txtSkillName = txtSkillList.get(i).getTextField();
            ComboBox comboBoxSkillLevel = txtSkillList.get(i).getComboBox();

            if (txtSkillName.getText().isEmpty() &&
                    comboBoxSkillLevel.getValue() == null) {
                break;
            }
            Skill skill = new Skill();
            if (!txtSkillName.getText().isEmpty()) {
                skill.setName(txtSkillName.getText());
            }
            if (comboBoxSkillLevel.getValue() != null &&
                    !comboBoxSkillLevel.getValue().toString().isEmpty()) {
                skill.setProficiencyLevel(Skill.SkillProficiency.valueOf(comboBoxSkillLevel.getValue().toString().toUpperCase()));
            }
            skillList.add(skill);
        }
        cv.setSkills(skillList);

        //Language
        List<Language> languageList = new ArrayList<Language>();
        for (int i = 0; i < txtLanguageList.size(); i++) {
            TextField txtLanguageName = txtLanguageList.get(i).getTextField();
            ComboBox comboBoxLanguageLevel = txtLanguageList.get(i).getComboBox();

            if (txtLanguageName.getText().isEmpty() &&
                    comboBoxLanguageLevel.getValue() == null) {
                break;
            }
            Language language = new Language();
            if (!txtLanguageName.getText().isEmpty()) {
                language.setName(txtLanguageName.getText());
            }
            if (comboBoxLanguageLevel.getValue() != null &&
                    !comboBoxLanguageLevel.getValue().toString().isEmpty()) {
                language.setProficiencyLevel(Language.LanguageProficiency.valueOf(comboBoxLanguageLevel.getValue().toString().toUpperCase()));
            }
            languageList.add(language);
        }
        cv.setLanguages(languageList);
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
        fieldLabel.setPrefHeight(FIELD_HEIGHT);
        fieldLabel.setStyle(FIELD_LABEL_STYLE);
        fieldLabel.setText(field);
        fieldLabel.setLayoutX(x);
        fieldLabel.setLayoutY(y);
        return fieldLabel;
    }

    private TextField createTextFieldAtPosition(Double x, Double y, Double width) {
        TextField textField = new TextField();
        textField.setPrefWidth(width);
        textField.setPrefHeight(FIELD_HEIGHT);
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        return textField;
    }

    private TextArea createTextAreaAtPosition(Double x, Double y, Double width, Double height) {
        TextArea textArea = new TextArea();
        textArea.setPrefWidth(width);
        textArea.setPrefHeight(height);
        textArea.setLayoutX(x);
        textArea.setLayoutY(y);
        return textArea;
    }

    private ComboBox createComboBoxAtPosition(Double x, Double y, ObservableList items) {
        ComboBox comboBox = new ComboBox(items);
        comboBox.setPrefHeight(FIELD_HEIGHT);
        comboBox.setLayoutX(x);
        comboBox.setLayoutY(y);
        return comboBox;
    }

    private ImageButton createAddButtonAtPosition(Double x, Double y) {
        ImageButton imageButton = new ImageButton(ADD_ICON_PATH, ADD_ICON_SIZE);
        imageButton.setLayoutX(x);
        imageButton.setLayoutY(y);
        return imageButton;
    }
}
