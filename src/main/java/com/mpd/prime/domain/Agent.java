package com.mpd.prime.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.mpd.prime.domain.enumeration.SituationMatrimoniale;

import com.mpd.prime.domain.enumeration.Statut;

/**
 * A Agent.
 */
@Entity
@Table(name = "agent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "matricule", nullable = false, unique = true)
    private Long matricule;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenoms")
    private String prenoms;

    @Column(name = "date_naiss")
    private Instant dateNaiss;

    @Column(name = "lieu_naiss")
    private String lieuNaiss;

    @NotNull
    @Column(name = "contact", nullable = false, unique = true)
    private String contact;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "date_prise_serv")
    private Instant datePriseServ;

    @Enumerated(EnumType.STRING)
    @Column(name = "situation_matrim")
    private SituationMatrimoniale situationMatrim;

    @Column(name = "nombre_enfts")
    private Integer nombreEnfts;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Statut statut;

    @OneToOne
    @JoinColumn(unique = true)
    private Fonction fonction;

    @OneToOne
    @JoinColumn(unique = true)
    private Direction direction;

    @OneToOne
    @JoinColumn(unique = true)
    private Grade grade;

    @OneToOne
    @JoinColumn(unique = true)
    private Corps corps;

    @OneToMany(mappedBy = "agent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Compte> comptes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatricule() {
        return matricule;
    }

    public Agent matricule(Long matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(Long matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public Agent nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public Agent prenoms(String prenoms) {
        this.prenoms = prenoms;
        return this;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public Instant getDateNaiss() {
        return dateNaiss;
    }

    public Agent dateNaiss(Instant dateNaiss) {
        this.dateNaiss = dateNaiss;
        return this;
    }

    public void setDateNaiss(Instant dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaiss() {
        return lieuNaiss;
    }

    public Agent lieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
        return this;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public String getContact() {
        return contact;
    }

    public Agent contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public Agent email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public Agent adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Instant getDatePriseServ() {
        return datePriseServ;
    }

    public Agent datePriseServ(Instant datePriseServ) {
        this.datePriseServ = datePriseServ;
        return this;
    }

    public void setDatePriseServ(Instant datePriseServ) {
        this.datePriseServ = datePriseServ;
    }

    public SituationMatrimoniale getSituationMatrim() {
        return situationMatrim;
    }

    public Agent situationMatrim(SituationMatrimoniale situationMatrim) {
        this.situationMatrim = situationMatrim;
        return this;
    }

    public void setSituationMatrim(SituationMatrimoniale situationMatrim) {
        this.situationMatrim = situationMatrim;
    }

    public Integer getNombreEnfts() {
        return nombreEnfts;
    }

    public Agent nombreEnfts(Integer nombreEnfts) {
        this.nombreEnfts = nombreEnfts;
        return this;
    }

    public void setNombreEnfts(Integer nombreEnfts) {
        this.nombreEnfts = nombreEnfts;
    }

    public Statut getStatut() {
        return statut;
    }

    public Agent statut(Statut statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public Agent fonction(Fonction fonction) {
        this.fonction = fonction;
        return this;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    public Direction getDirection() {
        return direction;
    }

    public Agent direction(Direction direction) {
        this.direction = direction;
        return this;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Grade getGrade() {
        return grade;
    }

    public Agent grade(Grade grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Corps getCorps() {
        return corps;
    }

    public Agent corps(Corps corps) {
        this.corps = corps;
        return this;
    }

    public void setCorps(Corps corps) {
        this.corps = corps;
    }

    public Set<Compte> getComptes() {
        return comptes;
    }

    public Agent comptes(Set<Compte> comptes) {
        this.comptes = comptes;
        return this;
    }

    public Agent addComptes(Compte compte) {
        this.comptes.add(compte);
        compte.setAgent(this);
        return this;
    }

    public Agent removeComptes(Compte compte) {
        this.comptes.remove(compte);
        compte.setAgent(null);
        return this;
    }

    public void setComptes(Set<Compte> comptes) {
        this.comptes = comptes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agent)) {
            return false;
        }
        return id != null && id.equals(((Agent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Agent{" +
            "id=" + getId() +
            ", matricule=" + getMatricule() +
            ", nom='" + getNom() + "'" +
            ", prenoms='" + getPrenoms() + "'" +
            ", dateNaiss='" + getDateNaiss() + "'" +
            ", lieuNaiss='" + getLieuNaiss() + "'" +
            ", contact='" + getContact() + "'" +
            ", email='" + getEmail() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", datePriseServ='" + getDatePriseServ() + "'" +
            ", situationMatrim='" + getSituationMatrim() + "'" +
            ", nombreEnfts=" + getNombreEnfts() +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
