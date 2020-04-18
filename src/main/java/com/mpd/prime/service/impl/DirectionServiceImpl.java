package com.mpd.prime.service.impl;

import com.mpd.prime.service.DirectionService;
import com.mpd.prime.domain.Direction;
import com.mpd.prime.repository.DirectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Direction}.
 */
@Service
@Transactional
public class DirectionServiceImpl implements DirectionService {

    private final Logger log = LoggerFactory.getLogger(DirectionServiceImpl.class);

    private final DirectionRepository directionRepository;

    public DirectionServiceImpl(DirectionRepository directionRepository) {
        this.directionRepository = directionRepository;
    }

    /**
     * Save a direction.
     *
     * @param direction the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Direction save(Direction direction) {
        log.debug("Request to save Direction : {}", direction);
        return directionRepository.save(direction);
    }

    /**
     * Get all the directions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Direction> findAll() {
        log.debug("Request to get all Directions");
        return directionRepository.findAll();
    }

    /**
     * Get one direction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Direction> findOne(Long id) {
        log.debug("Request to get Direction : {}", id);
        return directionRepository.findById(id);
    }

    /**
     * Delete the direction by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Direction : {}", id);
        directionRepository.deleteById(id);
    }
}
