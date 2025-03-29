package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.RecompensadoDAO;
import com.springboot3.sb3hxh.Entity.RecompensadoEntity;
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
    public List<RecompensadoEntity> index() {
        TypedQuery<RecompensadoEntity> query = entityManager.createQuery("SELECT re FROM RecompensadoEntity re " +
                        "INNER JOIN FETCH re.hunter_id h " +
                        "INNER JOIN FETCH re.recompensa_id r " +
                        "WHERE re.deleted_at IS NULL " +
                        "ORDER BY re.id ASC", RecompensadoEntity.class);
        return query.getResultList();
    }

    @Override
    public Page<RecompensadoEntity> indexPagination(int page, int size) {
        TypedQuery<RecompensadoEntity> query = entityManager.createQuery("SELECT re FROM RecompensadoEntity re " +
                "INNER JOIN FETCH re.hunter_id h " +
                "INNER JOIN FETCH re.recompensa_id r " +
                "WHERE re.deleted_at IS NULL " +
                "ORDER BY re.id ASC", RecompensadoEntity.class);
        long totalCount = entityManager.createQuery("SELECT COUNT(r) FROM RecompensadoEntity r WHERE r.deleted_at IS NULL", Long.class).getSingleResult();
        if (totalCount == 0) {
            return Page.empty(PageRequest.of(page, size));
        }
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<RecompensadoEntity> recompensados = query.getResultList();
        return new PageImpl<>(recompensados, PageRequest.of(page, size), totalCount);
    }

    @Override
    public Page<RecompensadoEntity> searchRecompensado(String search, int page, int size) {
        TypedQuery<RecompensadoEntity> query = entityManager.createQuery("SELECT re FROM RecompensadoEntity re " +
                "INNER JOIN FETCH re.hunter_id h " +
                "INNER JOIN FETCH re.recompensa_id r " +
                "WHERE re.deleted_at IS NULL " +
                "AND (LOWER(h.nome_hunter) LIKE LOWER(:search) OR LOWER(r.descricao_recompensa) LIKE LOWER(:search)) " +
                "ORDER BY re.id ASC ", RecompensadoEntity.class);
        query.setParameter("search", "%" + search + "%");
        long totalCount = entityManager.createQuery("SELECT COUNT(re) FROM RecompensadoEntity re " +
                "INNER JOIN re.hunter_id h " +
                "INNER JOIN re.recompensa_id r " +
                "WHERE re.deleted_at IS NULL " +
                "AND (LOWER(h.nome_hunter) LIKE LOWER(:search) OR LOWER(r.descricao_recompensa) LIKE LOWER(:search))", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult();
        if (totalCount == 0) {
            return Page.empty(PageRequest.of(page, size));
        }
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<RecompensadoEntity> recompensados = query.getResultList();
        return new PageImpl<>(recompensados, PageRequest.of(page, size), totalCount);
    }

    @Override
    @Transactional
    public RecompensadoEntity create(RecompensadoEntity theRecompensadoEntity) {
        entityManager.persist(theRecompensadoEntity);
        return theRecompensadoEntity;
    }

    @Override
    public RecompensadoEntity read(int id) {
        return entityManager.find(RecompensadoEntity.class, id);
    }

    @Override
    @Transactional
    public RecompensadoEntity update(RecompensadoEntity theRecompensadoEntity) {
        RecompensadoEntity recompensadoEntity = entityManager.merge(theRecompensadoEntity);
        return recompensadoEntity;
    }

    @Override
    @Transactional
    public void trash(int id) {
        RecompensadoEntity recompensadoEntity = entityManager.find(RecompensadoEntity.class, id);
        if (recompensadoEntity != null) {
            recompensadoEntity.setDeleted_at(LocalDateTime.now());
            entityManager.merge(recompensadoEntity);
        }
    }

    @Override
    public Page<RecompensadoEntity> indexTrash(int page, int size) {
        TypedQuery<RecompensadoEntity> query = entityManager.createQuery("SELECT re FROM RecompensadoEntity re " +
                "INNER JOIN FETCH re.hunter_id h " +
                "INNER JOIN FETCH re.recompensa_id r " +
                "WHERE re.deleted_at IS NOT NULL " +
                "ORDER BY re.id ASC", RecompensadoEntity.class);
        long totalCount = entityManager.createQuery("SELECT COUNT(r) FROM RecompensadoEntity r WHERE r.deleted_at IS NOT NULL", Long.class).getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<RecompensadoEntity> recompensados = query.getResultList();
        return new PageImpl<>(recompensados, PageRequest.of(page, size), totalCount);
    }

    @Override
    public Page<RecompensadoEntity> searchRecompensadoTrash(String search, int page, int size) {
        TypedQuery<RecompensadoEntity> query = entityManager.createQuery("SELECT re FROM RecompensadoEntity re " +
                "INNER JOIN FETCH re.hunter_id h " +
                "INNER JOIN FETCH re.recompensa_id r " +
                "WHERE re.deleted_at IS NOT NULL " +
                "AND (LOWER(h.nome_hunter) LIKE LOWER(:search) OR LOWER(r.descricao_recompensa) LIKE LOWER(:search)) " +
                "ORDER BY re.id ASC ", RecompensadoEntity.class);
        query.setParameter("search", "%" + search + "%");
        long totalCount = entityManager.createQuery("SELECT COUNT(re) FROM RecompensadoEntity re " +
                        "INNER JOIN re.hunter_id h " +
                        "INNER JOIN re.recompensa_id r " +
                        "WHERE re.deleted_at IS NOT NULL " +
                        "AND (LOWER(h.nome_hunter) LIKE LOWER(:search) OR LOWER(r.descricao_recompensa) LIKE LOWER(:search))", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult();
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<RecompensadoEntity> recompensados = query.getResultList();
        return new PageImpl<>(recompensados, PageRequest.of(page, size), totalCount);
    }

    @Override
    @Transactional
    public RecompensadoEntity restore(int id) {
        RecompensadoEntity recompensadoEntity = entityManager.find(RecompensadoEntity.class, id);
        if (recompensadoEntity != null) {
            recompensadoEntity.setDeleted_at(null);
            entityManager.persist(recompensadoEntity);
        } else {
            throw new IllegalArgumentException("Registro não encontrado com o ID fornecido: " + id);
        }
        return recompensadoEntity;
    }

    @Override
    @Transactional
    public void delete(int id) {
        RecompensadoEntity recompensadoEntity = entityManager.find(RecompensadoEntity.class,id);
        entityManager.remove(recompensadoEntity);
    }

    @Override
    public String showRecompensado(int id) {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT h.nome_hunter FROM RecompensadoEntity re " +
                        "INNER JOIN re.hunter_id h " +
                        "INNER JOIN re.recompensa_id r " +
                        "WHERE re.id = :id AND re.deleted_at IS NULL", String.class
        );
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public String showRecompensadoTrash(int id) {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT h.nome_hunter FROM RecompensadoEntity re " +
                        "INNER JOIN re.hunter_id h " +
                        "INNER JOIN re.recompensa_id r " +
                        "WHERE re.id = :id AND re.deleted_at IS NOT NULL", String.class
        );
        query.setParameter("id", id);
        return query.getSingleResult();
    }

}