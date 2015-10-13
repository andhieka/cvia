package cvia.analyzer;

import cvia.model.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Collections;


/**
 * Created by johnkevin on 12/10/15.
 */
public class Matcher {

    private CVParseResult parsedCV;
    private JDParseResult parsedJD;
    private Report report;

    private final int SKILL_MATCH_SCORE = 100;
    private final int LANGUAGE_MATCH_SCORE = 100;

    private final int EXTRA_SKILL_SCORE = 25;
    private final int EXTRA_LANGUAGE_SCORE = 25;

    private final int NO_SKILL_PENALTY = 50;
    private final int NO_LANGUAGE_PENALTY = 75;

    private final Float WORK_EXPERIENCE_BASE = new Float(100);
    private final Float WORK_EXPERIENCE_SCORE = new Float(10);
    private final Float WORK_EXPERIENCE_PENALTY = new Float(20);

    public Matcher(CVParseResult parsedCV, JDParseResult parsedJD) {
        this.parsedCV = parsedCV;
        this.parsedJD = parsedJD;
        PersonalInfo biodata = parsedCV.getPersonalInfo();
        this.report = new Report(biodata);
    }

    public void match() {
        int skillScore = getSkillScore();
        int educationScore = getEducationScore();
        int languageScore = getLanguageScore();
    }

    public Float getWorkExperienceScore() {
        Float minimumWorkExperience = parsedJD.getMinimumYearsOfWorkExperience();
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

    private Float getNumOfYears(Date startDate, Date endDate) {
        //TODO: implement getYear method in Date

        return null;
    }

    public int getLanguageScore() {

        List<Language> languages = parsedCV.getLanguages();
        List<Language> requiredLanguages = parsedJD.getRequiredLanguages();

        int total = 0;

        int numOfLanguages = languages.size();
        int numOfRequiredLanguages = requiredLanguages.size();

        Collections.sort(languages);
        Collections.sort(requiredLanguages);

        int languagePointer = 0;
        int requiredLanguagePointer = 0;

        int numOfExtraLanguages = 0;
        int numOfUnmatchedLanguages = 0;

        while (languagePointer < numOfLanguages && requiredLanguagePointer < numOfRequiredLanguages) {
            Language currentLanguage = languages.get(languagePointer);
            Language currentRequiredLanguage = requiredLanguages.get(requiredLanguagePointer);

            int compareScore = currentLanguage.compareTo(currentRequiredLanguage);

            if (compareScore == 0) {
                this.report.addMatchedLanguage(currentLanguage);
                languagePointer++;
                requiredLanguagePointer++;
                total += LANGUAGE_MATCH_SCORE;
            } else if (compareScore < 0) {
                this.report.addExtraLanguage(currentLanguage);
                numOfExtraLanguages++;
                languagePointer++;
            } else {
                this.report.addUnmatchedLanguage(currentRequiredLanguage);
                numOfUnmatchedLanguages++;
                requiredLanguagePointer++;
            }
        }

        numOfExtraLanguages += numOfLanguages - languagePointer;
        numOfUnmatchedLanguages += numOfRequiredLanguages - requiredLanguagePointer;

        total += numOfExtraLanguages * EXTRA_LANGUAGE_SCORE;
        total -= numOfUnmatchedLanguages * NO_LANGUAGE_PENALTY;

        addRemainingUnmatchedLanguages(requiredLanguages, numOfRequiredLanguages, requiredLanguagePointer);
        addRemainingExtraLanguages(languages, numOfLanguages, languagePointer);



        return total;

    }

    private void addRemainingExtraLanguages(List<Language> languages, int numOfLanguages, int languagePointer) {
        while (languagePointer < numOfLanguages) {
            Language currentLanguage = languages.get(languagePointer);
            this.report.addExtraLanguage(currentLanguage);
            languagePointer++;
        }
    }

    private void addRemainingUnmatchedLanguages(List<Language> requiredLanguages, int numOfRequiredLanguages, int requiredLanguagePointer) {
        while (requiredLanguagePointer < numOfRequiredLanguages) {
            Language currentRequiredLanguage = requiredLanguages.get(requiredLanguagePointer);
            this.report.addUnmatchedLanguage(currentRequiredLanguage);
            requiredLanguagePointer++;
        }
    }

    private int getEducationScore() {


        return 0;
    }

    public int getSkillScore() {

        List<Skill> skills = parsedCV.getSkills();
        List<Skill> requiredSkills = parsedJD.getRequiredSkills();

        int total = 0;

        int numOfSkills = skills.size();
        int numOfRequiredSkills = requiredSkills.size();

        Collections.sort(skills);
        Collections.sort(requiredSkills);

        int skillPointer = 0;
        int requiredSkillPointer = 0;

        int numOfExtraSkills = 0;
        int numOfUnmatchedSkills = 0;

        while (skillPointer < numOfSkills && requiredSkillPointer < numOfRequiredSkills) {
            Skill currentSkill = skills.get(skillPointer);
            Skill currentRequiredSkill = requiredSkills.get(requiredSkillPointer);

            int compareScore = currentSkill.compareTo(currentRequiredSkill);

            if (compareScore == 0) {
                this.report.addMatchedSkill(currentSkill);
                skillPointer++;
                requiredSkillPointer++;
                total += SKILL_MATCH_SCORE;
            } else if (compareScore < 0) {
                this.report.addExtraSkill(currentSkill);
                skillPointer++;
                numOfExtraSkills++;
            } else {
                this.report.addUnMatchedSkill(currentRequiredSkill);
                requiredSkillPointer++;
                numOfUnmatchedSkills++;
            }
        }


        numOfExtraSkills += numOfSkills - skillPointer;
        numOfUnmatchedSkills += numOfRequiredSkills - requiredSkillPointer;

        total += numOfExtraSkills * EXTRA_SKILL_SCORE;
        total -= numOfUnmatchedSkills * NO_SKILL_PENALTY;

        addRemaningUnmatchedSkills(requiredSkills, numOfRequiredSkills, requiredSkillPointer);
        addRemainingExtraSkills(skills, numOfSkills, skillPointer);


        return total;
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


}
