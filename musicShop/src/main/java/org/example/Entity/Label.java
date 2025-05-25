package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "labels", schema = "shop")
public class Label {
    @Id
    @Column(insertable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="labelIdSequence")
    @SequenceGenerator(name = "labelIdSequence", sequenceName = "label_id_seq", allocationSize = 1)
    public Integer id;

    @Column (name = "year_of_founding", nullable = false)
    public Integer yearOfFunding;

    @Column (name = "legal_address", insertable = false)
    public String legalAddress;

    @Column (name = "legal_name", nullable = false)
    public String legalName;

    @Column (name = "short_name", nullable = false)
    public String shortName;

    @Column (nullable = false)
    public String country;

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", yearOfFunding=" + yearOfFunding +
                ", legalAddress='" + legalAddress + '\'' +
                ", legalName='" + legalName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
