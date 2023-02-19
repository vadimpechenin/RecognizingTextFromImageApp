package db;

import dbclasses.User;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Сервис для работы с сущностью User, удалить как завершится SecurityManagerService
 */
public class UserService implements EntityService{
    private final SessionFactory sessionFactory;

    public List<User> users;

    public UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.users = new ArrayList<>();
    }

    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            int g = 0;
            //session.get(User.class,);
            //объект-конструктор запросов для Criteria API
            /*CriteriaBuilder cb = session.getCriteriaBuilder(); //не использовать session.createCriteria

            CriteriaQuery cq = cb.createQuery(User.class);

            Root<User> root = cq.from(User.class); // аналог From в sql
            cq.select(root);
            Query query = session.createQuery(cq);
            users =query.getResultList();*/
            users = session.createQuery("SELECT row FROM User row", User.class).list();
            session.close();
            return users;
        } catch (Exception e) {
            System.out.println("Ошибка с маппингом");
        }
        return null;
    }
}
