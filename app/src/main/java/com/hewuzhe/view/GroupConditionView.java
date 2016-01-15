package com.hewuzhe.view;

import com.hewuzhe.model.FriendCondition;
import com.hewuzhe.model.Group;
import com.hewuzhe.view.adapter.ConditionView;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.common.SwipeRefreshView;

/**
 * Created by xianguangjin on 15/12/31.
 */
public interface GroupConditionView extends ConditionView, SwipeRefreshView,ListView<FriendCondition>,GetView<Integer> {


    void setGroupData(Group data);

    void updateItem(boolean b);
}
