package com.hewuzhe.ui.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.VideoDetailPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.ui.widget.VideoControllerView;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.VideoDetailView;

import java.util.ArrayList;

import butterknife.Bind;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.widget.VideoView;

public class VideoDetailActivity extends ToolBarActivity<VideoDetailPresenter> implements VideoDetailView {


    @Bind(R.id.video_controller)
    VideoControllerView videoController;
    @Bind(R.id.img_avatar)
    ImageView imgAvatar;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.tv_times)
    TextView tvTimes;
    @Bind(R.id.img_collect)
    ImageView imgCollect;
    @Bind(R.id.lay_collect)
    LinearLayout layCollect;
    @Bind(R.id.img_praise)
    ImageView imgPraise;
    @Bind(R.id.lay_praise)
    LinearLayout layPraise;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_desc)
    TextView tvDesc;
    @Bind(R.id.tv_comment_count)
    TextView tvCommentCount;
    @Bind(R.id.lay_other)
    NestedScrollView layOther;
    private int id;
    private boolean isFulllScreen = false;
    private WindowManager windowManager;
    private int width;
    private int height;
    private int mLayout;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_video_detail;
    }


    /**
     * @param savedInstanceState 缓存数据
     *                           <p>
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        id = getIntent().getBundleExtra("data").getInt("Id");
        presenter.getVideoDetail(id);

    }


    /**
     * 绑定Presenter
     */
    @Override
    public VideoDetailPresenter createPresenter() {
        return new VideoDetailPresenter();
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        videoController.setOnFullScreenBtnClickListener(new VideoControllerView.OnFullScreenBtnClick() {
            @Override
            public void onClick(View v) {
                int mCurrentOrientation = VideoDetailActivity.this.getResources().getConfiguration().orientation;
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    VideoDetailActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    getWindow().setAttributes(attrs);
                    //设置全屏
                    layOther.setVisibility(View.GONE);
                    toolBar.setVisibility(View.GONE);
//                    v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

                    videoController.btnFullScreen.setImageResource(R.mipmap.icon_origin_screen);

                }
                if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    VideoDetailActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getWindow().setAttributes(attrs);
                    //取消全屏设置
                    toolBar.setVisibility(View.VISIBLE);
                    layOther.setVisibility(View.VISIBLE);
                    hideOrShowToolbar();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

                    videoController.btnFullScreen.setImageResource(R.mipmap.icon_full_screen);
                }
//

            }
        });

    }

    @Override
    protected String provideTitle() {
        return "视频详情";
//        return getIntent().getStringExtra("title");
    }


    @Override
    public void setData(Video video) {
        Glide.with(getContext())
                .load(C.BASE_URL + video.PhotoPath)
                .placeholder(R.mipmap.img_avatar)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(getContext()))
                .into(imgAvatar);

        tvUsername.setText(video.UserNicName);

        tvUsername.setText("名字：" + video.Title);
        tvTimes.setText("播放" + video.VisitNum + "次");
        tvDesc.setText("简介：" + video.Content);
        if (video.CommentNum <= 0) {
            tvCommentCount.setText("暂无评论");

        } else {
            tvCommentCount.setText(video.CommentNum + "次");
        }

        if (!LibsChecker.checkVitamioLibs(this))
            return;

        videoController.setVideoPath(C.BASE_URL + video.VideoPath);
        videoController.start();
    }

    @Override
    public void setOtherVideos(ArrayList<Video> videos) {

    }

    @Override
    public void setCommentsData(ArrayList<Comment> data) {

    }

    @Override
    public void collectAndOther(boolean b, int flag, int position) {

    }


    @Override
    public void commentSuccess() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayout = VideoView.VIDEO_LAYOUT_STRETCH;//全屏
            ViewGroup.LayoutParams params = videoController.getLayoutParams();
            params.height = windowManager.getDefaultDisplay().getHeight();
            params.width = windowManager.getDefaultDisplay().getWidth();
            videoController.setLayoutParams(params);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayout = VideoView.VIDEO_LAYOUT_FIT_PARENT;//原始尺寸

            ViewGroup.LayoutParams params = videoController.getLayoutParams();
            params.height = StringUtil.dip2px(getContext(), 200);
            params.width = windowManager.getDefaultDisplay().getWidth();
            videoController.setLayoutParams(params);
        }
        if (videoController.getVideoView() != null)
            videoController.getVideoView().setVideoLayout(mLayout, 0);
    }

    @Override
    public Integer getData() {
        return id;
    }

    /**
     * 加载更多
     */
    @Override
    public void loadMore() {

    }

    @Override
    public void noMore() {

    }

    @Override
    public void hasMore() {

    }
}
