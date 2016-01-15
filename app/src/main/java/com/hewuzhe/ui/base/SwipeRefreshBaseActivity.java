/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.hewuzhe.ui.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.common.SwipeRefreshView;

import butterknife.Bind;

/**
 * Created by drakeet on 1/3/15.
 */
public abstract class SwipeRefreshBaseActivity<P extends BasePresenterImp> extends ToolBarActivity<P> implements SwipeRefreshView {

    @Bind(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean mIsRequestDataRefresh = false;

    /**
     * @param savedInstanceState 缓存数据
     *                           <p>
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        trySetupSwipeRefresh();
    }

    void trySetupSwipeRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_3,
                    R.color.refresh_progress_2, R.color.refresh_progress_1);
            // do not use lambda!!
            mSwipeRefreshLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            requestDataRefresh();
                        }
                    });
            setProgressViewOffset(false, 0, StringUtil.dip2px(getContext(), 24));
        }
    }


    /**
     * 从数据源获取数据
     */
    public void requestDataRefresh() {
        mIsRequestDataRefresh = true;
    }


    @Override
    public void refresh(boolean refreshing) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        mIsRequestDataRefresh = refreshing;
        if (!mIsRequestDataRefresh) {
            mIsRequestDataRefresh = false;
            // 防止刷新消失太快，让子弹飞一会儿.
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 1000);
        } else {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    public void setProgressViewOffset(boolean scale, int start, int end) {
        mSwipeRefreshLayout.setProgressViewOffset(scale, start, end);
    }

    public boolean isRequestDataRefresh() {
        return mIsRequestDataRefresh;
    }

}
