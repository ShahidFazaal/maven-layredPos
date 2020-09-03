package lk.ijse.dep.dao.custom.impl;

import lk.ijse.dep.dao.CrudUtil;
import lk.ijse.dep.dao.custom.OrderDAO;
import lk.ijse.dep.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public String getLastOrderId() throws Exception {

            ResultSet rst = CrudUtil.execute("SELECT * FROM `Order` ORDER BY id DESC LIMIT 1");
            if (!rst.next()){
                return null;
            }else{
                return rst.getString(1);
            }
    }

    @Override
    public List<Order> findAll() throws Exception {

            ResultSet rst = CrudUtil.execute("SELECT * FROM `Order`");
            ArrayList<Order> orders = new ArrayList<>();
            while (rst.next()) {
                orders.add(new Order(rst.getString(1),rst.getDate(2),rst.getString(3)));
            }
            return orders;

    }

    @Override
    public Order find(String pk) throws Exception {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM `Order` WHERE id =?",pk);
            if (resultSet.next()) {
                return new Order(resultSet.getString(1),resultSet.getDate(2),resultSet.getString(3));
            }
            return null;

    }

    @Override
    public boolean save(Order order) throws Exception {

            return CrudUtil.execute("INSERT INTO `Order` VALUES (?,?,?)",order.getId(),order.getDate(),order.getCustomerId());

    }

    @Override
    public boolean update(Order order) throws Exception {
            return CrudUtil.execute("UPDATE `Order` SET date=?, customerId=? WHERE id=?",order.getDate(),order.getCustomerId(),order.getId());

    }

    @Override
    public boolean delete(String key) throws Exception {

            return CrudUtil.execute("DELETE FROM `Order` WHERE id=?",key);

    }
}
