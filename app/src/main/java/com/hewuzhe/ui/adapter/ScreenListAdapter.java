package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Product;
import com.hewuzhe.model.ScreenList;
import com.hewuzhe.presenter.ScreenListPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.adapter.base.BaseNoMoreAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zycom on 2016
 */
public class ScreenListAdapter extends BaseNoMoreAdapter<ScreenListAdapter.VHolder, ScreenList, ScreenListPresenter> {

    public ScreenListAdapter(Context context, ScreenListPresenter screenListPresenter) {
        super(context, screenListPresenter);
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.screen_item;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, int position) {
        final ScreenList screenList = data.get(position);
        holder.si_title.setText(screenList.Name);
        holder.tv_count.setText(screenList.StudentCount);
    }

    class VHolder extends RecyclerView.ViewHolder {

        @Nullable @Bind(R.id.si_title)
        TextView si_title;
        @Nullable @Bind(R.id.tv_count)
        TextView tv_count;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
