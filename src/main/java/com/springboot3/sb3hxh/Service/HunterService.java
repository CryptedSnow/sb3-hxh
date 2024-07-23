package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.HunterDAO;
import com.springboot3.sb3hxh.Entity.HunterEntity;
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
public class HunterService implements HunterDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public HunterService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<HunterEntity> index() {
        TypedQuery<HunterEntity> query = entityManager.createQuery("SELECT h FROM HunterEntity h " +
                "INNER JOIN FETCH h.tipo_hunter_id th " +
                "INNER JOIN FETCH h.tipo_nen_id tn " +
                "INNER JOIN FETCH h.tipo_sanguineo_id ts " +
                "WHERE h.deleted_at IS NULL " +
                "ORDER BY h.id ASC", HunterEntity.class);
        return query.getResultList();
    }

    @Override
    public Page<HunterEntity> indexPagination(int page, int size) {
        TypedQuery<HunterEntity> query = entityManager.createQuery("SELECT h FROM HunterEntity h " +
                "INNER JOIN FETCH h.tipo_hunter_id th " +
                "INNER JOIN FETCH h.tipo_nen_id tn " +
                "INNER JOIN FETCH h.tipo_sanguineo_id ts " +
                "WHERE h.deleted_at IS NULL " +
                "ORDER BY h.id ASC", HunterEntity.class);
        long totalCount = entityManager.createQuery("SELECT COUNT(h) FROM HunterEntity h WHERE h.deleted_at IS NULL", Long.class).getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<HunterEntity> hunters = query.getResultList();
        return new PageImpl<>(hunters, PageRequest.of(page, size), totalCount);
    }

    @Override
    public Page<HunterEntity> searchHunter(String search, int page, int size) {
        TypedQuery<HunterEntity> query = entityManager.createQuery("SELECT h FROM HunterEntity h " +
                "INNER JOIN FETCH h.tipo_hunter_id th " +
                "INNER JOIN FETCH h.tipo_nen_id tn " +
                "INNER JOIN FETCH h.tipo_sanguineo_id ts " +
                "WHERE h.deleted_at IS NULL " +
                "AND LOWER(h.nome_hunter) LIKE LOWER(:search) " +
                "ORDER BY h.id ASC", HunterEntity.class);
        query.setParameter("search", "%" + search + "%");
        long totalCount = entityManager.createQuery("SELECT COUNT(h) FROM HunterEntity h WHERE h.deleted_at IS NULL AND LOWER(h.nome_hunter) LIKE LOWER(:search)", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<HunterEntity> hunters = query.getResultList();
        return new PageImpl<>(hunters, PageRequest.of(page, size), totalCount);
    }

    @Override
    @Transactional
    public HunterEntity create(HunterEntity theHunterEntity) {
        entityManager.persist(theHunterEntity);
        return theHunterEntity;
    }

    @Override
    public HunterEntity read(int id) {
        return entityManager.find(HunterEntity.class, id);
    }

    @Override
    @Transactional
    public HunterEntity update(HunterEntity theHunterEntity) {
        HunterEntity hunterEntity = entityManager.merge(theHunterEntity);
        return hunterEntity;
    }

    @Override
    @Transactional
    public void trash(int id) {
        HunterEntity hunterEntity = entityManager.find(HunterEntity.class, id);
        if (hunterEntity != null) {
            hunterEntity.setDeletedAt(LocalDateTime.now());
            entityManager.merge(hunterEntity);
        }
    }

    @Override
    public Page<HunterEntity> indexTrash(int page, int size) {
        TypedQuery<HunterEntity> query = entityManager.createQuery("SELECT h FROM HunterEntity h " +
                "INNER JOIN FETCH h.tipo_hunter_id th " +
                "INNER JOIN FETCH h.tipo_nen_id tn " +
                "INNER JOIN FETCH h.tipo_sanguineo_id ts " +
                "WHERE h.deleted_at IS NOT NULL " +
                "ORDER BY h.id ASC", HunterEntity.class);
        long totalCount = entityManager.createQuery("SELECT COUNT(h) FROM HunterEntity h WHERE h.deleted_at IS NOT NULL", Long.class).getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<HunterEntity> hunters = query.getResultList();
        return new PageImpl<>(hunters, PageRequest.of(page, size), totalCount);
    }

    @Override
    public Page<HunterEntity> searchHunterTrash(String search, int page, int size) {
        TypedQuery<HunterEntity> query = entityManager.createQuery("SELECT h FROM HunterEntity h " +
                "INNER JOIN FETCH h.tipo_hunter_id th " +
                "INNER JOIN FETCH h.tipo_nen_id tn " +
                "INNER JOIN FETCH h.tipo_sanguineo_id ts " +
                "WHERE h.deleted_at IS NOT NULL " +
                "AND LOWER(h.nome_hunter) LIKE LOWER(:search) " +
                "ORDER BY h.id ASC", HunterEntity.class);
        query.setParameter("search", "%" + search + "%");
        long totalCount = entityManager.createQuery("SELECT COUNT(h) FROM HunterEntity h WHERE h.deleted_at IS NOT NULL AND LOWER(h.nome_hunter) LIKE LOWER(:search)", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<HunterEntity> hunters = query.getResultList();
        return new PageImpl<>(hunters, PageRequest.of(page, size), totalCount);
    }

    @Override
    @Transactional
    public HunterEntity restore(int id) {
        HunterEntity hunterEntity = entityManager.find(HunterEntity.class, id);
        if (hunterEntity != null) {
            hunterEntity.setDeletedAt(null);
            entityManager.persist(hunterEntity);
        } else {
            throw new IllegalArgumentException("Registro não encontrado com o ID fornecido: " + id);
        }
        return hunterEntity;
    }

    @Override
    @Transactional
    public void delete(int id) {
        HunterEntity hunterEntity = entityManager.find(HunterEntity.class,id);
        entityManager.remove(hunterEntity);
    }

    @Override
    public boolean existsId(String id) {
        try {
            int idAsInt = Integer.parseInt(id);
            HunterEntity hunterEntity = entityManager.find(HunterEntity.class, idAsInt);
            return hunterEntity != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}