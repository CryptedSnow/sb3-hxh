package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Model.RecompensadoModel;

import java.util.*;

public interface RecompensadoDAO {

    List<RecompensadoModel> index();
    RecompensadoModel create(RecompensadoModel theRecompensadoModel);
    RecompensadoModel read(int id);
    RecompensadoModel update(RecompensadoModel theRecompensadoModel);
    void trash(int id);
    List<RecompensadoModel> indexTrash();
    RecompensadoModel restore(int id);
    void delete(int id);

}