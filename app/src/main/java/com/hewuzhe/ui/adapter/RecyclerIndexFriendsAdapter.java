package com.hewuzhe.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Friend;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class RecyclerIndexFriendsAdapter extends BGARecyclerViewAdapter<Friend> {
    public RecyclerIndexFriendsAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_friend);
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
        viewHolderHelper.setItemChildClickListener(R.id.lay_content);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, Friend model) {
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            viewHolderHelper.setVisibility(R.id.tv_item_indexview_catalog, View.VISIBLE);
            viewHolderHelper.setText(R.id.tv_item_indexview_catalog, model.topc);
        } else {
            viewHolderHelper.setVisibility(R.id.tv_item_indexview_catalog, View.GONE);
        }
        viewHolderHelper.setText(R.id.tv_username, model.NicName);

        Glide.with(mContext)
                .load(C.BASE_URL + model.PhotoPath)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(mContext))
                .into(viewHolderHelper.getImageView(R.id.img_avatar));

        viewHolderHelper.setText(R.id.tv_level, "等级：lv" + model.Rank);


    }

    public int getSectionForPosition(int position) {
        return mDatas.get(position).topc.charAt(0);
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mDatas.get(i).topc;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}