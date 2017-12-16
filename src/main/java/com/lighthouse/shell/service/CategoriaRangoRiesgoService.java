package com.lighthouse.shell.service;

import com.lighthouse.shell.domain.CategoriaRangoRiesgo;
import com.lighthouse.shell.repository.CategoriaRangoRiesgoRepository;
import com.lighthouse.shell.service.dto.CategoriaRangoRiesgoDTO;
import com.lighthouse.shell.service.mapper.CategoriaRangoRiesgoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CategoriaRangoRiesgo.
 */
@Service
@Transactional
public class CategoriaRangoRiesgoService {

    private final Logger log = LoggerFactory.getLogger(CategoriaRangoRiesgoService.class);

    private final CategoriaRangoRiesgoRepository categoriaRangoRiesgoRepository;

    private final CategoriaRangoRiesgoMapper categoriaRangoRiesgoMapper;

    public CategoriaRangoRiesgoService(CategoriaRangoRiesgoRepository categoriaRangoRiesgoRepository, CategoriaRangoRiesgoMapper categoriaRangoRiesgoMapper) {
        this.categoriaRangoRiesgoRepository = categoriaRangoRiesgoRepository;
        this.categoriaRangoRiesgoMapper = categoriaRangoRiesgoMapper;
    }

    /**
     * Save a categoriaRangoRiesgo.
     *
     * @param categoriaRangoRiesgoDTO the entity to save
     * @return the persisted entity
     */
    public CategoriaRangoRiesgoDTO save(CategoriaRangoRiesgoDTO categoriaRangoRiesgoDTO) {
        log.debug("Request to save CategoriaRangoRiesgo : {}", categoriaRangoRiesgoDTO);
        CategoriaRangoRiesgo categoriaRangoRiesgo = categoriaRangoRiesgoMapper.toEntity(categoriaRangoRiesgoDTO);
        categoriaRangoRiesgo = categoriaRangoRiesgoRepository.save(categoriaRangoRiesgo);
        return categoriaRangoRiesgoMapper.toDto(categoriaRangoRiesgo);
    }

    /**
     *  Get all the categoriaRangoRiesgos.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CategoriaRangoRiesgoDTO> findAll() {
        log.debug("Request to get all CategoriaRangoRiesgos");
        return categoriaRangoRiesgoRepository.findAll().stream()
            .map(categoriaRangoRiesgoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one categoriaRangoRiesgo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CategoriaRangoRiesgoDTO findOne(Long id) {
        log.debug("Request to get CategoriaRangoRiesgo : {}", id);
        CategoriaRangoRiesgo categoriaRangoRiesgo = categoriaRangoRiesgoRepository.findOne(id);
        return categoriaRangoRiesgoMapper.toDto(categoriaRangoRiesgo);
    }

    /**
     *  Delete the  categoriaRangoRiesgo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CategoriaRangoRiesgo : {}", id);
        categoriaRangoRiesgoRepository.delete(id);
    }
}
