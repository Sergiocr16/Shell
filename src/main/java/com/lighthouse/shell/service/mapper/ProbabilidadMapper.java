package com.lighthouse.shell.service.mapper;

import com.lighthouse.shell.domain.*;
import com.lighthouse.shell.service.dto.ProbabilidadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Probabilidad and its DTO ProbabilidadDTO.
 */
@Mapper(componentModel = "spring", uses = {TablaProbabilidadMapper.class, })
public interface ProbabilidadMapper extends EntityMapper <ProbabilidadDTO, Probabilidad> {

    @Mapping(source = "tablaProbabilidad.id", target = "tablaProbabilidadId")
    ProbabilidadDTO toDto(Probabilidad probabilidad); 

    @Mapping(source = "tablaProbabilidadId", target = "tablaProbabilidad")
    Probabilidad toEntity(ProbabilidadDTO probabilidadDTO); 
    default Probabilidad fromId(Long id) {
        if (id == null) {
            return null;
        }
        Probabilidad probabilidad = new Probabilidad();
        probabilidad.setId(id);
        return probabilidad;
    }
}
