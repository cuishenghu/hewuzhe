package com.hewuzhe.ui.inter;

import android.view.View;

/**
 * Created by xianguangjin on 15/12/22.
 */
public interface OnItemClickListener<M>{
    /**
     * @param view
     * @param pos
     * 点击事件
     */
    public void onItemClick(View view, int pos, M item);
}
