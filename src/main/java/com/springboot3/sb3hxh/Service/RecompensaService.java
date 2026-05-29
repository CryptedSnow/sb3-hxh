package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.RecompensaDAO;
import com.springboot3.sb3hxh.Entity.Recompensa;
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
    public List<Recompensa> index() {
        TypedQuery<Recompensa> query = entityManager.createQuery("SELECT recompensa FROM Recompensa recompensa WHERE recompensa.deleted_at IS NULL ORDER BY recompensa.id ASC", Recompensa.class);
        return query.getResultList();
    }

    @Override
    public Page<Recompensa> indexPagination(int page, int size) {
        TypedQuery<Recompensa> query = entityManager.createQuery("SELECT recompensa FROM Recompensa recompensa WHERE recompensa.deleted_at IS NULL ORDER BY recompensa.id ASC", Recompensa.class);
        long totalCount = entityManager.createQuery("SELECT COUNT(recompensa) FROM Recompensa recompensa WHERE recompensa.deleted_at IS NULL", Long.class).getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Recompensa> recompensas = query.getResultList();
        return new PageImpl<>(recompensas, PageRequest.of(page, size), totalCount);
    }

    public Page<Recompensa> searchRecompensa(String search, int page, int size) {
        TypedQuery<Recompensa> query = entityManager.createQuery("SELECT recompensa FROM Recompensa recompensa " +
                "WHERE recompensa.deleted_at IS NULL " +
                "AND LOWER(recompensa.descricao_recompensa) LIKE LOWER(:search) " +
                "ORDER BY recompensa.id ASC", Recompensa.class);
        query.setParameter("search", "%" + search + "%");
        long totalCount = entityManager.createQuery("SELECT COUNT(recompensa) FROM Recompensa recompensa WHERE recompensa.deleted_at IS NULL AND LOWER(r.descricao_recompensa) LIKE LOWER(:search)", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Recompensa> recompensas = query.getResultList();
        return new PageImpl<>(recompensas, PageRequest.of(page, size), totalCount);
    }

    @Override
    @Transactional
    public Recompensa create(Recompensa theRecompensaEntity) {
        entityManager.persist(theRecompensaEntity);
        return theRecompensaEntity;
    }

    @Override
    public Recompensa read(int id) {
        return entityManager.find(Recompensa.class, id);
    }

    @Override
    @Transactional
    public Recompensa update(Recompensa theRecompensaEntity) {
        Recompensa recompensa = entityManager.merge(theRecompensaEntity);
        return recompensa;
    }

    @Override
    @Transactional
    public void trash(int id) {
        Recompensa recompensa = entityManager.find(Recompensa.class, id);
        if (recompensa != null) {
            recompensa.setDeletedAt(LocalDateTime.now());
            entityManager.merge(recompensa);
        }
    }

    @Override
    public Page<Recompensa> indexTrash(int page, int size) {
        TypedQuery<Recompensa> query = entityManager.createQuery("SELECT recompensa FROM Recompensa recompensa WHERE recompensa.deleted_at IS NOT NULL ORDER BY recompensa.id ASC", Recompensa.class);
        long totalCount = entityManager.createQuery("SELECT COUNT(recompensa) FROM Recompensa recompensa WHERE recompensa.deleted_at IS NOT NULL", Long.class).getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Recompensa> recompensas = query.getResultList();
        return new PageImpl<>(recompensas, PageRequest.of(page, size), totalCount);
    }

    public Page<Recompensa> searchRecompensaTrash(String search, int page, int size) {
        TypedQuery<Recompensa> query = entityManager.createQuery("SELECT recompensa FROM Recompensa recompensa " +
                "WHERE recompensa.deleted_at IS NOT NULL " +
                "AND LOWER(recompensa.descricao_recompensa) LIKE LOWER(:search) " +
                "ORDER BY recompensa.id ASC", Recompensa.class);
        query.setParameter("search", "%" + search + "%");
        long totalCount = entityManager.createQuery("SELECT COUNT(recompensa) FROM Recompensa recompensa WHERE recompensa.deleted_at IS NOT NULL AND LOWER(recompensa.descricao_recompensa) LIKE LOWER(:search)", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Recompensa> recompensas = query.getResultList();
        return new PageImpl<>(recompensas, PageRequest.of(page, size), totalCount);
    }

    @Override
    @Transactional
    public Recompensa restore(int id) {
        Recompensa recompensa = entityManager.find(Recompensa.class, id);
        if (recompensa != null) {
            recompensa.setDeletedAt(null);
            entityManager.persist(recompensa);
        } else {
            throw new IllegalArgumentException("Registro não encontrado com o ID fornecido: " + id);
        }
        return recompensa;
    }

    @Override
    public boolean existsId(String id) {
        try {
            int idAsInt = Integer.parseInt(id);
            Recompensa recompensa = entityManager.find(Recompensa.class, idAsInt);
            return recompensa != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}