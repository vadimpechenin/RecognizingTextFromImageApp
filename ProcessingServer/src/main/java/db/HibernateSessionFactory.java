package db;

import dbclasses.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Класс для создания SessionFactory.
 * Необходим наравне с файлом resources/hibernate.cfg.xml
 * (заменой может быть application.properties), где прописываются свойства БД.
 * Эти же свойства (по сути свойства базы данных и диалекта) можно прописать непосредственно и в этом классе c
 * org.hibernate.cfg.Configuration и java.util.Properties.
 */
public class HibernateSessionFactory {

    private static volatile SessionFactory sessionFactory; //настройки и работа с сессиями (фабрика сессий)

    private static final Object sessionFactoryLock = new Object();

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null)
        {
            synchronized(sessionFactoryLock)
            {
                if (sessionFactory == null)
                {
                    try
                    {
                        Configuration configuration = new Configuration().configure();

                        configuration.addAnnotatedClass(User.class);
                        configuration.addAnnotatedClass(Role.class);
                        configuration.addAnnotatedClass(UserRole.class);
                        configuration.addAnnotatedClass(Document.class);

                        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                        sessionFactory = configuration.buildSessionFactory(builder.build());
                    }
                    catch (Exception e)
                    {
                        System.out.println("Исключение!" + e);
                    }
                }
            }
        }
        return sessionFactory;
    }

}
