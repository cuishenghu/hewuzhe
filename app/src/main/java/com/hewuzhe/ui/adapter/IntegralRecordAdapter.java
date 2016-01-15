package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.IntegralRecord;
import com.hewuzhe.presenter.IntegralPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/25.
 */

public class IntegralRecordAdapter extends BaseAdapter<RecyclerView.ViewHolder, IntegralRecord,IntegralPresenter> {

    /**
     * RecycleView的头部
     *
     * @param context
     * @param header
     */
    public IntegralRecordAdapter(Context context, View header) {
        super(context, header);
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_integral_record;
    }

    /**
     * @param parent
     * @param viewType
     * @param view
     * @return 创建ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder createVH(ViewGroup parent, int viewType, View view) {
        return new ViewHolder(view);
    }

    /**
     * @param holder
     * @param position 绑定数据
     */
    @Override
    public void bindData(RecyclerView.ViewHolder holder, int position) {


    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'header_integral.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img_avatar)
        @Nullable
        ImageView imgAvatar;
        @Bind(R.id.tv_add_time)
        @Nullable
        TextView tvAddTime;
        @Bind(R.id.tv_from)
        @Nullable
        TextView tvFrom;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
