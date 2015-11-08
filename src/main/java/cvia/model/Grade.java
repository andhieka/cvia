package cvia.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by andhieka on 10/10/15.
 */
@Embeddable
public class Grade implements Comparable<Grade> {

    @Column(name = "grade")
    private Float grade;

    public Float getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(Float maxGrade) {
        this.maxGrade = maxGrade;
    }

    private Float maxGrade;

    // Empty constructor for Hibernate
    public Grade() { }

    public Grade(Float grade) {
        this.grade = grade;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public int compareTo(Grade o) {
        Float currentGrade = this.getGrade();
        Float currentMaxGrade = this.getMaxGrade();

        Float otherGrade = o.getGrade();
        Float otherMaxGrade = o.getMaxGrade();

        Float thisRatio = currentGrade / currentMaxGrade;
        Float otherRatio = otherGrade / otherMaxGrade;
        return thisRatio.compareTo(otherRatio);
    }
}
