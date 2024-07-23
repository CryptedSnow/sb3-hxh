package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Entity.RecompensaEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecompensaDAO {

    List<RecompensaEntity> index();
    Page<RecompensaEntity> indexPagination(int page, int size);
    RecompensaEntity create(RecompensaEntity theRecompensaEntity);
    RecompensaEntity read(int id);
    RecompensaEntity update(RecompensaEntity theRecompensaEntity);
    void trash(int id);
    Page<RecompensaEntity> indexTrash(int page, int size);
    RecompensaEntity restore(int id);
    void delete(int id);
    Page<RecompensaEntity> searchRecompensa(String search, int page, int size);
    Page<RecompensaEntity> searchRecompensaTrash(String search, int page, int size);
    boolean existsId(String id);

}