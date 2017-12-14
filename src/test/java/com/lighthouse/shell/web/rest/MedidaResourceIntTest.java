package com.lighthouse.shell.web.rest;

import com.lighthouse.shell.ShellApp;

import com.lighthouse.shell.domain.Medida;
import com.lighthouse.shell.repository.MedidaRepository;
import com.lighthouse.shell.service.MedidaService;
import com.lighthouse.shell.service.dto.MedidaDTO;
import com.lighthouse.shell.service.mapper.MedidaMapper;
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
 * Test class for the MedidaResource REST controller.
 *
 * @see MedidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShellApp.class)
public class MedidaResourceIntTest {

    private static final String DEFAULT_CONTENCION = "AAAAAAAAAA";
    private static final String UPDATED_CONTENCION = "BBBBBBBBBB";

    private static final String DEFAULT_MITIGACION = "AAAAAAAAAA";
    private static final String UPDATED_MITIGACION = "BBBBBBBBBB";

    @Autowired
    private MedidaRepository medidaRepository;

    @Autowired
    private MedidaMapper medidaMapper;

    @Autowired
    private MedidaService medidaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedidaMockMvc;

    private Medida medida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MedidaResource medidaResource = new MedidaResource(medidaService);
        this.restMedidaMockMvc = MockMvcBuilders.standaloneSetup(medidaResource)
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
    public static Medida createEntity(EntityManager em) {
        Medida medida = new Medida()
            .contencion(DEFAULT_CONTENCION)
            .mitigacion(DEFAULT_MITIGACION);
        return medida;
    }

    @Before
    public void initTest() {
        medida = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedida() throws Exception {
        int databaseSizeBeforeCreate = medidaRepository.findAll().size();

        // Create the Medida
        MedidaDTO medidaDTO = medidaMapper.toDto(medida);
        restMedidaMockMvc.perform(post("/api/medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidaDTO)))
            .andExpect(status().isCreated());

        // Validate the Medida in the database
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeCreate + 1);
        Medida testMedida = medidaList.get(medidaList.size() - 1);
        assertThat(testMedida.getContencion()).isEqualTo(DEFAULT_CONTENCION);
        assertThat(testMedida.getMitigacion()).isEqualTo(DEFAULT_MITIGACION);
    }

    @Test
    @Transactional
    public void createMedidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medidaRepository.findAll().size();

        // Create the Medida with an existing ID
        medida.setId(1L);
        MedidaDTO medidaDTO = medidaMapper.toDto(medida);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedidaMockMvc.perform(post("/api/medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedidas() throws Exception {
        // Initialize the database
        medidaRepository.saveAndFlush(medida);

        // Get all the medidaList
        restMedidaMockMvc.perform(get("/api/medidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medida.getId().intValue())))
            .andExpect(jsonPath("$.[*].contencion").value(hasItem(DEFAULT_CONTENCION.toString())))
            .andExpect(jsonPath("$.[*].mitigacion").value(hasItem(DEFAULT_MITIGACION.toString())));
    }

    @Test
    @Transactional
    public void getMedida() throws Exception {
        // Initialize the database
        medidaRepository.saveAndFlush(medida);

        // Get the medida
        restMedidaMockMvc.perform(get("/api/medidas/{id}", medida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medida.getId().intValue()))
            .andExpect(jsonPath("$.contencion").value(DEFAULT_CONTENCION.toString()))
            .andExpect(jsonPath("$.mitigacion").value(DEFAULT_MITIGACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedida() throws Exception {
        // Get the medida
        restMedidaMockMvc.perform(get("/api/medidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedida() throws Exception {
        // Initialize the database
        medidaRepository.saveAndFlush(medida);
        int databaseSizeBeforeUpdate = medidaRepository.findAll().size();

        // Update the medida
        Medida updatedMedida = medidaRepository.findOne(medida.getId());
        updatedMedida
            .contencion(UPDATED_CONTENCION)
            .mitigacion(UPDATED_MITIGACION);
        MedidaDTO medidaDTO = medidaMapper.toDto(updatedMedida);

        restMedidaMockMvc.perform(put("/api/medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidaDTO)))
            .andExpect(status().isOk());

        // Validate the Medida in the database
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeUpdate);
        Medida testMedida = medidaList.get(medidaList.size() - 1);
        assertThat(testMedida.getContencion()).isEqualTo(UPDATED_CONTENCION);
        assertThat(testMedida.getMitigacion()).isEqualTo(UPDATED_MITIGACION);
    }

    @Test
    @Transactional
    public void updateNonExistingMedida() throws Exception {
        int databaseSizeBeforeUpdate = medidaRepository.findAll().size();

        // Create the Medida
        MedidaDTO medidaDTO = medidaMapper.toDto(medida);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedidaMockMvc.perform(put("/api/medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidaDTO)))
            .andExpect(status().isCreated());

        // Validate the Medida in the database
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedida() throws Exception {
        // Initialize the database
        medidaRepository.saveAndFlush(medida);
        int databaseSizeBeforeDelete = medidaRepository.findAll().size();

        // Get the medida
        restMedidaMockMvc.perform(delete("/api/medidas/{id}", medida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Medida> medidaList = medidaRepository.findAll();
        assertThat(medidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medida.class);
        Medida medida1 = new Medida();
        medida1.setId(1L);
        Medida medida2 = new Medida();
        medida2.setId(medida1.getId());
        assertThat(medida1).isEqualTo(medida2);
        medida2.setId(2L);
        assertThat(medida1).isNotEqualTo(medida2);
        medida1.setId(null);
        assertThat(medida1).isNotEqualTo(medida2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedidaDTO.class);
        MedidaDTO medidaDTO1 = new MedidaDTO();
        medidaDTO1.setId(1L);
        MedidaDTO medidaDTO2 = new MedidaDTO();
        assertThat(medidaDTO1).isNotEqualTo(medidaDTO2);
        medidaDTO2.setId(medidaDTO1.getId());
        assertThat(medidaDTO1).isEqualTo(medidaDTO2);
        medidaDTO2.setId(2L);
        assertThat(medidaDTO1).isNotEqualTo(medidaDTO2);
        medidaDTO1.setId(null);
        assertThat(medidaDTO1).isNotEqualTo(medidaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medidaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medidaMapper.fromId(null)).isNull();
    }
}
