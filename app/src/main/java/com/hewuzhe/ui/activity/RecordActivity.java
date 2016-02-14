package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.hewuzhe.R;
import com.hewuzhe.model.Record;
import com.hewuzhe.presenter.RecordPresenter;
import com.hewuzhe.ui.adapter.RecordAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.RecordView;

import java.util.ArrayList;
import java.util.LinkedList;

import materialdialogs.DialogAction;
import materialdialogs.MaterialDialog;

public class RecordActivity extends SwipeRecycleViewActivity<RecordPresenter, RecordAdapter, Record> implements RecordView {


    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tvAction.setText("编辑");
        presenter.getData(page, count);

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_record;
    }

    @Override
    protected String provideTitle() {
        return "成长记录";
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    /**
     * 绑定Presenter
     */
    @Override
    public RecordPresenter createPresenter() {
        return new RecordPresenter();
    }


    /**
     * @return 右边按钮是否显示
     */
    @Override
    public boolean canAction() {
        return true;
    }


    @Override
    protected void action() {
        super.action();
        if (adapter.getCheckShowStatus()) {
            tvAction.setText("编辑");

            showInfoDialog("温馨提示", "确定要删除吗？", "确定", "取消", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();

                    /**
                     * 执行删除操作
                     * */

                    LinkedList<Record> checkedList = adapter.getCheckedList();
                    if (checkedList.size() > 0) {
                        presenter.deleteViedeo(checkedList);
                    } else {
                        adapter.showCheck(false);
                    }
                }
            }, new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
                    adapter.showCheck(false);
                }
            });

        } else {

            tvAction.setText("删除");
            adapter.showCheck(true);

        }

    }

    @Override
    public void bindData(ArrayList<Record> data) {

        if (page == 1) {
            adapter.data.clear();
        }

        adapter.data.addAll(data);
        adapter.notifyDataSetChanged();
    }


    /**
     * @return 提供Adapter
     */
    @Override
    protected RecordAdapter provideAdapter() {
        return new RecordAdapter(getContext());
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Record item) {
        if (adapter.getCheckShowStatus()) {
            /**
             * 选择状态
             * */
            ((CheckBox) view.findViewById(R.id.cb_plan)).setChecked(item.isChecked ? false : true);
        } else {

            startActivity(BasicVideoTwoActivity.class, new Bun().putInt("Id", item.Id).ok());

        }
    }

    @Override
    public Integer getData() {
        return new SessionUtil(getContext()).getUserId();
    }

    @Override
    public void deleteFinished() {
        snb("删除成功", tvTitle);
        adapter.showCheck(false);
    }

    @Override
    public void removeItem(Record record) {
        adapter.removeItem(record);
    }
}
