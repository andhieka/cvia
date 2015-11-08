package cvia.ui;

import cvia.model.*;
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
import java.time.format.DateTimeFormatterBuilder;
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
    private Pane personalInfoPane;
    @FXML
    private Pane educationPane;
    @FXML
    private Pane workPane;
    @FXML
    private Pane skillPane;

    private ImageButton btnSave;
    private ImageButton btnCancel;

    private CV cv;
    private Stage stage;
    private HashMap<TextInputControl, String> inputFieldToAttributeMap = new HashMap<TextInputControl, String>();
    private HashMap<String, Object> personalInfoMap = new HashMap<String, Object>();
    private HashMap<Integer, HashMap<String, Object>> educationInfoMap = new HashMap<Integer, HashMap<String, Object>>();
    private Integer educationIndex = 0;
    private Integer workIndex = 0;
    private DateTimeFormatter inFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private DateTimeFormatter outFormatter = DateTimeFormatter.ofPattern("MMM yyyy");

    public void setCV(CV cv) {
        this.cv = cv;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        setPdfImage();
        setUpButton();
        setUpForm();
    }

    private void setPdfImage() {
        try {
            PDDocument document = PDDocument.load("resume.pdf");
            PDPage page1 = (PDPage) document.getDocumentCatalog().getAllPages().get(0);
            BufferedImage bufferedImage = page1.convertToImage(BufferedImage.TYPE_INT_RGB, 60);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            pdfImageView.setFitHeight(image.getHeight());
            pdfImageView.setFitWidth(image.getWidth());
            pdfImageView.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpButton() {
        HBox hBox = new HBox();
        hBox.setLayoutX(425);
        btnSave = new ImageButton("/add.png", BUTTON_SIZE);
        btnSave.setOnAction(event -> {
            saveCV();
        });
        btnCancel = new ImageButton("/add.png", BUTTON_SIZE);
        btnCancel.setOnAction(event -> {
            stage.close();
        });
        hBox.getChildren().addAll(btnSave, btnCancel);
        rightPane.getChildren().addAll(hBox);
    }

    private void saveCV() {

    }

    private void modifyCV() {
        // Personal Info Section
        PersonalInfo personalInfo = cv.getPersonalInfo();
        personalInfo.setName(((TextInputControl) personalInfoMap.get("name")).getText());
        personalInfo.setContactNumber(((TextInputControl) personalInfoMap.get("contact_number")).getText());
        personalInfo.setEmail(((TextInputControl) personalInfoMap.get("email")).getText());
        personalInfo.setAddress(((TextInputControl) personalInfoMap.get("address")).getText());

        // Education Info Section
        List<EducationInfo> educationList = new ArrayList<EducationInfo>();
        for (HashMap<String, Object> educationMap: educationInfoMap.values()) {
            EducationInfo educationInfo = new EducationInfo();
            educationInfo.setInstitutionName(((TextInputControl) educationMap.get("institution_name")).getText());
           // educationInfo.setEducationLevel(((TextInputControl) educationMap.get("education_level")).getText());
            educationInfo.setMajor(((TextInputControl) educationMap.get("major")).getText());
            LocalDate startDate = DateUtilities.dateFromString("01" +
                    ((TextInputControl) educationMap.get("start_date")).getText(), inFormatter);
            educationInfo.setStartDate(startDate);
            LocalDate endDate = DateUtilities.dateFromString("01" +
                    ((TextInputControl) educationMap.get("end_date")).getText(), inFormatter);
            educationInfo.setEndDate(endDate);
            educationList.add(educationInfo);
        }

        cv.setEducationInfoList(educationList);
    }

    private void setUpForm() {
        setUpPersonalInfoForm();
        setUpEducationInfoForm();
        setUpWorkExperienceForm();
        setUpSkillForm();
    }

    private void setUpPersonalInfoForm() {
        VBox vBoxName = createVBoxWithTextField("Name", "");
        vBoxName.setLayoutX(20);
        vBoxName.setLayoutY(5);
        personalInfoMap.put("name", vBoxName.getChildren().get(1));
        VBox vBoxContactNumber = createVBoxWithTextField("Contact Number", "");
        vBoxContactNumber.setLayoutX(20);
        vBoxContactNumber.setLayoutY(65);
        personalInfoMap.put("contact_number", vBoxName.getChildren().get(1));
        VBox vBoxEmail = createVBoxWithTextField("Email", "");
        vBoxEmail.setLayoutX(20);
        vBoxEmail.setLayoutY(125);
        personalInfoMap.put("email", vBoxName.getChildren().get(1));
        VBox vBoxAddress = createVBoxWithTextArea("Address", "");
        vBoxAddress.setLayoutX(20);
        vBoxAddress.setLayoutY(185);
        personalInfoMap.put("address", vBoxName.getChildren().get(1));
        personalInfoPane.getChildren().addAll(vBoxName, vBoxContactNumber, vBoxEmail, vBoxAddress);
    }

    private void setUpEducationInfoForm() {
        Pane educationItemPane = createEducationItemView(null);
        educationPane.getChildren().addAll(educationItemPane);

        //TODO: addMoreEducationForm
    }

    private Pane createEducationItemView(EducationInfo educationInfo) {
        Pane pane = new Pane();
        Label label = new Label();
        label.setLayoutX(20);
        label.setLayoutY(5);
        label.setPrefHeight(30);
        label.setText("Education " + (educationIndex + 1));
        label.setStyle(HEADER_LIST_STYLE);
        VBox vBoxInstitutionName = createVBoxWithTextField("Institution Name", "ed_institution_name");
        vBoxInstitutionName.setLayoutX(25);
        vBoxInstitutionName.setLayoutY(40);
        VBox vBoxEducationLevel = createVBoxWithTextField("Education Level", "ed_education_level");
        vBoxEducationLevel.setLayoutX(25);
        vBoxEducationLevel.setLayoutY(100);
        VBox vBoxMajor = createVBoxWithTextField("Major", "ed_major");
        vBoxMajor.setLayoutX(250);
        vBoxMajor.setLayoutY(100);
        VBox vBoxStartDate = createVBoxWithTextField("Start Date", "ed_start_date");
        vBoxStartDate.setLayoutX(25);
        vBoxStartDate.setLayoutY(160);
        VBox vBoxEndDate = createVBoxWithTextField("End Date", "ed_end_date");
        vBoxEndDate.setLayoutX(250);
        vBoxEndDate.setLayoutY(160);

        HashMap<String, Object> hmap = new HashMap<String, Object>();
        hmap.put("institution_name", vBoxInstitutionName.getChildren().get(1));
        hmap.put("education_level", vBoxEducationLevel.getChildren().get(1));
        hmap.put("major", vBoxMajor.getChildren().get(1));
        hmap.put("start_date", vBoxStartDate.getChildren().get(1));
        hmap.put("end_date", vBoxEndDate.getChildren().get(1));
        educationInfoMap.put(educationIndex, hmap);
        educationIndex++;

        pane.getChildren().addAll(label, vBoxInstitutionName, vBoxEducationLevel, vBoxMajor, vBoxStartDate, vBoxEndDate);
        return pane;
    }

    private void setUpWorkExperienceForm() {
        for (int i = 0; i < 3; i++) {
            Pane workItemPane = createWorkExperienceItemView(null);
            workItemPane.setLayoutY(workIndex * 280);
            workPane.getChildren().add(workItemPane);
            workIndex++;
        }

        //TODO: addMoreExperienceForm
    }

    private Pane createWorkExperienceItemView(WorkExperience workExperience) {
        Pane pane = new Pane();
        Label label = new Label();
        label.setLayoutX(20);
        label.setLayoutY(5);
        label.setPrefHeight(30);
        label.setText("Experience " + (workIndex + 1));
        label.setStyle(HEADER_LIST_STYLE);
        VBox vBoxCompany = createVBoxWithTextField("Company", "");
        vBoxCompany.setLayoutX(25);
        vBoxCompany.setLayoutY(40);
        VBox vBoxPosition = createVBoxWithTextField("Position", "");
        vBoxPosition.setLayoutX(250);
        vBoxPosition.setLayoutY(40);
        VBox vBoxStartDate = createVBoxWithTextField("Start Date", "");
        vBoxStartDate.setLayoutX(25);
        vBoxStartDate.setLayoutY(100);
        VBox vBoxEndDate = createVBoxWithTextField("End Date", "");
        vBoxEndDate.setLayoutX(250);
        vBoxEndDate.setLayoutY(100);
        VBox vBoxDescription = createVBoxWithTextArea("Description", "");
        vBoxDescription.setLayoutX(25);
        vBoxDescription.setLayoutY(160);

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
        for (int i = 0; i < 5; i++) {
            HBox hBoxSkill = createSkillItemView(null);
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
        for (int i = 0; i < 5; i++) {
            HBox hBoxLanguage = createLanguageItemView(null);
            hBoxLanguage.setLayoutX(25);
            hBoxLanguage.setLayoutY(yCoor + i * 30.0);
            pane.getChildren().add(hBoxLanguage);
        }

        skillPane.getChildren().add(pane);
    }

    private HBox createSkillItemView(Skill skill) {
        HBox hBoxSkill = new HBox();
        TextField skillTextField = createTextField("");
        TextField levelTextField = createTextField("");
        hBoxSkill.setSpacing(25);
        hBoxSkill.getChildren().addAll(skillTextField, levelTextField);
        return hBoxSkill;
    }

    private HBox createLanguageItemView(Language language) {
        HBox hBoxSkill = new HBox();
        TextField languageTextField = createTextField("");
        TextField levelTextField = createTextField("");
        hBoxSkill.setSpacing(25);
        hBoxSkill.getChildren().addAll(languageTextField, levelTextField);
        return hBoxSkill;
    }

    private VBox createVBoxWithTextField(String labelName, String textChunk) {
        VBox vBox = new VBox();
        Label label = new Label();
        label.setPrefSize(200, 15);
        label.setStyle(FIELD_LABEL_STYLE);
        label.setText(labelName);
        TextField textField = new TextField();
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

    private TextField createTextField(String textChunk) {
        TextField textField = new TextField();
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

    private VBox createVBoxWithTextArea(String labelName, String textChunk) {
        VBox vBox = new VBox();
        Label label = new Label();
        label.setPrefSize(200, 15);
        label.setStyle(FIELD_LABEL_STYLE);
        label.setText(labelName);
        TextArea textArea = new TextArea();
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
