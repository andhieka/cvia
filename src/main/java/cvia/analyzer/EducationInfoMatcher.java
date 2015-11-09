package cvia.analyzer;

import cvia.model.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by johnkevin on 9/11/15.
 */
public class EducationInfoMatcher {
    private final int EDUCATION_BASE = 300;

    private static EducationInfoMatcher instance;

    public static EducationInfoMatcher getInstance() {
        if (instance == null) {
            instance = new EducationInfoMatcher();
        }

        instance.flush();

        return instance;
    }

    private void flush() {

    }

    private EducationInfoMatcher() {

    }

    public int getMaximumScore(JobDescription jobDescription) {
        EducationRequirement minRequirement = jobDescription.getMinimumEducation();

        Grade minimumGrade = minRequirement.getMinimumGrade();

        Float maxRequiredGrade = minimumGrade.getMaxGrade();
        int normalizedMinimumGrade = (int) ((minimumGrade.getGrade() / maxRequiredGrade) * 100);

        return EDUCATION_BASE + (100 - normalizedMinimumGrade);

    }

    public int getEducationScore(CV parsedCV, JobDescription parsedJobDescription) {

        int total = 0;

        List<EducationInfo> educationInfoList = parsedCV.getEducationInfoList();
        Collections.sort(educationInfoList);
        Collections.reverse(educationInfoList);


        EducationRequirement educationRequirement = parsedJobDescription.getMinimumEducation();


        int requirement = educationRequirement.getEducationLevelScore();

        EducationInfo highestEducation = null;

        for (EducationInfo currentEducation : educationInfoList) {
            String major = currentEducation.getMajor();

            int currentScore = currentEducation.getEducationLevelScore();

            if (currentScore >= requirement && hasMatch(major, educationRequirement)) {
                total += EDUCATION_BASE;
                highestEducation = currentEducation;
                break;
            }

        }

        if (highestEducation == null) {
            return 0;
        } else {
            Grade currentGrade = highestEducation.getGrade();
            Grade minimumGrade = educationRequirement.getMinimumGrade();

            Float maxGrade = currentGrade.getMaxGrade();
            int normalizedGrade = (int) ((currentGrade.getGrade() / maxGrade) * 100);

            Float maxRequiredGrade = minimumGrade.getMaxGrade();
            int normalizedMinimumGrade = (int) ((minimumGrade.getGrade() / maxRequiredGrade) * 100);

            total += normalizedGrade - normalizedMinimumGrade;

            return total;
        }

    }

    private boolean hasMatch(String major, EducationRequirement educationRequirement) {

        List<String> acceptedMajors = educationRequirement.getAcceptedMajors();

        for (String accepted : acceptedMajors) {
            if (major.toLowerCase().contains(accepted.toLowerCase())) {
                return true;
            }
        }

        return false;
    }


}
