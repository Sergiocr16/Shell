package com.lighthouse.shell.web.rest;

import com.lighthouse.shell.ShellApp;

import com.lighthouse.shell.domain.Impacto;
import com.lighthouse.shell.repository.ImpactoRepository;
import com.lighthouse.shell.service.ImpactoService;
import com.lighthouse.shell.service.dto.ImpactoDTO;
import com.lighthouse.shell.service.mapper.ImpactoMapper;
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
 * Test class for the ImpactoResource REST controller.
 *
 * @see ImpactoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShellApp.class)
public class ImpactoResourceIntTest {

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private ImpactoRepository impactoRepository;

    @Autowired
    private ImpactoMapper impactoMapper;

    @Autowired
    private ImpactoService impactoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restImpactoMockMvc;

    private Impacto impacto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ImpactoResource impactoResource = new ImpactoResource(impactoService);
        this.restImpactoMockMvc = MockMvcBuilders.standaloneSetup(impactoResource)
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
    public static Impacto createEntity(EntityManager em) {
        Impacto impacto = new Impacto()
            .categoria(DEFAULT_CATEGORIA)
            .valor(DEFAULT_VALOR)
            .descripcion(DEFAULT_DESCRIPCION);
        return impacto;
    }

    @Before
    public void initTest() {
        impacto = createEntity(em);
    }

    @Test
    @Transactional
    public void createImpacto() throws Exception {
        int databaseSizeBeforeCreate = impactoRepository.findAll().size();

        // Create the Impacto
        ImpactoDTO impactoDTO = impactoMapper.toDto(impacto);
        restImpactoMockMvc.perform(post("/api/impactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impactoDTO)))
            .andExpect(status().isCreated());

        // Validate the Impacto in the database
        List<Impacto> impactoList = impactoRepository.findAll();
        assertThat(impactoList).hasSize(databaseSizeBeforeCreate + 1);
        Impacto testImpacto = impactoList.get(impactoList.size() - 1);
        assertThat(testImpacto.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testImpacto.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testImpacto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createImpactoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = impactoRepository.findAll().size();

        // Create the Impacto with an existing ID
        impacto.setId(1L);
        ImpactoDTO impactoDTO = impactoMapper.toDto(impacto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImpactoMockMvc.perform(post("/api/impactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impactoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Impacto> impactoList = impactoRepository.findAll();
        assertThat(impactoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllImpactos() throws Exception {
        // Initialize the database
        impactoRepository.saveAndFlush(impacto);

        // Get all the impactoList
        restImpactoMockMvc.perform(get("/api/impactos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(impacto.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getImpacto() throws Exception {
        // Initialize the database
        impactoRepository.saveAndFlush(impacto);

        // Get the impacto
        restImpactoMockMvc.perform(get("/api/impactos/{id}", impacto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(impacto.getId().intValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImpacto() throws Exception {
        // Get the impacto
        restImpactoMockMvc.perform(get("/api/impactos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImpacto() throws Exception {
        // Initialize the database
        impactoRepository.saveAndFlush(impacto);
        int databaseSizeBeforeUpdate = impactoRepository.findAll().size();

        // Update the impacto
        Impacto updatedImpacto = impactoRepository.findOne(impacto.getId());
        updatedImpacto
            .categoria(UPDATED_CATEGORIA)
            .valor(UPDATED_VALOR)
            .descripcion(UPDATED_DESCRIPCION);
        ImpactoDTO impactoDTO = impactoMapper.toDto(updatedImpacto);

        restImpactoMockMvc.perform(put("/api/impactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impactoDTO)))
            .andExpect(status().isOk());

        // Validate the Impacto in the database
        List<Impacto> impactoList = impactoRepository.findAll();
        assertThat(impactoList).hasSize(databaseSizeBeforeUpdate);
        Impacto testImpacto = impactoList.get(impactoList.size() - 1);
        assertThat(testImpacto.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testImpacto.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testImpacto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingImpacto() throws Exception {
        int databaseSizeBeforeUpdate = impactoRepository.findAll().size();

        // Create the Impacto
        ImpactoDTO impactoDTO = impactoMapper.toDto(impacto);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImpactoMockMvc.perform(put("/api/impactos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impactoDTO)))
            .andExpect(status().isCreated());

        // Validate the Impacto in the database
        List<Impacto> impactoList = impactoRepository.findAll();
        assertThat(impactoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteImpacto() throws Exception {
        // Initialize the database
        impactoRepository.saveAndFlush(impacto);
        int databaseSizeBeforeDelete = impactoRepository.findAll().size();

        // Get the impacto
        restImpactoMockMvc.perform(delete("/api/impactos/{id}", impacto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Impacto> impactoList = impactoRepository.findAll();
        assertThat(impactoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Impacto.class);
        Impacto impacto1 = new Impacto();
        impacto1.setId(1L);
        Impacto impacto2 = new Impacto();
        impacto2.setId(impacto1.getId());
        assertThat(impacto1).isEqualTo(impacto2);
        impacto2.setId(2L);
        assertThat(impacto1).isNotEqualTo(impacto2);
        impacto1.setId(null);
        assertThat(impacto1).isNotEqualTo(impacto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImpactoDTO.class);
        ImpactoDTO impactoDTO1 = new ImpactoDTO();
        impactoDTO1.setId(1L);
        ImpactoDTO impactoDTO2 = new ImpactoDTO();
        assertThat(impactoDTO1).isNotEqualTo(impactoDTO2);
        impactoDTO2.setId(impactoDTO1.getId());
        assertThat(impactoDTO1).isEqualTo(impactoDTO2);
        impactoDTO2.setId(2L);
        assertThat(impactoDTO1).isNotEqualTo(impactoDTO2);
        impactoDTO1.setId(null);
        assertThat(impactoDTO1).isNotEqualTo(impactoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(impactoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(impactoMapper.fromId(null)).isNull();
    }
}
