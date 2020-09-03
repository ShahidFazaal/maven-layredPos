package lk.ijse.dep.dao.custom.impl;

import lk.ijse.dep.dao.CrudUtil;
import lk.ijse.dep.dao.custom.OrderDetailDAO;
import lk.ijse.dep.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {


    @Override
    public List<OrderDetail> findAll() throws Exception {

            ResultSet rst = CrudUtil.execute("SELECT * FROM OrderDetails");
            ArrayList<OrderDetail> orderDetail = new ArrayList<>();
            while (rst.next()) {
                orderDetail.add(new OrderDetail(rst.getString(1),rst.getString(2),rst.getInt(3),rst.getBigDecimal(4)));
            }
            return orderDetail;

    }

    @Override
    public OrderDetail find(OrderDetailPK pk) throws Exception {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM OrderDetail WHERE orderId =? AND itemCode=?",pk.getOrderId(),pk.getItemCode());
            if (resultSet.next()) {
                return new OrderDetail(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getBigDecimal(4));
            }
            return null;

    }

    @Override
    public boolean save(OrderDetail entity) throws Exception {

            return CrudUtil.execute("INSERT INTO OrderDetail VALUES (?,?,?,?)",entity.getOrderDetailPK().getOrderId(),entity.getOrderDetailPK().getItemCode(),entity.getQty(),entity.getUnitPrice());

    }

    @Override
    public boolean update(OrderDetail entity) throws Exception{
            return CrudUtil.execute("UPDATE OrderDetails SET itemCode=?, qty=?,unitPrice=? WHERE orderId=? AND itemCode =?",entity.getOrderDetailPK().getItemCode(),entity.getQty(),entity.getUnitPrice(),entity.getOrderDetailPK().getOrderId());
    }

    @Override
    public boolean delete(OrderDetailPK key) throws Exception {
            return CrudUtil.execute("DELETE FROM OrderDetails WHERE orderId=?",key.getOrderId());

    }
}
