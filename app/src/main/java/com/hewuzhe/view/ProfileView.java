package com.hewuzhe.view;

import com.hewuzhe.model.User;
import com.hewuzhe.view.common.AreaView;

/**
 * Created by xianguangjin on 15/12/15.
 */
public interface ProfileView extends AreaView {


    void setData();

    User getData();

    void checkGender(int gender);

    void showAvatarUpdateDialog();




}
