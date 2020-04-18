package com.mpd.prime.service.impl;

import com.mpd.prime.service.AnneeService;
import com.mpd.prime.domain.Annee;
import com.mpd.prime.repository.AnneeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Annee}.
 */
@Service
@Transactional
public class AnneeServiceImpl implements AnneeService {

    private final Logger log = LoggerFactory.getLogger(AnneeServiceImpl.class);

    private final AnneeRepository anneeRepository;

    public AnneeServiceImpl(AnneeRepository anneeRepository) {
        this.anneeRepository = anneeRepository;
    }

    /**
     * Save a annee.
     *
     * @param annee the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Annee save(Annee annee) {
        log.debug("Request to save Annee : {}", annee);
        return anneeRepository.save(annee);
    }

    /**
     * Get all the annees.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Annee> findAll() {
        log.debug("Request to get all Annees");
        return anneeRepository.findAll();
    }

    /**
     * Get one annee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Annee> findOne(Long id) {
        log.debug("Request to get Annee : {}", id);
        return anneeRepository.findById(id);
    }

    /**
     * Delete the annee by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Annee : {}", id);
        anneeRepository.deleteById(id);
    }
}
