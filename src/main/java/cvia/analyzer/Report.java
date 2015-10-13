package cvia.analyzer;

import cvia.model.*;

import java.util.List;
import java.util.LinkedList;

/**
 * Created by Michael Limantara on 9/23/2015.
 */
public class Report implements Comparable<Report>{
    private PersonalInfo biodata;

    public List<Skill> getMatchedSkills() {
        return matchedSkills;
    }

    private List<Skill> matchedSkills;

    public PersonalInfo getBiodata() {
        return biodata;
    }

    public List<Skill> getUnmatchedSkills() {
        return unmatchedSkills;
    }

    public List<Skill> getExtraSkills() {
        return extraSkills;
    }

    public List<Language> getMatchedLanguages() {
        return matchedLanguages;
    }

    public List<Language> getUnmatchedLanguages() {
        return unmatchedLanguages;
    }

    public List<Language> getExtraLanguages() {
        return extraLanguages;
    }

    private List<Skill> unmatchedSkills;
    private List<Skill> extraSkills;
    private List<Language> matchedLanguages;
    private List<Language> unmatchedLanguages;
    private List<Language> extraLanguages;
    private int score;

    public Report(PersonalInfo biodata) {
        this.biodata = biodata;

        this.matchedSkills = new LinkedList<Skill>();
        this.unmatchedSkills = new LinkedList<Skill>();
        this.extraSkills = new LinkedList<Skill>();

        this.matchedLanguages = new LinkedList<Language>();
        this.unmatchedLanguages = new LinkedList<Language>();
        this.extraLanguages = new LinkedList<Language>();
    }

    public void addMatchedSkill(Skill newSkill) {
        this.matchedSkills.add(newSkill);
    }

    public void addExtraSkill(Skill extraSkill) {
        this.extraSkills.add(extraSkill);
    }

    public void addUnMatchedSkill(Skill unmatchedSkill) {
        this.unmatchedSkills.add(unmatchedSkill);
    }

    public void addMatchedLanguage(Language newLanguage) {
        this.matchedLanguages.add(newLanguage);
    }

    public void addExtraLanguage(Language extraLanguage) {
        this.extraLanguages.add(extraLanguage);
    }

    public void addUnmatchedLanguage(Language unmatchedLanguage) {
        this.unmatchedLanguages.add(unmatchedLanguage);
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int compareTo(Report other) {
        if (this.getScore() < other.getScore()) {
            return -1;
        } else if (this.getScore() > other.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }






}
