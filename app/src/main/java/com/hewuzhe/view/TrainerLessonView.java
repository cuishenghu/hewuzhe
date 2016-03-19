package com.hewuzhe.view;

import com.hewuzhe.model.Address;
import com.hewuzhe.model.TrainerLessonTwo;
import com.hewuzhe.view.base.BaseView;

import java.util.ArrayList;

/**
 * Created by zycom on 2016/3/18.
 */
public interface TrainerLessonView extends BaseView {
    void bindData(TrainerLessonTwo trainerLessonTwo);
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
