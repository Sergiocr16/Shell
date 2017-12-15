package com.lighthouse.shell.repository;

import com.lighthouse.shell.domain.Probabilidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Probabilidad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProbabilidadRepository extends JpaRepository<Probabilidad,Long> {
    Page<Probabilidad> findByTablaProbabilidadId(Pageable pageable, Long tablaProbabilidadId);

}
