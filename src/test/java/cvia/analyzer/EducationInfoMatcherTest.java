package cvia.analyzer;

import cvia.model.*;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * Created by johnkevin on 9/11/15.
 */
public class EducationInfoMatcherTest {

    EducationInfoMatcher matcher = EducationInfoMatcher.getInstance();
    CV parsedCV;
    JobDescription parsedJobDescription;

    public EducationInfoMatcherTest() {

    }

    @Test
    public void EducationInfoMatcherTest1() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        Grade myGrade = new Grade();
        myGrade.setGrade(new Float(5.0));
        myGrade.setMaxGrade(new Float(5.0));

        Grade minGrade = new Grade();
        minGrade.setGrade(new Float(3.0));
        minGrade.setMaxGrade(new Float(5.0));

        EducationInfo myEducation = new EducationInfo();
        myEducation.setMajor("Computer Science");
        myEducation.setEducationLevel(EducationInfo.EducationLevel.UNDERGRADUATE);
        myEducation.setGrade(myGrade);

        List<String> accepted = new ArrayList<String>();

        accepted.add("computer Science");
        accepted.add("Mathematics");
        accepted.add("Computer Engineering");

        EducationRequirement requirement = new EducationRequirement();
        requirement.setAcceptedMajors(accepted);
        requirement.setMinimumEducation(EducationInfo.EducationLevel.UNDERGRADUATE);
        requirement.setMinimumGrade(minGrade);

        //min = 60/100
        //grade = 100/100

        //total = 340

        List<EducationInfo> myEducations = new ArrayList<EducationInfo>();
        myEducations.add(myEducation);

        parsedCV.setEducationInfoList(myEducations);
        parsedJobDescription.setMinimumEducation(requirement);
        int score = matcher.getEducationScore(parsedCV, parsedJobDescription);

        assertEquals(340, score);

    }

    @Test
    public void EducationInfoMatcherTest2() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        Grade myGrade = new Grade();
        myGrade.setGrade(new Float(5.0));
        myGrade.setMaxGrade(new Float(5.0));

        Grade minGrade = new Grade();
        minGrade.setGrade(new Float(3.0));
        minGrade.setMaxGrade(new Float(5.0));

        EducationInfo myEducation = new EducationInfo();
        myEducation.setMajor("Physics");
        myEducation.setEducationLevel(EducationInfo.EducationLevel.UNDERGRADUATE);
        myEducation.setGrade(myGrade);

        List<String> accepted = new ArrayList<String>();

        accepted.add("computer Science");
        accepted.add("Mathematics");
        accepted.add("Computer Engineering");

        EducationRequirement requirement = new EducationRequirement();
        requirement.setAcceptedMajors(accepted);
        requirement.setMinimumEducation(EducationInfo.EducationLevel.UNDERGRADUATE);
        requirement.setMinimumGrade(minGrade);

        //min = 60/100
        //grade = 100/100

        //total = 340

        List<EducationInfo> myEducations = new ArrayList<EducationInfo>();
        myEducations.add(myEducation);

        parsedCV.setEducationInfoList(myEducations);
        parsedJobDescription.setMinimumEducation(requirement);
        int score = matcher.getEducationScore(parsedCV, parsedJobDescription);

        assertEquals(0, score);

    }


    @Test
     public void EducationInfoMatcherTest3() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        Grade myGrade = new Grade();
        myGrade.setGrade(new Float(3.0));
        myGrade.setMaxGrade(new Float(5.0));

        Grade minGrade = new Grade();
        minGrade.setGrade(new Float(5.0));
        minGrade.setMaxGrade(new Float(5.0));

        EducationInfo myEducation = new EducationInfo();
        myEducation.setMajor("Computer Science");
        myEducation.setEducationLevel(EducationInfo.EducationLevel.UNDERGRADUATE);
        myEducation.setGrade(myGrade);

        List<String> accepted = new ArrayList<String>();

        accepted.add("computer Science");
        accepted.add("Mathematics");
        accepted.add("Computer Engineering");

        EducationRequirement requirement = new EducationRequirement();
        requirement.setAcceptedMajors(accepted);
        requirement.setMinimumEducation(EducationInfo.EducationLevel.UNDERGRADUATE);
        requirement.setMinimumGrade(minGrade);

        //min = 60/100
        //grade = 100/100

        //total = 260

        List<EducationInfo> myEducations = new ArrayList<EducationInfo>();
        myEducations.add(myEducation);

        parsedCV.setEducationInfoList(myEducations);
        parsedJobDescription.setMinimumEducation(requirement);
        int score = matcher.getEducationScore(parsedCV, parsedJobDescription);

        assertEquals(260, score);

    }

    @Test
    public void EducationInfoMatcherTest4() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        Grade myGrade = new Grade();
        myGrade.setGrade(new Float(3.0));
        myGrade.setMaxGrade(new Float(5.0));

        Grade minGrade = new Grade();
        minGrade.setGrade(new Float(5.0));
        minGrade.setMaxGrade(new Float(5.0));

        EducationInfo myEducation = new EducationInfo();
        myEducation.setMajor("Computer Science");
        myEducation.setEducationLevel(EducationInfo.EducationLevel.DIPLOMA);
        myEducation.setGrade(myGrade);

        List<String> accepted = new ArrayList<String>();

        accepted.add("computer Science");
        accepted.add("Mathematics");
        accepted.add("Computer Engineering");

        EducationRequirement requirement = new EducationRequirement();
        requirement.setAcceptedMajors(accepted);
        requirement.setMinimumEducation(EducationInfo.EducationLevel.UNDERGRADUATE);
        requirement.setMinimumGrade(minGrade);

        //min = 60/100
        //grade = 100/100

        //total = 0 since diploma

        List<EducationInfo> myEducations = new ArrayList<EducationInfo>();
        myEducations.add(myEducation);

        parsedCV.setEducationInfoList(myEducations);
        parsedJobDescription.setMinimumEducation(requirement);
        int score = matcher.getEducationScore(parsedCV, parsedJobDescription);

        assertEquals(0, score);

    }

    @Test
    public void EducationInfoMatcherTest5() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        Grade myGrade = new Grade();
        myGrade.setGrade(new Float(5.0));
        myGrade.setMaxGrade(new Float(5.0));

        Grade minGrade = new Grade();
        minGrade.setGrade(new Float(3.0));
        minGrade.setMaxGrade(new Float(5.0));

        EducationInfo myEducation = new EducationInfo();
        myEducation.setMajor("Physics");
        myEducation.setEducationLevel(EducationInfo.EducationLevel.UNDERGRADUATE);
        myEducation.setGrade(myGrade);

        List<String> accepted = new ArrayList<String>();

        accepted.add("computer Science");
        accepted.add("Mathematics");
        accepted.add("Computer Engineering");

        EducationRequirement requirement = new EducationRequirement();

        requirement.setMinimumEducation(EducationInfo.EducationLevel.UNDERGRADUATE);
        requirement.setMinimumGrade(minGrade);

        //min = 60/100
        //grade = 100/100

        //total = 340

        List<EducationInfo> myEducations = new ArrayList<EducationInfo>();
        myEducations.add(myEducation);

        parsedCV.setEducationInfoList(myEducations);
        parsedJobDescription.setMinimumEducation(requirement);
        int score = matcher.getEducationScore(parsedCV, parsedJobDescription);

        assertEquals(340, score);

    }

    @Test
    public void EducationInfoMatcherTest6() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        Grade myGrade = new Grade();
        myGrade.setGrade(new Float(5.0));
        myGrade.setMaxGrade(new Float(5.0));

        Grade minGrade = new Grade();

        EducationInfo myEducation = new EducationInfo();
        myEducation.setMajor("Physics");
        myEducation.setEducationLevel(EducationInfo.EducationLevel.UNDERGRADUATE);
        myEducation.setGrade(myGrade);

        List<String> accepted = new ArrayList<String>();

        accepted.add("computer Science");
        accepted.add("Mathematics");
        accepted.add("Computer Engineering");

        EducationRequirement requirement = new EducationRequirement();

        requirement.setMinimumEducation(EducationInfo.EducationLevel.UNDERGRADUATE);
        requirement.setMinimumGrade(minGrade);

        //min = 0/100
        //grade = 100/100

        //total = 340

        List<EducationInfo> myEducations = new ArrayList<EducationInfo>();
        myEducations.add(myEducation);

        parsedCV.setEducationInfoList(myEducations);
        parsedJobDescription.setMinimumEducation(requirement);
        int score = matcher.getEducationScore(parsedCV, parsedJobDescription);

        assertEquals(400, score);

    }

    @Test
    public void EducationInfoMatcherTest7() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        Grade myGrade = new Grade();


        Grade minGrade = new Grade();

        EducationInfo myEducation = new EducationInfo();
        myEducation.setMajor("Physics");
        myEducation.setEducationLevel(EducationInfo.EducationLevel.UNDERGRADUATE);
        myEducation.setGrade(myGrade);

        List<String> accepted = new ArrayList<String>();

        accepted.add("computer Science");
        accepted.add("Mathematics");
        accepted.add("Computer Engineering");

        EducationRequirement requirement = new EducationRequirement();

        requirement.setMinimumEducation(EducationInfo.EducationLevel.UNDERGRADUATE);
        requirement.setMinimumGrade(minGrade);

        //min = 0/100
        //grade = 100/100

        //total = 340

        List<EducationInfo> myEducations = new ArrayList<EducationInfo>();
        myEducations.add(myEducation);

        parsedCV.setEducationInfoList(myEducations);
        parsedJobDescription.setMinimumEducation(requirement);
        int score = matcher.getEducationScore(parsedCV, parsedJobDescription);

        assertEquals(300, score);

    }

    @Test
    public void EducationInfoMatcherTest8() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        Grade myGrade = new Grade();


        Grade minGrade = new Grade();
        minGrade.setGrade(new Float(3.0));
        minGrade.setMaxGrade(new Float(5.0));

        EducationInfo myEducation = new EducationInfo();
        myEducation.setMajor("Physics");
        myEducation.setEducationLevel(EducationInfo.EducationLevel.UNDERGRADUATE);
        myEducation.setGrade(myGrade);

        List<String> accepted = new ArrayList<String>();

        accepted.add("computer Science");
        accepted.add("Mathematics");
        accepted.add("Computer Engineering");

        EducationRequirement requirement = new EducationRequirement();

        requirement.setMinimumEducation(EducationInfo.EducationLevel.UNDERGRADUATE);
        requirement.setMinimumGrade(minGrade);

        //min = 60/100
        //grade = 100/100

        //total = 340

        List<EducationInfo> myEducations = new ArrayList<EducationInfo>();
        myEducations.add(myEducation);

        parsedCV.setEducationInfoList(myEducations);
        parsedJobDescription.setMinimumEducation(requirement);
        int score = matcher.getEducationScore(parsedCV, parsedJobDescription);

        assertEquals(240, score);

    }




}
