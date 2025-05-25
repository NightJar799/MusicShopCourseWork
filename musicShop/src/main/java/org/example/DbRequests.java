package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import static org.hibernate.cfg.AvailableSettings.*;

import org.example.entity.*;

import java.time.LocalDate;
import java.util.List;

public class DbRequests {

    public void getLabels(SessionFactory factory){
        Session session = factory.openSession();
        session.createNativeQuery("SELECT * FROM shop.labels", Label.class)
                .getResultList().forEach(System.out::println);
        session.close();
    }

    public void getAllLabels(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            session.createNativeQuery("SELECT * FROM shop.labels", Label.class)
                    .getResultList()
                    .forEach(System.out::println);
        }
    }

    public void getAllAlbums(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            session.createNativeQuery("SELECT * FROM shop.albums", Album.class)
                    .getResultList()
                    .forEach(System.out::println);
        }
    }


    public void getAllGroups(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            session.createNativeQuery("SELECT * FROM shop.groups", Group.class)
                    .getResultList()
                    .forEach(System.out::println);
        }
    }

    public void getAllCompositions(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            session.createNativeQuery("SELECT * FROM shop.compositions", Composition.class)
                    .getResultList()
                    .forEach(System.out::println);
        }
    }

    public void getAllInstruments(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            session.createNativeQuery("SELECT * FROM shop.instruments", Instrument.class)
                    .getResultList()
                    .forEach(System.out::println);
        }
    }

    public void getAllPersonalities(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            session.createNativeQuery("SELECT * FROM shop.personalities", Personality.class)
                    .getResultList()
                    .forEach(System.out::println);
        }
    }

    public void getAllParticipations(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            session.createNativeQuery("SELECT * FROM shop.participations", Participation.class)
                    .getResultList()
                    .forEach(System.out::println);
        }
    }

    public void getRockAlbumsWithGuitar(SessionFactory factory,String genre,String instrument) {
        try (Session session = factory.openSession()) {
            String query = " SELECT DISTINCT a.* FROM shop.albums a JOIN shop.labels l ON a.id_of_label = l.id JOIN shop.compositions c ON a.ean = c.ean_of_album " +
                    "JOIN shop.groups g ON c.id_of_group = g.id JOIN shop.participations p ON g.id = p.id_of_group " +
                    "JOIN shop.instruments i ON p.id_of_instrument = i.id WHERE c.genre = :genre AND i.name = + :instrument" +
                    " ORDER BY a.name ";

            session.createNativeQuery(query, Album.class)
                    .getResultList()
                    .forEach(System.out::println);
        }
    }

    public void getSonicAlbumsWithIronMike(SessionFactory factory, String labelShortName, String personNickname) {
        try (Session session = factory.openSession()) {
            String query = "SELECT DISTINCT a.* FROM shop.albums a JOIN shop.labels l ON a.id_of_label = l.id JOIN shop.compositions c ON a.ean = c.ean_of_album " +
                    "JOIN shop.groups g ON c.id_of_group = g.id JOIN shop.participations p ON g.id = p.id_of_group " +
                    "JOIN shop.personalities pe ON p.id_of_personality = pe.id " +
                    "WHERE l.short_name = :labelShortName AND pe.nickname = :personNickname AND pe.frontman = true GROUP BY a.ean ";

            session.createNativeQuery(query, Album.class)
                    .getResultList()
                    .forEach(System.out::println);
        }
    }

    public void getSonicCompositionsWithGuitarLikeInstruments(SessionFactory factory, String labelShortName) {
        try (Session session = factory.openSession()) {
            String query = " SELECT DISTINCT c.* FROM shop.compositions c JOIN shop.albums a ON c.ean_of_album = a.ean " +
                    "JOIN shop.labels l ON l.id = a.id_of_label JOIN shop.groups g ON g.id = c.id_of_group " +
                    "JOIN shop.participations p ON g.id = p.id_of_group JOIN shop.instruments i ON p.id_of_instrument = i.id " +
                    "WHERE i.name IN ('Violin', 'Bass', 'Guitar') AND l.short_name = :labelShortName ORDER BY c.name";

            session.createNativeQuery(query, Composition.class)
                    .getResultList()
                    .forEach(System.out::println);
        }
    }

    public void getKoliaAlbumsFromVinylLabel(SessionFactory factory, String groupName, String labelShortName) {
        try (Session session = factory.openSession()) {
            String query = "SELECT DISTINCT a.* FROM shop.albums a JOIN shop.compositions c ON a.ean = c.ean_of_album " +
                    "JOIN shop.groups g ON g.id = c.id_of_group JOIN shop.labels l ON l.id = a.id_of_label " +
                    "WHERE g.name = :groupName AND l.short_name = :labelShortName " +
                    "AND EXISTS ( SELECT 1 FROM shop.compositions c JOIN shop.groups g ON g.id = c.id_of_group " +
                    "WHERE c.ean_of_album = a.ean AND g.name = :groupName) ORDER BY a.name";

            session.createNativeQuery(query, Album.class)
                    .getResultList()
                    .forEach(System.out::println);
        }
    }

    public void countAlbumsWithSameSongsDifferentPackaging(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            String query = "WITH album_composition AS (SELECT a.ean, a.type_of_package, STRING_AGG(c.name, ',' ORDER BY c.name) AS songs_list " +
                    "FROM shop.albums a JOIN shop.compositions c ON a.ean = c.ean_of_album GROUP BY a.ean, a.type_of_package) " +
                    "SELECT COUNT(a1.ean) FROM album_composition a1 JOIN album_composition a2 ON a1.songs_list = a2.songs_list " +
                    "AND a1.ean != a2.ean AND a1.type_of_package != a2.type_of_package ";

            Object result = session.createNativeQuery(query).getSingleResult();
            System.out.println("Количество альбомов: " + result);
        }
    }

    public void updateAlbumLabel(SessionFactory factory, Long ean, String labelShortName) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            getAllAlbums(factory);

            String query = "UPDATE shop.albums SET id_of_label = (SELECT id FROM shop.labels " +
                    "WHERE short_name = :labelShortName) WHERE ean = :ean";

            getAllAlbums(factory);

            int updated = session.createNativeQuery(query)
                    .setParameter("ean", ean)
                    .setParameter("labelShortName", labelShortName)
                    .executeUpdate();

            tx.commit();
            System.out.println("Обновлено альбомов: " + updated);
        }
    }

    public void updateGroupParticipant(SessionFactory factory, int groupId, int instrumentId, String nickname) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            getAllParticipations(factory);

            String query = "UPDATE shop.participations SET id_of_personality = (SELECT id FROM shop.personalities WHERE nickname = :nickname) " +
                    "WHERE id_of_group = :groupId AND id_of_instrument = :instrumentId";

            getAllParticipations(factory);

            int updated = session.createNativeQuery(query)
                    .setParameter("groupId", groupId)
                    .setParameter("instrumentId", instrumentId)
                    .setParameter("nickname", nickname)
                    .executeUpdate();

            tx.commit();
            System.out.println("Обновлено участников: " + updated);
        }
    }

    public void deleteAlbumsByLabel(SessionFactory factory, String labelShortName) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            getAllAlbums(factory);

            String query = "DELETE FROM shop.albums WHERE id_of_label = " +
                    "(SELECT id FROM shop.labels WHERE short_name = :labelShortName)";

            getAllAlbums(factory);

            int deleted = session.createNativeQuery(query)
                    .setParameter("labelShortName", labelShortName)
                    .executeUpdate();

            tx.commit();
            System.out.println("Удалено альбомов: " + deleted);
        }
    }

    public void deleteAlbumsByParticipant(SessionFactory factory, String nickname) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            getAllAlbums(factory);

            String query = "DELETE FROM shop.albums WHERE ean IN (SELECT DISTINCT c.ean_of_album FROM shop.compositions c " +
                    "JOIN shop.groups g ON c.id_of_group = g.id JOIN shop.participations p ON g.id = p.id_of_group " +
                    "JOIN shop.personalities pe ON p.id_of_personality = pe.id WHERE pe.nickname = :nickname)";

            getAllAlbums(factory);

            int deleted = session.createNativeQuery(query)
                    .setParameter("nickname", nickname)
                    .executeUpdate();

            tx.commit();
            System.out.println("Удалено альбомов: " + deleted);
        }
    }

    public void insertNewAlbum(SessionFactory factory, Long ean, String labelShortName,
                               Integer cost, String typeOfPackage, String typeOfPublication, String name) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            getAllAlbums(factory);

            String query = "INSERT INTO shop.albums (ean, id_of_label, cost, date_of_relise, type_of_package," +
                    " type_of_publication, name) VALUES (:ean, (SELECT id FROM shop.labels WHERE short_name = " +
                    ":labelShortName),:cost, CURRENT_DATE,:typeOfPackage,:typeOfPublication," +
                    ":name)";

            getAllAlbums(factory);

            int inserted = session.createNativeQuery(query)
                    .setParameter("ean", ean)
                    .setParameter("labelShortName", labelShortName)
                    .setParameter("cost", cost)
                    .setParameter("typeOfPackage", typeOfPackage)
                    .setParameter("typeOfPublication", typeOfPublication)
                    .setParameter("name", name)
                    .executeUpdate();

            tx.commit();
            System.out.println("Добавлено альбомов: " + inserted);
        }
    }

    public void insertCompositions(SessionFactory factory, Long ean, String groupName,
                                   List<CompositionDTO> compositions) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            Integer groupId = (Integer) session.createNativeQuery("SELECT id FROM shop.groups WHERE name = :groupName")
                    .setParameter("groupName", groupName)
                    .getSingleResult();

            getAllCompositions(factory);

            for (CompositionDTO comp : compositions) {
                String query = "INSERT INTO shop.compositions (ean_of_album, id_of_group, name, duration, genre, number_of_comp) " +
                        "VALUES (:ean, :groupId, :name, :duration, :genre, :number)";

                session.createNativeQuery(query)
                        .setParameter("ean", ean)
                        .setParameter("groupId", groupId)
                        .setParameter("name", comp.name())
                        .setParameter("duration", comp.duration())
                        .setParameter("genre", comp.genre())
                        .setParameter("number", comp.number())
                        .executeUpdate();
            }

            getAllCompositions(factory);

            tx.commit();
            System.out.println("Добавлено композиций: " + compositions.size());
        }
    }

    public void insertPersonality(SessionFactory factory, PersonalityDTO personality) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            getAllPersonalities(factory);
            String query = "INSERT INTO shop.personalities (language, country, firstname, secondname, thirdname, date_of_birth, nickname, frontman) " +
                    "VALUES (:lang, :country, :firstName, :secondName, :thirdName, :birthDate, :nickname, :frontman)";

            getAllPersonalities(factory);

            int inserted = session.createNativeQuery(query)
                    .setParameter("lang", personality.language())
                    .setParameter("country", personality.country())
                    .setParameter("firstName", personality.firstname())
                    .setParameter("secondName", personality.secondname())
                    .setParameter("thirdName", personality.thirdname())
                    .setParameter("birthDate", personality.dateOfBirth())
                    .setParameter("nickname", personality.nickname())
                    .setParameter("frontman", personality.frontman())
                    .executeUpdate();

            tx.commit();
            System.out.println("Добавлено личностей: " + inserted);
        }
    }

    public void insertParticipation(SessionFactory factory, int groupId, String nickname, int instrumentId) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();

            getAllParticipations(factory);


            String query = "INSERT INTO shop.participations (id_of_group, id_of_personality, id_of_instrument) VALUES " +
                    "(:groupId, (SELECT id FROM shop.personalities WHERE nickname = :nickname),:instrumentId)";

            getAllParticipations(factory);

            int inserted = session.createNativeQuery(query)
                    .setParameter("groupId", groupId)
                    .setParameter("nickname", nickname)
                    .setParameter("instrumentId", instrumentId)
                    .executeUpdate();

            tx.commit();
            System.out.println("Добавлено участий: " + inserted);
        }
    }
}

record CompositionDTO(String name, String duration, String genre, int number) {}
record PersonalityDTO(String language, String country, String firstname, String secondname,
                      String thirdname, LocalDate dateOfBirth, String nickname, boolean frontman) {}