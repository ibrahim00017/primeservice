package com.mpd.prime.service.impl;

import com.mpd.prime.service.PrimeService;
import com.mpd.prime.domain.Prime;
import com.mpd.prime.repository.PrimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Prime}.
 */
@Service
@Transactional
public class PrimeServiceImpl implements PrimeService {

    private final Logger log = LoggerFactory.getLogger(PrimeServiceImpl.class);

    private final PrimeRepository primeRepository;

    public PrimeServiceImpl(PrimeRepository primeRepository) {
        this.primeRepository = primeRepository;
    }

    /**
     * Save a prime.
     *
     * @param prime the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Prime save(Prime prime) {
        log.debug("Request to save Prime : {}", prime);
        return primeRepository.save(prime);
    }

    /**
     * Get all the primes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Prime> findAll() {
        log.debug("Request to get all Primes");
        return primeRepository.findAll();
    }

    /**
     * Get one prime by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Prime> findOne(Long id) {
        log.debug("Request to get Prime : {}", id);
        return primeRepository.findById(id);
    }

    /**
     * Delete the prime by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prime : {}", id);
        primeRepository.deleteById(id);
    }
}
