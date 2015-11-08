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
public class SkillMatcherTest {
    SkillMatcher matcher = SkillMatcher.getInstance();
    CV parsedCV;
    JobDescription parsedJobDescription;

    public SkillMatcherTest() {

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

        Skill javaInt = new Skill("Java");
        javaInt.setProficiencyLevel(Skill.SkillProficiency.INTERMEDIATE);
        Skill rubyBasic = new Skill("Ruby");
        rubyBasic.setProficiencyLevel(Skill.SkillProficiency.BASIC);

        skills.add(java); //intermediate java
        skills.add(ruby); //intermediate ruby
        skills.add(python); //intermediate python

        requiredSkills.add(javaInt);
        requiredSkills.add(rubyBasic);


        //1 exact match intermediate 1 match intermediate vs basic and 1 extra
        //100 + 30 + 100 + 10 + 25 = 265

        parsedCV.setSkills(skills);
        parsedJobDescription.setRequiredSkills(requiredSkills);



        int totalSkillScore = matcher.getSkillScore(parsedCV, parsedJobDescription);

        assertEquals(265, totalSkillScore);
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

        Skill javaInt = new Skill("Java");
        javaInt.setProficiencyLevel(Skill.SkillProficiency.INTERMEDIATE);
        Skill rubyAdvanced = new Skill("Ruby");
        rubyAdvanced.setProficiencyLevel(Skill.SkillProficiency.ADVANCED);

        skills.add(java); //intermediate java
        skills.add(ruby); //intermediate ruby
        skills.add(python); //intermediate python

        requiredSkills.add(javaInt); //intermediate java
        requiredSkills.add(rubyAdvanced); //advanced ruby
        requiredSkills.add(django); //intermediate django



        //1 exact match intermediate, 1 match but intermediate vs advanced, 1 extra, 1 less intermediate
        //100 + 30 + 100 + 30 + 1 * 25 - 50 - 40
        //195

        parsedCV.setSkills(skills);
        parsedJobDescription.setRequiredSkills(requiredSkills);


        int totalSkillScore = matcher.getSkillScore(parsedCV, parsedJobDescription);


        assertEquals(195, totalSkillScore);
    }

    @Test
    public void skillMatcherTester3() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        List<Skill> skills = new LinkedList<Skill>();
        List<Skill> requiredSkills = new LinkedList<Skill>();

        Skill java = new Skill("Java");
        Skill makan = new Skill("Makan");
        Skill python = new Skill("Python");
        Skill leadership = new Skill("Leadership");
        Skill sql = new Skill("Zzz");
        Skill django = new Skill("Django");
        Skill sleep = new Skill("Sleep");

        Skill javaInt = new Skill("Java");


        Skill rubyAdvanced = new Skill("Ruby");
        rubyAdvanced.setProficiencyLevel(Skill.SkillProficiency.ADVANCED);


        Skill rubyBasic = new Skill("Ruby");
        rubyBasic.setProficiencyLevel(Skill.SkillProficiency.BASIC);



        java.setProficiencyLevel(Skill.SkillProficiency.ADVANCED);

        skills.add(java); //java advanced
        skills.add(rubyAdvanced); //ruby advanced
        skills.add(python); //python int
        skills.add(leadership); //leadership int
        skills.add(sql); //sql int
        skills.add(makan); //makan int

        requiredSkills.add(javaInt); //intermediate
        requiredSkills.add(rubyBasic); //basic
        requiredSkills.add(python); //python intermediate
        requiredSkills.add(django); //django intermediate
        requiredSkills.add(sleep); //sleep intermediate

        //1 exact match intermediate, 1 match adv vs int, 1 match adv vs basic, 3 extra, 2 unmatched intermediate
        //130 + 130 + 10 + 100 + 20 + 50 - 2 * (50 + 20 + 20)
        //260 + 180 - 180

        parsedCV.setSkills(skills);
        parsedJobDescription.setRequiredSkills(requiredSkills);




        int totalSkillScore = matcher.getSkillScore(parsedCV, parsedJobDescription);

        assertEquals(260, totalSkillScore);
    }

}
