package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.RecompensadoDAO;
import com.springboot3.sb3hxh.Entity.Recompensado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecompensadoService implements RecompensadoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public RecompensadoService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<Recompensado> index() {
        TypedQuery<Recompensado> query = entityManager.createQuery("SELECT recompensado FROM Recompensado recompensado " +
                        "INNER JOIN FETCH recompensado.hunterId hunter " +
                        "INNER JOIN FETCH recompensado.recompensaId recompensa " +
                        "WHERE recompensado.deleted_at IS NULL " +
                        "ORDER BY recompensado.id ASC", Recompensado.class);
        return query.getResultList();
    }

    @Override
    public Page<Recompensado> indexPagination(int page, int size) {
        TypedQuery<Recompensado> query = entityManager.createQuery("SELECT recompensado FROM Recompensado recompensado " +
                "INNER JOIN FETCH recompensado.hunterId hunter " +
                "INNER JOIN FETCH recompensado.recompensaId recompensa " +
                "WHERE recompensado.deleted_at IS NULL " +
                "ORDER BY recompensado.id ASC", Recompensado.class);
        long totalCount = entityManager.createQuery("SELECT COUNT(recompensado) FROM Recompensado recompensado WHERE recompensado.deleted_at IS NULL", Long.class).getSingleResult();
        if (totalCount == 0) {
            return Page.empty(PageRequest.of(page, size));
        }
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Recompensado> recompensados = query.getResultList();
        return new PageImpl<>(recompensados, PageRequest.of(page, size), totalCount);
    }

    @Override
    public Page<Recompensado> searchRecompensado(String search, int page, int size) {
        TypedQuery<Recompensado> query = entityManager.createQuery("SELECT recompensado FROM Recompensado recompensado " +
                "INNER JOIN FETCH recompensado.hunterId hunter " +
                "INNER JOIN FETCH recompensado.recompensaId recompensa " +
                "WHERE recompensado.deleted_at IS NULL " +
                "AND (LOWER(hunter.nomeHunter) LIKE LOWER(:search) OR LOWER(recompensa.descricaoRecompensa) LIKE LOWER(:search)) " +
                "ORDER BY recompensado.id ASC ", Recompensado.class);
        query.setParameter("search", "%" + search + "%");
        long totalCount = entityManager.createQuery("SELECT COUNT(recompensado) FROM Recompensado recompensado " +
                "INNER JOIN recompensado.hunterId hunter " +
                "INNER JOIN recompensado.recompensaId recompensa " +
                "WHERE recompensado.deleted_at IS NULL " +
                "AND (LOWER(hunter.nomeHunter) LIKE LOWER(:search) OR LOWER(recompensa.descricaoRecompensa) LIKE LOWER(:search))", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult();
        if (totalCount == 0) {
            return Page.empty(PageRequest.of(page, size));
        }
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Recompensado> recompensados = query.getResultList();
        return new PageImpl<>(recompensados, PageRequest.of(page, size), totalCount);
    }

    @Override
    @Transactional
    public Recompensado create(Recompensado theRecompensadoEntity) {
        entityManager.persist(theRecompensadoEntity);
        return theRecompensadoEntity;
    }

    @Override
    public Recompensado read(int id) {
        return entityManager.find(Recompensado.class, id);
    }

    @Override
    @Transactional
    public void trash(int id) {
        Recompensado recompensadoEntity = entityManager.find(Recompensado.class, id);
        if (recompensadoEntity != null) {
            recompensadoEntity.setDeletedAt(LocalDateTime.now());
            entityManager.merge(recompensadoEntity);
        }
    }

    @Override
    public Page<Recompensado> indexTrash(int page, int size) {
        TypedQuery<Recompensado> query = entityManager.createQuery("SELECT recompensado FROM Recompensado recompensado " +
                "INNER JOIN FETCH recompensado.hunterId hunter " +
                "INNER JOIN FETCH recompensado.recompensaId recompensa " +
                "WHERE recompensado.deleted_at IS NOT NULL " +
                "ORDER BY recompensado.id ASC", Recompensado.class);
        long totalCount = entityManager.createQuery("SELECT COUNT(recompensado) FROM Recompensado recompensado WHERE recompensado.deleted_at IS NOT NULL", Long.class).getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Recompensado> recompensados = query.getResultList();
        return new PageImpl<>(recompensados, PageRequest.of(page, size), totalCount);
    }

    @Override
    public Page<Recompensado> searchRecompensadoTrash(String search, int page, int size) {
        TypedQuery<Recompensado> query = entityManager.createQuery("SELECT recompensado FROM Recompensado recompensado " +
                "INNER JOIN FETCH recompensado.hunterId hunter " +
                "INNER JOIN FETCH recompensado.recompensaId recompensa " +
                "WHERE recompensado.deleted_at IS NOT NULL " +
                "AND (LOWER(hunter.nomeHunter) LIKE LOWER(:search) OR LOWER(recompensa.descricaoRecompensa) LIKE LOWER(:search)) " +
                "ORDER BY recompensado.id ASC ", Recompensado.class);
        query.setParameter("search", "%" + search + "%");
        long totalCount = entityManager.createQuery("SELECT COUNT(recompensado) FROM Recompensado recompensado " +
                        "INNER JOIN recompensado.hunterId hunter " +
                        "INNER JOIN recompensado.recompensaId recompensa " +
                        "WHERE recompensado.deleted_at IS NOT NULL " +
                        "AND (LOWER(hunter.nomeHunter) LIKE LOWER(:search) OR LOWER(recompensa.descricaoRecompensa) LIKE LOWER(:search))", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Recompensado> recompensados = query.getResultList();
        return new PageImpl<>(recompensados, PageRequest.of(page, size), totalCount);
    }

    @Override
    @Transactional
    public Recompensado restore(int id) {
        Recompensado recompensadoEntity = entityManager.find(Recompensado.class, id);
        if (recompensadoEntity != null) {
            recompensadoEntity.setDeletedAt(null);
            entityManager.persist(recompensadoEntity);
        } else {
            throw new IllegalArgumentException("Registro não encontrado com o ID fornecido: " + id);
        }
        return recompensadoEntity;
    }

}