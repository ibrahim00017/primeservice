package com.mpd.prime.service;

import com.mpd.prime.domain.Compte;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Compte}.
 */
public interface CompteService {

    /**
     * Save a compte.
     *
     * @param compte the entity to save.
     * @return the persisted entity.
     */
    Compte save(Compte compte);

    /**
     * Get all the comptes.
     *
     * @return the list of entities.
     */
    List<Compte> findAll();

    /**
     * Get the "id" compte.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Compte> findOne(Long id);

    /**
     * Delete the "id" compte.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
