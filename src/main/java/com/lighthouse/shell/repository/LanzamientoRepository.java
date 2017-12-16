package com.lighthouse.shell.repository;

import com.lighthouse.shell.domain.Lanzamiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Lanzamiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LanzamientoRepository extends JpaRepository<Lanzamiento,Long> {
    Page<Lanzamiento> findByProyectoId(Pageable pageable, Long proyectoId);
}
