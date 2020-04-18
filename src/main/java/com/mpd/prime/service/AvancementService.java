package com.mpd.prime.service;

import com.mpd.prime.domain.Avancement;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Avancement}.
 */
public interface AvancementService {

    /**
     * Save a avancement.
     *
     * @param avancement the entity to save.
     * @return the persisted entity.
     */
    Avancement save(Avancement avancement);

    /**
     * Get all the avancements.
     *
     * @return the list of entities.
     */
    List<Avancement> findAll();

    /**
     * Get the "id" avancement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Avancement> findOne(Long id);

    /**
     * Delete the "id" avancement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
