package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Entity.RecompensadoEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecompensadoDAO {

    List<RecompensadoEntity> index();
    Page<RecompensadoEntity> indexPagination(int page, int size);
    RecompensadoEntity create(RecompensadoEntity theRecompensadoEntity);
    RecompensadoEntity read(int id);
    String showRecompensado(int id);
    String showRecompensadoTrash(int id);
    RecompensadoEntity update(RecompensadoEntity theRecompensadoEntity);
    void trash(int id);
    Page<RecompensadoEntity> indexTrash(int page, int size);
    RecompensadoEntity restore(int id);
    void delete(int id);
    Page<RecompensadoEntity> searchRecompensado(String search, int page, int size);
    Page<RecompensadoEntity> searchRecompensadoTrash(String search, int page, int size);

}