package cvia.model;

import java.util.Comparator;

/**
 * Created by andhieka on 10/10/15.
 */
public class Language implements Comparable<Language>{
    private String name;
    private String proficiencyLevel;

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
