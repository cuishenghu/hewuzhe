package com.hewuzhe.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.hewuzhe.R;
import com.hewuzhe.banner.CircleFlowIndicator;
import com.hewuzhe.banner.ImagePagerAdapter;
import com.hewuzhe.banner.ViewFlow;
import com.hewuzhe.model.Bannar;
import com.hewuzhe.model.New;
import com.hewuzhe.model.SaiShiPic;
import com.hewuzhe.presenter.MatchFragmentPresenter;
import com.hewuzhe.ui.activity.CoorperationActivity;
import com.hewuzhe.ui.activity.FederalConditionActivity;
import com.hewuzhe.ui.activity.FederalConditionDetailActivity;
import com.hewuzhe.ui.activity.MainActivity;
import com.hewuzhe.ui.activity.MegaGameActivity;
import com.hewuzhe.ui.activity.StoryActivity;
import com.hewuzhe.ui.adapter.GridItemDecoration;
import com.hewuzhe.ui.adapter.SaishiAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewFragment;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.http.EntityHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.MatchFragmentView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class MatchFragment extends SwipeRecycleViewFragment<MatchFragmentPresenter, SaishiAdapter, New> implements MatchFragmentView {
    protected Toolbar toolBar;
    protected ActionBar actionBar;
    private ImageView imgBack;
    private TextView tvTitle;
    private AppBarLayout appBar;
    private boolean mIsHidden = false;
    private RelativeLayout llAction;
    private LinearLayout ll_bisai;
    private LinearLayout ll_zixun;
    private LinearLayout ll_huati;
    private LinearLayout ll_chuangye;
    private View headerView;
    private New _item;
    private RecyclerView re_zixun;
    private String title = "资讯详情";
    private GridItemDecoration decoration;
    private GridLayoutManager gridLayoutManager;
    private int spacingInPixels;

    ViewFlow mViewFlow;
    CircleFlowIndicator mFlowIndicator;
    FrameLayout framelayout;

    List<Bannar> bannar = new ArrayList<Bannar>();
    List<New> saiShiPics = new ArrayList<New>();

    private int mCurrPos;
    private ViewFlipper notice_vf;
    ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> linkUrlArray = new ArrayList<String>();
    ArrayList<String> titleList = new ArrayList<String>();

    /**
     * 点击事件监听
     */
    @Override
    public void initListeners() {
        /**
         * 比賽
         */
        ll_bisai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MegaGameActivity.class));
            }
        });
        /**
         * 资讯
         */
        ll_zixun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StoryActivity.class));
            }
        });
        /**
         * 话题
         */
        ll_huati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FederalConditionActivity.class));
            }
        });
        /**
         * 创业
         */
        ll_chuangye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //购物车
                startActivity(new Intent(getActivity(), CoorperationActivity.class));
            }
        });
    }

    /**
     * 初始化一些事情
     *
     * @param v
     */
    @Override
    protected void initThings(View v) {
        super.initThings(v);
        initToolBar(v);
        initHeader();
        presenter.getData(page, count);
    }

    protected void initToolBar(View rootView) {
        toolBar = (Toolbar) rootView.findViewById(R.id.toolbar_match);
        ((MainActivity) getActivity()).setSupportActionBar(toolBar);
        imgBack = (ImageView) rootView.findViewById(R.id.img_back);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        re_zixun = (RecyclerView) rootView.findViewById(R.id.recycler_view);
//        llAction = (RelativeLayout) rootView.findViewById(R.id.ll_search);
        appBar = (AppBarLayout) rootView.findViewById(R.id.app_bar_layout);
        tvTitle.setText("赛事");
        imgBack.setVisibility(View.GONE);
    }

    private void initHeader() {
        mViewFlow = (ViewFlow) headerView.findViewById(R.id.viewflow);
        mFlowIndicator = (CircleFlowIndicator) headerView.findViewById(R.id.viewflowindic);
        framelayout = (FrameLayout) headerView.findViewById(R.id.framelayout);

        ll_bisai = (LinearLayout) headerView.findViewById(R.id.ll_bisai);
        ll_zixun = (LinearLayout) headerView.findViewById(R.id.ll_zixun);
        ll_huati = (LinearLayout) headerView.findViewById(R.id.ll_huati);
        ll_chuangye = (LinearLayout) headerView.findViewById(R.id.ll_chuangye);
//        re_zixun = (RecyclerView) headerView.findViewById(R.id.recycler_view);

        spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycle_view_space);
        re_zixun.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        getBannarList();
        ViewGroup.LayoutParams para;
        para = framelayout.getLayoutParams();
        para.height = StringUtil.getScreenWidth(this.getActivity()) / 2;
        framelayout.setLayoutParams(para);
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getChildPosition(view) != 0)
                outRect.top = space;
            outRect.contains(space, space, space, space);
        }
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_match;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public MatchFragmentPresenter createPresenter() {
        return new MatchFragmentPresenter();
    }

    @Override
    public New getData() {
        New news = new New();
        return news;
    }

    @Override
    public void bindData(ArrayList<New> data) {
        bd(data);
    }

    @Override
    protected SaishiAdapter provideAdapter() {
        headerView = getActivity().getLayoutInflater().inflate(R.layout.fragment_match_top, null);

        return new SaishiAdapter(getActivity(), presenter, headerView);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {

        return new GridLayoutManager(getContext(), 2);
    }

    /**
     * 详情
     *
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, New item) {
//        startActivity(VideoDetail2Activity.class, new Bun().putInt("Id", item.Id).ok());
        _item = item;
        startActivity(FederalConditionDetailActivity.class, new Bun().putString("MessageCame", title)
                .putInt("id", _item.Id).putString("visitNum", item.VisitNum + "").putBoolean("IsFavorite", item.IsFavorite).ok());
    }


    private void moveNext() {
        setView(this.mCurrPos, this.mCurrPos + 1);
        this.notice_vf.setInAnimation(getActivity(), R.anim.in_bottomtop);
        this.notice_vf.setOutAnimation(getActivity(), R.anim.out_bottomtop);
        this.notice_vf.showNext();
    }

    private void setView(int curr, int next) {

        View noticeView = getActivity().getLayoutInflater().inflate(R.layout.notice_item, null);
        TextView notice_tv = (TextView) noticeView.findViewById(R.id.notice_tv);
        if ((curr < next) && (next > (titleList.size() - 1))) {
            next = 0;
        } else if ((curr > next) && (next < 0)) {
            next = titleList.size() - 1;
        }
        notice_tv.setText(titleList.get(next));
        notice_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
            }
        });
        if (notice_vf.getChildCount() > 1) {
            notice_vf.removeViewAt(0);
        }
        notice_vf.addView(noticeView, notice_vf.getChildCount());
        mCurrPos = next;

    }

    private void initBanner(ArrayList<String> imageUrlList) {
        mViewFlow.setAdapter(new ImagePagerAdapter(getActivity(), imageUrlList, linkUrlArray, titleList, saiShiPics, saiShiPics.size()).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
    }

    /**
     * 查询资讯轮播图
     */
    private void getBannarList() {
        presenter.SelectZixunPicList(9);
    }

    @Override
    public void getZixunPicList(ArrayList<New> picList) {
        saiShiPics = picList;
        for (int i = 0; i < picList.size(); i++) {
            imageUrlList.add(C.BASE_URL + picList.get(i).MatchImage);
            linkUrlArray.add("");
            titleList.add(picList.get(i).Name);
            initBanner(imageUrlList);

        }
    }

}
