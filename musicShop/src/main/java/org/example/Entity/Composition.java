package org.example.entity;

import jakarta.persistence.*;
import java.time.Duration;

@Entity
@Table(name = "compositions", schema = "shop")
public class Composition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "ean_of_album", nullable = false)
    public Album album;

    @ManyToOne
    @JoinColumn(name = "id_of_group", nullable = false)
    public Group group;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public Duration duration;

    @Column(nullable = false)
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
