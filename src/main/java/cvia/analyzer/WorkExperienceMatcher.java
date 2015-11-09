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

    public int getMaximumScore(CV myCV) {
        int total = 0;

        total += WORK_EXPERIENCE_BASE;
        total += BONUS_CAP * WORK_EXPERIENCE_DIFFERENCE;
        for (WorkExperience we : myCV.getWorkExperienceList()) {
            total += we.getWorkDuration();
        }

        return total;

    }

    public int getWorkExperienceScore(CV parsedCV, JobDescription parsedJobDescription) {
        List<WorkExperience> listOfWorkExperience = parsedCV.getWorkExperienceList();

        WorkRequirement workRequirement = parsedJobDescription.getWorkRequirement();

        Integer minimum = Integer.valueOf(workRequirement.getDuration());

        if (minimum == null) {
            minimum = 0;
        }

        int total = 0;



        for (WorkExperience we : listOfWorkExperience) {

            int duration = Integer.valueOf(we.getWorkDuration());



            total += duration * WORK_EXPERIENCE_SENIORITY;


            if (isEquivalent(we, workRequirement) && !hasMatch) {
                total += WORK_EXPERIENCE_BASE;

                int diff = duration - minimum;

                if (diff > BONUS_CAP) {
                    total += BONUS_CAP * WORK_EXPERIENCE_DIFFERENCE;
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
