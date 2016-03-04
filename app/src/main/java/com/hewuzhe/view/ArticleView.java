package com.hewuzhe.view;

import com.hewuzhe.model.Article;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.WebContents;
import com.hewuzhe.view.adapter.ConditionView;
import com.hewuzhe.view.base.CommentView;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.SetView;

/**
 * Created by xianguangjin on 15/12/31.
 */
public interface ArticleView extends SetView<Article>, CommentView, ConditionView,GetView<Integer>,ListView<Comment> {


    void collectAndOther(boolean b, int flag);

    void isWuYou(Boolean data, int userid);

    void setWeb(WebContents res);
}
