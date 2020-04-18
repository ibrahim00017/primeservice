package com.mpd.prime.service;

import com.mpd.prime.domain.Corps;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Corps}.
 */
public interface CorpsService {

    /**
     * Save a corps.
     *
     * @param corps the entity to save.
     * @return the persisted entity.
     */
    Corps save(Corps corps);

    /**
     * Get all the corps.
     *
     * @return the list of entities.
     */
    List<Corps> findAll();

    /**
     * Get the "id" corps.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Corps> findOne(Long id);

    /**
     * Delete the "id" corps.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
