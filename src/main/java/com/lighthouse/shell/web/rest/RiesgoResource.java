package com.lighthouse.shell.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lighthouse.shell.service.RiesgoService;
import com.lighthouse.shell.web.rest.util.HeaderUtil;
import com.lighthouse.shell.web.rest.util.PaginationUtil;
import com.lighthouse.shell.service.dto.RiesgoDTO;
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
 * REST controller for managing Riesgo.
 */
@RestController
@RequestMapping("/api")
public class RiesgoResource {

    private final Logger log = LoggerFactory.getLogger(RiesgoResource.class);

    private static final String ENTITY_NAME = "riesgo";

    private final RiesgoService riesgoService;

    public RiesgoResource(RiesgoService riesgoService) {
        this.riesgoService = riesgoService;
    }

    /**
     * POST  /riesgos : Create a new riesgo.
     *
     * @param riesgoDTO the riesgoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new riesgoDTO, or with status 400 (Bad Request) if the riesgo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/riesgos")
    @Timed
    public ResponseEntity<RiesgoDTO> createRiesgo(@RequestBody RiesgoDTO riesgoDTO) throws URISyntaxException {
        log.debug("REST request to save Riesgo : {}", riesgoDTO);
        if (riesgoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new riesgo cannot already have an ID")).body(null);
        }
        RiesgoDTO result = riesgoService.save(riesgoDTO);
        return ResponseEntity.created(new URI("/api/riesgos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /riesgos : Updates an existing riesgo.
     *
     * @param riesgoDTO the riesgoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated riesgoDTO,
     * or with status 400 (Bad Request) if the riesgoDTO is not valid,
     * or with status 500 (Internal Server Error) if the riesgoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/riesgos")
    @Timed
    public ResponseEntity<RiesgoDTO> updateRiesgo(@RequestBody RiesgoDTO riesgoDTO) throws URISyntaxException {
        log.debug("REST request to update Riesgo : {}", riesgoDTO);
        if (riesgoDTO.getId() == null) {
            return createRiesgo(riesgoDTO);
        }
        RiesgoDTO result = riesgoService.save(riesgoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, riesgoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /riesgos : get all the riesgos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of riesgos in body
     */
    @GetMapping("/riesgos")
    @Timed
    public ResponseEntity<List<RiesgoDTO>> getAllRiesgos(@ApiParam Pageable pageable, Long proyectoId) {
        log.debug("REST request to get a page of Riesgos");
        Page<RiesgoDTO> page = riesgoService.findAll(pageable, proyectoId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/riesgos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /riesgos/:id : get the "id" riesgo.
     *
     * @param id the id of the riesgoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the riesgoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/riesgos/{id}")
    @Timed
    public ResponseEntity<RiesgoDTO> getRiesgo(@PathVariable Long id) {
        log.debug("REST request to get Riesgo : {}", id);
        RiesgoDTO riesgoDTO = riesgoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(riesgoDTO));
    }

    /**
     * DELETE  /riesgos/:id : delete the "id" riesgo.
     *
     * @param id the id of the riesgoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/riesgos/{id}")
    @Timed
    public ResponseEntity<Void> deleteRiesgo(@PathVariable Long id) {
        log.debug("REST request to delete Riesgo : {}", id);
        riesgoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
