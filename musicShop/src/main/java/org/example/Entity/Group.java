package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "groups", schema = "shop")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    public Integer id;

    @Column(name = "country_of_group", nullable = false)
    public String country;

    @Column(name = "year_of_founding")
    public Integer yearOfFunding;

    @Column(nullable = false, unique = true)
    public String name;

    @Column(nullable = false)
    public String language;

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", yearOfFunding=" + yearOfFunding +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}