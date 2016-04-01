package com.hewuzhe.view;

import com.hewuzhe.model.Bannar;
import com.hewuzhe.model.Friend;
import com.hewuzhe.model.New;
import com.hewuzhe.model.SaiShiPic;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.SwipeRefreshView;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/25.
 */
public interface MatchFragmentView extends LoadMoreView, ListView<New>, SwipeRefreshView, GetView<New> {

    void getZixunPicList(ArrayList<New> picList);
}
