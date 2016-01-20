package com.hewuzhe.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hewuzhe.R;
import com.hewuzhe.model.Plan;
import com.hewuzhe.presenter.PlanPresenter;
import com.hewuzhe.ui.activity.PlanDetailActivity;
import com.hewuzhe.ui.adapter.PlanAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewFragment;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.NU;
import com.hewuzhe.view.PlanView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends SwipeRecycleViewFragment<PlanPresenter, PlanAdapter, Plan> implements PlanView {


    private int id;

    public static PlanFragment newInstance(Bundle args) {
        PlanFragment fragment = new PlanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PlanFragment() {
        // Required empty public constructor

    }

    @Override
    public void initListeners() {


    }

    /**
     * 初始化一些事情
     *
     * @param view
     */
    @Override
    protected void initThings(View view) {
        super.initThings(view);
        id = getArguments().getInt("id");
        presenter.getData(page, count);
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected PlanAdapter provideAdapter() {
        return new PlanAdapter(getContext());
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_plan;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public PlanPresenter createPresenter() {
        return new PlanPresenter();
    }


    @Override
    public Integer getData() {
        return id;
    }

    @Override
    public void bindData(ArrayList<Plan> data) {
        bd(data);
    }


    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Plan item) {
        if (adapter.getCheckShowStatus()) {
            /**
             * 选择状态
             * */

            ((android.widget.CheckBox) view.findViewById(R.id.cb_plan)).setChecked(item.isChecked ? false : true);

        } else {
            /**
             * 进入详情页
             * */

            item.catId = id;
            startActivity(PlanDetailActivity.class, new Bun().putInt("id", item.Id).ok());
        }

    }


    @Override
    public void onReceive(Integer msg) {
        if (msg == C.MSG_DEFAUT) {
            //编辑
            adapter.showCheck(true);
        } else if (msg == C.MSG_ONE) {
            //删除
            LinkedList<Plan> checkedList = adapter.getCheckedList();
            if (checkedList.size() > 0) {
                for (Plan plan : checkedList) {
                    presenter.deletePlan(plan.Id, checkedList.size());
                    adapter.removeItem(plan);
                }
            } else {
                adapter.showCheck(false);
            }
        } else if (msg == C.MSG_TWO) {
            adapter.showCheck(false);
        }
    }

    /**
     * 获取当前编辑状态
     *
     * @return
     */
    @Override
    public Boolean getMsg() {
        return NU.isNull(adapter) ? false : adapter.getCheckShowStatus();
    }
}
