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
public class LanguageMatcherTest {

    CV parsedCV;
    JobDescription parsedJobDescription;
    LanguageMatcher matcher = LanguageMatcher.getInstance();

    public LanguageMatcherTest() {

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


        //match 3 advance

        int totalLanguageScore = matcher.getLanguageScore(parsedCV, parsedJobDescription);

        assertEquals(390, totalLanguageScore);
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
        languages.add(Chinese);

        //extra 1 language

        Language EnglishBasic = new Language("English");
        EnglishBasic.setProficiencyLevel(Language.LanguageProficiency.BASIC);

        requiredLanguages.add(EnglishBasic);
        requiredLanguages.add(Indonesian);
        requiredLanguages.add(French);

        //Match 1 Advanced Need Basic + Exact Match 1 Advanced + 1 Extra (Advanced) Language + 1 Missing (Advanced) Language

        //total = 110 + 130 + 25 - 75

        parsedCV.setLanguages(languages);
        parsedJobDescription.setRequiredLanguages(requiredLanguages);


        int totalLanguageScore = matcher.getLanguageScore(parsedCV, parsedJobDescription);



        assertEquals(190, totalLanguageScore);
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

        Language EnglishBasic = new Language("English");
        EnglishBasic.setProficiencyLevel(Language.LanguageProficiency.BASIC);



        languages.add(EnglishBasic);
        languages.add(Indonesian);
        languages.add(French);
        languages.add(Korean);
        languages.add(Japanese);

        //extra 3 language

        requiredLanguages.add(English);
        requiredLanguages.add(Indonesian);
        requiredLanguages.add(Chinese);
        requiredLanguages.add(German);


        //match 1 basic but need advanced, match 1 advanced, extra 3 languages, missing 2 (advanced) languages

        //100 - 10 + 130 + 75 - 150
        //100

        parsedCV.setLanguages(languages);
        parsedJobDescription.setRequiredLanguages(requiredLanguages);


        int totalLanguageScore = matcher.getLanguageScore(parsedCV, parsedJobDescription);


        assertEquals(145, totalLanguageScore);
    }

}
