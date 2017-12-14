package com.lighthouse.shell.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lighthouse.shell.service.LanzamientoService;
import com.lighthouse.shell.web.rest.util.HeaderUtil;
import com.lighthouse.shell.web.rest.util.PaginationUtil;
import com.lighthouse.shell.service.dto.LanzamientoDTO;
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
 * REST controller for managing Lanzamiento.
 */
@RestController
@RequestMapping("/api")
public class LanzamientoResource {

    private final Logger log = LoggerFactory.getLogger(LanzamientoResource.class);

    private static final String ENTITY_NAME = "lanzamiento";

    private final LanzamientoService lanzamientoService;

    public LanzamientoResource(LanzamientoService lanzamientoService) {
        this.lanzamientoService = lanzamientoService;
    }

    /**
     * POST  /lanzamientos : Create a new lanzamiento.
     *
     * @param lanzamientoDTO the lanzamientoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lanzamientoDTO, or with status 400 (Bad Request) if the lanzamiento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lanzamientos")
    @Timed
    public ResponseEntity<LanzamientoDTO> createLanzamiento(@RequestBody LanzamientoDTO lanzamientoDTO) throws URISyntaxException {
        log.debug("REST request to save Lanzamiento : {}", lanzamientoDTO);
        if (lanzamientoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new lanzamiento cannot already have an ID")).body(null);
        }
        LanzamientoDTO result = lanzamientoService.save(lanzamientoDTO);
        return ResponseEntity.created(new URI("/api/lanzamientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lanzamientos : Updates an existing lanzamiento.
     *
     * @param lanzamientoDTO the lanzamientoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lanzamientoDTO,
     * or with status 400 (Bad Request) if the lanzamientoDTO is not valid,
     * or with status 500 (Internal Server Error) if the lanzamientoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lanzamientos")
    @Timed
    public ResponseEntity<LanzamientoDTO> updateLanzamiento(@RequestBody LanzamientoDTO lanzamientoDTO) throws URISyntaxException {
        log.debug("REST request to update Lanzamiento : {}", lanzamientoDTO);
        if (lanzamientoDTO.getId() == null) {
            return createLanzamiento(lanzamientoDTO);
        }
        LanzamientoDTO result = lanzamientoService.save(lanzamientoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lanzamientoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lanzamientos : get all the lanzamientos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lanzamientos in body
     */
    @GetMapping("/lanzamientos")
    @Timed
    public ResponseEntity<List<LanzamientoDTO>> getAllLanzamientos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Lanzamientos");
        Page<LanzamientoDTO> page = lanzamientoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lanzamientos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /lanzamientos/:id : get the "id" lanzamiento.
     *
     * @param id the id of the lanzamientoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lanzamientoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lanzamientos/{id}")
    @Timed
    public ResponseEntity<LanzamientoDTO> getLanzamiento(@PathVariable Long id) {
        log.debug("REST request to get Lanzamiento : {}", id);
        LanzamientoDTO lanzamientoDTO = lanzamientoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lanzamientoDTO));
    }

    /**
     * DELETE  /lanzamientos/:id : delete the "id" lanzamiento.
     *
     * @param id the id of the lanzamientoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lanzamientos/{id}")
    @Timed
    public ResponseEntity<Void> deleteLanzamiento(@PathVariable Long id) {
        log.debug("REST request to delete Lanzamiento : {}", id);
        lanzamientoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
