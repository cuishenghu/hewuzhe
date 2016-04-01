package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.RecommendUser;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.FriendProfileActivity;
import com.hewuzhe.ui.activity.StrangerProfileSettingsActivity;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.Bun;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zycom on 2016/3/30.
 */
public class BindUserAdapter  extends BaseAdapter<BindUserAdapter.ViewHolder, RecommendUser, BasePresenterImp> {
    /**
     * RecycleView的头部
     *
     * @param context
     */
    public BindUserAdapter(Context context) {
        super(context);
        isAddFooter = false;

    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.chat_user;
    }

    /**
     * @param parent
     * @param viewType
     * @param view
     * @return 创建ViewHolder
     */
    @Override
    public ViewHolder createVH(ViewGroup parent, int viewType, View view) {
        return new ViewHolder(view);
    }

    /**
     * @param holder
     * @param position 绑定数据
     */
    @Override
    public void bindData(ViewHolder holder, int position) {
        final RecommendUser recommendUser = data.get(position);
        Glide.with(context)
                .load(C.BASE_URL + recommendUser.PhotoPath)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(context))
                .placeholder(R.mipmap.img_avatar)
                .into(holder.chat_u_icon);
        holder.chat_u_name.setText(recommendUser.NicName);
        holder.cu_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recommendUser.IsFriend) {
                    context.startActivity(new Intent(context, FriendProfileActivity.class).putExtra("data", new Bun().putInt("id", recommendUser.Id).ok()));
                } else {
                    context.startActivity(new Intent(context, StrangerProfileSettingsActivity.class).putExtra("data", new Bun().putInt("id", recommendUser.Id).ok()));
                }
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable @Bind(R.id.chat_u_icon)
        ImageView chat_u_icon;
        @Nullable @Bind(R.id.chat_u_name)
        TextView chat_u_name;
        @Nullable @Bind(R.id.cu_click) LinearLayout cu_click;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
