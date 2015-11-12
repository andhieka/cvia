package cvia.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by andhieka on 10/10/15.
 */
@Embeddable
public class Skill implements Comparable<Skill> {

    public enum SkillProficiency {
        BASIC, INTERMEDIATE, ADVANCED
    }

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "proficiency_level")
    private SkillProficiency proficiencyLevel;

    // Empty Constructor for Hibernate
    public Skill() { }

    public Skill(String name) {
        this.name = name;
        this.proficiencyLevel = SkillProficiency.INTERMEDIATE;
    }
    public String getName() {
        return name;
    }

    public Skill setName(String name) {
        this.name = name;
        return this;
    }

    public SkillProficiency getProficiencyLevel() {
        return proficiencyLevel;
    }

    public Skill setProficiencyLevel(SkillProficiency proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
        return this;
    }

    public int compareProficiency (Skill other) {

        int currentLevel = this.getProficiencyScore();
        int otherLevel = other.getProficiencyScore();

        return currentLevel - otherLevel;


    }

    public int getProficiencyScore() {
        if (this.proficiencyLevel.equals(SkillProficiency.ADVANCED)) {
            return 3;
        } else if (this.proficiencyLevel.equals(SkillProficiency.INTERMEDIATE)) {
            return 2;
        } else {
            return 1;
        }
    }

    public int compareTo(Skill o) {
       return this.getName().toLowerCase().compareTo(o.getName().toLowerCase());
    }

    @Override
    public String toString() {
        return name;
    }
}
