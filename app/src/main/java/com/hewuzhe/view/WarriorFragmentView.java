package com.hewuzhe.view;

import com.hewuzhe.model.AboutUs;
import com.hewuzhe.view.base.BaseView;

/**
 * Created by xianguangjin on 15/12/30.
 */
public interface WarriorFragmentView extends BaseView {

    void setUserData();

    void isWuYou(Boolean data, int userid);

    void setIndexImg(AboutUs data);
}
