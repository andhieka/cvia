package cvia.resources;

import cvia.model.*;
import cvia.ui.LogicController;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andhieka on 12/11/15.
 */
public class CVSeed {
    private static CVSeed instance;
    private LogicController logicController = LogicController.getInstance();

    public static CVSeed getInstance() {
        if (instance == null) {
            instance = new CVSeed();
        }
        return instance;
    }

    private CVSeed() { }

    public void execute() throws IOException {
        logicController.saveCV(makeOne());
//        logicController.saveCV(makeTwo());
//        logicController.saveCV(makeThree());
//        logicController.saveCV(makeFour());
//        logicController.saveCV(makeFive());
    }

    private CV makeOne() throws IOException {
        CV seedCV = new CV();

        // Personal Info
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setName("Michael Limantara");
        personalInfo.setContactNumber("83677234");
        personalInfo.setEmail("mike.bdg@gmail.com");
        personalInfo.setAddress("22 College Avenue East #17-115 Singapore 138595");
        seedCV.setPersonalInfo(personalInfo);

        // Education Info
        List<EducationInfo> educationInfoList = new ArrayList<>();
        EducationInfo educationInfo = new EducationInfo();
        educationInfo.setEducationLevel(EducationInfo.EducationLevel.UNDERGRADUATE);
        educationInfo.setInstitutionName("National University of Singapore");
        educationInfo.setMajor("Computer Science");
        // - Grade
        Grade grade = new Grade();
        grade.setGrade(4.7f);
        grade.setMaxGrade(5.0f);
        educationInfo.setGrade(grade);
        // - Education Period
        educationInfo.setStartDate(LocalDate.of(2013, 8, 1));
        educationInfo.setEndDate(LocalDate.now());
        educationInfoList.add(educationInfo);
        seedCV.setEducationInfoList(educationInfoList);

        // Work Experience
        List<WorkExperience> workExperienceList = new ArrayList<>();
        WorkExperience workExperience = new WorkExperience();
        workExperience.setCompany("Accenture Pte Ltd");
        workExperience.setPosition("Software Engineering Intern");
        workExperience.setStartDate(LocalDate.of(2015, 5, 1));
        workExperience.setEndDate(LocalDate.of(2015, 7, 1));
        workExperience.setDescription("Part of functional team for a nation-wide authentication system for individuals and businesses");
        workExperienceList.add(workExperience);

        WorkExperience workExperience2 = new WorkExperience();
        workExperience2.setCompany("PT Walden Global Services");
        workExperience2.setPosition("Android Engineering Intern");
        workExperience2.setStartDate(LocalDate.of(2014, 5, 1));
        workExperience2.setEndDate(LocalDate.of(2014, 7, 1));
        workExperience2.setDescription("Learnt skills related to developing Android application using Java and Eclipse Android Development Tools");
        workExperienceList.add(workExperience2);

        WorkExperience workExperience3 = new WorkExperience();
        workExperience3.setCompany("Temasek Junior College");
        workExperience3.setPosition("Relief Teacher");
        workExperience3.setStartDate(LocalDate.of(2013, 1, 1));
        workExperience3.setEndDate(LocalDate.of(2013, 7, 1));
        workExperience3.setDescription("Teach");
        workExperienceList.add(workExperience3);

        seedCV.setWorkExperienceList(workExperienceList);

        // Skills
        List<Skill> skillList = new ArrayList<Skill>();
        skillList.add(new Skill("Javascript"));
        skillList.add(new Skill("Python"));
        skillList.add(new Skill("Java"));
        skillList.add(new Skill("SQL"));
        skillList.add(new Skill("Android"));
        skillList.add(new Skill("Git"));
        skillList.add(new Skill("Microsoft Office"));
        seedCV.setSkills(skillList);

        // Raw Source
        RawSource rawSource = new RawSource();
        File file = new File("Resume Michael Limantara.pdf");
        rawSource.setFilename(file.getName());
        rawSource.setRawContent(IOUtils.toByteArray(new FileReader(file)));
        seedCV.setRawSource(rawSource);

        return seedCV;
    }

    private CV makeTwo() {
        CV seedCV = new CV();

        // Personal Info
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setName("Michael Limantara");
        personalInfo.setContactNumber("83677234");
        personalInfo.setEmail("mike.bdg@gmail.com");
        personalInfo.setAddress("22 College Avenue East #17-115 Singapore 138595");
        seedCV.setPersonalInfo(personalInfo);

        // Education Info
        List<EducationInfo> educationInfoList = new ArrayList<>();
        EducationInfo educationInfo = new EducationInfo();
        educationInfo.setEducationLevel(EducationInfo.EducationLevel.UNDERGRADUATE);
        educationInfo.setInstitutionName("National University of Singapore");
        educationInfo.setMajor("Computer Science");
        // - Grade
        Grade grade = new Grade();
        grade.setGrade(4.7f);
        grade.setMaxGrade(5.0f);
        educationInfo.setGrade(grade);
        // - Education Period
        educationInfo.setStartDate(LocalDate.of(2013, 8, 1));
        educationInfo.setEndDate(LocalDate.now());
        educationInfoList.add(educationInfo);
        seedCV.setEducationInfoList(educationInfoList);

        // Work Experience
        List<WorkExperience> workExperienceList = new ArrayList<>();
        WorkExperience workExperience = new WorkExperience();
        workExperience.setCompany("Accenture Pte Ltd");
        workExperience.setPosition("Software Engineering Intern");
        workExperience.setStartDate(LocalDate.of(2015, 5, 1));
        workExperience.setEndDate(LocalDate.of(2015, 7, 1));
        workExperience.setDescription("Part of functional team for a nation-wide authentication system for individuals and businesses");
        workExperienceList.add(workExperience);

        WorkExperience workExperience2 = new WorkExperience();
        workExperience2.setCompany("PT Walden Global Services");
        workExperience2.setPosition("Android Engineering Intern");
        workExperience2.setStartDate(LocalDate.of(2014, 5, 1));
        workExperience2.setEndDate(LocalDate.of(2014, 7, 1));
        workExperience2.setDescription("Learnt skills related to developing Android application using Java and Eclipse Android Development Tools");
        workExperienceList.add(workExperience2);

        WorkExperience workExperience3 = new WorkExperience();
        workExperience3.setCompany("Temasek Junior College");
        workExperience3.setPosition("Relief Teacher");
        workExperience3.setStartDate(LocalDate.of(2013, 1, 1));
        workExperience3.setEndDate(LocalDate.of(2013, 7, 1));
        workExperience3.setDescription("Teach");
        workExperienceList.add(workExperience3);

        seedCV.setWorkExperienceList(workExperienceList);

        // Skills
        List<Skill> skillList = new ArrayList<Skill>();
        skillList.add(new Skill("Javascript"));
        skillList.add(new Skill("Python"));
        skillList.add(new Skill("Java"));
        skillList.add(new Skill("SQL"));
        skillList.add(new Skill("Android"));
        skillList.add(new Skill("Git"));
        skillList.add(new Skill("Microsoft Office"));
        seedCV.setSkills(skillList);

        // Languages
        List<Language> languageList = new ArrayList<>();
        languageList.add(new Language("English"));
        languageList.add(new Language("Indonesian"));
        seedCV.setLanguages(languageList);

        // Raw Source
        RawSource rawSource = new RawSource();
        File file = new File("Resume Michael Limantara.pdf");
        rawSource.setFilename(file.getName());
        rawSource.setRawContent(IOUtils.toByteArray(new FileReader(file)));
        seedCV.setRawSource(rawSource);

        return seedCV;
    }

    private CV makeThree() {

    }

    private CV makeFour() {

    }

    private CV makeFive() {

    }
}
