package com.hewuzhe.view;

import com.hewuzhe.model.Friend;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;

/**
 * Created by xianguangjin on 15/12/31.
 */
public interface GroupMembersView extends GetView<Integer>, ListView<Friend>, LoadMoreView {


    void isWuYou(Boolean data, int userid);
}
