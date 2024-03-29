package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.TipoNenDAO;
import com.springboot3.sb3hxh.Model.TipoNenModel;
import jakarta.persistence.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class TipoNenService implements TipoNenDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public TipoNenService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<TipoNenModel> index() {
        TypedQuery<TipoNenModel> query = entityManager.createQuery("SELECT r FROM TipoNenModel r WHERE r.deleted_at IS NULL", TipoNenModel.class);
        return query.getResultList();
    }

    public TipoNenModel read(int id) {
        return entityManager.find(TipoNenModel.class, id);
    }

}