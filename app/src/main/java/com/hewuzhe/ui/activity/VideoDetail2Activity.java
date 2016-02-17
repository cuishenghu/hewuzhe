package com.hewuzhe.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.hewuzhe.ui.widget.YsnowEditDialog;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TimeUtil;
import com.hewuzhe.view.VideoDetailView;
import com.sina.sinavideo.coreplayer.util.LogS;
import com.sina.sinavideo.sdk.VDVideoExtListeners;
import com.sina.sinavideo.sdk.VDVideoView;
import com.sina.sinavideo.sdk.VDVideoViewController;
import com.sina.sinavideo.sdk.data.VDVideoInfo;
import com.sina.sinavideo.sdk.data.VDVideoListInfo;
import com.sina.sinavideo.sdk.utils.VDVideoFullModeController;

import java.util.ArrayList;

import butterknife.Bind;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import materialdialogs.DialogAction;
import materialdialogs.MaterialDialog;

public class VideoDetail2Activity extends RecycleViewActivity<VideoDetailPresenter, CommentAdapter, Comment> implements VideoDetailView,
        VDVideoExtListeners.OnVDVideoInsertADListener, VDVideoExtListeners.OnVDVideoFrameADListener,
        VDVideoExtListeners.OnVDVideoPlaylistListener {

    private static final String TAG = "video";
    @Bind(R.id.vv1)
    VDVideoView mVDVideoView;
    @Bind(R.id.lay_no_vip)
    LinearLayout _LayNoVip;
    @Bind(R.id.btn_to_member)
    Button _BtnToMember;

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
     *                           <p/>
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        // 手动这是播放窗口父类，横屏的时候，会用这个做为容器使用，如果不设置，那么默认直接跳转到DecorView
        mVDVideoView.setVDVideoViewContainer((ViewGroup) mVDVideoView
                .getParent());

        initHeader();
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        id = getIntentData().getInt("Id");
        presenter.getVideoDetail(id);
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


        _BtnToMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), MemberActivity.class));
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
        presenter.getOtherVideos(_Video.UserId, id);

        ViewGroup.LayoutParams params = mVDVideoView.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;

        if (video.UserId == 0) {
            params.height = StringUtil.dip2px(getContext(), 200);
        } else {
            params.height = StringUtil.dip2px(getContext(), 320);
        }
        mVDVideoView.setLayoutParams(params);

        final int position = 0;

        if (video.UserId == 0) {
            tvAddTime.setText("播放" + video.VisitNum + "次");

            Glide.with(getContext())
                    .load(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.img_avatar)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(getContext()))
                    .into(imgAvatar);
        } else {

            tvAddTime.setText("发布于" + TimeUtil.timeAgo(video.PublishTime));
            Glide.with(getContext())
                    .load(C.BASE_URL + _Video.PhotoPath)
                    .placeholder(R.mipmap.img_avatar)
                    .centerCrop()
                    .crossFade()
                    .transform(new GlideCircleTransform(getContext()))
                    .into(imgAvatar);
        }

        Glide.with(getContext())
                .load(C.BASE_URL + new SessionUtil(getContext()).getUser().PhotoPath)
                .placeholder(R.mipmap.img_avatar)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(getContext()))
                .into(imgAvatar2);

        tvUsername.setText(video.UserNicName);

        if (video.Islike) {
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
                presenter.collectAndOther(id, 0, view, position, "");
            }
        });

        tvTitle.setText(video.Title);
        tvUsername.setText(video.UserNicName);
        tvDesc.setText("简介：" + video.Content);
        if (video.CommentNum <= 0) {
            tvCommentCount.setText("暂无评论");
        } else {
            tvCommentCount.setText(video.CommentNum + "次");
        }

        ImageView adIV = (ImageView) findViewById(R.id.adFrameImageView);
        adIV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(VideoDetail2Activity.this, "点击了静帧广告",
                        Toast.LENGTH_LONG).show();
            }
        });
        // 从layout里面得到播放器ID
        // 手动这是播放窗口父类，横屏的时候，会用这个做为容器使用，如果不设置，那么默认直接跳转到DecorView

        VDVideoListInfo infoList = new VDVideoListInfo();
        VDVideoInfo info = new VDVideoInfo();

        // 多流广告方式，就是在播放列表中配置广告流的方式
        // 单流方式：INSERTAD_TYPE_SINGLE，暂时不支持，如果设置了，会报exception
//		infoList.mInsertADType = VDVideoListInfo.INSERTAD_TYPE_MULTI;
        // 如果是两个或者以上的广告流，因为没办法直接取到每条流的时间，所以需要手动设置，否则会报exception
        // BTW：ticker组件最大显示长度为两位，也就是99，如果超过99秒的广告时长设置，会一直显示99，直到当前播放时间小于99为止
        // infoList.mInsertADSecNum = 133;
        // 填充播放列表，第一个是广告，理论上，可以在任何位置
//		info.mTitle = "这个是一个测试广告";
//		info.mPlayUrl = "http://v.iask.com/v_play_ipad.php?vid=137535755&tags=videoapp_android";
//		info.mIsInsertAD = true;
//		infoList.addVideoInfo(info);

//		info = new VDVideoInfo();
//		info.mTitle = "这就是一个测试视频0";
//		info.mPlayUrl = "http://120.27.115.235/UpLoad/Video/d82b761e-99f9-4bfe-843c-349472073759.mov";
//		infoList.addVideoInfo(info);

        info = new VDVideoInfo();
        info.mTitle = "视频";
//        info.mPlayUrl = "http://120.27.115.235/UpLoad/Video/a3a4eb2c-f953-4699-a6ee-b18217afcb35.mp4";
        info.mPlayUrl = C.BASE_URL + video.VideoPath;
        infoList.addVideoInfo(info);

//		info = new VDVideoInfo();
//		info.mTitle = "这就是一个测试视频2";
//		info.mPlayUrl = "http://v.iask.com/v_play_ipad.php?vid=131386882&tags=videoapp_android";
//		infoList.addVideoInfo(info);
//
//		info = new VDVideoInfo();
//		info.mTitle = "这就是一个测试视频3";
//		info.mPlayUrl = "http://v.iask.com/v_play_ipad.php?vid=131386882&tags=videoapp_android";
//		infoList.addVideoInfo(info);

        // 广告回调接口
        // FrameAD表明是一个帧间广告（点击暂停等出现的那个图）
//		mVDVideoView.setFrameADListener(this);
//		 InsertAD表明是一个前贴片广告，（插入广告）
//		mVDVideoView.setInsertADListener(this);
//		 播放列表回调接口
//		mVDVideoView.setPlaylistListener(this);

        // 简单方式处理的视频列表
//        VDVideoPlayListView listView = (VDVideoPlayListView) findViewById(R.id.play_list_view);
//        if (listView != null) {
//            listView.onVideoList(infoList);
//        }
        // 初始化播放器以及播放列表
        mVDVideoView.open(VideoDetail2Activity.this, infoList);
        // 开始播放，直接选择序号即可
        if (!video.IsFree) {
            layTranspond.setVisibility(View.GONE);
            layShare.setVisibility(View.GONE);

            if (new SessionUtil(getContext()).getUser().isVip()) {
                mVDVideoView.play(0);
            } else {
                _LayNoVip.setVisibility(View.VISIBLE);
            }
        } else {
            layTranspond.setVisibility(View.VISIBLE);
            layShare.setVisibility(View.VISIBLE);
            mVDVideoView.play(0);
        }


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
            public void onItemClick(View view, int pos, final Object item) {

                showDefautInfoDialog("提示", "要切换视频吗？", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Video video = (Video) item;
                        id = video.Id;
                        presenter.getVideoDetail(id);
                        page = 1;
                        presenter.getData(page, count);
                    }
                });


            }
        });

        reOthers.setAdapter(otherAdapter);
        if (videos != null && videos.size() > 0) {
            reOthers.setVisibility(View.VISIBLE);
            tvOtherCount.setText("共" + videos.size() + "部");
            otherAdapter.data.addAll(videos);
            otherAdapter.notifyDataSetChanged();
        } else {
            reOthers.setVisibility(View.GONE);
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
//                imgTranspond.setImageResource(R.mipmap.icon_share_focus);
//                imgTranspond.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        presenter.collectAndFavorateCance(id, 0, view);
//
//                    }
//                });

                snb("转发成功", imgAvatar);

                break;
            case 1:
                if (b) {
                    snb("收藏成功", imgAvatar);
                    imgCollect.setImageResource(R.mipmap.icon_collect_focus);
                    imgCollect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            presenter.collectAndFavorateCance(id, 1, view);

                        }
                    });

                } else {
                    snb("取消收藏", imgAvatar);

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
                    snb("点赞+1", imgAvatar);

                    imgPraise.setImageResource(R.mipmap.icon_praise_focus);
                    imgPraise.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            presenter.collectAndFavorateCance(id, 2, view);
                        }
                    });

                } else {
                    snb("取消点赞", imgAvatar);

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

//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            mLayout = VideoView.VIDEO_LAYOUT_STRETCH;//全屏
//            ViewGroup.LayoutParams params = videoController.getLayoutParams();
//            params.height = windowManager.getDefaultDisplay().getHeight();
//            params.width = windowManager.getDefaultDisplay().getWidth();
//            videoController.setLayoutParams(params);
//
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            mLayout = VideoView.VIDEO_LAYOUT_FIT_PARENT;//原始尺寸
//
//            ViewGroup.LayoutParams params = videoController.getLayoutParams();
//            params.height = StringUtil.dip2px(getContext(), 350);
//            params.width = windowManager.getDefaultDisplay().getWidth();
//            videoController.setLayoutParams(params);
//        }
//        if (videoController.getVideoView() != null)
//            videoController.getVideoView().setVideoLayout(mLayout, 0);
//    }


    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Comment item) {

        String content = "@" + item.CommenterNicName + ":" + item.Content;
        edtComment.setText(content);
        edtComment.setSelection(0);
        edtComment.requestFocus();

        if (getFirstVisiblePosition() == 0) {
            showSoftInput(edtComment);
        } else {
            recyclerView.scrollToPosition(1);
            recyclerView.smoothScrollBy(0, -100);
            showSoftInput(recyclerView);
        }
    }


    /**
     * 获取第一条展示的位置
     *
     * @return
     */
    private int getFirstVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] lastPositions = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        } else {
            position = 0;
        }
        return position;
    }

    /**
     * 获得当前展示最小的position
     *
     * @param positions
     * @return
     */
    private int getMinPositions(int[] positions) {
        int size = positions.length;
        int minPosition = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            minPosition = Math.min(minPosition, positions[i]);
        }
        return minPosition;
    }


    private RecyclerView.LayoutManager getLayoutManager() {
        return recyclerView.getLayoutManager();
    }


    @Override
    public Integer getData() {
        return id;
    }

    @Override
    protected void onDestroy() {
        ShareSDK.stopSDK(this);
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mVDVideoView.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mVDVideoView.onPause();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mVDVideoView.onStop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mVDVideoView.setIsFullScreen(true);
            isFulllScreen = true;

            LogS.e(VDVideoFullModeController.TAG, "onConfigurationChanged---横屏");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mVDVideoView.setIsFullScreen(false);
            isFulllScreen = false;
            LogS.e(VDVideoFullModeController.TAG, "onConfigurationChanged---竖屏");
        }
    }


    /**
     * 播放列表里面点击了某个视频，触发外部事件
     */
    @Override
    public void onPlaylistClick(VDVideoInfo info, int p) {
        // TODO Auto-generated method stub
        if (info == null) {
            LogS.e(TAG, "info is null");
        }
        mVDVideoView.play(p);
    }

    /**
     * 视频插入广告传回的接口，表明当前的广告被点击了『了解更多』
     */
    @Override
    public void onInsertADClick(VDVideoInfo info) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "插入广告被点击了", Toast.LENGTH_LONG).show();
    }

    /**
     * 视频插入广告传回的接口，表明当前的广告被点击了『去掉广告』，按照其他视频逻辑，直接跳转会员页
     */
    @Override
    public void onInsertADStepOutClick(VDVideoInfo info) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "去掉广告被点击了", Toast.LENGTH_LONG).show();
    }

    /**
     * 静帧广告换图，从这儿来
     */
    @Override
    public void onFrameADPrepared(VDVideoInfo info) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "开始换图", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
//        Toast.makeText(this, "isFulllScreen:" + isFulllScreen, Toast.LENGTH_SHORT).show();

        if (isFulllScreen) {
            VDVideoViewController controller = VDVideoViewController
                    .getInstance(getContext());
            if (null != controller)
                controller.setIsFullScreen(false);
        } else {
            super.onBackPressed();
        }

    }
}
