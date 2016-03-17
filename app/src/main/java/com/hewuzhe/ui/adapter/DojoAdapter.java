package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Comment;
import com.hewuzhe.presenter.DojoDetail2Presenter;
import com.hewuzhe.presenter.DojoDetailPresenter;
import com.hewuzhe.presenter.VideoDetailPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.TimeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zycom on 2016/3/17.
 */
public class DojoAdapter extends BaseAdapter<DojoAdapter.ViewHolder, Comment, DojoDetail2Presenter> {

    private int whitch = C.WHITCH_DEFAUT;

    public DojoAdapter(Context context, DojoDetail2Presenter videoDetailPresenter, View header, int whitch) {
        super(context, videoDetailPresenter, header);
        this.whitch = whitch;

    }

    public DojoAdapter(Context context, DojoDetail2Presenter videoDetailPresenter, View header) {
        super(context, videoDetailPresenter, header);
    }


    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_comment;
    }

    /**
     * @param parent
     * @param viewType
     * @param view
     * @return 创建ViewHolder
     */
    @Override
    public ViewHolder createVH(ViewGroup parent, int viewType, View view) {
        return new ViewHolder(view);
    }

    /**
     * @param holder
     * @param position 绑定数据
     */
    @Override
    public void bindData(ViewHolder holder, int position) {


    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
