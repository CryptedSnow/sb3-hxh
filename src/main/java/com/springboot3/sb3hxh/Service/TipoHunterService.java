package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.TipoHunterDAO;
import com.springboot3.sb3hxh.Model.TipoHunterModel;
import jakarta.persistence.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class TipoHunterService implements TipoHunterDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public TipoHunterService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<TipoHunterModel> index() {
        TypedQuery<TipoHunterModel> query = entityManager.createQuery("SELECT r FROM TipoHunterModel r WHERE r.deleted_at IS NULL", TipoHunterModel.class);
        return query.getResultList();
    }

    public TipoHunterModel read(int id) {
        return entityManager.find(TipoHunterModel.class, id);
    }

}