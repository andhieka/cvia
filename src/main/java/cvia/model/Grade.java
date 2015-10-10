package cvia.model;

/**
 * Created by andhieka on 10/10/15.
 */
public class Grade implements Comparable<Grade> {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Grade o) {
        return 1;
    }
}
