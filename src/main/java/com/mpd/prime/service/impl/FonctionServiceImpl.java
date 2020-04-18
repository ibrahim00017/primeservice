package com.mpd.prime.service.impl;

import com.mpd.prime.service.FonctionService;
import com.mpd.prime.domain.Fonction;
import com.mpd.prime.repository.FonctionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Fonction}.
 */
@Service
@Transactional
public class FonctionServiceImpl implements FonctionService {

    private final Logger log = LoggerFactory.getLogger(FonctionServiceImpl.class);

    private final FonctionRepository fonctionRepository;

    public FonctionServiceImpl(FonctionRepository fonctionRepository) {
        this.fonctionRepository = fonctionRepository;
    }

    /**
     * Save a fonction.
     *
     * @param fonction the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Fonction save(Fonction fonction) {
        log.debug("Request to save Fonction : {}", fonction);
        return fonctionRepository.save(fonction);
    }

    /**
     * Get all the fonctions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Fonction> findAll() {
        log.debug("Request to get all Fonctions");
        return fonctionRepository.findAll();
    }

    /**
     * Get one fonction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Fonction> findOne(Long id) {
        log.debug("Request to get Fonction : {}", id);
        return fonctionRepository.findById(id);
    }

    /**
     * Delete the fonction by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fonction : {}", id);
        fonctionRepository.deleteById(id);
    }
}
