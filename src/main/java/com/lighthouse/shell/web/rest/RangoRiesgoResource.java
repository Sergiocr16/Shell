package com.lighthouse.shell.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lighthouse.shell.service.RangoRiesgoService;
import com.lighthouse.shell.web.rest.util.HeaderUtil;
import com.lighthouse.shell.web.rest.util.PaginationUtil;
import com.lighthouse.shell.service.dto.RangoRiesgoDTO;
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
 * REST controller for managing RangoRiesgo.
 */
@RestController
@RequestMapping("/api")
public class RangoRiesgoResource {

    private final Logger log = LoggerFactory.getLogger(RangoRiesgoResource.class);

    private static final String ENTITY_NAME = "rangoRiesgo";

    private final RangoRiesgoService rangoRiesgoService;

    public RangoRiesgoResource(RangoRiesgoService rangoRiesgoService) {
        this.rangoRiesgoService = rangoRiesgoService;
    }

    /**
     * POST  /rango-riesgos : Create a new rangoRiesgo.
     *
     * @param rangoRiesgoDTO the rangoRiesgoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rangoRiesgoDTO, or with status 400 (Bad Request) if the rangoRiesgo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rango-riesgos")
    @Timed
    public ResponseEntity<RangoRiesgoDTO> createRangoRiesgo(@RequestBody RangoRiesgoDTO rangoRiesgoDTO) throws URISyntaxException {
        log.debug("REST request to save RangoRiesgo : {}", rangoRiesgoDTO);
        if (rangoRiesgoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rangoRiesgo cannot already have an ID")).body(null);
        }
        RangoRiesgoDTO result = rangoRiesgoService.save(rangoRiesgoDTO);
        return ResponseEntity.created(new URI("/api/rango-riesgos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rango-riesgos : Updates an existing rangoRiesgo.
     *
     * @param rangoRiesgoDTO the rangoRiesgoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rangoRiesgoDTO,
     * or with status 400 (Bad Request) if the rangoRiesgoDTO is not valid,
     * or with status 500 (Internal Server Error) if the rangoRiesgoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rango-riesgos")
    @Timed
    public ResponseEntity<RangoRiesgoDTO> updateRangoRiesgo(@RequestBody RangoRiesgoDTO rangoRiesgoDTO) throws URISyntaxException {
        log.debug("REST request to update RangoRiesgo : {}", rangoRiesgoDTO);
        if (rangoRiesgoDTO.getId() == null) {
            return createRangoRiesgo(rangoRiesgoDTO);
        }
        RangoRiesgoDTO result = rangoRiesgoService.save(rangoRiesgoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rangoRiesgoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rango-riesgos : get all the rangoRiesgos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rangoRiesgos in body
     */
    @GetMapping("/rango-riesgos")
    @Timed
    public ResponseEntity<List<RangoRiesgoDTO>> getAllRangoRiesgos(Long proyectoId) {
        log.debug("REST request to get a page of RangoRiesgos");
        Page<RangoRiesgoDTO> page = rangoRiesgoService.findAll(proyectoId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rango-riesgos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rango-riesgos/:id : get the "id" rangoRiesgo.
     *
     * @param id the id of the rangoRiesgoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rangoRiesgoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rango-riesgos/{id}")
    @Timed
    public ResponseEntity<RangoRiesgoDTO> getRangoRiesgo(@PathVariable Long id) {
        log.debug("REST request to get RangoRiesgo : {}", id);
        RangoRiesgoDTO rangoRiesgoDTO = rangoRiesgoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rangoRiesgoDTO));
    }

    /**
     * DELETE  /rango-riesgos/:id : delete the "id" rangoRiesgo.
     *
     * @param id the id of the rangoRiesgoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rango-riesgos/{id}")
    @Timed
    public ResponseEntity<Void> deleteRangoRiesgo(@PathVariable Long id) {
        log.debug("REST request to delete RangoRiesgo : {}", id);
        rangoRiesgoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
