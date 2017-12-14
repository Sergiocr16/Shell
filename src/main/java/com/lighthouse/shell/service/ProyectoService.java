package com.lighthouse.shell.service;

import com.lighthouse.shell.domain.Proyecto;
import com.lighthouse.shell.repository.ProyectoRepository;
import com.lighthouse.shell.service.dto.ProyectoDTO;
import com.lighthouse.shell.service.mapper.ProyectoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Proyecto.
 */
@Service
@Transactional
public class ProyectoService {

    private final Logger log = LoggerFactory.getLogger(ProyectoService.class);

    private final ProyectoRepository proyectoRepository;

    private final ProyectoMapper proyectoMapper;

    public ProyectoService(ProyectoRepository proyectoRepository, ProyectoMapper proyectoMapper) {
        this.proyectoRepository = proyectoRepository;
        this.proyectoMapper = proyectoMapper;
    }

    /**
     * Save a proyecto.
     *
     * @param proyectoDTO the entity to save
     * @return the persisted entity
     */
    public ProyectoDTO save(ProyectoDTO proyectoDTO) {
        log.debug("Request to save Proyecto : {}", proyectoDTO);
        Proyecto proyecto = proyectoMapper.toEntity(proyectoDTO);
        proyecto = proyectoRepository.save(proyecto);
        return proyectoMapper.toDto(proyecto);
    }

    /**
     *  Get all the proyectos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProyectoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Proyectos");
        return proyectoRepository.findAll(pageable)
            .map(proyectoMapper::toDto);
    }

    /**
     *  Get one proyecto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ProyectoDTO findOne(Long id) {
        log.debug("Request to get Proyecto : {}", id);
        Proyecto proyecto = proyectoRepository.findOne(id);
        return proyectoMapper.toDto(proyecto);
    }

    /**
     *  Delete the  proyecto by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Proyecto : {}", id);
        proyectoRepository.delete(id);
    }
}
