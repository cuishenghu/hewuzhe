package com.hewuzhe.view;

import com.hewuzhe.model.Group;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.common.AreaView;

/**
 * Created by xianguangjin on 15/12/31.
 */
public interface JoinGroupView extends GetView<Group>, AreaView,ListView<Group> {
    /**
     * 更新Item
     * @param b
     * @param pos
     */
    void updateItem(boolean b, int pos);

}
