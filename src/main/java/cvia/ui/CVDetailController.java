package cvia.ui;

import cvia.model.*;
import cvia.model.Language.LanguageProficiency;
import cvia.model.EducationInfo.EducationLevel;
import cvia.model.Skill.SkillProficiency;
import cvia.utilities.DateUtilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Michael Limantara on 11/7/2015.
 */
public class CVDetailController {
    private static final String HEADER_LIST_STYLE = "-fx-font-size: 13px; -fx-font-weight: bold";
    private static final String FIELD_LABEL_STYLE = "-fx-font-size: 14px";
    private static final Double BUTTON_SIZE = 25.0;

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

    private ImageButton btnPrev;
    private ImageButton btnNext;
    private Button btnSave;
    private ImageButton btnCancel;

    PDDocument document;
    private Integer pageNumber = 0;
    private Integer numberOfPages;
    private CVListController cvListController;

    private CV cv;
    private Stage stage;
    private HashMap<TextInputControl, String> inputFieldToAttributeMap = new HashMap<TextInputControl, String>();
    private HashMap<String, Object> personalInfoMap = new HashMap<String, Object>();
    private HashMap<Integer, HashMap<String, Object>> educationInfoMap = new HashMap<Integer, HashMap<String, Object>>();
    private List<HashMap<String,Object>> workExperienceMap = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> skillMap = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> languageMap = new ArrayList<HashMap<String, Object>>();
    private Integer educationIndex = 0;
    private Integer workIndex = 0;
    private DateTimeFormatter inFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private DateTimeFormatter outFormatter = DateTimeFormatter.ofPattern("MMM yyyy");

    public void setCV(CV cv) {
        this.cv = cv;
        setPdfImage();
        setUpButtons();
        setUpForm();
    }

    public void setController(CVListController cvListController) {
        this.cvListController = cvListController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

//    public void populate() {
//        PersonalInfo personalInfo = cv.getPersonalInfo();
//        ((TextInputControl)personalInfoMap.get("name")).setText(personalInfo.getName());
//        ((TextInputControl)personalInfoMap.get("contact_number")).setText(personalInfo.getContactNumber());
//        ((TextInputControl)personalInfoMap.get("email")).setText(personalInfo.getEmail());
//        ((TextInputControl)personalInfoMap.get("address")).setText(personalInfo.getAddress());
//
//        List<EducationInfo> educationInfoList = cv.getEducationInfoList();
//        for (int i = 0; i < educationInfoList.size(); i++) {
//            ((TextInputControl) educationInfoMap.get(i).get("institution_name")).
//                    setText(educationInfoList.get(i).getInstitutionName());
//            ((TextInputControl) educationInfoMap.get(i).get("education_level")).
//                    setText(educationInfoList.get(i).getInstitutionName());
//            ((TextInputControl) educationInfoMap.get(i).get("major")).
//                    setText(educationInfoList.get(i).getInstitutionName());
//            ((TextInputControl) educationInfoMap.get(i).get("start_date")).
//                    setText(educationInfoList.get(i).getInstitutionName());
//            ((TextInputControl) educationInfoMap.get(i).get("end_date")).
//                    setText(educationInfoList.get(i).getInstitutionName());
//        }
//
//        List<WorkExperience> workExperienceList = cv.getWorkExperienceList();
//        for (int i = 0; i < workExperienceList.size(); i++) {
//            ((TextInputControl) workExperienceMap.get(i).get("company")).
//                    setText(workExperienceList.get(i).getCompany());
//            ((TextInputControl) workExperienceMap.get(i).get("position")).
//                    setText(workExperienceList.get(i).getPosition());
//            ((TextInputControl) workExperienceMap.get(i).get("start_date")).
//                    setText(workExperienceList.get(i).getStartDate());
//            ((TextInputControl) workExperienceMap.get(i).get("end_date")).
//                    setText(workExperienceList.get(i).getInstitutionName());
//            ((TextInputControl) workExperienceMap.get(i).get("description")).
//                    setText(workExperienceList.get(i).getInstitutionName());
//        }
//    }

    @FXML
    private void initialize() {
//        try {
//            document = PDDocument.load("resume.pdf");
//            numberOfPages = document.getNumberOfPages();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        CV cv = new CV();
//        cv.setId(1L);
//        PersonalInfo personalInfo = new PersonalInfo();
//        personalInfo.setName("user 2");
//        personalInfo.setContactNumber("+658123456");
//        personalInfo.setEmail("user1@testing.com");
//        cv.setPersonalInfo(personalInfo);
//
//        List<EducationInfo> educationList = new ArrayList<EducationInfo>();
//        EducationInfo educationInfo = new EducationInfo();
//        educationInfo.setInstitutionName("NUS");
//        educationInfo.setEducationLevel(EducationLevel.UNDERGRADUATE);
//        educationInfo.setMajor("CS");
//        educationInfo.setStartDate(LocalDate.now());
//        educationInfo.setEndDate(LocalDate.now());
//        for (int i = 0; i < 3; i++) {
//            educationList.add(educationInfo);
//        }
//        cv.setEducationInfoList(educationList);
//
//        List<WorkExperience> workList = new ArrayList<WorkExperience>();
//        WorkExperience workExperience = new WorkExperience();
//        workExperience.setCompany("Google");
//        workExperience.setPosition("Software Engineer");
//        workExperience.setDescription("Description");
//        workExperience.setStartDate(LocalDate.now());
//        workExperience.setEndDate(LocalDate.now());
//        for (int i = 0; i < 2; i++) {
//            workList.add(workExperience);
//        }
//        cv.setWorkExperienceList(workList);
//
//        List<Skill> skillList = new ArrayList<Skill>();
//        Skill skill = new Skill();
//        skill.setName("Coding");
//        skill.setProficiencyLevel(SkillProficiency.ADVANCED);
//        for (int i = 0; i < 3; i++) {
//            skillList.add(skill);
//        }
//        cv.setSkills(skillList);
//
//        List<Language> languageList = new ArrayList<Language>();
//        Language language = new Language();
//        language.setName("English");
//        language.setProficiencyLevel(LanguageProficiency.ADVANCED);
//        for (int i = 0; i < 3; i++) {
//            languageList.add(language);
//        }
//        cv.setLanguages(languageList);
    }

    private void setPdfImage() {
        pdfImageView.setFitHeight(600);
        pdfImageView.setFitWidth(500);
        showImageAtPageNumber();
    }

    private void showImageAtPageNumber() {
        try {
            PDPage pdfPage = (PDPage) document.getDocumentCatalog().getAllPages().get(pageNumber);
            BufferedImage bufferedImage = pdfPage.convertToImage(BufferedImage.TYPE_INT_RGB, 100);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            pdfImageView.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpButtons() {
        HBox hBoxButtonCV = new HBox();
        hBoxButtonCV.setLayoutX(425);
        btnSave = new Button("Save");
        btnSave.setOnAction(event -> {
            saveCV();
        });
        btnCancel = new ImageButton("/add.png", BUTTON_SIZE);
        btnCancel.setOnAction(event -> {
            stage.close();
        });
        hBoxButtonCV.getChildren().addAll(btnSave);
        rightPane.getChildren().addAll(hBoxButtonCV);

        btnPrev = new ImageButton("/prev.png", BUTTON_SIZE);
        btnPrev.setLayoutX(10);
        btnNext = new ImageButton("/next.png", BUTTON_SIZE);
        btnNext.setLayoutX(40);

        Tooltip next = new Tooltip("Next Page");
        Tooltip.install(btnNext, next);

        Tooltip prev = new Tooltip("Previous Page");
        Tooltip.install(btnPrev, prev);
        btnPrev.setOnAction(event -> {
            prev();
        });
        btnNext.setOnAction(event -> {
            next();
        });
        rightPane.getChildren().addAll(btnPrev, btnNext);
    }

    private void prev() {
        System.out.println("prev");
        pageNumber = Math.max(0, pageNumber - 1);
        showImageAtPageNumber();
    }

    private void next() {
        System.out.println("next");
        pageNumber = Math.min(pageNumber + 1, numberOfPages - 1);
        showImageAtPageNumber();
    }

    private void saveCV() {
        modifyCV();
        LogicController.getInstance().editCV(cv.getId(), cv);
        cvListController.refreshData(cv);
    }

    private void modifyCV() {
        // Personal Info Section
        PersonalInfo personalInfo = new PersonalInfo();
        if (!(((TextInputControl) personalInfoMap.get("name")).getText().isEmpty())) {
            personalInfo.setName(((TextInputControl) personalInfoMap.get("name")).getText());
        }
        if (!((TextInputControl) personalInfoMap.get("contact_number")).getText().isEmpty()) {
            personalInfo.setContactNumber(((TextInputControl) personalInfoMap.get("contact_number")).getText());
        }
        if (!((TextInputControl) personalInfoMap.get("email")).getText().isEmpty()) {
            personalInfo.setEmail(((TextInputControl) personalInfoMap.get("email")).getText());
        }
        if (!((TextInputControl) personalInfoMap.get("address")).getText().isEmpty()) {
            personalInfo.setAddress(((TextInputControl) personalInfoMap.get("address")).getText());
        }
        cv.setPersonalInfo(personalInfo);

        // Education Info Section
        List<EducationInfo> educationList = new ArrayList<EducationInfo>();
        for (HashMap<String, Object> educationMap: educationInfoMap.values()) {
            EducationInfo educationInfo = new EducationInfo();
            educationInfo.setInstitutionName(((TextInputControl) educationMap.get("institution_name")).getText());
            educationInfo.setEducationLevel(
                    EducationLevel.valueOf(((TextInputControl) educationMap.get("education_level")).getText()));
            educationInfo.setMajor(((TextInputControl) educationMap.get("major")).getText());
            LocalDate startDate = DateUtilities.dateFromString("01 " +
                    ((TextInputControl) educationMap.get("start_date")).getText(), inFormatter);
            educationInfo.setStartDate(startDate);
            LocalDate endDate = DateUtilities.dateFromString("01 " +
                    ((TextInputControl) educationMap.get("end_date")).getText(), inFormatter);
            educationInfo.setEndDate(endDate);
            educationList.add(educationInfo);
        }
        cv.setEducationInfoList(educationList);

        // Work Experience Section
        List<WorkExperience> workExperienceList = new ArrayList<WorkExperience>();
        for (HashMap<String, Object> workExpMap: workExperienceMap) {
            WorkExperience workExperience = new WorkExperience();
            workExperience.setCompany(((TextInputControl) workExpMap.get("company")).getText());
            workExperience.setPosition(((TextInputControl) workExpMap.get("position")).getText());
            LocalDate startDate = DateUtilities.dateFromString("01 " +
                    ((TextInputControl) workExpMap.get("start_date")).getText(), inFormatter);
            workExperience.setStartDate(startDate);
            LocalDate endDate = DateUtilities.dateFromString("01 " +
                    ((TextInputControl) workExpMap.get("end_date")).getText(), inFormatter);
            workExperience.setEndDate(endDate);
            workExperience.setDescription(((TextInputControl) workExpMap.get("description")).getText());
        }
        cv.setWorkExperienceList(workExperienceList);

        // Skill Section
        List<Skill> skillList = new ArrayList<Skill>();
        for (HashMap<String, Object> map: skillMap) {
            Skill skill = new Skill();
            skill.setName(((TextInputControl) map.get("name")).getText());
            skill.setProficiencyLevel(SkillProficiency.valueOf(((TextInputControl) map.get("level")).getText()));
            skillList.add(skill);
        }
        cv.setSkills(skillList);

        // Language Section
        List<Language> languageList = new ArrayList<Language>();
        for (HashMap<String, Object> map: languageMap) {
            Language language = new Language();
            language.setName(((TextInputControl) map.get("name")).getText());
            language.setProficiencyLevel(LanguageProficiency.valueOf(((TextInputControl) map.get("level")).getText()));
        }
        cv.setLanguages(languageList);
    }

    private void setUpForm() {
        setUpPersonalInfoForm();
        setUpEducationInfoForm();
        setUpWorkExperienceForm();
        setUpSkillForm();
//        setPdfImage();
        setUpButtons();
    }

    private void setUpPersonalInfoForm() {
        PersonalInfo personalInfo = cv.getPersonalInfo();
        String name = "";
        String contactNumber = "";
        String email = "";
        String address = "";
        if (personalInfo != null) {
            name = personalInfo.getName();
            contactNumber = personalInfo.getContactNumber();
            email = personalInfo.getEmail();
            address = personalInfo.getAddress();
        }

        VBox vBoxName = createVBoxWithTextField("Name", name, "");
        vBoxName.setLayoutX(20);
        vBoxName.setLayoutY(5);
        personalInfoMap.put("name", vBoxName.getChildren().get(1));
        VBox vBoxContactNumber = createVBoxWithTextField("Contact Number", contactNumber,"");
        vBoxContactNumber.setLayoutX(20);
        vBoxContactNumber.setLayoutY(65);
        personalInfoMap.put("contact_number", vBoxContactNumber.getChildren().get(1));
        VBox vBoxEmail = createVBoxWithTextField("Email", email, "");
        vBoxEmail.setLayoutX(20);
        vBoxEmail.setLayoutY(125);
        personalInfoMap.put("email", vBoxEmail.getChildren().get(1));
        VBox vBoxAddress = createVBoxWithTextArea("Address", address, "");
        vBoxAddress.setLayoutX(20);
        vBoxAddress.setLayoutY(185);
        personalInfoMap.put("address", vBoxAddress.getChildren().get(1));
        personalInfoPane.getChildren().addAll(vBoxName, vBoxContactNumber, vBoxEmail, vBoxAddress);
    }

    private void setUpEducationInfoForm() {
        if (cv.getEducationInfoList() != null) {

        }
        for (EducationInfo educationInfo: cv.getEducationInfoList()) {
            Pane educationItemPane = createEducationItemView(educationInfo);
            educationItemPane.setLayoutY(educationIndex * 205);
            educationPane.getChildren().add(educationItemPane);
            educationIndex++;
        }

        //TODO: addMoreEducationForm
        educationPane.setPrefHeight(220 * cv.getEducationInfoList().size());
        educationScrollPane.setPrefHeight(220 * cv.getEducationInfoList().size());
    }

    private Pane createEducationItemView(EducationInfo educationInfo) {
        Pane pane = new Pane();
        Label label = new Label();
        label.setLayoutX(20);
        label.setLayoutY(5);
        label.setPrefHeight(30);
        label.setText("Education " + (educationIndex + 1));
        label.setStyle(HEADER_LIST_STYLE);
        VBox vBoxInstitutionName = createVBoxWithTextField("Institution Name", educationInfo.getInstitutionName(),
                "ed_institution_name");
        vBoxInstitutionName.setLayoutX(25);
        vBoxInstitutionName.setLayoutY(40);
        VBox vBoxEducationLevel = createVBoxWithTextField("Education Level", educationInfo.getEducationLevel().toString(),
                "ed_education_level");
        vBoxEducationLevel.setLayoutX(25);
        vBoxEducationLevel.setLayoutY(100);
        VBox vBoxMajor = createVBoxWithTextField("Major", educationInfo.getMajor(), "ed_major");
        vBoxMajor.setLayoutX(250);
        vBoxMajor.setLayoutY(100);
        String startDateString = DateUtilities.getDateString(educationInfo.getStartDate(), outFormatter);
        VBox vBoxStartDate = createVBoxWithTextField("Start Date", startDateString, "ed_start_date");
        vBoxStartDate.setLayoutX(25);
        vBoxStartDate.setLayoutY(160);
        String endDateString = DateUtilities.getDateString(educationInfo.getEndDate(), outFormatter);
        VBox vBoxEndDate = createVBoxWithTextField("End Date", endDateString, "ed_end_date");
        vBoxEndDate.setLayoutX(250);
        vBoxEndDate.setLayoutY(160);

        HashMap<String, Object> hmap = new HashMap<String, Object>();
        hmap.put("institution_name", vBoxInstitutionName.getChildren().get(1));
        hmap.put("education_level", vBoxEducationLevel.getChildren().get(1));
        hmap.put("major", vBoxMajor.getChildren().get(1));
        hmap.put("start_date", vBoxStartDate.getChildren().get(1));
        hmap.put("end_date", vBoxEndDate.getChildren().get(1));
        educationInfoMap.put(educationIndex, hmap);

        pane.getChildren().addAll(label, vBoxInstitutionName, vBoxEducationLevel, vBoxMajor, vBoxStartDate, vBoxEndDate);
        return pane;
    }

    private void setUpWorkExperienceForm() {
        for (WorkExperience workExperience: cv.getWorkExperienceList()) {
            Pane workItemPane = createWorkExperienceItemView(workExperience);
            workItemPane.setLayoutY(workIndex * 280);
            workPane.getChildren().add(workItemPane);
            workIndex++;
        }

        //TODO: addMoreExperienceForm
        workPane.setPrefHeight(300 * cv.getWorkExperienceList().size());
        workScrollPane.setPrefHeight(300 * cv.getWorkExperienceList().size());
    }

    private Pane createWorkExperienceItemView(WorkExperience workExperience) {
        Pane pane = new Pane();
        Label label = new Label();
        label.setLayoutX(20);
        label.setLayoutY(5);
        label.setPrefHeight(30);
        label.setText("Experience " + (workIndex + 1));
        label.setStyle(HEADER_LIST_STYLE);
        VBox vBoxCompany = createVBoxWithTextField("Company", workExperience.getCompany(), "");
        vBoxCompany.setLayoutX(25);
        vBoxCompany.setLayoutY(40);
        VBox vBoxPosition = createVBoxWithTextField("Position", workExperience.getPosition(), "");
        vBoxPosition.setLayoutX(250);
        vBoxPosition.setLayoutY(40);
        String startDateString = DateUtilities.getDateString(workExperience.getStartDate(), outFormatter);
        VBox vBoxStartDate = createVBoxWithTextField("Start Date", startDateString, "");
        vBoxStartDate.setLayoutX(25);
        vBoxStartDate.setLayoutY(100);
        String endDateString = DateUtilities.getDateString(workExperience.getEndDate(), outFormatter);
        VBox vBoxEndDate = createVBoxWithTextField("End Date", endDateString, "");
        vBoxEndDate.setLayoutX(250);
        vBoxEndDate.setLayoutY(100);
        VBox vBoxDescription = createVBoxWithTextArea("Description", workExperience.getDescription(), "");
        vBoxDescription.setLayoutX(25);
        vBoxDescription.setLayoutY(160);

        HashMap<String, Object> hmap = new HashMap<String, Object>();
        hmap.put("company", vBoxCompany.getChildren().get(1));
        hmap.put("position", vBoxPosition.getChildren().get(1));
        hmap.put("start_date", vBoxStartDate.getChildren().get(1));
        hmap.put("end_date",vBoxEndDate.getChildren().get(1));
        hmap.put("description", vBoxDescription.getChildren().get(1));
        workExperienceMap.add(hmap);

        pane.getChildren().addAll(label, vBoxCompany, vBoxPosition, vBoxStartDate, vBoxEndDate, vBoxDescription);
        return pane;
    }

    private void setUpSkillForm() {
        Pane pane = new Pane();
        Double yCoor = 0.0;

        // Skill section
        Label skillHeader = new Label();
        skillHeader.setLayoutX(20);
        skillHeader.setLayoutY(5);
        skillHeader.setPrefHeight(30);
        skillHeader.setText("Skills");
        skillHeader.setStyle(HEADER_LIST_STYLE);
        Label skillName = new Label("Skill Name");
        skillName.setPrefSize(200, 15);
        skillName.setLayoutX(25);
        skillName.setLayoutY(40);
        skillName.setStyle(FIELD_LABEL_STYLE);
        Label skillLevel = new Label("Proficiency Level");
        skillLevel.setPrefSize(200, 15);
        skillLevel.setLayoutX(250);
        skillLevel.setLayoutY(40);
        skillLevel.setStyle(FIELD_LABEL_STYLE);
        pane.getChildren().addAll(skillHeader, skillName, skillLevel);
        for (int i = 0; i < cv.getSkills().size(); i++) {
            HBox hBoxSkill = createSkillItemView(cv.getSkills().get(i));
            hBoxSkill.setLayoutX(25);
            yCoor = 60 + i * 30.0;
            hBoxSkill.setLayoutY(yCoor);
            pane.getChildren().add(hBoxSkill);
        }

        // Language Section
        yCoor += 40.0;
        Label languageHeader = new Label();
        languageHeader.setLayoutX(20);
        languageHeader.setLayoutY(yCoor);
        languageHeader.setPrefHeight(30);
        languageHeader.setText("Languages");
        languageHeader.setStyle(HEADER_LIST_STYLE);
        yCoor += 35.0;
        Label languageName = new Label("Language");
        languageName.setPrefSize(200, 15);
        languageName.setLayoutX(25);
        languageName.setLayoutY(yCoor);
        languageName.setStyle(FIELD_LABEL_STYLE);
        Label languageLevel = new Label("Proficiency Level");
        languageLevel.setPrefSize(200, 15);
        languageLevel.setLayoutX(250);
        languageLevel.setLayoutY(yCoor);
        languageLevel.setStyle(FIELD_LABEL_STYLE);
        pane.getChildren().addAll(languageHeader, languageName, languageLevel);
        yCoor += 20.0;
        for (int i = 0; i < cv.getLanguages().size(); i++) {
            HBox hBoxLanguage = createLanguageItemView(cv.getLanguages().get(i));
            hBoxLanguage.setLayoutX(25);
            hBoxLanguage.setLayoutY(yCoor + i * 30.0);
            pane.getChildren().add(hBoxLanguage);
        }

        skillPane.getChildren().add(pane);

        skillPane.setPrefHeight(40 * (cv.getSkills().size() + cv.getLanguages().size()));
        skillScrollPane.setPrefHeight(40 * (cv.getSkills().size() + cv.getLanguages().size()));
    }

    private HBox createSkillItemView(Skill skill) {
        HBox hBoxSkill = new HBox();
        TextField skillTextField = createTextField(skill.getName(), "");
        TextField levelTextField = createTextField(skill.getProficiencyLevel().toString(), "");
        hBoxSkill.setSpacing(25);
        hBoxSkill.getChildren().addAll(skillTextField, levelTextField);

        HashMap<String, Object> hmap = new HashMap<String, Object>();
        hmap.put("name", skillTextField);
        hmap.put("level", levelTextField);
        skillMap.add(hmap);

        return hBoxSkill;
    }

    private HBox createLanguageItemView(Language language) {
        HBox hBoxSkill = new HBox();
        TextField languageTextField = createTextField(language.getName(), "");
        TextField levelTextField = createTextField(language.getProficiencyLevel().toString(), "");
        hBoxSkill.setSpacing(25);
        hBoxSkill.getChildren().addAll(languageTextField, levelTextField);

        HashMap<String, Object> hmap = new HashMap<String, Object>();
        hmap.put("name", languageTextField);
        hmap.put("level", levelTextField);
        languageMap.add(hmap);

        return hBoxSkill;
    }

    private VBox createVBoxWithTextField(String labelName, String content, String textChunk) {
        VBox vBox = new VBox();
        Label label = new Label();
        label.setPrefSize(200, 15);
        label.setStyle(FIELD_LABEL_STYLE);
        label.setText(labelName);
        TextField textField = new TextField();
        textField.setText(content);
        inputFieldToAttributeMap.put(textField, textChunk);
        textField.setPrefSize(200, 20);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    //TODO: Highlighting
                    //Get textchunk from hmap
                    //Create scaled rectangle from textchunk
                    //Show rectangle at specified location
                }
            }
        });
        vBox.getChildren().addAll(label, textField);
        return vBox;
    }

    private TextField createTextField(String content, String textChunk) {
        TextField textField = new TextField();
        textField.setText(content);
        inputFieldToAttributeMap.put(textField, textChunk);
        textField.setPrefSize(200, 20);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    //TODO: Highlighting
                    //Get textchunk from hmap
                    //Create scaled rectangle from textchunk
                    //Show rectangle at specified location
                }
            }
        });
        return textField;
    }

    private VBox createVBoxWithTextArea(String labelName, String content, String textChunk) {
        VBox vBox = new VBox();
        Label label = new Label();
        label.setPrefSize(200, 15);
        label.setStyle(FIELD_LABEL_STYLE);
        label.setText(labelName);
        TextArea textArea = new TextArea();
        textArea.setText(content);
        inputFieldToAttributeMap.put(textArea, textChunk);
        textArea.setPrefSize(425, 100);
        textArea.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    //TODO: Highlighting
                    //Get textchunk from hmap
                    //Create scaled rectangle from textchunk
                    //Show rectangle at specified location
                }
            }
        });
        vBox.getChildren().addAll(label, textArea);
        return vBox;
    }
}
