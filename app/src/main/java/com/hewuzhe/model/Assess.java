package com.hewuzhe.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/31 0031.
 */
public class Assess implements Serializable {

    private String productId;     //商品ID
    private String content;     //评价内容
    private String typeId;     //评价分类 0：差评  1：中评  2：好评
    private String userId;     //评价人ID
    private String productPriceId;     //

    public Assess() {
    }

    public Assess(String productId, String content, String typeId, String userId, String productPriceId) {
        this.productId = productId;
        this.content = content;
        this.typeId = typeId;
        this.userId = userId;
        this.productPriceId = productPriceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductPriceId() {
        return productPriceId;
    }

    public void setProductPriceId(String productPriceId) {
        this.productPriceId = productPriceId;
    }


}
