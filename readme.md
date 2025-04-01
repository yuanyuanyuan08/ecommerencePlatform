#  项目介绍：


# 功能划分
1. Login page
   - Merchant SearchMerchantById(String phone)
   - Consumer SearchConsumerById(String phone)
   - ![img.png](img.png)
2. MerchantView:(确定Merchant 类中的各个getter类是否返回正确信息)
   - tab1:主页
      - merchant.updateMerchantInfo(String shopname,String phone, String address)
      - merchant.modifyProductInfo
      - merchant.deleteProductItem
      - merchant.addNewProduct
      - ![img_3.png](img_3.png)
   - tab2:订单页
      - merchant.shipOrder
      - ![img_4.png](img_4.png)
4. ConsumerView:
   - tab1:主页
      - consumer.updateConsumerInfo
      - consumer.getProductListBySearch
      - ![img_1.png](img_1.png)
   - tab2：个人主页
      - consumer.createOrder
      - consumer.giveOrderFeedback
      - consumer.cancelOrder
      - ![img_2.png](img_2.png)
      
