package com.hewuzhe.view;

import com.hewuzhe.model.VipPrice;
import com.hewuzhe.view.base.SetView;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 16/1/17.
 */
public interface MemberView extends SetView<String> {

    void bindData(ArrayList<VipPrice> list);
}
