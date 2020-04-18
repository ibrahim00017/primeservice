package com.mpd.prime.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Changement.
 */
@Entity
@Table(name = "changement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Changement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_debut")
    private Instant dateDebut;

    @Column(name = "datefin")
    private Instant datefin;

    @OneToOne
    @JoinColumn(unique = true)
    private Agent agent;

    @OneToOne
    @JoinColumn(unique = true)
    private Corps corps;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public Changement dateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDatefin() {
        return datefin;
    }

    public Changement datefin(Instant datefin) {
        this.datefin = datefin;
        return this;
    }

    public void setDatefin(Instant datefin) {
        this.datefin = datefin;
    }

    public Agent getAgent() {
        return agent;
    }

    public Changement agent(Agent agent) {
        this.agent = agent;
        return this;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Corps getCorps() {
        return corps;
    }

    public Changement corps(Corps corps) {
        this.corps = corps;
        return this;
    }

    public void setCorps(Corps corps) {
        this.corps = corps;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Changement)) {
            return false;
        }
        return id != null && id.equals(((Changement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Changement{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", datefin='" + getDatefin() + "'" +
            "}";
    }
}
