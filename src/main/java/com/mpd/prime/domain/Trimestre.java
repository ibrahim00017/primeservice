package com.mpd.prime.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Trimestre.
 */
@Entity
@Table(name = "trimestre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Trimestre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code_trimestre", nullable = false, unique = true)
    private Integer codeTrimestre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodeTrimestre() {
        return codeTrimestre;
    }

    public Trimestre codeTrimestre(Integer codeTrimestre) {
        this.codeTrimestre = codeTrimestre;
        return this;
    }

    public void setCodeTrimestre(Integer codeTrimestre) {
        this.codeTrimestre = codeTrimestre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trimestre)) {
            return false;
        }
        return id != null && id.equals(((Trimestre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Trimestre{" +
            "id=" + getId() +
            ", codeTrimestre=" + getCodeTrimestre() +
            "}";
    }
}
