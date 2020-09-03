package lk.ijse.dep.dao.custom.impl;

import lk.ijse.dep.dao.CrudUtil;
import lk.ijse.dep.dao.custom.QueryDAO;
import lk.ijse.dep.entity.CustomEntity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public CustomEntity getOrderDetail(String orderId) throws Exception {
            ResultSet resultSet =  CrudUtil.execute("SELECT o.id,c.name,o.date FROM `Order`o INNER JOIN Customer c ON o.customerId = c.id WHERE o.id =?",orderId);
            if (resultSet.next()) {
                return new CustomEntity(resultSet.getString(1),resultSet.getString(2),resultSet.getDate(3));
            }
            return null;

    }


    @Override
    public CustomEntity getOrderDetail2(String orderId) throws Exception {

            ResultSet resultSet = CrudUtil.execute("SELECT o.id,c.name,c.id FROM `Order`o INNER JOIN Customer c ON o.customerId = c.id WHERE c.id=?",orderId);
            if (resultSet.next()) {
                return new CustomEntity(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
            }
            return null;

    }

    @Override
    public List<CustomEntity> getAllDetails() throws Exception{

            ResultSet resultSet = CrudUtil.execute("SELECT o.id,o.date,c.name,c.id,SUM(od.qty * od.unitPrice) as 'total' FROM `Order`o INNER JOIN Customer c ON o.customerId = c.id INNER JOIN OrderDetail od on o.id = od.orderId GROUP BY 1");
            ArrayList<CustomEntity> allOrders = new ArrayList<>();
            while (resultSet.next()){
                allOrders.add(new CustomEntity(resultSet.getString(1),resultSet.getString(3),resultSet.getDate(2),resultSet.getString(4),resultSet.getInt(5)));
            }
            return allOrders;

    }
}
