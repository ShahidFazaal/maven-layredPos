package lk.ijse.dep.dao;

import lk.ijse.dep.entity.SuperEntity;

import java.io.Serializable;
import java.util.List;

public interface CrudDAO <T extends SuperEntity,ID extends Serializable> extends SuperDAO {
        public List<T> findAll() throws Exception;
        public  T find(ID pk) throws Exception;
        public  boolean save(T entity) throws Exception;
        public  boolean update(T entity) throws Exception;
        public  boolean delete(ID key) throws Exception;
    }

