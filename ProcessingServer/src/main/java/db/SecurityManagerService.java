package db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import core.securityManager.SecurityManagerData;
import dbclasses.*;

public class SecurityManagerService {
    private final SessionFactory sessionFactory;

    public SecurityManagerService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void getData(SecurityManagerData data) {
        data.clear();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            data.users.addAll(session.createQuery("SELECT row FROM User row", User.class).list());
            data.userRoles.addAll(session.createQuery("SELECT row FROM UserRole row", UserRole.class).list());
            data.roles.addAll(session.createQuery("SELECT row FROM Role row", Role.class).list());
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void createUser(User user, UserRole userRole) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(user);
            session.persist(userRole);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
