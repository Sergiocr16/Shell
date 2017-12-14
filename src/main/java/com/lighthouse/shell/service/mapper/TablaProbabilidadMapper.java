package com.lighthouse.shell.service.mapper;

import com.lighthouse.shell.domain.*;
import com.lighthouse.shell.service.dto.TablaProbabilidadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TablaProbabilidad and its DTO TablaProbabilidadDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TablaProbabilidadMapper extends EntityMapper <TablaProbabilidadDTO, TablaProbabilidad> {
    
    @Mapping(target = "impactos", ignore = true)
    TablaProbabilidad toEntity(TablaProbabilidadDTO tablaProbabilidadDTO); 
    default TablaProbabilidad fromId(Long id) {
        if (id == null) {
            return null;
        }
        TablaProbabilidad tablaProbabilidad = new TablaProbabilidad();
        tablaProbabilidad.setId(id);
        return tablaProbabilidad;
    }
}
