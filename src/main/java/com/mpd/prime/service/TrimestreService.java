package com.mpd.prime.service;

import com.mpd.prime.domain.Trimestre;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Trimestre}.
 */
public interface TrimestreService {

    /**
     * Save a trimestre.
     *
     * @param trimestre the entity to save.
     * @return the persisted entity.
     */
    Trimestre save(Trimestre trimestre);

    /**
     * Get all the trimestres.
     *
     * @return the list of entities.
     */
    List<Trimestre> findAll();

    /**
     * Get the "id" trimestre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Trimestre> findOne(Long id);

    /**
     * Delete the "id" trimestre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
