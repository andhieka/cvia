package cvia.resources;

import cvia.model.EducationRequirement;
import cvia.model.JobDescription;
import cvia.model.Language;
import cvia.model.Skill;
import cvia.model.Skill.SkillProficiency;
import cvia.ui.LogicController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by andhieka on 12/11/15.
 */
public class JobDescriptionSeed {
    private static JobDescriptionSeed instance;
    private LogicController logicController = LogicController.getInstance();

    private JobDescriptionSeed() {}

    public static JobDescriptionSeed getInstance() {
        if (instance == null) {
            instance = new JobDescriptionSeed();
        }
        return instance;
    }

    public void execute() {
        logicController.saveJD(makeOne());
    }

    private JobDescription makeOne() {
        JobDescription jd = new JobDescription();

        // Admin Stuff
        jd.setTitle("Holmusk Software Engineer (iOS)");
        jd.setVacancy(2);

        // Responsibilities
        List<String> responsibilities = Arrays.asList(
                "Work on, improve and develop our front-end client with some new features and an impressive user interface.",
                "Work with the backend team to come up with APIs and sync data across platform.",
                "Work across teams in a project based way and be involved in feature creations, product enhancements and experiments throughout the entire development lifecycle, from databases over backend and APIs to frontend and user experience",
                "Be part of a strong team that passionately believes in what it does",
                "Get exceptional career opportunities"
        );
        jd.setResponsibilities(responsibilities);

        // Skill Requirements
        List<Skill> requiredSkills = Arrays.asList(
                new Skill().setName("Swift").setProficiencyLevel(SkillProficiency.INTERMEDIATE),
                new Skill().setName("Objective-C").setProficiencyLevel(SkillProficiency.BASIC),
                new Skill().setName("iOS Development").setProficiencyLevel(SkillProficiency.INTERMEDIATE),
                new Skill().setName("UIKit").setProficiencyLevel(SkillProficiency.INTERMEDIATE),
                new Skill().setName("MVC").setProficiencyLevel(SkillProficiency.INTERMEDIATE)
        );
        jd.setRequiredSkills(requiredSkills);

        // Language Requirements
        List<Language> requiredLanguages = Arrays.asList(
                new Language("English").setProficiencyLevel(Language.LanguageProficiency.ADVANCED)
        );
        jd.setRequiredLanguages(requiredLanguages);

        // Scoring Weightage
        jd.setWeightage(Arrays.asList(1,3,3,2));
        return jd;
    }

    private JobDescription makeTwo() {
        JobDescription jd = new JobDescription();

        // Admin Stuff
        jd.setTitle("Holmusk Dietician");
        jd.setVacancy(1);

        // Responsibilities
        List<String> responsibilities = Arrays.asList(
                "Be the domain expert in nutrition, and provide expert inputs on nutrition in management of chronic diseases",
                "Work with the team to develop features for nutritional programs for diabetes that can be delivered using technology",
                "Work across teams in a project based way and be involved in feature creations, product enhancements and experiments throughout the entire development lifecycle, from databases over backend and APIs to frontend and user experience",
                "Be part of a strong team that passionately believes in what it does",
                "Get exceptional career opportunities"
        );
        jd.setResponsibilities(responsibilities);

        // Skill Requirements
        List<Skill> requiredSkills = Arrays.asList(
                new Skill().setName("Swift").setProficiencyLevel(SkillProficiency.INTERMEDIATE),
                new Skill().setName("Objective-C").setProficiencyLevel(SkillProficiency.BASIC),
                new Skill().setName("iOS Development").setProficiencyLevel(SkillProficiency.INTERMEDIATE),
                new Skill().setName("UIKit").setProficiencyLevel(SkillProficiency.INTERMEDIATE),
                new Skill().setName("MVC").setProficiencyLevel(SkillProficiency.INTERMEDIATE)
        );
        jd.setRequiredSkills(requiredSkills);

        // Language Requirements
        List<Language> requiredLanguages = Arrays.asList(
                new Language("English").setProficiencyLevel(Language.LanguageProficiency.ADVANCED)
        );
        jd.setRequiredLanguages(requiredLanguages);

        // Scoring Weightage
        jd.setWeightage(Arrays.asList(1,3,3,2));
        return jd;
    }


    public static void main(String[] args) {
        JobDescriptionSeed.getInstance().execute();
    }

}
