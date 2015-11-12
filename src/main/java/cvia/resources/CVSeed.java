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
        personalInfo.setName("Elizabeth Hilliard");
        personalInfo.setContactNumber("");
        personalInfo.setEmail("");
        personalInfo.setAddress("");
        seedCV.setPersonalInfo(personalInfo);

        // Education Info
        List<EducationInfo> educationInfoList = new ArrayList<>();
        EducationInfo educationInfo = new EducationInfo();
        educationInfo.setEducationLevel(EducationInfo.EducationLevel.GRADUATE);
        educationInfo.setInstitutionName("");
        educationInfo.setMajor("Nutrition");
        // - Grade
        Grade grade = new Grade();
        grade.setGrade(5.0f);
        grade.setMaxGrade(5.0f);
        educationInfo.setGrade(grade);
        // - Education Period
        educationInfo.setStartDate(null);
        educationInfo.setEndDate(null);
        educationInfoList.add(educationInfo);
        seedCV.setEducationInfoList(educationInfoList);

        // Work Experience
        List<WorkExperience> workExperienceList = new ArrayList<>();
        WorkExperience workExperience = new WorkExperience();
        workExperience.setCompany("North Dakota State University;");
        workExperience.setPosition("Assistant Professor of Practice/ Program Coordinator for Coordinated Program in Dietetics");
        workExperience.setStartDate(LocalDate.of(2011, 6, 1));
        workExperience.setEndDate(LocalDate.now());
        workExperience.setDescription(
                "• Instructor of HNES 250 – Nutrition Science; HNES 251 – Nutrition, Growth, and Development; HNES 442 – Community Health and Nutrition Education\n" +
                "• Maintain accreditation standards for Commission on Accreditation of Dietetic Education (CADE) for Coordinated Program in Dietetics (CPD)\n" +
                "• Maintain and develop contracts with supervised practice sites\n" +
                "• Manage CPD program budget\n" +
                "• Supervise Student Dietetic Association");
        workExperienceList.add(workExperience);

        WorkExperience workExperience2 = new WorkExperience();
        workExperience2.setCompany("Morganton/Hickory Children’s Developmental Services Agency");
        workExperience2.setPosition("Nutritionist III");
        workExperience2.setStartDate(LocalDate.of(2008, 2, 1));
        workExperience2.setEndDate(LocalDate.of(2011, 6, 1));
        workExperience2.setDescription(
                "• Nutrition assessment and follow-up of medically at risk and developmentally delayed children birth to age 3\n" +
                "• Coordinate nutrition care plans with physicians, specialists, and other health care professionals\n" +
                "• Nutrition counseling for families\n" +
                "• Participate in multidisciplinary developmental evaluations\n" +
                "• Maintain Infant Toddler Certification yearly, which includes maintaining knowledge of\n" +
                "appropriate child development\n" +
                "• Coordinate quarterly statewide CDSA Nutritionist meetings\n" +
                "• Dietetic Intern preceptor for enrichment rotation\n" +
                "• Presentation on nutrition assessment to Lenoir-Rhyne University Students\n" +
                "• Continuing education presentations for CDSA staff");
        workExperienceList.add(workExperience2);

        WorkExperience workExperience3 = new WorkExperience();
        workExperience3.setCompany("TCatawba County Health Department, Women, Infants, and Children Program");
        workExperience3.setPosition("WIC Nutritionist II/ Vendor Coordinator");
        workExperience3.setStartDate(LocalDate.of(2001, 10, 1));
        workExperience3.setEndDate(LocalDate.of(2008, 2, 1));
        workExperience3.setDescription(
                "• Nutrition assessment of infants, and children under age five, including assessment of formula intake and appropriateness\n" +
                "• Nutrition evaluation of children with developmental disabilities\n" +
                "• Nutrition education\n" +
                "• Nutrition counseling of older children and adolescents with nutrition related medical problems,\n" +
                "including eating disorders and obesity\n" +
                "￼￼\n" +
                "• Assessment of feeding issues in children\n" +
                "• Participant in multidisciplinary team meetings\n" +
                "• Chair of nutrition committees\n" +
                "• Coordinator for 35 stores contracted with the WIC Program\n" +
                "• Presentations and public speaking for community organizations and public schools, as well as\n" +
                "multiple radio and TV spots.\n" +
                "• Presentation on nutrition for nursing students at Catawba Valley Community College.\n" +
                "• Dietetic Intern preceptor for Public Health rotation");
        workExperienceList.add(workExperience3);

        seedCV.setWorkExperienceList(workExperienceList);

        // Skills
        List<Skill> skillList = new ArrayList<Skill>();
        skillList.add(new Skill("Dietetic"));
        skillList.add(new Skill("Dietitian"));
        skillList.add(new Skill("Consultant"));
        skillList.add(new Skill("Psychology"));
        skillList.add(new Skill("Nutrition"));
        seedCV.setSkills(skillList);

        // Languages
        List<Language> languageList = new ArrayList<>();
        languageList.add(new Language("English"));
        seedCV.setLanguages(languageList);

        // Raw Source
        RawSource rawSource = new RawSource();
        File file = new File("Elizabeth_Hilliard.pdf");
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
