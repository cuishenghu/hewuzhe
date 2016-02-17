package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.IntegralRecord;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.IntegralPresenter;
import com.hewuzhe.ui.adapter.IntegralRecordAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.IntegralView;

import java.util.ArrayList;

public class IntegralActivity extends RecycleViewActivity<IntegralPresenter, IntegralRecordAdapter, IntegralRecord> implements IntegralView {


    private TextView tvNickNae;
    private TextView tvIntegral;
    private ImageView imgAvatar;
    private User user;
    private View header;

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        user = new SessionUtil(getContext())
                .getUser();

        presenter.getData(page, count);
        initHeader();
    }

    /**
     * 初始化头部
     */
    private void initHeader() {
        tvNickNae = (TextView) header.findViewById(R.id.tv_nick_name);
        tvIntegral = (TextView) header.findViewById(R.id.tv_integral);
        imgAvatar = (ImageView) header.findViewById(R.id.img_avatar);
        tvNickNae.setText("昵称：" + user.NicName);
        tvIntegral.setText("积分：" + user.Credit + "个");

        Glide.with(getContext())
                .load(C.BASE_URL + user.PhotoPath)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(getContext()))
                .placeholder(R.mipmap.img_avatar)
                .into(imgAvatar);
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_integral;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    /**
     * 绑定Presenter
     */
    @Override
    public IntegralPresenter createPresenter() {
        return new IntegralPresenter();
    }


    @Override
    protected String provideTitle() {
        return "我的积分";
    }


    @Override
    public void bindData(ArrayList<IntegralRecord> data) {
        bd(data);
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected IntegralRecordAdapter provideAdapter() {
        header = getLayoutInflater().inflate(R.layout.header_integral, null);
        return new IntegralRecordAdapter(getContext(), header);
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }


    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, IntegralRecord item) {


    }

}
