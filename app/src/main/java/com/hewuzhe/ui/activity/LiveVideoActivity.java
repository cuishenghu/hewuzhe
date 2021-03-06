package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.external.viewpagerindicator.CirclePageIndicator;
import com.hewuzhe.R;
import com.hewuzhe.model.LiveVideo;
import com.hewuzhe.model.Pic;
import com.hewuzhe.presenter.LiveVideoPresenter;
import com.hewuzhe.ui.adapter.base.Bee_PageAdapter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.AutoScrollViewPager;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TimeUtil;
import com.hewuzhe.view.LiveVideoView;

import java.util.ArrayList;

import butterknife.Bind;

public class LiveVideoActivity extends ToolBarActivity<LiveVideoPresenter> implements LiveVideoView {


    @Bind(R.id.viewpager)
    AutoScrollViewPager _Viewpager;
    @Bind(R.id.indicator)
    CirclePageIndicator _Indicator;
    @Bind(R.id.tv_line)
    TextView _TvLine;
    @Bind(R.id.tv_time_start)
    TextView _TvTimeStart;
    @Bind(R.id.tv_apply_end)
    TextView _TvApplyEnd;
    @Bind(R.id.tv_require)
    TextView _TvRequire;
    @Bind(R.id.tv_name)
    TextView _TvName;
    @Bind(R.id.btn_others)
    Button _BtnOthers;
    private ArrayList<View> bannerListView;
    private Bee_PageAdapter bee_pageAdapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_live_video;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        bannerListView = new ArrayList<View>();
        bee_pageAdapter = new Bee_PageAdapter(bannerListView);
        _Viewpager.setAdapter(bee_pageAdapter);
        _Indicator.setViewPager(_Viewpager);

        presenter.SelectVideoLive(getIntentData().getInt("Id"));


    }

    /**
     * 绑定Presenter
     */
    @Override
    public LiveVideoPresenter createPresenter() {
        return new LiveVideoPresenter();
    }


    @Override
    protected String provideTitle() {
        return getIntentData().getString("title");//"视频直播";
    }


    @Override
    public void setData(final LiveVideo liveVideo) {

        _TvName.setText(liveVideo.Title);
        _TvTimeStart.setText(liveVideo.TimeStart);
        _TvApplyEnd.setText(liveVideo.TimeEnd);
        _TvRequire.setText(liveVideo.Content);

        _TvRequire.setMovementMethod(ScrollingMovementMethod.getInstance());


        bannerListView.clear();

        if (liveVideo.ImageList.size() > 0) {
            for (Pic pic : liveVideo.ImageList) {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getContext())
                        .load(C.BASE_URL + pic.ImagePath)
                        .placeholder(R.mipmap.img_bg_videio)
                        .crossFade()
                        .into(imageView);
                bannerListView.add(imageView);
            }
        }
        _Viewpager.setAdapter(bee_pageAdapter);


        if (TimeUtil.timeComparedNow(liveVideo.TimeStart)) {
            //比赛未开始
            _BtnOthers.setText("直播未开始");
            _BtnOthers.setOnClickListener(null);
        }

        if (!TimeUtil.timeComparedNow(liveVideo.TimeStart) && TimeUtil.timeComparedNow(liveVideo.TimeEnd)) {
            //比赛未结束,正在进行中
            _BtnOthers.setText("正在直播");
            _BtnOthers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(BasicWebActivity.class, new Bun()
                            .putString("title", liveVideo.Title)
                            .putString("url", C.BASE_URL + "zhibo.aspx?id=" + liveVideo.Id).ok());
                }
            });
        }

        if (!TimeUtil.timeComparedNow(liveVideo.TimeEnd)) {
            //比赛已经结束
            if (StringUtil.isEmpty(liveVideo.LiveBack)) {
                _BtnOthers.setText("直播已结束");
                _BtnOthers.setOnClickListener(null);
            } else {
                _BtnOthers.setText("查看回放");
                _BtnOthers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(C.BASE_URL + liveVideo.LiveBack);
                        //调用系统自带的播放器
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        String extStr = liveVideo.LiveBack.substring(liveVideo.LiveBack.lastIndexOf("."));
                        intent.setDataAndType(uri, "video/" + extStr);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public void bindData(ArrayList<LiveVideo> data) {
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void noMore() {

    }

    @Override
    public void hasMore() {

    }

    @Override
    public void loadMore(String tip) {

    }

    @Override
    public void hasMore(String tip) {

    }

    @Override
    public void noMore(String tip) {

    }

    @Override
    public void refresh(boolean refreshing) {

    }
}