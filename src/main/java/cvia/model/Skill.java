package cvia.model;

/**
 * Created by andhieka on 10/10/15.
 */
public class Skill implements Comparable<Skill> {
    private String name;
    private String yearsOfExperience;
    private String proficiencyLevel;

    public Skill(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(String proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public int compareTo(Skill o) {
       return this.getName().compareTo(o.getName());
    }
}
