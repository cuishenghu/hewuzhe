package com.hewuzhe.ui.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;

/**
 * Created by Administrator on 2016/3/24 0024.
 */


public class SaishiHolder extends RecyclerView.ViewHolder {
    public LinearLayout ll_news;
    public ImageView img_new_img;
    public TextView tvPublishTime;
    public TextView tvContent;
    public TextView tvComeFrom;
    public TextView tvVisitNum;

    public SaishiHolder(View itemView) {
        super(itemView);
        ll_news = (LinearLayout) itemView.findViewById(R.id.ll_news);
        img_new_img = (ImageView) itemView.findViewById(R.id.img_news_image);
        tvPublishTime = (TextView) itemView.findViewById(R.id.tv_news_publish_time);
        tvContent = (TextView) itemView.findViewById(R.id.tv_news_content);
        tvComeFrom = (TextView) itemView.findViewById(R.id.tv_news_come_from);
        tvVisitNum = (TextView) itemView.findViewById(R.id.tv_news_visit_num);
    }
}
