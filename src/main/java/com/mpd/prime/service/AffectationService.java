package com.mpd.prime.service;

import com.mpd.prime.domain.Affectation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Affectation}.
 */
public interface AffectationService {

    /**
     * Save a affectation.
     *
     * @param affectation the entity to save.
     * @return the persisted entity.
     */
    Affectation save(Affectation affectation);

    /**
     * Get all the affectations.
     *
     * @return the list of entities.
     */
    List<Affectation> findAll();

    /**
     * Get the "id" affectation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Affectation> findOne(Long id);

    /**
     * Delete the "id" affectation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
