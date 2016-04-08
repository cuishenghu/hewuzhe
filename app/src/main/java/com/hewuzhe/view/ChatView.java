package com.hewuzhe.view;

import com.hewuzhe.model.ChatList;
import com.hewuzhe.model.RecommendUser;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.SwipeRefreshView;

import java.util.ArrayList;

/**
 * Created by zycom on 2016/3/29.
 */
public interface ChatView extends LoadMoreView, ListView<ChatList> ,SwipeRefreshView{
    void bindCount(int count);
    void bindTuijian(ArrayList<ChatList> data);
    void bindTopFourTuijian(ArrayList<ChatList> data);
    void bindUsers(ArrayList<RecommendUser> data);
}
