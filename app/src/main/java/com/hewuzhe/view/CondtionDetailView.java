package com.hewuzhe.view;

import android.view.View;

import com.hewuzhe.model.Comment;
import com.hewuzhe.model.FriendCondition;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.base.SetView;
import com.hewuzhe.view.common.SwipeRefreshView;

/**
 * Created by xianguangjin on 16/1/12.
 */
public interface CondtionDetailView extends ListView<Comment>, LoadMoreView, GetView<Integer>,SetView<FriendCondition>,SwipeRefreshView {


    abstract void collectAndOther(boolean b, int flag);


    void showCommentInput(int id, View view);

    void commentSuccess(Comment comment);

    void showReplyInput(int id, String nicName, int commenterId, View view);

    void replySuccess(Comment comment);
}
