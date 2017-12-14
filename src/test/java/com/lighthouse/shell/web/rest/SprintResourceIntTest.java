package com.lighthouse.shell.web.rest;

import com.lighthouse.shell.ShellApp;

import com.lighthouse.shell.domain.Sprint;
import com.lighthouse.shell.repository.SprintRepository;
import com.lighthouse.shell.service.SprintService;
import com.lighthouse.shell.service.dto.SprintDTO;
import com.lighthouse.shell.service.mapper.SprintMapper;
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
 * Test class for the SprintResource REST controller.
 *
 * @see SprintResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShellApp.class)
public class SprintResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PUNTOS_PLANEADOS = 1;
    private static final Integer UPDATED_PUNTOS_PLANEADOS = 2;

    private static final Integer DEFAULT_PUNTOS_REALIZADOS = 1;
    private static final Integer UPDATED_PUNTOS_REALIZADOS = 2;

    private static final String DEFAULT_SPI = "AAAAAAAAAA";
    private static final String UPDATED_SPI = "BBBBBBBBBB";

    private static final String DEFAULT_CPI = "AAAAAAAAAA";
    private static final String UPDATED_CPI = "BBBBBBBBBB";

    private static final String DEFAULT_SV = "AAAAAAAAAA";
    private static final String UPDATED_SV = "BBBBBBBBBB";

    private static final String DEFAULT_VAC = "AAAAAAAAAA";
    private static final String UPDATED_VAC = "BBBBBBBBBB";

    private static final String DEFAULT_PE = "AAAAAAAAAA";
    private static final String UPDATED_PE = "BBBBBBBBBB";

    private static final String DEFAULT_BAC = "AAAAAAAAAA";
    private static final String UPDATED_BAC = "BBBBBBBBBB";

    private static final String DEFAULT_PV = "AAAAAAAAAA";
    private static final String UPDATED_PV = "BBBBBBBBBB";

    private static final String DEFAULT_AC = "AAAAAAAAAA";
    private static final String UPDATED_AC = "BBBBBBBBBB";

    private static final String DEFAULT_ETC = "AAAAAAAAAA";
    private static final String UPDATED_ETC = "BBBBBBBBBB";

    private static final String DEFAULT_EAC = "AAAAAAAAAA";
    private static final String UPDATED_EAC = "BBBBBBBBBB";

    private static final String DEFAULT_CV = "AAAAAAAAAA";
    private static final String UPDATED_CV = "BBBBBBBBBB";

    private static final String DEFAULT_VC = "AAAAAAAAAA";
    private static final String UPDATED_VC = "BBBBBBBBBB";

    private static final String DEFAULT_EV = "AAAAAAAAAA";
    private static final String UPDATED_EV = "BBBBBBBBBB";

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private SprintMapper sprintMapper;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSprintMockMvc;

    private Sprint sprint;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SprintResource sprintResource = new SprintResource(sprintService);
        this.restSprintMockMvc = MockMvcBuilders.standaloneSetup(sprintResource)
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
    public static Sprint createEntity(EntityManager em) {
        Sprint sprint = new Sprint()
            .numero(DEFAULT_NUMERO)
            .status(DEFAULT_STATUS)
            .puntosPlaneados(DEFAULT_PUNTOS_PLANEADOS)
            .puntosRealizados(DEFAULT_PUNTOS_REALIZADOS)
            .spi(DEFAULT_SPI)
            .cpi(DEFAULT_CPI)
            .sv(DEFAULT_SV)
            .vac(DEFAULT_VAC)
            .pe(DEFAULT_PE)
            .bac(DEFAULT_BAC)
            .pv(DEFAULT_PV)
            .ac(DEFAULT_AC)
            .etc(DEFAULT_ETC)
            .eac(DEFAULT_EAC)
            .cv(DEFAULT_CV)
            .vc(DEFAULT_VC)
            .ev(DEFAULT_EV)
            .comentario(DEFAULT_COMENTARIO);
        return sprint;
    }

    @Before
    public void initTest() {
        sprint = createEntity(em);
    }

    @Test
    @Transactional
    public void createSprint() throws Exception {
        int databaseSizeBeforeCreate = sprintRepository.findAll().size();

        // Create the Sprint
        SprintDTO sprintDTO = sprintMapper.toDto(sprint);
        restSprintMockMvc.perform(post("/api/sprints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sprintDTO)))
            .andExpect(status().isCreated());

        // Validate the Sprint in the database
        List<Sprint> sprintList = sprintRepository.findAll();
        assertThat(sprintList).hasSize(databaseSizeBeforeCreate + 1);
        Sprint testSprint = sprintList.get(sprintList.size() - 1);
        assertThat(testSprint.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testSprint.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSprint.getPuntosPlaneados()).isEqualTo(DEFAULT_PUNTOS_PLANEADOS);
        assertThat(testSprint.getPuntosRealizados()).isEqualTo(DEFAULT_PUNTOS_REALIZADOS);
        assertThat(testSprint.getSpi()).isEqualTo(DEFAULT_SPI);
        assertThat(testSprint.getCpi()).isEqualTo(DEFAULT_CPI);
        assertThat(testSprint.getSv()).isEqualTo(DEFAULT_SV);
        assertThat(testSprint.getVac()).isEqualTo(DEFAULT_VAC);
        assertThat(testSprint.getPe()).isEqualTo(DEFAULT_PE);
        assertThat(testSprint.getBac()).isEqualTo(DEFAULT_BAC);
        assertThat(testSprint.getPv()).isEqualTo(DEFAULT_PV);
        assertThat(testSprint.getAc()).isEqualTo(DEFAULT_AC);
        assertThat(testSprint.getEtc()).isEqualTo(DEFAULT_ETC);
        assertThat(testSprint.getEac()).isEqualTo(DEFAULT_EAC);
        assertThat(testSprint.getCv()).isEqualTo(DEFAULT_CV);
        assertThat(testSprint.getVc()).isEqualTo(DEFAULT_VC);
        assertThat(testSprint.getEv()).isEqualTo(DEFAULT_EV);
        assertThat(testSprint.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
    }

    @Test
    @Transactional
    public void createSprintWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sprintRepository.findAll().size();

        // Create the Sprint with an existing ID
        sprint.setId(1L);
        SprintDTO sprintDTO = sprintMapper.toDto(sprint);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSprintMockMvc.perform(post("/api/sprints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sprintDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Sprint> sprintList = sprintRepository.findAll();
        assertThat(sprintList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSprints() throws Exception {
        // Initialize the database
        sprintRepository.saveAndFlush(sprint);

        // Get all the sprintList
        restSprintMockMvc.perform(get("/api/sprints?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sprint.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].puntosPlaneados").value(hasItem(DEFAULT_PUNTOS_PLANEADOS)))
            .andExpect(jsonPath("$.[*].puntosRealizados").value(hasItem(DEFAULT_PUNTOS_REALIZADOS)))
            .andExpect(jsonPath("$.[*].spi").value(hasItem(DEFAULT_SPI.toString())))
            .andExpect(jsonPath("$.[*].cpi").value(hasItem(DEFAULT_CPI.toString())))
            .andExpect(jsonPath("$.[*].sv").value(hasItem(DEFAULT_SV.toString())))
            .andExpect(jsonPath("$.[*].vac").value(hasItem(DEFAULT_VAC.toString())))
            .andExpect(jsonPath("$.[*].pe").value(hasItem(DEFAULT_PE.toString())))
            .andExpect(jsonPath("$.[*].bac").value(hasItem(DEFAULT_BAC.toString())))
            .andExpect(jsonPath("$.[*].pv").value(hasItem(DEFAULT_PV.toString())))
            .andExpect(jsonPath("$.[*].ac").value(hasItem(DEFAULT_AC.toString())))
            .andExpect(jsonPath("$.[*].etc").value(hasItem(DEFAULT_ETC.toString())))
            .andExpect(jsonPath("$.[*].eac").value(hasItem(DEFAULT_EAC.toString())))
            .andExpect(jsonPath("$.[*].cv").value(hasItem(DEFAULT_CV.toString())))
            .andExpect(jsonPath("$.[*].vc").value(hasItem(DEFAULT_VC.toString())))
            .andExpect(jsonPath("$.[*].ev").value(hasItem(DEFAULT_EV.toString())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())));
    }

    @Test
    @Transactional
    public void getSprint() throws Exception {
        // Initialize the database
        sprintRepository.saveAndFlush(sprint);

        // Get the sprint
        restSprintMockMvc.perform(get("/api/sprints/{id}", sprint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sprint.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.puntosPlaneados").value(DEFAULT_PUNTOS_PLANEADOS))
            .andExpect(jsonPath("$.puntosRealizados").value(DEFAULT_PUNTOS_REALIZADOS))
            .andExpect(jsonPath("$.spi").value(DEFAULT_SPI.toString()))
            .andExpect(jsonPath("$.cpi").value(DEFAULT_CPI.toString()))
            .andExpect(jsonPath("$.sv").value(DEFAULT_SV.toString()))
            .andExpect(jsonPath("$.vac").value(DEFAULT_VAC.toString()))
            .andExpect(jsonPath("$.pe").value(DEFAULT_PE.toString()))
            .andExpect(jsonPath("$.bac").value(DEFAULT_BAC.toString()))
            .andExpect(jsonPath("$.pv").value(DEFAULT_PV.toString()))
            .andExpect(jsonPath("$.ac").value(DEFAULT_AC.toString()))
            .andExpect(jsonPath("$.etc").value(DEFAULT_ETC.toString()))
            .andExpect(jsonPath("$.eac").value(DEFAULT_EAC.toString()))
            .andExpect(jsonPath("$.cv").value(DEFAULT_CV.toString()))
            .andExpect(jsonPath("$.vc").value(DEFAULT_VC.toString()))
            .andExpect(jsonPath("$.ev").value(DEFAULT_EV.toString()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSprint() throws Exception {
        // Get the sprint
        restSprintMockMvc.perform(get("/api/sprints/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSprint() throws Exception {
        // Initialize the database
        sprintRepository.saveAndFlush(sprint);
        int databaseSizeBeforeUpdate = sprintRepository.findAll().size();

        // Update the sprint
        Sprint updatedSprint = sprintRepository.findOne(sprint.getId());
        updatedSprint
            .numero(UPDATED_NUMERO)
            .status(UPDATED_STATUS)
            .puntosPlaneados(UPDATED_PUNTOS_PLANEADOS)
            .puntosRealizados(UPDATED_PUNTOS_REALIZADOS)
            .spi(UPDATED_SPI)
            .cpi(UPDATED_CPI)
            .sv(UPDATED_SV)
            .vac(UPDATED_VAC)
            .pe(UPDATED_PE)
            .bac(UPDATED_BAC)
            .pv(UPDATED_PV)
            .ac(UPDATED_AC)
            .etc(UPDATED_ETC)
            .eac(UPDATED_EAC)
            .cv(UPDATED_CV)
            .vc(UPDATED_VC)
            .ev(UPDATED_EV)
            .comentario(UPDATED_COMENTARIO);
        SprintDTO sprintDTO = sprintMapper.toDto(updatedSprint);

        restSprintMockMvc.perform(put("/api/sprints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sprintDTO)))
            .andExpect(status().isOk());

        // Validate the Sprint in the database
        List<Sprint> sprintList = sprintRepository.findAll();
        assertThat(sprintList).hasSize(databaseSizeBeforeUpdate);
        Sprint testSprint = sprintList.get(sprintList.size() - 1);
        assertThat(testSprint.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testSprint.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSprint.getPuntosPlaneados()).isEqualTo(UPDATED_PUNTOS_PLANEADOS);
        assertThat(testSprint.getPuntosRealizados()).isEqualTo(UPDATED_PUNTOS_REALIZADOS);
        assertThat(testSprint.getSpi()).isEqualTo(UPDATED_SPI);
        assertThat(testSprint.getCpi()).isEqualTo(UPDATED_CPI);
        assertThat(testSprint.getSv()).isEqualTo(UPDATED_SV);
        assertThat(testSprint.getVac()).isEqualTo(UPDATED_VAC);
        assertThat(testSprint.getPe()).isEqualTo(UPDATED_PE);
        assertThat(testSprint.getBac()).isEqualTo(UPDATED_BAC);
        assertThat(testSprint.getPv()).isEqualTo(UPDATED_PV);
        assertThat(testSprint.getAc()).isEqualTo(UPDATED_AC);
        assertThat(testSprint.getEtc()).isEqualTo(UPDATED_ETC);
        assertThat(testSprint.getEac()).isEqualTo(UPDATED_EAC);
        assertThat(testSprint.getCv()).isEqualTo(UPDATED_CV);
        assertThat(testSprint.getVc()).isEqualTo(UPDATED_VC);
        assertThat(testSprint.getEv()).isEqualTo(UPDATED_EV);
        assertThat(testSprint.getComentario()).isEqualTo(UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingSprint() throws Exception {
        int databaseSizeBeforeUpdate = sprintRepository.findAll().size();

        // Create the Sprint
        SprintDTO sprintDTO = sprintMapper.toDto(sprint);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSprintMockMvc.perform(put("/api/sprints")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sprintDTO)))
            .andExpect(status().isCreated());

        // Validate the Sprint in the database
        List<Sprint> sprintList = sprintRepository.findAll();
        assertThat(sprintList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSprint() throws Exception {
        // Initialize the database
        sprintRepository.saveAndFlush(sprint);
        int databaseSizeBeforeDelete = sprintRepository.findAll().size();

        // Get the sprint
        restSprintMockMvc.perform(delete("/api/sprints/{id}", sprint.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sprint> sprintList = sprintRepository.findAll();
        assertThat(sprintList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sprint.class);
        Sprint sprint1 = new Sprint();
        sprint1.setId(1L);
        Sprint sprint2 = new Sprint();
        sprint2.setId(sprint1.getId());
        assertThat(sprint1).isEqualTo(sprint2);
        sprint2.setId(2L);
        assertThat(sprint1).isNotEqualTo(sprint2);
        sprint1.setId(null);
        assertThat(sprint1).isNotEqualTo(sprint2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SprintDTO.class);
        SprintDTO sprintDTO1 = new SprintDTO();
        sprintDTO1.setId(1L);
        SprintDTO sprintDTO2 = new SprintDTO();
        assertThat(sprintDTO1).isNotEqualTo(sprintDTO2);
        sprintDTO2.setId(sprintDTO1.getId());
        assertThat(sprintDTO1).isEqualTo(sprintDTO2);
        sprintDTO2.setId(2L);
        assertThat(sprintDTO1).isNotEqualTo(sprintDTO2);
        sprintDTO1.setId(null);
        assertThat(sprintDTO1).isNotEqualTo(sprintDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sprintMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sprintMapper.fromId(null)).isNull();
    }
}
