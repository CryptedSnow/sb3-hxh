package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Model.TipoSanguineoModel;

import java.util.*;

public interface TipoSanguineoDAO {

    List<TipoSanguineoModel> index();
    TipoSanguineoModel read(int id);

}
