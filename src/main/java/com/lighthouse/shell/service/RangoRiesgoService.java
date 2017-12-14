package com.lighthouse.shell.service;

import com.lighthouse.shell.domain.RangoRiesgo;
import com.lighthouse.shell.repository.RangoRiesgoRepository;
import com.lighthouse.shell.service.dto.RangoRiesgoDTO;
import com.lighthouse.shell.service.mapper.RangoRiesgoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RangoRiesgo.
 */
@Service
@Transactional
public class RangoRiesgoService {

    private final Logger log = LoggerFactory.getLogger(RangoRiesgoService.class);

    private final RangoRiesgoRepository rangoRiesgoRepository;

    private final RangoRiesgoMapper rangoRiesgoMapper;

    public RangoRiesgoService(RangoRiesgoRepository rangoRiesgoRepository, RangoRiesgoMapper rangoRiesgoMapper) {
        this.rangoRiesgoRepository = rangoRiesgoRepository;
        this.rangoRiesgoMapper = rangoRiesgoMapper;
    }

    /**
     * Save a rangoRiesgo.
     *
     * @param rangoRiesgoDTO the entity to save
     * @return the persisted entity
     */
    public RangoRiesgoDTO save(RangoRiesgoDTO rangoRiesgoDTO) {
        log.debug("Request to save RangoRiesgo : {}", rangoRiesgoDTO);
        RangoRiesgo rangoRiesgo = rangoRiesgoMapper.toEntity(rangoRiesgoDTO);
        rangoRiesgo = rangoRiesgoRepository.save(rangoRiesgo);
        return rangoRiesgoMapper.toDto(rangoRiesgo);
    }

    /**
     *  Get all the rangoRiesgos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RangoRiesgoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RangoRiesgos");
        return rangoRiesgoRepository.findAll(pageable)
            .map(rangoRiesgoMapper::toDto);
    }

    /**
     *  Get one rangoRiesgo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public RangoRiesgoDTO findOne(Long id) {
        log.debug("Request to get RangoRiesgo : {}", id);
        RangoRiesgo rangoRiesgo = rangoRiesgoRepository.findOne(id);
        return rangoRiesgoMapper.toDto(rangoRiesgo);
    }

    /**
     *  Delete the  rangoRiesgo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RangoRiesgo : {}", id);
        rangoRiesgoRepository.delete(id);
    }
}
