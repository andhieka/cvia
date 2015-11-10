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
    private JobDescription jobDescription;

    public JobDescription getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(JobDescription jobDescription) {
        this.jobDescription = jobDescription;
    }

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

    public int getEducationScore() {
        return educationScore;
    }

    public void setEducationScore(int educationScore) {
        this.educationScore = educationScore;
    }

    public int getWorkScore() {
        return workScore;
    }

    public void setWorkScore(int workScore) {
        this.workScore = workScore;
    }

    private int languageScore;
    private int skillScore;
    private int educationScore;
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
        List<Integer> weightage = jobDescription.getWeightage();

        int total = 0;
        for (int i : weightage) {
            total += i;
        }

        this.score = this.educationScore * weightage.get(0) + this.workScore * weightage.get(1) + this.skillScore * weightage.get(2) + this.languageScore * weightage.get(3);

        return this.score / total;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int compareTo(Report other) {
        return Integer.valueOf(this.getScore()).compareTo(Integer.valueOf(other.getScore()));
    }






}
