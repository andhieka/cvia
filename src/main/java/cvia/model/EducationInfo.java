package cvia.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by andhieka on 10/10/15.
 */
@Embeddable
public class EducationInfo implements Comparable<EducationInfo> {
    public enum EducationLevel {
        PRIMARY, SECONDARY, HIGHSCHOOL, DIPLOMA, UNDERGRADUATE, GRADUATE, PHD
    }

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "institution_name")
    private String institutionName;

    @Embedded
    private Grade grade;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_level")
    private EducationLevel educationLevel;

    @Column(name = "major")
    private String major;

    // Empty Constructor for Hibernate
    public EducationInfo() { }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(EducationLevel educationLevel) {
        this.educationLevel = educationLevel;
    }

    public int getDuration() {
        return (endDate.getYear()  - startDate.getYear()) * 12 + endDate.getMonthValue() - startDate.getMonthValue() + 1;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getEducationLevelScore() {
        if (this.educationLevel == null) {
            return -1;
        } else if (this.educationLevel.equals(EducationLevel.PRIMARY)) {
            return 0;
        } else if (this.educationLevel.equals(EducationLevel.SECONDARY)) {
            return 1;
        } else if (this.educationLevel.equals(EducationLevel.HIGHSCHOOL)) {
            return 2;
        } else if (this.educationLevel.equals(EducationLevel.DIPLOMA)) {
            return 3;
        } else if (this.educationLevel.equals(EducationLevel.UNDERGRADUATE)) {
            return 4;
        } else if (this.educationLevel.equals(EducationLevel.GRADUATE)) {
            return 5;
        } else { //PHD
            return 6;
        }
    }

    @Override
    public int compareTo(EducationInfo o) {
        return Integer.valueOf(this.getEducationLevelScore()).compareTo(Integer.valueOf(o.getEducationLevelScore()));
    }
}
