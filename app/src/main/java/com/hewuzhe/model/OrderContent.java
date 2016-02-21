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

    public OrderContent() {
    }

    public OrderContent(String  productId,String assessType,String productPriceId, String productName, String id, String middleImagePath, String number, String productPriceTotalPrice, String productSizeName, String productColorName,String LiveryPrice) {
        ProductPriceId = productPriceId;
        ProductName = productName;
        Id = id;
        MiddleImagePath = middleImagePath;
        Number = number;
        ProductPriceTotalPrice = productPriceTotalPrice;
        ProductSizeName = productSizeName;
        ProductColorName = productColorName;
        AssessType=assessType;
        ProductId=productId;

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
}
