package cvia.model;

import java.util.List;

/**
 * Created by Michael Limantara on 9/23/2015.
 */
public class JDParseResult {
    // Must be used by the Analyzer to match agains CVParseResult
    private List<Skill> requiredSkills;
    private EducationInfo minimumEducation;
    private Float minimumYearsOfWorkExperience;
    private List<Language> requiredLanguages;


    //temporary constructor for the purpose of testing

    public JDParseResult() {

    }

    // For manual verification by humans
    private List<String> responsibilities;

    public List<Skill> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<Skill> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public EducationInfo getMinimumEducation() {
        return minimumEducation;
    }

    public void setMinimumEducation(EducationInfo minimumEducation) {
        this.minimumEducation = minimumEducation;
    }

    public Float getMinimumYearsOfWorkExperience() {
        return minimumYearsOfWorkExperience;
    }

    public void setMinimumYearsOfWorkExperience(Float minimumYearsOfWorkExperience) {
        this.minimumYearsOfWorkExperience = minimumYearsOfWorkExperience;
    }

    public List<Language> getRequiredLanguages() {
        return requiredLanguages;
    }

    public void setRequiredLanguages(List<Language> requiredLanguages) {
        this.requiredLanguages = requiredLanguages;
    }

    public List<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(List<String> responsibilities) {
        this.responsibilities = responsibilities;
    }
}
