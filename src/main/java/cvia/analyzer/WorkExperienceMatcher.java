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

    private final int WORK_EXPERIENCE_BASE = 300;
    private final int WORK_EXPERIENCE_DIFFERENCE = 3; //per month
    private final int WORK_EXPERIENCE_SENIORITY = 1; //1 for each month
    private final int BONUS_CAP = 50; //capped at 50 months

    private boolean hasMatch;

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
        hasMatch = false;
        extraWorkExperience = new LinkedList<WorkExperience>();
    }

    private WorkExperienceMatcher() {
       hasMatch = false;
        extraWorkExperience = new LinkedList<WorkExperience>();
    }

    public int getWorkExperienceScore(CV parsedCV, JobDescription parsedJobDescription) {
        List<WorkExperience> listOfWorkExperience = parsedCV.getWorkExperienceList();

        WorkRequirement workRequirement = parsedJobDescription.getWorkRequirement();

        int minimum = workRequirement.getDuration();

        int total = 0;



        for (WorkExperience we : listOfWorkExperience) {
            total += we.getWorkDuration() * WORK_EXPERIENCE_SENIORITY; //for total work experience of any kind
            int duration = we.getWorkDuration();

            if (isEquivalent(we, workRequirement) && !hasMatch) {
                total += WORK_EXPERIENCE_BASE;

                int diff = duration - minimum;

                if (diff > BONUS_CAP) {
                    total += diff * BONUS_CAP;
                } else {
                    total += diff * WORK_EXPERIENCE_DIFFERENCE;
                }

                hasMatch = true;
            } else {
                extraWorkExperience.add(we);
            }
        }

        return total;

    }

    private boolean isEquivalent(WorkExperience we, WorkRequirement workRequirement) {

        for (String keyword : workRequirement.getKeywords()) {
            if (we.getPosition().toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public List<WorkExperience> getExtraWorkExperience() {
        return this.extraWorkExperience;
    }





}
