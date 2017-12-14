package com.lighthouse.shell.repository;

import com.lighthouse.shell.domain.Impacto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Impacto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImpactoRepository extends JpaRepository<Impacto,Long> {
    
}
