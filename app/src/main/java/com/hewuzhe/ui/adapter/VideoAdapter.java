package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

    private boolean isChecked = false;
    private int screenWidth = 10;

    /**
     * RecycleView的头部
     *
     * @param context
     */
    public VideoAdapter(Context context) {
        super(context);
        this.screenWidth = StringUtil.getScreenWidth((FragmentActivity) context);
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
    public void bindData(final ViewHolder holder, int position) {
         Video video = data.get(position);
        if (isChecked) {
            holder.ll_single_item.setVisibility(View.VISIBLE);
            holder.ll_double_item.setVisibility(View.GONE);
            getData1(holder, video);
        } else {
            holder.ll_double_item.setVisibility(View.VISIBLE);
            holder.ll_single_item.setVisibility(View.GONE);
            getData2(holder, video);
        }
    }

    private void getData1(ViewHolder holder, Video video) {
        holder.tvTitle1.setText(video.Title);
        holder.tvAddTime1.setText(TimeUtil.timeAgo(video.PublishTime) + "发布");
        holder.tvVisitSum1.setText(video.VisitNum + "人已浏览");
        if (StringUtil.isEmpty(video.NicName)) {
            holder.tvUsername1.setText(video.NicName);
        } else {
            holder.tvUsername1.setText(video.NicName);
        }

        ViewGroup.LayoutParams pageParms = holder.imgBg1.getLayoutParams();
        pageParms.height = isChecked ? screenWidth * 2 / 3 : screenWidth / 3;
        Glide.with(context)
                .load(C.BASE_URL + video.ImagePath)
                .crossFade()
                .placeholder(R.mipmap.img_bg_videio)
                .into(holder.imgBg1);

        if (video.UserId == 0) {
//            holder.imgAvatar.setImageResource(R.mipmap.ic_launcher);

            Glide.with(context)
                    .load(R.mipmap.ic_launcher)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.img_avatar)
                    .into(holder.imgAvatar1);


        } else {
            Glide.with(context)
                    .load(C.BASE_URL + video.PhotoPath)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.img_avatar)
                    .into(holder.imgAvatar1);
        }
        if (video.IsFree) {
            holder.tvIsFree.setVisibility(View.GONE);
        } else {
            holder.tvIsFree.setVisibility(View.VISIBLE);
        }
    }

    private void getData2(ViewHolder holder, Video video) {
        holder.tvTitle2.setText(video.Title);
//        holder.tvAddTime2.setText(TimeUtil.timeAgo(video.PublishTime) + "发布");
        holder.tvVisitSum2.setText(video.VisitNum+"人已浏览");
        if (StringUtil.isEmpty(video.NicName)) {
            holder.tvUsername2.setText(video.NicName);
        } else {
            holder.tvUsername2.setText(video.NicName);
        }

        ViewGroup.LayoutParams pageParms = holder.imgBg2.getLayoutParams();
        pageParms.height = isChecked ? screenWidth * 2 / 3 : screenWidth / 3;
        Glide.with(context)
                .load(C.BASE_URL + video.ImagePath)
                .crossFade()
                .placeholder(R.mipmap.img_bg_videio)
                .into(holder.imgBg2);

        if (video.UserId == 0) {
//            holder.imgAvatar.setImageResource(R.mipmap.ic_launcher);

            Glide.with(context)
                    .load(R.mipmap.ic_launcher)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.img_avatar)
                    .into(holder.imgAvatar2);


        } else {
            Glide.with(context)
                    .load(C.BASE_URL + video.PhotoPath)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.img_avatar)
                    .into(holder.imgAvatar2);
        }
        if (video.IsFree) {
            holder.tvIsFree_t.setVisibility(View.GONE);
        } else {
            holder.tvIsFree_t.setVisibility(View.VISIBLE);
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
        @Bind(R.id.img_bg1)//视频封面图片
        ImageView imgBg1;
        @Nullable
        @Bind(R.id.img_bg2)//视频封面图片
        ImageView imgBg2;
        @Nullable
        @Bind(R.id.tv_title1)//视频标题
        TextView tvTitle1;
        @Nullable
        @Bind(R.id.tv_title2)//视频标题
        TextView tvTitle2;
        @Nullable
        @Bind(R.id.img_share)
        ImageView imgShare;
        @Nullable
        @Bind(R.id.img_avatar1)//发布者头像
        ImageView imgAvatar1;
        @Nullable
        @Bind(R.id.img_avatar2)//发布者头像
        ImageView imgAvatar2;
        @Nullable
        @Bind(R.id.tv_username1)//发布者昵称
        TextView tvUsername1;
        @Nullable
        @Bind(R.id.tv_username2)//发布者昵称
        TextView tvUsername2;
        @Nullable
        @Bind(R.id.tv_comment_count)
        TextView tvCommentCount;
        @Nullable
        @Bind(R.id.tv_add_time1)//发布时间
        TextView tvAddTime1;
        @Nullable
        @Bind(R.id.tv_add_time2)//发布时间
        TextView tvAddTime2;
        @Nullable
        @Bind(R.id.tv_visit_sum1)//浏览量
        TextView tvVisitSum1;
        @Nullable
        @Bind(R.id.tv_visit_sum2)//浏览量
        TextView tvVisitSum2;
        @Nullable
        @Bind(R.id.ll_single_item)//单列显示布局
        RelativeLayout ll_single_item;
        @Nullable
        @Bind(R.id.ll_double_item)//双列显示布局
        RelativeLayout ll_double_item;
        @Nullable
        @Bind(R.id.tv_is_free)
        TextView tvIsFree;
        @Nullable
        @Bind(R.id.tv_is_free_t)
        TextView tvIsFree_t;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void changeViewHeight(boolean isChecked) {
        this.isChecked = isChecked;
    }
}