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

import com.hewuzhe.R;
import com.hewuzhe.model.Plan;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.utils.TimeUtil;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 16/1/4.
 */
public class PlanAdapter extends BaseAdapter<PlanAdapter.VHolder, Plan, BasePresenterImp> {
    /**
     * 是否显示checkBox
     */
    private boolean isNeedShow = false;
    private LinkedList<Plan> checkedList = new LinkedList<>();

    /**
     * RecycleView的头部
     *
     * @param context
     */
    public PlanAdapter(Context context) {
        super(context);
    }

    /**
     * @return ItemView的LayoutId
     */

    @Override
    public int provideItemLayoutId() {
        return R.layout.item_plan;
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
        final Plan plan = data.get(position);
        holder._TvTitle.setText(plan.Title);
        holder._TvConent.setText(plan.Content);
        holder._TvAddTime.setText(TimeUtil.timeAgo(plan.PublishTime));
        holder._TvPlanTime.setText(TimeUtil.timeFormatTwo(plan.StartTime) + "-" + TimeUtil.timeFormatTwo(plan.EndTime));

        holder._CbPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (!PlanAdapter.this.checkedList.contains(plan)) {
                        plan.isChecked = true;
                        PlanAdapter.this.checkedList.add(plan);
                    }
                } else {
                    if (PlanAdapter.this.checkedList.contains(plan)) {
                        plan.isChecked = false;
                        PlanAdapter.this.checkedList.remove(plan);
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
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_plan.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class VHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.cb_plan)
        CheckBox _CbPlan;
        @Nullable
        @Bind(R.id.img_circle)
        ImageView _ImgCircle;
        @Nullable
        @Bind(R.id.tv_plan_time)
        TextView _TvPlanTime;
        @Nullable
        @Bind(R.id.tv_title)
        TextView _TvTitle;
        @Nullable
        @Bind(R.id.tv_conent)
        TextView _TvConent;
        @Nullable
        @Bind(R.id.tv_add_time)
        TextView _TvAddTime;

        VHolder(View view) {
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
    public LinkedList<Plan> getCheckedList() {
        return this.checkedList;
    }

}
