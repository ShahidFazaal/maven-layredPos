package lk.ijse.dep.dao.custom.impl;

import lk.ijse.dep.dao.CrudUtil;
import lk.ijse.dep.dao.custom.ItemDAO;
import lk.ijse.dep.entity.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public String getLastItemCode() throws Exception {

            ResultSet rst = CrudUtil.execute("SELECT * FROM Item ORDER BY code DESC LIMIT 1");

            if (!rst.next()){
                return null;
            }else{
                return rst.getString(1);
            }
    }

    @Override
    public List<Item> findAll() throws Exception {

            ResultSet rst = CrudUtil.execute("SELECT * FROM Item");
            ArrayList<Item> items = new ArrayList<>();
            while (rst.next()) {
                items.add(new Item(rst.getString(1),rst.getString(2),rst.getBigDecimal(3),rst.getInt(4)));
            }
            return items;
    }

    @Override
    public Item find(String pk) throws Exception {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item WHERE code =?",pk);
            if (resultSet.next()) {
                return new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getBigDecimal(3), resultSet.getInt(4));
            }
            return null;
    }

    @Override
    public boolean save(Item item) throws Exception {

            return CrudUtil.execute("INSERT INTO Item VALUES (?,?,?,?)",item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());

    }

    @Override
    public boolean update(Item item) throws Exception {

            return CrudUtil.execute("UPDATE Item SET description=?, unitPrice=?,qtyOnHand=? WHERE code=?",item.getDescription(),item.getUnitPrice(),item.getQtyOnHand(),item.getCode());

    }

    @Override
    public boolean delete(String key) throws Exception {

            return CrudUtil.execute("DELETE FROM Item WHERE code=?",key);

    }
}
