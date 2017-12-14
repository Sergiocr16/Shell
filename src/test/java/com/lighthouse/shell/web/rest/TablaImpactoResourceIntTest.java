package com.lighthouse.shell.web.rest;

import com.lighthouse.shell.ShellApp;

import com.lighthouse.shell.domain.TablaImpacto;
import com.lighthouse.shell.repository.TablaImpactoRepository;
import com.lighthouse.shell.service.TablaImpactoService;
import com.lighthouse.shell.service.dto.TablaImpactoDTO;
import com.lighthouse.shell.service.mapper.TablaImpactoMapper;
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
 * Test class for the TablaImpactoResource REST controller.
 *
 * @see TablaImpactoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShellApp.class)
public class TablaImpactoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TablaImpactoRepository tablaImpactoRepository;

    @Autowired
    private TablaImpactoMapper tablaImpactoMapper;

    @Autowired
    private TablaImpactoService tablaImpactoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTablaImpactoMockMvc;

    private TablaImpacto tablaImpacto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TablaImpactoResource tablaImpactoResource = new TablaImpactoResource(tablaImpactoService);
        this.restTablaImpactoMockMvc = MockMvcBuilders.standaloneSetup(tablaImpactoResource)
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
    public static TablaImpacto createEntity(EntityManager em) {
        TablaImpacto tablaImpacto = new TablaImpacto()
            .nombre(DEFAULT_NOMBRE);
        return tablaImpacto;
    }

    @Before
    public void initTest() {
        tablaImpacto = createEntity(em);
    }

    @Test
    @Transactional
    public void createTablaImpacto() throws Exception {
        int databaseSizeBeforeCreate = tablaImpactoRepository.findAll().size();

        // Create the TablaImpacto
        TablaImpactoDTO tablaImpactoDTO = tablaImpactoMapper.toDto(tablaImpacto);
        restTablaImpactoMockMvc.perform(post("/api/tabla-impactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tablaImpactoDTO)))
            .andExpect(status().isCreated());

        // Validate the TablaImpacto in the database
        List<TablaImpacto> tablaImpactoList = tablaImpactoRepository.findAll();
        assertThat(tablaImpactoList).hasSize(databaseSizeBeforeCreate + 1);
        TablaImpacto testTablaImpacto = tablaImpactoList.get(tablaImpactoList.size() - 1);
        assertThat(testTablaImpacto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createTablaImpactoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tablaImpactoRepository.findAll().size();

        // Create the TablaImpacto with an existing ID
        tablaImpacto.setId(1L);
        TablaImpactoDTO tablaImpactoDTO = tablaImpactoMapper.toDto(tablaImpacto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTablaImpactoMockMvc.perform(post("/api/tabla-impactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tablaImpactoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TablaImpacto> tablaImpactoList = tablaImpactoRepository.findAll();
        assertThat(tablaImpactoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTablaImpactos() throws Exception {
        // Initialize the database
        tablaImpactoRepository.saveAndFlush(tablaImpacto);

        // Get all the tablaImpactoList
        restTablaImpactoMockMvc.perform(get("/api/tabla-impactos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tablaImpacto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getTablaImpacto() throws Exception {
        // Initialize the database
        tablaImpactoRepository.saveAndFlush(tablaImpacto);

        // Get the tablaImpacto
        restTablaImpactoMockMvc.perform(get("/api/tabla-impactos/{id}", tablaImpacto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tablaImpacto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTablaImpacto() throws Exception {
        // Get the tablaImpacto
        restTablaImpactoMockMvc.perform(get("/api/tabla-impactos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTablaImpacto() throws Exception {
        // Initialize the database
        tablaImpactoRepository.saveAndFlush(tablaImpacto);
        int databaseSizeBeforeUpdate = tablaImpactoRepository.findAll().size();

        // Update the tablaImpacto
        TablaImpacto updatedTablaImpacto = tablaImpactoRepository.findOne(tablaImpacto.getId());
        updatedTablaImpacto
            .nombre(UPDATED_NOMBRE);
        TablaImpactoDTO tablaImpactoDTO = tablaImpactoMapper.toDto(updatedTablaImpacto);

        restTablaImpactoMockMvc.perform(put("/api/tabla-impactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tablaImpactoDTO)))
            .andExpect(status().isOk());

        // Validate the TablaImpacto in the database
        List<TablaImpacto> tablaImpactoList = tablaImpactoRepository.findAll();
        assertThat(tablaImpactoList).hasSize(databaseSizeBeforeUpdate);
        TablaImpacto testTablaImpacto = tablaImpactoList.get(tablaImpactoList.size() - 1);
        assertThat(testTablaImpacto.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTablaImpacto() throws Exception {
        int databaseSizeBeforeUpdate = tablaImpactoRepository.findAll().size();

        // Create the TablaImpacto
        TablaImpactoDTO tablaImpactoDTO = tablaImpactoMapper.toDto(tablaImpacto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTablaImpactoMockMvc.perform(put("/api/tabla-impactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tablaImpactoDTO)))
            .andExpect(status().isCreated());

        // Validate the TablaImpacto in the database
        List<TablaImpacto> tablaImpactoList = tablaImpactoRepository.findAll();
        assertThat(tablaImpactoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTablaImpacto() throws Exception {
        // Initialize the database
        tablaImpactoRepository.saveAndFlush(tablaImpacto);
        int databaseSizeBeforeDelete = tablaImpactoRepository.findAll().size();

        // Get the tablaImpacto
        restTablaImpactoMockMvc.perform(delete("/api/tabla-impactos/{id}", tablaImpacto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TablaImpacto> tablaImpactoList = tablaImpactoRepository.findAll();
        assertThat(tablaImpactoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TablaImpacto.class);
        TablaImpacto tablaImpacto1 = new TablaImpacto();
        tablaImpacto1.setId(1L);
        TablaImpacto tablaImpacto2 = new TablaImpacto();
        tablaImpacto2.setId(tablaImpacto1.getId());
        assertThat(tablaImpacto1).isEqualTo(tablaImpacto2);
        tablaImpacto2.setId(2L);
        assertThat(tablaImpacto1).isNotEqualTo(tablaImpacto2);
        tablaImpacto1.setId(null);
        assertThat(tablaImpacto1).isNotEqualTo(tablaImpacto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TablaImpactoDTO.class);
        TablaImpactoDTO tablaImpactoDTO1 = new TablaImpactoDTO();
        tablaImpactoDTO1.setId(1L);
        TablaImpactoDTO tablaImpactoDTO2 = new TablaImpactoDTO();
        assertThat(tablaImpactoDTO1).isNotEqualTo(tablaImpactoDTO2);
        tablaImpactoDTO2.setId(tablaImpactoDTO1.getId());
        assertThat(tablaImpactoDTO1).isEqualTo(tablaImpactoDTO2);
        tablaImpactoDTO2.setId(2L);
        assertThat(tablaImpactoDTO1).isNotEqualTo(tablaImpactoDTO2);
        tablaImpactoDTO1.setId(null);
        assertThat(tablaImpactoDTO1).isNotEqualTo(tablaImpactoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tablaImpactoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tablaImpactoMapper.fromId(null)).isNull();
    }
}
