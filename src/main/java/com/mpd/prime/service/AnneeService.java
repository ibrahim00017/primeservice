package com.mpd.prime.service;

import com.mpd.prime.domain.Annee;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Annee}.
 */
public interface AnneeService {

    /**
     * Save a annee.
     *
     * @param annee the entity to save.
     * @return the persisted entity.
     */
    Annee save(Annee annee);

    /**
     * Get all the annees.
     *
     * @return the list of entities.
     */
    List<Annee> findAll();

    /**
     * Get the "id" annee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Annee> findOne(Long id);

    /**
     * Delete the "id" annee.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
