package com.hewuzhe.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.TrainerLesson;
import com.hewuzhe.ui.adapter.base.RecommendDongtaiHolder;
import com.hewuzhe.ui.adapter.base.SaishiHolder;
import com.hewuzhe.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/24 0024.
 */
public class RecommendDongtaiAdapter extends RecyclerView.Adapter<RecommendDongtaiHolder> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;

    private final View viewHeader;
    private final List<TrainerLesson> list;

    public RecommendDongtaiAdapter(View viewHeader, int count) {
        this.viewHeader = viewHeader;
        this.list = new ArrayList<TrainerLesson>(count);
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public RecommendDongtaiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return new RecommendDongtaiHolder(viewHeader);
        }
        View topView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recomm_dongtai, parent, false);
        return new RecommendDongtaiHolder(topView);
    }

    @Override
    public void onBindViewHolder(RecommendDongtaiHolder holder, int position) {
        if (isHeader(position)) {
            return;
        }
        final TrainerLesson trainerLesson = list.get(position - 1);
//        ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL+    ,"http://"),holder.img_avatar);
//        ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL+    ,"http://"),holder.img_dt_img);
        holder.tvUsername.setText("");
        holder.tvContent.setText("");
        holder.tvLikeNum.setText("来源：");
        holder.tvCommentNum.setText("");

        /**
         * 详情
         */
        holder.ll_recomm_dongtai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }
}
