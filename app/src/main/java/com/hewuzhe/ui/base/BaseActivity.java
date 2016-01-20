package com.hewuzhe.ui.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.adapter.base.BeeBaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NU;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.base.BaseView;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import materialdialogs.DialogAction;
import materialdialogs.GravityEnum;
import materialdialogs.MaterialDialog;

/**
 * Created by xianguangjin on 15/12/4.
 *
 * @param <P> Presenter
 */
public abstract class BaseActivity<P extends BasePresenterImp> extends AutoLayoutActivity implements BaseView {

    protected Intent intent;

    public P presenter;
    private MaterialDialog dialog;

    /**
     * @return 提供LayoutId
     */
    abstract protected int provideContentViewId();


    /**
     * 初始化事件监听者
     */
    public abstract void initListeners();

    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     *                           初始化一些事情
     */
    protected abstract void initThings(Bundle savedInstanceState);

    /**
     * 绑定Presenter
     */
    public abstract P createPresenter();

    /**
     * 解绑Presenter
     */
    public void killPresenter() {
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());
        ButterKnife.bind(this);

//        PgyCrashManager.register(this);

        this.presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }

        initThings(savedInstanceState);
        initListeners();
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void snb(String text, View view) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public void snb(String text, View view, String actionText, View.OnClickListener action) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).setAction(actionText, action).show();
    }


    @Override
    public void showDialog() {
        dialog = new MaterialDialog.Builder(this)
                .title("请稍候")
                .titleGravity(GravityEnum.CENTER)
                .titleColor(Color.WHITE)
                .contentColor(Color.WHITE)
                .positiveColor(C.COLOR_YELLOW)
                .negativeColor(C.COLOR_YELLOW)
                .content("请稍候...")
                .backgroundColor(C.COLOR_BG)
                .progress(true, 0)
                .build();
        dialog.show();
    }

    @Override
    public void showDialog(String title, String content) {
        dialog = new MaterialDialog.Builder(this)
                .title(title)
                .titleGravity(GravityEnum.CENTER)
                .titleColor(Color.WHITE)
                .contentColor(Color.WHITE)
                .positiveColor(C.COLOR_YELLOW)
                .negativeColor(C.COLOR_YELLOW)
                .content(content)
                .backgroundColor(C.COLOR_BG)
                .progress(true, 0)
                .build();
        dialog.show();

    }

    @Override
    public void showListDialog(BeeBaseAdapter adapter, MaterialDialog.ListCallback callback, String title) {

        dialog = new MaterialDialog.Builder(this)
                .title(title)
                .titleGravity(GravityEnum.CENTER)
                .titleColor(Color.WHITE)
                .contentColor(Color.WHITE)
                .backgroundColor(C.COLOR_BG)
                .adapter(adapter,
                        callback)
                .build();
        dialog.show();
    }

    @Override
    public void showListDialog(BeeBaseAdapter adapter, MaterialDialog.ListCallback callback) {
        dialog = new MaterialDialog.Builder(this)
                .title("请选择")
                .titleGravity(GravityEnum.CENTER)
                .titleColor(Color.WHITE)
                .contentColor(Color.WHITE)
                .backgroundColor(C.COLOR_BG)
                .adapter(adapter,
                        callback)
                .build();
        dialog.show();
    }


    @Override
    public void showListDialog(@ArrayRes int resId, MaterialDialog.ListCallback callback) {

        dialog = new MaterialDialog.Builder(this)
                .title("请选择")
                .titleGravity(GravityEnum.CENTER)
                .titleColor(Color.WHITE)
                .contentColor(Color.WHITE)
                .backgroundColor(C.COLOR_BG)
                .items(resId)
                .itemsCallback(callback)
                .build();

        dialog.show();
    }


    @Override
    public void showInfoDialog(String title, String content, String positive, String negative, MaterialDialog.SingleButtonCallback positiveCallBack, MaterialDialog.SingleButtonCallback negativeCallBack) {
        dialog = new MaterialDialog.Builder(getContext())
                .title(title)
                .titleColor(Color.WHITE)
                .contentColor(Color.WHITE)
                .positiveColor(C.COLOR_YELLOW)
                .negativeColor(C.COLOR_YELLOW)
                .content(content)
                .positiveText(StringUtil.isEmpty(positive) ? "确定" : positive)
                .negativeText(StringUtil.isEmpty(negative) ? "取消" : negative)
                .backgroundColor(C.COLOR_BG)
                .onPositive(positiveCallBack)
                .onNegative(negativeCallBack)
                .build();
        dialog.show();
    }


    @Override
    public void showDefautInfoDialog(String title, String content, MaterialDialog.SingleButtonCallback positiveCallBack) {
        dialog = new MaterialDialog.Builder(getContext())
                .title(title)
                .titleColor(Color.WHITE)
                .contentColor(Color.WHITE)
                .positiveColor(C.COLOR_YELLOW)
                .negativeColor(C.COLOR_YELLOW)
                .content(content)
                .positiveText("确定")
                .negativeText("取消")
                .backgroundColor(C.COLOR_BG)
                .onPositive(positiveCallBack)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
        dialog.show();
    }


    @Override
    public void showListDialog(@ArrayRes int resId, MaterialDialog.ListCallback callback, String title) {

        dialog = new MaterialDialog.Builder(this)
                .title(title)
                .titleGravity(GravityEnum.CENTER)
                .titleColor(Color.WHITE)
                .contentColor(Color.WHITE)
                .backgroundColor(C.COLOR_BG)
                .items(resId)
                .itemsCallback(callback)
                .build();
        dialog.show();
    }

    @Override
    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void showSoftInput(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }


    @Override
    public void hideSoftMethod(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        killPresenter();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean isShowingDialog() {
        if (dialog == null) {
            return false;
        } else {
            return dialog.isShowing();
        }
    }

    @Override
    public void startActivity(Class clazz) {
        startActivity(new Intent(getContext(), clazz));

    }

    @Override
    public void startActivity(Class clazz, Bundle bundle) {
        startActivity(new Intent(getContext(), clazz).putExtra("data", bundle));
    }

    @Override
    public void startActivityForResult(Class clazz, int requestCode) {
        startActivityForResult(new Intent(getContext(), clazz), requestCode);

    }

    @Override
    public void startActivityForResult(Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtra("data", bundle);
        startActivityForResult(intent, requestCode);

    }


    public Bundle getIntentData() {
        Bundle data = getIntent().getBundleExtra("data");
        return NU.isNull(data) ? new Bundle() : data;
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
