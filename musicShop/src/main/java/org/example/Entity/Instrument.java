package org.example.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "instruments", schema = "shop")
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    public Long id;

    @Column(name = "type_of_instrument", nullable = false)
    public String type;

    @Column(nullable = false)
    public String name;

    @Column(name = "name_of_model", nullable = false, unique = true)
    public String modelName;

    @Override
    public String toString() {
        return "Instrument{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", modelName='" + modelName + '\'' +
                '}';
    }
}