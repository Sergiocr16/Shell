package com.lighthouse.shell.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lighthouse.shell.service.TablaProbabilidadService;
import com.lighthouse.shell.web.rest.util.HeaderUtil;
import com.lighthouse.shell.web.rest.util.PaginationUtil;
import com.lighthouse.shell.service.dto.TablaProbabilidadDTO;
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
 * REST controller for managing TablaProbabilidad.
 */
@RestController
@RequestMapping("/api")
public class TablaProbabilidadResource {

    private final Logger log = LoggerFactory.getLogger(TablaProbabilidadResource.class);

    private static final String ENTITY_NAME = "tablaProbabilidad";

    private final TablaProbabilidadService tablaProbabilidadService;

    public TablaProbabilidadResource(TablaProbabilidadService tablaProbabilidadService) {
        this.tablaProbabilidadService = tablaProbabilidadService;
    }

    /**
     * POST  /tabla-probabilidads : Create a new tablaProbabilidad.
     *
     * @param tablaProbabilidadDTO the tablaProbabilidadDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tablaProbabilidadDTO, or with status 400 (Bad Request) if the tablaProbabilidad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tabla-probabilidads")
    @Timed
    public ResponseEntity<TablaProbabilidadDTO> createTablaProbabilidad(@RequestBody TablaProbabilidadDTO tablaProbabilidadDTO) throws URISyntaxException {
        log.debug("REST request to save TablaProbabilidad : {}", tablaProbabilidadDTO);
        if (tablaProbabilidadDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tablaProbabilidad cannot already have an ID")).body(null);
        }
        TablaProbabilidadDTO result = tablaProbabilidadService.save(tablaProbabilidadDTO);
        return ResponseEntity.created(new URI("/api/tabla-probabilidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tabla-probabilidads : Updates an existing tablaProbabilidad.
     *
     * @param tablaProbabilidadDTO the tablaProbabilidadDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tablaProbabilidadDTO,
     * or with status 400 (Bad Request) if the tablaProbabilidadDTO is not valid,
     * or with status 500 (Internal Server Error) if the tablaProbabilidadDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tabla-probabilidads")
    @Timed
    public ResponseEntity<TablaProbabilidadDTO> updateTablaProbabilidad(@RequestBody TablaProbabilidadDTO tablaProbabilidadDTO) throws URISyntaxException {
        log.debug("REST request to update TablaProbabilidad : {}", tablaProbabilidadDTO);
        if (tablaProbabilidadDTO.getId() == null) {
            return createTablaProbabilidad(tablaProbabilidadDTO);
        }
        TablaProbabilidadDTO result = tablaProbabilidadService.save(tablaProbabilidadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tablaProbabilidadDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tabla-probabilidads : get all the tablaProbabilidads.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tablaProbabilidads in body
     */
    @GetMapping("/tabla-probabilidads")
    @Timed
    public ResponseEntity<List<TablaProbabilidadDTO>> getAllTablaProbabilidads(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TablaProbabilidads");
        Page<TablaProbabilidadDTO> page = tablaProbabilidadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tabla-probabilidads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tabla-probabilidads/:id : get the "id" tablaProbabilidad.
     *
     * @param id the id of the tablaProbabilidadDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tablaProbabilidadDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tabla-probabilidads/{id}")
    @Timed
    public ResponseEntity<TablaProbabilidadDTO> getTablaProbabilidad(@PathVariable Long id) {
        log.debug("REST request to get TablaProbabilidad : {}", id);
        TablaProbabilidadDTO tablaProbabilidadDTO = tablaProbabilidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tablaProbabilidadDTO));
    }

    /**
     * DELETE  /tabla-probabilidads/:id : delete the "id" tablaProbabilidad.
     *
     * @param id the id of the tablaProbabilidadDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tabla-probabilidads/{id}")
    @Timed
    public ResponseEntity<Void> deleteTablaProbabilidad(@PathVariable Long id) {
        log.debug("REST request to delete TablaProbabilidad : {}", id);
        tablaProbabilidadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
