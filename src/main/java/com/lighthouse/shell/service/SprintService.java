package com.lighthouse.shell.service;

import com.lighthouse.shell.domain.Sprint;
import com.lighthouse.shell.repository.SprintRepository;
import com.lighthouse.shell.service.dto.SprintDTO;
import com.lighthouse.shell.service.mapper.SprintMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Sprint.
 */
@Service
@Transactional
public class SprintService {

    private final Logger log = LoggerFactory.getLogger(SprintService.class);

    private final SprintRepository sprintRepository;

    private final SprintMapper sprintMapper;

    public SprintService(SprintRepository sprintRepository, SprintMapper sprintMapper) {
        this.sprintRepository = sprintRepository;
        this.sprintMapper = sprintMapper;
    }

    /**
     * Save a sprint.
     *
     * @param sprintDTO the entity to save
     * @return the persisted entity
     */
    public SprintDTO save(SprintDTO sprintDTO) {
        log.debug("Request to save Sprint : {}", sprintDTO);
        Sprint sprint = sprintMapper.toEntity(sprintDTO);
        sprint = sprintRepository.save(sprint);
        return sprintMapper.toDto(sprint);
    }

    /**
     *  Get all the sprints.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SprintDTO> findAll(Pageable pageable,Long lanzamientoId) {
        Page<Sprint> result = sprintRepository.findByLanzamientoId(pageable, lanzamientoId);
        return result.map(lanzamiento -> sprintMapper.toDto(lanzamiento));
    }

    /**
     *  Get one sprint by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SprintDTO findOne(Long id) {
        log.debug("Request to get Sprint : {}", id);
        Sprint sprint = sprintRepository.findOne(id);
        return sprintMapper.toDto(sprint);
    }

    /**
     *  Delete the  sprint by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Sprint : {}", id);
        sprintRepository.delete(id);
    }
}
