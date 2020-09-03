package lk.ijse.dep.dao.custom.impl;

import lk.ijse.dep.dao.CrudUtil;
import lk.ijse.dep.dao.custom.CustomerDAO;
import lk.ijse.dep.entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public String getLastCustomerId() throws Exception {
            ResultSet rst = CrudUtil.execute("SELECT * FROM Customer ORDER BY id DESC LIMIT 1");
            if (!rst.next()){
                return null;
            }else{
                return rst.getString(1);
            }
    }

    @Override
    public List<Customer> findAll() throws Exception {

            ResultSet rst = CrudUtil.execute("SELECT * FROM Customer");
            ArrayList<Customer> customers = new ArrayList<>();
            while (rst.next()) {
                customers.add(new Customer(rst.getString(1),rst.getString(2),rst.getString(3)));
            }
            return customers;
    }

    @Override
    public Customer find(String pk) throws Exception {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Customer WHERE id =?", pk);

            if (resultSet.next()) {
                return new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
            }
            return null;
    }

    @Override
    public boolean save(Customer customer) throws Exception {

            return CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?)",customer.getId(),customer.getName(),customer.getAddress());

    }

    @Override
    public boolean update(Customer customer) throws Exception {
            return CrudUtil.execute("UPDATE Customer SET name=?, address=? WHERE id=?",customer.getName(),customer.getAddress(),customer.getId());
    }

    @Override
    public boolean delete(String key) throws  Exception{

            return CrudUtil.execute("DELETE FROM Customer WHERE id=?",key);

    }
}
