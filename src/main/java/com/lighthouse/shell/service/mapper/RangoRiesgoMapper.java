package com.lighthouse.shell.service.mapper;

import com.lighthouse.shell.domain.*;
import com.lighthouse.shell.service.dto.RangoRiesgoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RangoRiesgo and its DTO RangoRiesgoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProyectoMapper.class, })
public interface RangoRiesgoMapper extends EntityMapper <RangoRiesgoDTO, RangoRiesgo> {

    @Mapping(source = "proyecto.id", target = "proyectoId")
    RangoRiesgoDTO toDto(RangoRiesgo rangoRiesgo); 

    @Mapping(source = "proyectoId", target = "proyecto")
    RangoRiesgo toEntity(RangoRiesgoDTO rangoRiesgoDTO); 
    default RangoRiesgo fromId(Long id) {
        if (id == null) {
            return null;
        }
        RangoRiesgo rangoRiesgo = new RangoRiesgo();
        rangoRiesgo.setId(id);
        return rangoRiesgo;
    }
}
