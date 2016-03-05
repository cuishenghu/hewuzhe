package com.hewuzhe.ui.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.MegaVideo;
import com.hewuzhe.R;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.Group;
import com.hewuzhe.model.MegaComment;
import com.hewuzhe.model.User;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.MegaVideoDetailPresenter;
import com.hewuzhe.ui.adapter.MegaCommentAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.ui.widget.VideoControllerView;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.MegaVideoDetailView;

import java.util.ArrayList;

import butterknife.Bind;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.widget.VideoView;

public class MegaVideoDetailActivity extends RecycleViewActivity<MegaVideoDetailPresenter, MegaCommentAdapter, MegaComment> implements MegaVideoDetailView {


    private static final String TAG = "video";
    @Bind(R.id.vv1)
    VideoControllerView mVDVideoView;

    private ImageView imgAvatar;
    private TextView tvUsername;
    private TextView tvAddTime;
    private TextView tvDesc;
    private TextView tvCommentCount;

    private int id;
    private boolean isFulllScreen = false;
    private WindowManager windowManager;
    private int width;
    private int height;
    private int mLayout;
    private View headerView;
    private Button btnPublish;
    private EditText edtComment;
    private TextView tvTotal;
    private TextView tvPageViews;
    private ImageView imgAvatar2;
    private int teamid;
    private int userid;
    private Button btnPost;
    private LinearLayout layVote;
    private MegaVideo _Video;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_mega_video_detail;
    }

    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        initHeader();
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        id = getIntentData().getInt("id");
        teamid = getIntentData().getInt("teamid");
        userid = getIntentData().getInt("userid");
        presenter.getVideoDetail(id, userid, teamid);
//      presenter.getOtherVideos(id);
        presenter.getData(page, count);

        if (MegaGameActivity.PAGE == 1) {
            presenter.getBasicInfo(teamid);
        } else {
            presenter.getUserData(userid);
        }
    }

    private void initHeader() {
        imgAvatar = (ImageView) headerView.findViewById(R.id.img_avatar);
        tvUsername = (TextView) headerView.findViewById(R.id.tv_username);
        tvAddTime = (TextView) headerView.findViewById(R.id.tv_add_time);
        tvTotal = (TextView) headerView.findViewById(R.id.tv_total);
        tvPageViews = (TextView) headerView.findViewById(R.id.tv_page_views);
        tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
        tvDesc = (TextView) headerView.findViewById(R.id.tv_desc);
        tvCommentCount = (TextView) headerView.findViewById(R.id.tv_comment_count);
        imgAvatar2 = (ImageView) headerView.findViewById(R.id.img_avatar_2);
        btnPublish = (Button) headerView.findViewById(R.id.btn_publish);
        btnPost = (Button) headerView.findViewById(R.id.btn_post);
        edtComment = (EditText) headerView.findViewById(R.id.edt_comment);
        layVote = (LinearLayout) headerView.findViewById(R.id.lay_vote);
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected MegaCommentAdapter provideAdapter() {
        headerView = getLayoutInflater().inflate(R.layout.header_video_mega_detail, null);
        return new MegaCommentAdapter(getContext(), presenter, headerView);
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /**
     * 绑定Presenter
     */
    @Override
    public MegaVideoDetailPresenter createPresenter() {
        return new MegaVideoDetailPresenter();
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        final int position = 0;
        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.publisComment(getData(), edtComment.getText().toString().trim(), view);
            }
        });

        mVDVideoView.setOnFullScreenBtnClickListener(new VideoControllerView.OnFullScreenBtnClick() {
            @Override
            public void onClick(View v) {
                int mCurrentOrientation = MegaVideoDetailActivity.this.getResources().getConfiguration().orientation;
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    MegaVideoDetailActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    getWindow().setAttributes(attrs);
                    //设置全屏
                    recyclerView.setVisibility(View.GONE);
                    toolBar.setVisibility(View.GONE);
//                    v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

                    mVDVideoView.btnFullScreen.setImageResource(R.mipmap.icon_origin_screen);

                }
                if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    MegaVideoDetailActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getWindow().setAttributes(attrs);
                    //取消全屏设置
                    toolBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    hideOrShowToolbar();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

                    mVDVideoView.btnFullScreen.setImageResource(R.mipmap.icon_full_screen);
                }
            }
        });

    }

    private void showShare() {

        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);


    }

    @Override
    protected String provideTitle() {
        return MegaGameActivity.PAGE == 0 ? "大赛个人详情" : "大赛战队详情";
//        return getIntent().getStringExtra("title");
    }

    @Override
    public void setData(MegaVideo video) {
        _Video = video;

        Glide.with(getContext())
                .load(C.BASE_URL + new SessionUtil(getContext()).getUser().PhotoPath)
                .placeholder(R.mipmap.img_avatar)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(getContext()))
                .into(imgAvatar2);


        tvTitle.setText(video.Title);
        tvAddTime.setText("编号：" + video.MatchCode + "");
        tvDesc.setText("简介：" + video.MatchDescription);

//        if (video.CommentNum <= 0) {
//            tvCommentCount.setText("暂无评论");
//        } else {
//            tvCommentCount.setText(video.CommentNum + "次");
//        }

        if (video.IsVote) {
            btnPost.setText("已投票");
            btnPost.setEnabled(false);

        } else {
            if (MegaGameActivity.PAGE == 0) {
                btnPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.vote(id, userid);
                    }
                });

            } else {
                btnPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.voteTeam(id, teamid);
                    }
                });

            }


        }

        tvTotal.setText("已获得票数：" + video.VoteNum);
        tvPageViews.setText("查看人数：" + video.VisitNum);


        if (!LibsChecker.checkVitamioLibs(this))
            return;

        mVDVideoView.setVideoPath(C.BASE_URL + video.MatchVideo);
        mVDVideoView.start();


    }

    /**
     * @param videos
     */
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
        edtComment.setText("");
        page = 1;
        presenter.getData(page, count);
    }

    @Override
    public void voteSuccess() {
        btnPost.setText("已投票");
        btnPost.setEnabled(false);
        _Video.VoteNum++;

        tvTotal.setText("已获得票数：" + _Video.VoteNum);
    }

    @Override
    public void setGroupData(Group data) {
        Glide.with(getContext())
                .load(C.BASE_URL + data.ImagePath)
                .placeholder(R.mipmap.img_avatar)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(getContext()))
                .into(imgAvatar);

        tvUsername.setText(data.Name);
    }

    @Override
    public void setUserData(User data) {
        Glide.with(getContext())
                .load(C.BASE_URL + data.PhotoPath)
                .placeholder(R.mipmap.img_avatar)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(getContext()))
                .into(imgAvatar);

        tvUsername.setText(data.NicName);
    }

    @Override
    public void setDataCount(int recordcount) {
        tvCommentCount.setText("共" + recordcount + "条");
    }


    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, MegaComment item) {

    }

    @Override
    public Integer getData() {
        return teamid == -1 ? userid : teamid;
    }


    @Override
    public void bindData(ArrayList<MegaComment> data) {
        bd(data);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayout = VideoView.VIDEO_LAYOUT_STRETCH;//全屏
            ViewGroup.LayoutParams params = mVDVideoView.getLayoutParams();
            params.height = windowManager.getDefaultDisplay().getHeight();
            params.width = windowManager.getDefaultDisplay().getWidth();
            mVDVideoView.setLayoutParams(params);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayout = VideoView.VIDEO_LAYOUT_FIT_PARENT;//原始尺寸

            ViewGroup.LayoutParams params = mVDVideoView.getLayoutParams();
            params.height = StringUtil.dip2px(getContext(), 350);
            params.width = windowManager.getDefaultDisplay().getWidth();
            mVDVideoView.setLayoutParams(params);
        }
        if (mVDVideoView.getVideoView() != null)
            mVDVideoView.getVideoView().setVideoLayout(mLayout, 0);

    }


    @Override
    protected void onDestroy() {
        mVDVideoView.release();
        super.onDestroy();
    }


}
