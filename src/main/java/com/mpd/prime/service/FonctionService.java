package com.mpd.prime.service;

import com.mpd.prime.domain.Fonction;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Fonction}.
 */
public interface FonctionService {

    /**
     * Save a fonction.
     *
     * @param fonction the entity to save.
     * @return the persisted entity.
     */
    Fonction save(Fonction fonction);

    /**
     * Get all the fonctions.
     *
     * @return the list of entities.
     */
    List<Fonction> findAll();

    /**
     * Get the "id" fonction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Fonction> findOne(Long id);

    /**
     * Delete the "id" fonction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
