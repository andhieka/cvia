package cvia.model;

import cvia.model.*;

import java.util.List;

/**
 * Created by Michael Limantara on 9/23/2015.
 */
public class CVParseResult {

    private PersonalInfo personalInfo;
    private List<EducationInfo> educationInfoList;
    private List<WorkExperience> workExperienceList;
    private List<Language> languages;
    private List<Skill> skills;
    private List<WorkExperience> workExperiences;
    private String rawContents;

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public List<EducationInfo> getEducationInfoList() {
        return educationInfoList;
    }

    public void setEducationInfoList(List<EducationInfo> educationInfoList) {
        this.educationInfoList = educationInfoList;
    }

    public List<WorkExperience> getWorkExperienceList() {
        return workExperienceList;
    }

    public void setWorkExperienceList(List<WorkExperience> workExperienceList) {
        this.workExperienceList = workExperienceList;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public String getRawContents() {
        return rawContents;
    }

    public void setRawContents(String rawContents) {
        this.rawContents = rawContents;
    }


}
