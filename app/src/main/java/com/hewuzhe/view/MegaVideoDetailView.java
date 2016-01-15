package com.hewuzhe.view;

import com.hewuzhe.MegaVideo;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.Group;
import com.hewuzhe.model.MegaComment;
import com.hewuzhe.model.User;
import com.hewuzhe.model.Video;
import com.hewuzhe.view.base.CommentView;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/23.
 */
public interface MegaVideoDetailView extends ListView<MegaComment>, CommentView, GetView<Integer> {

    void setData(MegaVideo video);

    void setOtherVideos(ArrayList<Video> videos);

    void setCommentsData(ArrayList<Comment> data);

    void collectAndOther(boolean b, int flag, int position);

    void commentSuccess();

    void voteSuccess();

    void setGroupData(Group data);

    void setUserData(User data);

    void setDataCount(int recordcount);
}
