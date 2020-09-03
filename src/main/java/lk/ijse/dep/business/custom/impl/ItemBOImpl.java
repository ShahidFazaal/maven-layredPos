package lk.ijse.dep.business.custom.impl;

import lk.ijse.dep.business.custom.ItemBO;
import lk.ijse.dep.dao.DAOFactory;
import lk.ijse.dep.dao.DAOType;
import lk.ijse.dep.dao.custom.ItemDAO;
import lk.ijse.dep.entity.Item;
import lk.ijse.dep.util.ItemTM;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
    public  String getNewItemCode() throws Exception {


            String lastItemCode =itemDAO.getLastItemCode();
            if (lastItemCode == null) {
                return "I001";
            } else {
                int maxId = Integer.parseInt(lastItemCode.replace("I", ""));
                maxId = maxId + 1;
                String id = "";
                if (maxId < 10) {
                    id = "I00" + maxId;
                } else if (maxId < 100) {
                    id = "I0" + maxId;
                } else {
                    id = "I" + maxId;
                }
                return id;
            }
    }
    public  List<ItemTM> getAllItems() throws Exception {


            List<Item> allItems = itemDAO.findAll();
            ArrayList<ItemTM> items = new ArrayList<>();
            for (Object entity : allItems) {
                Item item = (Item) entity;
                items.add(new ItemTM(item.getCode(), item.getDescription(), item.getQtyOnHand(), item.getUnitPrice().doubleValue()));
            }
            return items;
    }
    public  boolean saveItem(String code, String description, int qtyOnHand, BigDecimal unitPrice) throws Exception {


            return itemDAO.save(new Item(code, description, unitPrice, qtyOnHand));
    }
    public  boolean deleteItem(String itemCode) throws Exception{

            return itemDAO.delete(itemCode);

    }
    public  boolean updateItem(String description, int qtyOnHand, BigDecimal unitPrice, String itemCode) throws Exception {

            return itemDAO.update(new Item(itemCode, description, unitPrice, qtyOnHand));
    }


}
