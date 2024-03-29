package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Model.HunterModel;

import java.util.*;

public interface HunterDAO {

    List<HunterModel> index();
    HunterModel create(HunterModel theHunterModel);
    HunterModel read(int id);
    HunterModel update(HunterModel theHunterModel);
    void trash(int id);
    List<HunterModel> indexTrash();
    HunterModel restore(int id);
    void delete(int id);

}