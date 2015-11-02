package cvia.parser;

import cvia.model.CV;
import cvia.model.PersonalInfo;
import cvia.model.Skill;

import java.util.List;

/**
 * Created by Michael Limantara on 9/23/2015.
 */
public class CVParser {
    private PersonalInfoParser personalInfoParser;
    private SkillParser skillParser;
    private String cvContent;

    public CVParser() {
        skillParser = new SkillParser();
    }

    public CV parse(String cv) {
        this.cvContent = cv;

        CV cvParseResult = new CV();
        PersonalInfo personalInfo = parsePersonalInfo();
        cvParseResult.setPersonalInfo(personalInfo);

        // Get skillset
        List<Skill> skills = skillParser.findSkills(cvContent);
        cvParseResult.setSkills(skills);

        return cvParseResult;
    }

    private PersonalInfo parsePersonalInfo() {
        if (personalInfoParser == null) {
            personalInfoParser = new PersonalInfoParser();
        }

        // Extract personal information from cvContent
        // String name = personalInfoParser.parseName();
        String contactNumber = personalInfoParser.parseContactNumber(cvContent);
        String email = personalInfoParser.parseEmail(cvContent);
        String address = personalInfoParser.parseAddress(cvContent);

        // Based on the information extracted, construct personal information object
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setContactNumber(contactNumber);
        personalInfo.setEmail(email);
        personalInfo.setAddress(address);

        return personalInfo;
    }
}
