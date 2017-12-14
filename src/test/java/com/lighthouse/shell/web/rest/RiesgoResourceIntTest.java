package com.lighthouse.shell.web.rest;

import com.lighthouse.shell.ShellApp;

import com.lighthouse.shell.domain.Riesgo;
import com.lighthouse.shell.repository.RiesgoRepository;
import com.lighthouse.shell.service.RiesgoService;
import com.lighthouse.shell.service.dto.RiesgoDTO;
import com.lighthouse.shell.service.mapper.RiesgoMapper;
import com.lighthouse.shell.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RiesgoResource REST controller.
 *
 * @see RiesgoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShellApp.class)
public class RiesgoResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PROBABILIDAD = 1;
    private static final Integer UPDATED_PROBABILIDAD = 2;

    private static final String DEFAULT_IMPACTO = "AAAAAAAAAA";
    private static final String UPDATED_IMPACTO = "BBBBBBBBBB";

    @Autowired
    private RiesgoRepository riesgoRepository;

    @Autowired
    private RiesgoMapper riesgoMapper;

    @Autowired
    private RiesgoService riesgoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRiesgoMockMvc;

    private Riesgo riesgo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RiesgoResource riesgoResource = new RiesgoResource(riesgoService);
        this.restRiesgoMockMvc = MockMvcBuilders.standaloneSetup(riesgoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Riesgo createEntity(EntityManager em) {
        Riesgo riesgo = new Riesgo()
            .description(DEFAULT_DESCRIPTION)
            .probabilidad(DEFAULT_PROBABILIDAD)
            .impacto(DEFAULT_IMPACTO);
        return riesgo;
    }

    @Before
    public void initTest() {
        riesgo = createEntity(em);
    }

    @Test
    @Transactional
    public void createRiesgo() throws Exception {
        int databaseSizeBeforeCreate = riesgoRepository.findAll().size();

        // Create the Riesgo
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);
        restRiesgoMockMvc.perform(post("/api/riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riesgoDTO)))
            .andExpect(status().isCreated());

        // Validate the Riesgo in the database
        List<Riesgo> riesgoList = riesgoRepository.findAll();
        assertThat(riesgoList).hasSize(databaseSizeBeforeCreate + 1);
        Riesgo testRiesgo = riesgoList.get(riesgoList.size() - 1);
        assertThat(testRiesgo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRiesgo.getProbabilidad()).isEqualTo(DEFAULT_PROBABILIDAD);
        assertThat(testRiesgo.getImpacto()).isEqualTo(DEFAULT_IMPACTO);
    }

    @Test
    @Transactional
    public void createRiesgoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = riesgoRepository.findAll().size();

        // Create the Riesgo with an existing ID
        riesgo.setId(1L);
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiesgoMockMvc.perform(post("/api/riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riesgoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Riesgo> riesgoList = riesgoRepository.findAll();
        assertThat(riesgoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRiesgos() throws Exception {
        // Initialize the database
        riesgoRepository.saveAndFlush(riesgo);

        // Get all the riesgoList
        restRiesgoMockMvc.perform(get("/api/riesgos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(riesgo.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].probabilidad").value(hasItem(DEFAULT_PROBABILIDAD)))
            .andExpect(jsonPath("$.[*].impacto").value(hasItem(DEFAULT_IMPACTO.toString())));
    }

    @Test
    @Transactional
    public void getRiesgo() throws Exception {
        // Initialize the database
        riesgoRepository.saveAndFlush(riesgo);

        // Get the riesgo
        restRiesgoMockMvc.perform(get("/api/riesgos/{id}", riesgo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(riesgo.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.probabilidad").value(DEFAULT_PROBABILIDAD))
            .andExpect(jsonPath("$.impacto").value(DEFAULT_IMPACTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRiesgo() throws Exception {
        // Get the riesgo
        restRiesgoMockMvc.perform(get("/api/riesgos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRiesgo() throws Exception {
        // Initialize the database
        riesgoRepository.saveAndFlush(riesgo);
        int databaseSizeBeforeUpdate = riesgoRepository.findAll().size();

        // Update the riesgo
        Riesgo updatedRiesgo = riesgoRepository.findOne(riesgo.getId());
        updatedRiesgo
            .description(UPDATED_DESCRIPTION)
            .probabilidad(UPDATED_PROBABILIDAD)
            .impacto(UPDATED_IMPACTO);
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(updatedRiesgo);

        restRiesgoMockMvc.perform(put("/api/riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riesgoDTO)))
            .andExpect(status().isOk());

        // Validate the Riesgo in the database
        List<Riesgo> riesgoList = riesgoRepository.findAll();
        assertThat(riesgoList).hasSize(databaseSizeBeforeUpdate);
        Riesgo testRiesgo = riesgoList.get(riesgoList.size() - 1);
        assertThat(testRiesgo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRiesgo.getProbabilidad()).isEqualTo(UPDATED_PROBABILIDAD);
        assertThat(testRiesgo.getImpacto()).isEqualTo(UPDATED_IMPACTO);
    }

    @Test
    @Transactional
    public void updateNonExistingRiesgo() throws Exception {
        int databaseSizeBeforeUpdate = riesgoRepository.findAll().size();

        // Create the Riesgo
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRiesgoMockMvc.perform(put("/api/riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(riesgoDTO)))
            .andExpect(status().isCreated());

        // Validate the Riesgo in the database
        List<Riesgo> riesgoList = riesgoRepository.findAll();
        assertThat(riesgoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRiesgo() throws Exception {
        // Initialize the database
        riesgoRepository.saveAndFlush(riesgo);
        int databaseSizeBeforeDelete = riesgoRepository.findAll().size();

        // Get the riesgo
        restRiesgoMockMvc.perform(delete("/api/riesgos/{id}", riesgo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Riesgo> riesgoList = riesgoRepository.findAll();
        assertThat(riesgoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Riesgo.class);
        Riesgo riesgo1 = new Riesgo();
        riesgo1.setId(1L);
        Riesgo riesgo2 = new Riesgo();
        riesgo2.setId(riesgo1.getId());
        assertThat(riesgo1).isEqualTo(riesgo2);
        riesgo2.setId(2L);
        assertThat(riesgo1).isNotEqualTo(riesgo2);
        riesgo1.setId(null);
        assertThat(riesgo1).isNotEqualTo(riesgo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiesgoDTO.class);
        RiesgoDTO riesgoDTO1 = new RiesgoDTO();
        riesgoDTO1.setId(1L);
        RiesgoDTO riesgoDTO2 = new RiesgoDTO();
        assertThat(riesgoDTO1).isNotEqualTo(riesgoDTO2);
        riesgoDTO2.setId(riesgoDTO1.getId());
        assertThat(riesgoDTO1).isEqualTo(riesgoDTO2);
        riesgoDTO2.setId(2L);
        assertThat(riesgoDTO1).isNotEqualTo(riesgoDTO2);
        riesgoDTO1.setId(null);
        assertThat(riesgoDTO1).isNotEqualTo(riesgoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(riesgoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(riesgoMapper.fromId(null)).isNull();
    }
}
