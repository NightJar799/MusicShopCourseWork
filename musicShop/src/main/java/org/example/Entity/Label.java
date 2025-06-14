package org.example.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "labels", schema = "shop")
public class Label{
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

    public Label(){

    }

    public Label(Integer id,Integer yearOfFunding, String legalAddress, String legalName, String shortName, String country){
        this.id = id;
        this.yearOfFunding = yearOfFunding;
        this.legalAddress = legalAddress;
        this.legalName = legalName;
        this.shortName = shortName;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYearOfFunding() {
        return yearOfFunding;
    }

    public void setYearOfFunding(Integer yearOfFunding) {
        this.yearOfFunding = yearOfFunding;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
