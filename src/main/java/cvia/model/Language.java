package cvia.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Comparator;

/**
 * Created by andhieka on 10/10/15.
 */
@Embeddable
public class Language implements Comparable<Language>{
    @Column(name = "name")
    private String name;

    @Column(name = "proficiency_level")
    private String proficiencyLevel;

    // Empty Constructor for Hibernate
    public Language() { }

    public Language(String languageName) {
        this.name = languageName;
    }

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

    public int compareTo(Language other) {
        return this.getName().compareTo(other.getName());
    }
}
