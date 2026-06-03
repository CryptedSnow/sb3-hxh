package com.springboot3.sb3hxh.Repository;

import com.springboot3.sb3hxh.Entity.Recompensado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecompensadoRepository extends JpaRepository<Recompensado, Integer> {

    @Query("SELECT r FROM Recompensado r JOIN FETCH r.hunterId h JOIN FETCH r.recompensaId rc WHERE r.deletedAt IS NULL ORDER BY r.id ASC")
    List<Recompensado> findAllAtivos();

    @Query(value = "SELECT r FROM Recompensado r JOIN FETCH r.hunterId h JOIN FETCH r.recompensaId rc WHERE r.deletedAt IS NULL ORDER BY r.id ASC",
            countQuery = "SELECT COUNT(r) FROM Recompensado r WHERE r.deletedAt IS NULL")
    Page<Recompensado> findAllAtivosPaginated(Pageable pageable);

    @Query(value = "SELECT r FROM Recompensado r JOIN FETCH r.hunterId h JOIN FETCH r.recompensaId rc WHERE r.deletedAt IS NOT NULL ORDER BY r.id ASC",
            countQuery = "SELECT COUNT(r) FROM Recompensado r WHERE r.deletedAt IS NOT NULL")
    Page<Recompensado> findAllLixeiraPaginated(Pageable pageable);

    @Query(value = "SELECT r FROM Recompensado r JOIN FETCH r.hunterId h JOIN FETCH r.recompensaId rc WHERE r.deletedAt IS NULL AND (LOWER(h.nomeHunter) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(rc.descricaoRecompensa) LIKE LOWER(CONCAT('%', :search, '%'))) ORDER BY r.id ASC",
            countQuery = "SELECT COUNT(r) FROM Recompensado r JOIN r.hunterId h JOIN r.recompensaId rc WHERE r.deletedAt IS NULL AND (LOWER(h.nomeHunter) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(rc.descricaoRecompensa) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Recompensado> searchAtivos(@Param("search") String search, Pageable pageable);

    @Query(value = "SELECT r FROM Recompensado r JOIN FETCH r.hunterId h JOIN FETCH r.recompensaId rc WHERE r.deletedAt IS NOT NULL AND (LOWER(h.nomeHunter) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(rc.descricaoRecompensa) LIKE LOWER(CONCAT('%', :search, '%'))) ORDER BY r.id ASC",
            countQuery = "SELECT COUNT(r) FROM Recompensado r JOIN r.hunterId h JOIN r.recompensaId rc WHERE r.deletedAt IS NOT NULL AND (LOWER(h.nomeHunter) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(rc.descricaoRecompensa) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Recompensado> searchLixeira(@Param("search") String search, Pageable pageable);
}