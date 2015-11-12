package cvia.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
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

    public Language setProficiencyLevel(LanguageProficiency proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
        return this;
    }

    public int getProficiencyScore() {
       return this.getProficiencyLevel().equals(LanguageProficiency.ADVANCED) ? 2 : 1;
    }

    public String getName() {
        return name;
    }

    public Language setName(String name) {
        this.name = name;
        return this;
    }

    public int compareTo(Language other) {
        if (!name.equalsIgnoreCase(other.name)) {
            return name.compareToIgnoreCase(other.name);
        }
        return proficiencyLevel.compareTo(other.proficiencyLevel);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Language)) return false;
        Language other = (Language) o;
        return this.compareTo(other) == 0;
    }

}
