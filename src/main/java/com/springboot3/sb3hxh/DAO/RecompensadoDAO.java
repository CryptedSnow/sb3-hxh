package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Entity.Recompensado;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecompensadoDAO {

    List<Recompensado> index();
    Page<Recompensado> indexPagination(int page, int size);
    Page<Recompensado> searchRecompensado(String search, int page, int size);
    Recompensado create(Recompensado theRecompensadoEntity);
    Recompensado read(int id);
    void trash(int id);
    Page<Recompensado> indexTrash(int page, int size);
    Page<Recompensado> searchRecompensadoTrash(String search, int page, int size);
    Recompensado restore(int id);;

}