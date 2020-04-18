package com.mpd.prime.service;

import com.mpd.prime.domain.Direction;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Direction}.
 */
public interface DirectionService {

    /**
     * Save a direction.
     *
     * @param direction the entity to save.
     * @return the persisted entity.
     */
    Direction save(Direction direction);

    /**
     * Get all the directions.
     *
     * @return the list of entities.
     */
    List<Direction> findAll();

    /**
     * Get the "id" direction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Direction> findOne(Long id);

    /**
     * Delete the "id" direction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
