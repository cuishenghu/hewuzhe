package com.hewuzhe.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/29 0029.
 */

public class OrderContent implements Serializable{
    private String Id;//订单明细ID
    private String MiddleImagePath;//商品图片
    private String Number;//购买个数
    private String ProductPriceTotalPrice;//商品价格
    private String ProductSizeName;//商品尺寸
    private String ProductColorName;//商品颜色
    private String ProductName;//商品名称
    private String ProductPriceId;//商品价格ID
    private String  ProductId;//商品ID
    private String AssessType;
    private String LiveryName;//快递公司名称
    private String LiveryNo;//快递编号
    private String LiveryType;//快递公司编号
    private String OperateTime;//订单生成时间
    private String AreaId;//收货地址ID
    private String BuildTime;//创建订单时间
    private String PayTime;//付款时间
    private String SendTime;//发货时间


    public OrderContent() {
    }

    public OrderContent(String id, String middleImagePath, String number, String productPriceTotalPrice, String productSizeName, String productColorName, String productName, String productPriceId, String productId, String assessType, String liveryName, String liveryNo, String liveryType, String operateTime, String areaId, String buildTime, String payTime, String sendTime) {
        Id = id;
        MiddleImagePath = middleImagePath;
        Number = number;
        ProductPriceTotalPrice = productPriceTotalPrice;
        ProductSizeName = productSizeName;
        ProductColorName = productColorName;
        ProductName = productName;
        ProductPriceId = productPriceId;
        ProductId = productId;
        AssessType = assessType;
        LiveryName = liveryName;
        LiveryNo = liveryNo;
        LiveryType = liveryType;
        OperateTime = operateTime;
        AreaId = areaId;
        BuildTime = buildTime;
        PayTime = payTime;
        SendTime = sendTime;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMiddleImagePath() {
        return MiddleImagePath;
    }

    public void setMiddleImagePath(String middleImagePath) {
        MiddleImagePath = middleImagePath;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getProductPriceTotalPrice() {
        return ProductPriceTotalPrice;
    }

    public void setProductPriceTotalPrice(String productPriceTotalPrice) {
        ProductPriceTotalPrice = productPriceTotalPrice;
    }

    public String getProductSizeName() {
        return ProductSizeName;
    }

    public void setProductSizeName(String productSizeName) {
        ProductSizeName = productSizeName;
    }

    public String getProductColorName() {
        return ProductColorName;
    }

    public void setProductColorName(String productColorName) {
        ProductColorName = productColorName;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPriceId() {
        return ProductPriceId;
    }

    public void setProductPriceId(String productPriceId) {
        ProductPriceId = productPriceId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getAssessType() {
        return AssessType;
    }

    public void setAssessType(String assessType) {
        AssessType = assessType;
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

    public String getOperateTime() {
        return OperateTime;
    }

    public void setOperateTime(String operateTime) {
        OperateTime = operateTime;
    }

    public String getAreaId() {
        return AreaId;
    }

    public void setAreaId(String areaId) {
        AreaId = areaId;
    }

    public String getBuildTime() {
        return BuildTime;
    }

    public void setBuildTime(String buildTime) {
        BuildTime = buildTime;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String payTime) {
        PayTime = payTime;
    }

    public String getSendTime() {
        return SendTime;
    }

    public void setSendTime(String sendTime) {
        SendTime = sendTime;
    }
}
