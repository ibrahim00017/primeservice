package com.mpd.prime.service;

import com.mpd.prime.domain.Allouer;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Allouer}.
 */
public interface AllouerService {

    /**
     * Save a allouer.
     *
     * @param allouer the entity to save.
     * @return the persisted entity.
     */
    Allouer save(Allouer allouer);

    /**
     * Get all the allouers.
     *
     * @return the list of entities.
     */
    List<Allouer> findAll();

    /**
     * Get the "id" allouer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Allouer> findOne(Long id);

    /**
     * Delete the "id" allouer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
