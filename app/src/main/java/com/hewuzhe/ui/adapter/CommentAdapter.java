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
import com.hewuzhe.presenter.VideoDetailPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.TimeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/29.
 */
public class CommentAdapter extends BaseAdapter<CommentAdapter.ViewHolder, Comment, VideoDetailPresenter> {

    private int whitch = C.WHITCH_DEFAUT;

    public CommentAdapter(Context context, VideoDetailPresenter videoDetailPresenter, View header, int whitch) {
        super(context, videoDetailPresenter, header);
        this.whitch = whitch;

    }

    public CommentAdapter(Context context, VideoDetailPresenter videoDetailPresenter, View header) {
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

        final Comment comment = data.get(position);
        if (whitch == C.WHITCH_DEFAUT) {
            holder.tvName.setText(comment.CommenterNicName);
            holder.tvAddTime.setText(TimeUtil.timeAgo(comment.PublishTime));
            holder.tvContent.setText(comment.Content);
            holder.tvPraiseCount.setText(10 + "");

            Glide.with(context)
                    .load(C.BASE_URL + comment.CommenterPhotoPath)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.img_avatar)
                    .into(holder.imgAvatar);

        } else if (whitch == C.WHITCH_ONE) {
            holder.tvName.setText(comment.NicName);
            holder.tvAddTime.setText(TimeUtil.timeAgo(comment.PublishTime));
            holder.tvContent.setText(comment.Content);
            holder.tvPraiseCount.setText(10 + "");

            Glide.with(context)
                    .load(C.BASE_URL + comment.PhotoPath)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.img_avatar)
                    .into(holder.imgAvatar);

            holder.imgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _presenter.isWuyou(comment.CommenterId);
                }
            });
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_avatar)
        @Nullable
        ImageView imgAvatar;
        @Bind(R.id.tv_name)
        @Nullable
        TextView tvName;
        @Bind(R.id.tv_praise_count)
        @Nullable
        TextView tvPraiseCount;
        @Bind(R.id.img_praise)
        @Nullable
        ImageView imgPraise;
        @Bind(R.id.tv_content)
        @Nullable
        TextView tvContent;
        @Bind(R.id.tv_add_time)
        @Nullable
        TextView tvAddTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
