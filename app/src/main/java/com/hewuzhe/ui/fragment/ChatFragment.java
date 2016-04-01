package com.hewuzhe.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import com.hewuzhe.model.ProductSort;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.AddWarriorsActivity;
import com.hewuzhe.ui.activity.AddWarriorsActivity2;
import com.hewuzhe.ui.activity.ContactsActivity;
import com.hewuzhe.ui.activity.ConversationListActivity;
import com.hewuzhe.ui.activity.CoorperationActivity;
import com.hewuzhe.ui.activity.FederalConditionActivity;
import com.hewuzhe.ui.activity.FriendsConditionActivity;
import com.hewuzhe.ui.activity.GroupConditionActivity;
import com.hewuzhe.ui.activity.JoinGroupActivity;
import com.hewuzhe.ui.activity.MainActivity;
import com.hewuzhe.ui.activity.MakeWarriorsActivity;
import com.hewuzhe.ui.activity.MegaGameActivity;
import com.hewuzhe.ui.activity.StoryActivity;
import com.hewuzhe.ui.adapter.EquipmentSortRecommendAdapter2;
import com.hewuzhe.ui.adapter.RecommendDongtaiAdapter;
import com.hewuzhe.ui.adapter.SaishiAdapter;
import com.hewuzhe.ui.base.BaseFragment;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.http.EntityHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by xianguangjin on 15/12/8.
 */
public class ChatFragment extends BaseFragment {
    protected Toolbar toolBar;
    protected ActionBar actionBar;
    private ImageView imgBack;
    private TextView tvTitle;
    private AppBarLayout appBar;
    private boolean mIsHidden = false;
    private RelativeLayout llAction;
    private LinearLayout ll_msg_center;
    private LinearLayout ll_wuyouquan;
    private LinearLayout ll_xingququan;
    private LinearLayout ll_fujinren;
    private LinearLayout ll_wuyou_list;
    @Nullable
    @Bind(R.id.list_good_dongtai)
    RecyclerView mRecyclerView;
    private EquipmentSortRecommendAdapter2 equipmentSortRecommendAdapter2;
    private List<ProductSort> productSorts = new ArrayList<ProductSort>();
    private Handler mHandler;
    private int pageNo = 0;//页码
    private int pageSum = 100;//每页显示分类的条数

    ViewFlow mViewFlow;
    CircleFlowIndicator mFlowIndicator;
    FrameLayout framelayout;

    List<Bannar> bannar = new ArrayList<Bannar>();

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
         * 右上角添加武友
         */
        llAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddWarriorsActivity2.class));
            }
        });
        /**
         * 消息中心
         */
        ll_msg_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ConversationListActivity.class));
            }
        });
        /**
         * 武友圈
         */
        ll_wuyouquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FriendsConditionActivity.class));
            }
        });
        /**
         * 兴趣圈
         */
        ll_xingququan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new SessionUtil(getContext()).getUser().TeamId == 0) {
                    snb("请先加入圈子", ll_xingququan);
                    startActivity(new Intent(getActivity(), JoinGroupActivity.class));
                } else {
                    startActivity(GroupConditionActivity.class, new Bun().putInt("teamid", new SessionUtil(getContext()).getUser().TeamId).ok());
                }
            }
        });
//        /**
//         * 附近人
//         */
//        ll_fujinren.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //购物车
//                startActivity(new Intent(getActivity(), CoorperationActivity.class));
//            }
//        });
        /**
         * 武友列表
         */
        ll_wuyou_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //购物车
                startActivity(new Intent(getActivity(), ContactsActivity.class));
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
        initToolBar(v);
        mHandler = new Handler();
//        mListView.setGroupIndicator(null);
//        mListView.setDividerHeight(0);
//        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        mListView.setPullRefreshEnable(true);
//        mListView.setPullLoadEnable(true);
//        mListView.setXListViewListener(this);
        //2代表The number of columns in the grid 列数
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(manager);

        View topView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_chat_top, null);

        ll_msg_center = (LinearLayout) topView.findViewById(R.id.ll_msg_center);
        ll_wuyouquan = (LinearLayout) topView.findViewById(R.id.ll_wuyouquan);
        ll_xingququan = (LinearLayout) topView.findViewById(R.id.ll_xingququan);
        ll_fujinren = (LinearLayout) topView.findViewById(R.id.ll_near_person);
        ll_wuyou_list = (LinearLayout) topView.findViewById(R.id.ll_friend_center);
        final RecommendDongtaiAdapter recommendDongtaiAdapter = new RecommendDongtaiAdapter(topView, 10);
        mRecyclerView.setAdapter(recommendDongtaiAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return recommendDongtaiAdapter.isHeader(position) ? manager.getSpanCount() : 1;
            }
        });


    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_chat;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }


    protected void initToolBar(View rootView) {
        toolBar = (Toolbar) rootView.findViewById(R.id.toolbar_chat);
        ((MainActivity) getActivity()).setSupportActionBar(toolBar);
        imgBack = (ImageView) rootView.findViewById(R.id.img_back);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        llAction = (RelativeLayout) rootView.findViewById(R.id.ll_add_friend);
        tvTitle.setText("聊天");
//        tvAction.setVisibility(View.VISIBLE);
//        tvAction.setText("搜索");
        imgBack.setVisibility(View.GONE);
        requestData();
    }

    private void requestData() {
        RequestParams params = new RequestParams();
        params.put("startRowIndex", pageNo * pageSum);// startRowIndex 开始行索引
        params.put("maximumRows", pageSum);//  maximumRows 每页条数
        params.put("userId", new SessionUtil(getActivity()).getUserId());//   userId 用户ID
        params.put("productNum", 5);// productNum 每个分类下商品最多显示个数
        HttpUtils.getRecommendProduct(res_getRecommendProduct, params);
    }

    private AsyncHttpResponseHandler res_getRecommendProduct = new EntityHandler<ProductSort>(ProductSort.class) {

        @Override
        public void onReadSuccess(List<ProductSort> list) {
//            list
            if (pageNo == 0) {
                productSorts.clear();
            }

            int size = list.size();
            productSorts.addAll(list);
            //加载商品条数
//            initData();
            equipmentSortRecommendAdapter2.notifyDataSetChanged();
        }
    };


}
