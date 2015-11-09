package cvia.analyzer;

import cvia.model.*;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by johnkevin on 12/10/15.
 */
public class Matcher {

    private List<CV> parsedCVs;
    private JobDescription parsedJobDescription;
    private List<Report> reports;

    private LanguageMatcher languageMatcher;
    private SkillMatcher skillMatcher;
    private WorkExperienceMatcher workExperienceMatcher;
    private EducationInfoMatcher educationInfoMatcher;

    public Matcher(List<CV> parsedCVs, JobDescription parsedJobDescription) {
        this.parsedCVs = parsedCVs;
        this.parsedJobDescription = parsedJobDescription;

        this.reports = new LinkedList<Report>();

        this.languageMatcher = LanguageMatcher.getInstance();
        this.skillMatcher = SkillMatcher.getInstance();
        this.workExperienceMatcher = WorkExperienceMatcher.getInstance();
        this.educationInfoMatcher = EducationInfoMatcher.getInstance();

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
        matchEducation(cv, report);
        matchWorkExperience(cv, report);
    }

    private void matchWorkExperience(CV cv, Report report) {
        int workScore = workExperienceMatcher.getWorkExperienceScore(cv, parsedJobDescription);
        int maxWorkScore = workExperienceMatcher.getWorkExperienceScore(cv, parsedJobDescription);

        int normalized = (workScore / maxWorkScore) * 100;
        report.setWorkScore(normalized);
    }

    private void matchEducation(CV cv, Report report) {
        int educationScore = educationInfoMatcher.getEducationScore(cv, parsedJobDescription);
        int maxEducationScore = educationInfoMatcher.getEducationScore(cv, parsedJobDescription);

        int normalized = (educationScore / maxEducationScore) * 100;
        report.setEducationScore(normalized);
    }

    private void matchSkill(CV cv, Report report) {
        int skillScore = skillMatcher.getSkillScore(cv, parsedJobDescription);
        int maxSkillScore = skillMatcher.getSkillScore(cv, parsedJobDescription);

        int normalized = (skillScore / maxSkillScore) * 100;

        List<Skill> matchedSkills = skillMatcher.getMatchedSkills();
        List<Skill> unmatchedSkills = skillMatcher.getUnmatchedSkills();
        List<Skill> extraSkills = skillMatcher.getExtraSkills();

        report.setSkillScore(normalized);
        report.setMatchedSkills(matchedSkills);
        report.setUnmatchedSkills(unmatchedSkills);
        report.setExtraSkills(extraSkills);
    }


    private void matchLanguage(CV cv, Report report) {
        int languageScore = languageMatcher.getLanguageScore(cv, parsedJobDescription);
        int maxLanguageScore = languageMatcher.getMaximumScore(cv, parsedJobDescription);
        int normalized = (languageScore / maxLanguageScore) * 100;

        List<Language> matchedLanguage = languageMatcher.getMatchedLanguages();
        List<Language> unmatchedLanguage = languageMatcher.getUnmatchedLanguages();
        List<Language> extraLanguage = languageMatcher.getExtraLanguages();

        report.setLanguageScore(normalized);
        report.setMatchedLanguages(matchedLanguage);
        report.setUnmatchedLanguages(unmatchedLanguage);
        report.setExtraLanguages(extraLanguage);
    }

    public static void main(String[] args) {

    }

}
