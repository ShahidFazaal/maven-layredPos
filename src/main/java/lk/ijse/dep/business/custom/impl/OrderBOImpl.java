package lk.ijse.dep.business.custom.impl;

import lk.ijse.dep.business.custom.OrderBO;
import lk.ijse.dep.dao.DAOFactory;
import lk.ijse.dep.dao.DAOType;
import lk.ijse.dep.dao.custom.ItemDAO;
import lk.ijse.dep.dao.custom.OrderDAO;
import lk.ijse.dep.dao.custom.OrderDetailDAO;
import lk.ijse.dep.db.DBConnection;
import lk.ijse.dep.entity.Item;
import lk.ijse.dep.entity.Order;
import lk.ijse.dep.entity.OrderDetail;
import lk.ijse.dep.util.OrderDetailTM;
import lk.ijse.dep.util.OrderTM;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class OrderBOImpl implements OrderBO {
    OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
    ItemDAO itemDAO =  DAOFactory.getInstance().getDAO(DAOType.ITEM);
    OrderDetailDAO orderDetailDAO =DAOFactory.getInstance().getDAO(DAOType.ORDER_DETAILS);
    public  String getNewOrderId() throws Exception{
            String lastOrderId = orderDAO.getLastOrderId();
            if (lastOrderId == null) {
                return "OD001";
            } else {
                int maxId = Integer.parseInt(lastOrderId.replace("OD", ""));
                maxId = maxId + 1;
                String id = "";
                if (maxId < 10) {
                    id = "OD00" + maxId;
                } else if (maxId < 100) {
                    id = "OD0" + maxId;
                } else {
                    id = "OD" + maxId;
                }
                return id;
            }
    }
    public  boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) throws Exception{
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean result = orderDAO.save(new Order(order.getOrderId(), Date.valueOf(order.getOrderDate()),order.getCustomerId()));
            if (result == false) {
                connection.rollback();
                return false;
            }

            for (OrderDetailTM orderDetail : orderDetails) {
                result = orderDetailDAO.save(new OrderDetail(order.getOrderId(), orderDetail.getCode(), orderDetail.getQty(), BigDecimal.valueOf(orderDetail.getUnitPrice())));
                if (!result){
                    connection.rollback();
                    return false;
                }
                Object entity = itemDAO.find(orderDetail.getCode());
                Item item = (Item) entity;
                item.setQtyOnHand(item.getQtyOnHand()-orderDetail.getQty());
                result = itemDAO.update(item);
                if (!result){
                    connection.rollback();
                    return false;
                }

            }
            connection.commit();
            return true;


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
