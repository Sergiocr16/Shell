package com.lighthouse.shell.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lighthouse.shell.service.CategoriaRangoRiesgoService;
import com.lighthouse.shell.web.rest.util.HeaderUtil;
import com.lighthouse.shell.service.dto.CategoriaRangoRiesgoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CategoriaRangoRiesgo.
 */
@RestController
@RequestMapping("/api")
public class CategoriaRangoRiesgoResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaRangoRiesgoResource.class);

    private static final String ENTITY_NAME = "categoriaRangoRiesgo";

    private final CategoriaRangoRiesgoService categoriaRangoRiesgoService;

    public CategoriaRangoRiesgoResource(CategoriaRangoRiesgoService categoriaRangoRiesgoService) {
        this.categoriaRangoRiesgoService = categoriaRangoRiesgoService;
    }

    /**
     * POST  /categoria-rango-riesgos : Create a new categoriaRangoRiesgo.
     *
     * @param categoriaRangoRiesgoDTO the categoriaRangoRiesgoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categoriaRangoRiesgoDTO, or with status 400 (Bad Request) if the categoriaRangoRiesgo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/categoria-rango-riesgos")
    @Timed
    public ResponseEntity<CategoriaRangoRiesgoDTO> createCategoriaRangoRiesgo(@RequestBody CategoriaRangoRiesgoDTO categoriaRangoRiesgoDTO) throws URISyntaxException {
        log.debug("REST request to save CategoriaRangoRiesgo : {}", categoriaRangoRiesgoDTO);
        if (categoriaRangoRiesgoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new categoriaRangoRiesgo cannot already have an ID")).body(null);
        }
        CategoriaRangoRiesgoDTO result = categoriaRangoRiesgoService.save(categoriaRangoRiesgoDTO);
        return ResponseEntity.created(new URI("/api/categoria-rango-riesgos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /categoria-rango-riesgos : Updates an existing categoriaRangoRiesgo.
     *
     * @param categoriaRangoRiesgoDTO the categoriaRangoRiesgoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoriaRangoRiesgoDTO,
     * or with status 400 (Bad Request) if the categoriaRangoRiesgoDTO is not valid,
     * or with status 500 (Internal Server Error) if the categoriaRangoRiesgoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/categoria-rango-riesgos")
    @Timed
    public ResponseEntity<CategoriaRangoRiesgoDTO> updateCategoriaRangoRiesgo(@RequestBody CategoriaRangoRiesgoDTO categoriaRangoRiesgoDTO) throws URISyntaxException {
        log.debug("REST request to update CategoriaRangoRiesgo : {}", categoriaRangoRiesgoDTO);
        if (categoriaRangoRiesgoDTO.getId() == null) {
            return createCategoriaRangoRiesgo(categoriaRangoRiesgoDTO);
        }
        CategoriaRangoRiesgoDTO result = categoriaRangoRiesgoService.save(categoriaRangoRiesgoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categoriaRangoRiesgoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /categoria-rango-riesgos : get all the categoriaRangoRiesgos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categoriaRangoRiesgos in body
     */
    @GetMapping("/categoria-rango-riesgos")
    @Timed
    public List<CategoriaRangoRiesgoDTO> getAllCategoriaRangoRiesgos() {
        log.debug("REST request to get all CategoriaRangoRiesgos");
        return categoriaRangoRiesgoService.findAll();
    }

    /**
     * GET  /categoria-rango-riesgos/:id : get the "id" categoriaRangoRiesgo.
     *
     * @param id the id of the categoriaRangoRiesgoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categoriaRangoRiesgoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/categoria-rango-riesgos/{id}")
    @Timed
    public ResponseEntity<CategoriaRangoRiesgoDTO> getCategoriaRangoRiesgo(@PathVariable Long id) {
        log.debug("REST request to get CategoriaRangoRiesgo : {}", id);
        CategoriaRangoRiesgoDTO categoriaRangoRiesgoDTO = categoriaRangoRiesgoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(categoriaRangoRiesgoDTO));
    }

    /**
     * DELETE  /categoria-rango-riesgos/:id : delete the "id" categoriaRangoRiesgo.
     *
     * @param id the id of the categoriaRangoRiesgoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/categoria-rango-riesgos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCategoriaRangoRiesgo(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaRangoRiesgo : {}", id);
        categoriaRangoRiesgoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
