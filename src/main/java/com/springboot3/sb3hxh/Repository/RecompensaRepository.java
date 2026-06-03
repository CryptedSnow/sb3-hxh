package com.springboot3.sb3hxh.Repository;

import com.springboot3.sb3hxh.Entity.Recompensa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecompensaRepository extends JpaRepository<Recompensa, Integer> {

    List<Recompensa> findByDeletedAtIsNullOrderByIdAsc();

    Page<Recompensa> findByDeletedAtIsNullOrderByIdAsc(Pageable pageable);

    Page<Recompensa> findByDeletedAtIsNotNullOrderByIdAsc(Pageable pageable);

    @Query("SELECT r FROM Recompensa r WHERE r.deletedAt IS NULL AND LOWER(r.descricaoRecompensa) LIKE LOWER(CONCAT('%', :search, '%')) ORDER BY r.id ASC")
    Page<Recompensa> searchAtivos(@Param("search") String search, Pageable pageable);

    @Query("SELECT r FROM Recompensa r WHERE r.deletedAt IS NOT NULL AND LOWER(r.descricaoRecompensa) LIKE LOWER(CONCAT('%', :search, '%')) ORDER BY r.id ASC")
    Page<Recompensa> searchLixeira(@Param("search") String search, Pageable pageable);
}