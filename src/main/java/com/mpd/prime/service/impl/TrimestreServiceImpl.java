package com.mpd.prime.service.impl;

import com.mpd.prime.service.TrimestreService;
import com.mpd.prime.domain.Trimestre;
import com.mpd.prime.repository.TrimestreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Trimestre}.
 */
@Service
@Transactional
public class TrimestreServiceImpl implements TrimestreService {

    private final Logger log = LoggerFactory.getLogger(TrimestreServiceImpl.class);

    private final TrimestreRepository trimestreRepository;

    public TrimestreServiceImpl(TrimestreRepository trimestreRepository) {
        this.trimestreRepository = trimestreRepository;
    }

    /**
     * Save a trimestre.
     *
     * @param trimestre the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Trimestre save(Trimestre trimestre) {
        log.debug("Request to save Trimestre : {}", trimestre);
        return trimestreRepository.save(trimestre);
    }

    /**
     * Get all the trimestres.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Trimestre> findAll() {
        log.debug("Request to get all Trimestres");
        return trimestreRepository.findAll();
    }

    /**
     * Get one trimestre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Trimestre> findOne(Long id) {
        log.debug("Request to get Trimestre : {}", id);
        return trimestreRepository.findById(id);
    }

    /**
     * Delete the trimestre by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trimestre : {}", id);
        trimestreRepository.deleteById(id);
    }
}
