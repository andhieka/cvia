package cvia.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by andhieka on 10/10/15.
 */
@Embeddable
public class EducationInfo {
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "institution_name")
    private String institutionName;

    @Embedded
    @AttributeOverride(name = "description", column = @Column(name = "grade_description"))
    private Grade grade;

    @Column(name = "education_level")
    private String educationLevel;

    @Column(name = "major")
    private String major;

    // Empty Constructor for Hibernate
    public EducationInfo() { }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
