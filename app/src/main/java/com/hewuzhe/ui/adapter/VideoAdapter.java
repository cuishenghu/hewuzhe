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
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TimeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class VideoAdapter extends BaseAdapter<VideoAdapter.ViewHolder, Video, BasePresenterImp> {

    /**
     * RecycleView的头部
     *
     * @param context
     */
    public VideoAdapter(Context context) {
        super(context);
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.video_item;
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

        holder.tvCommentCount.setText(video.CommentNum + "条评论");
        holder.tvAddTime.setText(TimeUtil.timeAgo(video.PublishTime) + "发布");
        if (StringUtil.isEmpty(video.NicName)) {
            holder.tvUsername.setText(video.UserNicName);
        } else {
            holder.tvUsername.setText(video.NicName);
        }

        holder.tvTitle.setText(video.Title);


        Glide.with(context)
                .load(C.BASE_URL + video.ImagePath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_bg_videio)
                .into(holder.imgBg);

        if (video.UserId == 0) {
//            holder.imgAvatar.setImageResource(R.mipmap.ic_launcher);

            Glide.with(context)
                    .load(R.mipmap.ic_launcher)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.img_avatar)
                    .into(holder.imgAvatar);


        } else {
            Glide.with(context)
                    .load(C.BASE_URL + video.PhotoPath)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.img_avatar)
                    .into(holder.imgAvatar);
        }


    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'video_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img_bg)
        ImageView imgBg;
        @Nullable
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Nullable
        @Bind(R.id.img_share)
        ImageView imgShare;
        @Nullable
        @Bind(R.id.img_avatar)
        ImageView imgAvatar;
        @Nullable
        @Bind(R.id.tv_username)
        TextView tvUsername;
        @Nullable
        @Bind(R.id.tv_comment_count)
        TextView tvCommentCount;
        @Nullable
        @Bind(R.id.tv_add_time)
        TextView tvAddTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
