package com.lighthouse.shell.web.rest;

import com.lighthouse.shell.ShellApp;

import com.lighthouse.shell.domain.Lanzamiento;
import com.lighthouse.shell.repository.LanzamientoRepository;
import com.lighthouse.shell.service.LanzamientoService;
import com.lighthouse.shell.service.dto.LanzamientoDTO;
import com.lighthouse.shell.service.mapper.LanzamientoMapper;
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
 * Test class for the LanzamientoResource REST controller.
 *
 * @see LanzamientoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShellApp.class)
public class LanzamientoResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    @Autowired
    private LanzamientoRepository lanzamientoRepository;

    @Autowired
    private LanzamientoMapper lanzamientoMapper;

    @Autowired
    private LanzamientoService lanzamientoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLanzamientoMockMvc;

    private Lanzamiento lanzamiento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LanzamientoResource lanzamientoResource = new LanzamientoResource(lanzamientoService);
        this.restLanzamientoMockMvc = MockMvcBuilders.standaloneSetup(lanzamientoResource)
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
    public static Lanzamiento createEntity(EntityManager em) {
        Lanzamiento lanzamiento = new Lanzamiento()
            .numero(DEFAULT_NUMERO);
        return lanzamiento;
    }

    @Before
    public void initTest() {
        lanzamiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createLanzamiento() throws Exception {
        int databaseSizeBeforeCreate = lanzamientoRepository.findAll().size();

        // Create the Lanzamiento
        LanzamientoDTO lanzamientoDTO = lanzamientoMapper.toDto(lanzamiento);
        restLanzamientoMockMvc.perform(post("/api/lanzamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lanzamientoDTO)))
            .andExpect(status().isCreated());

        // Validate the Lanzamiento in the database
        List<Lanzamiento> lanzamientoList = lanzamientoRepository.findAll();
        assertThat(lanzamientoList).hasSize(databaseSizeBeforeCreate + 1);
        Lanzamiento testLanzamiento = lanzamientoList.get(lanzamientoList.size() - 1);
        assertThat(testLanzamiento.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    public void createLanzamientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lanzamientoRepository.findAll().size();

        // Create the Lanzamiento with an existing ID
        lanzamiento.setId(1L);
        LanzamientoDTO lanzamientoDTO = lanzamientoMapper.toDto(lanzamiento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLanzamientoMockMvc.perform(post("/api/lanzamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lanzamientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Lanzamiento> lanzamientoList = lanzamientoRepository.findAll();
        assertThat(lanzamientoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLanzamientos() throws Exception {
        // Initialize the database
        lanzamientoRepository.saveAndFlush(lanzamiento);

        // Get all the lanzamientoList
        restLanzamientoMockMvc.perform(get("/api/lanzamientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lanzamiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())));
    }

    @Test
    @Transactional
    public void getLanzamiento() throws Exception {
        // Initialize the database
        lanzamientoRepository.saveAndFlush(lanzamiento);

        // Get the lanzamiento
        restLanzamientoMockMvc.perform(get("/api/lanzamientos/{id}", lanzamiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lanzamiento.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLanzamiento() throws Exception {
        // Get the lanzamiento
        restLanzamientoMockMvc.perform(get("/api/lanzamientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLanzamiento() throws Exception {
        // Initialize the database
        lanzamientoRepository.saveAndFlush(lanzamiento);
        int databaseSizeBeforeUpdate = lanzamientoRepository.findAll().size();

        // Update the lanzamiento
        Lanzamiento updatedLanzamiento = lanzamientoRepository.findOne(lanzamiento.getId());
        updatedLanzamiento
            .numero(UPDATED_NUMERO);
        LanzamientoDTO lanzamientoDTO = lanzamientoMapper.toDto(updatedLanzamiento);

        restLanzamientoMockMvc.perform(put("/api/lanzamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lanzamientoDTO)))
            .andExpect(status().isOk());

        // Validate the Lanzamiento in the database
        List<Lanzamiento> lanzamientoList = lanzamientoRepository.findAll();
        assertThat(lanzamientoList).hasSize(databaseSizeBeforeUpdate);
        Lanzamiento testLanzamiento = lanzamientoList.get(lanzamientoList.size() - 1);
        assertThat(testLanzamiento.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void updateNonExistingLanzamiento() throws Exception {
        int databaseSizeBeforeUpdate = lanzamientoRepository.findAll().size();

        // Create the Lanzamiento
        LanzamientoDTO lanzamientoDTO = lanzamientoMapper.toDto(lanzamiento);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLanzamientoMockMvc.perform(put("/api/lanzamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lanzamientoDTO)))
            .andExpect(status().isCreated());

        // Validate the Lanzamiento in the database
        List<Lanzamiento> lanzamientoList = lanzamientoRepository.findAll();
        assertThat(lanzamientoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLanzamiento() throws Exception {
        // Initialize the database
        lanzamientoRepository.saveAndFlush(lanzamiento);
        int databaseSizeBeforeDelete = lanzamientoRepository.findAll().size();

        // Get the lanzamiento
        restLanzamientoMockMvc.perform(delete("/api/lanzamientos/{id}", lanzamiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lanzamiento> lanzamientoList = lanzamientoRepository.findAll();
        assertThat(lanzamientoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lanzamiento.class);
        Lanzamiento lanzamiento1 = new Lanzamiento();
        lanzamiento1.setId(1L);
        Lanzamiento lanzamiento2 = new Lanzamiento();
        lanzamiento2.setId(lanzamiento1.getId());
        assertThat(lanzamiento1).isEqualTo(lanzamiento2);
        lanzamiento2.setId(2L);
        assertThat(lanzamiento1).isNotEqualTo(lanzamiento2);
        lanzamiento1.setId(null);
        assertThat(lanzamiento1).isNotEqualTo(lanzamiento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LanzamientoDTO.class);
        LanzamientoDTO lanzamientoDTO1 = new LanzamientoDTO();
        lanzamientoDTO1.setId(1L);
        LanzamientoDTO lanzamientoDTO2 = new LanzamientoDTO();
        assertThat(lanzamientoDTO1).isNotEqualTo(lanzamientoDTO2);
        lanzamientoDTO2.setId(lanzamientoDTO1.getId());
        assertThat(lanzamientoDTO1).isEqualTo(lanzamientoDTO2);
        lanzamientoDTO2.setId(2L);
        assertThat(lanzamientoDTO1).isNotEqualTo(lanzamientoDTO2);
        lanzamientoDTO1.setId(null);
        assertThat(lanzamientoDTO1).isNotEqualTo(lanzamientoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lanzamientoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lanzamientoMapper.fromId(null)).isNull();
    }
}
