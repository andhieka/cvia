package cvia.analyzer;

import cvia.model.CV;
import cvia.model.JobDescription;
import cvia.model.Language;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by johnkevin on 4/11/15.
 */
public class LanguageMatcher {

    private final int LANGUAGE_MATCH_SCORE = 100;
    private final int EXTRA_LANGUAGE_SCORE = 25;
    private final int NO_LANGUAGE_PENALTY = 75;

    private List<Language> matchedLanguages;
    private List<Language> unmatchedLanguages;
    private List<Language> extraLanguages;
    private static LanguageMatcher instance;

    public static LanguageMatcher getInstance() {
        if (instance == null) {
            instance = new LanguageMatcher();
        }

        return instance;
    }

    private LanguageMatcher() {
        matchedLanguages = new LinkedList<Language>();
        unmatchedLanguages = new LinkedList<Language>();
        extraLanguages = new LinkedList<Language>();
    }

    public List<Language> getMatchedLanguages() {
        return this.matchedLanguages;
    }


    public List<Language> getUnmatchedLanguages() {
        return this.unmatchedLanguages;
    }


    public List<Language> getExtraLanguages() {
        return this.extraLanguages;
    }

    public int getLanguageScore(CV parsedCV, JobDescription parsedJobDescription) {

        List<Language> languages = parsedCV.getLanguages();
        List<Language> requiredLanguages = parsedJobDescription.getRequiredLanguages();

        int total = 0;

        int numOfLanguages = languages.size();
        int numOfRequiredLanguages = requiredLanguages.size();

        Collections.sort(languages);
        Collections.sort(requiredLanguages);

        int languagePointer = 0;
        int requiredLanguagePointer = 0;

        int numOfExtraLanguages = 0;
        int numOfUnmatchedLanguages = 0;

        while (languagePointer < numOfLanguages && requiredLanguagePointer < numOfRequiredLanguages) {
            Language currentLanguage = languages.get(languagePointer);
            Language currentRequiredLanguage = requiredLanguages.get(requiredLanguagePointer);

            int compareScore = currentLanguage.compareTo(currentRequiredLanguage);

            if (compareScore == 0) {
                this.matchedLanguages.add(currentLanguage);
                languagePointer++;
                requiredLanguagePointer++;
                total += LANGUAGE_MATCH_SCORE;
            } else if (compareScore < 0) {
                this.extraLanguages.add(currentLanguage);
                numOfExtraLanguages++;
                languagePointer++;
            } else {
                this.unmatchedLanguages.add(currentRequiredLanguage);
                numOfUnmatchedLanguages++;
                requiredLanguagePointer++;
            }
        }

        numOfExtraLanguages += numOfLanguages - languagePointer;
        numOfUnmatchedLanguages += numOfRequiredLanguages - requiredLanguagePointer;

        total += numOfExtraLanguages * EXTRA_LANGUAGE_SCORE;
        total -= numOfUnmatchedLanguages * NO_LANGUAGE_PENALTY;

        addRemainingUnmatchedLanguages(requiredLanguages, numOfRequiredLanguages, requiredLanguagePointer);
        addRemainingExtraLanguages(languages, numOfLanguages, languagePointer);



        return total;

    }

    private void addRemainingExtraLanguages(List<Language> languages, int numOfLanguages, int languagePointer) {
        while (languagePointer < numOfLanguages) {
            Language currentLanguage = languages.get(languagePointer);
            this.extraLanguages.add(currentLanguage);
            languagePointer++;
        }
    }

    private void addRemainingUnmatchedLanguages(List<Language> requiredLanguages, int numOfRequiredLanguages, int requiredLanguagePointer) {
        while (requiredLanguagePointer < numOfRequiredLanguages) {
            Language currentRequiredLanguage = requiredLanguages.get(requiredLanguagePointer);
            this.unmatchedLanguages.add(currentRequiredLanguage);
            requiredLanguagePointer++;
        }
    }
}
