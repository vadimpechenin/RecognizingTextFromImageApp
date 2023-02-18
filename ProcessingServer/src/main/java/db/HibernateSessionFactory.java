package db;


import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Класс для создания SessionFactory.
 * Необходим наравне с файлом resources/hibernate.cfg.xml
 * (заменой может быть application.properties), где прописываются свойства БД.
 * Эти же свойства (по сути свойства базы данных и диалекта) можно прописать непосредственно и в этом классе c
 * org.hibernate.cfg.Configuration и java.util.Properties.
 */
public class HibernateSessionFactory {

    private static SessionFactory sessionFactory; //настройки и работа с сессиями (фабрика сессий)

    static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder() // получение реестра сервисов
                .configure()//настройка из hibernate.cfg.xml
                .build();
    try {
        //MetadataSources - для работы с метаданными маппинга объектов
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }catch (Exception e){
        StandardServiceRegistryBuilder.destroy( registry );
    }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

}
