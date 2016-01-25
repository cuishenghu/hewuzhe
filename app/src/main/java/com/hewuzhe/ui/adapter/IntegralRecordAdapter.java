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
import com.hewuzhe.utils.TimeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/25.
 */

public class IntegralRecordAdapter extends BaseAdapter<IntegralRecordAdapter.VHolder, IntegralRecord, IntegralPresenter> {

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
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    /**
     * @param holder
     * @param position 绑定数据
     */
    @Override
    public void bindData(VHolder holder, int position) {
        IntegralRecord item = data.get(position);
        holder.tvFrom.setText("来源：" + item.CreditCome);
        holder.tvAddTime.setText(TimeUtil.timeAgo(item.CreditTime));
        holder._TvNum.setText("+" + item.CreditNum + "个");

    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'header_integral.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */

    class VHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.img_avatar)
        @Nullable
        ImageView imgAvatar;
        @Bind(R.id.tv_add_time)
        @Nullable
        TextView tvAddTime;
        @Bind(R.id.tv_from)
        @Nullable
        TextView tvFrom;
        @Bind(R.id.tv_num)
        @Nullable
        TextView _TvNum;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
