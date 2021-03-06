package cvia.analyzer;

import cvia.model.CV;
import cvia.model.JobDescription;
import cvia.model.Skill;
import cvia.model.Skill.SkillProficiency;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by johnkevin on 7/11/15.
 */

public class SkillMatcher {

    private final int SKILL_MATCH_SCORE = 100;
    private final int EXTRA_SKILL_SCORE = 25;
    private final int NO_SKILL_PENALTY = 50;
    private final int EXTRA_LEVEL_SCORE = 30;
    private final int BONUS_SCORE = 10;
    private final int PROFICIENCY_PENALTY = 20;
    private final int EXTRA_CAP = 2;

    private List<Skill> matchedSkills;
    private List<Skill> unmatchedSkills;
    private List<Skill> extraSkills;
    private static SkillMatcher instance;

    public static SkillMatcher getInstance() {
        if (instance == null) {
            instance = new SkillMatcher();
        }

        instance.flush();

        return instance;
    }

    private void flush() {
        matchedSkills = new LinkedList<Skill>();
        unmatchedSkills = new LinkedList<Skill>();
        extraSkills = new LinkedList<Skill>();
    }

    private SkillMatcher() {
        matchedSkills = new LinkedList<Skill>();
        unmatchedSkills = new LinkedList<Skill>();
        extraSkills = new LinkedList<Skill>();
    }

    public int getMaximumScore(CV parsedCV, JobDescription parsedJobDescription) {

        int total = 0;

        List<Skill> required = parsedJobDescription.getRequiredSkills();
        total += required.size() * SKILL_MATCH_SCORE;

        List<Skill> extra = parsedCV.getSkills();

        if (extra.size() > required.size()) {
            total += EXTRA_CAP * EXTRA_SKILL_SCORE;
        }

        return total;
    }

    public List<Skill> getMatchedSkills() {
        return this.matchedSkills;
    }


    public List<Skill> getUnmatchedSkills() {
        return this.unmatchedSkills;
    }


    public List<Skill> getExtraSkills() {
        return this.extraSkills;
    }

    public int getSkillScore(CV parsedCV, JobDescription parsedJobDescription) {
        this.flush();

        List<Skill> Skills = parsedCV.getSkills();
        List<Skill> requiredSkills = parsedJobDescription.getRequiredSkills();

        int total = 0;

        int numOfSkills = Skills.size();
        int numOfRequiredSkills = requiredSkills.size();

        Collections.sort(Skills);
        Collections.sort(requiredSkills);

        int SkillPointer = 0;
        int requiredSkillPointer = 0;

        int numOfExtraSkills = 0;
        int numOfUnmatchedSkills = 0;

        while (SkillPointer < numOfSkills && requiredSkillPointer < numOfRequiredSkills) {
            Skill currentSkill = Skills.get(SkillPointer);
            Skill currentRequiredSkill = requiredSkills.get(requiredSkillPointer);

            int compareScore = currentSkill.compareTo(currentRequiredSkill);

            if (compareScore == 0) {
                this.matchedSkills.add(currentSkill);
                SkillPointer++;
                requiredSkillPointer++;

                total += SKILL_MATCH_SCORE;

                int diff = currentSkill.compareProficiency(currentRequiredSkill);

                if (diff <= 0) {
                    total -= (currentSkill.getProficiencyScore() - 1) * EXTRA_LEVEL_SCORE;
                } else {
                    total += (currentRequiredSkill.getProficiencyScore() - 1) * EXTRA_LEVEL_SCORE;
                    total += diff * BONUS_SCORE;
                }

            } else if (compareScore < 0) {
                this.extraSkills.add(currentSkill);
                numOfExtraSkills++;
                SkillPointer++;
            } else {
                this.unmatchedSkills.add(currentRequiredSkill);
                numOfUnmatchedSkills++;
                requiredSkillPointer++;
            }
        }

        numOfExtraSkills += numOfSkills - SkillPointer;

        if (numOfExtraSkills <= EXTRA_CAP) {
            total += numOfExtraSkills * EXTRA_SKILL_SCORE;
        } else {
            total += EXTRA_CAP * EXTRA_SKILL_SCORE;
        }



        addRemainingUnmatchedSkills(requiredSkills, numOfRequiredSkills, requiredSkillPointer);
        addRemainingExtraSkills(Skills, numOfSkills, SkillPointer);

        for (Skill s : unmatchedSkills) {
            total -= NO_SKILL_PENALTY;
            total -= s.getProficiencyScore() * PROFICIENCY_PENALTY;
        }

        return total;

    }

    private void addRemainingExtraSkills(List<Skill> Skills, int numOfSkills, int SkillPointer) {
        while (SkillPointer < numOfSkills) {
            Skill currentSkill = Skills.get(SkillPointer);
            this.extraSkills.add(currentSkill);
            SkillPointer++;
        }
    }

    private void addRemainingUnmatchedSkills(List<Skill> requiredSkills, int numOfRequiredSkills, int requiredSkillPointer) {
        while (requiredSkillPointer < numOfRequiredSkills) {
            Skill currentRequiredSkill = requiredSkills.get(requiredSkillPointer);
            this.unmatchedSkills.add(currentRequiredSkill);
            requiredSkillPointer++;
        }
    }
}
