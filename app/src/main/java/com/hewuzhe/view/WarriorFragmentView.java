package com.hewuzhe.view;

import com.hewuzhe.model.AboutUs;
import com.hewuzhe.model.Images;
import com.hewuzhe.view.base.BaseView;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/30.
 */
public interface WarriorFragmentView extends BaseView {

    void setUserData();

    void isWuYou(Boolean data, int userid);
    void setIndexImg(AboutUs list);
    void setIndexImg(ArrayList<Images> list);
}
