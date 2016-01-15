package com.hewuzhe.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.cons.C;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by xianguangjin on 15/12/22.
 * <p>
 * M data的Item MODEL
 */
public abstract class BaseHeaderAdapter<VH extends RecyclerView.ViewHolder, M, P extends BasePresenterImp> extends BaseAdapter<VH, M, P> {

    private final View header;

    /**
     * RecycleView的头部
     */

    public BaseHeaderAdapter(Context context, View header) {
        super(context);
        this.context = context;
        this.header = header;
    }

    public BaseHeaderAdapter(Context context, P p, View header) {
        super(context, p);
        this.header = header;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == C.VIEW_TYPE_HEADER) {
            return C.VIEW_TYPE_HEADER;
        } else {
            return position;
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == C.VIEW_TYPE_HEADER) {
            AutoUtils.auto(header);
            return createVH(parent, viewType, header);
        } else {
            View view = LayoutInflater.from(context).inflate(provideItemLayoutId(), null);
            AutoUtils.auto(view);
            return createVH(parent, viewType, view);
        }
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        if (position == C.VIEW_TYPE_HEADER) {


        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, position, data.get(position - 1));
                }
            });
            bindData(holder, position - 1);
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }


}
