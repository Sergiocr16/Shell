package com.lighthouse.shell.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lighthouse.shell.service.ProbabilidadService;
import com.lighthouse.shell.web.rest.util.HeaderUtil;
import com.lighthouse.shell.web.rest.util.PaginationUtil;
import com.lighthouse.shell.service.dto.ProbabilidadDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Probabilidad.
 */
@RestController
@RequestMapping("/api")
public class ProbabilidadResource {

    private final Logger log = LoggerFactory.getLogger(ProbabilidadResource.class);

    private static final String ENTITY_NAME = "probabilidad";

    private final ProbabilidadService probabilidadService;

    public ProbabilidadResource(ProbabilidadService probabilidadService) {
        this.probabilidadService = probabilidadService;
    }

    /**
     * POST  /probabilidads : Create a new probabilidad.
     *
     * @param probabilidadDTO the probabilidadDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new probabilidadDTO, or with status 400 (Bad Request) if the probabilidad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/probabilidads")
    @Timed
    public ResponseEntity<ProbabilidadDTO> createProbabilidad(@RequestBody ProbabilidadDTO probabilidadDTO) throws URISyntaxException {
        log.debug("REST request to save Probabilidad : {}", probabilidadDTO);
        if (probabilidadDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new probabilidad cannot already have an ID")).body(null);
        }
        ProbabilidadDTO result = probabilidadService.save(probabilidadDTO);
        return ResponseEntity.created(new URI("/api/probabilidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /probabilidads : Updates an existing probabilidad.
     *
     * @param probabilidadDTO the probabilidadDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated probabilidadDTO,
     * or with status 400 (Bad Request) if the probabilidadDTO is not valid,
     * or with status 500 (Internal Server Error) if the probabilidadDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/probabilidads")
    @Timed
    public ResponseEntity<ProbabilidadDTO> updateProbabilidad(@RequestBody ProbabilidadDTO probabilidadDTO) throws URISyntaxException {
        log.debug("REST request to update Probabilidad : {}", probabilidadDTO);
        if (probabilidadDTO.getId() == null) {
            return createProbabilidad(probabilidadDTO);
        }
        ProbabilidadDTO result = probabilidadService.save(probabilidadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, probabilidadDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /probabilidads : get all the probabilidads.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of probabilidads in body
     */
    @GetMapping("/probabilidads")
    @Timed
    public ResponseEntity<List<ProbabilidadDTO>> getAllProbabilidads(@ApiParam Pageable pageable, Long tablaProbabilidadId) {
        log.debug("REST request to get a page of Probabilidads");
        Page<ProbabilidadDTO> page = probabilidadService.findAll(pageable, tablaProbabilidadId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/probabilidads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /probabilidads/:id : get the "id" probabilidad.
     *
     * @param id the id of the probabilidadDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the probabilidadDTO, or with status 404 (Not Found)
     */
    @GetMapping("/probabilidads/{id}")
    @Timed
    public ResponseEntity<ProbabilidadDTO> getProbabilidad(@PathVariable Long id) {
        log.debug("REST request to get Probabilidad : {}", id);
        ProbabilidadDTO probabilidadDTO = probabilidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(probabilidadDTO));
    }

    /**
     * DELETE  /probabilidads/:id : delete the "id" probabilidad.
     *
     * @param id the id of the probabilidadDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/probabilidads/{id}")
    @Timed
    public ResponseEntity<Void> deleteProbabilidad(@PathVariable Long id) {
        log.debug("REST request to delete Probabilidad : {}", id);
        probabilidadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
