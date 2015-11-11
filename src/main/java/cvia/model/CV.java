package cvia.model;

import cvia.model.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Michael Limantara on 9/23/2015.
 */
@Entity
@Table(name = "cv")
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PersonalInfo personalInfo;

    @ElementCollection
    @CollectionTable(name = "education_info", joinColumns = {@JoinColumn(name = "cv_id")})
    private List<EducationInfo> educationInfoList;

    @ElementCollection
    @CollectionTable(name = "work_experience", joinColumns = {@JoinColumn(name = "cv_id")})
    private List<WorkExperience> workExperienceList;

    @ElementCollection
    @CollectionTable(name = "cv_language", joinColumns = {@JoinColumn(name = "cv_id")})
    private List<Language> languages;

    @ElementCollection
    @CollectionTable(name = "cv_skill", joinColumns = {@JoinColumn(name = "cv_id")})
    private List<Skill> skills;

    @ElementCollection
    @CollectionTable(name = "publication", joinColumns = {@JoinColumn(name = "cv_id")})
    private List<Publication> publications;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private RawSource rawSource;

    @Column(name = "full_text")
    @Type(type = "text")
    private String fullText;

    // Empty Constructor for Hibernate
    public CV() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Publication> getPublications() {
        return publications;
    }

    public void setPublications(List<Publication> publications) {
        this.publications = publications;
    }

    public RawSource getRawSource() {
        return rawSource;
    }

    public void setRawSource(RawSource rawSource) {
        this.rawSource = rawSource;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }
}
