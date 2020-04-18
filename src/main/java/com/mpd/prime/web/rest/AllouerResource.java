package com.mpd.prime.web.rest;

import com.mpd.prime.domain.Allouer;
import com.mpd.prime.service.AllouerService;
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
 * REST controller for managing {@link com.mpd.prime.domain.Allouer}.
 */
@RestController
@RequestMapping("/api")
public class AllouerResource {

    private final Logger log = LoggerFactory.getLogger(AllouerResource.class);

    private static final String ENTITY_NAME = "primeserviceAllouer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllouerService allouerService;

    public AllouerResource(AllouerService allouerService) {
        this.allouerService = allouerService;
    }

    /**
     * {@code POST  /allouers} : Create a new allouer.
     *
     * @param allouer the allouer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allouer, or with status {@code 400 (Bad Request)} if the allouer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allouers")
    public ResponseEntity<Allouer> createAllouer(@Valid @RequestBody Allouer allouer) throws URISyntaxException {
        log.debug("REST request to save Allouer : {}", allouer);
        if (allouer.getId() != null) {
            throw new BadRequestAlertException("A new allouer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Allouer result = allouerService.save(allouer);
        return ResponseEntity.created(new URI("/api/allouers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allouers} : Updates an existing allouer.
     *
     * @param allouer the allouer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allouer,
     * or with status {@code 400 (Bad Request)} if the allouer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allouer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allouers")
    public ResponseEntity<Allouer> updateAllouer(@Valid @RequestBody Allouer allouer) throws URISyntaxException {
        log.debug("REST request to update Allouer : {}", allouer);
        if (allouer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Allouer result = allouerService.save(allouer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, allouer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /allouers} : get all the allouers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allouers in body.
     */
    @GetMapping("/allouers")
    public List<Allouer> getAllAllouers() {
        log.debug("REST request to get all Allouers");
        return allouerService.findAll();
    }

    /**
     * {@code GET  /allouers/:id} : get the "id" allouer.
     *
     * @param id the id of the allouer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allouer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allouers/{id}")
    public ResponseEntity<Allouer> getAllouer(@PathVariable Long id) {
        log.debug("REST request to get Allouer : {}", id);
        Optional<Allouer> allouer = allouerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(allouer);
    }

    /**
     * {@code DELETE  /allouers/:id} : delete the "id" allouer.
     *
     * @param id the id of the allouer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allouers/{id}")
    public ResponseEntity<Void> deleteAllouer(@PathVariable Long id) {
        log.debug("REST request to delete Allouer : {}", id);
        allouerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
