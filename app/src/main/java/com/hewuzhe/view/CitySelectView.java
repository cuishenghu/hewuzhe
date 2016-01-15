package com.hewuzhe.view;

import com.hewuzhe.model.Address;
import com.hewuzhe.view.base.BaseView;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/30.
 */
public interface CitySelectView extends BaseView {
    public void bindData(ArrayList<Address> addresses);


}
