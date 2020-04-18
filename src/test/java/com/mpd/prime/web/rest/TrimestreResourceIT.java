package com.mpd.prime.web.rest;

import com.mpd.prime.PrimeserviceApp;
import com.mpd.prime.domain.Trimestre;
import com.mpd.prime.repository.TrimestreRepository;
import com.mpd.prime.service.TrimestreService;
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
 * Integration tests for the {@link TrimestreResource} REST controller.
 */
@SpringBootTest(classes = PrimeserviceApp.class)
public class TrimestreResourceIT {

    private static final Integer DEFAULT_CODE_TRIMESTRE = 1;
    private static final Integer UPDATED_CODE_TRIMESTRE = 2;

    @Autowired
    private TrimestreRepository trimestreRepository;

    @Autowired
    private TrimestreService trimestreService;

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

    private MockMvc restTrimestreMockMvc;

    private Trimestre trimestre;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrimestreResource trimestreResource = new TrimestreResource(trimestreService);
        this.restTrimestreMockMvc = MockMvcBuilders.standaloneSetup(trimestreResource)
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
    public static Trimestre createEntity(EntityManager em) {
        Trimestre trimestre = new Trimestre()
            .codeTrimestre(DEFAULT_CODE_TRIMESTRE);
        return trimestre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trimestre createUpdatedEntity(EntityManager em) {
        Trimestre trimestre = new Trimestre()
            .codeTrimestre(UPDATED_CODE_TRIMESTRE);
        return trimestre;
    }

    @BeforeEach
    public void initTest() {
        trimestre = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrimestre() throws Exception {
        int databaseSizeBeforeCreate = trimestreRepository.findAll().size();

        // Create the Trimestre
        restTrimestreMockMvc.perform(post("/api/trimestres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trimestre)))
            .andExpect(status().isCreated());

        // Validate the Trimestre in the database
        List<Trimestre> trimestreList = trimestreRepository.findAll();
        assertThat(trimestreList).hasSize(databaseSizeBeforeCreate + 1);
        Trimestre testTrimestre = trimestreList.get(trimestreList.size() - 1);
        assertThat(testTrimestre.getCodeTrimestre()).isEqualTo(DEFAULT_CODE_TRIMESTRE);
    }

    @Test
    @Transactional
    public void createTrimestreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trimestreRepository.findAll().size();

        // Create the Trimestre with an existing ID
        trimestre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrimestreMockMvc.perform(post("/api/trimestres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trimestre)))
            .andExpect(status().isBadRequest());

        // Validate the Trimestre in the database
        List<Trimestre> trimestreList = trimestreRepository.findAll();
        assertThat(trimestreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeTrimestreIsRequired() throws Exception {
        int databaseSizeBeforeTest = trimestreRepository.findAll().size();
        // set the field null
        trimestre.setCodeTrimestre(null);

        // Create the Trimestre, which fails.

        restTrimestreMockMvc.perform(post("/api/trimestres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trimestre)))
            .andExpect(status().isBadRequest());

        List<Trimestre> trimestreList = trimestreRepository.findAll();
        assertThat(trimestreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrimestres() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get all the trimestreList
        restTrimestreMockMvc.perform(get("/api/trimestres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trimestre.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTrimestre").value(hasItem(DEFAULT_CODE_TRIMESTRE)));
    }
    
    @Test
    @Transactional
    public void getTrimestre() throws Exception {
        // Initialize the database
        trimestreRepository.saveAndFlush(trimestre);

        // Get the trimestre
        restTrimestreMockMvc.perform(get("/api/trimestres/{id}", trimestre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trimestre.getId().intValue()))
            .andExpect(jsonPath("$.codeTrimestre").value(DEFAULT_CODE_TRIMESTRE));
    }

    @Test
    @Transactional
    public void getNonExistingTrimestre() throws Exception {
        // Get the trimestre
        restTrimestreMockMvc.perform(get("/api/trimestres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrimestre() throws Exception {
        // Initialize the database
        trimestreService.save(trimestre);

        int databaseSizeBeforeUpdate = trimestreRepository.findAll().size();

        // Update the trimestre
        Trimestre updatedTrimestre = trimestreRepository.findById(trimestre.getId()).get();
        // Disconnect from session so that the updates on updatedTrimestre are not directly saved in db
        em.detach(updatedTrimestre);
        updatedTrimestre
            .codeTrimestre(UPDATED_CODE_TRIMESTRE);

        restTrimestreMockMvc.perform(put("/api/trimestres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrimestre)))
            .andExpect(status().isOk());

        // Validate the Trimestre in the database
        List<Trimestre> trimestreList = trimestreRepository.findAll();
        assertThat(trimestreList).hasSize(databaseSizeBeforeUpdate);
        Trimestre testTrimestre = trimestreList.get(trimestreList.size() - 1);
        assertThat(testTrimestre.getCodeTrimestre()).isEqualTo(UPDATED_CODE_TRIMESTRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTrimestre() throws Exception {
        int databaseSizeBeforeUpdate = trimestreRepository.findAll().size();

        // Create the Trimestre

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrimestreMockMvc.perform(put("/api/trimestres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trimestre)))
            .andExpect(status().isBadRequest());

        // Validate the Trimestre in the database
        List<Trimestre> trimestreList = trimestreRepository.findAll();
        assertThat(trimestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTrimestre() throws Exception {
        // Initialize the database
        trimestreService.save(trimestre);

        int databaseSizeBeforeDelete = trimestreRepository.findAll().size();

        // Delete the trimestre
        restTrimestreMockMvc.perform(delete("/api/trimestres/{id}", trimestre.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trimestre> trimestreList = trimestreRepository.findAll();
        assertThat(trimestreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
