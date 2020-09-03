package lk.ijse.dep.business.custom;

import lk.ijse.dep.business.SuperBO;
import lk.ijse.dep.util.OrderDetailTM;
import lk.ijse.dep.util.OrderTM;

import java.util.List;

public interface OrderBO extends SuperBO {
    public  String getNewOrderId() throws Exception;
    public  boolean placeOrder(OrderTM order, List<OrderDetailTM> orderDetails) throws Exception;

}
