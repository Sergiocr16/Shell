package com.lighthouse.shell.service.mapper;

import com.lighthouse.shell.domain.*;
import com.lighthouse.shell.service.dto.LanzamientoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lanzamiento and its DTO LanzamientoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProyectoMapper.class, })
public interface LanzamientoMapper extends EntityMapper <LanzamientoDTO, Lanzamiento> {

    @Mapping(source = "proyecto.id", target = "proyectoId")
    LanzamientoDTO toDto(Lanzamiento lanzamiento); 

    @Mapping(source = "proyectoId", target = "proyecto")
    @Mapping(target = "sprints", ignore = true)
    Lanzamiento toEntity(LanzamientoDTO lanzamientoDTO); 
    default Lanzamiento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lanzamiento lanzamiento = new Lanzamiento();
        lanzamiento.setId(id);
        return lanzamiento;
    }
}
