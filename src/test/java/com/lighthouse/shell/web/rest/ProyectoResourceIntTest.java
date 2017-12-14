package com.lighthouse.shell.web.rest;

import com.lighthouse.shell.ShellApp;

import com.lighthouse.shell.domain.Proyecto;
import com.lighthouse.shell.repository.ProyectoRepository;
import com.lighthouse.shell.service.ProyectoService;
import com.lighthouse.shell.service.dto.ProyectoDTO;
import com.lighthouse.shell.service.mapper.ProyectoMapper;
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
 * Test class for the ProyectoResource REST controller.
 *
 * @see ProyectoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShellApp.class)
public class ProyectoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TABLA_IMPACTO_ID = 1;
    private static final Integer UPDATED_TABLA_IMPACTO_ID = 2;

    private static final Integer DEFAULT_TABLA_PROBABILIDAD_ID = 1;
    private static final Integer UPDATED_TABLA_PROBABILIDAD_ID = 2;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private ProyectoMapper proyectoMapper;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProyectoMockMvc;

    private Proyecto proyecto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProyectoResource proyectoResource = new ProyectoResource(proyectoService);
        this.restProyectoMockMvc = MockMvcBuilders.standaloneSetup(proyectoResource)
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
    public static Proyecto createEntity(EntityManager em) {
        Proyecto proyecto = new Proyecto()
            .nombre(DEFAULT_NOMBRE)
            .tablaImpactoId(DEFAULT_TABLA_IMPACTO_ID)
            .tablaProbabilidadId(DEFAULT_TABLA_PROBABILIDAD_ID);
        return proyecto;
    }

    @Before
    public void initTest() {
        proyecto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProyecto() throws Exception {
        int databaseSizeBeforeCreate = proyectoRepository.findAll().size();

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);
        restProyectoMockMvc.perform(post("/api/proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proyectoDTO)))
            .andExpect(status().isCreated());

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeCreate + 1);
        Proyecto testProyecto = proyectoList.get(proyectoList.size() - 1);
        assertThat(testProyecto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProyecto.getTablaImpactoId()).isEqualTo(DEFAULT_TABLA_IMPACTO_ID);
        assertThat(testProyecto.getTablaProbabilidadId()).isEqualTo(DEFAULT_TABLA_PROBABILIDAD_ID);
    }

    @Test
    @Transactional
    public void createProyectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proyectoRepository.findAll().size();

        // Create the Proyecto with an existing ID
        proyecto.setId(1L);
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProyectoMockMvc.perform(post("/api/proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proyectoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProyectos() throws Exception {
        // Initialize the database
        proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList
        restProyectoMockMvc.perform(get("/api/proyectos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].tablaImpactoId").value(hasItem(DEFAULT_TABLA_IMPACTO_ID)))
            .andExpect(jsonPath("$.[*].tablaProbabilidadId").value(hasItem(DEFAULT_TABLA_PROBABILIDAD_ID)));
    }

    @Test
    @Transactional
    public void getProyecto() throws Exception {
        // Initialize the database
        proyectoRepository.saveAndFlush(proyecto);

        // Get the proyecto
        restProyectoMockMvc.perform(get("/api/proyectos/{id}", proyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proyecto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.tablaImpactoId").value(DEFAULT_TABLA_IMPACTO_ID))
            .andExpect(jsonPath("$.tablaProbabilidadId").value(DEFAULT_TABLA_PROBABILIDAD_ID));
    }

    @Test
    @Transactional
    public void getNonExistingProyecto() throws Exception {
        // Get the proyecto
        restProyectoMockMvc.perform(get("/api/proyectos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProyecto() throws Exception {
        // Initialize the database
        proyectoRepository.saveAndFlush(proyecto);
        int databaseSizeBeforeUpdate = proyectoRepository.findAll().size();

        // Update the proyecto
        Proyecto updatedProyecto = proyectoRepository.findOne(proyecto.getId());
        updatedProyecto
            .nombre(UPDATED_NOMBRE)
            .tablaImpactoId(UPDATED_TABLA_IMPACTO_ID)
            .tablaProbabilidadId(UPDATED_TABLA_PROBABILIDAD_ID);
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(updatedProyecto);

        restProyectoMockMvc.perform(put("/api/proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proyectoDTO)))
            .andExpect(status().isOk());

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
        Proyecto testProyecto = proyectoList.get(proyectoList.size() - 1);
        assertThat(testProyecto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProyecto.getTablaImpactoId()).isEqualTo(UPDATED_TABLA_IMPACTO_ID);
        assertThat(testProyecto.getTablaProbabilidadId()).isEqualTo(UPDATED_TABLA_PROBABILIDAD_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingProyecto() throws Exception {
        int databaseSizeBeforeUpdate = proyectoRepository.findAll().size();

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProyectoMockMvc.perform(put("/api/proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proyectoDTO)))
            .andExpect(status().isCreated());

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProyecto() throws Exception {
        // Initialize the database
        proyectoRepository.saveAndFlush(proyecto);
        int databaseSizeBeforeDelete = proyectoRepository.findAll().size();

        // Get the proyecto
        restProyectoMockMvc.perform(delete("/api/proyectos/{id}", proyecto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proyecto.class);
        Proyecto proyecto1 = new Proyecto();
        proyecto1.setId(1L);
        Proyecto proyecto2 = new Proyecto();
        proyecto2.setId(proyecto1.getId());
        assertThat(proyecto1).isEqualTo(proyecto2);
        proyecto2.setId(2L);
        assertThat(proyecto1).isNotEqualTo(proyecto2);
        proyecto1.setId(null);
        assertThat(proyecto1).isNotEqualTo(proyecto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProyectoDTO.class);
        ProyectoDTO proyectoDTO1 = new ProyectoDTO();
        proyectoDTO1.setId(1L);
        ProyectoDTO proyectoDTO2 = new ProyectoDTO();
        assertThat(proyectoDTO1).isNotEqualTo(proyectoDTO2);
        proyectoDTO2.setId(proyectoDTO1.getId());
        assertThat(proyectoDTO1).isEqualTo(proyectoDTO2);
        proyectoDTO2.setId(2L);
        assertThat(proyectoDTO1).isNotEqualTo(proyectoDTO2);
        proyectoDTO1.setId(null);
        assertThat(proyectoDTO1).isNotEqualTo(proyectoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(proyectoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(proyectoMapper.fromId(null)).isNull();
    }
}
