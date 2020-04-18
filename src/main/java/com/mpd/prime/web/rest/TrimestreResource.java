package com.mpd.prime.web.rest;

import com.mpd.prime.domain.Trimestre;
import com.mpd.prime.service.TrimestreService;
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
 * REST controller for managing {@link com.mpd.prime.domain.Trimestre}.
 */
@RestController
@RequestMapping("/api")
public class TrimestreResource {

    private final Logger log = LoggerFactory.getLogger(TrimestreResource.class);

    private static final String ENTITY_NAME = "primeserviceTrimestre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrimestreService trimestreService;

    public TrimestreResource(TrimestreService trimestreService) {
        this.trimestreService = trimestreService;
    }

    /**
     * {@code POST  /trimestres} : Create a new trimestre.
     *
     * @param trimestre the trimestre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trimestre, or with status {@code 400 (Bad Request)} if the trimestre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trimestres")
    public ResponseEntity<Trimestre> createTrimestre(@Valid @RequestBody Trimestre trimestre) throws URISyntaxException {
        log.debug("REST request to save Trimestre : {}", trimestre);
        if (trimestre.getId() != null) {
            throw new BadRequestAlertException("A new trimestre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Trimestre result = trimestreService.save(trimestre);
        return ResponseEntity.created(new URI("/api/trimestres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trimestres} : Updates an existing trimestre.
     *
     * @param trimestre the trimestre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trimestre,
     * or with status {@code 400 (Bad Request)} if the trimestre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trimestre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trimestres")
    public ResponseEntity<Trimestre> updateTrimestre(@Valid @RequestBody Trimestre trimestre) throws URISyntaxException {
        log.debug("REST request to update Trimestre : {}", trimestre);
        if (trimestre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Trimestre result = trimestreService.save(trimestre);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trimestre.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trimestres} : get all the trimestres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trimestres in body.
     */
    @GetMapping("/trimestres")
    public List<Trimestre> getAllTrimestres() {
        log.debug("REST request to get all Trimestres");
        return trimestreService.findAll();
    }

    /**
     * {@code GET  /trimestres/:id} : get the "id" trimestre.
     *
     * @param id the id of the trimestre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trimestre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trimestres/{id}")
    public ResponseEntity<Trimestre> getTrimestre(@PathVariable Long id) {
        log.debug("REST request to get Trimestre : {}", id);
        Optional<Trimestre> trimestre = trimestreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trimestre);
    }

    /**
     * {@code DELETE  /trimestres/:id} : delete the "id" trimestre.
     *
     * @param id the id of the trimestre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trimestres/{id}")
    public ResponseEntity<Void> deleteTrimestre(@PathVariable Long id) {
        log.debug("REST request to delete Trimestre : {}", id);
        trimestreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
