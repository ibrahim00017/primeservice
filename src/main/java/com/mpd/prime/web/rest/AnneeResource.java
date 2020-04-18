package com.mpd.prime.web.rest;

import com.mpd.prime.domain.Annee;
import com.mpd.prime.service.AnneeService;
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
 * REST controller for managing {@link com.mpd.prime.domain.Annee}.
 */
@RestController
@RequestMapping("/api")
public class AnneeResource {

    private final Logger log = LoggerFactory.getLogger(AnneeResource.class);

    private static final String ENTITY_NAME = "primeserviceAnnee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnneeService anneeService;

    public AnneeResource(AnneeService anneeService) {
        this.anneeService = anneeService;
    }

    /**
     * {@code POST  /annees} : Create a new annee.
     *
     * @param annee the annee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new annee, or with status {@code 400 (Bad Request)} if the annee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/annees")
    public ResponseEntity<Annee> createAnnee(@Valid @RequestBody Annee annee) throws URISyntaxException {
        log.debug("REST request to save Annee : {}", annee);
        if (annee.getId() != null) {
            throw new BadRequestAlertException("A new annee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Annee result = anneeService.save(annee);
        return ResponseEntity.created(new URI("/api/annees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /annees} : Updates an existing annee.
     *
     * @param annee the annee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annee,
     * or with status {@code 400 (Bad Request)} if the annee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the annee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/annees")
    public ResponseEntity<Annee> updateAnnee(@Valid @RequestBody Annee annee) throws URISyntaxException {
        log.debug("REST request to update Annee : {}", annee);
        if (annee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Annee result = anneeService.save(annee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, annee.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /annees} : get all the annees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of annees in body.
     */
    @GetMapping("/annees")
    public List<Annee> getAllAnnees() {
        log.debug("REST request to get all Annees");
        return anneeService.findAll();
    }

    /**
     * {@code GET  /annees/:id} : get the "id" annee.
     *
     * @param id the id of the annee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the annee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/annees/{id}")
    public ResponseEntity<Annee> getAnnee(@PathVariable Long id) {
        log.debug("REST request to get Annee : {}", id);
        Optional<Annee> annee = anneeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(annee);
    }

    /**
     * {@code DELETE  /annees/:id} : delete the "id" annee.
     *
     * @param id the id of the annee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/annees/{id}")
    public ResponseEntity<Void> deleteAnnee(@PathVariable Long id) {
        log.debug("REST request to delete Annee : {}", id);
        anneeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
