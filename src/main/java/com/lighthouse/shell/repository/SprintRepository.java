package com.lighthouse.shell.repository;

import com.lighthouse.shell.domain.Sprint;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Sprint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SprintRepository extends JpaRepository<Sprint,Long> {
    Page<Sprint> findByLanzamientoId(Pageable pageable, Long lanzamientoId);
}
