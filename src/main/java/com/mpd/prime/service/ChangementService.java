package com.mpd.prime.service;

import com.mpd.prime.domain.Changement;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Changement}.
 */
public interface ChangementService {

    /**
     * Save a changement.
     *
     * @param changement the entity to save.
     * @return the persisted entity.
     */
    Changement save(Changement changement);

    /**
     * Get all the changements.
     *
     * @return the list of entities.
     */
    List<Changement> findAll();

    /**
     * Get the "id" changement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Changement> findOne(Long id);

    /**
     * Delete the "id" changement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
