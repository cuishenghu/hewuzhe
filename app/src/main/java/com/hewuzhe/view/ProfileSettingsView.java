package com.hewuzhe.view;

import com.hewuzhe.model.User;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.SetView;

/**
 * Created by xianguangjin on 16/1/11.
 */
public interface ProfileSettingsView extends GetView<Integer>,SetView<User> {
    void followSuccess(boolean b);

    void setRemarkSuccess(String rName);
}
