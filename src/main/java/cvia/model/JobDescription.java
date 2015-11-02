package cvia.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Michael Limantara on 9/23/2015.
 */
@Entity
@Table(name = "jd")
public class JobDescription {
    // Must be used by the Analyzer to match agains CV
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "jd_skill", joinColumns = {@JoinColumn(name = "jd_id")})
    private List<Skill> requiredSkills;

    @Embedded
    private EducationInfo minimumEducation;

    @Column(name = "minimum_experience")
    private Float minimumYearsOfWorkExperience;

    @ElementCollection
    @CollectionTable(name = "jd_language", joinColumns = {@JoinColumn(name = "jd_id")})
    private List<Language> requiredLanguages;

    // For manual verification by humans
    @ElementCollection
    @CollectionTable(name = "jd_responsibility")
    private List<String> responsibilities;

    // Empty constructor for Hibernate
    public JobDescription() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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