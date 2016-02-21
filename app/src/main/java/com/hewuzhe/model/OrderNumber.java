package com.hewuzhe.model;

import java.io.Serializable;
import java.util.ArrayList;
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
//        "ProList":
public class OrderNumber implements Serializable {
    private String Id;//订单ID
    private String BillNo;//订单编号
    private String Price;//总价
    private String Postage;//邮费    邮费 -1为到付 0为包邮 其他为邮费
    private String LiveryName;//快递公司名称
    private String LiveryNo;//快递编号
    private String LiveryType;//快递公司编号
    private String OperateTime;//订单生成时间
    private String AreaId;//收货地址ID
    private String BuildTime;//创建订单时间
    private String PayTime;//付款时间
    private String SendTime;//发货时间
    private String State;//判断是否评论过
    private String IsCancle;//判断是否评论过
    private String Count;//订单商品总件数

    private List<OrderContent> ProList=new ArrayList<OrderContent>();//订单里的商品列表

    public OrderNumber() {
    }

    public OrderNumber(String count,String state,String id, String billNo, String price, String postage, String liveryName, String liveryNo, String liveryType,
                       String areaId, String buildTime, String payTime, String sendTime, String operateTime, List<OrderContent> proList,String isCancle) {
        Id = id;
        BillNo = billNo;
        Price = price;
        Postage = postage;
        LiveryName = liveryName;
        LiveryNo = liveryNo;
        LiveryType = liveryType;
        OperateTime = operateTime;
        AreaId = areaId;
        BuildTime = buildTime;
        PayTime = payTime;
        SendTime = sendTime;
        ProList = proList;
        State=state;
        IsCancle=isCancle;
        Count=count;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPostage() {
        return Postage;
    }

    public void setPostage(String postage) {
        Postage = postage;
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

    public String getSendTime() {
        return SendTime;
    }

    public void setSendTime(String sendTime) {
        SendTime = sendTime;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String payTime) {
        PayTime = payTime;
    }

    public List<OrderContent> getProList() {
        return ProList;
    }

    public void setProList(List<OrderContent> proList) {
        ProList = proList;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getIsCancle() {
        return IsCancle;
    }

    public void setIsCancle(String isCancle) {
        IsCancle = isCancle;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }
}
