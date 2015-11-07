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


    private final Float WORK_EXPERIENCE_BASE = new Float(100);
    private final Float WORK_EXPERIENCE_SCORE = new Float(10);
    private final Float WORK_EXPERIENCE_PENALTY = new Float(20);

    public Matcher(List<CV> parsedCVs, JobDescription parsedJobDescription) {
        this.parsedCVs = parsedCVs;
        this.parsedJobDescription = parsedJobDescription;

        this.reports = new LinkedList<Report>();

        this.languageMatcher = LanguageMatcher.getInstance();
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

    

    private void addRemainingExtraSkills(List<Skill> skills, int numOfSkills, int skillPointer) {
        while (skillPointer < numOfSkills) {
            Skill currentSkill = skills.get(skillPointer);
            this.report.addExtraSkill(currentSkill);
            skillPointer++;
        }
    }

    private void addRemaningUnmatchedSkills(List<Skill> requiredSkills, int numOfRequiredSkills, int requiredSkillPointer) {
        while (requiredSkillPointer < numOfRequiredSkills) {
            Skill currentRequiredSkill = requiredSkills.get(requiredSkillPointer);
            this.report.addUnMatchedSkill(currentRequiredSkill);
            requiredSkillPointer++;
        }
    }

    public Report getReport() {
        return this.report;
    }

    public static void main(String[] args) {
        CV parsedCV = new CV();
        JobDescription parsedJobDescription = new JobDescription();

        Scanner sc = new Scanner(System.in);
        languageSimulator(sc, parsedCV, parsedJobDescription);
        skillSimulator(sc, parsedCV, parsedJobDescription);

        Matcher match = new Matcher(parsedCV, parsedJobDescription);

        System.out.print("Language Score = ");
        int languageScore = match.getLanguageScore();
        System.out.println(languageScore);

        Report r = match.getReport();

        List<Language> matchedLanguages = r.getMatchedLanguages();
        List<Language> unmatchedLanguages = r.getUnmatchedLanguages();
        List<Language> extraLanguages = r.getExtraLanguages();

        System.out.print("Matched Languages = ");
        for (Language l : matchedLanguages) {
            System.out.print(l.getName() + " ");
        }

        System.out.print("\nUnmatched Languages = ");
        for (Language l : unmatchedLanguages) {
            System.out.print(l.getName());
        }

        System.out.print("\nExtra Languages = ");
        for (Language l : extraLanguages) {
            System.out.print(l.getName());
        }



        System.out.print("\nSkill Score = ");
        int skillScore = match.getSkillScore();
        System.out.println(skillScore);

        List<Skill> matchedSkills = r.getMatchedSkills();
        List<Skill> unmatchedSkills = r.getUnmatchedSkills();
        List<Skill> extraSkills = r.getExtraSkills();

        System.out.print("Matched Skills = ");
        for (Skill s : matchedSkills) {
            System.out.print(s.getName() + " ");
        }

        System.out.print("\nUnmatched Skills = ");
        for (Skill s : unmatchedSkills) {
            System.out.print(s.getName() + " ");
        }

        System.out.print("\nExtra Skills = ");
        for (Skill s : extraSkills) {
            System.out.print(s.getName() + " ");
        }

        System.out.println("\n\nTotal Score = " + (languageScore + skillScore));


    }

    private void languageSimulator(Scanner sc, CV parsedCV, JobDescription parsedJobDescription) {


        List<Language> languages = new LinkedList<Language>();
        List<Language> requiredLanguages = new LinkedList<Language>();

        System.out.print("How many required languages: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter required language #" + (i+1) + " : ");
            String language = sc.nextLine();

            Language newLanguage = new Language(language);
            requiredLanguages.add(newLanguage);
        }

        System.out.print("How many languages in the CV: ");
        int m = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < m; i++) {
            System.out.print("Enter language skill #" + (i+1) + " : ");
            String language = sc.nextLine();

            Language newLanguage = new Language(language);
            languages.add(newLanguage);
        }

        parsedCV.setLanguages(languages);
        parsedJobDescription.setRequiredLanguages(requiredLanguages);

        Matcher match = new Matcher(parsedCV, parsedJobDescription);

        System.out.print("Language Score = ");
        System.out.println(match.getLanguageScore());

        Report r = match.getReport();

        List<Language> matchedLanguages = r.getMatchedLanguages();
        List<Language> unmatchedLanguages = r.getUnmatchedLanguages();
        List<Language> extraLanguages = r.getExtraLanguages();


    }

    private void skillSimulator(Scanner sc, CV parsedCV, JobDescription parsedJobDescription) {


        List<Skill> skills = new LinkedList<Skill>();
        List<Skill> requiredSkills = new LinkedList<Skill>();

        System.out.print("\nHow many required skills: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter required skill #" + (i+1) + " :");
            String skill = sc.nextLine();

            Skill newSkill = new Skill(skill);
            requiredSkills.add(newSkill);
        }

        System.out.print("How many skills in the CV: ");
        int m = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < m; i++) {
            System.out.print("Enter skill #" + (i+1) + " :");
            String skill = sc.nextLine();

            Skill newSkill = new Skill(skill);
            skills.add(newSkill);
        }

        parsedCV.setSkills(skills);
        parsedJobDescription.setRequiredSkills(requiredSkills);


    }


}
