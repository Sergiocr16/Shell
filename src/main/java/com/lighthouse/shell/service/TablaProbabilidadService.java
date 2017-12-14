package com.lighthouse.shell.service;

import com.lighthouse.shell.domain.TablaProbabilidad;
import com.lighthouse.shell.repository.TablaProbabilidadRepository;
import com.lighthouse.shell.service.dto.TablaProbabilidadDTO;
import com.lighthouse.shell.service.mapper.TablaProbabilidadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TablaProbabilidad.
 */
@Service
@Transactional
public class TablaProbabilidadService {

    private final Logger log = LoggerFactory.getLogger(TablaProbabilidadService.class);

    private final TablaProbabilidadRepository tablaProbabilidadRepository;

    private final TablaProbabilidadMapper tablaProbabilidadMapper;

    public TablaProbabilidadService(TablaProbabilidadRepository tablaProbabilidadRepository, TablaProbabilidadMapper tablaProbabilidadMapper) {
        this.tablaProbabilidadRepository = tablaProbabilidadRepository;
        this.tablaProbabilidadMapper = tablaProbabilidadMapper;
    }

    /**
     * Save a tablaProbabilidad.
     *
     * @param tablaProbabilidadDTO the entity to save
     * @return the persisted entity
     */
    public TablaProbabilidadDTO save(TablaProbabilidadDTO tablaProbabilidadDTO) {
        log.debug("Request to save TablaProbabilidad : {}", tablaProbabilidadDTO);
        TablaProbabilidad tablaProbabilidad = tablaProbabilidadMapper.toEntity(tablaProbabilidadDTO);
        tablaProbabilidad = tablaProbabilidadRepository.save(tablaProbabilidad);
        return tablaProbabilidadMapper.toDto(tablaProbabilidad);
    }

    /**
     *  Get all the tablaProbabilidads.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TablaProbabilidadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TablaProbabilidads");
        return tablaProbabilidadRepository.findAll(pageable)
            .map(tablaProbabilidadMapper::toDto);
    }

    /**
     *  Get one tablaProbabilidad by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TablaProbabilidadDTO findOne(Long id) {
        log.debug("Request to get TablaProbabilidad : {}", id);
        TablaProbabilidad tablaProbabilidad = tablaProbabilidadRepository.findOne(id);
        return tablaProbabilidadMapper.toDto(tablaProbabilidad);
    }

    /**
     *  Delete the  tablaProbabilidad by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TablaProbabilidad : {}", id);
        tablaProbabilidadRepository.delete(id);
    }
}
