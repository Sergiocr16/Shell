package com.lighthouse.shell.service;

import com.lighthouse.shell.domain.Lanzamiento;
import com.lighthouse.shell.repository.LanzamientoRepository;
import com.lighthouse.shell.service.dto.LanzamientoDTO;
import com.lighthouse.shell.service.mapper.LanzamientoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Lanzamiento.
 */
@Service
@Transactional
public class LanzamientoService {

    private final Logger log = LoggerFactory.getLogger(LanzamientoService.class);

    private final LanzamientoRepository lanzamientoRepository;

    private final LanzamientoMapper lanzamientoMapper;

    public LanzamientoService(LanzamientoRepository lanzamientoRepository, LanzamientoMapper lanzamientoMapper) {
        this.lanzamientoRepository = lanzamientoRepository;
        this.lanzamientoMapper = lanzamientoMapper;
    }

    /**
     * Save a lanzamiento.
     *
     * @param lanzamientoDTO the entity to save
     * @return the persisted entity
     */
    public LanzamientoDTO save(LanzamientoDTO lanzamientoDTO) {
        log.debug("Request to save Lanzamiento : {}", lanzamientoDTO);
        Lanzamiento lanzamiento = lanzamientoMapper.toEntity(lanzamientoDTO);
        lanzamiento = lanzamientoRepository.save(lanzamiento);
        return lanzamientoMapper.toDto(lanzamiento);
    }

    /**
     *  Get all the lanzamientos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LanzamientoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lanzamientos");
        return lanzamientoRepository.findAll(pageable)
            .map(lanzamientoMapper::toDto);
    }

    /**
     *  Get one lanzamiento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public LanzamientoDTO findOne(Long id) {
        log.debug("Request to get Lanzamiento : {}", id);
        Lanzamiento lanzamiento = lanzamientoRepository.findOne(id);
        return lanzamientoMapper.toDto(lanzamiento);
    }

    /**
     *  Delete the  lanzamiento by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Lanzamiento : {}", id);
        lanzamientoRepository.delete(id);
    }
}
