package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.LiveVideo;
import com.hewuzhe.presenter.LiveVideoPresenter;
import com.hewuzhe.presenter.VideoDetailPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ninos on 2016/4/21.
 */
public class LiveVideo2Adapter extends BaseAdapter<LiveVideo2Adapter.ViewHolder, LiveVideo, LiveVideoPresenter> {

    public LiveVideo2Adapter(Context context) {
        super(context);
        isAddFooter = false;
    }
    public LiveVideo2Adapter(Context context, LiveVideoPresenter videoDetailPresenter, View header) {
        super(context, videoDetailPresenter, header);
        isAddFooter = false;
    }
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_comment;
    }

    @Override
    public ViewHolder createVH(ViewGroup parent, int viewType, View view) {
        return new ViewHolder(view);
    }

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
