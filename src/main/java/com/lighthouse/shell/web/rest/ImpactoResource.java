package com.lighthouse.shell.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lighthouse.shell.service.ImpactoService;
import com.lighthouse.shell.web.rest.util.HeaderUtil;
import com.lighthouse.shell.web.rest.util.PaginationUtil;
import com.lighthouse.shell.service.dto.ImpactoDTO;
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
 * REST controller for managing Impacto.
 */
@RestController
@RequestMapping("/api")
public class ImpactoResource {

    private final Logger log = LoggerFactory.getLogger(ImpactoResource.class);

    private static final String ENTITY_NAME = "impacto";

    private final ImpactoService impactoService;

    public ImpactoResource(ImpactoService impactoService) {
        this.impactoService = impactoService;
    }

    /**
     * POST  /impactos : Create a new impacto.
     *
     * @param impactoDTO the impactoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new impactoDTO, or with status 400 (Bad Request) if the impacto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/impactos")
    @Timed
    public ResponseEntity<ImpactoDTO> createImpacto(@RequestBody ImpactoDTO impactoDTO) throws URISyntaxException {
        log.debug("REST request to save Impacto : {}", impactoDTO);
        if (impactoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new impacto cannot already have an ID")).body(null);
        }
        ImpactoDTO result = impactoService.save(impactoDTO);
        return ResponseEntity.created(new URI("/api/impactos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /impactos : Updates an existing impacto.
     *
     * @param impactoDTO the impactoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated impactoDTO,
     * or with status 400 (Bad Request) if the impactoDTO is not valid,
     * or with status 500 (Internal Server Error) if the impactoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/impactos")
    @Timed
    public ResponseEntity<ImpactoDTO> updateImpacto(@RequestBody ImpactoDTO impactoDTO) throws URISyntaxException {
        log.debug("REST request to update Impacto : {}", impactoDTO);
        if (impactoDTO.getId() == null) {
            return createImpacto(impactoDTO);
        }
        ImpactoDTO result = impactoService.save(impactoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, impactoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /impactos : get all the impactos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of impactos in body
     */
    @GetMapping("/impactos")
    @Timed
    public ResponseEntity<List<ImpactoDTO>> getAllImpactos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Impactos");
        Page<ImpactoDTO> page = impactoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/impactos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /impactos/:id : get the "id" impacto.
     *
     * @param id the id of the impactoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the impactoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/impactos/{id}")
    @Timed
    public ResponseEntity<ImpactoDTO> getImpacto(@PathVariable Long id) {
        log.debug("REST request to get Impacto : {}", id);
        ImpactoDTO impactoDTO = impactoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(impactoDTO));
    }

    /**
     * DELETE  /impactos/:id : delete the "id" impacto.
     *
     * @param id the id of the impactoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/impactos/{id}")
    @Timed
    public ResponseEntity<Void> deleteImpacto(@PathVariable Long id) {
        log.debug("REST request to delete Impacto : {}", id);
        impactoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
