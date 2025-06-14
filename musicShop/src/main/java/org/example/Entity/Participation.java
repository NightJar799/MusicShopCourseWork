package org.example.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "participations", schema = "shop")
@IdClass(ParticipationId.class)
public class Participation {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_of_group", nullable = false)
    public Group group;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_of_personality", nullable = false)
    public Personality personality;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_of_instrument", nullable = false)
    public Instrument instrument;

    @Override
    public String toString() {
        return "Participation{" +
                "group=" + group +
                ", personality=" + personality +
                ", instrument=" + instrument +
                '}';
    }

    public Group getGroup() {
        return group;
    }

    public Personality getPersonality() {
        return personality;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Participation(Group group, Personality personality, Instrument instrument) {
        this.group = group;
        this.personality = personality;
        this.instrument = instrument;
    }

    public Participation() {

    }
}



class ParticipationId implements java.io.Serializable {
    public Integer group;
    public Long personality;
    public Long instrument;
}
