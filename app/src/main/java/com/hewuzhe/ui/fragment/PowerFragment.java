package com.hewuzhe.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.duanqu.qupai.android.app.QupaiServiceImpl;
import com.duanqu.qupai.engine.session.MovieExportOptions;
import com.duanqu.qupai.engine.session.VideoSessionCreateInfo;
import com.duanqu.qupai.recorder.EditorCreateInfo;
import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.MainActivity;
import com.hewuzhe.ui.activity.PublishVideoActivity;
import com.hewuzhe.ui.activity.SearchVideosActivity;
import com.hewuzhe.ui.adapter.MyViewPagerAdapter;
import com.hewuzhe.ui.base.BaseFragment;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.cons.Contant;
import com.hewuzhe.ui.cons.FileUtils;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.PowerView;

import butterknife.Bind;
import cn.xm.weidongjian.popuphelper.PopupWindowHelper;
import imagechooser.api.ChooserType;
import imagechooser.api.ChosenVideo;
import imagechooser.api.VideoChooserListener;
import imagechooser.api.VideoChooserManager;

/**
 * Created by xianguangjin on 15/12/8.
 */
public class PowerFragment extends BaseFragment implements PowerView, VideoChooserListener {

    protected Toolbar toolBar;
    @Bind(R.id.img_add)
    ImageView imgAdd;
    @Bind(R.id.img_search)
    ImageView imgSearch;
    @Bind(R.id.swicth_button)
    CheckBox swicthButton;
    private ImageView imgBack;
    private TextView tvTitle;
    private AppBarLayout appBar;
    private boolean mIsHidden = false;
    private View popView;
    private TextView tvLocalVideo;
    private TextView tvTakeVideo;
    private PopupWindowHelper popupWindowHelper;
    private int chooserType;
    private VideoChooserManager videoChooserManager;
    private int beautySkinProgress;
    private final EditorCreateInfo _CreateInfo = new EditorCreateInfo();

    private String videoPath;
    public static int QUPAI_RECORD_REQUEST = 101;
    private MyViewPagerAdapter viewPagerAdapter;


    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopView();
            }
        });
        swicthButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((OneFragment) viewPagerAdapter.getItem(0)).changeSpanCount(isChecked);
                ((OneFragment) viewPagerAdapter.getItem(1)).changeSpanCount(isChecked);
                ((OneFragment) viewPagerAdapter.getItem(2)).changeSpanCount(isChecked);
                ((OneFragment) viewPagerAdapter.getItem(3)).changeSpanCount(isChecked);
            }
        });
    }

    /**
     * 初始化一些事情
     *
     * @param view
     */
    @Override
    protected void initThings(View view) {
        initToolBar(view);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchVideosActivity.class, new Bun().putString("title", "搜索").ok());
            }
        });

    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_power;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }


    protected void initToolBar(View rootView) {
        toolBar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolBar);
        imgBack = (ImageView) rootView.findViewById(R.id.img_back);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        appBar = (AppBarLayout) rootView.findViewById(R.id.app_bar_layout);
        tvTitle.setText("视频");

        final ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPagerAdapter = new MyViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPagerAdapter.addFragment(OneFragment.newInstance("NewVideo"), "最新");//添加Fragment
        viewPagerAdapter.addFragment(OneFragment.newInstance("HotVideo"), "热门");
        viewPagerAdapter.addFragment(OneFragment.newInstance("YuanchuangVideo"), "关注");
//        viewPagerAdapter.addFragment(OneFragment.newInstance("TuijianVideo"), "推荐");
        viewPagerAdapter.addFragment(FiveFragment.newInstance(), "推荐");
        viewPagerAdapter.addFragment(FiveFragment.newInstance(), "交流");
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(viewPagerAdapter);

        TabLayout mTabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab());//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                swicthButton.setVisibility(tab.getPosition() == 4||tab.getPosition() == 3 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    /**
     * 显示拍摄视频的PopView
     */
    @Override
    public void showPopView() {
        if (popupWindowHelper == null) {
            popView = LayoutInflater.from(getActivity()).inflate(R.layout.popview, null);
            tvLocalVideo = (TextView) popView.findViewById(R.id.tv_local_video);
            tvTakeVideo = (TextView) popView.findViewById(R.id.tv_take_video);
            tvLocalVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pickVideo(view);

                }
            });

            tvTakeVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    startActivity(TakeVideoActivity.class, new Bun().putInt(C.WHITCH, C.WHITCH_DEFAUT).ok());
//                    startActivity(FFmpegRecorderActivity.class);

                    startRecordActivity();
                }
            });
            popupWindowHelper = new PopupWindowHelper(popView);
        }

        popupWindowHelper.showAsDropDown(imgAdd);
    }

    /**
     * 销毁PopView
     */
    @Override
    public void dismissPopView() {
        if (popupWindowHelper != null) {
            popupWindowHelper.dismiss();
            popupWindowHelper = null;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK
                && (requestCode == ChooserType.REQUEST_CAPTURE_VIDEO || requestCode == ChooserType.REQUEST_PICK_VIDEO)) {
            if (videoChooserManager == null) {
                reinitializeVideoChooser();
            }
            videoChooserManager.submit(requestCode, data);
        } else {
            dismissDialog();
        }
    }

    // Should be called if for some reason the VideoChooserManager is null (Due
    // to destroying of activity for low memory situations)
    private void reinitializeVideoChooser() {
        videoChooserManager = new VideoChooserManager(this, chooserType, true);
        videoChooserManager.setVideoChooserListener(this);
//        videoChooserManager.reinitialize(filePath);
    }


    public void pickVideo(View view) {

        chooserType = ChooserType.REQUEST_PICK_VIDEO;
        videoChooserManager = new VideoChooserManager(this,
                ChooserType.REQUEST_PICK_VIDEO);
        videoChooserManager.setVideoChooserListener(this);
        try {
            videoChooserManager.choose();
            showDialog("提示", "正在解析视频...");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * When the processing is complete, you will receive this callback with
     * {@link ChosenVideo}
     *
     * @param video
     */
    @Override
    public void onVideoChosen(final ChosenVideo video) {
        dismissDialog();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (video != null) {
                    video.getVideoFilePath();
                    video.getThumbnailPath();
                    startActivity(PublishVideoActivity.class, new Bun().putString("file_name", video.getVideoFilePath()).putInt("uploadType", C.UPLOAD_TYPE_LOCAL).ok());
                }
            }
        });

    }

    /**
     * Handle any error conditions if at all, when you receieve this callback
     *
     * @param reason
     */
    @Override
    public void onError(String reason) {

    }


    private void startRecordActivity() {
        //美颜参数:1-100.这里不设指定为80,这个值只在第一次设置，之后在录制界面滑动美颜参数之后系统会记住上一次滑动的状态
        beautySkinProgress = 80;

        /**
         * 压缩参数，可以自由调节
         */
        MovieExportOptions movie_options = new MovieExportOptions.Builder()
                .setVideoProfile("high")
                .setVideoBitrate(Contant.DEFAULT_BITRATE)
                .setVideoPreset(Contant.DEFAULT_VIDEO_Preset).setVideoRateCRF(Contant.DEFAULT_VIDEO_RATE_CRF)
                .setOutputVideoLevel(Contant.DEFAULT_VIDEO_LEVEL)
                .setOutputVideoTune(Contant.DEFAULT_VIDEO_TUNE)
                .configureMuxer(Contant.DEFAULT_VIDEO_MOV_FLAGS_KEY, Contant.DEFAULT_VIDEO_MOV_FLAGS_VALUE)
                .build();

        /**
         * 界面参数
         */
        VideoSessionCreateInfo create_info = new VideoSessionCreateInfo.Builder()
                .setOutputDurationLimit(Contant.DEFAULT_DURATION_MAX_LIMIT_TWO)
                .setOutputDurationMin(Contant.DEFAULT_DURATION_LIMIT_MIN_TWO)
                .setMovieExportOptions(movie_options)
                .setWaterMarkPath(Contant.WATER_MARK_PATH)
                .setWaterMarkPosition(1)
                .setBeautyProgress(beautySkinProgress)
                .setBeautySkinOn(false)
                .setCameraFacing(
                        Camera.CameraInfo.CAMERA_FACING_BACK)
                .setVideoSize(480, 480)
                .setCaptureHeight(getResources().getDimension(R.dimen.qupai_recorder_capture_height_size))
                .setBeautySkinViewOn(false)
                .setFlashLightOn(true)
                .setTimelineTimeIndicator(true)
                .build();

        _CreateInfo.setSessionCreateInfo(create_info);
        _CreateInfo.setNextIntent(null);
        _CreateInfo.setOutputThumbnailSize(360, 640);//输出图片宽高
        videoPath = FileUtils.newOutgoingFilePath(getActivity());
        _CreateInfo.setOutputVideoPath(videoPath);//输出视频路径
        _CreateInfo.setOutputThumbnailPath(videoPath + ".png");//输出图片路径

        QupaiServiceImpl qupaiService = new QupaiServiceImpl.Builder()
                .setEditorCreateInfo(_CreateInfo).build();
        qupaiService.showRecordPage(getActivity(), QUPAI_RECORD_REQUEST);
    }
}
