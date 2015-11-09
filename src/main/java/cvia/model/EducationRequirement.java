package cvia.model;

import cvia.model.EducationInfo.EducationLevel;

import javax.persistence.*;
import java.util.List;

/**
 * Created by johnkevin on 9/11/15.
 */
@Entity
@Table(name = "education_requirement")
public class EducationRequirement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "minimum_education")
    private EducationLevel minimumEducation;

    @ElementCollection
    @CollectionTable(name = "accepted_major", joinColumns = {@JoinColumn(name = "edu_req_id")})
    private List<String> acceptedMajors;

    @Embedded
    private Grade minimumGrade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Empty constructor for Hibernate
    public EducationRequirement() {

    }

    public EducationLevel getMinimumEducation() {
        return minimumEducation;
    }

    public void setMinimumEducation(EducationLevel minimumEducation) {
        this.minimumEducation = minimumEducation;
    }

    public List<String> getAcceptedMajors() {
        return acceptedMajors;
    }

    public void setAcceptedMajors(List<String> acceptedMajors) {
        this.acceptedMajors = acceptedMajors;
    }

    public Grade getMinimumGrade() {
        return minimumGrade;
    }

    public void setMinimumGrade(Grade minimumGrade) {
        this.minimumGrade = minimumGrade;
    }

    public int getEducationLevelScore() {
        if (this.minimumEducation.equals(EducationLevel.PRIMARY)) {
            return 0;
        } else if (this.minimumEducation.equals(EducationLevel.SECONDARY)) {
            return 1;
        } else if (this.minimumEducation.equals(EducationLevel.HIGHSCHOOL)) {
            return 2;
        } else if (this.minimumEducation.equals(EducationLevel.DIPLOMA)) {
            return 3;
        } else if (this.minimumEducation.equals(EducationLevel.UNDERGRADUATE)) {
            return 4;
        } else if (this.minimumEducation.equals(EducationLevel.GRADUATE)) {
            return 5;
        } else { //PHD
            return 6;
        }
    }
}
