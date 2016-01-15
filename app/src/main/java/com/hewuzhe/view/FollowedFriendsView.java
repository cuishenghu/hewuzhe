package com.hewuzhe.view;

import com.hewuzhe.model.Friend;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;

/**
 * Created by xianguangjin on 16/1/15.
 */
public interface FollowedFriendsView extends LoadMoreView, ListView<Friend>, GetView<Integer> {


    void updatePosItem(int pos, boolean b);
}
