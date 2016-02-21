package com.hewuzhe.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/23 0023.
 */
public class ProductScore implements Serializable {
    //            "Id": 3,
//            "Name": "测试产品以积分兑换",
//            "ImagePath": "UpLoad/Image/61a6dbf8-e9aa-4a9d-81ad-e9100c10b27b.jpg",
//            "Price": 25,
//            "PublishTime": "2016-01-28 00:00:00",
//            "SaleNum": 22,
//            "CommentNum": 2,
//            "ProductCategoryName": "三级分类一二二",
//            "VisitNum": 117,
//            "FavoriteNum": 5
    private String Id;//ID,也是商品Id,其他里面用  服务器没有统一
    private String ProductId;//商品Id  兑换记录用
    private String BillDetailId;//兑换记录的ID
    private String Name;//名称
    private String ImagePath;//图片
    private String Price;//价格
    private String CreditTotal;//兑换所需要的积分
    private String PublishTime;//时间
    private String CommentNum;//评论数量
    private String SaleNum;//销售数量
    private String ProductCategoryName;//属于什么分类
    private String VisitNum;//关注度
    private String FavoriteNum;//收藏人数
    private String Content; //商品描述
    private String RemainSum;//商品还剩余数量
    private String State;//兑换状态
    private String Number;//商品兑换的兑换数量
    private String StockNum;//兑换商品的库存量
    private String CreditNum;//已兑换的数量
    private String LiveryName;//快递公司名称
    private String LiveryNo;//快递单号
    private String LiveryType;//快递公司编码

    public ProductScore() {
    }

    public ProductScore(String id, String name, String imagePath, String price, String creditTotal, String publishTime, String commentNum,
                   String state, String saleNum, String productCategoryName, String visitNum, String favoriteNum, String content,
                   String remainSum, String number, String stockNum, String creditNum,String liveryName,String liveryNo,String liveryType) {
        this.Id = id;
        this.Name = name;
        this.ImagePath = imagePath;
        this.Price = price;
        this.CreditTotal = creditTotal;
        this.PublishTime = publishTime;
        this.CommentNum = commentNum;
        this.SaleNum = saleNum;
        this.ProductCategoryName = productCategoryName;
        this.VisitNum = visitNum;
        this.FavoriteNum = favoriteNum;
        this.Content = content;
        this.RemainSum = remainSum;
        this.State = state;
        this.Number = number;
        this.StockNum = stockNum;
        this.CreditNum = creditNum;
        this.LiveryName=liveryName;
        this.LiveryNo=liveryNo;
        this.LiveryType=liveryType;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getBillDetailId() {
        return BillDetailId;
    }

    public void setBillDetailId(String billDetailId) {
        BillDetailId = billDetailId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCreditTotal() {
        return CreditTotal;
    }

    public void setCreditTotal(String creditTotal) {
        CreditTotal = creditTotal;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String publishTime) {
        PublishTime = publishTime;
    }

    public String getCommentNum() {
        return CommentNum;
    }

    public void setCommentNum(String commentNum) {
        CommentNum = commentNum;
    }

    public String getSaleNum() {
        return SaleNum;
    }

    public void setSaleNum(String saleNum) {
        SaleNum = saleNum;
    }

    public String getProductCategoryName() {
        return ProductCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        ProductCategoryName = productCategoryName;
    }

    public String getVisitNum() {
        return VisitNum;
    }

    public void setVisitNum(String visitNum) {
        VisitNum = visitNum;
    }

    public String getFavoriteNum() {
        return FavoriteNum;
    }

    public void setFavoriteNum(String favoriteNum) {
        FavoriteNum = favoriteNum;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getRemainSum() {
        return RemainSum;
    }

    public void setRemainSum(String remainSum) {
        RemainSum = remainSum;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getStockNum() {
        return StockNum;
    }

    public void setStockNum(String stockNum) {
        StockNum = stockNum;
    }

    public String getCreditNum() {
        return CreditNum;
    }

    public void setCreditNum(String creditNum) {
        CreditNum = creditNum;
    }

    public String getLiveryName() {
        return LiveryName;
    }

    public void setLiveryName(String liveryName) {
        LiveryName = liveryName;
    }

    public String getLiveryNo() {
        return LiveryNo;
    }

    public void setLiveryNo(String liveryNo) {
        LiveryNo = liveryNo;
    }

    public String getLiveryType() {
        return LiveryType;
    }

    public void setLiveryType(String liveryType) {
        LiveryType = liveryType;
    }
}
