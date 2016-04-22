package com.hewuzhe.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hewuzhe.R;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.Dojo;
import com.hewuzhe.model.OtherImage;
import com.hewuzhe.model.Pic;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.DojoDetail2Presenter;
import com.hewuzhe.presenter.VideoDetailPresenter;
import com.hewuzhe.ui.adapter.CommentAdapter;
import com.hewuzhe.ui.adapter.DojoAdapter;
import com.hewuzhe.ui.adapter.OtherVideosAdapter;
import com.hewuzhe.ui.adapter.common.OtherImgsAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.ui.base.RecycleViewNoMoreActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.ui.inter.OnItemClickListener;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.ui.widget.VideoControllerView;
import com.hewuzhe.ui.widget.YsnowEditDialog;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TimeUtil;
import com.hewuzhe.view.DojoDetailView;
import com.hewuzhe.view.VideoDetailView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.vov.vitamio.widget.VideoView;
import materialdialogs.DialogAction;
import materialdialogs.MaterialDialog;

/**
 * Created by zycom on 2016/3/17.
 */
public class DojoDetail2Activity extends RecycleViewNoMoreActivity<DojoDetail2Presenter, DojoAdapter, Comment> implements DojoDetailView {

    private static final String TAG = "video";
    private static int HEITH_VIDEO = 200;
    @Bind(R.id.vv1)
    VideoControllerView mVDVideoView;
    @Bind(R.id.lay_no_vip)
    LinearLayout _LayNoVip;
    @Bind(R.id.btn_to_member)
    Button _BtnToMember;
    @Bind(R.id.img)
    ImageView _Img;
    @Bind(R.id.play)
    LinearLayout play;

    TextView _TvName;
    ImageView _ImgCollect;
    LinearLayout _LayCollect;
    ImageView _ImgPraise;
    LinearLayout _LayPraise;
    TextView _TvAddress;
    ImageView _TvCall;
    TextView _ImgCall;
    TextView _TvDesc;
    TextView _TvCommentCount;
    RecyclerView _RecyclerView;
    private Dojo dojo;
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
        return R.layout.activity_dojo_2_detail;
    }

    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        io.vov.vitamio.LibsChecker.checkVitamioLibs(this);
        super.initThings(savedInstanceState);
        // 手动这是播放窗口父类，横屏的时候，会用这个做为容器使用，如果不设置，那么默认直接跳转到DecorView
        initHeader();
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        id = getIntentData().getInt("id");
        presenter.getDetail();
        presenter.getOthers();
    }

    @OnClick(R.id.play)
    public void playClick() {
        mVDVideoView.start();
        play.setVisibility(View.GONE);
    }

    private void initHeader() {
        _TvName = (TextView) headerView.findViewById(R.id.tv_name);
        _ImgCollect = (ImageView) headerView.findViewById(R.id.img_collect);
        _LayCollect = (LinearLayout) headerView.findViewById(R.id.lay_collect);
        _ImgPraise = (ImageView) headerView.findViewById(R.id.img_praise);
        _LayPraise = (LinearLayout) headerView.findViewById(R.id.lay_praise);
        _TvAddress = (TextView) headerView.findViewById(R.id.tv_address);
        _TvCall = (ImageView) headerView.findViewById(R.id.tv_call);
        _ImgCall = (TextView) headerView.findViewById(R.id.img_call);
        _TvDesc = (TextView) headerView.findViewById(R.id.tv_desc);
        _TvCommentCount = (TextView) headerView.findViewById(R.id.tv_comment_count);
        _RecyclerView = (RecyclerView) headerView.findViewById(R.id.recycler_view);

    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected DojoAdapter provideAdapter() {
        headerView = getLayoutInflater().inflate(R.layout.header_dojo2_detail, null);
        return new DojoAdapter(getContext(), presenter, headerView);
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
    public DojoDetail2Presenter createPresenter() {
        return new DojoDetail2Presenter();
    }


    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

        mVDVideoView.setOnFullScreenBtnClickListener(new VideoControllerView.OnFullScreenBtnClick() {
            @Override
            public void onClick(View v) {
                int mCurrentOrientation = DojoDetail2Activity.this.getResources().getConfiguration().orientation;
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    DojoDetail2Activity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
                    DojoDetail2Activity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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


    @Override
    protected String provideTitle() {
        return "";
    }


    @Override
    public void setData(final Dojo dojo) {
        this.dojo = dojo;
        if (dojo.VideoPath.trim().equals("")) {
            mVDVideoView.setVisibility(View.GONE);
            _Img.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(C.BASE_URL + dojo.ImagePath)
                    .fitCenter()
                    .crossFade()
                    .into(_Img);

            _Img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pic pic = new Pic();
                    pic.PictureUrl = dojo.ImagePath;
                    ArrayList<Pic> pics = new ArrayList();
                    pics.add(pic);
                    startActivity(PicsActivity.class, new Bun().putString("pics", new Gson().toJson(pics)).ok());
                }
            });
        } else {
            mVDVideoView.setVisibility(View.VISIBLE);
            _Img.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = mVDVideoView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;

            HEITH_VIDEO = 200;
            params.height = StringUtil.dip2px(getContext(), HEITH_VIDEO);

            mVDVideoView.setLayoutParams(params);
            startPlay(C.BASE_URL + dojo.VideoPath);

        }


        _TvName.setText(dojo.Title);
        _ImgCall.setText(dojo.TelePhone);

        _TvAddress.setText(dojo.Address);
        _TvDesc.setText(dojo.Content);
        _TvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dojo.TelePhone)));
            }
        });
        _ImgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dojo.TelePhone)));
            }
        });

        _TvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(BasicMapActivity.class, new Bun().putString("lat", dojo.Lat).putString("lng", dojo.Lng).putString("address", dojo.Address).putString("name", dojo.Title).putString("title", "武馆位置").ok());
            }
        });
    }

    private void startPlay(String videoPath) {
        mVDVideoView.setonPreparedListener(new VideoControllerView.OnPreparedListener() {
            @Override
            public void onPrepared() {
                mVDVideoView.getVideoView().setVideoLayout(VideoView.VIDEO_LAYOUT_FIT_PARENT, 0);
            }
        });
//       mVDVideoView.btnFullScreen.setVisibility(View.GONE);
        mVDVideoView.setVideoPath(videoPath);
        play.setVisibility(View.VISIBLE);
    }

    @Override
    public void setOthers(ArrayList<OtherImage> data) {
        if (data != null && data.size() > 0) {
            _TvCommentCount.setText("共" + data.size() + "条");
            _RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            OtherImgsAdapter otherImgsAdapter = new OtherImgsAdapter(getContext());
            _RecyclerView.setAdapter(otherImgsAdapter);
            otherImgsAdapter.addDatas(data);

            final ArrayList<Pic> pics = new ArrayList<>();
            for (OtherImage otherImage : data) {
                Pic pic = new Pic();
                pic.PictureUrl = otherImage.ImagePath;
                pics.add(pic);
            }

            otherImgsAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int pos, Object item) {
                    startActivity(PicsActivity.class, new Bun().putInt("pos", pos).putString("pics", new Gson().toJson(pics)).ok());
                }
            });

        } else {
            _TvCommentCount.setText("共" + 0 + "条");

        }

    }

    @Override
    public void collectAndOther(boolean b, int flag, int position) {

    }

//    @Override
//    public void setCommentsData(ArrayList<Comment> comments) {
//        bd(comments);
//    }


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
        mVDVideoView.release();
        super.onDestroy();
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
            params.height = StringUtil.dip2px(getContext(), HEITH_VIDEO);
            params.width = windowManager.getDefaultDisplay().getWidth();
            mVDVideoView.setLayoutParams(params);
        }
        if (mVDVideoView.getVideoView() != null)
            mVDVideoView.getVideoView().setVideoLayout(mLayout, 0);
    }


    @Override
    public void onBackPressed() {
//        Toast.makeText(this, "isFulllScreen:" + isFulllScreen, Toast.LENGTH_SHORT).show();

        if (isFulllScreen) {
        } else {
            super.onBackPressed();
        }

    }
}
