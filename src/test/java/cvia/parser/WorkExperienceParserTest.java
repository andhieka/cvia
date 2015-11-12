package cvia.parser;

import cvia.model.CV;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by andhieka on 12/11/15.
 */
public class WorkExperienceParserTest {
    private WorkExperienceParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new WorkExperienceParser();
    }

    @After
    public void tearDown() throws Exception {
        parser = null;
    }

    @Test
    public void testYamini() throws Exception {
        String workRoutedContent = "Research     School of Biological Sciences, Nanyang Technological University, Singapore Nov 2012- Nov 2014 \n" +
                "\uF0B7 Studying neurobiological aspects of resilience using a wide range of  behavioral tests  \n" +
                "\uF0B7 Functional studies on brain using electrophysiological techniques   \n" +
                "\uF0B7 Proteomics, mainly western blot to assess protein composition \n" +
                "\uF0B7 Key contributor to set up the laboratory  \n" +
                "\uF0B7 Designed and executed projects to investigate the effects of stress in rats \n" +
                "\uF0B7 Responsible for logistics and maintaining laboratory inventory \n" +
                " \n" +
                "Research Intern                    Genome Institute of Singapore. A*STAR, Singapore            Aug – Oct 2012 \n" +
                "\uF0B7 Worked on bioinformatics related to cancer genetics. \n" +
                "\uF0B7 Data base mining for cancers and drugs that could be used to treat them  \n" +
                "\uF0B7 Created a genome data base for few types of cancers \n" +
                " \n" +
                "Junior Trainer/Presenter     Wildlife Reserves Singapore, Singapore                                 July 2010 – Current \n" +
                "\uF0B7 Presented animal shows and token feeding sessions at the Singapore zoo and Night Safari \n" +
                "\uF0B7 Husbandry and general care taking of the animals  \n" +
                "\uF0B7 Helped write scripts for token feeding trails around the zoo \n" +
                "\uF0B7 Assisted with training of animals to perform on shows \n" +
                " \n" +
                "\uF0B7 Elephant Nature Park, Chiang Mai, Thailand. – Sept 2013:  2 weeks \n" +
                "\uF0B7 Agumbe Rainforest Research Station – Nov 2014 : 2 weeks  \n" +
                "\uF0B7 Madras Crocodile Bank Trust – April 2015 : 2 months  \n" +
                " \n" +
                " \n" +
                "Extracurricular activities \n" +
                " \n" +
                "\uF0B7 Assistant Producer for a local TV series in Singapore- Jan- April 2015  \n" +
                "\uF0B7 Debate Secretary in Students Council in UG college \n" +
                "\uF0B7 Founding member of Third Vision, NGO which is successfully running in India \n" +
                "\uF0B7 First place in poster designing competition in National Students Convention \n" +
                "\uF0B7 Editor of college’s online newspaper “The Midas Touch” at Jerusalem College of Engineering \n" +
                " \n" +
                "Interests \n" +
                " \n" +
                "\uF0B7 Baking \n" +
                "\uF0B7 Tennis \n" +
                "\uF0B7 Part of a dance team that performs regularly at events \n" +
                "\uF0B7 Oil paintings \n" +
                "\uF0B7 Voluntary teaching at SINDA  \n" +
                " \n" +
                "Referees \n" +
                " \n" +
                "Dr. Anthony Lim      Email Id: anthonylim@ethoneuro.com \n" +
                "Research Fellow \n" +
                "School of Biological Sciences \n" +
                "Singapore \n" +
                " \n" +
                "Mr. Marshall Michael                                                  Email Id: marshall.michael@wrs.com.sg \n" +
                "Manager \n" +
                "Animal Presentations \n" +
                "Singapore Zoo  \n" +
                "80, Mandai Lake Road \n" +
                "Singapore ";
        CV cv = new CV();
        parser.setCV(cv);
        parser.parseString(workRoutedContent);


    }
}
