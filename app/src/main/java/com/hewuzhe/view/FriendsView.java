package com.hewuzhe.view;

import com.hewuzhe.model.Friend;
import com.hewuzhe.model.Group;
import com.hewuzhe.view.base.GetView;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/31.
 */
public interface FriendsView extends GetView<Integer> {

    void bindData(ArrayList<Friend> friends);

    void setGroupData(Group data);

}
