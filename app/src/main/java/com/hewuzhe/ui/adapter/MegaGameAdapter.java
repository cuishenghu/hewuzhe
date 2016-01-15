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
import com.hewuzhe.model.MegaGame;
import com.hewuzhe.presenter.MegaGamePresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.TimeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class MegaGameAdapter extends BaseAdapter<MegaGameAdapter.VHolder, MegaGame, MegaGamePresenter> {
    /**
     * RecycleView的头部
     *
     * @param context
     */
    public MegaGameAdapter(Context context) {
        super(context);
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_mega_game;
    }

    /**
     * @param parent
     * @param viewType
     * @param view
     * @return 创建ViewHolder
     */
    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    /**
     * @param holder
     * @param position 绑定数据
     */
    @Override
    public void bindData(VHolder holder, int position) {

        MegaGame megaGame = data.get(position);
        holder._TvAddTime.setText(TimeUtil.timeAgo(megaGame.MatchTimeStart));
        holder._TvTitle.setText(megaGame.Name);
        holder._TvContent.setText(megaGame.Introduction);
        Glide.with(context)
                .load(C.BASE_URL + megaGame.MatchImage)
                .centerCrop()
                .crossFade()
                .into(holder._Img);


        if (TimeUtil.timeComparedNow(megaGame.MatchTimeStart)) {
            //比赛未开始
            holder._ImgStatus.setImageResource(R.mipmap.bg_ready);
            holder._TvTime.setText("距离开始：" + TimeUtil.timeLeft(megaGame.MatchTimeStart));
            megaGame.status = MegaGame.STATUS_READY;

        }

        if (!TimeUtil.timeComparedNow(megaGame.MatchTimeStart) && TimeUtil.timeComparedNow(megaGame.MatchTimeEnd)) {
            //比赛未结束,正在进行中
            holder._ImgStatus.setImageResource(R.mipmap.bg_ing);
            holder._TvTime.setText("距离结束：" + TimeUtil.timeLeft(megaGame.MatchTimeEnd));

            megaGame.status = MegaGame.STATUS_ING;

        }

        if (!TimeUtil.timeComparedNow(megaGame.MatchTimeEnd)) {
            //比赛已经结束
            holder._ImgStatus.setImageResource(R.mipmap.bg_finished);
            holder._TvTime.setText("结束时间：" + TimeUtil.timeAgo(megaGame.MatchTimeEnd));

            megaGame.status = MegaGame.STATUS_FINISHED;

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
