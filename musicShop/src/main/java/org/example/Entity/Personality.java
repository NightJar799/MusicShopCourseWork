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
        return "Personality{" +
                "id=" + id +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", firstname='" + firstname + '\'' +
                ", secondName='" + secondName + '\'' +
                ", thirdName='" + thirdName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", nickname='" + nickname + '\'' +
                ", frontman=" + frontman +
                '}';
    }
}
