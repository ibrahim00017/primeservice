package com.mpd.prime.service.impl;

import com.mpd.prime.service.CorpsService;
import com.mpd.prime.domain.Corps;
import com.mpd.prime.repository.CorpsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Corps}.
 */
@Service
@Transactional
public class CorpsServiceImpl implements CorpsService {

    private final Logger log = LoggerFactory.getLogger(CorpsServiceImpl.class);

    private final CorpsRepository corpsRepository;

    public CorpsServiceImpl(CorpsRepository corpsRepository) {
        this.corpsRepository = corpsRepository;
    }

    /**
     * Save a corps.
     *
     * @param corps the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Corps save(Corps corps) {
        log.debug("Request to save Corps : {}", corps);
        return corpsRepository.save(corps);
    }

    /**
     * Get all the corps.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Corps> findAll() {
        log.debug("Request to get all Corps");
        return corpsRepository.findAll();
    }

    /**
     * Get one corps by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Corps> findOne(Long id) {
        log.debug("Request to get Corps : {}", id);
        return corpsRepository.findById(id);
    }

    /**
     * Delete the corps by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Corps : {}", id);
        corpsRepository.deleteById(id);
    }
}
