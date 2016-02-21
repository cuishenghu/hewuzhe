package com.hewuzhe.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/23 0023.
 */
public class OrderChild implements Serializable {
    //            "Name": "测试产品以积分兑换",
//            "ImagePath": "UpLoad/Image/61a6dbf8-e9aa-4a9d-81ad-e9100c10b27b.jpg",
//            "Price": 25,
//            "PublishTime": "2016-01-28 00:00:00",
//            "SaleNum": 22,
//            "CommentNum": 2,
//            "ProductCategoryName": "三级分类一二二",
//            "VisitNum": 90,
//            "FavoriteNum": 5

    private String Id;//ID
    private String ImagePath;//图片
    private String Name;//名称
    private String VisitNum;//关注度
    private String Price;//价格
    private String Price2;//原来价格
    private String SaleNum;//销售数量
    private String ProductCategoryName;//所属分类名
    private String FavoriteNum;//收藏人数
    private String Special;//规格
    private String orderNum;//订单的数量
    private String CommentNum;//评论条数

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public OrderChild() {
    }

    public OrderChild(String Id, String Name, String Special, String Price, String SaleNum) {
        this.Id = Id;
        this.Name = Name;
        this.Price = Price;
        this.Special = Special;
        this.SaleNum = SaleNum;

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getVisitNum() {
        return VisitNum;
    }

    public void setVisitNum(String visitNum) {
        VisitNum = visitNum;
    }

    public String getSaleNum() {
        return SaleNum;
    }

    public void setSaleNum(String saleNum) {
        SaleNum = saleNum;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getProductCategoryName() {
        return ProductCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        ProductCategoryName = productCategoryName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPrice2() {
        return Price2;
    }

    public void setPrice2(String price2) {
        Price2 = price2;
    }

    public String getFavoriteNum() {
        return FavoriteNum;
    }

    public void setFavoriteNum(String favoriteNum) {
        FavoriteNum = favoriteNum;
    }

    public String getCommentNum() {
        return CommentNum;
    }

    public void setCommentNum(String commentNum) {
        CommentNum = commentNum;
    }

    public String getSpecial() {
        return Special;
    }

    public void setSpecial(String special) {
        Special = special;
    }
}
