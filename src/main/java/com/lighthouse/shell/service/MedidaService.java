package com.lighthouse.shell.service;

import com.lighthouse.shell.domain.Medida;
import com.lighthouse.shell.repository.MedidaRepository;
import com.lighthouse.shell.service.dto.MedidaDTO;
import com.lighthouse.shell.service.mapper.MedidaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Medida.
 */
@Service
@Transactional
public class MedidaService {

    private final Logger log = LoggerFactory.getLogger(MedidaService.class);

    private final MedidaRepository medidaRepository;

    private final MedidaMapper medidaMapper;

    public MedidaService(MedidaRepository medidaRepository, MedidaMapper medidaMapper) {
        this.medidaRepository = medidaRepository;
        this.medidaMapper = medidaMapper;
    }

    /**
     * Save a medida.
     *
     * @param medidaDTO the entity to save
     * @return the persisted entity
     */
    public MedidaDTO save(MedidaDTO medidaDTO) {
        log.debug("Request to save Medida : {}", medidaDTO);
        Medida medida = medidaMapper.toEntity(medidaDTO);
        medida = medidaRepository.save(medida);
        return medidaMapper.toDto(medida);
    }

    /**
     *  Get all the medidas.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MedidaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Medidas");
        return medidaRepository.findAll(pageable)
            .map(medidaMapper::toDto);
    }

    /**
     *  Get one medida by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MedidaDTO findOne(Long id) {
        log.debug("Request to get Medida : {}", id);
        Medida medida = medidaRepository.findOne(id);
        return medidaMapper.toDto(medida);
    }

    /**
     *  Delete the  medida by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Medida : {}", id);
        medidaRepository.delete(id);
    }
}
