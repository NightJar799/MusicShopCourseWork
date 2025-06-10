package org.example.Entity;

import jakarta.persistence.*;
import java.time.Duration;

@Entity
@Table(name = "compositions", schema = "shop")
public class Composition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ean_of_album", nullable = false)
    public Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_of_group", nullable = false)
    public Group group;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "duration", nullable = false)
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
}
