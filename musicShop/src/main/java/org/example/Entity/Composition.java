package org.example.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.annotations.Type;
import org.hibernate.type.descriptor.java.DurationJavaType;

import java.time.Duration;

@Entity
@Table(name = "compositions", schema = "shop")
public class Composition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false)
    public Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ean_of_album", nullable = false)
    public Album album;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_of_group", nullable = false)
    public Group group;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "duration", nullable = false, columnDefinition = "interval")
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    public Duration duration;

    @Column(name = "genre", nullable = false)
    public String genre;

    @Column(name = "number_of_comp", nullable = false)
    public Short numberOfComp;

    @Override
    public String toString() {
        return "Composition{" +
                "id=" + id +
                ", album=" + album +
                ", group=" + group +
                ", name='" + name + '\'' +
                ", duration=" + duration +
                ", genre='" + genre + '\'' +
                ", numberOfComp=" + numberOfComp +
                '}';
    }

    public Long getId() {
        return id;
    }

    public Album getAlbum() {
        return album;
    }

    public Group getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public Short getNumberOfComp() {
        return numberOfComp;
    }

    public Composition(Long id, Album album, Group group, String name, Duration duration, String genre, Short numberOfComp) {
        this.id = id;
        this.album = album;
        this.group = group;
        this.name = name;
        this.duration = duration;
        this.genre = genre;
        this.numberOfComp = numberOfComp;
    }

    public Composition() {

    }
}

