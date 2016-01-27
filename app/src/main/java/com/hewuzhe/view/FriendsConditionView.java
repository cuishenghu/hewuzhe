package com.hewuzhe.view;

import com.hewuzhe.model.FriendCondition;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.User;
import com.hewuzhe.view.adapter.ConditionView;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.common.SwipeRefreshView;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/31.
 */
public interface FriendsConditionView extends ConditionView, SwipeRefreshView,ListView<FriendCondition>,GetView<Integer> {


    void setUserData(User data);

    void setDataStatus(int page, int count, Res<ArrayList<FriendCondition>> res);

    void updateFriendNoReadNum(int count, String data);
}
