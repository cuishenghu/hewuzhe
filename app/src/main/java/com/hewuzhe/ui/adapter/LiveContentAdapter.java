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
import com.hewuzhe.model.LiveVideo;
import com.hewuzhe.presenter.LiveVideoPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.TimeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/20.
 */
public class LiveContentAdapter extends BaseAdapter<LiveContentAdapter.VHolder, LiveVideo, LiveVideoPresenter> {

    public LiveContentAdapter(Context context) {
        super(context);
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.item_live_content;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, int position) {
        LiveVideo liveVideo = data.get(position);
        holder._TvAddTime.setVisibility(View.GONE);
        holder._TvTitle.setText(liveVideo.Id+liveVideo.Title);
        holder._TvContent.setText(liveVideo.Content);
        Glide.with(context)
                .load(C.BASE_URL + liveVideo.ImagePath)
                .centerCrop()
                .crossFade()
                .into(holder._Img);

        holder._TvTime.setText("结束时间：" + liveVideo.TimeEnd);
        if (TimeUtil.timeComparedNow(liveVideo.TimeStart)) {
            //比赛未开始
            holder._ImgStatus.setImageResource(R.mipmap.bg_ready);
//            holder._TvTime.setText("距离开始：" + TimeUtil.timeLeft(liveVideo.TimeStart));
//            megaGame.status = MegaGame.STATUS_READY;

        }

        if (!TimeUtil.timeComparedNow(liveVideo.TimeStart) && TimeUtil.timeComparedNow(liveVideo.TimeEnd)) {
            //比赛未结束,正在进行中
            holder._ImgStatus.setImageResource(R.mipmap.bg_ing);
//            holder._TvTime.setText("距离结束：" + TimeUtil.timeLeft(liveVideo.TimeEnd));
//            megaGame.status = MegaGame.STATUS_ING;

        }

        if (!TimeUtil.timeComparedNow(liveVideo.TimeEnd)) {
            //比赛已经结束
            holder._ImgStatus.setImageResource(R.mipmap.bg_finished);
//            holder._TvTime.setText("结束时间：" + TimeUtil.timeAgo(liveVideo.TimeEnd));
//            megaGame.status = MegaGame.STATUS_FINISHED;

        }
    }

    class VHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img)
        ImageView _Img;
        @Nullable
        @Bind(R.id.tv_title)
        TextView _TvTitle;
        @Nullable
        @Bind(R.id.tv_add_time)
        TextView _TvAddTime;
        @Nullable
        @Bind(R.id.tv_content)
        TextView _TvContent;
        @Nullable
        @Bind(R.id.tv_time)
        TextView _TvTime;
        @Nullable
        @Bind(R.id.img_status)
        ImageView _ImgStatus;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
