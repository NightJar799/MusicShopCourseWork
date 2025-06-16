package org.example.Hibernate;

import org.example.Entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySetting("hibernate.connection.driver_class", "org.postgresql.Driver")
                    .applySetting("hibernate.connection.url", "jdbc:postgresql://localhost:5432/musicshop")
                    .applySetting("hibernate.connection.username", "postgres")
                    .applySetting("hibernate.connection.password", "postgres")
                    .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                    .applySetting("hibernate.hbm2ddl.auto", "update")
                    .applySetting("hibernate.jdbc.time_zone", "UTC")
                    .applySetting("hibernate.temp.use_jdbc_metadata_defaults", "false")
                    .applySetting("hibernate.default_schema", "shop")
                    .applySetting("hibernate.show_sql", "true")
                    .applySetting("hibernate.format_sql", "true")
                    .applySetting("hibernate.use_sql_comments", "true")
                    .applySetting("logging.level.org.hibernate.SQL", "DEBUG")
                    .build();

            MetadataSources sources = new MetadataSources(registry)
                    .addPackage("org.example.Entity")
                    .addAnnotatedClass(Label.class)
                    .addAnnotatedClass(Composition.class)
                    .addAnnotatedClass(Group.class)
                    .addAnnotatedClass(Instrument.class)
                    .addAnnotatedClass(Participation.class)
                    .addAnnotatedClass(Personality.class)
                    .addAnnotatedClass(Album.class);

            Metadata metadata = sources.getMetadataBuilder().build();

            return metadata.getSessionFactoryBuilder().build();

        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
