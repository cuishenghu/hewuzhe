package com.hewuzhe.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hewuzhe.R;
import com.hewuzhe.model.TrainerLesson;
import com.hewuzhe.ui.adapter.base.RecommendFriendHolder;
import com.hewuzhe.ui.adapter.base.SaishiHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/24 0024.
 */
public class RecommendFriendAdapter extends RecyclerView.Adapter<RecommendFriendHolder> {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;

    private final View viewHeader;
    private final List<TrainerLesson> list;

    public RecommendFriendAdapter(View viewHeader, int count) {
        this.viewHeader = viewHeader;
        this.list = new ArrayList<TrainerLesson>(count);
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public RecommendFriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return new RecommendFriendHolder(viewHeader);
        }
        View topView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_add_warriors, parent, false);
        return new RecommendFriendHolder(topView);
    }

    @Override
    public void onBindViewHolder(RecommendFriendHolder holder, int position) {
        if (isHeader(position)) {
            return;
        }
        final TrainerLesson trainerLesson = list.get(position - 1);
//        ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL+    ,"http://"),holder.img_new_img);
        holder.tvRecommUsername.setText("");
        holder.tvRecommCome.setText("");
        holder.tvAddFriend.setText("");
        /**
         * 详情
         */
        holder.ll_recommend.setOnClickListener(new View.OnClickListener() {
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
