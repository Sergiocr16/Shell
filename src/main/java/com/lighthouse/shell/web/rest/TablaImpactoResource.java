package com.lighthouse.shell.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lighthouse.shell.service.TablaImpactoService;
import com.lighthouse.shell.web.rest.util.HeaderUtil;
import com.lighthouse.shell.web.rest.util.PaginationUtil;
import com.lighthouse.shell.service.dto.TablaImpactoDTO;
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
 * REST controller for managing TablaImpacto.
 */
@RestController
@RequestMapping("/api")
public class TablaImpactoResource {

    private final Logger log = LoggerFactory.getLogger(TablaImpactoResource.class);

    private static final String ENTITY_NAME = "tablaImpacto";

    private final TablaImpactoService tablaImpactoService;

    public TablaImpactoResource(TablaImpactoService tablaImpactoService) {
        this.tablaImpactoService = tablaImpactoService;
    }

    /**
     * POST  /tabla-impactos : Create a new tablaImpacto.
     *
     * @param tablaImpactoDTO the tablaImpactoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tablaImpactoDTO, or with status 400 (Bad Request) if the tablaImpacto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tabla-impactos")
    @Timed
    public ResponseEntity<TablaImpactoDTO> createTablaImpacto(@RequestBody TablaImpactoDTO tablaImpactoDTO) throws URISyntaxException {
        log.debug("REST request to save TablaImpacto : {}", tablaImpactoDTO);
        if (tablaImpactoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tablaImpacto cannot already have an ID")).body(null);
        }
        TablaImpactoDTO result = tablaImpactoService.save(tablaImpactoDTO);
        return ResponseEntity.created(new URI("/api/tabla-impactos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tabla-impactos : Updates an existing tablaImpacto.
     *
     * @param tablaImpactoDTO the tablaImpactoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tablaImpactoDTO,
     * or with status 400 (Bad Request) if the tablaImpactoDTO is not valid,
     * or with status 500 (Internal Server Error) if the tablaImpactoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tabla-impactos")
    @Timed
    public ResponseEntity<TablaImpactoDTO> updateTablaImpacto(@RequestBody TablaImpactoDTO tablaImpactoDTO) throws URISyntaxException {
        log.debug("REST request to update TablaImpacto : {}", tablaImpactoDTO);
        if (tablaImpactoDTO.getId() == null) {
            return createTablaImpacto(tablaImpactoDTO);
        }
        TablaImpactoDTO result = tablaImpactoService.save(tablaImpactoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tablaImpactoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tabla-impactos : get all the tablaImpactos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tablaImpactos in body
     */
    @GetMapping("/tabla-impactos")
    @Timed
    public ResponseEntity<List<TablaImpactoDTO>> getAllTablaImpactos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TablaImpactos");
        Page<TablaImpactoDTO> page = tablaImpactoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tabla-impactos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tabla-impactos/:id : get the "id" tablaImpacto.
     *
     * @param id the id of the tablaImpactoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tablaImpactoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tabla-impactos/{id}")
    @Timed
    public ResponseEntity<TablaImpactoDTO> getTablaImpacto(@PathVariable Long id) {
        log.debug("REST request to get TablaImpacto : {}", id);
        TablaImpactoDTO tablaImpactoDTO = tablaImpactoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tablaImpactoDTO));
    }

    /**
     * DELETE  /tabla-impactos/:id : delete the "id" tablaImpacto.
     *
     * @param id the id of the tablaImpactoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tabla-impactos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTablaImpacto(@PathVariable Long id) {
        log.debug("REST request to delete TablaImpacto : {}", id);
        tablaImpactoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
