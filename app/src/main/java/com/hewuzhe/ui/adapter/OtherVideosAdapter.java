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
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/29.
 */
public class OtherVideosAdapter extends BaseAdapter<OtherVideosAdapter.ViewHolder, Video, BasePresenterImp> {
    /**
     * RecycleView的头部
     *
     * @param context
     */
    public OtherVideosAdapter(Context context) {
        super(context);
        isAddFooter = false;

    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_other_video;
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
        Video video = data.get(position);
        Glide.with(context)
                .load(C.BASE_URL + video.ImagePath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_bg_videio)
                .into(holder.img);
        holder.tvName.setText(video.Title);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img)
        ImageView img;
        @Nullable
        @Bind(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
