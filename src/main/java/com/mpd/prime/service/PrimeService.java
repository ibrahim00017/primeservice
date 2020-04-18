package com.mpd.prime.service;

import com.mpd.prime.domain.Prime;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Prime}.
 */
public interface PrimeService {

    /**
     * Save a prime.
     *
     * @param prime the entity to save.
     * @return the persisted entity.
     */
    Prime save(Prime prime);

    /**
     * Get all the primes.
     *
     * @return the list of entities.
     */
    List<Prime> findAll();

    /**
     * Get the "id" prime.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Prime> findOne(Long id);

    /**
     * Delete the "id" prime.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
