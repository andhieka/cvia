package cvia.model;

/**
 * Created by andhieka on 10/10/15.
 */
public class Language {
    private String name;

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(String proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String proficiencyLevel;
}
