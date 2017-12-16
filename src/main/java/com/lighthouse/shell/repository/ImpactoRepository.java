package com.lighthouse.shell.repository;

import com.lighthouse.shell.domain.Impacto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Impacto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImpactoRepository extends JpaRepository<Impacto,Long> {
    Page<Impacto> findByTablaImpactoId(Pageable pageable, Long tablaImpactoId);
}
