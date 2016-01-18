package com.hewuzhe.view;

import com.hewuzhe.model.Comment;
import com.hewuzhe.model.Video;
import com.hewuzhe.view.base.CommentView;
import com.hewuzhe.view.base.GetView;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/23.
 */
public interface VideoDetailView extends CommentView,GetView<Integer> {

    void setData(Video video);

    void setOtherVideos(ArrayList<Video> videos);

    void setCommentsData(ArrayList<Comment> data);

    void collectAndOther(boolean b, int flag, int position);

    void commentSuccess();

    void isWuYou(Boolean data, int userid);
}
