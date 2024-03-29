package com.springboot3.sb3hxh.Service;

import com.springboot3.sb3hxh.DAO.TipoSanguineoDAO;
import com.springboot3.sb3hxh.Model.TipoSanguineoModel;
import jakarta.persistence.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class TipoSanguineoService implements TipoSanguineoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public TipoSanguineoService(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public List<TipoSanguineoModel> index() {
        TypedQuery<TipoSanguineoModel> query = entityManager.createQuery("SELECT r FROM TipoSanguineoModel r WHERE r.deleted_at IS NULL", TipoSanguineoModel.class);
        return query.getResultList();
    }

    public TipoSanguineoModel read(int id) {
        return entityManager.find(TipoSanguineoModel.class, id);
    }

}
