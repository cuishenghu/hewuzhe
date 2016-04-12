package com.hewuzhe.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
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
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.MainActivity;
import com.hewuzhe.ui.activity.OrderCenterActivity2;
import com.hewuzhe.ui.activity.ProductClassifiActivity;
import com.hewuzhe.ui.activity.ProductListActivity;
import com.hewuzhe.ui.activity.ShopCarActivity;
import com.hewuzhe.ui.activity.SignInActivity;
import com.hewuzhe.ui.adapter.EquipmentSortRecommendAdapter2;
import com.hewuzhe.ui.base.BaseFragment;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.http.EntityHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.XListView;
import com.hewuzhe.view.XListView.IXListViewListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by xianguangjin on 15/12/8.
 */
public class EquipmentFragment3 extends BaseFragment implements IXListViewListener {
    protected Toolbar toolBar;
    protected ActionBar actionBar;
    private ImageView imgBack;
    private TextView tvTitle;
    private AppBarLayout appBar;
    private boolean mIsHidden = false;
    private RelativeLayout llAction;
    private User user;
    LinearLayout ll_all_sort;
    LinearLayout ll_recommend;
    LinearLayout ll_my_order;
    LinearLayout ll_shopping_cart;
    @Bind(R.id.list_recomand_product)
    XListView mListView;
    private EquipmentSortRecommendAdapter2 equipmentSortRecommendAdapter2;
    private List<ProductSort> productSorts = new ArrayList<ProductSort>();
    private int groupCount;
    private int sumtiaoshu = 0;
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
         * 搜索
         */
        llAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到搜索界面=========================================================
                startActivity(new Intent(getActivity(), ProductListActivity.class));
            }
        });
        /**
         * 分类
         */
        ll_all_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ProductClassifiActivity.class));



            }
        });
        /**
         * 推荐
         */
        ll_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ProductListActivity.class).putExtra("recommend", "1"));


            }
        });
        /**
         * 我的订单
         */
        ll_my_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new SessionUtil(getContext()).isLogin())
                    startActivity(new Intent(getActivity(), OrderCenterActivity2.class));
                else
                startActivity(SignInActivity.class);

            }
        });
        /**
         * 购物车
         */
        ll_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //购物车
                if(new SessionUtil(getContext()).isLogin())
                    startActivity(new Intent(getActivity(), ShopCarActivity.class));
                else
                startActivity(SignInActivity.class);

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
        mListView.setDividerHeight(0);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this);
        View topView = LayoutInflater.from(getActivity()).inflate(R.layout.equipment_top, null);
        mViewFlow = (ViewFlow) topView.findViewById(R.id.viewflow);
        mFlowIndicator = (CircleFlowIndicator) topView.findViewById(R.id.viewflowindic);
        framelayout = (FrameLayout) topView.findViewById(R.id.framelayout);

        ll_all_sort = (LinearLayout) topView.findViewById(R.id.ll_all_sort);
        ll_recommend = (LinearLayout) topView.findViewById(R.id.ll_recommend);
        ll_my_order = (LinearLayout) topView.findViewById(R.id.ll_my_order);
        ll_shopping_cart = (LinearLayout) topView.findViewById(R.id.ll_shopping_cart);
        mListView.addHeaderView(topView);

        getBannarList();
        ViewGroup.LayoutParams para;
        para = framelayout.getLayoutParams();
        para.height = StringUtil.getScreenWidth(this.getActivity()) / 2;
        framelayout.setLayoutParams(para);


    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_equipment3;
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
        llAction = (RelativeLayout) rootView.findViewById(R.id.ll_search);
        appBar = (AppBarLayout) rootView.findViewById(R.id.app_bar_layout);
        tvTitle.setText("购物");
//        tvAction.setVisibility(View.VISIBLE);
//        tvAction.setText("搜索");
        imgBack.setVisibility(View.GONE);
        initData();
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

            //删除空列表项
            listremove(list);
            int size = list.size();
            productSorts.addAll(list);
            //加载商品条数
//            initData();
            equipmentSortRecommendAdapter2.notifyDataSetChanged();
        }
    };

    public void listremove(List<ProductSort> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProductList().size() == 0) {
                list.remove(i);
                listremove(list);
            }
        }
    }

    public void initData() {//注释掉的是ExpandableListView,具体运行效果加载EquipmengFragment
//        for (int i = 0; i < equipments.size(); i++) {
//            sumtiaoshu = sumtiaoshu + equipments.get(i).getProductList().size();//商品总条数
//        }
//        Tools.toast(getActivity(), "" + sumtiaoshu);
        equipmentSortRecommendAdapter2 = new EquipmentSortRecommendAdapter2(getActivity(), productSorts);
        mListView.setAdapter(equipmentSortRecommendAdapter2);
//        StringUtil.listAutoHeight(mListView);

//        groupCount = mListView.getCount();
//        for (int i = 0; i < groupCount; i++) {
//            mListView.expandGroup(i);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////
//        List<Equipment> list1 = new ArrayList<Equipment>();
//        list1.add(new Equipment("1子id", "1子name", "1价格", "100", "100"));
//        list1.add(new Equipment("2子id", "2子name", "2价格", "100", "100"));
//        list1.add(new Equipment("3子id", "3子name", "3价格", "100", "100"));
//
//        EquipmentSort equipmentSort1 = new EquipmentSort("1组id", "1组name", list1);
//        equipments.add(equipmentSort1);
//
//        List<Equipment> list2 = new ArrayList<Equipment>();
//        list2.add(new Equipment("4子id", "4子name", "4价格", "100", "100"));
//        list2.add(new Equipment("5子id", "5子name", "5价格", "100", "100"));
//
//        EquipmentSort groupItem2 = new EquipmentSort("2组id", "2组name", list2);
//        equipments.add(groupItem2);
//
//        List<Equipment> list3 = new ArrayList<Equipment>();
//        list3.add(new Equipment("6子id", "6子name", "6价格", "100", "100"));
//        list3.add(new Equipment("7子id", "7子name", "7价格", "100", "100"));
//        list3.add(new Equipment("8子id", "8子name", "8价格", "100", "100"));
//
//        EquipmentSort groupItem3 = new EquipmentSort("3组id", "3组name", list3);
//        equipments.add(groupItem3);
//        for (int i = 0; i < equipments.size(); i++) {
//            sumtiaoshu = sumtiaoshu + equipments.get(i).getProductList().size();//商品总条数
//        }
//        Tools.toast(getActivity(), "" + sumtiaoshu);
//
//        equipmentRecommendAdapter = new EquipmentRecommendAdapter(getActivity(), equipments, sumtiaoshu);
//        mListView.setAdapter(equipmentRecommendAdapter);
//
//        groupCount = mListView.getCount();
//        for (int i = 0; i < groupCount; i++) {
//            mListView.expandGroup(i);
//        }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                pageNo = 0;
                requestData();
                onLoad();
            }
        }, 1000);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                pageNo += 1;
                requestData();
                onLoad();
            }
        }, 1000);
    }

    /**
     * 结束加载/刷新
     */
    public void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime("刚刚");
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
        mViewFlow.setAdapter(new ImagePagerAdapter(getActivity(), imageUrlList, linkUrlArray, titleList, bannar).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
    }

    private void getBannarList() {
        RequestParams params = new RequestParams();
        HttpUtils.getBannarList(res_getBannarList, params);
    }

    private AsyncHttpResponseHandler res_getBannarList = new EntityHandler<Bannar>(Bannar.class) {

        @Override
        public void onReadSuccess(List<Bannar> list) {
//            int i = list.size();
            bannar = list;
            //banner轮播图
            for (int i = 0; i < list.size(); i++) {
                imageUrlList.add(C.BASE_URL + list.get(i).getPath());
                linkUrlArray.add("");
                titleList.add(list.get(i).getTitle());

                initBanner(imageUrlList);

            }
        }
    };


}
