package cvia.analyzer;



import cvia.model.JDParseResult;
import cvia.model.CVParseResult;
import cvia.analyzer.Matcher;

import cvia.model.Language;
import cvia.model.Skill;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by johnkevin on 13/10/15.
 */
public class skillMatcherTester {
    Matcher matcher;
    CVParseResult parsedCV;
    JDParseResult parsedJD;

    public skillMatcherTester() {

    }

    @Test
    public void skillMatcherTester1() {
        parsedCV = new CVParseResult();
        parsedJD = new JDParseResult();

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
        parsedJD.setRequiredSkills(requiredSkills);

        matcher = new Matcher(parsedCV, parsedJD);

        int totalSkillScore = matcher.getSkillScore();

        assertEquals(300, totalSkillScore);
    }

    @Test
    public void skillMatcherTester2() {
        parsedCV = new CVParseResult();
        parsedJD = new JDParseResult();

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
        parsedJD.setRequiredSkills(requiredSkills);

        matcher = new Matcher(parsedCV, parsedJD);

        //200 + 25 - 50 = 175
        int totalSkillScore = matcher.getSkillScore();

        assertEquals(175, totalSkillScore);
    }
    
    @Test
    public void skillMatcherTester3() {
        parsedCV = new CVParseResult();
        parsedJD = new JDParseResult();

        List<Skill> skills = new LinkedList<Skill>();
        List<Skill> requiredSkills = new LinkedList<Skill>();

        Skill java = new Skill("Java");
        Skill ruby = new Skill("Ruby");
        Skill python = new Skill("Python");
        Skill leadership = new Skill("Leadership");
        Skill sql = new Skill("SQL");
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
        skills.add(django);
        skills.add(sleep);

        //extra 2 missing 2 match 3

        parsedCV.setSkills(skills);
        parsedJD.setRequiredSkills(requiredSkills);

        matcher = new Matcher(parsedCV, parsedJD);

        //300 + 50 - 100
        int totalSkillScore = matcher.getSkillScore();

        assertEquals(250, totalSkillScore);
    }

}
