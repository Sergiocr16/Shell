package com.lighthouse.shell.repository;

import com.lighthouse.shell.domain.TablaProbabilidad;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TablaProbabilidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TablaProbabilidadRepository extends JpaRepository<TablaProbabilidad,Long> {
    
}
