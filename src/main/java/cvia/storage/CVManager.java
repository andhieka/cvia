package cvia.storage;

import cvia.model.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Michael Limantara on 11/2/2015.
 */
public class CVManager {
    private SessionFactory factory;

    public CVManager () {
        setUpConnection();
    }

    public Long createCV(CVParseResult cvParseResult) {
        Session session = factory.openSession();
        Transaction transaction = null;
        Long cvId = null;

        try {
            transaction = session.beginTransaction();
            cvId = (Long) session.save(cvParseResult);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error addCV:");
            e.printStackTrace();
        } finally {
            session.close();
        }

        return cvId;
    }

    public CVParseResult getCVContentById(Long id) {
        Session session = factory.openSession();
        Transaction transaction = null;
        CVParseResult cvParseResult = null;

        try {
            transaction = session.beginTransaction();
            cvParseResult = session.get(CVParseResult.class, id);
            if (cvParseResult != null) {
                Hibernate.initialize(cvParseResult.getEducationInfoList());
                Hibernate.initialize(cvParseResult.getWorkExperienceList());
                Hibernate.initialize(cvParseResult.getPublications());
                Hibernate.initialize(cvParseResult.getLanguages());
                Hibernate.initialize(cvParseResult.getSkills());
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error getCVContentById:");
            e.printStackTrace();
        } finally {
            session.close();
        }

        return cvParseResult;
    }

    public void updateCV(Long id, CVParseResult newCVParseResult) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            CVParseResult cvParseResult = session.get(CVParseResult.class, id);
            cvParseResult.setPersonalInfo(newCVParseResult.getPersonalInfo());
            cvParseResult.setEducationInfoList(newCVParseResult.getEducationInfoList());
            cvParseResult.setLanguages(newCVParseResult.getLanguages());
            cvParseResult.setPublications(newCVParseResult.getPublications());
            cvParseResult.setSkills(newCVParseResult.getSkills());
            cvParseResult.setWorkExperienceList(newCVParseResult.getWorkExperienceList());
            cvParseResult.setRawContents(newCVParseResult.getRawContents());
            session.update(cvParseResult);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error updateCV:");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteCV(Long id) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            CVParseResult cvParseResult = session.get(CVParseResult.class, id);
            session.delete(cvParseResult);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error deleteCV:");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private void setUpConnection() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void main(String[] args) {
        CVManager cvManager = new CVManager();

        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setName("User1");
        personalInfo.setEmail("testing@example.com");
        personalInfo.setAddress("NUS");
        personalInfo.setContactNumber("81234567");

        EducationInfo ed1 = new EducationInfo();
        ed1.setInstitutionName("NUS");
        ed1.setMajor("CS");
        ed1.setStartDate(new Date());
        ed1.setEndDate(new Date());
        ed1.setEducationLevel("university");
        EducationInfo ed2 = new EducationInfo();
        ed2.setInstitutionName("TJC");
        List<EducationInfo> educationList = new ArrayList<EducationInfo>();
        educationList.add(ed1);
        educationList.add(ed2);

        List<Language> languageList = new ArrayList<Language>();
        languageList.add(new Language("English"));
        languageList.add(new Language("French"));

        Publication pub1 = new Publication();
        pub1.setTitle("title1");
        pub1.setAuthor("author1");
        pub1.setPages("30");
        List<Publication> publicationList = new ArrayList<Publication>();
        publicationList.add(pub1);

        List<Skill> skillList = new ArrayList<Skill>();
        skillList.add(new Skill("Javascript"));
        skillList.add(new Skill("HTML"));

        WorkExperience workExperience = new WorkExperience();
        workExperience.setCompany("company1");
        workExperience.setStartDate(new Date());
        workExperience.setEndDate(new Date());
        workExperience.setDescription("my first work");
        workExperience.setPosition("software engineer");
        List<WorkExperience> workExperienceList = new ArrayList<WorkExperience>();

        CVParseResult cv1 = new CVParseResult();
        cv1.setPersonalInfo(personalInfo);
        cv1.setEducationInfoList(educationList);
        cv1.setLanguages(languageList);
        cv1.setPublications(publicationList);
        cv1.setSkills(skillList);
        cv1.setWorkExperienceList(workExperienceList);
        cv1.setRawContents("link to pdf cv 1");

        Long id = cvManager.createCV(cv1);
        CVParseResult cvParseResult1 = cvManager.getCVContentById(id);
        System.out.println(cvParseResult1.getPersonalInfo().toString());

        for (EducationInfo educationInfo: cvParseResult1.getEducationInfoList()) {
            System.out.println(educationInfo.getInstitutionName() + "," + educationInfo.getEducationLevel());
        }

        for (Skill skillInfo: cvParseResult1.getSkills()) {
            System.out.println(skillInfo.getName());
        }

        cv1.getPersonalInfo().setName("user2");
        cvManager.updateCV(id, cv1);

        CVParseResult cvParseResult2 = cvManager.getCVContentById(id);
        System.out.println(cvParseResult2.getPersonalInfo().toString());
    }
}
