package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.RecompensadoDAO;
import com.springboot3.sb3hxh.Model.RecompensadoModel;
import jakarta.persistence.*;
import jakarta.transaction.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

@Service
public class RecompensadoService implements RecompensadoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public RecompensadoService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<RecompensadoModel> index() {
        TypedQuery<RecompensadoModel> query = entityManager.createQuery("SELECT r FROM RecompensadoModel r WHERE r.deleted_at IS NULL", RecompensadoModel.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public RecompensadoModel create(RecompensadoModel theRecompensadoModel) {
        entityManager.persist(theRecompensadoModel);
        return theRecompensadoModel;
    }

    @Override
    public RecompensadoModel read(int id) {
        return entityManager.find(RecompensadoModel.class, id);
    }

    @Override
    @Transactional
    public RecompensadoModel update(RecompensadoModel theRecompensadoModel) {
        RecompensadoModel recompensadoModel = entityManager.merge(theRecompensadoModel);
        return recompensadoModel;
    }

    @Override
    @Transactional
    public void trash(int id) {
        RecompensadoModel recompensadoModel = entityManager.find(RecompensadoModel.class, id);
        if (recompensadoModel != null) {
            recompensadoModel.setDeleted_at(LocalDateTime.now());
            entityManager.merge(recompensadoModel);
        }
    }

    @Override
    public List<RecompensadoModel> indexTrash() {
        TypedQuery<RecompensadoModel> query = entityManager.createQuery("SELECT r FROM RecompensadoModel r WHERE r.deleted_at IS NOT NULL", RecompensadoModel.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public RecompensadoModel restore(int id) {
        RecompensadoModel recompensadoModel = entityManager.find(RecompensadoModel.class, id);
        if (recompensadoModel != null) {
            recompensadoModel.setDeleted_at(null);
            entityManager.persist(recompensadoModel);
        } else {
            throw new IllegalArgumentException("Registro não encontrado com o ID fornecido: " + id);
        }
        return recompensadoModel;
    }

    @Override
    @Transactional
    public void delete(int id) {
        RecompensadoModel recompensadoModel = entityManager.find(RecompensadoModel.class,id);
        entityManager.remove(recompensadoModel);
    }

}
