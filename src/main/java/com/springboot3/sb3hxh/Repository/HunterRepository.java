package com.springboot3.sb3hxh.Repository;

import com.springboot3.sb3hxh.Entity.Hunter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HunterRepository extends JpaRepository<Hunter, Integer> {

    List<Hunter> findByDeletedAtIsNullOrderByIdAsc();

    Page<Hunter> findByDeletedAtIsNullOrderByIdAsc(Pageable pageable);

    Page<Hunter> findByDeletedAtIsNotNullOrderByIdAsc(Pageable pageable);

    @Query("SELECT h FROM Hunter h WHERE h.deletedAt IS NULL AND LOWER(h.nomeHunter) LIKE LOWER(CONCAT('%', :search, '%')) ORDER BY h.id ASC")
    Page<Hunter> searchAtivos(@Param("search") String search, Pageable pageable);

    @Query("SELECT h FROM Hunter h WHERE h.deletedAt IS NOT NULL AND LOWER(h.nomeHunter) LIKE LOWER(CONCAT('%', :search, '%')) ORDER BY h.id ASC")
    Page<Hunter> searchLixeira(@Param("search") String search, Pageable pageable);
}