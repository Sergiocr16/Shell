package com.lighthouse.shell.service.mapper;

import com.lighthouse.shell.domain.*;
import com.lighthouse.shell.service.dto.ImpactoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Impacto and its DTO ImpactoDTO.
 */
@Mapper(componentModel = "spring", uses = {TablaImpactoMapper.class, })
public interface ImpactoMapper extends EntityMapper <ImpactoDTO, Impacto> {

    @Mapping(source = "tablaImpacto.id", target = "tablaImpactoId")
    ImpactoDTO toDto(Impacto impacto); 

    @Mapping(source = "tablaImpactoId", target = "tablaImpacto")
    Impacto toEntity(ImpactoDTO impactoDTO); 
    default Impacto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Impacto impacto = new Impacto();
        impacto.setId(id);
        return impacto;
    }
}
