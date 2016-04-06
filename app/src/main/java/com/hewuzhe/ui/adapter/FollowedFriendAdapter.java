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
import com.hewuzhe.presenter.FollowedFriendsPresenter;
import com.hewuzhe.ui.activity.FriendProfileActivity;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.Bun;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.easydone.swipemenuviewholder.SwipeMenuViewHolder;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class FollowedFriendAdapter extends BaseAdapter<RecyclerView.ViewHolder, Friend, FollowedFriendsPresenter> {
    /**
     * RecycleView的头部
     *
     * @param context
     */

    public FollowedFriendAdapter(Context context, FollowedFriendsPresenter followedFriendsPresenter) {
        super(context, followedFriendsPresenter);
    }


    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_friends_followed;
    }

    /**
     * @param parent
     * @param viewType
     * @param view
     * @return 创建ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder createVH(ViewGroup parent, int viewType, View view) {
        View swipeMenuView = _inflater.inflate(R.layout.swipe_menu_view, parent, false);
        swipeMenuView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return new VHolder(context, (ViewGroup) swipeMenuView, (ViewGroup) view, SwipeMenuViewHolder.EDGE_RIGHT).getDragViewHolder();


    }

    /**
     * @param position 绑定数据
     */
    @Override
    public void bindData(RecyclerView.ViewHolder viewHolder, final int position) {
        final VHolder holder = (VHolder) VHolder.getHolder(viewHolder);
        final Friend friend = data.get(position);
        holder._TvUsername.setText(friend.NicName);
        holder._TvLevel.setText(friend.JoinTime + "添加了你");

//        holder._ImgAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context, FriendProfileActivity.class).putExtra("data", new Bun().putInt("id",friend.Id).ok()));
//            }
//        });

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _presenter.DeleteFriended(friend.Id);
                FollowedFriendAdapter.this.data.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });

        Glide.with(context)
                .load(C.BASE_URL + friend.PhotoPath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_avatar)
                .transform(new GlideCircleTransform(context))
                .into(holder._ImgAvatar);

        if (friend.IsFriend) {
            holder._TvFollow.setText("删除武友");
            holder._TvFollow.setBackgroundResource(R.color.colorBg);
            holder._TvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _presenter.unFollow(friend.UserId, position);
                }
            });

        } else {
            holder._TvFollow.setText("添加武友");
            holder._TvFollow.setBackgroundResource(R.color.colorYellow);
            holder._TvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _presenter.follow(friend.UserId, position);
                    context.startActivity(new Intent(context,FriendProfileActivity.class), new Bun().putInt("id", friend.UserId).ok());
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
    class VHolder extends SwipeMenuViewHolder {
        @Nullable
        @Bind(R.id.tv_delete)
        TextView tv_delete;
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

        public VHolder(Context context, View swipMenuView, View captureView, int mTrackingEdges) {
            super(context, swipMenuView, captureView, mTrackingEdges);
        }


        /**
         * 初始化控件
         *
         * @param itemView 条目的布局
         */
        @Override
        public void initView(View itemView) {
            ButterKnife.bind(this, itemView);

        }
    }
}
