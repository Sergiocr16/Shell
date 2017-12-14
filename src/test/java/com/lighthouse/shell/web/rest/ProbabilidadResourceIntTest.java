package com.lighthouse.shell.web.rest;

import com.lighthouse.shell.ShellApp;

import com.lighthouse.shell.domain.Probabilidad;
import com.lighthouse.shell.repository.ProbabilidadRepository;
import com.lighthouse.shell.service.ProbabilidadService;
import com.lighthouse.shell.service.dto.ProbabilidadDTO;
import com.lighthouse.shell.service.mapper.ProbabilidadMapper;
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
 * Test class for the ProbabilidadResource REST controller.
 *
 * @see ProbabilidadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShellApp.class)
public class ProbabilidadResourceIntTest {

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Double DEFAULT_RANGO_MAYOR = 1D;
    private static final Double UPDATED_RANGO_MAYOR = 2D;

    private static final Double DEFAULT_RANGO_MENOR = 1D;
    private static final Double UPDATED_RANGO_MENOR = 2D;

    @Autowired
    private ProbabilidadRepository probabilidadRepository;

    @Autowired
    private ProbabilidadMapper probabilidadMapper;

    @Autowired
    private ProbabilidadService probabilidadService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProbabilidadMockMvc;

    private Probabilidad probabilidad;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProbabilidadResource probabilidadResource = new ProbabilidadResource(probabilidadService);
        this.restProbabilidadMockMvc = MockMvcBuilders.standaloneSetup(probabilidadResource)
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
    public static Probabilidad createEntity(EntityManager em) {
        Probabilidad probabilidad = new Probabilidad()
            .categoria(DEFAULT_CATEGORIA)
            .valor(DEFAULT_VALOR)
            .descripcion(DEFAULT_DESCRIPCION)
            .rangoMayor(DEFAULT_RANGO_MAYOR)
            .rangoMenor(DEFAULT_RANGO_MENOR);
        return probabilidad;
    }

    @Before
    public void initTest() {
        probabilidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createProbabilidad() throws Exception {
        int databaseSizeBeforeCreate = probabilidadRepository.findAll().size();

        // Create the Probabilidad
        ProbabilidadDTO probabilidadDTO = probabilidadMapper.toDto(probabilidad);
        restProbabilidadMockMvc.perform(post("/api/probabilidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(probabilidadDTO)))
            .andExpect(status().isCreated());

        // Validate the Probabilidad in the database
        List<Probabilidad> probabilidadList = probabilidadRepository.findAll();
        assertThat(probabilidadList).hasSize(databaseSizeBeforeCreate + 1);
        Probabilidad testProbabilidad = probabilidadList.get(probabilidadList.size() - 1);
        assertThat(testProbabilidad.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testProbabilidad.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testProbabilidad.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProbabilidad.getRangoMayor()).isEqualTo(DEFAULT_RANGO_MAYOR);
        assertThat(testProbabilidad.getRangoMenor()).isEqualTo(DEFAULT_RANGO_MENOR);
    }

    @Test
    @Transactional
    public void createProbabilidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = probabilidadRepository.findAll().size();

        // Create the Probabilidad with an existing ID
        probabilidad.setId(1L);
        ProbabilidadDTO probabilidadDTO = probabilidadMapper.toDto(probabilidad);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProbabilidadMockMvc.perform(post("/api/probabilidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(probabilidadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Probabilidad> probabilidadList = probabilidadRepository.findAll();
        assertThat(probabilidadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProbabilidads() throws Exception {
        // Initialize the database
        probabilidadRepository.saveAndFlush(probabilidad);

        // Get all the probabilidadList
        restProbabilidadMockMvc.perform(get("/api/probabilidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(probabilidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].rangoMayor").value(hasItem(DEFAULT_RANGO_MAYOR.doubleValue())))
            .andExpect(jsonPath("$.[*].rangoMenor").value(hasItem(DEFAULT_RANGO_MENOR.doubleValue())));
    }

    @Test
    @Transactional
    public void getProbabilidad() throws Exception {
        // Initialize the database
        probabilidadRepository.saveAndFlush(probabilidad);

        // Get the probabilidad
        restProbabilidadMockMvc.perform(get("/api/probabilidads/{id}", probabilidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(probabilidad.getId().intValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.rangoMayor").value(DEFAULT_RANGO_MAYOR.doubleValue()))
            .andExpect(jsonPath("$.rangoMenor").value(DEFAULT_RANGO_MENOR.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProbabilidad() throws Exception {
        // Get the probabilidad
        restProbabilidadMockMvc.perform(get("/api/probabilidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProbabilidad() throws Exception {
        // Initialize the database
        probabilidadRepository.saveAndFlush(probabilidad);
        int databaseSizeBeforeUpdate = probabilidadRepository.findAll().size();

        // Update the probabilidad
        Probabilidad updatedProbabilidad = probabilidadRepository.findOne(probabilidad.getId());
        updatedProbabilidad
            .categoria(UPDATED_CATEGORIA)
            .valor(UPDATED_VALOR)
            .descripcion(UPDATED_DESCRIPCION)
            .rangoMayor(UPDATED_RANGO_MAYOR)
            .rangoMenor(UPDATED_RANGO_MENOR);
        ProbabilidadDTO probabilidadDTO = probabilidadMapper.toDto(updatedProbabilidad);

        restProbabilidadMockMvc.perform(put("/api/probabilidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(probabilidadDTO)))
            .andExpect(status().isOk());

        // Validate the Probabilidad in the database
        List<Probabilidad> probabilidadList = probabilidadRepository.findAll();
        assertThat(probabilidadList).hasSize(databaseSizeBeforeUpdate);
        Probabilidad testProbabilidad = probabilidadList.get(probabilidadList.size() - 1);
        assertThat(testProbabilidad.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testProbabilidad.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testProbabilidad.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProbabilidad.getRangoMayor()).isEqualTo(UPDATED_RANGO_MAYOR);
        assertThat(testProbabilidad.getRangoMenor()).isEqualTo(UPDATED_RANGO_MENOR);
    }

    @Test
    @Transactional
    public void updateNonExistingProbabilidad() throws Exception {
        int databaseSizeBeforeUpdate = probabilidadRepository.findAll().size();

        // Create the Probabilidad
        ProbabilidadDTO probabilidadDTO = probabilidadMapper.toDto(probabilidad);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProbabilidadMockMvc.perform(put("/api/probabilidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(probabilidadDTO)))
            .andExpect(status().isCreated());

        // Validate the Probabilidad in the database
        List<Probabilidad> probabilidadList = probabilidadRepository.findAll();
        assertThat(probabilidadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProbabilidad() throws Exception {
        // Initialize the database
        probabilidadRepository.saveAndFlush(probabilidad);
        int databaseSizeBeforeDelete = probabilidadRepository.findAll().size();

        // Get the probabilidad
        restProbabilidadMockMvc.perform(delete("/api/probabilidads/{id}", probabilidad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Probabilidad> probabilidadList = probabilidadRepository.findAll();
        assertThat(probabilidadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Probabilidad.class);
        Probabilidad probabilidad1 = new Probabilidad();
        probabilidad1.setId(1L);
        Probabilidad probabilidad2 = new Probabilidad();
        probabilidad2.setId(probabilidad1.getId());
        assertThat(probabilidad1).isEqualTo(probabilidad2);
        probabilidad2.setId(2L);
        assertThat(probabilidad1).isNotEqualTo(probabilidad2);
        probabilidad1.setId(null);
        assertThat(probabilidad1).isNotEqualTo(probabilidad2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProbabilidadDTO.class);
        ProbabilidadDTO probabilidadDTO1 = new ProbabilidadDTO();
        probabilidadDTO1.setId(1L);
        ProbabilidadDTO probabilidadDTO2 = new ProbabilidadDTO();
        assertThat(probabilidadDTO1).isNotEqualTo(probabilidadDTO2);
        probabilidadDTO2.setId(probabilidadDTO1.getId());
        assertThat(probabilidadDTO1).isEqualTo(probabilidadDTO2);
        probabilidadDTO2.setId(2L);
        assertThat(probabilidadDTO1).isNotEqualTo(probabilidadDTO2);
        probabilidadDTO1.setId(null);
        assertThat(probabilidadDTO1).isNotEqualTo(probabilidadDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(probabilidadMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(probabilidadMapper.fromId(null)).isNull();
    }
}
