package com.hewuzhe.model;

import java.io.Serializable;

/**
 * Created by csh on 2016/02/01.
 */
public class Site implements Serializable{

    /**
     * Id : 63
     * ParentId :
     * Name : 收货地址
     */

    public int Id;/**收货地址ID*/
    public String Address;/**收货详细地址*/
    public String AreaId;/**所属区域ID*/
    public String Code;/**邮编*/
    public String IsDefault;/**是否为默认地址*/
    public String Phone;/**收货联系电话*/
    public String ReceiverName;/**收货联系人*/
    public int UserId;/**用户ID*/
    public String Area;/**山东省/临沂市/兰山区*/

    @Override
    public String toString() {
        return Area+"/"+Address;
    }
}
