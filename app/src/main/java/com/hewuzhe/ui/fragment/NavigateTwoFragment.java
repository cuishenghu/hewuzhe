package com.hewuzhe.ui.fragment;


import android.support.v4.app.Fragment;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigateTwoFragment extends BaseFragment {


    private static NavigateTwoFragment instance;

    public NavigateTwoFragment() {
        // Required empty public constructor
    }

    public static NavigateTwoFragment newInstance() {
        if (instance == null) {
            instance = new NavigateTwoFragment();
        }
        return instance;
    }


    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    /**
     * 初始化一些事情
     *
     * @param view
     */
    @Override
    protected void initThings(View view) {

    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_navigate_two;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }
}
