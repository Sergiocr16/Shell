package com.lighthouse.shell.service.mapper;

import com.lighthouse.shell.domain.*;
import com.lighthouse.shell.service.dto.TablaImpactoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TablaImpacto and its DTO TablaImpactoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TablaImpactoMapper extends EntityMapper <TablaImpactoDTO, TablaImpacto> {
    
    @Mapping(target = "impactos", ignore = true)
    TablaImpacto toEntity(TablaImpactoDTO tablaImpactoDTO); 
    default TablaImpacto fromId(Long id) {
        if (id == null) {
            return null;
        }
        TablaImpacto tablaImpacto = new TablaImpacto();
        tablaImpacto.setId(id);
        return tablaImpacto;
    }
}
