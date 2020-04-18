package com.mpd.prime.service.impl;

import com.mpd.prime.service.AvancementService;
import com.mpd.prime.domain.Avancement;
import com.mpd.prime.repository.AvancementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Avancement}.
 */
@Service
@Transactional
public class AvancementServiceImpl implements AvancementService {

    private final Logger log = LoggerFactory.getLogger(AvancementServiceImpl.class);

    private final AvancementRepository avancementRepository;

    public AvancementServiceImpl(AvancementRepository avancementRepository) {
        this.avancementRepository = avancementRepository;
    }

    /**
     * Save a avancement.
     *
     * @param avancement the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Avancement save(Avancement avancement) {
        log.debug("Request to save Avancement : {}", avancement);
        return avancementRepository.save(avancement);
    }

    /**
     * Get all the avancements.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Avancement> findAll() {
        log.debug("Request to get all Avancements");
        return avancementRepository.findAll();
    }

    /**
     * Get one avancement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Avancement> findOne(Long id) {
        log.debug("Request to get Avancement : {}", id);
        return avancementRepository.findById(id);
    }

    /**
     * Delete the avancement by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Avancement : {}", id);
        avancementRepository.deleteById(id);
    }
}
