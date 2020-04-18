package com.mpd.prime.web.rest;

import com.mpd.prime.PrimeserviceApp;
import com.mpd.prime.domain.Corps;
import com.mpd.prime.repository.CorpsRepository;
import com.mpd.prime.service.CorpsService;
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
 * Integration tests for the {@link CorpsResource} REST controller.
 */
@SpringBootTest(classes = PrimeserviceApp.class)
public class CorpsResourceIT {

    private static final String DEFAULT_LIBELLE_CORPS = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CORPS = "BBBBBBBBBB";

    @Autowired
    private CorpsRepository corpsRepository;

    @Autowired
    private CorpsService corpsService;

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

    private MockMvc restCorpsMockMvc;

    private Corps corps;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorpsResource corpsResource = new CorpsResource(corpsService);
        this.restCorpsMockMvc = MockMvcBuilders.standaloneSetup(corpsResource)
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
    public static Corps createEntity(EntityManager em) {
        Corps corps = new Corps()
            .libelleCorps(DEFAULT_LIBELLE_CORPS);
        return corps;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Corps createUpdatedEntity(EntityManager em) {
        Corps corps = new Corps()
            .libelleCorps(UPDATED_LIBELLE_CORPS);
        return corps;
    }

    @BeforeEach
    public void initTest() {
        corps = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorps() throws Exception {
        int databaseSizeBeforeCreate = corpsRepository.findAll().size();

        // Create the Corps
        restCorpsMockMvc.perform(post("/api/corps")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corps)))
            .andExpect(status().isCreated());

        // Validate the Corps in the database
        List<Corps> corpsList = corpsRepository.findAll();
        assertThat(corpsList).hasSize(databaseSizeBeforeCreate + 1);
        Corps testCorps = corpsList.get(corpsList.size() - 1);
        assertThat(testCorps.getLibelleCorps()).isEqualTo(DEFAULT_LIBELLE_CORPS);
    }

    @Test
    @Transactional
    public void createCorpsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corpsRepository.findAll().size();

        // Create the Corps with an existing ID
        corps.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorpsMockMvc.perform(post("/api/corps")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corps)))
            .andExpect(status().isBadRequest());

        // Validate the Corps in the database
        List<Corps> corpsList = corpsRepository.findAll();
        assertThat(corpsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleCorpsIsRequired() throws Exception {
        int databaseSizeBeforeTest = corpsRepository.findAll().size();
        // set the field null
        corps.setLibelleCorps(null);

        // Create the Corps, which fails.

        restCorpsMockMvc.perform(post("/api/corps")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corps)))
            .andExpect(status().isBadRequest());

        List<Corps> corpsList = corpsRepository.findAll();
        assertThat(corpsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorps() throws Exception {
        // Initialize the database
        corpsRepository.saveAndFlush(corps);

        // Get all the corpsList
        restCorpsMockMvc.perform(get("/api/corps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corps.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleCorps").value(hasItem(DEFAULT_LIBELLE_CORPS)));
    }
    
    @Test
    @Transactional
    public void getCorps() throws Exception {
        // Initialize the database
        corpsRepository.saveAndFlush(corps);

        // Get the corps
        restCorpsMockMvc.perform(get("/api/corps/{id}", corps.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(corps.getId().intValue()))
            .andExpect(jsonPath("$.libelleCorps").value(DEFAULT_LIBELLE_CORPS));
    }

    @Test
    @Transactional
    public void getNonExistingCorps() throws Exception {
        // Get the corps
        restCorpsMockMvc.perform(get("/api/corps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorps() throws Exception {
        // Initialize the database
        corpsService.save(corps);

        int databaseSizeBeforeUpdate = corpsRepository.findAll().size();

        // Update the corps
        Corps updatedCorps = corpsRepository.findById(corps.getId()).get();
        // Disconnect from session so that the updates on updatedCorps are not directly saved in db
        em.detach(updatedCorps);
        updatedCorps
            .libelleCorps(UPDATED_LIBELLE_CORPS);

        restCorpsMockMvc.perform(put("/api/corps")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCorps)))
            .andExpect(status().isOk());

        // Validate the Corps in the database
        List<Corps> corpsList = corpsRepository.findAll();
        assertThat(corpsList).hasSize(databaseSizeBeforeUpdate);
        Corps testCorps = corpsList.get(corpsList.size() - 1);
        assertThat(testCorps.getLibelleCorps()).isEqualTo(UPDATED_LIBELLE_CORPS);
    }

    @Test
    @Transactional
    public void updateNonExistingCorps() throws Exception {
        int databaseSizeBeforeUpdate = corpsRepository.findAll().size();

        // Create the Corps

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorpsMockMvc.perform(put("/api/corps")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corps)))
            .andExpect(status().isBadRequest());

        // Validate the Corps in the database
        List<Corps> corpsList = corpsRepository.findAll();
        assertThat(corpsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCorps() throws Exception {
        // Initialize the database
        corpsService.save(corps);

        int databaseSizeBeforeDelete = corpsRepository.findAll().size();

        // Delete the corps
        restCorpsMockMvc.perform(delete("/api/corps/{id}", corps.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Corps> corpsList = corpsRepository.findAll();
        assertThat(corpsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
