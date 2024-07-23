package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Entity.TipoSanguineoEntity;

import java.util.List;

public interface TipoSanguineoDAO {

    List<TipoSanguineoEntity> index();
    TipoSanguineoEntity read(int id);
    boolean existsId(String id);

}