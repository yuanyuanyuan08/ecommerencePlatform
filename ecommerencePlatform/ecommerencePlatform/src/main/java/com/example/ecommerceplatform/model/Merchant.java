package com.example.ecommerceplatform.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Merchant.java
public class Merchant extends User {
    private String address; //地址
    private String phone;//手机号
    private String shopname;//商店名

    private Double score;

    private List<Product> products;//商品信息列表
    private List<Order> orders;//订单信息列表





    public Merchant(String id, String phone) {
        //构造函数
        super(id, "Merchant");
        this.address = "";
        this.phone = phone;
        this.shopname = "";
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void setScore(Double score) {
        this.score = score;
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
        this.products = User.getProductByVid(this.getId(),"product");

        return this.products;
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
        int row = updateMerchantByVid(this.getId(), newShopname, newPhone, newLocation);
        if (row != 0){
            System.out.println("Update success!");
        }

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
            if (resultSet.next()) {
                String id = resultSet.getString("VID");
                merchant = new Merchant(id, "");
                merchant.setShopname(resultSet.getString("name"));
                merchant.setPhone(resultSet.getString("phone"));
                merchant.setScore(Double.valueOf(resultSet.getString("score")));
                merchant.setAddress(resultSet.getString("location"));

                //填入订单信息和商品信息。。。
                List<Product> products = getProductByVid(id,"product");
                //Product(name,price,stock, merchantId,tags)
                merchant.setProducts(products);

                List<Order> orderList = getOrderByVid(id,"order");
                merchant.setOrders(orderList);

            }else{
                int row = addMerchant(phone);
                if(row != 0){
                    getMerchantInfoByPhone(phone);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return merchant;
    }

    public static List<Product> getProductListByMerchantId(String id){ // 查找数据库，返回Product对象

        List<Product> products = getProductByVid(id,"product");
        //Product(name,price,stock, merchantId,tags)
        return products;
    }

    public void deleteProductItem(Product product) {
        //删除对应商品信息，this.products和数据库都要删除
        this.products.remove(product);
        if(deleteProductById(product.getPid()) != 0){
            System.out.println("Delete success!");
        }
    }

    public void addNewProduct(String name, String price, String stock, String tag1,String tag2,String tag3) {
        //添加新商品，需要联表
        System.out.println("add new product");
        List<String> tagList = new ArrayList<>();
        if (tag1 != null && !tag1.isEmpty()) tagList.add(tag1);
        if (tag2 != null && !tag2.isEmpty()) tagList.add(tag2);
        if (tag3 != null && !tag3.isEmpty()) tagList.add(tag3);
        String tags = String.join(",", tagList);
        Product product = new Product(name, Double.parseDouble(price), Integer.parseInt(stock), this.getId(), tags);
        addProduct(name,price,stock,this.getId(),tags);
        this.products.add(product);

    }

    public void modifyProductInfo(String name, String price, String stock, String tag1,String tag2,String tag3,Product product) {
        //修改商品Product 的 name price stock tags，需要联表(直接删掉重加了）
        System.out.println("Modify Product");
        this.products.remove(product);
        List<String> tagList = new ArrayList<>();
        if (tag1 != null && !tag1.isEmpty()) tagList.add(tag1);
        if (tag2 != null && !tag2.isEmpty()) tagList.add(tag2);
        if (tag3 != null && !tag3.isEmpty()) tagList.add(tag3);
        String tags = String.join(",", tagList);
        Product product2 = new Product(name, Double.parseDouble(price), Integer.parseInt(stock), this.getId(), tags);
        this.products.add(product2);
        int row = updateProductByPid(product.getPid(), name, price, stock, tags);
        if (row >= 0){
            System.out.println("Modify success!");
        }
    }

    public String getScore() {
        //根据历史订单的评分计算平均分
        //计算店铺得分
        Double score = getScoreByVid(this.getId());
        return "Score: "+score;
    }
    public void shipOrder(Order order){
//        this.orders.remove(order);
        order.setIsShipped(true);

        Product product = getProductById(order.getProductId());
        int productStock = product.getStock();
        int stock = productStock - order.getQuantity();
        this.products.stream().filter(product1 -> product1.getPid()!=product.getPid()).collect(Collectors.toList()).get(0).setStock(stock);
//        this.orders.add()
        //联表修改订单信息
        int row = updateOrderShipByOid(order.getOrderId());
        int row1 = updateStockByPid(product.getPid(), stock);
        if(row != 0 && row1 != 0){
            System.out.println("Change success!");
        }
        Order order1 = getOrderById(order.getOrderId(), "order");
        String endDate = order1.getEndDate();
        order.setEndDate(endDate);

    }


}