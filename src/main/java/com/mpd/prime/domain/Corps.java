package com.mpd.prime.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Corps.
 */
@Entity
@Table(name = "corps")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Corps implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle_corps", nullable = false, unique = true)
    private String libelleCorps;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleCorps() {
        return libelleCorps;
    }

    public Corps libelleCorps(String libelleCorps) {
        this.libelleCorps = libelleCorps;
        return this;
    }

    public void setLibelleCorps(String libelleCorps) {
        this.libelleCorps = libelleCorps;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Corps)) {
            return false;
        }
        return id != null && id.equals(((Corps) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Corps{" +
            "id=" + getId() +
            ", libelleCorps='" + getLibelleCorps() + "'" +
            "}";
    }
}
