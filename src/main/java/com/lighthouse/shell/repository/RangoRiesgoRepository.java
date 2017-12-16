package com.lighthouse.shell.repository;

import com.lighthouse.shell.domain.RangoRiesgo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RangoRiesgo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RangoRiesgoRepository extends JpaRepository<RangoRiesgo,Long> {
    Page<RangoRiesgo> findByProyectoId(Pageable pageable, Long proyectoId);
}
