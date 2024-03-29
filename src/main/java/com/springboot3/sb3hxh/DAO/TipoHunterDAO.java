package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Model.TipoHunterModel;

import java.util.*;

public interface TipoHunterDAO {

    List<TipoHunterModel> index();
    TipoHunterModel read(int id);

}