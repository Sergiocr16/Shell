package com.lighthouse.shell.service;

import com.lighthouse.shell.domain.Probabilidad;
import com.lighthouse.shell.repository.ProbabilidadRepository;
import com.lighthouse.shell.service.dto.ProbabilidadDTO;
import com.lighthouse.shell.service.mapper.ProbabilidadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Probabilidad.
 */
@Service
@Transactional
public class ProbabilidadService {

    private final Logger log = LoggerFactory.getLogger(ProbabilidadService.class);

    private final ProbabilidadRepository probabilidadRepository;

    private final ProbabilidadMapper probabilidadMapper;

    public ProbabilidadService(ProbabilidadRepository probabilidadRepository, ProbabilidadMapper probabilidadMapper) {
        this.probabilidadRepository = probabilidadRepository;
        this.probabilidadMapper = probabilidadMapper;
    }

    /**
     * Save a probabilidad.
     *
     * @param probabilidadDTO the entity to save
     * @return the persisted entity
     */
    public ProbabilidadDTO save(ProbabilidadDTO probabilidadDTO) {
        log.debug("Request to save Probabilidad : {}", probabilidadDTO);
        Probabilidad probabilidad = probabilidadMapper.toEntity(probabilidadDTO);
        probabilidad = probabilidadRepository.save(probabilidad);
        return probabilidadMapper.toDto(probabilidad);
    }

    /**
     *  Get all the probabilidads.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProbabilidadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Probabilidads");
        return probabilidadRepository.findAll(pageable)
            .map(probabilidadMapper::toDto);
    }

    /**
     *  Get one probabilidad by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ProbabilidadDTO findOne(Long id) {
        log.debug("Request to get Probabilidad : {}", id);
        Probabilidad probabilidad = probabilidadRepository.findOne(id);
        return probabilidadMapper.toDto(probabilidad);
    }

    /**
     *  Delete the  probabilidad by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Probabilidad : {}", id);
        probabilidadRepository.delete(id);
    }
}
