package org.example.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "personalities", schema = "shop")
public class Personality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    public Long id;

    @Column(nullable = false)
    public String language;

    @Column(nullable = false)
    public String country;

    @Column(nullable = false)
    public String firstname;

    @Column(nullable = false)
    public String secondName;

    public String thirdName;

    @Column(name = "date_of_birth", nullable = false)
    public LocalDate dateOfBirth;

    @Column(nullable = false)
    public String nickname;

    @Column(nullable = false)
    public Boolean frontman;

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    public Long getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNickname() {
        return nickname;
    }

    public Boolean getFrontman() {
        return frontman;
    }

    public Personality(Long id, String language, String country, String firstname, String secondName, String thirdName, LocalDate dateOfBirth, String nickname, Boolean frontman) {
        this.id = id;
        this.language = language;
        this.country = country;
        this.firstname = firstname;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.dateOfBirth = dateOfBirth;
        this.nickname = nickname;
        this.frontman = frontman;
    }

    public Personality() {

    }
}
