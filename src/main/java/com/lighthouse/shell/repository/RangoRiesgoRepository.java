package com.lighthouse.shell.repository;

import com.lighthouse.shell.domain.RangoRiesgo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RangoRiesgo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RangoRiesgoRepository extends JpaRepository<RangoRiesgo,Long> {
    
}
