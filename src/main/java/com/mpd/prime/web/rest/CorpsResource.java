package com.mpd.prime.web.rest;

import com.mpd.prime.domain.Corps;
import com.mpd.prime.service.CorpsService;
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
 * REST controller for managing {@link com.mpd.prime.domain.Corps}.
 */
@RestController
@RequestMapping("/api")
public class CorpsResource {

    private final Logger log = LoggerFactory.getLogger(CorpsResource.class);

    private static final String ENTITY_NAME = "primeserviceCorps";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorpsService corpsService;

    public CorpsResource(CorpsService corpsService) {
        this.corpsService = corpsService;
    }

    /**
     * {@code POST  /corps} : Create a new corps.
     *
     * @param corps the corps to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new corps, or with status {@code 400 (Bad Request)} if the corps has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/corps")
    public ResponseEntity<Corps> createCorps(@Valid @RequestBody Corps corps) throws URISyntaxException {
        log.debug("REST request to save Corps : {}", corps);
        if (corps.getId() != null) {
            throw new BadRequestAlertException("A new corps cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Corps result = corpsService.save(corps);
        return ResponseEntity.created(new URI("/api/corps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /corps} : Updates an existing corps.
     *
     * @param corps the corps to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated corps,
     * or with status {@code 400 (Bad Request)} if the corps is not valid,
     * or with status {@code 500 (Internal Server Error)} if the corps couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/corps")
    public ResponseEntity<Corps> updateCorps(@Valid @RequestBody Corps corps) throws URISyntaxException {
        log.debug("REST request to update Corps : {}", corps);
        if (corps.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Corps result = corpsService.save(corps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, corps.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /corps} : get all the corps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of corps in body.
     */
    @GetMapping("/corps")
    public List<Corps> getAllCorps() {
        log.debug("REST request to get all Corps");
        return corpsService.findAll();
    }

    /**
     * {@code GET  /corps/:id} : get the "id" corps.
     *
     * @param id the id of the corps to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the corps, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/corps/{id}")
    public ResponseEntity<Corps> getCorps(@PathVariable Long id) {
        log.debug("REST request to get Corps : {}", id);
        Optional<Corps> corps = corpsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(corps);
    }

    /**
     * {@code DELETE  /corps/:id} : delete the "id" corps.
     *
     * @param id the id of the corps to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/corps/{id}")
    public ResponseEntity<Void> deleteCorps(@PathVariable Long id) {
        log.debug("REST request to delete Corps : {}", id);
        corpsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
