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
import com.hewuzhe.model.Group;
import com.hewuzhe.presenter.JoinPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/31.
 */
public class JoinGroupAdapter extends BaseAdapter<JoinGroupAdapter.VHolder, Group, JoinPresenter> {

    public JoinGroupAdapter(Context context, JoinPresenter joinPresenter) {
        super(context, joinPresenter);
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.group_list_search_item;
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
    public void bindData(VHolder holder, final int position) {
        final Group group = data.get(position);
        holder._TvUsername.setText(group.Name);
        holder._TvAddress.setText("所在地" + group.Address);

        Glide.with(context)
                .load(C.BASE_URL + group.ImagePath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_bg)
                .into(holder._ImgAvatar);

        if (group.isJoined(context)) {
            holder._TvJoin.setText("退出");
            holder._TvJoin.setBackgroundResource(R.color.colorYellow);
            holder._TvJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _presenter.quitGroup(group.Id, position);
                }
            });

        } else {
            holder._TvJoin.setText("加入");
            holder._TvJoin.setBackgroundResource(R.color.colorBg);
            holder._TvJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    _presenter.joinGroup(group.Id, group.Name, position);
                }
            });

        }
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'group_list_search_item.xml'
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
        @Bind(R.id.tv_address)
        TextView _TvAddress;
        @Nullable
        @Bind(R.id.tv_join)
        TextView _TvJoin;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
