package com.lighthouse.shell.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lighthouse.shell.service.ProyectoService;
import com.lighthouse.shell.web.rest.util.HeaderUtil;
import com.lighthouse.shell.web.rest.util.PaginationUtil;
import com.lighthouse.shell.service.dto.ProyectoDTO;
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
 * REST controller for managing Proyecto.
 */
@RestController
@RequestMapping("/api")
public class ProyectoResource {

    private final Logger log = LoggerFactory.getLogger(ProyectoResource.class);

    private static final String ENTITY_NAME = "proyecto";

    private final ProyectoService proyectoService;

    public ProyectoResource(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    /**
     * POST  /proyectos : Create a new proyecto.
     *
     * @param proyectoDTO the proyectoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new proyectoDTO, or with status 400 (Bad Request) if the proyecto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/proyectos")
    @Timed
    public ResponseEntity<ProyectoDTO> createProyecto(@RequestBody ProyectoDTO proyectoDTO) throws URISyntaxException {
        log.debug("REST request to save Proyecto : {}", proyectoDTO);
        if (proyectoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new proyecto cannot already have an ID")).body(null);
        }
        ProyectoDTO result = proyectoService.save(proyectoDTO);
        return ResponseEntity.created(new URI("/api/proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proyectos : Updates an existing proyecto.
     *
     * @param proyectoDTO the proyectoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated proyectoDTO,
     * or with status 400 (Bad Request) if the proyectoDTO is not valid,
     * or with status 500 (Internal Server Error) if the proyectoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/proyectos")
    @Timed
    public ResponseEntity<ProyectoDTO> updateProyecto(@RequestBody ProyectoDTO proyectoDTO) throws URISyntaxException {
        log.debug("REST request to update Proyecto : {}", proyectoDTO);
        if (proyectoDTO.getId() == null) {
            return createProyecto(proyectoDTO);
        }
        ProyectoDTO result = proyectoService.save(proyectoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, proyectoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proyectos : get all the proyectos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of proyectos in body
     */
    @GetMapping("/proyectos")
    @Timed
    public ResponseEntity<List<ProyectoDTO>> getAllProyectos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Proyectos");
        Page<ProyectoDTO> page = proyectoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proyectos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /proyectos/:id : get the "id" proyecto.
     *
     * @param id the id of the proyectoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the proyectoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/proyectos/{id}")
    @Timed
    public ResponseEntity<ProyectoDTO> getProyecto(@PathVariable Long id) {
        log.debug("REST request to get Proyecto : {}", id);
        ProyectoDTO proyectoDTO = proyectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(proyectoDTO));
    }

    /**
     * DELETE  /proyectos/:id : delete the "id" proyecto.
     *
     * @param id the id of the proyectoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/proyectos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProyecto(@PathVariable Long id) {
        log.debug("REST request to delete Proyecto : {}", id);
        proyectoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
