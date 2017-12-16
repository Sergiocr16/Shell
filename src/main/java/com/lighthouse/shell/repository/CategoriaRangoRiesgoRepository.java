package com.lighthouse.shell.repository;

import com.lighthouse.shell.domain.CategoriaRangoRiesgo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CategoriaRangoRiesgo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaRangoRiesgoRepository extends JpaRepository<CategoriaRangoRiesgo,Long> {
    
}
