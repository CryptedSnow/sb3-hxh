package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.HunterDAO;
import com.springboot3.sb3hxh.Entity.Hunter;
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
    public List<Hunter> index() {
        TypedQuery<Hunter> query = entityManager.createQuery("SELECT hunter FROM Hunter hunter WHERE hunter.deleted_at IS NULL ORDER BY hunter.id ASC", Hunter.class);
        return query.getResultList();
    }

    @Override
    public Page<Hunter> indexPagination(int page, int size) {
        TypedQuery<Hunter> query = entityManager.createQuery("SELECT hunter FROM Hunter hunter WHERE hunter.deleted_at IS NULL ORDER BY hunter.id ASC", Hunter.class);
        long totalCount = entityManager.createQuery("SELECT COUNT(hunter) FROM Hunter hunter WHERE hunter.deleted_at IS NULL", Long.class).getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Hunter> hunters = query.getResultList();
        return new PageImpl<>(hunters, PageRequest.of(page, size), totalCount);
    }

    @Override
    public Page<Hunter> searchHunter(String search, int page, int size) {
        TypedQuery<Hunter> query = entityManager.createQuery("SELECT hunter FROM Hunter hunter " +
                "WHERE hunter.deleted_at IS NULL " +
                "AND LOWER(hunter.nome_hunter) LIKE LOWER(:search) " +
                "ORDER BY hunter.id ASC", Hunter.class);
        query.setParameter("search", "%" + search + "%");
        long totalCount = entityManager.createQuery("SELECT COUNT(hunter) FROM Hunter hunter WHERE hunter.deleted_at IS NULL AND LOWER(hunter.nome_hunter) LIKE LOWER(:search)", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult();
        query.setMaxResults(size);
        List<Hunter> hunters = query.getResultList();
        return new PageImpl<>(hunters, PageRequest.of(page, size), totalCount);
    }

    @Override
    @Transactional
    public Hunter create(Hunter theHunterEntity) {
        entityManager.persist(theHunterEntity);
        return theHunterEntity;
    }

    @Override
    public Hunter read(int id) {
        return entityManager.find(Hunter.class, id);
    }

    @Override
    @Transactional
    public Hunter update(Hunter theHunterEntity) {
        Hunter hunterEntity = entityManager.merge(theHunterEntity);
        return hunterEntity;
    }

    @Override
    @Transactional
    public void trash(int id) {
        Hunter hunter = entityManager.find(Hunter.class, id);
        if (hunter != null) {
            hunter.setDeletedAt(LocalDateTime.now());
            entityManager.merge(hunter);
        }
    }

    @Override
    public Page<Hunter> indexTrash(int page, int size) {
        TypedQuery<Hunter> query = entityManager.createQuery("SELECT hunter FROM Hunter hunter WHERE hunter.deleted_at IS NOT NULL ORDER BY hunter.id ASC", Hunter.class);
        long totalCount = entityManager.createQuery("SELECT COUNT(hunter) FROM Hunter hunter WHERE hunter.deleted_at IS NOT NULL", Long.class).getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Hunter> hunters = query.getResultList();
        return new PageImpl<>(hunters, PageRequest.of(page, size), totalCount);
    }

    @Override
    public Page<Hunter> searchHunterTrash(String search, int page, int size) {
        TypedQuery<Hunter> query = entityManager.createQuery("SELECT hunter FROM Hunter hunter " +
                "WHERE hunter.deleted_at IS NOT NULL " +
                "AND LOWER(hunter.nome_hunter) LIKE LOWER(:search) " +
                "ORDER BY hunter.id ASC", Hunter.class);
        query.setParameter("search", "%" + search + "%");
        long totalCount = entityManager.createQuery("SELECT COUNT(hunter) FROM Hunter hunter WHERE hunter.deleted_at IS NOT NULL AND LOWER(hunter.nome_hunter) LIKE LOWER(:search)", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Hunter> hunters = query.getResultList();
        return new PageImpl<>(hunters, PageRequest.of(page, size), totalCount);
    }

    @Override
    @Transactional
    public Hunter restore(int id) {
        Hunter hunterEntity = entityManager.find(Hunter.class, id);
        if (hunterEntity != null) {
            hunterEntity.setDeletedAt(null);
            entityManager.persist(hunterEntity);
        } else {
            throw new IllegalArgumentException("Registro não encontrado com o ID fornecido: " + id);
        }
        return hunterEntity;
    }

    @Override
    public boolean existsId(String id) {
        try {
            int idAsInt = Integer.parseInt(id);
            Hunter hunterEntity = entityManager.find(Hunter.class, idAsInt);
            return hunterEntity != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}