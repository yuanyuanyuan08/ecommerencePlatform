package com.example.ecommerceplatform.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Consumer extends User {
    private String name; //名字
    private String phone; //电话（登陆时用到）
    private String address;//地址
    private Map<Product,Integer>cart;//购物车： Product--商品；Integer--数量

    private List<Order> orders;// 历史订单

    public Consumer(String id, String address) {
        //构造函数，初始化Consumer对象
        super(id, "Consumer");
        this.address = address;
        this.cart = new HashMap<>();
        this.orders = new ArrayList<>();
    }


    public Map<Product,Integer>getCart() {
        //返回购物车信息（购物车信息不存储在数据库）
        return this.cart;
    }

    public List<Order> getOrders() {
        //返回此消费者的历史订单（需要联表查找）
        return this.orders;
    }
    public String getAddress() {
        //返回地址（...）
        return this.address;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public static Consumer getCustomerByPhone(String phone){
        //根据手机号查找消费者，如果查找结果为空，表明为新用户，则直接插入一条新记录(只包括id,和手机号)
        Consumer consumer = new Consumer("01","");
        consumer.name = "Wang xiao yuan";
        consumer.address = "Hong Kong";
        consumer.phone = "110";
        Map<Product,Integer>cart = consumer.getCart();
        List<Order> orderList = consumer.getOrders();
        orderList.add(new Order("01", "01", "01","fish1", 1,10.2, "Hong Kong",true,5.0,"01"));
        orderList.add(new Order("01", "01", "01","fish2", 2, 10.2,"Hong Kong",true,-1,"01"));
        orderList.add(new Order("01", "01", "01","fish3", 3, 10.2,"Hong Kong",false,-1,"01"));
        orderList.add(new Order("01", "01", "01","fish1", 1, 10.2,"Hong Kong",true,-1,"02"));
        orderList.add(new Order("01", "01", "01","fish2", 2,10.2, "Hong Kong",true,-1,"02"));
        orderList.add(new Order("01", "01", "01","fish3", 3, 10.2,"Hong Kong",true,-1,"02"));


        return consumer;

    }

    public void updateConsumerInfo(String name, String phone, String address) {
        //联表修改顾客个人信息
        this.name = name;
        this.phone = phone;
        this.address = address;

    }

    public void deleteCartItem(Product product) {
        //购物车一律无需联表
        this.cart.remove(product);
    }

    public void addItemsToCart(Product product, int quantity) {
        this.cart.put(product, cart.getOrDefault(product,0)+quantity);
    }

    public Integer getCartItemQuantity(Product item) {
        //返回购物车对应商品的数量
        return this.cart.get(item);
    }

    public void changeCartItemsQuantity(Product product, int quantity) {
        //修改购物车对应商品的数量
        this.cart.put(product, quantity);
    }

    public List<Order> createOrder() {
        //下单this.cart中的所有商品。（需要联表）

        return null;
    }

    public void setOrderFeedback(Order order, Integer score) {
        //设置评分
    }

    public void giveOrderFeedback(String string, Order order) {
        order.setFeedback(Double.valueOf(string));
        //
    }

    public void cancelOrder(Order order) {
        this.orders.remove(order);

    }

    public List<Product> getProductListBySearch(String searchStr) {
        return Product.getProductListBySearch(searchStr);
    }


    // Getters and Setters
}
