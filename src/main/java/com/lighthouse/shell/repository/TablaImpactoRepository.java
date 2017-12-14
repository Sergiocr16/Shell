package com.lighthouse.shell.repository;

import com.lighthouse.shell.domain.TablaImpacto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TablaImpacto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TablaImpactoRepository extends JpaRepository<TablaImpacto,Long> {
    
}
