package cvia.parser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by andhieka on 10/11/15.
 */
public class UniversityBank {
    public static final String FILENAME_UNIVERSITIES_LIST = "world_universities_and_domains.json";
    private ArrayList<String> universityNames = new ArrayList<>();
    private ArrayList<String> universityAcronyms = new ArrayList<>();
    private HashMap<String, ArrayList<String>> acronymToNameDict = new HashMap<>();
    private static UniversityBank instance;

    public static UniversityBank getInstance() {
        if (instance == null) {
            instance = new UniversityBank();
        }
        return instance;
    }

    private UniversityBank() {
        loadData();
    }

    public List<String> getUniversityNames() {
        return universityNames;
    }

    public List<String> getUniversityAcronyms() {
        return universityAcronyms;
    }

    public List<String> universityNamesWithAcronym(String acronym) {
        return acronymToNameDict.get(acronym.toLowerCase());
    }

    // Private methods
    private void loadData() {
        try {
            InputStream inputStream = new FileInputStream(FILENAME_UNIVERSITIES_LIST);
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONArray universities = new JSONArray(tokener);
            for (int i = 0; i < universities.length(); i++) {
                JSONObject university = universities.getJSONObject(i);
                String acronym = university.getString("domain").split("\\.")[0];
                String name = university.getString("name");
                universityNames.add(name);
                universityAcronyms.add(acronym.toLowerCase());
                if (acronymToNameDict.containsKey(acronym.toLowerCase())) {
                    ArrayList<String> entries = acronymToNameDict.get(acronym.toLowerCase());
                    entries.add(name);
                } else {
                    ArrayList<String> entries = new ArrayList<>();
                    entries.add(name);
                    acronymToNameDict.put(acronym.toLowerCase(), entries);
                }

            }
        } catch (FileNotFoundException e) {
            Logger logger = Logger.getGlobal();
            logger.log(Level.SEVERE, String.format("Cannot find languages list (%s)", FILENAME_UNIVERSITIES_LIST), e);
        }
    }
}
