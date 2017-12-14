package com.lighthouse.shell.repository;

import com.lighthouse.shell.domain.Medida;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Medida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedidaRepository extends JpaRepository<Medida,Long> {
    
}
