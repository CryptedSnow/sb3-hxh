package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Entity.HunterEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HunterDAO {

    List<HunterEntity> index();
    Page<HunterEntity> indexPagination(int page, int size);
    HunterEntity create(HunterEntity theHunterEntity);
    HunterEntity read(int id);
    HunterEntity update(HunterEntity theHunterEntity);
    void trash(int id);
    Page<HunterEntity> indexTrash(int page, int size);
    HunterEntity restore(int id);
    void delete(int id);
    Page<HunterEntity> searchHunter(String search, int page, int size);
    Page<HunterEntity> searchHunterTrash(String search, int page, int size);
    boolean existsId(String id);

}