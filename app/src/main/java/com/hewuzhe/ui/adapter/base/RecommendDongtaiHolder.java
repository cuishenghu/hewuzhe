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


public class RecommendDongtaiHolder extends RecyclerView.ViewHolder {
    public LinearLayout ll_recomm_dongtai;
    public CircleImageView img_avatar;
    private ImageView img_dt_img;
    public TextView tvUsername;
    public TextView tvContent;
    public TextView tvLikeNum;
    public TextView tvCommentNum;

    public RecommendDongtaiHolder(View itemView) {
        super(itemView);
        ll_recomm_dongtai = (LinearLayout) itemView.findViewById(R.id.ll_recomm_dongtai);
        img_avatar = (CircleImageView) itemView.findViewById(R.id.img_avatar);
        img_dt_img = (ImageView) itemView.findViewById(R.id.img_dongtai_image);
        tvUsername = (TextView) itemView.findViewById(R.id.user_name);
        tvContent = (TextView) itemView.findViewById(R.id.tv_dongtai_content);
        tvLikeNum = (TextView) itemView.findViewById(R.id.tv_dt_like_num);
        tvCommentNum = (TextView) itemView.findViewById(R.id.tv_dt_comment_num);
    }
}
