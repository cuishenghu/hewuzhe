package com.hewuzhe.ui.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.view.CircleImageView;

/**
 * Created by Administrator on 2016/3/24 0024.
 */


public class RecommendFriendHolder extends RecyclerView.ViewHolder {
    public LinearLayout ll_recommend;
    public CircleImageView imgRecommAvatar;
    public TextView tvRecommUsername;
    public TextView tvRecommCome;
    public TextView tvAddFriend;

    public RecommendFriendHolder(View itemView) {
        super(itemView);
        ll_recommend = (LinearLayout) itemView.findViewById(R.id.ll_recomm_friend);
        imgRecommAvatar = (CircleImageView) itemView.findViewById(R.id.img_recomm_avatar);
        tvRecommUsername = (TextView) itemView.findViewById(R.id.tv_recomm_username);
        tvRecommCome = (TextView) itemView.findViewById(R.id.tv_recomm_come);
        tvAddFriend = (TextView) itemView.findViewById(R.id.tv_add_friend);
    }
}
