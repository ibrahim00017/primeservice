package com.mpd.prime.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Allouer.
 */
@Entity
@Table(name = "allouer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Allouer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0")
    @DecimalMax(value = "20")
    @Column(name = "note")
    private Double note;

    @Column(name = "nombre_jours")
    private Integer nombreJours;

    @Column(name = "montant")
    private Double montant;

    @OneToOne
    @JoinColumn(unique = true)
    private Agent agent;

    @OneToOne
    @JoinColumn(unique = true)
    private Prime prime;

    @OneToOne
    @JoinColumn(unique = true)
    private Annee annee;

    @OneToOne
    @JoinColumn(unique = true)
    private Trimestre trimestre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNote() {
        return note;
    }

    public Allouer note(Double note) {
        this.note = note;
        return this;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public Integer getNombreJours() {
        return nombreJours;
    }

    public Allouer nombreJours(Integer nombreJours) {
        this.nombreJours = nombreJours;
        return this;
    }

    public void setNombreJours(Integer nombreJours) {
        this.nombreJours = nombreJours;
    }

    public Double getMontant() {
        return montant;
    }

    public Allouer montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Agent getAgent() {
        return agent;
    }

    public Allouer agent(Agent agent) {
        this.agent = agent;
        return this;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Prime getPrime() {
        return prime;
    }

    public Allouer prime(Prime prime) {
        this.prime = prime;
        return this;
    }

    public void setPrime(Prime prime) {
        this.prime = prime;
    }

    public Annee getAnnee() {
        return annee;
    }

    public Allouer annee(Annee annee) {
        this.annee = annee;
        return this;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public Trimestre getTrimestre() {
        return trimestre;
    }

    public Allouer trimestre(Trimestre trimestre) {
        this.trimestre = trimestre;
        return this;
    }

    public void setTrimestre(Trimestre trimestre) {
        this.trimestre = trimestre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Allouer)) {
            return false;
        }
        return id != null && id.equals(((Allouer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Allouer{" +
            "id=" + getId() +
            ", note=" + getNote() +
            ", nombreJours=" + getNombreJours() +
            ", montant=" + getMontant() +
            "}";
    }
}
