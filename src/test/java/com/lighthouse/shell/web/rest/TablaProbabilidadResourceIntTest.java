package com.lighthouse.shell.web.rest;

import com.lighthouse.shell.ShellApp;

import com.lighthouse.shell.domain.TablaProbabilidad;
import com.lighthouse.shell.repository.TablaProbabilidadRepository;
import com.lighthouse.shell.service.TablaProbabilidadService;
import com.lighthouse.shell.service.dto.TablaProbabilidadDTO;
import com.lighthouse.shell.service.mapper.TablaProbabilidadMapper;
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
 * Test class for the TablaProbabilidadResource REST controller.
 *
 * @see TablaProbabilidadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShellApp.class)
public class TablaProbabilidadResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TablaProbabilidadRepository tablaProbabilidadRepository;

    @Autowired
    private TablaProbabilidadMapper tablaProbabilidadMapper;

    @Autowired
    private TablaProbabilidadService tablaProbabilidadService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTablaProbabilidadMockMvc;

    private TablaProbabilidad tablaProbabilidad;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TablaProbabilidadResource tablaProbabilidadResource = new TablaProbabilidadResource(tablaProbabilidadService);
        this.restTablaProbabilidadMockMvc = MockMvcBuilders.standaloneSetup(tablaProbabilidadResource)
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
    public static TablaProbabilidad createEntity(EntityManager em) {
        TablaProbabilidad tablaProbabilidad = new TablaProbabilidad()
            .nombre(DEFAULT_NOMBRE);
        return tablaProbabilidad;
    }

    @Before
    public void initTest() {
        tablaProbabilidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createTablaProbabilidad() throws Exception {
        int databaseSizeBeforeCreate = tablaProbabilidadRepository.findAll().size();

        // Create the TablaProbabilidad
        TablaProbabilidadDTO tablaProbabilidadDTO = tablaProbabilidadMapper.toDto(tablaProbabilidad);
        restTablaProbabilidadMockMvc.perform(post("/api/tabla-probabilidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tablaProbabilidadDTO)))
            .andExpect(status().isCreated());

        // Validate the TablaProbabilidad in the database
        List<TablaProbabilidad> tablaProbabilidadList = tablaProbabilidadRepository.findAll();
        assertThat(tablaProbabilidadList).hasSize(databaseSizeBeforeCreate + 1);
        TablaProbabilidad testTablaProbabilidad = tablaProbabilidadList.get(tablaProbabilidadList.size() - 1);
        assertThat(testTablaProbabilidad.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createTablaProbabilidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tablaProbabilidadRepository.findAll().size();

        // Create the TablaProbabilidad with an existing ID
        tablaProbabilidad.setId(1L);
        TablaProbabilidadDTO tablaProbabilidadDTO = tablaProbabilidadMapper.toDto(tablaProbabilidad);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTablaProbabilidadMockMvc.perform(post("/api/tabla-probabilidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tablaProbabilidadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TablaProbabilidad> tablaProbabilidadList = tablaProbabilidadRepository.findAll();
        assertThat(tablaProbabilidadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTablaProbabilidads() throws Exception {
        // Initialize the database
        tablaProbabilidadRepository.saveAndFlush(tablaProbabilidad);

        // Get all the tablaProbabilidadList
        restTablaProbabilidadMockMvc.perform(get("/api/tabla-probabilidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tablaProbabilidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getTablaProbabilidad() throws Exception {
        // Initialize the database
        tablaProbabilidadRepository.saveAndFlush(tablaProbabilidad);

        // Get the tablaProbabilidad
        restTablaProbabilidadMockMvc.perform(get("/api/tabla-probabilidads/{id}", tablaProbabilidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tablaProbabilidad.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTablaProbabilidad() throws Exception {
        // Get the tablaProbabilidad
        restTablaProbabilidadMockMvc.perform(get("/api/tabla-probabilidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTablaProbabilidad() throws Exception {
        // Initialize the database
        tablaProbabilidadRepository.saveAndFlush(tablaProbabilidad);
        int databaseSizeBeforeUpdate = tablaProbabilidadRepository.findAll().size();

        // Update the tablaProbabilidad
        TablaProbabilidad updatedTablaProbabilidad = tablaProbabilidadRepository.findOne(tablaProbabilidad.getId());
        updatedTablaProbabilidad
            .nombre(UPDATED_NOMBRE);
        TablaProbabilidadDTO tablaProbabilidadDTO = tablaProbabilidadMapper.toDto(updatedTablaProbabilidad);

        restTablaProbabilidadMockMvc.perform(put("/api/tabla-probabilidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tablaProbabilidadDTO)))
            .andExpect(status().isOk());

        // Validate the TablaProbabilidad in the database
        List<TablaProbabilidad> tablaProbabilidadList = tablaProbabilidadRepository.findAll();
        assertThat(tablaProbabilidadList).hasSize(databaseSizeBeforeUpdate);
        TablaProbabilidad testTablaProbabilidad = tablaProbabilidadList.get(tablaProbabilidadList.size() - 1);
        assertThat(testTablaProbabilidad.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTablaProbabilidad() throws Exception {
        int databaseSizeBeforeUpdate = tablaProbabilidadRepository.findAll().size();

        // Create the TablaProbabilidad
        TablaProbabilidadDTO tablaProbabilidadDTO = tablaProbabilidadMapper.toDto(tablaProbabilidad);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTablaProbabilidadMockMvc.perform(put("/api/tabla-probabilidads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tablaProbabilidadDTO)))
            .andExpect(status().isCreated());

        // Validate the TablaProbabilidad in the database
        List<TablaProbabilidad> tablaProbabilidadList = tablaProbabilidadRepository.findAll();
        assertThat(tablaProbabilidadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTablaProbabilidad() throws Exception {
        // Initialize the database
        tablaProbabilidadRepository.saveAndFlush(tablaProbabilidad);
        int databaseSizeBeforeDelete = tablaProbabilidadRepository.findAll().size();

        // Get the tablaProbabilidad
        restTablaProbabilidadMockMvc.perform(delete("/api/tabla-probabilidads/{id}", tablaProbabilidad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TablaProbabilidad> tablaProbabilidadList = tablaProbabilidadRepository.findAll();
        assertThat(tablaProbabilidadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TablaProbabilidad.class);
        TablaProbabilidad tablaProbabilidad1 = new TablaProbabilidad();
        tablaProbabilidad1.setId(1L);
        TablaProbabilidad tablaProbabilidad2 = new TablaProbabilidad();
        tablaProbabilidad2.setId(tablaProbabilidad1.getId());
        assertThat(tablaProbabilidad1).isEqualTo(tablaProbabilidad2);
        tablaProbabilidad2.setId(2L);
        assertThat(tablaProbabilidad1).isNotEqualTo(tablaProbabilidad2);
        tablaProbabilidad1.setId(null);
        assertThat(tablaProbabilidad1).isNotEqualTo(tablaProbabilidad2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TablaProbabilidadDTO.class);
        TablaProbabilidadDTO tablaProbabilidadDTO1 = new TablaProbabilidadDTO();
        tablaProbabilidadDTO1.setId(1L);
        TablaProbabilidadDTO tablaProbabilidadDTO2 = new TablaProbabilidadDTO();
        assertThat(tablaProbabilidadDTO1).isNotEqualTo(tablaProbabilidadDTO2);
        tablaProbabilidadDTO2.setId(tablaProbabilidadDTO1.getId());
        assertThat(tablaProbabilidadDTO1).isEqualTo(tablaProbabilidadDTO2);
        tablaProbabilidadDTO2.setId(2L);
        assertThat(tablaProbabilidadDTO1).isNotEqualTo(tablaProbabilidadDTO2);
        tablaProbabilidadDTO1.setId(null);
        assertThat(tablaProbabilidadDTO1).isNotEqualTo(tablaProbabilidadDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tablaProbabilidadMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tablaProbabilidadMapper.fromId(null)).isNull();
    }
}
