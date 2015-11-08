package cvia.analyzer;

import cvia.model.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.Scanner;
import java.util.Date;

/**
 * Created by johnkevin on 12/10/15.
 */
public class Matcher {

    private List<CV> parsedCVs;
    private JobDescription parsedJobDescription;
    private List<Report> reports;

    private LanguageMatcher languageMatcher;
    private SkillMatcher skillMatcher;


    private final Float WORK_EXPERIENCE_BASE = new Float(100);
    private final Float WORK_EXPERIENCE_SCORE = new Float(10);
    private final Float WORK_EXPERIENCE_PENALTY = new Float(20);

    public Matcher(List<CV> parsedCVs, JobDescription parsedJobDescription) {
        this.parsedCVs = parsedCVs;
        this.parsedJobDescription = parsedJobDescription;

        this.reports = new LinkedList<Report>();

        this.languageMatcher = LanguageMatcher.getInstance();
        this.skillMatcher = SkillMatcher.getInstance();

    }

    public void run() {
        for (CV cv : parsedCVs) {
            PersonalInfo biodata = cv.getPersonalInfo();
            Report report = new Report(biodata);
            report.setParsedCV(cv);
            match(cv, report);
            reports.add(report);
        }
    }

    public void match(CV cv, Report report) {
        matchLanguage(cv, report);
        matchSkill(cv, report);
    }

    private void matchSkill(CV cv, Report report) {
        int skillScore = skillMatcher.getSkillScore(cv, parsedJobDescription);
        List<Skill> matchedSkills = skillMatcher.getMatchedSkills();
        List<Skill> unmatchedSkills = skillMatcher.getUnmatchedSkills();
        List<Skill> extraSkills = skillMatcher.getExtraSkills();

        report.setSkillScore(skillScore);
        report.setMatchedSkills(matchedSkills);
        report.setUnmatchedSkills(unmatchedSkills);
        report.setExtraSkills(extraSkills);
    }


    private void matchLanguage(CV cv, Report report) {
        int languageScore = languageMatcher.getLanguageScore(cv, parsedJobDescription);
        List<Language> matchedLanguage = languageMatcher.getMatchedLanguages();
        List<Language> unmatchedLanguage = languageMatcher.getUnmatchedLanguages();
        List<Language> extraLanguage = languageMatcher.getExtraLanguages();

        report.setLanguageScore(languageScore);
        report.setMatchedLanguages(matchedLanguage);
        report.setUnmatchedLanguages(unmatchedLanguage);
        report.setExtraLanguages(extraLanguage);
    }

    /*
    public Float getWorkExperienceScore() {
        Float minimumWorkExperience = parsedJobDescription.getMinimumYearsOfWorkExperience();
        List<WorkExperience> listOfWorkExperience = parsedCV.getWorkExperienceList();

        Float totalWorkExperience = new Float(0);

        for (WorkExperience we : listOfWorkExperience) {
            Date startDate = we.getStartDate();
            Date endDate = we.getEndDate();

            Float numOfYears = getNumOfYears(startDate, endDate);

            totalWorkExperience += numOfYears;
        }

        Float difference = totalWorkExperience - minimumWorkExperience;

        if (difference > 0) {
            return WORK_EXPERIENCE_BASE + difference * WORK_EXPERIENCE_SCORE;
        } else if (difference < 0) {
            return WORK_EXPERIENCE_BASE - Math.abs(difference) * WORK_EXPERIENCE_PENALTY;
        } else {
            return WORK_EXPERIENCE_BASE;
        }

    }

    */






    public static void main(String[] args) {



    }

    private void languageSimulator(Scanner sc, CV parsedCV, JobDescription parsedJobDescription) {


    }

    private void skillSimulator(Scanner sc, CV parsedCV, JobDescription parsedJobDescription) {


    }


}
