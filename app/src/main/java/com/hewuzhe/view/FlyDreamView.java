package com.hewuzhe.view;

import com.hewuzhe.model.FlyDreamHeader;
import com.hewuzhe.model.MyDream;
import com.hewuzhe.view.base.BaseView;

/**
 * Created by xianguangjin on 15/12/15.
 */
public interface FlyDreamView extends BaseView {

    MyDream getDate();

    void setData(MyDream dream);

    /**
     * 设置顶部信息
     * @param data
     */
    void setHeader(FlyDreamHeader data);
}
