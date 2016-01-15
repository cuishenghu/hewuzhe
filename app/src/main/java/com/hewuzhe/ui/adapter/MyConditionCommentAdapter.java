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
import com.hewuzhe.model.ConditionComment;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 16/1/15.
 */
public class MyConditionCommentAdapter extends BaseAdapter<MyConditionCommentAdapter.VHolder, ConditionComment, BasePresenterImp> {
    /**
     * RecycleView的头部
     *
     * @param context
     */
    public MyConditionCommentAdapter(Context context) {
        super(context);
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_my_condition_comment;
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
        ConditionComment condtioncoment = data.get(position);

        holder._TvAddTime.setText(condtioncoment.PublishTime);
        holder._TvContent.setText(condtioncoment.Content);
        holder._TvUsername.setText(condtioncoment.NicName);

        Glide.with(context)
                .load(C.BASE_URL + condtioncoment.PhotoPath)
                .crossFade()
                .fitCenter()
                .placeholder(R.mipmap.img_avatar)
                .into(holder._ImgAvatar);


        if (condtioncoment.PicList != null && condtioncoment.PicList.size() > 0) {
            holder._ImgContent.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(C.BASE_URL + condtioncoment.PicList.get(0).ImagePath)
                    .crossFade()
                    .fitCenter()
                    .placeholder(R.mipmap.img_bg_videio)
                    .into(holder._ImgContent);

        } else {
            holder._ImgContent.setVisibility(View.GONE);


        }

    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_my_condition_comment.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class VHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img_avatar)
        ImageView _ImgAvatar;
        @Nullable
        @Bind(R.id.tv_username)
        TextView _TvUsername;
        @Nullable
        @Bind(R.id.tv_content)
        TextView _TvContent;
        @Nullable
        @Bind(R.id.tv_content_two)
        TextView _TvContentTwo;
        @Nullable
        @Bind(R.id.tv_add_time)
        TextView _TvAddTime;
        @Nullable
        @Bind(R.id.img_content)
        ImageView _ImgContent;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
