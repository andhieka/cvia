package cvia.analyzer;

import cvia.model.CV;
import cvia.model.JobDescription;
import cvia.model.Skill;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by johnkevin on 7/11/15.
 */

public class SkillMatcher {

    private final int SKILL_MATCH_SCORE = 100;
    private final int EXTRA_SKILL_SCORE = 25;
    private final int NO_SKILL_PENALTY = 75;

    private List<Skill> matchedSkills;
    private List<Skill> unmatchedSkills;
    private List<Skill> extraSkills;
    private static SkillMatcher instance;

    public static SkillMatcher getInstance() {
        if (instance == null) {
            instance = new SkillMatcher();
        }

        return instance;
    }

    private SkillMatcher() {
        matchedSkills = new LinkedList<Skill>();
        unmatchedSkills = new LinkedList<Skill>();
        extraSkills = new LinkedList<Skill>();
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
        numOfUnmatchedSkills += numOfRequiredSkills - requiredSkillPointer;

        total += numOfExtraSkills * EXTRA_SKILL_SCORE;
        total -= numOfUnmatchedSkills * NO_SKILL_PENALTY;

        addRemainingUnmatchedSkills(requiredSkills, numOfRequiredSkills, requiredSkillPointer);
        addRemainingExtraSkills(Skills, numOfSkills, SkillPointer);

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
