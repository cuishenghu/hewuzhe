package com.hewuzhe.model;

import java.util.List;

/**
 * Created by Administrator on 2016/1/29 0029.
 */
public class EquipmentSort {
    private String Id;//商品分类ID
    private String Name;//商品分类名称
    private List<Equipment> ProductList;//商品分类下的产品

    public EquipmentSort() {
    }

    public EquipmentSort(String Id, String Name, List<Equipment> ProductList) {
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

    public List<Equipment> getProductList() {
        return ProductList;
    }

    public void setProductList(List<Equipment> productList) {
        ProductList = productList;
    }

}
