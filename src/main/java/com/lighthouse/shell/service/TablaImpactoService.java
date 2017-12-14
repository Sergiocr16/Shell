package com.lighthouse.shell.service;

import com.lighthouse.shell.domain.TablaImpacto;
import com.lighthouse.shell.repository.TablaImpactoRepository;
import com.lighthouse.shell.service.dto.TablaImpactoDTO;
import com.lighthouse.shell.service.mapper.TablaImpactoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TablaImpacto.
 */
@Service
@Transactional
public class TablaImpactoService {

    private final Logger log = LoggerFactory.getLogger(TablaImpactoService.class);

    private final TablaImpactoRepository tablaImpactoRepository;

    private final TablaImpactoMapper tablaImpactoMapper;

    public TablaImpactoService(TablaImpactoRepository tablaImpactoRepository, TablaImpactoMapper tablaImpactoMapper) {
        this.tablaImpactoRepository = tablaImpactoRepository;
        this.tablaImpactoMapper = tablaImpactoMapper;
    }

    /**
     * Save a tablaImpacto.
     *
     * @param tablaImpactoDTO the entity to save
     * @return the persisted entity
     */
    public TablaImpactoDTO save(TablaImpactoDTO tablaImpactoDTO) {
        log.debug("Request to save TablaImpacto : {}", tablaImpactoDTO);
        TablaImpacto tablaImpacto = tablaImpactoMapper.toEntity(tablaImpactoDTO);
        tablaImpacto = tablaImpactoRepository.save(tablaImpacto);
        return tablaImpactoMapper.toDto(tablaImpacto);
    }

    /**
     *  Get all the tablaImpactos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TablaImpactoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TablaImpactos");
        return tablaImpactoRepository.findAll(pageable)
            .map(tablaImpactoMapper::toDto);
    }

    /**
     *  Get one tablaImpacto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TablaImpactoDTO findOne(Long id) {
        log.debug("Request to get TablaImpacto : {}", id);
        TablaImpacto tablaImpacto = tablaImpactoRepository.findOne(id);
        return tablaImpactoMapper.toDto(tablaImpacto);
    }

    /**
     *  Delete the  tablaImpacto by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TablaImpacto : {}", id);
        tablaImpactoRepository.delete(id);
    }
}
