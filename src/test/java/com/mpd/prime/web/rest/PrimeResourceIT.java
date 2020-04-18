package com.mpd.prime.web.rest;

import com.mpd.prime.PrimeserviceApp;
import com.mpd.prime.domain.Prime;
import com.mpd.prime.repository.PrimeRepository;
import com.mpd.prime.service.PrimeService;
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

import com.mpd.prime.domain.enumeration.Typeprime;
/**
 * Integration tests for the {@link PrimeResource} REST controller.
 */
@SpringBootTest(classes = PrimeserviceApp.class)
public class PrimeResourceIT {

    private static final String DEFAULT_PRIME = "AAAAAAAAAA";
    private static final String UPDATED_PRIME = "BBBBBBBBBB";

    private static final Double DEFAULT_TAUX_MENSUEL = 1D;
    private static final Double UPDATED_TAUX_MENSUEL = 2D;

    private static final Typeprime DEFAULT_TYPE_PRIME = Typeprime.MENSUELLE;
    private static final Typeprime UPDATED_TYPE_PRIME = Typeprime.TRIMESTRIELLE;

    @Autowired
    private PrimeRepository primeRepository;

    @Autowired
    private PrimeService primeService;

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

    private MockMvc restPrimeMockMvc;

    private Prime prime;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrimeResource primeResource = new PrimeResource(primeService);
        this.restPrimeMockMvc = MockMvcBuilders.standaloneSetup(primeResource)
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
    public static Prime createEntity(EntityManager em) {
        Prime prime = new Prime()
            .prime(DEFAULT_PRIME)
            .tauxMensuel(DEFAULT_TAUX_MENSUEL)
            .typePrime(DEFAULT_TYPE_PRIME);
        return prime;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prime createUpdatedEntity(EntityManager em) {
        Prime prime = new Prime()
            .prime(UPDATED_PRIME)
            .tauxMensuel(UPDATED_TAUX_MENSUEL)
            .typePrime(UPDATED_TYPE_PRIME);
        return prime;
    }

    @BeforeEach
    public void initTest() {
        prime = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrime() throws Exception {
        int databaseSizeBeforeCreate = primeRepository.findAll().size();

        // Create the Prime
        restPrimeMockMvc.perform(post("/api/primes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prime)))
            .andExpect(status().isCreated());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeCreate + 1);
        Prime testPrime = primeList.get(primeList.size() - 1);
        assertThat(testPrime.getPrime()).isEqualTo(DEFAULT_PRIME);
        assertThat(testPrime.getTauxMensuel()).isEqualTo(DEFAULT_TAUX_MENSUEL);
        assertThat(testPrime.getTypePrime()).isEqualTo(DEFAULT_TYPE_PRIME);
    }

    @Test
    @Transactional
    public void createPrimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = primeRepository.findAll().size();

        // Create the Prime with an existing ID
        prime.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrimeMockMvc.perform(post("/api/primes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prime)))
            .andExpect(status().isBadRequest());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPrimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = primeRepository.findAll().size();
        // set the field null
        prime.setPrime(null);

        // Create the Prime, which fails.

        restPrimeMockMvc.perform(post("/api/primes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prime)))
            .andExpect(status().isBadRequest());

        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrimes() throws Exception {
        // Initialize the database
        primeRepository.saveAndFlush(prime);

        // Get all the primeList
        restPrimeMockMvc.perform(get("/api/primes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prime.getId().intValue())))
            .andExpect(jsonPath("$.[*].prime").value(hasItem(DEFAULT_PRIME)))
            .andExpect(jsonPath("$.[*].tauxMensuel").value(hasItem(DEFAULT_TAUX_MENSUEL.doubleValue())))
            .andExpect(jsonPath("$.[*].typePrime").value(hasItem(DEFAULT_TYPE_PRIME.toString())));
    }
    
    @Test
    @Transactional
    public void getPrime() throws Exception {
        // Initialize the database
        primeRepository.saveAndFlush(prime);

        // Get the prime
        restPrimeMockMvc.perform(get("/api/primes/{id}", prime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prime.getId().intValue()))
            .andExpect(jsonPath("$.prime").value(DEFAULT_PRIME))
            .andExpect(jsonPath("$.tauxMensuel").value(DEFAULT_TAUX_MENSUEL.doubleValue()))
            .andExpect(jsonPath("$.typePrime").value(DEFAULT_TYPE_PRIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrime() throws Exception {
        // Get the prime
        restPrimeMockMvc.perform(get("/api/primes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrime() throws Exception {
        // Initialize the database
        primeService.save(prime);

        int databaseSizeBeforeUpdate = primeRepository.findAll().size();

        // Update the prime
        Prime updatedPrime = primeRepository.findById(prime.getId()).get();
        // Disconnect from session so that the updates on updatedPrime are not directly saved in db
        em.detach(updatedPrime);
        updatedPrime
            .prime(UPDATED_PRIME)
            .tauxMensuel(UPDATED_TAUX_MENSUEL)
            .typePrime(UPDATED_TYPE_PRIME);

        restPrimeMockMvc.perform(put("/api/primes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrime)))
            .andExpect(status().isOk());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate);
        Prime testPrime = primeList.get(primeList.size() - 1);
        assertThat(testPrime.getPrime()).isEqualTo(UPDATED_PRIME);
        assertThat(testPrime.getTauxMensuel()).isEqualTo(UPDATED_TAUX_MENSUEL);
        assertThat(testPrime.getTypePrime()).isEqualTo(UPDATED_TYPE_PRIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPrime() throws Exception {
        int databaseSizeBeforeUpdate = primeRepository.findAll().size();

        // Create the Prime

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrimeMockMvc.perform(put("/api/primes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prime)))
            .andExpect(status().isBadRequest());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrime() throws Exception {
        // Initialize the database
        primeService.save(prime);

        int databaseSizeBeforeDelete = primeRepository.findAll().size();

        // Delete the prime
        restPrimeMockMvc.perform(delete("/api/primes/{id}", prime.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
