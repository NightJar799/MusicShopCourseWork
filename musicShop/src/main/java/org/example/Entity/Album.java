package org.example.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "albums", schema = "shop")
public class Album {
    @Id
    @Column(name = "ean", nullable = false)
    public Long ean;

    @ManyToOne
    @JoinColumn(name = "id_of_label", nullable = false)
    public Label label;

    @Column(nullable = false)
    public Integer cost;

    @Column(name = "date_of_relise", nullable = false)
    public LocalDate dateOfRelease;

    @Column(name = "type_of_package", nullable = false)
    public String typeOfPackage;

    @Column(name = "type_of_publication", nullable = false)
    public String typeOfPublication;

    @Column(nullable = false)
    public String name;

    @Override
    public String toString() {
        return "Album{" +
                "ean=" + ean +
                ", label=" + label +
                ", cost=" + cost +
                ", dateOfRelease=" + dateOfRelease +
                ", typeOfPackage='" + typeOfPackage + '\'' +
                ", typeOfPublication='" + typeOfPublication + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}