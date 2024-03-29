package com.springboot3.sb3hxh.DAO;

import com.springboot3.sb3hxh.Model.RecompensaModel;

import java.util.*;

public interface RecompensaDAO {

    List<RecompensaModel> index();
    RecompensaModel create(RecompensaModel theRecompensaModel);
    RecompensaModel read(int id);
    RecompensaModel update(RecompensaModel theRecompensaModel);
    void trash(int id);
    List<RecompensaModel> indexTrash();
    RecompensaModel restore(int id);
    void delete(int id);

}
