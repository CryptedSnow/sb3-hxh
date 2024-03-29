package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Model.TipoNenModel;

import java.util.*;

public interface TipoNenDAO {

    List<TipoNenModel> index();
    TipoNenModel read(int id);

}