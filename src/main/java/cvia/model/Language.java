package cvia.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Comparator;

/**
 * Created by andhieka on 10/10/15.
 */
@Embeddable
public class Language implements Comparable<Language>{

    public enum LanguageProficiency {
        BASIC, ADVANCED
    }

    @Column(name = "name")
    private String name;

    @Column(name = "proficiency_level")
    private LanguageProficiency proficiencyLevel;

    // Empty Constructor for Hibernate
    public Language() { }

    public Language(String languageName) {
        this.name = languageName;
        this.proficiencyLevel = LanguageProficiency.ADVANCED;
    }

    public LanguageProficiency getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(LanguageProficiency proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public int getProficiencyScore() {
       return this.getProficiencyLevel().equals(LanguageProficiency.ADVANCED) ? 2 : 1;
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
