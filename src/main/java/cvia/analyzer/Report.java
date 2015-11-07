package cvia.analyzer;

import cvia.model.*;

import java.util.List;
import java.util.LinkedList;

/**
 * Created by Michael Limantara on 9/23/2015.
 */
public class Report implements Comparable<Report>{
    private PersonalInfo biodata;

    private CV parsedCV;

    public void setMatchedSkills(List<Skill> matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public void setUnmatchedSkills(List<Skill> unmatchedSkills) {
        this.unmatchedSkills = unmatchedSkills;
    }

    public void setExtraSkills(List<Skill> extraSkills) {
        this.extraSkills = extraSkills;
    }

    private List<Skill> matchedSkills;
    private List<Skill> unmatchedSkills;
    private List<Skill> extraSkills;


    private List<Language> matchedLanguages;
    private List<Language> unmatchedLanguages;
    private List<Language> extraLanguages;



    public List<Skill> getMatchedSkills() {
        return matchedSkills;
    }

    private int score;


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



    public void setParsedCV(CV parsedCV) {
        this.parsedCV = parsedCV;
    }

    public int getLanguageScore() {
        return languageScore;
    }

    public void setLanguageScore(int languageScore) {
        this.languageScore = languageScore;
    }

    public int getSkillScore() {
        return skillScore;
    }

    public void setSkillScore(int skillScore) {
        this.skillScore = skillScore;
    }

    public int getEduationScore() {
        return eduationScore;
    }

    public void setEduationScore(int eduationScore) {
        this.eduationScore = eduationScore;
    }

    public int getWorkScore() {
        return workScore;
    }

    public void setWorkScore(int workScore) {
        this.workScore = workScore;
    }

    private int languageScore;
    private int skillScore;
    private int eduationScore;
    private int workScore;

    public Report(PersonalInfo biodata) {
        this.biodata = biodata;

        this.matchedSkills = new LinkedList<Skill>();
        this.unmatchedSkills = new LinkedList<Skill>();
        this.extraSkills = new LinkedList<Skill>();

        this.matchedLanguages = new LinkedList<Language>();
        this.unmatchedLanguages = new LinkedList<Language>();
        this.extraLanguages = new LinkedList<Language>();
    }



    public void setMatchedLanguages(List<Language> matchedLanguages) {
        this.matchedLanguages = matchedLanguages;
    }

    public void setUnmatchedLanguages(List<Language> unmatchedLanguages) {
        this.unmatchedLanguages = unmatchedLanguages;
    }

    public void setExtraLanguages(List<Language> extraLanguages) {
        this.extraLanguages = extraLanguages;
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
