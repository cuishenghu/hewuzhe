package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hewuzhe.R;
import com.hewuzhe.model.Classification;
import com.hewuzhe.presenter.ClassificationPresenter;
import com.hewuzhe.ui.adapter.ClassificationAdapter;
import com.hewuzhe.ui.base.ToolBarActivity;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by csh on 2016/1/28.
 * 商品分类
 */
public class ProductClassifiActivity extends ToolBarActivity<ClassificationPresenter>
        implements AdapterView.OnItemClickListener, com.hewuzhe.view.base.ListView<Classification> {

    @Bind(R.id.classification_one)
    ListView classification_one;/**一级分类*/
    @Bind(R.id.classification_two)
    ListView classification_two;/**二级分类*/
    @Bind(R.id.classification_three)
    ListView classification_three;/**三级分类*/
    private ClassificationAdapter<Classification> OneAdapter;
    private ClassificationAdapter<Classification> TwoAdapter;
    private ClassificationAdapter<Classification> ThreeAdapter;
    private int m_screen_width;/**手机屏幕宽度*/

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_product_classification;
    }

    @Override
    public void initListeners() {
        classification_one.setOnItemClickListener(this);
        classification_two.setOnItemClickListener(this);
        classification_three.setOnItemClickListener(this);
    }

    @Override
    protected String provideTitle() {
        return "产品分类";
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        classification_one.setFooterDividersEnabled(false);
        classification_two.setFooterDividersEnabled(false);
        classification_three.setFooterDividersEnabled(false);
        presenter.getBigCategory();/**初始化*/

        /**获取手机屏幕宽度*/
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        m_screen_width = metrics.widthPixels;
    }

    @Override
    public ClassificationPresenter createPresenter() {
        return new ClassificationPresenter();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Classification classification;
        switch (parent.getId()) {
            case R.id.classification_one:
                /**点击一级分类*/
                classification = OneAdapter.getItem(position);
                OneAdapter.setSelectedPosition(position);
                presenter.getSmallCategory(classification.Id, 1);
                break;
            case R.id.classification_two:
                /**点击二级分类*/
                classification = TwoAdapter.getItem(position);
                TwoAdapter.setSelectedPosition(position);
                presenter.getSmallCategory(classification.Id, 2);
                break;
            case R.id.classification_three:
                /**点击三级分类*/
                classification = ThreeAdapter.getItem(position);
                startActivity(new Intent(getContext(),ProductListActivity.class).putExtra("classId", classification.Id));
                finish();
                break;
            default:break;
        }
    }

    @Override
    public void bindData(ArrayList<Classification> classifications) {
        switch (presenter.getCurrentList()) {
            case 0:/**一级分类列表*/
                OneAdapter = new ClassificationAdapter<Classification>(getContext(),
                        classifications, R.layout.item_classification_one, 0, m_screen_width);
                classification_one.setAdapter(OneAdapter);
                break;
            case 1:/**二级分类列表*/
                TwoAdapter = new ClassificationAdapter<Classification>(getContext(),
                        classifications, R.layout.item_classification_one, 1, m_screen_width);
                classification_two.setAdapter(TwoAdapter);
                classification_three.setAdapter(null);
                break;
            case 2:/**三级分类列表*/
                ThreeAdapter = new ClassificationAdapter<Classification>(getContext(),
                        classifications, R.layout.item_classification_one, 2, m_screen_width);
                classification_three.setAdapter(ThreeAdapter);
                break;
            default:break;
        }
    }
}