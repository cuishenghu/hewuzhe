package com.hewuzhe.model;

import java.io.Serializable;

/**
 * Created by zycom on 2016/2/29.
 */
public class Bannar implements Serializable {
    public String Title;
    public int ProductId;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public int Id=-1;
    public String Path;

    public Bannar(){

    }

    public Bannar(String title, int productId, int id, String path) {
        Title = title;
        ProductId = productId;
        Id = id;
        Path = path;
    }
}
