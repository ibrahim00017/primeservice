package com.mpd.prime.web.rest;

import com.mpd.prime.PrimeserviceApp;
import com.mpd.prime.domain.Allouer;
import com.mpd.prime.repository.AllouerRepository;
import com.mpd.prime.service.AllouerService;
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
import java.util.List;

import static com.mpd.prime.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AllouerResource} REST controller.
 */
@SpringBootTest(classes = PrimeserviceApp.class)
public class AllouerResourceIT {

    private static final Double DEFAULT_NOTE = 0D;
    private static final Double UPDATED_NOTE = 1D;

    private static final Integer DEFAULT_NOMBRE_JOURS = 1;
    private static final Integer UPDATED_NOMBRE_JOURS = 2;

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    @Autowired
    private AllouerRepository allouerRepository;

    @Autowired
    private AllouerService allouerService;

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

    private MockMvc restAllouerMockMvc;

    private Allouer allouer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AllouerResource allouerResource = new AllouerResource(allouerService);
        this.restAllouerMockMvc = MockMvcBuilders.standaloneSetup(allouerResource)
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
    public static Allouer createEntity(EntityManager em) {
        Allouer allouer = new Allouer()
            .note(DEFAULT_NOTE)
            .nombreJours(DEFAULT_NOMBRE_JOURS)
            .montant(DEFAULT_MONTANT);
        return allouer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Allouer createUpdatedEntity(EntityManager em) {
        Allouer allouer = new Allouer()
            .note(UPDATED_NOTE)
            .nombreJours(UPDATED_NOMBRE_JOURS)
            .montant(UPDATED_MONTANT);
        return allouer;
    }

    @BeforeEach
    public void initTest() {
        allouer = createEntity(em);
    }

    @Test
    @Transactional
    public void createAllouer() throws Exception {
        int databaseSizeBeforeCreate = allouerRepository.findAll().size();

        // Create the Allouer
        restAllouerMockMvc.perform(post("/api/allouers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(allouer)))
            .andExpect(status().isCreated());

        // Validate the Allouer in the database
        List<Allouer> allouerList = allouerRepository.findAll();
        assertThat(allouerList).hasSize(databaseSizeBeforeCreate + 1);
        Allouer testAllouer = allouerList.get(allouerList.size() - 1);
        assertThat(testAllouer.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testAllouer.getNombreJours()).isEqualTo(DEFAULT_NOMBRE_JOURS);
        assertThat(testAllouer.getMontant()).isEqualTo(DEFAULT_MONTANT);
    }

    @Test
    @Transactional
    public void createAllouerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = allouerRepository.findAll().size();

        // Create the Allouer with an existing ID
        allouer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAllouerMockMvc.perform(post("/api/allouers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(allouer)))
            .andExpect(status().isBadRequest());

        // Validate the Allouer in the database
        List<Allouer> allouerList = allouerRepository.findAll();
        assertThat(allouerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAllouers() throws Exception {
        // Initialize the database
        allouerRepository.saveAndFlush(allouer);

        // Get all the allouerList
        restAllouerMockMvc.perform(get("/api/allouers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(allouer.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.doubleValue())))
            .andExpect(jsonPath("$.[*].nombreJours").value(hasItem(DEFAULT_NOMBRE_JOURS)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAllouer() throws Exception {
        // Initialize the database
        allouerRepository.saveAndFlush(allouer);

        // Get the allouer
        restAllouerMockMvc.perform(get("/api/allouers/{id}", allouer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(allouer.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.doubleValue()))
            .andExpect(jsonPath("$.nombreJours").value(DEFAULT_NOMBRE_JOURS))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAllouer() throws Exception {
        // Get the allouer
        restAllouerMockMvc.perform(get("/api/allouers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAllouer() throws Exception {
        // Initialize the database
        allouerService.save(allouer);

        int databaseSizeBeforeUpdate = allouerRepository.findAll().size();

        // Update the allouer
        Allouer updatedAllouer = allouerRepository.findById(allouer.getId()).get();
        // Disconnect from session so that the updates on updatedAllouer are not directly saved in db
        em.detach(updatedAllouer);
        updatedAllouer
            .note(UPDATED_NOTE)
            .nombreJours(UPDATED_NOMBRE_JOURS)
            .montant(UPDATED_MONTANT);

        restAllouerMockMvc.perform(put("/api/allouers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAllouer)))
            .andExpect(status().isOk());

        // Validate the Allouer in the database
        List<Allouer> allouerList = allouerRepository.findAll();
        assertThat(allouerList).hasSize(databaseSizeBeforeUpdate);
        Allouer testAllouer = allouerList.get(allouerList.size() - 1);
        assertThat(testAllouer.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testAllouer.getNombreJours()).isEqualTo(UPDATED_NOMBRE_JOURS);
        assertThat(testAllouer.getMontant()).isEqualTo(UPDATED_MONTANT);
    }

    @Test
    @Transactional
    public void updateNonExistingAllouer() throws Exception {
        int databaseSizeBeforeUpdate = allouerRepository.findAll().size();

        // Create the Allouer

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAllouerMockMvc.perform(put("/api/allouers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(allouer)))
            .andExpect(status().isBadRequest());

        // Validate the Allouer in the database
        List<Allouer> allouerList = allouerRepository.findAll();
        assertThat(allouerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAllouer() throws Exception {
        // Initialize the database
        allouerService.save(allouer);

        int databaseSizeBeforeDelete = allouerRepository.findAll().size();

        // Delete the allouer
        restAllouerMockMvc.perform(delete("/api/allouers/{id}", allouer.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Allouer> allouerList = allouerRepository.findAll();
        assertThat(allouerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
