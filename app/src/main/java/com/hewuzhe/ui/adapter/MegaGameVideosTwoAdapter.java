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
import com.hewuzhe.model.MegaGameVideo;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 16/1/14.
 */
public class MegaGameVideosTwoAdapter extends BaseAdapter<MegaGameVideosTwoAdapter.VHolder, MegaGameVideo, BasePresenterImp> {


    /**
     * RecycleView的头部
     *
     * @param context
     */
    public MegaGameVideosTwoAdapter(Context context) {
        super(context);
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_megagame_video_two;
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
        MegaGameVideo megaGameVideo = data.get(position);

        Glide.with(context)
                .load(C.BASE_URL + megaGameVideo.PhotoPath)
                .fitCenter()
                .placeholder(R.mipmap.img_avatar)
                .crossFade()
                .transform(new GlideCircleTransform(context))
                .into(holder._ImgAvatar);
        holder._TvUserName.setText(megaGameVideo.NicName);
        holder._TvJoinTime.setText("报名时间：" + megaGameVideo.JoinTime);
        holder._TvCode.setText("编号：" + megaGameVideo.MatchCode);


    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_megagame_video_two.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class VHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img_avatar)
        ImageView _ImgAvatar;
        @Nullable
        @Bind(R.id.tv_user_name)
        TextView _TvUserName;
        @Nullable
        @Bind(R.id.tv_join_time)
        TextView _TvJoinTime;
        @Nullable
        @Bind(R.id.tv_code)
        TextView _TvCode;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
