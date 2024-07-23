package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Entity.TipoNenEntity;

import java.util.List;

public interface TipoNenDAO {

    List<TipoNenEntity> index();
    TipoNenEntity read(int id);
    boolean existsId(String id);

}