package com.mpd.prime.service.impl;

import com.mpd.prime.service.AllouerService;
import com.mpd.prime.domain.Allouer;
import com.mpd.prime.repository.AllouerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Allouer}.
 */
@Service
@Transactional
public class AllouerServiceImpl implements AllouerService {

    private final Logger log = LoggerFactory.getLogger(AllouerServiceImpl.class);

    private final AllouerRepository allouerRepository;

    public AllouerServiceImpl(AllouerRepository allouerRepository) {
        this.allouerRepository = allouerRepository;
    }

    /**
     * Save a allouer.
     *
     * @param allouer the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Allouer save(Allouer allouer) {
        log.debug("Request to save Allouer : {}", allouer);
        return allouerRepository.save(allouer);
    }

    /**
     * Get all the allouers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Allouer> findAll() {
        log.debug("Request to get all Allouers");
        return allouerRepository.findAll();
    }

    /**
     * Get one allouer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Allouer> findOne(Long id) {
        log.debug("Request to get Allouer : {}", id);
        return allouerRepository.findById(id);
    }

    /**
     * Delete the allouer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Allouer : {}", id);
        allouerRepository.deleteById(id);
    }
}
