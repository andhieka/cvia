package cvia.storage;

import cvia.model.*;
import org.apache.commons.io.FileUtils;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Michael Limantara on 11/2/2015.
 */
public class CVManager {
    private SessionFactory factory;
    private Logger logger;

    public CVManager () {
        setUpConnection();
        logger = Logger.getLogger("CVManager");
    }

    public Long save(CV cv) {
        Session session = factory.openSession();
        Transaction transaction = null;
        Long cvId = null;

        try {
            transaction = session.beginTransaction();
            cvId = (Long) session.save(cv);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, String.format("Error save CV: %s", e.getMessage()));
        } finally {
            session.close();
        }

        return cvId;
    }

    public List<CV> listCV() {
        Session session = factory.openSession();
        Transaction transaction = null;
        List<CV> cvList = null;

        try {
            transaction = session.beginTransaction();
            cvList = session.createQuery("from CV").list();
            for (CV cv: cvList) {
                Hibernate.initialize(cv.getEducationInfoList());
                Hibernate.initialize(cv.getWorkExperienceList());
                Hibernate.initialize(cv.getPublications());
                Hibernate.initialize(cv.getLanguages());
                Hibernate.initialize(cv.getSkills());
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, String.format("Error list CV: %s", e.getMessage()));
        } finally {
            session.close();
        }

        return cvList;
    }

    public CV getCVContentById(Long id) {
        Session session = factory.openSession();
        Transaction transaction = null;
        CV cv = null;

        try {
            transaction = session.beginTransaction();
            cv = session.get(CV.class, id);
            if (cv != null) {
                Hibernate.initialize(cv.getEducationInfoList());
                Hibernate.initialize(cv.getWorkExperienceList());
                Hibernate.initialize(cv.getPublications());
                Hibernate.initialize(cv.getLanguages());
                Hibernate.initialize(cv.getSkills());
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

        return cv;
    }

    public RawSource getCVRawSourceById(Long id) {
        Session session = factory.openSession();
        Transaction transaction = null;
        CV cv = null;

        try {
            transaction = session.beginTransaction();
            cv = session.get(CV.class, id);
            if (cv != null) {
                Hibernate.initialize(cv.getRawSource());
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error getCVRawSourceById:");
            e.printStackTrace();
        } finally {
            session.close();
        }

        return cv.getRawSource();
    }

    public void update(Long id, CV newCV) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            CV cv = session.get(CV.class, id);
            cv.setPersonalInfo(newCV.getPersonalInfo());
            cv.setEducationInfoList(newCV.getEducationInfoList());
            cv.setWorkExperienceList(newCV.getWorkExperienceList());
            cv.setSkills(newCV.getSkills());
            cv.setLanguages(newCV.getLanguages());
            cv.setPublications(newCV.getPublications());
            session.update(cv);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, String.format("Error update CV: %s", e.getMessage()));
        } finally {
            session.close();
        }
    }

    public void delete(Long id) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            CV cv = session.get(CV.class, id);
            session.delete(cv);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, String.format("Error delete CV: %s", e.getMessage()));
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
        ed1.setStartDate(LocalDate.now());
        ed1.setEndDate(LocalDate.now());
        ed1.setEducationLevel(EducationInfo.EducationLevel.UNDERGRADUATE);
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
        workExperience.setStartDate(LocalDate.now());
        workExperience.setEndDate(LocalDate.now());
        workExperience.setDescription("my first work");
        workExperience.setPosition("software engineer");
        List<WorkExperience> workExperienceList = new ArrayList<WorkExperience>();

        RawSource rawSource = new RawSource();
        try {
            File pdf = new File("resume.pdf");
            byte[] rawContent = FileUtils.readFileToByteArray(pdf);
            rawSource.setFilename(pdf.getName());
            rawSource.setRawContent(rawContent);
        } catch (IOException e) {
            e.getMessage();
        }

        CV cv1 = new CV();
        cv1.setPersonalInfo(personalInfo);
        cv1.setEducationInfoList(educationList);
        cv1.setLanguages(languageList);
        cv1.setPublications(publicationList);
        cv1.setSkills(skillList);
        cv1.setWorkExperienceList(workExperienceList);
        cv1.setRawSource(rawSource);

        Long id = cvManager.save(cv1);
        CV cv2 = cvManager.getCVContentById(id);
        System.out.println(cv2.getPersonalInfo().toString());

        for (EducationInfo educationInfo: cv2.getEducationInfoList()) {
            System.out.println(educationInfo.getInstitutionName() + "," + educationInfo.getEducationLevel());
        }

        for (Skill skillInfo: cv2.getSkills()) {
            System.out.println(skillInfo.getName());
        }

        byte[] rawContentInDb = cvManager.getCVRawSourceById(id).getRawContent();
        System.out.println(rawSource.getRawContent().length == rawContentInDb.length);

        cv1.getPersonalInfo().setName("user2");
        cvManager.update(id, cv1);

        CV cv3 = cvManager.getCVContentById(id);
        System.out.println(cv3.getPersonalInfo().toString());
    }

}
