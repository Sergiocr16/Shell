package com.lighthouse.shell.web.rest;

import com.lighthouse.shell.ShellApp;

import com.lighthouse.shell.domain.RangoRiesgo;
import com.lighthouse.shell.repository.RangoRiesgoRepository;
import com.lighthouse.shell.service.RangoRiesgoService;
import com.lighthouse.shell.service.dto.RangoRiesgoDTO;
import com.lighthouse.shell.service.mapper.RangoRiesgoMapper;
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
 * Test class for the RangoRiesgoResource REST controller.
 *
 * @see RangoRiesgoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShellApp.class)
public class RangoRiesgoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_PROBABILIDAD = 1;
    private static final Integer UPDATED_PROBABILIDAD = 2;

    private static final Integer DEFAULT_IMPACTO = 1;
    private static final Integer UPDATED_IMPACTO = 2;

    @Autowired
    private RangoRiesgoRepository rangoRiesgoRepository;

    @Autowired
    private RangoRiesgoMapper rangoRiesgoMapper;

    @Autowired
    private RangoRiesgoService rangoRiesgoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRangoRiesgoMockMvc;

    private RangoRiesgo rangoRiesgo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RangoRiesgoResource rangoRiesgoResource = new RangoRiesgoResource(rangoRiesgoService);
        this.restRangoRiesgoMockMvc = MockMvcBuilders.standaloneSetup(rangoRiesgoResource)
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
    public static RangoRiesgo createEntity(EntityManager em) {
        RangoRiesgo rangoRiesgo = new RangoRiesgo()
            .nombre(DEFAULT_NOMBRE)
            .color(DEFAULT_COLOR)
            .probabilidad(DEFAULT_PROBABILIDAD)
            .impacto(DEFAULT_IMPACTO);
        return rangoRiesgo;
    }

    @Before
    public void initTest() {
        rangoRiesgo = createEntity(em);
    }

    @Test
    @Transactional
    public void createRangoRiesgo() throws Exception {
        int databaseSizeBeforeCreate = rangoRiesgoRepository.findAll().size();

        // Create the RangoRiesgo
        RangoRiesgoDTO rangoRiesgoDTO = rangoRiesgoMapper.toDto(rangoRiesgo);
        restRangoRiesgoMockMvc.perform(post("/api/rango-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rangoRiesgoDTO)))
            .andExpect(status().isCreated());

        // Validate the RangoRiesgo in the database
        List<RangoRiesgo> rangoRiesgoList = rangoRiesgoRepository.findAll();
        assertThat(rangoRiesgoList).hasSize(databaseSizeBeforeCreate + 1);
        RangoRiesgo testRangoRiesgo = rangoRiesgoList.get(rangoRiesgoList.size() - 1);
        assertThat(testRangoRiesgo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRangoRiesgo.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testRangoRiesgo.getProbabilidad()).isEqualTo(DEFAULT_PROBABILIDAD);
        assertThat(testRangoRiesgo.getImpacto()).isEqualTo(DEFAULT_IMPACTO);
    }

    @Test
    @Transactional
    public void createRangoRiesgoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rangoRiesgoRepository.findAll().size();

        // Create the RangoRiesgo with an existing ID
        rangoRiesgo.setId(1L);
        RangoRiesgoDTO rangoRiesgoDTO = rangoRiesgoMapper.toDto(rangoRiesgo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRangoRiesgoMockMvc.perform(post("/api/rango-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rangoRiesgoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RangoRiesgo> rangoRiesgoList = rangoRiesgoRepository.findAll();
        assertThat(rangoRiesgoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRangoRiesgos() throws Exception {
        // Initialize the database
        rangoRiesgoRepository.saveAndFlush(rangoRiesgo);

        // Get all the rangoRiesgoList
        restRangoRiesgoMockMvc.perform(get("/api/rango-riesgos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rangoRiesgo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].probabilidad").value(hasItem(DEFAULT_PROBABILIDAD)))
            .andExpect(jsonPath("$.[*].impacto").value(hasItem(DEFAULT_IMPACTO)));
    }

    @Test
    @Transactional
    public void getRangoRiesgo() throws Exception {
        // Initialize the database
        rangoRiesgoRepository.saveAndFlush(rangoRiesgo);

        // Get the rangoRiesgo
        restRangoRiesgoMockMvc.perform(get("/api/rango-riesgos/{id}", rangoRiesgo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rangoRiesgo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.probabilidad").value(DEFAULT_PROBABILIDAD))
            .andExpect(jsonPath("$.impacto").value(DEFAULT_IMPACTO));
    }

    @Test
    @Transactional
    public void getNonExistingRangoRiesgo() throws Exception {
        // Get the rangoRiesgo
        restRangoRiesgoMockMvc.perform(get("/api/rango-riesgos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRangoRiesgo() throws Exception {
        // Initialize the database
        rangoRiesgoRepository.saveAndFlush(rangoRiesgo);
        int databaseSizeBeforeUpdate = rangoRiesgoRepository.findAll().size();

        // Update the rangoRiesgo
        RangoRiesgo updatedRangoRiesgo = rangoRiesgoRepository.findOne(rangoRiesgo.getId());
        updatedRangoRiesgo
            .nombre(UPDATED_NOMBRE)
            .color(UPDATED_COLOR)
            .probabilidad(UPDATED_PROBABILIDAD)
            .impacto(UPDATED_IMPACTO);
        RangoRiesgoDTO rangoRiesgoDTO = rangoRiesgoMapper.toDto(updatedRangoRiesgo);

        restRangoRiesgoMockMvc.perform(put("/api/rango-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rangoRiesgoDTO)))
            .andExpect(status().isOk());

        // Validate the RangoRiesgo in the database
        List<RangoRiesgo> rangoRiesgoList = rangoRiesgoRepository.findAll();
        assertThat(rangoRiesgoList).hasSize(databaseSizeBeforeUpdate);
        RangoRiesgo testRangoRiesgo = rangoRiesgoList.get(rangoRiesgoList.size() - 1);
        assertThat(testRangoRiesgo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRangoRiesgo.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testRangoRiesgo.getProbabilidad()).isEqualTo(UPDATED_PROBABILIDAD);
        assertThat(testRangoRiesgo.getImpacto()).isEqualTo(UPDATED_IMPACTO);
    }

    @Test
    @Transactional
    public void updateNonExistingRangoRiesgo() throws Exception {
        int databaseSizeBeforeUpdate = rangoRiesgoRepository.findAll().size();

        // Create the RangoRiesgo
        RangoRiesgoDTO rangoRiesgoDTO = rangoRiesgoMapper.toDto(rangoRiesgo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRangoRiesgoMockMvc.perform(put("/api/rango-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rangoRiesgoDTO)))
            .andExpect(status().isCreated());

        // Validate the RangoRiesgo in the database
        List<RangoRiesgo> rangoRiesgoList = rangoRiesgoRepository.findAll();
        assertThat(rangoRiesgoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRangoRiesgo() throws Exception {
        // Initialize the database
        rangoRiesgoRepository.saveAndFlush(rangoRiesgo);
        int databaseSizeBeforeDelete = rangoRiesgoRepository.findAll().size();

        // Get the rangoRiesgo
        restRangoRiesgoMockMvc.perform(delete("/api/rango-riesgos/{id}", rangoRiesgo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RangoRiesgo> rangoRiesgoList = rangoRiesgoRepository.findAll();
        assertThat(rangoRiesgoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RangoRiesgo.class);
        RangoRiesgo rangoRiesgo1 = new RangoRiesgo();
        rangoRiesgo1.setId(1L);
        RangoRiesgo rangoRiesgo2 = new RangoRiesgo();
        rangoRiesgo2.setId(rangoRiesgo1.getId());
        assertThat(rangoRiesgo1).isEqualTo(rangoRiesgo2);
        rangoRiesgo2.setId(2L);
        assertThat(rangoRiesgo1).isNotEqualTo(rangoRiesgo2);
        rangoRiesgo1.setId(null);
        assertThat(rangoRiesgo1).isNotEqualTo(rangoRiesgo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RangoRiesgoDTO.class);
        RangoRiesgoDTO rangoRiesgoDTO1 = new RangoRiesgoDTO();
        rangoRiesgoDTO1.setId(1L);
        RangoRiesgoDTO rangoRiesgoDTO2 = new RangoRiesgoDTO();
        assertThat(rangoRiesgoDTO1).isNotEqualTo(rangoRiesgoDTO2);
        rangoRiesgoDTO2.setId(rangoRiesgoDTO1.getId());
        assertThat(rangoRiesgoDTO1).isEqualTo(rangoRiesgoDTO2);
        rangoRiesgoDTO2.setId(2L);
        assertThat(rangoRiesgoDTO1).isNotEqualTo(rangoRiesgoDTO2);
        rangoRiesgoDTO1.setId(null);
        assertThat(rangoRiesgoDTO1).isNotEqualTo(rangoRiesgoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rangoRiesgoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rangoRiesgoMapper.fromId(null)).isNull();
    }
}
