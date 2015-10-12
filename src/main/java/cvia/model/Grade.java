package cvia.model;

/**
 * Created by andhieka on 10/10/15.
 */
public class Grade implements Comparable<Grade> {
    private String description;
    private Float grade;

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
