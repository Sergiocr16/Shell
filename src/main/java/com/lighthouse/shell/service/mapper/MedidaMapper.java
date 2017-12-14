package com.lighthouse.shell.service.mapper;

import com.lighthouse.shell.domain.*;
import com.lighthouse.shell.service.dto.MedidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Medida and its DTO MedidaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MedidaMapper extends EntityMapper <MedidaDTO, Medida> {
    
    
    default Medida fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medida medida = new Medida();
        medida.setId(id);
        return medida;
    }
}
