package com.hewuzhe.view;

import com.hewuzhe.model.Friend;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.common.AreaView;

/**
 * Created by xianguangjin on 15/12/31.
 */
public interface MakeWarriorsView extends GetView<Friend>,AreaView,ListView<Friend> {

    /**
     * 更新pos上的Item
     * @param pos
     */
    void updatePosItem(int pos, boolean IsFriend);

    void isWuYou(Boolean data);
}
