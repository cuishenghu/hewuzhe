package com.hewuzhe.view.common;

import com.hewuzhe.model.Address;
import com.hewuzhe.view.base.LoadMoreView;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/31.
 */
public interface AreaView extends LoadMoreView {

    /**
     * 设置province
     *
     * @param address
     */
    public void setProvinces(ArrayList<Address> address);


    /**
     * 设置城市
     * @param address
     */
    void setCitys(ArrayList<Address> address);

    /**
     * 设置县区
     * @param address
     */
    void setDistricts(ArrayList<Address> address);
}
