package com.example.ecommerceplatform.model;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCart(Map<Product, Integer> cart) {
        this.cart = cart;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public static Consumer getCustomerByPhone(String phone){
        //根据手机号查找消费者，如果查找结果为空，表明为新用户，则直接插入一条新记录(只包括id,和手机号)
        ResultSet resultSet = getIdByPhone(phone, "customer");
        Consumer consumer = null;
        try {
            if(resultSet.next()){
                String id = resultSet.getString("UID");
                consumer = new Consumer(id, "");
                consumer.setName(resultSet.getString("name"));
                consumer.setAddress(resultSet.getString("address"));
                consumer.setPhone(resultSet.getString("phone"));
                //填入订单信息。。。
                List<Order> orderList = User.getOrderByUid(id,"order");
                consumer.setOrders(orderList);
            }else {
                int row = addConsumer(phone);
                if(row != 0){
                    getCustomerByPhone(phone);
                }
            }
            return consumer;
        }catch (Exception e){
            e.printStackTrace();
        }
        return consumer;
    }

    public void updateConsumerInfo(String name, String phone, String address) {
        //联表修改顾客个人信息
        this.name = name;
        this.phone = phone;
        this.address = address;
        int row = updateConsumerByUid(this.getId(), name, phone, address);
        if (row != 0){
            System.out.println("Update success!");
        }
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

    public void createOrder() {
        //下单this.cart中的所有商品。（需要联表）
        Set<Product> products = this.cart.keySet();
        //随机生成orderGroupId
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        String randomSuffix = String.format("%04d", new Random().nextInt(10000));
        String orderGroupId = "GROUP-" + timestamp + "-" + randomSuffix;
        for(Product product: products){
            Order order = new Order();
            order.setConsumerId(this.getId());
            order.setMerchantId(String.valueOf(product.getVid()));
            order.setProductId(product.getPid());
            order.setAddress(this.getAddress());
            int quantity = this.cart.get(product);
            order.setQuantity(quantity);
            Double perPrice = product.getPrice();
            Double price = quantity * perPrice;
            order.setPrice(price);
            order.setProductName(product.getName());
            order.setIsShipped(false);
            String createTime = sdf.format(new Date());
            order.setCreatedDate(createTime);
            order.setOrderGroupId(orderGroupId);
            this.orders.add(order);
            User.addOrder(this.getId(), String.valueOf(product.getVid()),
                    product.getPid(),quantity,price, this.getAddress(), 1,
                    orderGroupId,createTime);
        }
    }
    public void giveOrderFeedback(String string, Order order) {
        order.setFeedback(Double.valueOf(string));
        User.updateScoreByOid(order.getOrderId(), Integer.valueOf(string));
        //
    }

    public void cancelOrder(Order order) {
        this.orders.remove(order);
        User.deleteOrderById(order.getOrderId());
    }

    public List<Product> getProductListByTag(String searchStr) {
        return Product.getProductListBySearch(searchStr);
    }

    public List<Product> getProductListByMerchantId(String searchStr) {
        return Merchant.getProductListByMerchantId(searchStr);
    }


    // Getters and Setters
}
