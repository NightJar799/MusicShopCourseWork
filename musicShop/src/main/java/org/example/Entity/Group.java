package org.example.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "groups", schema = "shop")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false)
    public Integer id;

    @Column(name = "country_of_group", nullable = false)
    public String country;

    @Column(name = "year_of_founding")
    public Integer yearOfFunding;

    @Column(name = "name", nullable = false, unique = true)
    public String name;

    @Column(name = "language", nullable = false)
    public String language;

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    public Integer getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public Integer getYearOfFunding() {
        return yearOfFunding;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public Group() {

    }

    public Group(Integer id, String country, Integer yearOfFunding, String name, String language) {
        this.id = id;
        this.country = country;
        this.yearOfFunding = yearOfFunding;
        this.name = name;
        this.language = language;
    }

}