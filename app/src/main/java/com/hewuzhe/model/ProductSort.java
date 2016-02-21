package com.hewuzhe.model;

import java.util.List;

/**
 * Created by Administrator on 2016/1/29 0029.
 */
public class ProductSort {
    private String Id;//商品分类ID
    private String Name;//商品分类名称
    private List<ProductScore> ProductList;//商品分类下的产品

    public ProductSort() {
    }

    public ProductSort(String Id, String Name, List<ProductScore> ProductList) {
        this.Id = Id;
        this.Name = Name;
        this.ProductList = ProductList;

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

    public List<ProductScore> getProductList() {
        return ProductList;
    }

    public void setProductList(List<ProductScore> productList) {
        ProductList = productList;
    }

}
