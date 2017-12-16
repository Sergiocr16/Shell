package com.lighthouse.shell.web.rest;

import com.lighthouse.shell.ShellApp;

import com.lighthouse.shell.domain.CategoriaRangoRiesgo;
import com.lighthouse.shell.repository.CategoriaRangoRiesgoRepository;
import com.lighthouse.shell.service.CategoriaRangoRiesgoService;
import com.lighthouse.shell.service.dto.CategoriaRangoRiesgoDTO;
import com.lighthouse.shell.service.mapper.CategoriaRangoRiesgoMapper;
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
 * Test class for the CategoriaRangoRiesgoResource REST controller.
 *
 * @see CategoriaRangoRiesgoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShellApp.class)
public class CategoriaRangoRiesgoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    @Autowired
    private CategoriaRangoRiesgoRepository categoriaRangoRiesgoRepository;

    @Autowired
    private CategoriaRangoRiesgoMapper categoriaRangoRiesgoMapper;

    @Autowired
    private CategoriaRangoRiesgoService categoriaRangoRiesgoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCategoriaRangoRiesgoMockMvc;

    private CategoriaRangoRiesgo categoriaRangoRiesgo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CategoriaRangoRiesgoResource categoriaRangoRiesgoResource = new CategoriaRangoRiesgoResource(categoriaRangoRiesgoService);
        this.restCategoriaRangoRiesgoMockMvc = MockMvcBuilders.standaloneSetup(categoriaRangoRiesgoResource)
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
    public static CategoriaRangoRiesgo createEntity(EntityManager em) {
        CategoriaRangoRiesgo categoriaRangoRiesgo = new CategoriaRangoRiesgo()
            .nombre(DEFAULT_NOMBRE)
            .color(DEFAULT_COLOR);
        return categoriaRangoRiesgo;
    }

    @Before
    public void initTest() {
        categoriaRangoRiesgo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoriaRangoRiesgo() throws Exception {
        int databaseSizeBeforeCreate = categoriaRangoRiesgoRepository.findAll().size();

        // Create the CategoriaRangoRiesgo
        CategoriaRangoRiesgoDTO categoriaRangoRiesgoDTO = categoriaRangoRiesgoMapper.toDto(categoriaRangoRiesgo);
        restCategoriaRangoRiesgoMockMvc.perform(post("/api/categoria-rango-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaRangoRiesgoDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoriaRangoRiesgo in the database
        List<CategoriaRangoRiesgo> categoriaRangoRiesgoList = categoriaRangoRiesgoRepository.findAll();
        assertThat(categoriaRangoRiesgoList).hasSize(databaseSizeBeforeCreate + 1);
        CategoriaRangoRiesgo testCategoriaRangoRiesgo = categoriaRangoRiesgoList.get(categoriaRangoRiesgoList.size() - 1);
        assertThat(testCategoriaRangoRiesgo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCategoriaRangoRiesgo.getColor()).isEqualTo(DEFAULT_COLOR);
    }

    @Test
    @Transactional
    public void createCategoriaRangoRiesgoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriaRangoRiesgoRepository.findAll().size();

        // Create the CategoriaRangoRiesgo with an existing ID
        categoriaRangoRiesgo.setId(1L);
        CategoriaRangoRiesgoDTO categoriaRangoRiesgoDTO = categoriaRangoRiesgoMapper.toDto(categoriaRangoRiesgo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriaRangoRiesgoMockMvc.perform(post("/api/categoria-rango-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaRangoRiesgoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CategoriaRangoRiesgo> categoriaRangoRiesgoList = categoriaRangoRiesgoRepository.findAll();
        assertThat(categoriaRangoRiesgoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCategoriaRangoRiesgos() throws Exception {
        // Initialize the database
        categoriaRangoRiesgoRepository.saveAndFlush(categoriaRangoRiesgo);

        // Get all the categoriaRangoRiesgoList
        restCategoriaRangoRiesgoMockMvc.perform(get("/api/categoria-rango-riesgos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoriaRangoRiesgo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())));
    }

    @Test
    @Transactional
    public void getCategoriaRangoRiesgo() throws Exception {
        // Initialize the database
        categoriaRangoRiesgoRepository.saveAndFlush(categoriaRangoRiesgo);

        // Get the categoriaRangoRiesgo
        restCategoriaRangoRiesgoMockMvc.perform(get("/api/categoria-rango-riesgos/{id}", categoriaRangoRiesgo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoriaRangoRiesgo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategoriaRangoRiesgo() throws Exception {
        // Get the categoriaRangoRiesgo
        restCategoriaRangoRiesgoMockMvc.perform(get("/api/categoria-rango-riesgos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoriaRangoRiesgo() throws Exception {
        // Initialize the database
        categoriaRangoRiesgoRepository.saveAndFlush(categoriaRangoRiesgo);
        int databaseSizeBeforeUpdate = categoriaRangoRiesgoRepository.findAll().size();

        // Update the categoriaRangoRiesgo
        CategoriaRangoRiesgo updatedCategoriaRangoRiesgo = categoriaRangoRiesgoRepository.findOne(categoriaRangoRiesgo.getId());
        updatedCategoriaRangoRiesgo
            .nombre(UPDATED_NOMBRE)
            .color(UPDATED_COLOR);
        CategoriaRangoRiesgoDTO categoriaRangoRiesgoDTO = categoriaRangoRiesgoMapper.toDto(updatedCategoriaRangoRiesgo);

        restCategoriaRangoRiesgoMockMvc.perform(put("/api/categoria-rango-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaRangoRiesgoDTO)))
            .andExpect(status().isOk());

        // Validate the CategoriaRangoRiesgo in the database
        List<CategoriaRangoRiesgo> categoriaRangoRiesgoList = categoriaRangoRiesgoRepository.findAll();
        assertThat(categoriaRangoRiesgoList).hasSize(databaseSizeBeforeUpdate);
        CategoriaRangoRiesgo testCategoriaRangoRiesgo = categoriaRangoRiesgoList.get(categoriaRangoRiesgoList.size() - 1);
        assertThat(testCategoriaRangoRiesgo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCategoriaRangoRiesgo.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoriaRangoRiesgo() throws Exception {
        int databaseSizeBeforeUpdate = categoriaRangoRiesgoRepository.findAll().size();

        // Create the CategoriaRangoRiesgo
        CategoriaRangoRiesgoDTO categoriaRangoRiesgoDTO = categoriaRangoRiesgoMapper.toDto(categoriaRangoRiesgo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCategoriaRangoRiesgoMockMvc.perform(put("/api/categoria-rango-riesgos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoriaRangoRiesgoDTO)))
            .andExpect(status().isCreated());

        // Validate the CategoriaRangoRiesgo in the database
        List<CategoriaRangoRiesgo> categoriaRangoRiesgoList = categoriaRangoRiesgoRepository.findAll();
        assertThat(categoriaRangoRiesgoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCategoriaRangoRiesgo() throws Exception {
        // Initialize the database
        categoriaRangoRiesgoRepository.saveAndFlush(categoriaRangoRiesgo);
        int databaseSizeBeforeDelete = categoriaRangoRiesgoRepository.findAll().size();

        // Get the categoriaRangoRiesgo
        restCategoriaRangoRiesgoMockMvc.perform(delete("/api/categoria-rango-riesgos/{id}", categoriaRangoRiesgo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CategoriaRangoRiesgo> categoriaRangoRiesgoList = categoriaRangoRiesgoRepository.findAll();
        assertThat(categoriaRangoRiesgoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaRangoRiesgo.class);
        CategoriaRangoRiesgo categoriaRangoRiesgo1 = new CategoriaRangoRiesgo();
        categoriaRangoRiesgo1.setId(1L);
        CategoriaRangoRiesgo categoriaRangoRiesgo2 = new CategoriaRangoRiesgo();
        categoriaRangoRiesgo2.setId(categoriaRangoRiesgo1.getId());
        assertThat(categoriaRangoRiesgo1).isEqualTo(categoriaRangoRiesgo2);
        categoriaRangoRiesgo2.setId(2L);
        assertThat(categoriaRangoRiesgo1).isNotEqualTo(categoriaRangoRiesgo2);
        categoriaRangoRiesgo1.setId(null);
        assertThat(categoriaRangoRiesgo1).isNotEqualTo(categoriaRangoRiesgo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriaRangoRiesgoDTO.class);
        CategoriaRangoRiesgoDTO categoriaRangoRiesgoDTO1 = new CategoriaRangoRiesgoDTO();
        categoriaRangoRiesgoDTO1.setId(1L);
        CategoriaRangoRiesgoDTO categoriaRangoRiesgoDTO2 = new CategoriaRangoRiesgoDTO();
        assertThat(categoriaRangoRiesgoDTO1).isNotEqualTo(categoriaRangoRiesgoDTO2);
        categoriaRangoRiesgoDTO2.setId(categoriaRangoRiesgoDTO1.getId());
        assertThat(categoriaRangoRiesgoDTO1).isEqualTo(categoriaRangoRiesgoDTO2);
        categoriaRangoRiesgoDTO2.setId(2L);
        assertThat(categoriaRangoRiesgoDTO1).isNotEqualTo(categoriaRangoRiesgoDTO2);
        categoriaRangoRiesgoDTO1.setId(null);
        assertThat(categoriaRangoRiesgoDTO1).isNotEqualTo(categoriaRangoRiesgoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(categoriaRangoRiesgoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(categoriaRangoRiesgoMapper.fromId(null)).isNull();
    }
}
