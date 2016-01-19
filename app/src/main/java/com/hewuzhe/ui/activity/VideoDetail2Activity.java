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
import com.hewuzhe.R;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.VideoDetailPresenter;
import com.hewuzhe.ui.adapter.CommentAdapter;
import com.hewuzhe.ui.adapter.OtherVideosAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.inter.OnItemClickListener;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.ui.widget.VideoControllerView;
import com.hewuzhe.ui.widget.YsnowEditDialog;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.VideoDetailView;

import java.util.ArrayList;

import butterknife.Bind;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.widget.VideoView;

public class VideoDetail2Activity extends RecycleViewActivity<VideoDetailPresenter, CommentAdapter, Comment> implements VideoDetailView {


    @Bind(R.id.video_controller)
    VideoControllerView videoController;

    private ImageView imgAvatar;
    private TextView tvUsername;
    private TextView tvAddTime;
    private LinearLayout layTranspond;
    private ImageView imgTranspond;
    private LinearLayout layShare;
    private ImageView imgShare;
    private LinearLayout layCollect;
    private ImageView imgCollect;
    private LinearLayout layPraise;
    private ImageView imgPraise;
    private TextView tvTitle;
    private TextView tvDesc;
    private TextView tvOtherCount;
    private RecyclerView reOthers;
    private TextView tvCommentCount;
    private ImageView imgAvatar2;

    private int id;
    private boolean isFulllScreen = false;
    private WindowManager windowManager;
    private int width;
    private int height;
    private int mLayout;
    private View headerView;
    private Button btnPublish;
    private EditText edtComment;
    private TextView tvReport;
    private Video _Video;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_video_2_detail;
    }

    /**
     * @param savedInstanceState 缓存数据
     *                           <p>
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {

        io.vov.vitamio.LibsChecker.checkVitamioLibs(this);

        super.initThings(savedInstanceState);
        initHeader();
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        id = getIntentData().getInt("Id");
        presenter.getVideoDetail(id);
        presenter.getOtherVideos(id);
        presenter.getData(page, count);
    }

    private void initHeader() {
        imgAvatar = (ImageView) headerView.findViewById(R.id.img_avatar);
        tvUsername = (TextView) headerView.findViewById(R.id.tv_username);
        tvAddTime = (TextView) headerView.findViewById(R.id.tv_add_time);
        layTranspond = (LinearLayout) headerView.findViewById(R.id.lay_transpond);
        imgTranspond = (ImageView) headerView.findViewById(R.id.img_transpond);
        layShare = (LinearLayout) headerView.findViewById(R.id.lay_share);
        imgShare = (ImageView) headerView.findViewById(R.id.img_share);
        layCollect = (LinearLayout) headerView.findViewById(R.id.lay_collect);
        imgCollect = (ImageView) headerView.findViewById(R.id.img_collect);
        layPraise = (LinearLayout) headerView.findViewById(R.id.lay_praise);
        imgPraise = (ImageView) headerView.findViewById(R.id.img_praise);
        tvTitle = (TextView) headerView.findViewById(R.id.tv_title);
        tvReport = (TextView) headerView.findViewById(R.id.tv_report);
        tvDesc = (TextView) headerView.findViewById(R.id.tv_desc);
        tvOtherCount = (TextView) headerView.findViewById(R.id.tv_other_count);
        reOthers = (RecyclerView) headerView.findViewById(R.id.re_others);
        tvCommentCount = (TextView) headerView.findViewById(R.id.tv_comment_count);
        imgAvatar2 = (ImageView) headerView.findViewById(R.id.img_avatar_2);
        btnPublish = (Button) headerView.findViewById(R.id.btn_publish);
        edtComment = (EditText) headerView.findViewById(R.id.edt_comment);
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected CommentAdapter provideAdapter() {
        headerView = getLayoutInflater().inflate(R.layout.header_video2_detail, null);
        return new CommentAdapter(getContext(), presenter, headerView);
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
                int mCurrentOrientation = VideoDetail2Activity.this.getResources().getConfiguration().orientation;
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    VideoDetail2Activity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    getWindow().setAttributes(attrs);
                    //设置全屏
                    recyclerView.setVisibility(View.GONE);
                    toolBar.setVisibility(View.GONE);
//                    v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

                    videoController.btnFullScreen.setImageResource(R.mipmap.icon_origin_screen);

                }
                if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    VideoDetail2Activity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    getWindow().setAttributes(attrs);
                    //取消全屏设置
                    toolBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    hideOrShowToolbar();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

                    videoController.btnFullScreen.setImageResource(R.mipmap.icon_full_screen);
                }
            }
        });

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.publisComment(id, edtComment.getText().toString().trim(), view);
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showShare();
            }
        });


        tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final YsnowEditDialog ysnowEditDialog = new YsnowEditDialog(getContext(), "举报事由", "");
                ysnowEditDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ysnowEditDialog.dismiss();

                        String conent = ysnowEditDialog.content.getText().toString().trim();
                        if (StringUtil.isEmpty(conent)) {
                            toast("举报内容不能为空");
                            return;
                        }
                        presenter.collectAndOther(id, 3, tvAction, 1, conent);
                    }
                });

                ysnowEditDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ysnowEditDialog.dismiss();
                    }
                });


                ysnowEditDialog.show();
            }
        });


        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_Video.UserId == 0) {

                    startActivity(AboutActivity.class);

                } else {
                    presenter.isWuyou(_Video.UserId);
                }
            }
        });

        imgAvatar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ProfileActivity.class);
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
        return "";
//        return getIntent().getStringExtra("title");
    }

    @Override
    public void setData(Video video) {
        _Video = video;
        final int position = 0;

        Glide.with(getContext())
                .load(C.BASE_URL + _Video.PhotoPath)
                .placeholder(R.mipmap.img_avatar)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(getContext()))
                .into(imgAvatar);


        Glide.with(getContext())
                .load(C.BASE_URL + new SessionUtil(getContext()).getUser().PhotoPath)
                .placeholder(R.mipmap.img_avatar)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(getContext()))
                .into(imgAvatar2);


        tvUsername.setText(video.UserNicName);

        if (video.IsLike) {
            imgPraise.setImageResource(R.mipmap.icon_praise_focus);
            imgPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.collectAndFavorateCance(id, 2, view);
                }
            });

        } else {
            imgPraise.setImageResource(R.mipmap.icon_praise);

            imgPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.collectAndOther(id, 2, view, position, "");
                }
            });

        }

        if (video.IsFavorite) {
            imgCollect.setImageResource(R.mipmap.icon_collect_focus);

            imgCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.collectAndFavorateCance(id, 1, view);

                }
            });

        } else {
            imgCollect.setImageResource(R.mipmap.icon_collect);
            imgCollect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.collectAndOther(id, 1, view, position, "");

                }
            });

        }

        imgTranspond.setImageResource(R.mipmap.icon_share);
        imgTranspond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.collectAndFavorateCance(id, 0, view);
            }
        });

        tvTitle.setText(video.Title);
        tvUsername.setText(video.UserNicName);
        tvAddTime.setText("播放" + video.VisitNum + "次");
        tvDesc.setText("简介：" + video.Content);
        if (video.CommentNum <= 0) {
            tvCommentCount.setText("暂无评论");
        } else {
            tvCommentCount.setText(video.CommentNum + "次");
        }

        if (!LibsChecker.checkVitamioLibs(this))
            return;

//
//        if (video.UpLoadType == 0) {
//            ViewGroup.LayoutParams params = videoController.getLayoutParams();
//            params.height = StringUtil.dip2px(getContext(), 350);
//            params.width = windowManager.getDefaultDisplay().getWidth();
//            videoController.setLayoutParams(params);
//            videoController.btnFullScreen.setVisibility(View.GONE);
//
//        }
//        else {
//            ViewGroup.LayoutParams params = videoController.getLayoutParams();
//            params.height = StringUtil.dip2px(getContext(), 200);
//            params.width = windowManager.getDefaultDisplay().getWidth();
//            videoController.setLayoutParams(params);
//            videoController.btnFullScreen.setVisibility(View.VISIBLE);
//
//        }

        mLayout = VideoView.VIDEO_LAYOUT_FIT_PARENT;
        videoController.getVideoView().setVideoLayout(mLayout, 0);


        videoController.btnFullScreen.setVisibility(View.GONE);
        videoController.setVideoPath(C.BASE_URL + video.VideoPath);
        videoController.start();

    }

    /**
     * @param videos
     */
    @Override
    public void setOtherVideos(ArrayList<Video> videos) {
        reOthers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        OtherVideosAdapter otherAdapter = new OtherVideosAdapter(getContext());
        otherAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos, Object item) {
                Video video = (Video) item;
                id = video.Id;
                presenter.getVideoDetail(id);
                presenter.getOtherVideos(id);
                page = 1;
                presenter.getData(page, count);
            }
        });
        reOthers.setAdapter(otherAdapter);
        if (videos != null && videos.size() > 0) {
            tvOtherCount.setText("共" + videos.size() + "部");
            otherAdapter.data.addAll(videos);
            otherAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setCommentsData(ArrayList<Comment> comments) {
        bd(comments);
    }

    @Override
    public void collectAndOther(boolean b, int flag, final int position) {
        switch (flag) {
            case 0:
                imgTranspond.setImageResource(R.mipmap.icon_share_focus);
                imgTranspond.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.collectAndFavorateCance(id, 0, view);

                    }
                });
                break;
            case 1:
                if (b) {
                    imgCollect.setImageResource(R.mipmap.icon_collect_focus);
                    imgCollect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            presenter.collectAndFavorateCance(id, 1, view);

                        }
                    });

                } else {
                    imgCollect.setImageResource(R.mipmap.icon_collect);
                    imgCollect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            presenter.collectAndOther(id, 1, view, position, "");

                        }
                    });

                }

                break;
            case 2:
                if (b) {
                    imgPraise.setImageResource(R.mipmap.icon_praise_focus);
                    imgPraise.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            presenter.collectAndFavorateCance(id, 2, view);
                        }
                    });

                } else {
                    imgPraise.setImageResource(R.mipmap.icon_praise);
                    imgPraise.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            presenter.collectAndOther(id, 2, view, position, "");
                        }
                    });

                }

                break;

            case 3:
                if (b) {
//                    imgPraise.setImageResource(R.mipmap.icon_praise_focus);
                    snb("举报成功", tvAction);
                } else {
//                    imgPraise.setImageResource(R.mipmap.icon_praise);
                }

                break;
        }
    }


    @Override
    public void commentSuccess() {
        edtComment.setText("");
        presenter.getData(page, count);
    }


    @Override
    public void isWuYou(Boolean data, int userid) {
        if (data) {
            startActivity(FriendProfileActivity.class, new Bun().putInt("id", userid).ok());
        } else {
            startActivity(StrangerProfileSettingsActivity.class, new Bun().putInt("id", userid).ok());
        }

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
            mLayout = VideoView.VIDEO_LAYOUT_SCALE;//原始尺寸

            ViewGroup.LayoutParams params = videoController.getLayoutParams();
            params.height = StringUtil.dip2px(getContext(), 200);
            params.width = windowManager.getDefaultDisplay().getWidth();
            videoController.setLayoutParams(params);
        }
        if (videoController.getVideoView() != null)
            videoController.getVideoView().setVideoLayout(mLayout, 0);
    }


    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Comment item) {


    }

    @Override
    public Integer getData() {
        return id;
    }

    @Override
    protected void onDestroy() {
        videoController.release();
        ShareSDK.stopSDK(this);
        super.onDestroy();
    }
}
