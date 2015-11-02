package cvia.storage;

import cvia.model.JobDescription;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

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
    }
}
