package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Entity.Recompensa;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecompensaDAO {

    List<Recompensa> index();
    Page<Recompensa> indexPagination(int page, int size);
    Page<Recompensa> searchRecompensa(String search, int page, int size);
    Recompensa create(Recompensa theRecompensaEntity);
    Recompensa read(int id);
    Recompensa update(Recompensa theRecompensaEntity);
    void trash(int id);
    Page<Recompensa> indexTrash(int page, int size);
    Page<Recompensa> searchRecompensaTrash(String search, int page, int size);
    Recompensa restore(int id);
    boolean existsId(String id);

}