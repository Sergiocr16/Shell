package com.lighthouse.shell.service;

import com.lighthouse.shell.domain.Riesgo;
import com.lighthouse.shell.repository.RiesgoRepository;
import com.lighthouse.shell.service.dto.RiesgoDTO;
import com.lighthouse.shell.service.mapper.RiesgoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Riesgo.
 */
@Service
@Transactional
public class RiesgoService {

    private final Logger log = LoggerFactory.getLogger(RiesgoService.class);

    private final RiesgoRepository riesgoRepository;

    private final RiesgoMapper riesgoMapper;

    public RiesgoService(RiesgoRepository riesgoRepository, RiesgoMapper riesgoMapper) {
        this.riesgoRepository = riesgoRepository;
        this.riesgoMapper = riesgoMapper;
    }

    /**
     * Save a riesgo.
     *
     * @param riesgoDTO the entity to save
     * @return the persisted entity
     */
    public RiesgoDTO save(RiesgoDTO riesgoDTO) {
        log.debug("Request to save Riesgo : {}", riesgoDTO);
        Riesgo riesgo = riesgoMapper.toEntity(riesgoDTO);
        riesgo = riesgoRepository.save(riesgo);
        return riesgoMapper.toDto(riesgo);
    }

    /**
     *  Get all the riesgos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RiesgoDTO> findAll(Pageable pageable,Long proyectoId) {
        log.debug("Request to get all Riesgos");
        Page<Riesgo> result = riesgoRepository.findByProyectoId(pageable, proyectoId);
        return result.map(riesgo -> riesgoMapper.toDto(riesgo));
    }

    /**
     *  Get one riesgo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public RiesgoDTO findOne(Long id) {
        log.debug("Request to get Riesgo : {}", id);
        Riesgo riesgo = riesgoRepository.findOne(id);
        return riesgoMapper.toDto(riesgo);
    }

    /**
     *  Delete the  riesgo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Riesgo : {}", id);
        riesgoRepository.delete(id);
    }
}
