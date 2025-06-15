package org.example.Hibernate;

import org.example.Entity.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.example.Hibernate.HibernateUtil;

public class DbRequests {

    public static QueryResult executeQuery(int numberOfQuery, Map<String, String> inputs) {
        switch (numberOfQuery) {
            case (0):
                return getAllLabels(HibernateUtil.getSessionFactory());
            case (1):
                return getAllAlbums(HibernateUtil.getSessionFactory());
            case (2):
                return getAllGroups(HibernateUtil.getSessionFactory());
            case (3):
                return getAllCompositions(HibernateUtil.getSessionFactory());
            case (4):
                return getAllInstruments(HibernateUtil.getSessionFactory());
            case (5):
                return getAllPersonalities(HibernateUtil.getSessionFactory());
            case (6):
                return getAllParticipations(HibernateUtil.getSessionFactory());
            case (7):
                return getMinAlbumCostForLabelWithFrontman(HibernateUtil.getSessionFactory(), inputs);
            case (8):
                return getAvgAlbumCostForGenreWithInstrument(HibernateUtil.getSessionFactory(), inputs);
            case (9):
                return getCompositionsWithInstrumentsFromLabel(HibernateUtil.getSessionFactory(), inputs);
            case (10):
                return getAlbumsByGroupAndLabel(HibernateUtil.getSessionFactory(), inputs);
            case (11):
                return updateAlbumLabel(HibernateUtil.getSessionFactory(), inputs);
            case (12):
                return updateParticipation(HibernateUtil.getSessionFactory(), inputs);
            case (13):
                return deleteAlbumsWithCompositionsByPersonality(HibernateUtil.getSessionFactory(), inputs);
            case (14):
                return deleteAlbumsWithLabel(HibernateUtil.getSessionFactory(), inputs);
//            case (15):
//                return insertNewAlbumWithCompositions(HibernateUtil.getSessionFactory(),inputs);
//            case (16):
//                return insertNewPersonalityAndParticipation(HibernateUtil.getSessionFactory(),inputs);
            default:
                return new QueryResult(true, Collections.emptyList());
        }
    }

    private static QueryResult getAllLabels(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            List<Label> labels = session.createNativeQuery("SELECT * FROM shop.labels", Label.class).getResultList();
            List<Map<String, Object>> resultList = new ArrayList<>();

            for (Label label : labels) {
                Map<String, Object> row = new HashMap<>();
                // Assuming Label class has getId(), getName(), etc. methods
                // Add all properties you want to display in the table
                row.put("id", label.getId());
                row.put("shortname", label.getShortName());
                row.put("legal name", label.getLegalName());
                row.put("legal Address", label.getLegalAddress());
                row.put("year of Funding", label.getYearOfFunding());
                row.put("country", label.getCountry());
                // Add other properties as needed...

                resultList.add(row);
            }

            // Return as a SELECT query result
            return new QueryResult(true, resultList);
        }
    }

    public static QueryResult getAllAlbums(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            List<Album> albums = session.createQuery("FROM Album", Album.class).getResultList();
            List<Map<String, Object>> resultList = new ArrayList<>();

            for (Album album : albums) {
                Map<String, Object> row = new HashMap<>();
                row.put("ean", album.getEan());
                row.put("id_of_label", album.getLabel().getId()); // Get the ID from the Label object
                row.put("cost", album.getCost());
                row.put("date_of_relise", album.getDateOfRelease());
                row.put("type_of_package", album.getTypeOfPackage());
                row.put("type_of_publication", album.getTypeOfPublication());
                row.put("name", album.getName());
                resultList.add(row);
            }

            return new QueryResult(true, resultList);
        }
    }

    public static QueryResult getAllGroups(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            List<Group> groups = session.createNativeQuery("SELECT * FROM shop.groups", Group.class).getResultList();
            List<Map<String, Object>> resultList = new ArrayList<>();

            for (Group group : groups) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", group.getId());
                row.put("country_of_group", group.getCountry());
                row.put("year_of_founding", group.getYearOfFunding());
                row.put("name", group.getName());
                row.put("language", group.getLanguage());
                resultList.add(row);
            }

            return new QueryResult(true, resultList);
        }
    }

    public static QueryResult getAllCompositions(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            List<Composition> compositions = session.createNativeQuery("SELECT * FROM shop.compositions", Composition.class).getResultList();
            List<Map<String, Object>> resultList = new ArrayList<>();

            for (Composition composition : compositions) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", composition.getId());
                row.put("ean_of_album", composition.getAlbum());
                row.put("id_of_group", composition.getGroup());
                row.put("name", composition.getName());
                row.put("duration", composition.getDuration());
                row.put("genre", composition.getGenre());
                row.put("number_of_comp", composition.getNumberOfComp());
                resultList.add(row);
            }

            return new QueryResult(true, resultList);
        }
    }

    public static QueryResult getAllInstruments(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            List<Instrument> instruments = session.createNativeQuery("SELECT * FROM shop.instruments", Instrument.class).getResultList();
            List<Map<String, Object>> resultList = new ArrayList<>();

            for (Instrument instrument : instruments) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", instrument.getId());
                row.put("type_of_instrument", instrument.getType());
                row.put("name", instrument.getName());
                row.put("name_of_model", instrument.getModelName());
                resultList.add(row);
            }

            return new QueryResult(true, resultList);
        }
    }

    public static QueryResult getAllPersonalities(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            List<Personality> personalities = session.createNativeQuery("SELECT * FROM shop.personalities", Personality.class).getResultList();
            List<Map<String, Object>> resultList = new ArrayList<>();

            for (Personality personality : personalities) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", personality.getId());
                row.put("language", personality.getLanguage());
                row.put("country", personality.getCountry());
                row.put("firstname", personality.getFirstname());
                row.put("secondname", personality.getSecondName());
                row.put("thirdname", personality.getThirdName());
                row.put("date_of_birth", personality.getDateOfBirth());
                row.put("nickname", personality.getNickname());
                row.put("frontman", personality.getFrontman());
                resultList.add(row);
            }

            return new QueryResult(true, resultList);
        }
    }

    public static QueryResult getAllParticipations(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            List<Participation> participations = session.createNativeQuery("SELECT * FROM shop.participations", Participation.class).getResultList();
            List<Map<String, Object>> resultList = new ArrayList<>();

            for (Participation participation : participations) {
                Map<String, Object> row = new HashMap<>();
                row.put("id_of_group", participation.getGroup());
                row.put("id_of_personality", participation.getPersonality());
                row.put("id_of_instrument", participation.getInstrument());
                resultList.add(row);
            }

            return new QueryResult(true, resultList);
        }
    }

    // 1. Query to find minimum album cost for a specific label with a specific frontman
    public static QueryResult getMinAlbumCostForLabelWithFrontman(SessionFactory factory, Map<String, String> inputs) {
        try (Session session = factory.openSession()) {
            String query = "SELECT min(a.cost) FROM shop.albums a " +
                    "JOIN shop.labels l ON a.id_of_label = l.id " +
                    "JOIN shop.compositions c ON a.ean = c.ean_of_album " +
                    "JOIN shop.groups g ON c.id_of_group = g.id " +
                    "JOIN shop.participations p ON g.id = p.id_of_group " +
                    "JOIN shop.personalities pe ON p.id_of_personality = pe.id " +
                    "WHERE l.short_name = :labelShortName " +
                    "AND pe.nickname = :nickname " +
                    "AND pe.frontman = true";

            Integer minCost = (Integer) session.createNativeQuery(query)
                    .setParameter("labelShortName", inputs.get("labelShortname"))
                    .setParameter("nickname", inputs.get("personalityNickname"))
                    .uniqueResult();

            Map<String, Object> result = new HashMap<>();
            result.put("min_cost", minCost);

            return new QueryResult(true, Collections.singletonList(result));
        }
    }

    // 2. Query to find average album cost for specific genre with specific instrument
    public static QueryResult getAvgAlbumCostForGenreWithInstrument(SessionFactory factory, Map<String, String> inputs) {
        try (Session session = factory.openSession()) {
            String query = "SELECT avg(a.cost) FROM shop.albums a " +
                    "JOIN shop.labels l ON a.id_of_label = l.id " +
                    "JOIN shop.compositions c ON a.ean = c.ean_of_album " +
                    "JOIN shop.groups g ON c.id_of_group = g.id " +
                    "JOIN shop.participations p ON g.id = p.id_of_group " +
                    "JOIN shop.instruments i ON p.id_of_instrument = i.id " +
                    "WHERE c.genre = :genre AND i.name = :instrumentName";

            BigDecimal avgCost = (BigDecimal) session.createNativeQuery(query)
                    .setParameter("genre", inputs.get("genre"))
                    .setParameter("instrumentName", inputs.get("instrumentName"))
                    .uniqueResult();

            Map<String, Object> result = new HashMap<>();
            result.put("avg_cost", avgCost);

            return new QueryResult(true, Collections.singletonList(result));
        }
    }

    // 3. Query to find albums by specific group and label
    public static QueryResult getAlbumsByGroupAndLabel(SessionFactory factory, Map<String, String> inputs) {
        try (Session session = factory.openSession()) {
            String query = "SELECT DISTINCT a.ean, a.id_of_label, a.cost, a.name " +
                    "FROM shop.albums a " +
                    "JOIN shop.compositions c ON a.ean = c.ean_of_album " +
                    "JOIN shop.groups g ON g.id = c.id_of_group " +
                    "JOIN shop.labels l ON l.id = a.id_of_label " +
                    "WHERE g.name = :groupName AND l.short_name = :labelShortName " +
                    "AND EXISTS (SELECT 1 FROM shop.compositions c " +
                    "JOIN shop.groups g ON g.id = c.id_of_group " +
                    "WHERE c.ean_of_album = a.ean AND g.name = :groupName)";

            List<Object[]> results = session.createNativeQuery(query)
                    .setParameter("groupName", inputs.get("groupName"))
                    .setParameter("labelShortName", inputs.get("labelShortName"))
                    .getResultList();

            List<Map<String, Object>> resultList = new ArrayList<>();
            for (Object[] row : results) {
                Map<String, Object> rowMap = new HashMap<>();
                rowMap.put("ean", row[0]);
                rowMap.put("id_of_label", row[1]);
                rowMap.put("cost", row[2]);
                rowMap.put("name", row[3]);
                resultList.add(rowMap);
            }

            return new QueryResult(true, resultList);
        }
    }

    // 4. Query to find compositions with specific instruments from specific label
    public static QueryResult getCompositionsWithInstrumentsFromLabel(SessionFactory factory, Map<String, String> inputs) {
        try (Session session = factory.openSession()) {
            String query = "SELECT DISTINCT c.id, c.ean_of_album, c.id_of_group, c.name, c.genre " +
                    "FROM shop.compositions c " +
                    "JOIN shop.albums a ON c.ean_of_album = a.ean " +
                    "JOIN shop.labels l ON l.id = a.id_of_label " +
                    "JOIN shop.groups g ON g.id = c.id_of_group " +
                    "JOIN shop.participations p ON g.id = p.id_of_group " +
                    "JOIN shop.instruments i ON p.id_of_instrument = i.id " +
                    "WHERE i.name IN (:instrumentNames) AND l.short_name = :labelShortName " +
                    "ORDER BY c.name";

            List<Object[]> results = session.createNativeQuery(query)
                    .setParameterList("instrumentNames", Collections.singleton(inputs.get("instrumentsNames")))
                    .setParameter("labelShortName", inputs.get("labelShortName"))
                    .getResultList();

            List<Map<String, Object>> resultList = new ArrayList<>();
            for (Object[] row : results) {
                Map<String, Object> rowMap = new HashMap<>();
                rowMap.put("id", row[0]);
                rowMap.put("ean_of_album", row[1]);
                rowMap.put("id_of_group", row[2]);
                rowMap.put("name", row[3]);
                rowMap.put("genre", row[4]);
                resultList.add(rowMap);
            }

            return new QueryResult(true, resultList);
        }
    }

    // 6. Update label for specific album
    public static QueryResult updateAlbumLabel(SessionFactory factory, Map<String, String> inputs) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            //getAllAlbums(factory);

            List<Album> albumsBefore = session.createQuery("FROM Album", Album.class).getResultList();
            List<Map<String, Object>> beforeList = new ArrayList<>();

            for (Album album : albumsBefore) {
                Map<String, Object> row = new HashMap<>();
                row.put("ean", album.getEan());
                row.put("id_of_label", album.getLabel().getId()); // Get the ID from the Label object
                row.put("cost", album.getCost());
                row.put("date_of_relise", album.getDateOfRelease());
                row.put("type_of_package", album.getTypeOfPackage());
                row.put("type_of_publication", album.getTypeOfPublication());
                row.put("name", album.getName());
                beforeList.add(row);
            }

            String query = "UPDATE shop.albums " +
                    "SET id_of_label = (SELECT id FROM shop.labels WHERE short_name = :labelShortName) " +
                    "WHERE ean = CAST(:ean as BIGINT)";

            session.createNativeQuery(query)
                    .setParameter("labelShortName", inputs.get("labelShortName"))
                    .setParameter("ean", inputs.get("ean"))
                    .executeUpdate();

            List<Album> albumsAfter = session.createQuery("FROM Album", Album.class).getResultList();
            List<Map<String, Object>> afterList = new ArrayList<>();

            for (Album album : albumsAfter) {
                Map<String, Object> row = new HashMap<>();
                row.put("ean", album.getEan());
                row.put("id_of_label", album.getLabel().getId()); // Get the ID from the Label object
                row.put("cost", album.getCost());
                row.put("date_of_relise", album.getDateOfRelease());
                row.put("type_of_package", album.getTypeOfPackage());
                row.put("type_of_publication", album.getTypeOfPublication());
                row.put("name", album.getName());
                afterList.add(row);
            }

            session.getTransaction().commit();

//            Map<String, Object> result = new HashMap<>();
//            result.put("updated_rows", updated);

            return new QueryResult(false, beforeList, afterList);
        }
    }

    // 7. Update participation to set personality for specific group and instrument
    public static QueryResult updateParticipation(SessionFactory factory, Map<String, String> inputs) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            List<Participation> participationsBefore = session.createNativeQuery("SELECT * FROM shop.participations", Participation.class).getResultList();
            List<Map<String, Object>> resultListBefore = new ArrayList<>();

            for (Participation participation : participationsBefore) {
                Map<String, Object> row = new HashMap<>();
                row.put("id_of_group", participation.getGroup());
                row.put("id_of_personality", participation.getPersonality());
                row.put("id_of_instrument", participation.getInstrument());
                resultListBefore.add(row);
            }

            String query = "UPDATE shop.participations " +
                    "SET id_of_personality = (SELECT id FROM shop.personalities WHERE nickname = :nickname) " +
                    "WHERE id_of_group = CAST(:groupId AS INT) AND id_of_instrument = CAST(:instrumentId AS BIGINT)";

            session.createNativeQuery(query)
                    .setParameter("nickname", inputs.get("nickname"))
                    .setParameter("groupId", inputs.get("groupId"))
                    .setParameter("instrumentId", inputs.get("instrumentId"))
                    .executeUpdate();

            List<Participation> participationsAfter = session.createNativeQuery("SELECT * FROM shop.participations", Participation.class).getResultList();
            List<Map<String, Object>> resultListAfter = new ArrayList<>();

            for (Participation participation : participationsAfter) {
                Map<String, Object> row = new HashMap<>();
                row.put("id_of_group", participation.getGroup());
                row.put("id_of_personality", participation.getPersonality());
                row.put("id_of_instrument", participation.getInstrument());
                resultListAfter.add(row);
            }

            session.getTransaction().commit();

            return new QueryResult(false, resultListBefore, resultListAfter);
        }
    }

    // 8. Delete albums with specific label
    public static QueryResult deleteAlbumsWithLabel(SessionFactory factory, Map<String, String> inputs) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            List<Album> albumsBefore = session.createQuery("FROM Album", Album.class).getResultList();
            List<Map<String, Object>> beforeList = new ArrayList<>();

            for (Album album : albumsBefore) {
                Map<String, Object> row = new HashMap<>();
                row.put("ean", album.getEan());
                row.put("id_of_label", album.getLabel().getId()); // Get the ID from the Label object
                row.put("cost", album.getCost());
                row.put("date_of_relise", album.getDateOfRelease());
                row.put("type_of_package", album.getTypeOfPackage());
                row.put("type_of_publication", album.getTypeOfPublication());
                row.put("name", album.getName());
                beforeList.add(row);
            }

            String query = "DELETE FROM shop.albums " +
                    "WHERE id_of_label = (SELECT id FROM shop.labels WHERE short_name = :labelShortName)";

            session.createNativeQuery(query)
                    .setParameter("labelShortName", inputs.get("labelShortName"))
                    .executeUpdate();

            List<Album> albumsAfter = session.createQuery("FROM Album", Album.class).getResultList();
            List<Map<String, Object>> afterList = new ArrayList<>();

            for (Album album : albumsAfter) {
                Map<String, Object> row = new HashMap<>();
                row.put("ean", album.getEan());
                row.put("id_of_label", album.getLabel().getId()); // Get the ID from the Label object
                row.put("cost", album.getCost());
                row.put("date_of_relise", album.getDateOfRelease());
                row.put("type_of_package", album.getTypeOfPackage());
                row.put("type_of_publication", album.getTypeOfPublication());
                row.put("name", album.getName());
                afterList.add(row);
            }

            session.getTransaction().commit();

            return new QueryResult(false, beforeList, afterList);
        }
    }

    // 9. Delete albums with compositions by specific personality
    public static QueryResult deleteAlbumsWithCompositionsByPersonality(SessionFactory factory, Map<String, String> inputs) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            List<Album> albumsBefore = session.createQuery("FROM Album", Album.class).getResultList();
            List<Map<String, Object>> beforeList = new ArrayList<>();

            for (Album album : albumsBefore) {
                Map<String, Object> row = new HashMap<>();
                row.put("ean", album.getEan());
                row.put("id_of_label", album.getLabel().getId()); // Get the ID from the Label object
                row.put("cost", album.getCost());
                row.put("date_of_relise", album.getDateOfRelease());
                row.put("type_of_package", album.getTypeOfPackage());
                row.put("type_of_publication", album.getTypeOfPublication());
                row.put("name", album.getName());
                beforeList.add(row);
            }

            String query = "DELETE FROM shop.albums " +
                    "WHERE ean IN (SELECT DISTINCT c.ean_of_album " +
                    "FROM shop.compositions c " +
                    "JOIN shop.groups g ON c.id_of_group = g.id " +
                    "JOIN shop.participations p ON g.id = p.id_of_group " +
                    "JOIN shop.personalities pe ON p.id_of_personality = pe.id " +
                    "WHERE pe.nickname = :nickname)";

            session.createNativeQuery(query)
                    .setParameter("nickname", inputs.get("nickname"))
                    .executeUpdate();

            List<Album> albumsAfter = session.createQuery("FROM Album", Album.class).getResultList();
            List<Map<String, Object>> afterList = new ArrayList<>();

            for (Album album : albumsAfter) {
                Map<String, Object> row = new HashMap<>();
                row.put("ean", album.getEan());
                row.put("id_of_label", album.getLabel().getId()); // Get the ID from the Label object
                row.put("cost", album.getCost());
                row.put("date_of_relise", album.getDateOfRelease());
                row.put("type_of_package", album.getTypeOfPackage());
                row.put("type_of_publication", album.getTypeOfPublication());
                row.put("name", album.getName());
                afterList.add(row);
            }

            session.getTransaction().commit();

            return new QueryResult(false, beforeList, afterList);
        }
    }

    // 10. Insert new album with compositions
    public static QueryResult insertNewAlbumWithCompositions(SessionFactory factory,
                                                             long ean, String labelShortName, int cost, String packageType,
                                                             String publicationType, String albumName, List<CompositionDTO> compositions) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            // Insert album
            String albumQuery = "INSERT INTO shop.albums (ean, id_of_label, cost, date_of_relise, " +
                    "type_of_package, type_of_publication, name) " +
                    "VALUES (:ean, " +
                    "(SELECT id FROM shop.labels WHERE short_name = :labelShortName), " +
                    ":cost, CURRENT_DATE, :packageType, :publicationType, :albumName)";

            int albumsInserted = session.createNativeQuery(albumQuery)
                    .setParameter("ean", ean)
                    .setParameter("labelShortName", labelShortName)
                    .setParameter("cost", cost)
                    .setParameter("packageType", packageType)
                    .setParameter("publicationType", publicationType)
                    .setParameter("albumName", albumName)
                    .executeUpdate();

            // Insert compositions
            int compositionsInserted = 0;
            for (CompositionDTO comp : compositions) {
                String compQuery = "INSERT INTO shop.compositions (ean_of_album, id_of_group, " +
                        "name, duration, genre, number_of_comp) " +
                        "VALUES (:ean, (SELECT id FROM shop.groups WHERE name = :groupName), " +
                        ":name, :duration, :genre, :number)";

                compositionsInserted += session.createNativeQuery(compQuery)
                        .setParameter("ean", ean)
                        .setParameter("groupName", comp.getGroupName())
                        .setParameter("name", comp.getName())
                        .setParameter("duration", comp.getDuration())
                        .setParameter("genre", comp.getGenre())
                        .setParameter("number", comp.getNumber())
                        .executeUpdate();
            }

            session.getTransaction().commit();

            Map<String, Object> result = new HashMap<>();
            result.put("albums_inserted", albumsInserted);
            result.put("compositions_inserted", compositionsInserted);

            return new QueryResult(true, Collections.singletonList(result));
        }
    }

    // 12. Insert new personality and participation
    public static QueryResult insertNewPersonalityAndParticipation(SessionFactory factory,
                                                                   PersonalityDTO personality, int groupId, int instrumentId) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            // Insert personality
            String personalityQuery = "INSERT INTO shop.personalities (language, country, firstname, " +
                    "secondname, thirdname, date_of_birth, nickname, frontman) " +
                    "VALUES (:language, :country, :firstname, " +
                    ":secondname, :thirdname, :dateOfBirth, :nickname, :frontman)";

            int personalityInserted = session.createNativeQuery(personalityQuery)
                    .setParameter("language", personality.getLanguage())
                    .setParameter("country", personality.getCountry())
                    .setParameter("firstname", personality.getFirstname())
                    .setParameter("secondname", personality.getSecondname())
                    .setParameter("thirdname", personality.getThirdname())
                    .setParameter("dateOfBirth", personality.getDateOfBirth())
                    .setParameter("nickname", personality.getNickname())
                    .setParameter("frontman", personality.isFrontman())
                    .executeUpdate();

            // Insert participation
            String participationQuery = "INSERT INTO shop.participations (id_of_group, id_of_personality, id_of_instrument) " +
                    "VALUES (:groupId, " +
                    "(SELECT id FROM shop.personalities WHERE nickname = :nickname), " +
                    ":instrumentId)";

            int participationInserted = session.createNativeQuery(participationQuery)
                    .setParameter("groupId", groupId)
                    .setParameter("nickname", personality.getNickname())
                    .setParameter("instrumentId", instrumentId)
                    .executeUpdate();

            session.getTransaction().commit();

            Map<String, Object> result = new HashMap<>();
            result.put("personality_inserted", personalityInserted);
            result.put("participation_inserted", participationInserted);

            return new QueryResult(true, Collections.singletonList(result));
        }
    }

            // DTO classes for complex parameters
            public static class CompositionDTO {
                private String groupName;
                private String name;
                private String duration;
                private String genre;
                private int number;

                public void setGroupName(String groupName) {
                    this.groupName = groupName;
                }

                public String getGroupName() {
                    return groupName;
                }

                public String getName() {
                    return name;
                }

                public String getDuration() {
                    return duration;
                }

                public String getGenre() {
                    return genre;
                }

                public int getNumber() {
                    return number;
                }

                // constructor, getters and setters
            }

            public static class PersonalityDTO {
                private String language;
                private String country;
                private String firstname;
                private String secondname;
                private String thirdname;
                private Date dateOfBirth;
                private String nickname;
                private boolean frontman;

                public String getLanguage() {
                    return language;
                }

                public String getCountry() {
                    return country;
                }

                public String getFirstname() {
                    return firstname;
                }

                public String getSecondname() {
                    return secondname;
                }

                public String getThirdname() {
                    return thirdname;
                }

                public Date getDateOfBirth() {
                    return dateOfBirth;
                }

                public String getNickname() {
                    return nickname;
                }

                public boolean isFrontman() {
                    return frontman;
                }

                // constructor, getters and setters
    }

}
