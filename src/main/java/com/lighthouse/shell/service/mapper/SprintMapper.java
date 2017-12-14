package com.lighthouse.shell.service.mapper;

import com.lighthouse.shell.domain.*;
import com.lighthouse.shell.service.dto.SprintDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sprint and its DTO SprintDTO.
 */
@Mapper(componentModel = "spring", uses = {LanzamientoMapper.class, })
public interface SprintMapper extends EntityMapper <SprintDTO, Sprint> {

    @Mapping(source = "lanzamiento.id", target = "lanzamientoId")
    SprintDTO toDto(Sprint sprint); 

    @Mapping(source = "lanzamientoId", target = "lanzamiento")
    Sprint toEntity(SprintDTO sprintDTO); 
    default Sprint fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sprint sprint = new Sprint();
        sprint.setId(id);
        return sprint;
    }
}
