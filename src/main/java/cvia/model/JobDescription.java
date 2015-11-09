package cvia.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Michael Limantara on 9/23/2015.
 */
@Entity
@Table(name = "jd")
public class JobDescription {
    // Must be used by the Analyzer to match against CV
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "vacancy")
    private Integer vacancy;

    @ElementCollection
    @CollectionTable(name = "jd_skill", joinColumns = {@JoinColumn(name = "jd_id")})
    private List<Skill> requiredSkills;

    @Embedded
    private EducationRequirement minimumEducation;

    private WorkRequirement workRequirement;

    @ElementCollection
    @CollectionTable(name = "jd_language", joinColumns = {@JoinColumn(name = "jd_id")})
    private List<Language> requiredLanguages;

    // For manual verification by humans
    @ElementCollection
    @CollectionTable(name = "jd_responsibility", joinColumns = {@JoinColumn(name = "jd_id")})
    private List<String> responsibilities;



    //index 0: Education
    //index 1: Work Experience
    //index 2: Skills
    //index 3: Language
    private int[] weightage;

    // Empty constructor for Hibernate
    public JobDescription() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVacancy() {
        return vacancy;
    }

    public void setVacancy(Integer vacancy) {
        this.vacancy = vacancy;
    }

    public List<Skill> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<Skill> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public EducationRequirement getMinimumEducation() {
        return minimumEducation;
    }

    public void setMinimumEducation(EducationRequirement minimumEducation) {
        this.minimumEducation = minimumEducation;
    }

    public WorkRequirement getWorkRequirement() {
        return workRequirement;
    }

    public void setWorkRequirement(WorkRequirement workRequirement) {
        this.workRequirement = workRequirement;
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

    public int[] getWeightage() {
        return weightage;
    }

    public void setWeightage(int[] weightage) {
        this.weightage = weightage;
    }

}
