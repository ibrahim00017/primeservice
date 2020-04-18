package com.mpd.prime.service.impl;

import com.mpd.prime.service.ChangementService;
import com.mpd.prime.domain.Changement;
import com.mpd.prime.repository.ChangementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Changement}.
 */
@Service
@Transactional
public class ChangementServiceImpl implements ChangementService {

    private final Logger log = LoggerFactory.getLogger(ChangementServiceImpl.class);

    private final ChangementRepository changementRepository;

    public ChangementServiceImpl(ChangementRepository changementRepository) {
        this.changementRepository = changementRepository;
    }

    /**
     * Save a changement.
     *
     * @param changement the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Changement save(Changement changement) {
        log.debug("Request to save Changement : {}", changement);
        return changementRepository.save(changement);
    }

    /**
     * Get all the changements.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Changement> findAll() {
        log.debug("Request to get all Changements");
        return changementRepository.findAll();
    }

    /**
     * Get one changement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Changement> findOne(Long id) {
        log.debug("Request to get Changement : {}", id);
        return changementRepository.findById(id);
    }

    /**
     * Delete the changement by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Changement : {}", id);
        changementRepository.deleteById(id);
    }
}
