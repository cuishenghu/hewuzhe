package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.TimeUtil;

import butterknife.Bind;

public class PlanSelectActivity extends ToolBarActivity {

    @Bind(R.id.tv_51)
    TextView _Tv51;
    @Bind(R.id.tv_50)
    TextView _Tv50;
    @Bind(R.id.tv_49)
    TextView _Tv49;
    @Bind(R.id.tv_48)
    TextView _Tv48;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "选择计划选项";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_plan_select;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

        _Tv48.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK, new Intent().putExtra("data", new Bun().putInt("catId", 48).putString("catName", "年计划").putString("start", TimeUtil.getCurrentDayFormat()).putString("end", TimeUtil.getDayAfterFormat(365)).ok()));
                finishActivity();
            }
        });

        _Tv49.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK, new Intent().putExtra("data", new Bun().putInt("catId", 49).putString("catName", "季计划").putString("start", TimeUtil.getCurrentDayFormat()).putString("end", TimeUtil.getDayAfterFormat(90)).ok()));
                finishActivity();
            }
        });


        _Tv50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK, new Intent().putExtra("data", new Bun().putInt("catId", 50).putString("catName", "月计划").putString("start", TimeUtil.getCurrentDayFormat()).putString("end", TimeUtil.getDayAfterFormat(30)).ok()));
                finishActivity();

            }
        });


        _Tv51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK, new Intent().putExtra("data", new Bun().putInt("catId", 51).putString("catName", "周计划").putString("start", TimeUtil.getCurrentDayFormat()).putString("end", TimeUtil.getDayAfterFormat(7)).ok()));
                finishActivity();
            }
        });


    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }

}
