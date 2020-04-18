package com.mpd.prime.web.rest;

import com.mpd.prime.domain.Prime;
import com.mpd.prime.service.PrimeService;
import com.mpd.prime.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mpd.prime.domain.Prime}.
 */
@RestController
@RequestMapping("/api")
public class PrimeResource {

    private final Logger log = LoggerFactory.getLogger(PrimeResource.class);

    private static final String ENTITY_NAME = "primeservicePrime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrimeService primeService;

    public PrimeResource(PrimeService primeService) {
        this.primeService = primeService;
    }

    /**
     * {@code POST  /primes} : Create a new prime.
     *
     * @param prime the prime to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prime, or with status {@code 400 (Bad Request)} if the prime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/primes")
    public ResponseEntity<Prime> createPrime(@Valid @RequestBody Prime prime) throws URISyntaxException {
        log.debug("REST request to save Prime : {}", prime);
        if (prime.getId() != null) {
            throw new BadRequestAlertException("A new prime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prime result = primeService.save(prime);
        return ResponseEntity.created(new URI("/api/primes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /primes} : Updates an existing prime.
     *
     * @param prime the prime to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prime,
     * or with status {@code 400 (Bad Request)} if the prime is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prime couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/primes")
    public ResponseEntity<Prime> updatePrime(@Valid @RequestBody Prime prime) throws URISyntaxException {
        log.debug("REST request to update Prime : {}", prime);
        if (prime.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prime result = primeService.save(prime);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prime.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /primes} : get all the primes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of primes in body.
     */
    @GetMapping("/primes")
    public List<Prime> getAllPrimes() {
        log.debug("REST request to get all Primes");
        return primeService.findAll();
    }

    /**
     * {@code GET  /primes/:id} : get the "id" prime.
     *
     * @param id the id of the prime to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prime, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/primes/{id}")
    public ResponseEntity<Prime> getPrime(@PathVariable Long id) {
        log.debug("REST request to get Prime : {}", id);
        Optional<Prime> prime = primeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prime);
    }

    /**
     * {@code DELETE  /primes/:id} : delete the "id" prime.
     *
     * @param id the id of the prime to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/primes/{id}")
    public ResponseEntity<Void> deletePrime(@PathVariable Long id) {
        log.debug("REST request to delete Prime : {}", id);
        primeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
