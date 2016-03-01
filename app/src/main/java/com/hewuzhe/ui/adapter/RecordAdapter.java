package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class RecordAdapter extends BaseAdapter<RecordAdapter.ViewHolder, Record, RecordPresenter> implements StickyRecyclerHeadersAdapter {


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

        holder.tvAddTime.setText(record.PublishTime.substring(11, 19));
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
     * Get the ID of the header associated with this item.  For example, if your headers group
     * items by their first letter, you could return the character representation of the first letter.
     * Return a value < 0 if the view should not have a header (like, a header view or footer view)
     *
     * @param position
     * @return
     */
    @Override
    public long getHeaderId(int position) {
        if (position != getItemCount() - 1) {
            Record item = data.get(position);
            String publishTime = item.PublishTime.substring(0, 10);
            return Long.parseLong(TimeUtil.timeHavedDay(publishTime));
        } else {
            return -1;
        }
    }

    /**
     * Creates a new ViewHolder for a header.  This works the same way onCreateViewHolder in
     * Recycler.Adapter, ViewHolders can be reused for different views.  This is usually a good place
     * to inflate the layout for the header.
     *
     * @param parent
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    /**
     * Binds an existing ViewHolder to the specified adapter position.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != getItemCount() - 1) {
            TextView textView = (TextView) holder.itemView.findViewById(R.id.tv_header);
            Record item = data.get(position);
            String publishTime = item.PublishTime.substring(0, 10);

            textView.setText(publishTime);
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
