package lk.ijse.dep.dao.custom;

import lk.ijse.dep.dao.SuperDAO;
import lk.ijse.dep.entity.CustomEntity;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    CustomEntity getOrderDetail(String orderId) throws Exception;
    CustomEntity getOrderDetail2(String orderId) throws Exception;
    List<CustomEntity> getAllDetails() throws Exception;
}
