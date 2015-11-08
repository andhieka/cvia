package cvia.analyzer;

import cvia.model.CV;
import cvia.model.JobDescription;
import cvia.model.WorkExperience;
import cvia.model.WorkRequirement;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by johnkevin on 7/11/15.
 */
public class WorkExperienceMatcher {

    private final int WORK_EXPERIENCE_BASE = 100;
    private final int WORK_EXPERIENCE_DIFFERENCE = 50;
    private final int WORK_EXPERIENCE_SENIORITY = 10; //10 for each year of work experience in any field
    private final int BONUS_CAP = 5;

    private List<WorkExperience> matchedWorkExperience;
    private List<WorkExperience> unmatchedWorkExperience;
    private List<WorkExperience> extraWorkExperience;

    private static WorkExperienceMatcher instance;

    public static WorkExperienceMatcher getInstance() {
        if (instance == null) {
            instance = new WorkExperienceMatcher();
        }

        instance.flush();

        return instance;
    }

    private void flush() {
        matchedWorkExperience = new LinkedList<WorkExperience>();
        unmatchedWorkExperience = new LinkedList<WorkExperience>();
        extraWorkExperience = new LinkedList<WorkExperience>();
    }

    private WorkExperienceMatcher() {
        matchedWorkExperience = new LinkedList<WorkExperience>();
        unmatchedWorkExperience = new LinkedList<WorkExperience>();
        extraWorkExperience = new LinkedList<WorkExperience>();
    }

    public int getWorkExperienceScore(CV parsedCV, JobDescription parsedJobDescription) {
        List<WorkExperience> listOfWorkExperience = parsedCV.getWorkExperienceList();

        WorkRequirement workRequirement = parsedJobDescription.getWorkRequirement();

        int minimum = workRequirement.getDuration();

        int total = 0;

        boolean hasMatch = false;

        for (WorkExperience we : listOfWorkExperience) {
            total += we.getWorkDuration() * WORK_EXPERIENCE_SENIORITY; //for total work experience of any kind

            if (isEquivalent(we, workRequirement) && !hasMatch) {
                total += WORK_EXPERIENCE_BASE;

                int duration = we.getWorkDuration();

                int diff = duration - minimum;

                total += diff * WORK_EXPERIENCE_DIFFERENCE;

                hasMatch = true;
            }
        }

        return total;



    }

    private boolean isEquivalent(WorkExperience we, WorkRequirement workRequirement) {

        for (String keyword : workRequirement.getKeywords()) {
            if (we.getPosition().contains(keyword)) {
                return true;
            }
        }

        return false;
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
