package cvia.analyzer;

import cvia.model.CV;
import cvia.model.JobDescription;

import cvia.model.Skill;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by johnkevin on 13/10/15.
 */
public class SkillMatcherTester {
    SkillMatcher matcher = SkillMatcher.getInstance();
    CV parsedCV;
    JobDescription parsedJobDescription;

    public SkillMatcherTester() {

    }

    @Test
    public void skillMatcherTester1() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        List<Skill> skills = new LinkedList<Skill>();
        List<Skill> requiredSkills = new LinkedList<Skill>();

        Skill java = new Skill("Java");
        Skill ruby = new Skill("Ruby");
        Skill python = new Skill("Python");
        Skill leadership = new Skill("Leadership");
        Skill sql = new Skill("SQL");
        Skill django = new Skill("Django");

        skills.add(java);
        skills.add(ruby);
        skills.add(python);

        requiredSkills.add(java);
        requiredSkills.add(ruby);
        requiredSkills.add(python);

        parsedCV.setSkills(skills);
        parsedJobDescription.setRequiredSkills(requiredSkills);



        int totalSkillScore = matcher.getSkillScore(parsedCV, parsedJobDescription);

        assertEquals(300, totalSkillScore);
    }

    @Test
    public void skillMatcherTester2() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        List<Skill> skills = new LinkedList<Skill>();
        List<Skill> requiredSkills = new LinkedList<Skill>();

        Skill java = new Skill("Java");
        Skill ruby = new Skill("Ruby");
        Skill python = new Skill("Python");
        Skill leadership = new Skill("Leadership");
        Skill sql = new Skill("SQL");
        Skill django = new Skill("Django");

        skills.add(java);
        skills.add(ruby);
        skills.add(python);

        requiredSkills.add(java);
        requiredSkills.add(ruby);
        requiredSkills.add(leadership);


        //1 extra 1 missing

        parsedCV.setSkills(skills);
        parsedJobDescription.setRequiredSkills(requiredSkills);

        //200 + 25 - 50 = 175
        int totalSkillScore = matcher.getSkillScore(parsedCV, parsedJobDescription);


        assertEquals(175, totalSkillScore);
    }

    @Test
    public void skillMatcherTester3() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        List<Skill> skills = new LinkedList<Skill>();
        List<Skill> requiredSkills = new LinkedList<Skill>();

        Skill java = new Skill("Java");
        Skill ruby = new Skill("Ruby");
        Skill python = new Skill("Python");
        Skill leadership = new Skill("Leadership");
        Skill sql = new Skill("Zzz");
        Skill django = new Skill("Django");
        Skill sleep = new Skill("Sleep");

        skills.add(java);
        skills.add(ruby);
        skills.add(python);
        skills.add(leadership);
        skills.add(sql);

        requiredSkills.add(java);
        requiredSkills.add(ruby);
        requiredSkills.add(python);
        requiredSkills.add(django);
        requiredSkills.add(sleep);

        //extra 2 missing 2 match 3

        parsedCV.setSkills(skills);
        parsedJobDescription.setRequiredSkills(requiredSkills);



        //300 + 50 - 100
        int totalSkillScore = matcher.getSkillScore(parsedCV, parsedJobDescription);

        assertEquals(250, totalSkillScore);
    }

}
