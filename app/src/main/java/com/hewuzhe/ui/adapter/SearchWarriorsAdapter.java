package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Friend;
import com.hewuzhe.presenter.MakeWarriorsPresenter;
import com.hewuzhe.ui.activity.FriendProfileActivity;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.Bun;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class SearchWarriorsAdapter extends BaseAdapter<SearchWarriorsAdapter.VHolder, Friend, MakeWarriorsPresenter> {
    /**
     * RecycleView的头部
     *
     * @param context
     */


    public SearchWarriorsAdapter(Context context, MakeWarriorsPresenter makeWarriorsPresenter, View header) {
        super(context, makeWarriorsPresenter, header);
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_warrior_list_search;
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
    public void bindData(VHolder holder, final int position) {
        final Friend friend = data.get(position);
        holder._TvUsername.setText(friend.NicName);
//        holder._TvLevel.setText("等级：lv" + friend.Rank);

        Glide.with(context)
                .load(C.BASE_URL + friend.PhotoPath)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(context))
                .into(holder._ImgAvatar);

        if (friend.IsFriend) {
            holder._TvFollow.setText("已关注");
            holder._TvFollow.setBackgroundResource(R.color.colorYellow);
            holder._TvFollow.setOnClickListener(null);

        } else {
            holder._TvFollow.setText("关注");
            holder._TvFollow.setBackgroundResource(R.color.colorBg);
            holder._TvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _presenter.follow(friend.Id, position);
                }
            });
        }
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_warrior_list_search.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class VHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img_avatar)
        ImageView _ImgAvatar;
        @Nullable
        @Bind(R.id.tv_username)
        TextView _TvUsername;
        @Nullable
        @Bind(R.id.tv_level)
        TextView _TvLevel;
        @Nullable
        @Bind(R.id.tv_follow)
        TextView _TvFollow;


        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
