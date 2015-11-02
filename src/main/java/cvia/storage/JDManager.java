package cvia.storage;

import cvia.model.JDParseResult;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

/**
 * Created by Michael Limantara on 11/3/2015.
 */
public class JDManager {
    private SessionFactory factory;

    public JDManager() { setUpConnection(); }

    public Long createJD(JDParseResult jdParseResult) {
        Session session = factory.openSession();
        Transaction transaction = null;
        Long jobDescriptionId = null;

        try {
            transaction = session.beginTransaction();
            jobDescriptionId = (Long) session.save(jdParseResult);
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

    public JDParseResult getJobDescriptionDetailById(Long id) {
        Session session = factory.openSession();
        Transaction transaction = null;
        JDParseResult jdParseResult = null;

        try {
            transaction = session.beginTransaction();
            jdParseResult = session.get(JDParseResult.class, id);
            if (jdParseResult != null) {
                Hibernate.initialize(jdParseResult.getRequiredLanguages());
                Hibernate.initialize(jdParseResult.getRequiredSkills());
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

        return jdParseResult;
    }

    public void updateCV(Long id, JDParseResult newJD) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            JDParseResult jdParseResult = session.get(JDParseResult.class, id);
            jdParseResult.setMinimumEducation(newJD.getMinimumEducation());
            jdParseResult.setMinimumYearsOfWorkExperience(newJD.getMinimumYearsOfWorkExperience());
            jdParseResult.setRequiredLanguages(newJD.getRequiredLanguages());
            jdParseResult.setRequiredSkills(newJD.getRequiredSkills());
            jdParseResult.setResponsibilities(newJD.getResponsibilities());
            session.update(jdParseResult);
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
            JDParseResult jdParseResult = session.get(JDParseResult.class, id);
            session.delete(jdParseResult);
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
        JDManager jdManager new JDManager();
    }
}
