package cvia.storage;

import cvia.model.EducationInfo;
import cvia.model.JobDescription;
import cvia.model.Language;
import cvia.model.Skill;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Michael Limantara on 11/3/2015.
 */
public class JDManager {
    private SessionFactory factory;

    public JDManager() { setUpConnection(); }

    public Long createJD(JobDescription jobDescription) {
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
            System.out.println("Error addJD:");
            e.printStackTrace();
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

    public void updateCV(Long id, JobDescription newJobDescription) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            JobDescription jobDescription = session.get(JobDescription.class, id);
            jobDescription.setMinimumEducation(newJobDescription.getMinimumEducation());
            jobDescription.setMinimumYearsOfWorkExperience(newJobDescription.getMinimumYearsOfWorkExperience());
            jobDescription.setRequiredLanguages(newJobDescription.getRequiredLanguages());
            jobDescription.setRequiredSkills(newJobDescription.getRequiredSkills());
            jobDescription.setResponsibilities(newJobDescription.getResponsibilities());
            session.update(jobDescription);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error updateJD:");
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
            JobDescription jobDescription = session.get(JobDescription.class, id);
            session.delete(jobDescription);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error deleteJD:");
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
        education.setStartDate(new Date());
        education.setEndDate(new Date());
        education.setEducationLevel("university");

        JobDescription jobDescription = new JobDescription();
        jobDescription.setTitle("Software Engineer");
        jobDescription.setResponsibilities(responsibilities);
        jobDescription.setRequiredSkills(requiredSkills);
        jobDescription.setRequiredLanguages(languages);
        jobDescription.setMinimumYearsOfWorkExperience(minimumYear);
        jobDescription.setMinimumEducation(education);

        Long id = jdManager.createJD(jobDescription);
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
