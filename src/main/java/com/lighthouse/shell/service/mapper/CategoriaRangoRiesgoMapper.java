package com.lighthouse.shell.service.mapper;

import com.lighthouse.shell.domain.*;
import com.lighthouse.shell.service.dto.CategoriaRangoRiesgoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CategoriaRangoRiesgo and its DTO CategoriaRangoRiesgoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriaRangoRiesgoMapper extends EntityMapper <CategoriaRangoRiesgoDTO, CategoriaRangoRiesgo> {
    
    
    default CategoriaRangoRiesgo fromId(Long id) {
        if (id == null) {
            return null;
        }
        CategoriaRangoRiesgo categoriaRangoRiesgo = new CategoriaRangoRiesgo();
        categoriaRangoRiesgo.setId(id);
        return categoriaRangoRiesgo;
    }
}
