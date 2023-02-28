package db;

import core.documentManager.DocumentManagerData;
import dbclasses.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DocumentManagerService {
    private final SessionFactory sessionFactory;

    public DocumentManagerService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void getData(DocumentManagerData data) {
        data.clear();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            data.documents.addAll(session.createQuery("SELECT row FROM Document row", Document.class).list());
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void getDataByUserID(DocumentManagerData data, String id) {
        data.clear();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            data.documents.addAll(session.createQuery("SELECT row FROM Document row WHERE row.userID= : userID", Document.class).
                setParameter("userID", id).
                list());
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void getDataByID(DocumentManagerData data, String id) {
        data.clear();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            data.documents.addAll(session.createQuery("SELECT row FROM Document row WHERE row.id= : id", Document.class).
                    setParameter("id", id).
                    list());
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void createDocument(Document document) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(document);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
