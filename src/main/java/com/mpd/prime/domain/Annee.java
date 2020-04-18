package com.mpd.prime.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Annee.
 */
@Entity
@Table(name = "annee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Annee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code_annee", nullable = false, unique = true)
    private Integer codeAnnee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodeAnnee() {
        return codeAnnee;
    }

    public Annee codeAnnee(Integer codeAnnee) {
        this.codeAnnee = codeAnnee;
        return this;
    }

    public void setCodeAnnee(Integer codeAnnee) {
        this.codeAnnee = codeAnnee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Annee)) {
            return false;
        }
        return id != null && id.equals(((Annee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Annee{" +
            "id=" + getId() +
            ", codeAnnee=" + getCodeAnnee() +
            "}";
    }
}
