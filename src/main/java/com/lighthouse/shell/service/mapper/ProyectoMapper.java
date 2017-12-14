package com.lighthouse.shell.service.mapper;

import com.lighthouse.shell.domain.*;
import com.lighthouse.shell.service.dto.ProyectoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Proyecto and its DTO ProyectoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProyectoMapper extends EntityMapper <ProyectoDTO, Proyecto> {
    
    @Mapping(target = "lanzamientos", ignore = true)
    @Mapping(target = "riesgos", ignore = true)
    @Mapping(target = "rangoRiesgos", ignore = true)
    Proyecto toEntity(ProyectoDTO proyectoDTO); 
    default Proyecto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Proyecto proyecto = new Proyecto();
        proyecto.setId(id);
        return proyecto;
    }
}
