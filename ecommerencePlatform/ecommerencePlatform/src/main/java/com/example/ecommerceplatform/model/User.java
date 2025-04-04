package com.example.ecommerceplatform.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String role; // "Merchant" or "Consumer"

    public User(String id, String role) {
        this.id = id;
        this.role = role;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public static ResultSet getIdByPhone(String phone, String table){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "select * from `"+table+"` where phone=\'"+ phone+"\'";

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            //为空则插入新用户 id: phone:

            return queryOutput;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Product> getProductByVid(String vid, String table){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "select * from `"+table+"` where vid='"+ vid+"'";

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            List<Product> productList = new ArrayList<>();
            while (queryOutput.next()){
                Product product = new Product();
                product.setPid(queryOutput.getInt("pid"));
                product.setVid(Integer.valueOf(vid));
                product.setName(queryOutput.getString(3));
                product.setPrice(queryOutput.getDouble(4));
                product.setStock(queryOutput.getInt(5));
                product.setTags(queryOutput.getString("tag"));
                productList.add(product);
            }

            return productList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Product> getProduct(){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "select * from `product`";

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            List<Product> productList = new ArrayList<>();
            while (queryOutput.next()){
                Product product = new Product();
                product.setPid(queryOutput.getInt("pid"));
                product.setVid(queryOutput.getInt("vid"));
                product.setName(queryOutput.getString(3));
                product.setPrice(queryOutput.getDouble(4));
                product.setStock(queryOutput.getInt(5));
                product.setTags(queryOutput.getString("tag"));
                productList.add(product);
            }

            return productList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Product getProductById(String id){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "select * from `product` where pid=\'"+id+"\'";

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            Product product = new Product();
            if (queryOutput.next()){
                product.setPid(queryOutput.getInt("pid"));
                product.setVid(queryOutput.getInt("vid"));
                product.setName(queryOutput.getString(3));
                product.setPrice(queryOutput.getDouble(4));
                product.setStock(queryOutput.getInt(5));
                product.setTags(queryOutput.getString("tag"));
            }

            return product;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Order> getOrderByVid(String vid, String table){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "select * from `"+table+"` where vid='"+ vid+"'";

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            List<Order> orderList = new ArrayList<>();
            while (queryOutput.next()){
                Order order = new Order();
                order.setOrderId(queryOutput.getString("order_id"));
                order.setConsumerId(queryOutput.getString("uid"));
                order.setMerchantId(queryOutput.getString("vid"));
                String pid = queryOutput.getString("pid");
                order.setProductId(pid);
                order.setQuantity(queryOutput.getInt("total_quantity"));
                order.setOrderGroupId(queryOutput.getString("order_group"));
                order.setAddress(queryOutput.getString("address"));
                order.setIsShipped(queryOutput.getInt("isShipped") == 2);
                order.setFeedback(queryOutput.getDouble("feedback"));
                order.setCreatedDate(queryOutput.getString("created_at"));
                order.setEndDate(queryOutput.getString("ended_at"));
                String productName = getProductNameByProductId(pid);
                order.setProductName(productName);
                Double productPrice = getProductPriceByProductId(pid);
                order.setPrice(productPrice);
                orderList.add(order);
            }

            return orderList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Order> getOrderByUid(String uid, String table){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "select * from `"+table+"` where `uid`='"+ uid+"'";

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            List<Order> orderList = new ArrayList<>();
            while (queryOutput.next()){
                Order order = new Order();
                order.setOrderId(queryOutput.getString("order_id"));
                order.setConsumerId(queryOutput.getString("uid"));
                order.setMerchantId(queryOutput.getString("vid"));
                String pid = queryOutput.getString("pid");
                order.setProductId(pid);
                order.setQuantity(queryOutput.getInt("total_quantity"));
                order.setOrderGroupId(queryOutput.getString("order_group"));
                order.setAddress(queryOutput.getString("address"));
                order.setIsShipped(queryOutput.getInt("isShipped") == 2);
                order.setFeedback(queryOutput.getDouble("feedback"));
                order.setCreatedDate(queryOutput.getString("created_at"));
                order.setEndDate(queryOutput.getString("ended_at"));
                String productName = getProductNameByProductId(pid);
                order.setProductName(productName);
                Double productPrice = getProductPriceByProductId(pid);
                order.setPrice(productPrice);
                orderList.add(order);
            }

            return orderList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Order getOrderById(String id, String table){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "select * from `"+table+"` where `order_id`='"+ id+"'";

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            Order order = new Order();
            if (queryOutput.next()){
                order.setOrderId(queryOutput.getString("order_id"));
                order.setConsumerId(queryOutput.getString("uid"));
                order.setMerchantId(queryOutput.getString("vid"));
                String pid = queryOutput.getString("pid");
                order.setProductId(pid);
                order.setQuantity(queryOutput.getInt("total_quantity"));
                order.setOrderGroupId(queryOutput.getString("order_group"));
                order.setAddress(queryOutput.getString("address"));
                order.setIsShipped(queryOutput.getInt("isShipped") == 2);
                order.setFeedback(queryOutput.getDouble("feedback"));
                order.setCreatedDate(queryOutput.getString("created_at"));
                order.setEndDate(queryOutput.getString("ended_at"));
                String productName = getProductNameByProductId(pid);
                order.setProductName(productName);
                Double productPrice = getProductPriceByProductId(pid);
                order.setPrice(productPrice);
            }

            return order;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getProductNameByProductId(String pid){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "select * from `product` where pid='"+ pid+"'";

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            if (queryOutput.next()){
                String name = queryOutput.getString("name");
                return name;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getMerchantNameByMerchantId(String vid){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "select * from `vendor` where VID='"+ vid+"'";

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            if (queryOutput.next()){
                String name = queryOutput.getString("name");
                return name;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Double getProductPriceByProductId(String pid){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "select * from `product` where pid='"+ pid+"'";

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            if (queryOutput.next()){
                Double price = queryOutput.getDouble("price");
                return price;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Double getScoreByVid(String vid){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "select `feedback` from `order` where vid='"+ vid+"'";

        try{
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            Double result = 0.0;
            int i = 0;
            while (queryOutput.next()){
                Double score = queryOutput.getDouble("feedback");
                if(score != -1) {
                    result += score;
                    i++;
                }
            }
            return result/i;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public static List<Product> getProductByTags(String tag){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "SELECT * \n" +
                "FROM `product`\n" +
                "WHERE `tag` LIKE '%"+tag+"%'" +// 搜索包含"tag"标签的产品
                "   OR `name` LIKE '%"+tag+"%'; ";
        try{
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            List<Product> productList = new ArrayList<>();
            if (queryOutput.next()){
                Product product = new Product();
                product.setPid(queryOutput.getInt("pid"));
                product.setVid(queryOutput.getInt("vid"));
                product.setName(queryOutput.getString(3));
                product.setPrice(queryOutput.getDouble(4));
                product.setStock(queryOutput.getInt(5));
                product.setTags(queryOutput.getString("tag"));
                productList.add(product);
            }
            return productList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int deleteProductById(String pid){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "delete from `product` where pid='"+ pid+"'";

        try{
            Statement statement = connectionDB.createStatement();
            int row = statement.executeUpdate(connectQuery);
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int deleteOrderById(String order_id){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "delete from `order` where order_id='"+ order_id+"'";

        try{
            Statement statement = connectionDB.createStatement();
            int row = statement.executeUpdate(connectQuery);
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int addMerchant(String phone){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "INSERT INTO `vendor` (`phone`) VALUES ('"+phone+"')";

        try{
            Statement statement = connectionDB.createStatement();
            int row = statement.executeUpdate(connectQuery);
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int addConsumer(String phone){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "INSERT INTO `customer` (`phone`) VALUES ('"+phone+"')";

        try{
            Statement statement = connectionDB.createStatement();
            int row = statement.executeUpdate(connectQuery);
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int addProduct(String name,String price,String stock,String merchantId,String tags){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "INSERT INTO `product` (`name`, `price`, `quantity`, `vid`, `tag`) VALUES ('"
                +name+"','"+price+"','"+stock+"','"+merchantId+"','"+tags+"')";

        try{
            Statement statement = connectionDB.createStatement();
            int row = statement.executeUpdate(connectQuery);
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int addOrder(String consumerId, String merchantId,
                               String productId,int quantity,double price,String address,
                               int isShipped,String orderGroupId, String createTime){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "INSERT INTO `order` (" +
                "    vid,\n" +
                "    pid,\n" +
                "    uid,\n" +
                "    isShipped,\n" +
                "    price,\n"+
                "    total_quantity,\n" +
                "    order_group,\n" +
                "    address,\n" +
                "    created_at) VALUES ('"
                +merchantId+"','"+productId+"','"+consumerId+"','"+ isShipped
                +"','"+price+"','"+quantity+"','"+orderGroupId+"','"+address+"','"+createTime+"')";

        try{
            Statement statement = connectionDB.createStatement();
            int row = statement.executeUpdate(connectQuery);
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int updateMerchantByVid(String vid, String newShopname, String newPhone, String newLocation){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "update `vendor` " +
                "set `name`= '" + newShopname +
                "', `phone` = '" + newPhone +
                "', `location` = '" + newLocation +
                "' where `VID`='"+ vid+"'";

        try{
            Statement statement = connectionDB.createStatement();
            int row = statement.executeUpdate(connectQuery);
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int updateProductByPid(String pid, String name, String price, String stock, String tag){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "update `product` " +
                "set `name`= '" + name +
                "', `price` = '" + price +
                "', `quantity` = '" + stock +
                "', `tag` = '" + tag +
                "' where `pid`='"+ pid+"'";

        try{
            Statement statement = connectionDB.createStatement();
            int row = statement.executeUpdate(connectQuery);
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int updateConsumerByUid(String uid, String name, String phone, String address){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "update `customer` " +
                "set `name`= '" + name +
                "', `phone` = '" + phone +
                "', `address` = '" + address +
                "' where `UID`='"+ uid+"'";

        try{
            Statement statement = connectionDB.createStatement();
            int row = statement.executeUpdate(connectQuery);
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int updateOrderShipByOid(String orderId){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "update `order` " +
                "set `isShipped`= '2' ," +
                "`ended_at` = CASE \n" +
                "        WHEN `isShipped` = '2' THEN CURRENT_TIMESTAMP \n" +
                "        ELSE ended_at \n " +
                "   END " +
                "where `order_id`='"+ orderId+"'";

        try{
            Statement statement = connectionDB.createStatement();
            int row = statement.executeUpdate(connectQuery);
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int updateStockByPid(String pid, Integer stock){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "update `product` " +
                "set `quantity`= '" + stock +
                "' where `pid`='"+ pid+"'";

        try{
            Statement statement = connectionDB.createStatement();
            int row = statement.executeUpdate(connectQuery);
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int updateScoreByOid(String order_id, Integer score){
        //
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection();
        String connectQuery = "update `order` " +
                "set `feedback`= '" + score +
                "' where `order_id`='"+ order_id+"'";

        try{
            Statement statement = connectionDB.createStatement();
            int row = statement.executeUpdate(connectQuery);
            return row;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}