package com.mpd.prime.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.mpd.prime.domain.enumeration.Typeprime;

/**
 * A Prime.
 */
@Entity
@Table(name = "prime")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Prime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "prime", nullable = false, unique = true)
    private String prime;

    @Column(name = "taux_mensuel")
    private Double tauxMensuel;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_prime")
    private Typeprime typePrime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrime() {
        return prime;
    }

    public Prime prime(String prime) {
        this.prime = prime;
        return this;
    }

    public void setPrime(String prime) {
        this.prime = prime;
    }

    public Double getTauxMensuel() {
        return tauxMensuel;
    }

    public Prime tauxMensuel(Double tauxMensuel) {
        this.tauxMensuel = tauxMensuel;
        return this;
    }

    public void setTauxMensuel(Double tauxMensuel) {
        this.tauxMensuel = tauxMensuel;
    }

    public Typeprime getTypePrime() {
        return typePrime;
    }

    public Prime typePrime(Typeprime typePrime) {
        this.typePrime = typePrime;
        return this;
    }

    public void setTypePrime(Typeprime typePrime) {
        this.typePrime = typePrime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prime)) {
            return false;
        }
        return id != null && id.equals(((Prime) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Prime{" +
            "id=" + getId() +
            ", prime='" + getPrime() + "'" +
            ", tauxMensuel=" + getTauxMensuel() +
            ", typePrime='" + getTypePrime() + "'" +
            "}";
    }
}
