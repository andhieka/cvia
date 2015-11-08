package cvia.analyzer;

import cvia.model.CV;
import cvia.model.JobDescription;
import cvia.model.WorkExperience;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by johnkevin on 7/11/15.
 */
public class WorkExperienceMatcher {

    private final int WORK_EXPERIENCE_BASE = 100;
    private final int WORK_EXPERIENCE_EXTRA = 25;
    private final int WORK_EXPERIENCE_PENALTY = 50;

    private List<WorkExperience> matchedWorkExperience;
    private List<WorkExperience> unmatchedWorkExperience;
    private List<WorkExperience> extraWorkExperience;

    private static WorkExperienceMatcher instance;

    public static WorkExperienceMatcher getInstance() {
        if (instance == null) {
            instance = new WorkExperienceMatcher();
        }

        return instance;
    }

    private WorkExperienceMatcher() {
        matchedWorkExperience = new LinkedList<WorkExperience>();
        unmatchedWorkExperience = new LinkedList<WorkExperience>();
        extraWorkExperience = new LinkedList<WorkExperience>();
    }

    public int getWorkExperienceScore(CV parsedCV, JobDescription parsedJobDescription) {
        List<WorkExperience> listOfWorkExperience = parsedCV.getWorkExperienceList();

        int minimum = parsedJobDescription.getMinimumYearsOfWorkExperience();

        int total = 0;

        for (WorkExperience we : listOfWorkExperience) {
            total += we.getWorkDuration();
        }

        int difference = minimum - total;

        if (difference > 0) {
            return WORK_EXPERIENCE_BASE + difference * WORK_EXPERIENCE_EXTRA;
        } else if (difference < 0) {
            return WORK_EXPERIENCE_BASE - Math.abs(difference) * WORK_EXPERIENCE_PENALTY;
        } else {
            return WORK_EXPERIENCE_BASE;
        }
    }

    public void setMatchedWorkExperience(List<WorkExperience> matchedWorkExperience) {
        this.matchedWorkExperience = matchedWorkExperience;
    }

    public void setUnmatchedWorkExperience(List<WorkExperience> unmatchedWorkExperience) {
        this.unmatchedWorkExperience = unmatchedWorkExperience;
    }

    public void setExtraWorkExperience(List<WorkExperience> extraWorkExperience) {
        this.extraWorkExperience = extraWorkExperience;
    }




}
