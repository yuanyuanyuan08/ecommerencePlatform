package com.example.ecommerceplatform.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Merchant.java
public class Merchant extends User {
    private String address;
    private String phone;
    private String shopname;

    private List<Product> products;
    private List<Order> orders;





    public Merchant(String id, String phone) { // 根据需要修改
        super(id, "Merchant");
        this.address = "";
        this.phone = phone;
        this.shopname = "";
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getId() {
        return super.getId();
    }



    public String getPhone() {
        return phone;
    }

    public String getShopname() {
        return shopname;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Order> getOrders() {

        return this.orders;
    }

    public String getAddress() {
        return this.address;
    }

    public void updateMerchantInfo(String newShopname, String newPhone, String newLocation) {


        System.out.println("update Info");

        // merchant 修改信息
        this.setShopname(newShopname);
        this.setPhone(newPhone);
        this.setAddress(newLocation);

    }

    // Getters and Setters

    public static  ResultSet getMerchantInfoByPhone(String phone){ //根据phone 查找
        ResultSet merchantInfo = User.getIdByPhone(phone, "vendor");
        return merchantInfo;
    }


    public static Merchant getMerchantByPhone(String phone){ // 查找数据库，返回Merchant对象 如果数据库中没有对应用户，就注册新用户

        ResultSet resultSet = getMerchantInfoByPhone(phone);
        //数据库返回的数据 填入 merchant对象
        Merchant merchant = null;

        try{
            while (resultSet.next()) {
                String id = resultSet.getString("idvendor");
                merchant = new Merchant(id, "");
                merchant.setShopname(resultSet.getString("name"));
                merchant.setPhone(resultSet.getString("phone"));
                merchant.setAddress("Hong Kong");

                //填入订单信息和商品信息。。。
                List<Product> productList = new ArrayList<>();
                //Product(name,price,stock, merchantId,tags)
                productList.add(new Product("fish1", 10.2, 10, "01", "food,seafood"));
                productList.add(new Product("fish2", 10.2, 10, "01", "food,seafood"));
                productList.add(new Product("fish3", 10.2, 10, "01", "food,seafood"));
                merchant.setProducts(productList);

                List<Order> orderList = new ArrayList<>();
                orderList.add(new Order("01", "01", "01","fish1", 1,10.2, "Hong Kong",true,5.0,"01"));
                orderList.add(new Order("01", "01", "01", "fish1",2,10.2, "Hong Kong",false,-1,"01"));
                orderList.add(new Order("01", "01", "01", "fish1",3,10.2, "Hong Kong",false,-1,"01"));
                merchant.setOrders(orderList);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return merchant;
    }

    public void deleteProductItem(Product product) {
        //删除对应商品信息，this.products和数据库都要删除
    }

    public void modifyProductInfo(String name, String price, String stock, String tags) {
        System.out.println("Modify Product");
    }

    public void addNewProduct(String name, String price, String stock, String tags) {
        //
        System.out.println("add new product");
        this.products.add(Product.addNewProduct(name, Double.valueOf(price), Integer.valueOf(stock), this.getId(), tags));
    }

    public String getScore() {
        String score = "";//计算店铺得分
        return "Score: "+score;
    }


}