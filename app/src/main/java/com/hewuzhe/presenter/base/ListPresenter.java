package com.hewuzhe.presenter.base;


import com.hewuzhe.view.base.ListView;

/**
 * Created by xianguangjin on 16/1/2.
 */
public abstract class ListPresenter<V extends ListView> extends BasePresenterImp<V> {

    /**
     * 获取数据
     *
     * @param page
     * @param count
     */
    public abstract void getData(int page, int count);


}
