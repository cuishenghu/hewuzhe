package com.hewuzhe.view.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.view.View;

import com.hewuzhe.ui.adapter.base.BeeBaseAdapter;

import materialdialogs.MaterialDialog;

/**
 * Created by xianguangjin on 15/12/14.
 */
public interface BaseView {

    Context getContext();

    void toast(String text);

    void snb(String text, View view);

    void snb(String text, View view, String actionText, View.OnClickListener action);

    void showSoftInput(View v);

    void hideSoftMethod(View v);

    void showDialog();

    void showInfoDialog(String title, String content, String positive, String negative, MaterialDialog.SingleButtonCallback positiveCallBack, MaterialDialog.SingleButtonCallback negativeCallBack);

    void showDefautInfoDialog(String title, String content, MaterialDialog.SingleButtonCallback positiveCallBack);

    /**
     * 用arraystring资源显示listDialog
     *
     * @param resId
     * @param callback
     */
    void showListDialog(@ArrayRes int resId, MaterialDialog.ListCallback callback, String title);

    void dismissDialog();

    /**
     * 用自定义的adapter显示listDialog
     *
     * @param adapter
     * @param callback
     */

    void showListDialog(BeeBaseAdapter adapter, MaterialDialog.ListCallback callback, String title);

    /**
     * 用自定义的adapter显示listDialog
     *
     * @param adapter
     * @param callback
     */
    void showListDialog(BeeBaseAdapter adapter, MaterialDialog.ListCallback callback);

    /**
     * 用arraystring资源显示listDialog
     *
     * @param resId
     * @param callback
     */
    void showListDialog(@ArrayRes int resId, MaterialDialog.ListCallback callback);

    boolean isShowingDialog();


    void startActivity(Class clazz);

    void startActivityForResult(Class clazz, int requestCode);

    void startActivity(Class clazz, Bundle bundle);

    void startActivityForResult(Class clazz, Bundle bundle, int requestCode);

    void finishActivity();


}
