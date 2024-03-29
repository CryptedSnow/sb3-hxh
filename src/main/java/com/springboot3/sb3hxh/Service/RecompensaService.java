package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.RecompensaDAO;
import com.springboot3.sb3hxh.Model.RecompensaModel;
import jakarta.persistence.*;
import org.springframework.stereotype.*;
import jakarta.transaction.*;

import java.time.*;
import java.util.*;

@Service
public class RecompensaService implements RecompensaDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public RecompensaService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<RecompensaModel> index() {
        TypedQuery<RecompensaModel> query = entityManager.createQuery("SELECT r FROM RecompensaModel r WHERE r.deleted_at IS NULL", RecompensaModel.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public RecompensaModel create(RecompensaModel theRecompensaModel) {
        entityManager.persist(theRecompensaModel);
        return theRecompensaModel;
    }

    @Override
    public RecompensaModel read(int id) {
        return entityManager.find(RecompensaModel.class, id);
    }

    @Override
    @Transactional
    public RecompensaModel update(RecompensaModel theRecompensaModel) {
        RecompensaModel recompensaModel = entityManager.merge(theRecompensaModel);
        return recompensaModel;
    }

    @Override
    @Transactional
    public void trash(int id) {
        RecompensaModel recompensaModel = entityManager.find(RecompensaModel.class, id);
        if (recompensaModel != null) {
            recompensaModel.setDeletedAt(LocalDateTime.now());
            entityManager.merge(recompensaModel);
        }
    }

    @Override
    public List<RecompensaModel> indexTrash() {
        TypedQuery<RecompensaModel> query = entityManager.createQuery("SELECT r FROM RecompensaModel r WHERE r.deleted_at IS NOT NULL", RecompensaModel.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public RecompensaModel restore(int id) {
        RecompensaModel recompensaModel = entityManager.find(RecompensaModel.class, id);
        if (recompensaModel != null) {
            recompensaModel.setDeletedAt(null);
            entityManager.persist(recompensaModel);
        } else {
            throw new IllegalArgumentException("Registro não encontrado com o ID fornecido: " + id);
        }
        return recompensaModel;
    }

    @Override
    @Transactional
    public void delete(int id) {
        RecompensaModel recompensaModel = entityManager.find(RecompensaModel.class,id);
        entityManager.remove(recompensaModel);
    }

}