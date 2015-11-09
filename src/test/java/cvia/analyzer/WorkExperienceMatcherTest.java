package cvia.analyzer;

import cvia.model.*;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by johnkevin on 8/11/15.
 */
public class WorkExperienceMatcherTest {

    WorkExperienceMatcher matcher = WorkExperienceMatcher.getInstance();
    CV parsedCV;
    JobDescription parsedJobDescription;


    public WorkExperienceMatcherTest() {

    }

    @Test
    public void WorkExperienceMatcherTest1() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        WorkExperience softwareEngineer = new WorkExperience();
        softwareEngineer.setPosition("Software Engineer");
        softwareEngineer.setEndDate(LocalDate.of(2015, 04, 01));
        softwareEngineer.setStartDate(LocalDate.of(2014, 05, 01));
        //12 months

        WorkExperience tukangPel = new WorkExperience();
        tukangPel.setPosition("Tukang Pel");
        tukangPel.setEndDate(LocalDate.of(2013, 05, 01));
        tukangPel.setStartDate(LocalDate.of(2012, 05, 01));
        //13 months

        List<WorkExperience> workExperiences = new ArrayList<WorkExperience>();
        workExperiences.add(softwareEngineer);
        workExperiences.add(tukangPel);

        parsedCV.setWorkExperienceList(workExperiences);

        int workDuration = softwareEngineer.getWorkDuration();

        WorkRequirement softDev = new WorkRequirement();
        softDev.setDuration(10);
        List<String> keywords = new ArrayList<String>();

        keywords.add("software");
        keywords.add("software engineer");
        keywords.add("software developer");
        keywords.add("software engineer");
        keywords.add("programmer");

        softDev.setKeywords(keywords);

        parsedJobDescription.setWorkRequirement(softDev);

        //total months = 25 = 25 * 1 = +25
        //total matched months = 100 + 2 * 3 = 106
        //total = 131

        int score = matcher.getWorkExperienceScore(parsedCV, parsedJobDescription);

        assertEquals(331, score);

    }

    @Test
    public void WorkExperienceMatcherTest2() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        WorkExperience softwareEngineer = new WorkExperience();
        softwareEngineer.setPosition("Software Engineer");
        softwareEngineer.setEndDate(LocalDate.of(2015, 04, 01));
        softwareEngineer.setStartDate(LocalDate.of(2014, 05, 01));
        //12 months

        WorkExperience tukangPel = new WorkExperience();
        tukangPel.setPosition("Tukang Pel");
        tukangPel.setEndDate(LocalDate.of(2013, 05, 01));
        tukangPel.setStartDate(LocalDate.of(2012, 05, 01));
        //13 months

        List<WorkExperience> workExperiences = new ArrayList<WorkExperience>();
        workExperiences.add(softwareEngineer);
        workExperiences.add(tukangPel);

        parsedCV.setWorkExperienceList(workExperiences);

        int workDuration = softwareEngineer.getWorkDuration();

        WorkRequirement softDev = new WorkRequirement();
        softDev.setDuration(20);
        List<String> keywords = new ArrayList<String>();

        keywords.add("software");
        keywords.add("software engineer");
        keywords.add("software developer");
        keywords.add("software engineer");
        keywords.add("programmer");

        softDev.setKeywords(keywords);

        parsedJobDescription.setWorkRequirement(softDev);

        //match 1 but less 8 months

        //total months = 25 = 25 * 1 = +25
        //total matched months = 300 + (-8 * 3) = 276
        //total = 301

        int score = matcher.getWorkExperienceScore(parsedCV, parsedJobDescription);

        assertEquals(301, score);

    }

    @Test
    public void WorkExperienceMatcherTest3() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        WorkExperience softwareEngineer = new WorkExperience();
        softwareEngineer.setPosition("Software Engineer");
        softwareEngineer.setEndDate(LocalDate.of(2015, 04, 01));
        softwareEngineer.setStartDate(LocalDate.of(2010, 05, 01));
        //60 months

        WorkExperience tukangPel = new WorkExperience();
        tukangPel.setPosition("Tukang Pel");
        tukangPel.setEndDate(LocalDate.of(2013, 05, 01));
        tukangPel.setStartDate(LocalDate.of(2012, 05, 01));
        //13 months

        List<WorkExperience> workExperiences = new ArrayList<WorkExperience>();
        workExperiences.add(softwareEngineer);
        workExperiences.add(tukangPel);

        parsedCV.setWorkExperienceList(workExperiences);

        int workDuration = softwareEngineer.getWorkDuration();

        WorkRequirement softDev = new WorkRequirement();
        softDev.setDuration(20);
        List<String> keywords = new ArrayList<String>();

        keywords.add("software");


        keywords.add("programmer");

        softDev.setKeywords(keywords);

        parsedJobDescription.setWorkRequirement(softDev);

        //match 1 but less 8 months

        //total months = 73
        //total matched months = 300 + (40 * 3) = 420
        //total = 493

        int score = matcher.getWorkExperienceScore(parsedCV, parsedJobDescription);

        assertEquals(493, score);
    }

    @Test
    public void WorkExperienceMatcherTest4() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();



        WorkExperience tukangPel = new WorkExperience();
        tukangPel.setPosition("Tukang Pel");
        tukangPel.setEndDate(LocalDate.of(2013, 05, 01));
        tukangPel.setStartDate(LocalDate.of(2012, 05, 01));
        //13 months

        List<WorkExperience> workExperiences = new ArrayList<WorkExperience>();

        workExperiences.add(tukangPel);

        parsedCV.setWorkExperienceList(workExperiences);



        WorkRequirement softDev = new WorkRequirement();
        softDev.setDuration(20);
        List<String> keywords = new ArrayList<String>();

        keywords.add("software");
        keywords.add("software engineer");
        keywords.add("software developer");
        keywords.add("software engineer");
        keywords.add("programmer");

        softDev.setKeywords(keywords);

        parsedJobDescription.setWorkRequirement(softDev);

        //match 1 but less 8 months

        //total months = 13
        //total matched months = 0
        //total = 13

        int score = matcher.getWorkExperienceScore(parsedCV, parsedJobDescription);

        assertEquals(13, score);

    }

    @Test
    public void WorkExperienceMatcherTest5() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();



        WorkExperience tukangPel = new WorkExperience();
        tukangPel.setPosition("Tukang Pel");

        List<WorkExperience> workExperiences = new ArrayList<WorkExperience>();

        workExperiences.add(tukangPel);

        parsedCV.setWorkExperienceList(workExperiences);



        WorkRequirement softDev = new WorkRequirement();
        softDev.setDuration(20);
        List<String> keywords = new ArrayList<String>();

        keywords.add("software");
        keywords.add("software engineer");
        keywords.add("software developer");
        keywords.add("software engineer");
        keywords.add("programmer");

        softDev.setKeywords(keywords);

        parsedJobDescription.setWorkRequirement(softDev);

        //match 1 but less 8 months

        //total months = 13
        //total matched months = 0
        //total = 13

        int score = matcher.getWorkExperienceScore(parsedCV, parsedJobDescription);

        assertEquals(0, score);

    }



    @Test
     public void WorkExperienceMatcherTest6() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        WorkExperience softwareEngineer = new WorkExperience();
        softwareEngineer.setPosition("Software Engineer");
        softwareEngineer.setEndDate(LocalDate.of(2015, 04, 01));
        softwareEngineer.setStartDate(LocalDate.of(2010, 05, 01));
        //60 months

        WorkExperience tukangPel = new WorkExperience();
        tukangPel.setPosition("Tukang Pel");

        //13 months

        List<WorkExperience> workExperiences = new ArrayList<WorkExperience>();
        workExperiences.add(softwareEngineer);
        workExperiences.add(tukangPel);

        parsedCV.setWorkExperienceList(workExperiences);

        int workDuration = softwareEngineer.getWorkDuration();

        WorkRequirement softDev = new WorkRequirement();
        softDev.setDuration(20);
        List<String> keywords = new ArrayList<String>();

        keywords.add("software");


        keywords.add("programmer");

        softDev.setKeywords(keywords);

        parsedJobDescription.setWorkRequirement(softDev);

        //match 1 but less 8 months

        //total months = 60
        //total matched months = 300 + (40 * 3) = 420
        //total = 480

        int score = matcher.getWorkExperienceScore(parsedCV, parsedJobDescription);

        assertEquals(480, score);
    }


    @Test
    public void WorkExperienceMatcherTest7() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        WorkExperience softwareEngineer = new WorkExperience();
        softwareEngineer.setPosition("Software Engineer");


        WorkExperience tukangPel = new WorkExperience();
        tukangPel.setPosition("Tukang Pel");
        tukangPel.setEndDate(LocalDate.of(2013, 05, 01));
        tukangPel.setStartDate(LocalDate.of(2012, 05, 01));

        //13 months

        List<WorkExperience> workExperiences = new ArrayList<WorkExperience>();
        workExperiences.add(softwareEngineer);
        workExperiences.add(tukangPel);

        parsedCV.setWorkExperienceList(workExperiences);

        int workDuration = softwareEngineer.getWorkDuration();

        WorkRequirement softDev = new WorkRequirement();
        softDev.setDuration(20);
        List<String> keywords = new ArrayList<String>();

        keywords.add("software");


        keywords.add("programmer");

        softDev.setKeywords(keywords);

        parsedJobDescription.setWorkRequirement(softDev);

        //match 1 but less 8 months

        //total months = 13
        //total matched months = 300 - 20 * 3 = 240
        //total = 253

        int score = matcher.getWorkExperienceScore(parsedCV, parsedJobDescription);

        assertEquals(253, score);
    }




}
