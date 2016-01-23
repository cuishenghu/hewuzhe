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
import com.hewuzhe.model.Friend;
import com.hewuzhe.presenter.MakeWarriorsPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class GroupMembersAdapter extends BaseAdapter<GroupMembersAdapter.VHolder, Friend, MakeWarriorsPresenter> {
    /**
     * RecycleView的头部
     *
     * @param context
     */

    public GroupMembersAdapter(Context context) {
        super(context);
    }


    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_group_member;
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
        Friend friend = data.get(position);
        holder._TvUsername.setText(friend.NicName);
        holder._TvLevel.setText("ID：" + getId(friend));

        Glide.with(context)
                .load(C.BASE_URL + friend.PhotoPath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_avatar)
                .transform(new GlideCircleTransform(context))
                .into(holder._ImgAvatar);

    }

    private String getId(Friend friend) {
        return StringUtil.isEmpty(friend.Phone) ? "000000" + friend.Id : friend.Phone;
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


        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
