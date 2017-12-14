package com.lighthouse.shell.service;

import com.lighthouse.shell.domain.Impacto;
import com.lighthouse.shell.repository.ImpactoRepository;
import com.lighthouse.shell.service.dto.ImpactoDTO;
import com.lighthouse.shell.service.mapper.ImpactoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Impacto.
 */
@Service
@Transactional
public class ImpactoService {

    private final Logger log = LoggerFactory.getLogger(ImpactoService.class);

    private final ImpactoRepository impactoRepository;

    private final ImpactoMapper impactoMapper;

    public ImpactoService(ImpactoRepository impactoRepository, ImpactoMapper impactoMapper) {
        this.impactoRepository = impactoRepository;
        this.impactoMapper = impactoMapper;
    }

    /**
     * Save a impacto.
     *
     * @param impactoDTO the entity to save
     * @return the persisted entity
     */
    public ImpactoDTO save(ImpactoDTO impactoDTO) {
        log.debug("Request to save Impacto : {}", impactoDTO);
        Impacto impacto = impactoMapper.toEntity(impactoDTO);
        impacto = impactoRepository.save(impacto);
        return impactoMapper.toDto(impacto);
    }

    /**
     *  Get all the impactos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ImpactoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Impactos");
        return impactoRepository.findAll(pageable)
            .map(impactoMapper::toDto);
    }

    /**
     *  Get one impacto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ImpactoDTO findOne(Long id) {
        log.debug("Request to get Impacto : {}", id);
        Impacto impacto = impactoRepository.findOne(id);
        return impactoMapper.toDto(impacto);
    }

    /**
     *  Delete the  impacto by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Impacto : {}", id);
        impactoRepository.delete(id);
    }
}
