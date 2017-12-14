package com.lighthouse.shell.service.mapper;

import com.lighthouse.shell.domain.*;
import com.lighthouse.shell.service.dto.RiesgoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Riesgo and its DTO RiesgoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProyectoMapper.class, MedidaMapper.class, })
public interface RiesgoMapper extends EntityMapper <RiesgoDTO, Riesgo> {

    @Mapping(source = "proyecto.id", target = "proyectoId")

    @Mapping(source = "medida.id", target = "medidaId")
    RiesgoDTO toDto(Riesgo riesgo); 

    @Mapping(source = "proyectoId", target = "proyecto")

    @Mapping(source = "medidaId", target = "medida")
    Riesgo toEntity(RiesgoDTO riesgoDTO); 
    default Riesgo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Riesgo riesgo = new Riesgo();
        riesgo.setId(id);
        return riesgo;
    }
}
