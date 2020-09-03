package lk.ijse.dep.business.custom.impl;

import lk.ijse.dep.business.custom.CustomerBO;
import lk.ijse.dep.dao.DAOFactory;
import lk.ijse.dep.dao.DAOType;
import lk.ijse.dep.dao.custom.CustomerDAO;
import lk.ijse.dep.entity.Customer;
import lk.ijse.dep.util.CustomerTM;

import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    public  String getNewCustomerId() throws Exception {
            String lastCustomerId = customerDAO.getLastCustomerId();
            if (lastCustomerId == null) {
                return "C001";
            } else {
                int maxId = Integer.parseInt(lastCustomerId.replace("C", ""));
                maxId = maxId + 1;
                String id = "";
                if (maxId < 10) {
                    id = "C00" + maxId;
                } else if (maxId < 100) {
                    id = "C0" + maxId;
                } else {
                    id = "C" + maxId;
                }
                return id;
            }
    }
    public  List<CustomerTM> getAllCustomers() throws Exception {
            List<Customer> allCustomers = customerDAO.findAll();
            ArrayList<CustomerTM> customers = new ArrayList<>();
            for (Object entity : allCustomers) {
                Customer customer = (Customer) entity;
                customers.add(new CustomerTM(customer.getId(), customer.getName(), customer.getAddress()));
            }
            return customers;

    }
    public  boolean saveCustomer(String id, String name, String address) throws Exception {

            return customerDAO.save(new Customer(id, name, address));
    }
    public  boolean deleteCustomer(String customerId) throws Exception {

            return customerDAO.delete(customerId);

    }
    public  boolean updateCustomer(String name, String address, String customerId) throws Exception {
            return customerDAO.update(new Customer(customerId, name, address));

    }
}
