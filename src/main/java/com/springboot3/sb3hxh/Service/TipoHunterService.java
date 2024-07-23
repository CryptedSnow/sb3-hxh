package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.*;
import com.springboot3.sb3hxh.Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoHunterService implements TipoHunterDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public TipoHunterService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<TipoHunterEntity> index() {
        TypedQuery<TipoHunterEntity> query = entityManager.createQuery("SELECT th FROM TipoHunterEntity th WHERE th.deleted_at IS NULL ORDER BY th.id ASC", TipoHunterEntity.class);
        return query.getResultList();
    }

    public TipoHunterEntity read(int id) {
        return entityManager.find(TipoHunterEntity.class, id);
    }

    @Override
    public boolean existsId(String id) {
        try {
            int idAsInt = Integer.parseInt(id);
            TipoHunterEntity tipoHunterEntity = entityManager.find(TipoHunterEntity.class, idAsInt);
            return tipoHunterEntity != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}