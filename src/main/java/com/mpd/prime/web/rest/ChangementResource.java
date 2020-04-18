package com.mpd.prime.web.rest;

import com.mpd.prime.domain.Changement;
import com.mpd.prime.service.ChangementService;
import com.mpd.prime.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mpd.prime.domain.Changement}.
 */
@RestController
@RequestMapping("/api")
public class ChangementResource {

    private final Logger log = LoggerFactory.getLogger(ChangementResource.class);

    private static final String ENTITY_NAME = "primeserviceChangement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChangementService changementService;

    public ChangementResource(ChangementService changementService) {
        this.changementService = changementService;
    }

    /**
     * {@code POST  /changements} : Create a new changement.
     *
     * @param changement the changement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new changement, or with status {@code 400 (Bad Request)} if the changement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/changements")
    public ResponseEntity<Changement> createChangement(@RequestBody Changement changement) throws URISyntaxException {
        log.debug("REST request to save Changement : {}", changement);
        if (changement.getId() != null) {
            throw new BadRequestAlertException("A new changement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Changement result = changementService.save(changement);
        return ResponseEntity.created(new URI("/api/changements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /changements} : Updates an existing changement.
     *
     * @param changement the changement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated changement,
     * or with status {@code 400 (Bad Request)} if the changement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the changement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/changements")
    public ResponseEntity<Changement> updateChangement(@RequestBody Changement changement) throws URISyntaxException {
        log.debug("REST request to update Changement : {}", changement);
        if (changement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Changement result = changementService.save(changement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, changement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /changements} : get all the changements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of changements in body.
     */
    @GetMapping("/changements")
    public List<Changement> getAllChangements() {
        log.debug("REST request to get all Changements");
        return changementService.findAll();
    }

    /**
     * {@code GET  /changements/:id} : get the "id" changement.
     *
     * @param id the id of the changement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the changement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/changements/{id}")
    public ResponseEntity<Changement> getChangement(@PathVariable Long id) {
        log.debug("REST request to get Changement : {}", id);
        Optional<Changement> changement = changementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(changement);
    }

    /**
     * {@code DELETE  /changements/:id} : delete the "id" changement.
     *
     * @param id the id of the changement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/changements/{id}")
    public ResponseEntity<Void> deleteChangement(@PathVariable Long id) {
        log.debug("REST request to delete Changement : {}", id);
        changementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
