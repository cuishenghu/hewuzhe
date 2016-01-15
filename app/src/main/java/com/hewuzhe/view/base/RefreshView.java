package com.hewuzhe.view.base;

/**
 * Created by xianguangjin on 15/12/22.
 */
public interface RefreshView extends BaseView {

    /**
     * @param isRefresh 设置是否显示刷新
     */
    void setRefresh(boolean isRefresh);

    /**
     * @param isLoading 设置是否显示加载更多
     */
    void setLoading(boolean isLoading);

}
