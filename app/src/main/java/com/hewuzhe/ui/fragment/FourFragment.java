package com.hewuzhe.ui.fragment;


import android.support.v4.app.Fragment;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.SwipeRefreshFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FourFragment extends SwipeRefreshFragment {


    private static FourFragment instance = null;

    public static FourFragment newInstance() {
        if (instance == null) {
            instance = new FourFragment();
        }
        return instance;
    }

    public FourFragment() {
        // Required empty public constructor
    }




    @Override
    public void initListeners() {

    }

    /**
     * 初始化一些事情
     *
     * @param v
     */
    @Override
    protected void initThings(View v) {

    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_one;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }


}
