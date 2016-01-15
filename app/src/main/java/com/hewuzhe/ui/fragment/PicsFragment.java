package com.hewuzhe.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.BaseFragment;
import com.hewuzhe.ui.cons.C;

import butterknife.Bind;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * A simple {@link Fragment} subclass.
 */
public class PicsFragment extends BaseFragment {

    @Bind(R.id.img)
    ImageViewTouch _Img;

    public PicsFragment() {

        // Required empty public constructor
    }

    public static PicsFragment newInstance(Bundle args) {
        PicsFragment picsFragment = new PicsFragment();
        picsFragment.setArguments(args);
        return picsFragment;
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
        String picUrl = getArguments().getString("picUrl");
        _Img.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        Glide.with(getActivity())
                .load(C.BASE_URL + picUrl)
                .fitCenter()
                .crossFade()
                .into(_Img);

        _Img.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() {
            @Override
            public void onSingleTapConfirmed() {
                finishActivity();
            }
        });
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_pics;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }


}
