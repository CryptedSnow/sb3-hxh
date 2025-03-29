package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.RecompensaDAO;
import com.springboot3.sb3hxh.Entity.RecompensaEntity;
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
public class RecompensaService implements RecompensaDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public RecompensaService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<RecompensaEntity> index() {
        TypedQuery<RecompensaEntity> query = entityManager.createQuery("SELECT r FROM RecompensaEntity r WHERE r.deleted_at IS NULL ORDER BY r.id ASC", RecompensaEntity.class);
        return query.getResultList();
    }

    @Override
    public Page<RecompensaEntity> indexPagination(int page, int size) {
        TypedQuery<RecompensaEntity> query = entityManager.createQuery("SELECT r FROM RecompensaEntity r WHERE r.deleted_at IS NULL ORDER BY r.id ASC", RecompensaEntity.class);
        long totalCount = entityManager.createQuery("SELECT COUNT(r) FROM RecompensaEntity r WHERE r.deleted_at IS NULL", Long.class).getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<RecompensaEntity> recompensas = query.getResultList();
        return new PageImpl<>(recompensas, PageRequest.of(page, size), totalCount);
    }

    public Page<RecompensaEntity> searchRecompensa(String search, int page, int size) {
        TypedQuery<RecompensaEntity> query = entityManager.createQuery("SELECT r FROM RecompensaEntity r " +
                "WHERE r.deleted_at IS NULL " +
                "AND LOWER(r.descricao_recompensa) LIKE LOWER(:search) " +
                "ORDER BY r.id ASC", RecompensaEntity.class);
        query.setParameter("search", "%" + search + "%");
        long totalCount = entityManager.createQuery("SELECT COUNT(r) FROM RecompensaEntity r WHERE r.deleted_at IS NULL AND LOWER(r.descricao_recompensa) LIKE LOWER(:search)", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<RecompensaEntity> recompensas = query.getResultList();
        return new PageImpl<>(recompensas, PageRequest.of(page, size), totalCount);
    }

    @Override
    @Transactional
    public RecompensaEntity create(RecompensaEntity theRecompensaEntity) {
        entityManager.persist(theRecompensaEntity);
        return theRecompensaEntity;
    }

    @Override
    public RecompensaEntity read(int id) {
        return entityManager.find(RecompensaEntity.class, id);
    }

    @Override
    @Transactional
    public RecompensaEntity update(RecompensaEntity theRecompensaEntity) {
        RecompensaEntity recompensaEntity = entityManager.merge(theRecompensaEntity);
        return recompensaEntity;
    }

    @Override
    @Transactional
    public void trash(int id) {
        RecompensaEntity recompensaEntity = entityManager.find(RecompensaEntity.class, id);
        if (recompensaEntity != null) {
            recompensaEntity.setDeleted_at(LocalDateTime.now());
            entityManager.merge(recompensaEntity);
        }
    }

    @Override
    public Page<RecompensaEntity> indexTrash(int page, int size) {
        TypedQuery<RecompensaEntity> query = entityManager.createQuery("SELECT r FROM RecompensaEntity r WHERE r.deleted_at IS NOT NULL ORDER BY r.id ASC", RecompensaEntity.class);
        long totalCount = entityManager.createQuery("SELECT COUNT(r) FROM RecompensaEntity r WHERE r.deleted_at IS NOT NULL", Long.class).getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<RecompensaEntity> recompensas = query.getResultList();
        return new PageImpl<>(recompensas, PageRequest.of(page, size), totalCount);
    }

    public Page<RecompensaEntity> searchRecompensaTrash(String search, int page, int size) {
        TypedQuery<RecompensaEntity> query = entityManager.createQuery("SELECT r FROM RecompensaEntity r " +
                "WHERE r.deleted_at IS NOT NULL " +
                "AND LOWER(r.descricao_recompensa) LIKE LOWER(:search) " +
                "ORDER BY r.id ASC", RecompensaEntity.class);
        query.setParameter("search", "%" + search + "%");
        long totalCount = entityManager.createQuery("SELECT COUNT(r) FROM RecompensaEntity r WHERE r.deleted_at IS NOT NULL AND LOWER(r.descricao_recompensa) LIKE LOWER(:search)", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<RecompensaEntity> recompensas = query.getResultList();
        return new PageImpl<>(recompensas, PageRequest.of(page, size), totalCount);
    }

    @Override
    @Transactional
    public RecompensaEntity restore(int id) {
        RecompensaEntity recompensaEntity = entityManager.find(RecompensaEntity.class, id);
        if (recompensaEntity != null) {
            recompensaEntity.setDeleted_at(null);
            entityManager.persist(recompensaEntity);
        } else {
            throw new IllegalArgumentException("Registro não encontrado com o ID fornecido: " + id);
        }
        return recompensaEntity;
    }

    @Override
    @Transactional
    public void delete(int id) {
        RecompensaEntity recompensaEntity = entityManager.find(RecompensaEntity.class,id);
        entityManager.remove(recompensaEntity);
    }

    @Override
    public boolean existsId(String id) {
        try {
            int idAsInt = Integer.parseInt(id);
            RecompensaEntity recompensaEntity = entityManager.find(RecompensaEntity.class, idAsInt);
            return recompensaEntity != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}