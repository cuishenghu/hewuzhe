package com.hewuzhe.view;

import com.hewuzhe.model.User;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.SetView;

/**
 * Created by xianguangjin on 16/1/6.
 */
public interface FriendProfileView extends GetView<Integer>, SetView<User> {


    void setIsWuyou(boolean b);

    void followSuccess();
}
