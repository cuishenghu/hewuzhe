package com.hewuzhe.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/29 0029.
 */
//"data": [
//        {
//        "Id": 4,
//        "BillNo": "",
//        "Price": 0,
//        "Postage": -1,
//        "LiveryName": "",
//        "LiveryNo": "",
//        "LiveryType": "",
//        "OperateTime": "1900-01-01 00:00:00",
//        "ProList": [
                //        {
                //        "Id": 35,
                //        "MiddleImagePath": "UpLoad/Image/61a6dbf8-e9aa-4a9d-81ad-e9100c10b27b.jpg",
                //        "Number": 7,
                //        "ProductPriceTotalPrice": 29,
                //        "ProductSizeName": "L",
                //        "ProductColorName": "白色",
                //        "ProductName": "测试产品以",
                //        "ProductPriceId": 5
                //        },
public class OrderGroup implements Serializable{
    private String Id;//商品分类ID
    private String Name;//商品分类名称
    private List<OrderChild> OrderLists;//商品分类下的产品

    public OrderGroup() {
    }
    public OrderGroup(String Id, String Name, List<OrderChild> OrderLists) {
        this.Id = Id;
        this.Name = Name;
        this.OrderLists = OrderLists;
    }

    public List<OrderChild> getOrderLists() {
        return OrderLists;
    }

    public void setOrderLists(List<OrderChild> orderLists) {
        OrderLists = orderLists;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
