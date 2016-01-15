package com.hewuzhe.view.adapter;

import android.view.View;

import com.hewuzhe.model.Comment;
import com.hewuzhe.view.base.LoadMoreView;

/**
 * 状态列表Adapter的View
 * Created by xianguangjin on 16/1/1.
 */
public interface ConditionView extends LoadMoreView {

    /**
     * 点赞收藏等
     *
     * @param b
     * @param flag
     * @param position
     */
    void collectAndOther(boolean b, int flag, int position);

    /**
     * 显示评论框
     *
     * @param id
     * @param position
     * @param v
     */
    void showCommentInput(int id, int position, View v);

    void commentSuccess(int position, Comment comment);

    /**
     * 回复评论
     *  @param id
     * @param nicName
     * @param position
     * @param v
     */
    void showReplyInput(int id, String nicName, int position, View v);

    void replySuccess(int position, Comment comment);


}
