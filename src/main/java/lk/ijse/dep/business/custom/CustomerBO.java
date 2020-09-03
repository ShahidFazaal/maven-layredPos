package lk.ijse.dep.business.custom;

import lk.ijse.dep.business.SuperBO;
import lk.ijse.dep.util.CustomerTM;

import java.util.List;

public interface CustomerBO extends SuperBO {
    public  String getNewCustomerId() throws Exception;
    public List<CustomerTM> getAllCustomers()throws Exception;
    public  boolean saveCustomer(String id, String name, String address) throws Exception;
    public  boolean deleteCustomer(String customerId) throws Exception;
    public  boolean updateCustomer(String name, String address, String customerId) throws Exception;
}
