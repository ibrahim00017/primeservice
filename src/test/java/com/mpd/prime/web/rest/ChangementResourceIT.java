package com.mpd.prime.web.rest;

import com.mpd.prime.PrimeserviceApp;
import com.mpd.prime.domain.Changement;
import com.mpd.prime.repository.ChangementRepository;
import com.mpd.prime.service.ChangementService;
import com.mpd.prime.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mpd.prime.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ChangementResource} REST controller.
 */
@SpringBootTest(classes = PrimeserviceApp.class)
public class ChangementResourceIT {

    private static final Instant DEFAULT_DATE_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATEFIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATEFIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ChangementRepository changementRepository;

    @Autowired
    private ChangementService changementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restChangementMockMvc;

    private Changement changement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChangementResource changementResource = new ChangementResource(changementService);
        this.restChangementMockMvc = MockMvcBuilders.standaloneSetup(changementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Changement createEntity(EntityManager em) {
        Changement changement = new Changement()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .datefin(DEFAULT_DATEFIN);
        return changement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Changement createUpdatedEntity(EntityManager em) {
        Changement changement = new Changement()
            .dateDebut(UPDATED_DATE_DEBUT)
            .datefin(UPDATED_DATEFIN);
        return changement;
    }

    @BeforeEach
    public void initTest() {
        changement = createEntity(em);
    }

    @Test
    @Transactional
    public void createChangement() throws Exception {
        int databaseSizeBeforeCreate = changementRepository.findAll().size();

        // Create the Changement
        restChangementMockMvc.perform(post("/api/changements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(changement)))
            .andExpect(status().isCreated());

        // Validate the Changement in the database
        List<Changement> changementList = changementRepository.findAll();
        assertThat(changementList).hasSize(databaseSizeBeforeCreate + 1);
        Changement testChangement = changementList.get(changementList.size() - 1);
        assertThat(testChangement.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testChangement.getDatefin()).isEqualTo(DEFAULT_DATEFIN);
    }

    @Test
    @Transactional
    public void createChangementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = changementRepository.findAll().size();

        // Create the Changement with an existing ID
        changement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChangementMockMvc.perform(post("/api/changements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(changement)))
            .andExpect(status().isBadRequest());

        // Validate the Changement in the database
        List<Changement> changementList = changementRepository.findAll();
        assertThat(changementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllChangements() throws Exception {
        // Initialize the database
        changementRepository.saveAndFlush(changement);

        // Get all the changementList
        restChangementMockMvc.perform(get("/api/changements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(changement.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].datefin").value(hasItem(DEFAULT_DATEFIN.toString())));
    }
    
    @Test
    @Transactional
    public void getChangement() throws Exception {
        // Initialize the database
        changementRepository.saveAndFlush(changement);

        // Get the changement
        restChangementMockMvc.perform(get("/api/changements/{id}", changement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(changement.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.datefin").value(DEFAULT_DATEFIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChangement() throws Exception {
        // Get the changement
        restChangementMockMvc.perform(get("/api/changements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChangement() throws Exception {
        // Initialize the database
        changementService.save(changement);

        int databaseSizeBeforeUpdate = changementRepository.findAll().size();

        // Update the changement
        Changement updatedChangement = changementRepository.findById(changement.getId()).get();
        // Disconnect from session so that the updates on updatedChangement are not directly saved in db
        em.detach(updatedChangement);
        updatedChangement
            .dateDebut(UPDATED_DATE_DEBUT)
            .datefin(UPDATED_DATEFIN);

        restChangementMockMvc.perform(put("/api/changements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedChangement)))
            .andExpect(status().isOk());

        // Validate the Changement in the database
        List<Changement> changementList = changementRepository.findAll();
        assertThat(changementList).hasSize(databaseSizeBeforeUpdate);
        Changement testChangement = changementList.get(changementList.size() - 1);
        assertThat(testChangement.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testChangement.getDatefin()).isEqualTo(UPDATED_DATEFIN);
    }

    @Test
    @Transactional
    public void updateNonExistingChangement() throws Exception {
        int databaseSizeBeforeUpdate = changementRepository.findAll().size();

        // Create the Changement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChangementMockMvc.perform(put("/api/changements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(changement)))
            .andExpect(status().isBadRequest());

        // Validate the Changement in the database
        List<Changement> changementList = changementRepository.findAll();
        assertThat(changementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChangement() throws Exception {
        // Initialize the database
        changementService.save(changement);

        int databaseSizeBeforeDelete = changementRepository.findAll().size();

        // Delete the changement
        restChangementMockMvc.perform(delete("/api/changements/{id}", changement.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Changement> changementList = changementRepository.findAll();
        assertThat(changementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
