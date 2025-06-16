package org.example.Hibernate;

import org.example.Entity.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.util.*;

public class DbRequests {

    public static QueryResult executeQuery(int numberOfQuery, Map<String, String> inputs) {
        String validationError = validateInputs(numberOfQuery, inputs);
        if (validationError != null) {
            return new QueryResult(validationError);
        }

        try {
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
                    return getAlbumsByGroupAndLabel(HibernateUtil.getSessionFactory(), inputs);
                case (10):
                    return updateAlbumLabel(HibernateUtil.getSessionFactory(), inputs);
                case (11):
                    return updateParticipation(HibernateUtil.getSessionFactory(), inputs);
                case (12):
                    return deleteAlbumsWithCompositionsByPersonality(HibernateUtil.getSessionFactory(), inputs);
                case (13):
                    return deleteAlbumsWithLabel(HibernateUtil.getSessionFactory(), inputs);
                default:
                    return new QueryResult("Неправильный номер запроса");
            }
        } catch (Exception e) {
            return new QueryResult(e.getMessage());
        }
    }

    private static String validateInputs(int queryNumber, Map<String, String> inputs) {
        for (Map.Entry<String, String> entry : inputs.entrySet()) {
            if (entry.getValue() == null || entry.getValue().trim().isEmpty()) {
                return "Необходимы все поля";
            }
        }

        switch (queryNumber) {
            case 7:
                if (!inputs.get(Constants.LABELSHORTNAME).matches("[a-zA-Z0-9]+")) {
                    return "Короткое имя лейбла должно содержать только буквы и цифры";
                } else if (!inputs.get(Constants.LABELSHORTNAME).substring(0,1).matches("[A-Z]+")) {
                    return "Короткое имя лейбла начинается с заглавной буквы";
                } else if (!inputs.get(Constants.NICKNAME).matches("[a-zA-Z0-9]+")) {
                    return "Прозвище должно содержать только буквы и цифры.";
                } else if (!inputs.get(Constants.NICKNAME).substring(0,1).matches("[A-Z]+")) {
                    return "Прозвище должно начинаться с заглавной буквы";
                } else if (inputs.get(Constants.LABELSHORTNAME).length() > 150) {
                    return "Длина названия лейбла должна быть не более 150 букв";
                } else if (inputs.get(Constants.NICKNAME).length() > 100) {
                    return "Длина Прозвища должна быть не более 100 букв";
                }
                break;
            case 8:
                if (!inputs.get(Constants.INSTRUMENTNAME).matches("[a-zA-Z0-9]+")) {
                    return "Короткое имя инструмента должно содержать только буквы и цифры";
                } else if (!inputs.get(Constants.INSTRUMENTNAME).substring(0,1).matches("[A-Z]+")) {
                    return "Название инструмента начинается с заглавной буквы";
                } else if (!inputs.get(Constants.GENRE).matches("[a-zA-Z0-9]+")) {
                    return "Название жанра должно содержать только буквы и цифры";
                } else if (inputs.get(Constants.INSTRUMENTNAME).length() > 100) {
                    return "Длина названия инструмента должна быть не более 100 букв";
                } else if (inputs.get(Constants.GENRE).length() > 100) {
                    return "Длина названия жанра должна быть не более 100 букв";
                }
                break;
            case 9:
                if (!inputs.get(Constants.LABELSHORTNAME).matches("[a-zA-Z0-9]+")) {
                    return "Короткое имя лейбла должно содержать только буквы и цифры";
                } else if (!inputs.get(Constants.LABELSHORTNAME).substring(0,1).matches("[A-Z]+")) {
                    return "Короткое имя лейбла начинается с заглавной буквы";
                } else if (!inputs.get(Constants.GROUPNAME).matches("[a-zA-Z0-9]+")) {
                    return "Название группы должно содержать только буквы и цифры";
                } else if (!inputs.get(Constants.GROUPNAME).substring(0,1).matches("[A-Z]+")) {
                    return "Название группы начинается с заглавной буквы";
                } else if (inputs.get(Constants.GROUPNAME).length() > 200) {
                    return "Длина названия группы должна быть не более 200 букв";
                } else if (inputs.get(Constants.LABELSHORTNAME).length() > 150) {
                    return "Длина названия лейбла должна быть не более 150 букв";
                }
                break;
            case 10:
                if (!inputs.get(Constants.LABELSHORTNAME).matches("[a-zA-Z0-9]+")) {
                    return "Короткое имя лейбла должно содержать только буквы и цифры";
                } else if (!inputs.get(Constants.LABELSHORTNAME).substring(0,1).matches("[A-Z]+")) {
                    return "Короткое имя лейбла начинается с заглавной буквы";
                } else if (inputs.get(Constants.LABELSHORTNAME).length() > 150) {
                    return "Длина названия лейбла должна быть не более 150 букв";
                } else if (!inputs.get(Constants.EAN).matches("[0-9]+")) {
                    return "EAN должен состоять только из цифр";
                } else if (inputs.get(Constants.EAN).length() != 13) {
                    return "EAN по своему стандарту всегда имеет только 13 цифр";
                }
                break;
            case 12:
                if (!inputs.get(Constants.NICKNAME).matches("[a-zA-Z0-9]+")) {
                    return "Прозвище должно содержать только буквы и цифры.";
                } else if (!inputs.get(Constants.NICKNAME).substring(0,1).matches("[A-Z]+")) {
                    return "Прозвище должно начинаться с заглавной буквы";
                } else if (inputs.get(Constants.NICKNAME).length() > 100) {
                    return "Длина Прозвища должна быть не более 100 букв";
                }
                break;
            case 13:
                if (!inputs.get(Constants.LABELSHORTNAME).matches("[a-zA-Z0-9]+")) {
                    return "Короткое имя лейбла должно содержать только буквы и цифры";
                } else if (!inputs.get(Constants.LABELSHORTNAME).substring(0,1).matches("[A-Z]+")) {
                    return "Короткое имя лейбла начинается с заглавной буквы";
                } else if (inputs.get(Constants.LABELSHORTNAME).length() > 150) {
                    return "Длина названия лейбла должна быть не более 150 букв";
                }
                break;
        }
        return null;
    }

    private static QueryResult getAllLabels(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            List<Label> labels = session.createNativeQuery("SELECT * FROM shop.labels", Label.class).getResultList();
            List<Map<String, Object>> resultList = new ArrayList<>();

            for (Label label : labels) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", label.getId());
                row.put("shortname", label.getShortName());
                row.put("legal name", label.getLegalName());
                row.put("legal Address", label.getLegalAddress());
                row.put("year of Funding", label.getYearOfFunding());
                row.put("country", label.getCountry());

                resultList.add(row);
            }

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
                row.put("id_of_label", album.getLabel().getId());
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
            List<ParticipationDTO> participations = session.createNativeQuery("SELECT g.name, i.name, pe.nickname FROM shop.participations p JOIN " +
                    "shop.groups g ON p.id_of_group = g.id JOIN shop.instruments i ON i.id = p.id_of_instrument JOIN " +
                    "shop.personalities pe ON pe.id = p.id_of_personality", ParticipationDTO.class).getResultList();
            List<Map<String, Object>> resultList = new ArrayList<>();

            for (ParticipationDTO participation : participations) {
                Map<String, Object> row = new HashMap<>();
                row.put("id_of_group", participation.getNameOfGroup());
                row.put("id_of_personality", participation.getNameOfPersonality());
                row.put("id_of_instrument", participation.getNameOfInstrument());
                resultList.add(row);
            }

            return new QueryResult(true, resultList);
        }
    }

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
                    .setParameter("labelShortName", inputs.get(Constants.LABELSHORTNAME))
                    .setParameter("nickname", inputs.get(Constants.NICKNAME))
                    .uniqueResult();

            Map<String, Object> result = new HashMap<>();
            result.put("min_cost", minCost);

            return new QueryResult(true, Collections.singletonList(result));
        }
    }

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
                    .setParameter("genre", inputs.get(Constants.GENRE))
                    .setParameter("instrumentName", inputs.get(Constants.INSTRUMENTNAME))
                    .uniqueResult();

            Map<String, Object> result = new HashMap<>();
            result.put("avg_cost", avgCost);

            return new QueryResult(true, Collections.singletonList(result));
        }
    }

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
                    .setParameter("groupName", inputs.get(Constants.GROUPNAME))
                    .setParameter("labelShortName", inputs.get(Constants.LABELSHORTNAME))
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

    public static QueryResult updateAlbumLabel(SessionFactory factory, Map<String, String> inputs) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            List<Album> albumsBefore = session.createQuery("FROM Album", Album.class).getResultList();
            List<Map<String, Object>> beforeList = new ArrayList<>();

            for (Album album : albumsBefore) {
                Map<String, Object> row = new HashMap<>();
                row.put("ean", album.getEan());
                row.put("id_of_label", album.getLabel().getId());
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
                    .setParameter("labelShortName", inputs.get(Constants.LABELSHORTNAME))
                    .setParameter("ean", inputs.get(Constants.EAN))
                    .executeUpdate();

            List<Album> albumsAfter = session.createQuery("FROM Album", Album.class).getResultList();
            List<Map<String, Object>> afterList = new ArrayList<>();

            for (Album album : albumsAfter) {
                Map<String, Object> row = new HashMap<>();
                row.put("ean", album.getEan());
                row.put("id_of_label", album.getLabel().getId());
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

    public static QueryResult updateParticipation(SessionFactory factory, Map<String, String> inputs) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            List<ParticipationDTO> resultListBefore = session.createNativeQuery("SELECT g.name, i.name, pe.nickname FROM shop.participations p JOIN " +
                    "shop.groups g ON p.id_of_group = g.id JOIN shop.instruments i ON i.id = p.id_of_instrument JOIN " +
                    "shop.personalities pe ON pe.id = p.id_of_personality", ParticipationDTO.class).getResultList();
            List<Map<String, Object>> beforeList = new ArrayList<>();

            for (ParticipationDTO participation : resultListBefore) {
                Map<String, Object> row = new HashMap<>();
                row.put("id_of_group", participation.getNameOfGroup());
                row.put("id_of_personality", participation.getNameOfPersonality());
                row.put("id_of_instrument", participation.getNameOfInstrument());
                beforeList.add(row);
            }


            String query = "UPDATE shop.participations " +
                    "SET id_of_personality = (SELECT id FROM shop.personalities WHERE nickname = :nickname) " +
                    "WHERE id_of_group = (SELECT id FROM shop.groups WHERE name = :nameOfGroup) AND id_of_instrument = " +
                    "(SELECT id from shop.instruments WHERE name = :nameOfInstrument)";

            session.createNativeQuery(query)
                    .setParameter("nickname", inputs.get(Constants.NICKNAME))
                    .setParameter("nameOfGroup", inputs.get(Constants.GROUPNAME))
                    .setParameter("nameOfInstrument", inputs.get(Constants.INSTRUMENTNAME))
                    .executeUpdate();

            List<ParticipationDTO> resultListAfter = session.createNativeQuery("SELECT g.name, i.name, pe.nickname FROM shop.participations p JOIN " +
                    "shop.groups g ON p.id_of_group = g.id JOIN shop.instruments i ON i.id = p.id_of_instrument JOIN " +
                    "shop.personalities pe ON pe.id = p.id_of_personality", ParticipationDTO.class).getResultList();
            List<Map<String, Object>> afterList = new ArrayList<>();

            for (ParticipationDTO participation : resultListAfter) {
                Map<String, Object> row = new HashMap<>();
                row.put("id_of_group", participation.getNameOfGroup());
                row.put("id_of_personality", participation.getNameOfPersonality());
                row.put("id_of_instrument", participation.getNameOfInstrument());
                afterList.add(row);
            }

            session.getTransaction().commit();

            return new QueryResult(false, beforeList, afterList);
        }
    }

    public static QueryResult deleteAlbumsWithLabel(SessionFactory factory, Map<String, String> inputs) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            List<Album> albumsBefore = session.createQuery("FROM Album", Album.class).getResultList();
            List<Map<String, Object>> beforeList = new ArrayList<>();

            for (Album album : albumsBefore) {
                Map<String, Object> row = new HashMap<>();
                row.put("ean", album.getEan());
                row.put("id_of_label", album.getLabel().getId());
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
                    .setParameter("labelShortName", inputs.get(Constants.LABELSHORTNAME))
                    .executeUpdate();

            List<Album> albumsAfter = session.createQuery("FROM Album", Album.class).getResultList();
            List<Map<String, Object>> afterList = new ArrayList<>();

            for (Album album : albumsAfter) {
                Map<String, Object> row = new HashMap<>();
                row.put("ean", album.getEan());
                row.put("id_of_label", album.getLabel().getId());
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

    public static QueryResult deleteAlbumsWithCompositionsByPersonality(SessionFactory factory, Map<String, String> inputs) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            List<Album> albumsBefore = session.createQuery("FROM Album", Album.class).getResultList();
            List<Map<String, Object>> beforeList = new ArrayList<>();

            for (Album album : albumsBefore) {
                Map<String, Object> row = new HashMap<>();
                row.put("ean", album.getEan());
                row.put("id_of_label", album.getLabel().getId());
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
                    .setParameter("nickname", inputs.get(Constants.NICKNAME))
                    .executeUpdate();

            List<Album> albumsAfter = session.createQuery("FROM Album", Album.class).getResultList();
            List<Map<String, Object>> afterList = new ArrayList<>();

            for (Album album : albumsAfter) {
                Map<String, Object> row = new HashMap<>();
                row.put("ean", album.getEan());
                row.put("id_of_label", album.getLabel().getId());
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

    }

    public static class ParticipationDTO {
        private String nameOfGroup;
        private String nameOfInstrument;
        private String NameOfPersonality;

        public String getNameOfGroup() {
            return nameOfGroup;
        }

        public String getNameOfInstrument() {
            return nameOfInstrument;
        }

        public String getNameOfPersonality() {
            return NameOfPersonality;
        }

        public ParticipationDTO () {

        }

        public ParticipationDTO(String nameOfGroup, String nameOfInstrument, String nameOfPersonality) {
            this.nameOfGroup = nameOfGroup;
            this.nameOfInstrument = nameOfInstrument;
            NameOfPersonality = nameOfPersonality;
        }
    }

}
