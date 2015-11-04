package cvia.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by andhieka on 10/10/15.
 */
@Embeddable
public class Skill implements Comparable<Skill> {
    @Column(name = "name")
    private String name;

    @Column(name = "year_of_experience")
    private String yearsOfExperience;

    @Column(name = "proficiency_level")
    private String proficiencyLevel;

    // Empty Constructor for Hibernate
    public Skill() { }

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

    @Override
    public String toString() {
        return name;
    }
}
