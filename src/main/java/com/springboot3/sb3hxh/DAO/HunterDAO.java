package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Entity.Hunter;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HunterDAO {

    List<Hunter> index();
    Page<Hunter> indexPagination(int page, int size);
    Page<Hunter> searchHunter(String search, int page, int size);
    Hunter create(Hunter theHunterEntity);
    Hunter read(int id);
    Hunter update(Hunter theHunterEntity);
    void trash(int id);
    Page<Hunter> indexTrash(int page, int size);
    Page<Hunter> searchHunterTrash(String search, int page, int size);
    Hunter restore(int id);
    boolean existsId(String id);

}