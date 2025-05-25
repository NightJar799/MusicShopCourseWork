package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import static org.hibernate.cfg.AvailableSettings.*;

import org.example.entity.*;

public class Main {
    public static void main(String[] args) {
        var factory = new Configuration()
                .addAnnotatedClass(Label.class)
                .addAnnotatedClass(Album.class)
                .addAnnotatedClass(Composition.class)
                .addAnnotatedClass(Group.class)
                .addAnnotatedClass(Participation.class)
                .addAnnotatedClass(Instrument.class)
                .addAnnotatedClass(Personality.class)
                .buildSessionFactory();
    }
}