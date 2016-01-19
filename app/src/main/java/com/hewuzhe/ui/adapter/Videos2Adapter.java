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
import com.hewuzhe.utils.TimeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/22.
 */
public class Videos2Adapter extends BaseAdapter<Videos2Adapter.ViewHolder, Video, BasePresenterImp> {

    public Videos2Adapter(Context context) {
        super(context);
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_video_2;
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
     * @param position
     */
    @Override
    public void bindData(ViewHolder holder, int position) {
        Video video = data.get(position);
        Glide.with(context)
                .load(C.BASE_URL + video.ImagePath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_default)
                .into(holder.imgBg);

        holder.tvAddTime.setText(TimeUtil.timeAgo(video.OperateTime));
        holder.tvCollectCount.setText(video.FavoriteNum + "");
        holder.tvPraiseCount.setText(video.LikeNum + "");
        holder.tvRepeatCount.setText(video.RepeatNum + "");
        holder.tvTitle.setText(video.Title);
        holder.tvDesc.setText(video.Content);

        if (video.IsFree) {
            holder.tvIsFree.setVisibility(View.GONE);
        } else {
            holder.tvIsFree.setVisibility(View.VISIBLE);
        }
        if (video.Islike) {
            holder.imgPraise.setImageResource(R.mipmap.icon_praise_focus);
        } else {
            holder.imgPraise.setImageResource(R.mipmap.icon_praise_gray);
        }

        if (video.IsFavorite) {
            holder.imgCollect.setImageResource(R.mipmap.icon_collect_focus);
        } else {
            holder.imgCollect.setImageResource(R.mipmap.icon_collect_gray);
        }


        if (video.IsRepeat) {
            holder.imgRepeat.setImageResource(R.mipmap.icon_share_gray);
        } else {
            holder.imgRepeat.setImageResource(R.mipmap.icon_share_focus);
        }

    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_video_2.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img_bg)
        ImageView imgBg;
        @Nullable
        @Bind(R.id.tv_add_time)
        TextView tvAddTime;
        @Nullable
        @Bind(R.id.tv_is_free)
        TextView tvIsFree;
        @Nullable
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Nullable
        @Bind(R.id.tv_desc)
        TextView tvDesc;
        @Nullable
        @Bind(R.id.img_praise)
        ImageView imgPraise;
        @Nullable
        @Bind(R.id.tv_praise_count)
        TextView tvPraiseCount;
        @Nullable
        @Bind(R.id.img_collect)
        ImageView imgCollect;
        @Nullable
        @Bind(R.id.tv_collect_count)
        TextView tvCollectCount;
        @Nullable
        @Bind(R.id.img_repeat)
        ImageView imgRepeat;
        @Nullable
        @Bind(R.id.tv_repeat_count)
        TextView tvRepeatCount;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
