package cvia.storage;

import cvia.model.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Michael Limantara on 11/3/2015.
 */
public class JDManager {
    private SessionFactory factory;
    private Logger logger;

    public JDManager() {
        setUpConnection();
        logger = Logger.getLogger("JDManager");
    }

    public Long save(JobDescription jobDescription) {
        Session session = factory.openSession();
        Transaction transaction = null;
        Long jobDescriptionId = null;

        try {
            transaction = session.beginTransaction();
            jobDescriptionId = (Long) session.save(jobDescription);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, String.format("Error save JD: %s", e.getMessage()));
        } finally {
            session.close();
        }

        return jobDescriptionId;
    }

    public JobDescription getJobDescriptionDetailById(Long id) {
        Session session = factory.openSession();
        Transaction transaction = null;
        JobDescription jobDescription = null;

        try {
            transaction = session.beginTransaction();
            jobDescription = session.get(JobDescription.class, id);
            if (jobDescription != null) {
                Hibernate.initialize(jobDescription.getRequiredLanguages());
                Hibernate.initialize(jobDescription.getRequiredSkills());
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error getJobDescriptionDetailById:");
            e.printStackTrace();
        } finally {
            session.close();
        }

        return jobDescription;
    }

    public List<JobDescription> listJD() {
        Session session = factory.openSession();
        Transaction transaction = null;
        List<JobDescription> jdList = null;

        try {
            transaction = session.beginTransaction();
            jdList = session.createQuery("from JobDescription").list();
            for (JobDescription jd: jdList) {
                Hibernate.initialize(jd.getRequiredSkills());
                Hibernate.initialize(jd.getRequiredLanguages());
                Hibernate.initialize(jd.getMinimumEducation());
                Hibernate.initialize(jd.getWorkRequirement());
                Hibernate.initialize(jd.getResponsibilities());
                Hibernate.initialize(jd.getWeightage());
            }
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, String.format("Error list JD: %s", e.getMessage()));
        } finally {
            session.close();
        }

        return jdList;
    }

    public void update(Long id, JobDescription newJobDescription) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            JobDescription jobDescription = session.get(JobDescription.class, id);
            jobDescription.setMinimumEducation(newJobDescription.getMinimumEducation());
            jobDescription.setRequiredLanguages(newJobDescription.getRequiredLanguages());
            jobDescription.setRequiredSkills(newJobDescription.getRequiredSkills());
            jobDescription.setResponsibilities(newJobDescription.getResponsibilities());
            session.update(jobDescription);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, String.format("Error save JD: %s", e.getMessage()));
        } finally {
            session.close();
        }
    }

    public void delete(Long id) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            JobDescription jobDescription = session.get(JobDescription.class, id);
            session.delete(jobDescription);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, String.format("Error save JD: %s", e.getMessage()));
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
        JDManager jdManager = new JDManager();

        List<String> responsibilities = new ArrayList<String>();
        responsibilities.add("Responsibility 1");
        responsibilities.add("Responsibility 2");
        responsibilities.add("Responsibility 3");

        List<Skill> requiredSkills = new ArrayList<Skill>();
        requiredSkills.add(new Skill("Javascript"));
        requiredSkills.add(new Skill("HTML"));

        List<Language> languages = new ArrayList<Language>();
        languages.add(new Language("English"));
        languages.add(new Language("Chinese"));

        Float minimumYear = (float) 2.0;

        EducationInfo education = new EducationInfo();
        education.setMajor("CS");
        education.setStartDate(LocalDate.now());
        education.setEndDate(LocalDate.now());
        education.setEducationLevel(EducationInfo.EducationLevel.UNDERGRADUATE);

        JobDescription jobDescription = new JobDescription();
        jobDescription.setTitle("Software Engineer");
        jobDescription.setResponsibilities(responsibilities);
        jobDescription.setRequiredSkills(requiredSkills);
        jobDescription.setRequiredLanguages(languages);

        WorkRequirement workRequirement = new WorkRequirement();
        List<String> keywords = new ArrayList<String>();
        keywords.add("keyword 1");
        keywords.add("keyword 2");
        keywords.add("keyword 3");
        workRequirement.setKeywords(keywords);
        workRequirement.setDuration(12);
        jobDescription.setWorkRequirement(workRequirement);

        EducationRequirement educationRequirement = new EducationRequirement();
        List<String> majors = new ArrayList<String>();
        majors.add("major 1");
        majors.add("major 2");
        educationRequirement.setAcceptedMajors(majors);
        educationRequirement.setMinimumEducation(EducationInfo.EducationLevel.UNDERGRADUATE);
        Grade grade = new Grade();
        grade.setGrade(4f);
        grade.setMaxGrade(5f);
        educationRequirement.setMinimumGrade(grade);
        jobDescription.setMinimumEducation(educationRequirement);

        Long id = jdManager.save(jobDescription);
        JobDescription jobDescription1 = jdManager.getJobDescriptionDetailById(id);
        System.out.println(jobDescription1.getTitle());

        for (Skill skill: jobDescription1.getRequiredSkills()) {
            System.out.println(skill.getName());
        }

        for (Language language: jobDescription1.getRequiredLanguages()) {
            System.out.println(language.getName());
        }
    }
}
