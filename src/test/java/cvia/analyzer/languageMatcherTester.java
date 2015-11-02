package cvia.analyzer;



import cvia.model.CV;
import cvia.model.JobDescription;

import cvia.model.Language;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by johnkevin on 13/10/15.
 */
public class LanguageMatcherTester {
    Matcher matcher;
    CV parsedCV;
    JobDescription parsedJobDescription;

    public LanguageMatcherTester() {

    }



    @Test
    public void testLanguageMatcher1() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        List<Language> languages = new LinkedList<Language>();
        List<Language> requiredLanguages = new LinkedList<Language>();

        Language English = new Language("English");
        Language Chinese = new Language("Chinese");
        Language Indonesian = new Language("Indonesian");
        Language Japanese = new Language("Japanese");
        Language Korean = new Language("Korean");
        Language French = new Language("French");
        Language German = new Language("German");

        languages.add(English);
        languages.add(Indonesian);
        languages.add(Chinese);

        requiredLanguages.add(English);
        requiredLanguages.add(Indonesian);
        requiredLanguages.add(Chinese);

        parsedCV.setLanguages(languages);
        parsedJobDescription.setRequiredLanguages(requiredLanguages);

        matcher = new Matcher(parsedCV, parsedJobDescription);

        int totalLanguageScore = matcher.getLanguageScore();

        assertEquals(300, totalLanguageScore);
    }

    @Test
    public void testLanguageMatcher2() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        List<Language> languages = new LinkedList<Language>();
        List<Language> requiredLanguages = new LinkedList<Language>();

        Language English = new Language("English");
        Language Chinese = new Language("Chinese");
        Language Indonesian = new Language("Indonesian");
        Language Japanese = new Language("Japanese");
        Language Korean = new Language("Korean");
        Language French = new Language("French");
        Language German = new Language("German");

        languages.add(English);
        languages.add(Indonesian);
        languages.add(French);

        //extra 1 language

        requiredLanguages.add(English);
        requiredLanguages.add(Indonesian);
        requiredLanguages.add(Chinese);

        //missing 1 language

        //total = 200 + 25 (extra 1) - 75 (missing 1) = 150

        parsedCV.setLanguages(languages);
        parsedJobDescription.setRequiredLanguages(requiredLanguages);

        matcher = new Matcher(parsedCV, parsedJobDescription);

        int totalLanguageScore = matcher.getLanguageScore();




        assertEquals(150, totalLanguageScore);
    }


    @Test
    public void testLanguageMatcher3() {
        parsedCV = new CV();
        parsedJobDescription = new JobDescription();

        List<Language> languages = new LinkedList<Language>();
        List<Language> requiredLanguages = new LinkedList<Language>();

        Language English = new Language("English");
        Language Chinese = new Language("Chinese");
        Language Indonesian = new Language("Indonesian");
        Language Japanese = new Language("Japanese");
        Language Korean = new Language("Korean");
        Language French = new Language("French");
        Language German = new Language("German");

        languages.add(English);
        languages.add(Indonesian);
        languages.add(French);
        languages.add(Korean);
        languages.add(Japanese);

        //extra 3 language

        requiredLanguages.add(English);
        requiredLanguages.add(Indonesian);
        requiredLanguages.add(Chinese);
        requiredLanguages.add(German);

        //missing 2 languages

        //total = 200 + 75 (extra 3) - 150 (missing 2) = 125

        parsedCV.setLanguages(languages);
        parsedJobDescription.setRequiredLanguages(requiredLanguages);

        matcher = new Matcher(parsedCV, parsedJobDescription);

        int totalLanguageScore = matcher.getLanguageScore();


        assertEquals(125, totalLanguageScore);
    }

}
