package com.lighthouse.shell.repository;

import com.lighthouse.shell.domain.Riesgo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Riesgo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RiesgoRepository extends JpaRepository<Riesgo,Long> {
    Page<Riesgo> findByProyectoId(Pageable pageable, Long proyectoId);
}
