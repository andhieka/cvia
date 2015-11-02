package cvia.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by andhieka on 10/10/15.
 */
@Embeddable
public class Grade implements Comparable<Grade> {
    @Column(name = "description")
    private String description;
    @Column(name = "grade")
    private Float grade;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int compareTo(Grade o) {
        if (this.grade > o.getGrade()) {
            return 1;
        } else if (this.grade < o.getGrade()) {
            return -1;
        } else {
            return 0;
        }
    }
}
