package com.lighthouse.shell.repository;

import com.lighthouse.shell.domain.Sprint;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Sprint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SprintRepository extends JpaRepository<Sprint,Long> {
    
}
