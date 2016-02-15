package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Record;
import com.hewuzhe.presenter.RecordPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.TimeUtil;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class RecordAdapter extends BaseAdapter<RecordAdapter.ViewHolder, Record, RecordPresenter> {


    /**
     * 是否显示checkBox
     */
    private boolean isNeedShow = false;
    private LinkedList<Record> checkedList = new LinkedList<>();

    public RecordAdapter(Context context) {
        super(context);
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_record;
    }

    /**
     * @param parent
     * @param viewType
     * @param view
     * @return 创建ViewHolder
     */
    @Override
    public RecordAdapter.ViewHolder createVH(ViewGroup parent, int viewType, View view) {
        return new ViewHolder(view);
    }

    /**
     * @param holder
     * @param position 绑定数据
     */
    @Override
    public void bindData(RecordAdapter.ViewHolder holder, int position) {
        final Record record = data.get(position);

        holder.tvAddTime.setText(TimeUtil.timeAgo(record.PublishTime));
        holder.tvTitle.setText(record.Title);
        holder.tvContent.setText(record.Content);
        holder.tvTime.setText(record.VideoDuration);

        Glide.with(context)
                .load(C.BASE_URL + record.ImagePath)
                .centerCrop()
                .crossFade()
                .into(holder.img);

        holder._CbPlan.setChecked(record.isChecked);
        holder._CbPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (!RecordAdapter.this.checkedList.contains(record)) {
                        record.isChecked = true;
                        RecordAdapter.this.checkedList.add(record);
                    }
                } else {
                    if (RecordAdapter.this.checkedList.contains(record)) {
                        record.isChecked = false;
                        RecordAdapter.this.checkedList.remove(record);
                    }
                }
            }
        });

        if (this.isNeedShow) {
            holder._CbPlan.setVisibility(View.VISIBLE);
        } else {
            holder._CbPlan.setVisibility(View.GONE);
        }


    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_record.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.img_circle)
        ImageView imgCircle;
        @Nullable
        @Bind(R.id.tv_add_time)
        TextView tvAddTime;
        @Nullable
        @Bind(R.id.img)
        ImageView img;
        @Nullable
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Nullable
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Nullable
        @Bind(R.id.tv_time)
        TextView tvTime;

        @Nullable
        @Bind(R.id.cb_plan)
        CheckBox _CbPlan;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    /**
     * 设置是否显示checkBox
     *
     * @param isNeedShow
     */
    public void showCheck(boolean isNeedShow) {
        this.isNeedShow = isNeedShow;
        if (!isNeedShow) {
            for (Record plan : data) {
                plan.isChecked = false;
            }
        }

        this.notifyDataSetChanged();
    }

    /**
     * 获取当前编辑状态
     *
     * @return
     */
    public boolean getCheckShowStatus() {
        return this.isNeedShow;
    }

    /**
     * 获取选中的Item
     *
     * @return
     */
    public LinkedList<Record> getCheckedList() {
        return this.checkedList;
    }


}
