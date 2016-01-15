package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.TeamAnnounce;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.base.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/25.
 */

public class TeamAnnounceAdapter extends BaseAdapter<TeamAnnounceAdapter.VHolder, TeamAnnounce, BasePresenterImp> {

    /**
     * RecycleView的头部
     *
     * @param context
     */
    public TeamAnnounceAdapter(Context context) {
        super(context);
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_team_announce;
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
        TeamAnnounce teamAnnounce = data.get(position);
        holder._TvAddTime.setText(teamAnnounce.PublishTime);
        holder._TvContent.setText(teamAnnounce.Content);
        holder._TvName.setText(teamAnnounce.Title);

    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_team_announce.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class VHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img_avatar)
        ImageView _ImgAvatar;
        @Nullable
        @Bind(R.id.tv_name)
        TextView _TvName;
        @Nullable
        @Bind(R.id.tv_content)
        TextView _TvContent;
        @Nullable
        @Bind(R.id.tv_add_time)
        TextView _TvAddTime;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'header_integral.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */


}
