package com.hewuzhe.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zycom on 2016/1/23.
 * 商品类
 */
public class Product {
    public int Id=-1;
    public String Name;                     //商品标题
    public String Content;                  //详情内容
    public String ImagePath;                //商品图片
    public double Price;                    //价格
    public int CreditTotal;                 //兑换积分
    public double LiveryPrice;              //邮费
    public int FavoriteNum;                 //收藏数量
    public int SaleNum;                     //销售数量
    public boolean isFavorite;              //是否收藏
    public String PublishTime;              //发布时间
    public int CommentNum;                  //评论数
    public String ProductCategoryName;      //商品类别名称
    public String ProductColorName;         //商品颜色名称
    public String ProductSizeName;          //商品尺寸名称
    public int VisitNum;                    //商品浏览次数
    public ArrayList<ProductComment> CommentList;//商品评论列表
    public ArrayList<ProductColor> ColorList;    //商品颜色列表
    public ArrayList<ProductCategory> SizeList;  //尺寸列表
    public ArrayList<ProductPrice>  PriceList;   //价格列表
    public ArrayList<ProductPic> PicList;        //图片列表
}
