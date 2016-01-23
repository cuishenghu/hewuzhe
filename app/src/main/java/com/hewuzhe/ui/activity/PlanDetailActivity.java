package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hewuzhe.R;
import com.hewuzhe.model.Pic;
import com.hewuzhe.model.Plan;
import com.hewuzhe.model.UploadImage;
import com.hewuzhe.presenter.PlanDetailPresenter;
import com.hewuzhe.ui.adapter.common.PlanImgsAdapter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.inter.OnItemClickListener;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.TimeUtil;
import com.hewuzhe.view.PlanDetialView;

import java.util.ArrayList;

import butterknife.Bind;

public class PlanDetailActivity extends ToolBarActivity<PlanDetailPresenter> implements PlanDetialView {

    @Bind(R.id.tv_name)
    TextView _TvName;
    @Bind(R.id.tv_time)
    TextView _TvTime;
    @Bind(R.id.tv_content)
    TextView _TvContent;
    @Bind(R.id.recycler_view)
    RecyclerView _RecyclerView;
    private Integer id = 0;
    private Plan item;
    private ArrayList<Pic> pics = new ArrayList<>();

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "计划详情";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_plan_detail;
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void action() {
        super.action();
        startActivityForResult(PublishPlanActivity.class, new Bun().putP("item", item).ok(), C.REQUEST_SELECT_CATE);
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tvAction.setText("编辑");

        presenter.getPlanDetail();
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finishActivity();
        }

    }

    /**
     * 绑定Presenter
     */
    @Override
    public PlanDetailPresenter createPresenter() {
        return new PlanDetailPresenter();
    }


    @Override
    public void setData(Plan plan) {
        item = plan;
        _TvName.setText(plan.Title);
        _TvContent.setText(plan.Content);
        _TvTime.setText(TimeUtil.timeFormatTwo(plan.StartTime) + "-" + TimeUtil.timeFormatTwo(plan.EndTime));

        _RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        final PlanImgsAdapter planImgsAdapter = new PlanImgsAdapter(getContext());
        _RecyclerView.setAdapter(planImgsAdapter);
        planImgsAdapter.addDatas(plan.ImageList);

        planImgsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos, Object item) {

                for (UploadImage pickImg : planImgsAdapter.data) {
                    Pic pic = new Pic();
                    pic.PictureUrl = pickImg.ImagePath;
                    pics.add(pic);
                }

                startActivity(PicsActivity.class, new Bun().putInt("pos", pos).putString("pics", new Gson().toJson(pics)).ok());
            }
        });
    }

    @Override
    public Integer getData() {
        return getIntentData().getInt("id");
    }
}
