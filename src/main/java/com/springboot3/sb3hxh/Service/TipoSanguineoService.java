package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.*;
import com.springboot3.sb3hxh.Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoSanguineoService implements TipoSanguineoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public TipoSanguineoService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<TipoSanguineoEntity> index() {
        TypedQuery<TipoSanguineoEntity> query = entityManager.createQuery("SELECT ts FROM TipoSanguineoEntity ts WHERE ts.deleted_at IS NULL ORDER BY ts.id ASC", TipoSanguineoEntity.class);
        return query.getResultList();
    }

    public TipoSanguineoEntity read(int id) {
        return entityManager.find(TipoSanguineoEntity.class, id);
    }

    @Override
    public boolean existsId(String id) {
        try {
            int idAsInt = Integer.parseInt(id);
            TipoSanguineoEntity tipoSanguineoEntity = entityManager.find(TipoSanguineoEntity.class, idAsInt);
            return tipoSanguineoEntity != null;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
