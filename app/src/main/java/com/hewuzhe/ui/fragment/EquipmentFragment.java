package com.hewuzhe.ui.fragment;

import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Equipment;
import com.hewuzhe.model.EquipmentSort;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.MainActivity;
import com.hewuzhe.ui.adapter.EquipmentRecommendAdapter;
import com.hewuzhe.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by xianguangjin on 15/12/8.
 */
public class EquipmentFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private int nowpage = 1;// 当前显示的页面
    protected Toolbar toolBar;
    protected ActionBar actionBar;
    private ImageView imgBack;
    private TextView tvTitle;
    private AppBarLayout appBar;
    private boolean mIsHidden = false;
    private TextView tvAction;
    private User user;
    @Bind(R.id.ll_all_sort)
    LinearLayout ll_all_sort;
    @Bind(R.id.ll_recommend)
    LinearLayout ll_recommend;
    @Bind(R.id.ll_my_order)
    LinearLayout ll_my_order;
    @Bind(R.id.ll_shopping_cart)
    LinearLayout ll_shopping_cart;
    @Bind(R.id.list_recomand_product)
    ExpandableListView mListView;
    private EquipmentRecommendAdapter equipmentRecommendAdapter;
    private List<EquipmentSort> equipments = new ArrayList<EquipmentSort>();
    private int groupCount;
    private int sumtiaoshu = 0;
    private Handler mHandler;

    /**
     * 点击事件监听
     */
    @Override
    public void initListeners() {
//        ll_all_sort.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), OrderAssessActivity.class));
//            }
//        });
//        ll_recommend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), MyScoreActivity.class));
//            }
//        });
//        ll_my_order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), OrderCenterActivity.class));
//            }
//        });
//        ll_shopping_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

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
//        mListView = (XListView) findViewById(R.id.list_recomand_product);
        mListView.setGroupIndicator(null);
        mListView.setDividerHeight(8);
//        requestData();
        initData();
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_equipment_copy;
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
        tvAction = (TextView) rootView.findViewById(R.id.tv_action);
        appBar = (AppBarLayout) rootView.findViewById(R.id.app_bar_layout);
        tvTitle.setText("功夫库");
        tvAction.setVisibility(View.GONE);
        imgBack.setVisibility(View.GONE);

    }

//    private void requestData() {
//        RequestParams params = new RequestParams();
//        params.put("startRowIndex", 0);// startRowIndex 开始行索引
//        params.put("maximumRows", 5);//  maximumRows 每页条数
//        params.put("userId", 2);//   userId 用户ID
//        params.put("productNum", 2);// productNum 每个分类下商品最多显示个数
//        HttpUtils.getRecommendProduct(res_getRecommendProduct, params);
//    }
//
//    private AsyncHttpResponseHandler res_getRecommendProduct = new EntityHandler<EquipmentSort>(EquipmentSort.class) {
//
//        @Override
//        public void onReadSuccess(List<EquipmentSort> list) {
//
//            if (nowpage == 1) {
//                equipments.clear();
//            }
//            //删除空列表项
//            listremove(list);
//
//            int size = list.size();
//            equipments.addAll(list);
//
//            //加载商品条数
//            initData();
//            equipmentRecommendAdapter.notifyDataSetChanged();
//        }
//    };
//
//    public void listremove(List<EquipmentSort> list){
//        for(int i=0;i<list.size();i++){
//            if(list.get(i).getProductList().size()==0) {
//                list.remove(i);
//                listremove(list);
//            }
//        }
//    }

    public void initData() {
//        for(int i=0;i<equipments.size();i++){
//            sumtiaoshu=sumtiaoshu+equipments.get(i).getProductList().size();//商品总条数
//        }
//        Tools.toast(getActivity(),""+sumtiaoshu);
//
//        equipmentRecommendAdapter = new EquipmentRecommendAdapter(getActivity(),equipments,sumtiaoshu);
//        mListView.setAdapter(equipmentRecommendAdapter);
//
//        groupCount=mListView.getCount();
//        for(int i=0;i<groupCount;i++){
//            mListView.expandGroup(i);
//        }
        ///////////////////////////////////////////////////////////////////////////////////////////
        List<Equipment> list1 = new ArrayList<Equipment>();
        list1.add(new Equipment("1子id", "1子name", "1价格", "100", "100"));
        list1.add(new Equipment("2子id", "2子name", "2价格", "100", "100"));
        list1.add(new Equipment("3子id", "3子name", "3价格", "100", "100"));

        EquipmentSort equipmentSort1 = new EquipmentSort("1组id", "1组name", list1);
        equipments.add(equipmentSort1);

        List<Equipment> list2 = new ArrayList<Equipment>();
        list2.add(new Equipment("4子id", "4子name", "4价格", "100", "100"));
        list2.add(new Equipment("5子id", "5子name", "5价格", "100", "100"));

        EquipmentSort groupItem2 = new EquipmentSort("2组id", "2组name", list2);
        equipments.add(groupItem2);

        List<Equipment> list3 = new ArrayList<Equipment>();
        list3.add(new Equipment("6子id", "6子name", "6价格", "100", "100"));
        list3.add(new Equipment("7子id", "7子name", "7价格", "100", "100"));
        list3.add(new Equipment("8子id", "8子name", "8价格", "100", "100"));

        EquipmentSort groupItem3 = new EquipmentSort("3组id", "3组name", list3);
        equipments.add(groupItem3);
        for (int i = 0; i < equipments.size(); i++) {
            sumtiaoshu = sumtiaoshu + equipments.get(i).getProductList().size();//商品总条数
        }
//        Tools.toast(getActivity(), "" + sumtiaoshu);

        equipmentRecommendAdapter = new EquipmentRecommendAdapter(getActivity(), equipments, sumtiaoshu);
        mListView.setAdapter(equipmentRecommendAdapter);

        groupCount = mListView.getCount();
        for (int i = 0; i < groupCount; i++) {
            mListView.expandGroup(i);
        }
    }
//    /**
//     * 下拉刷新
//     */
//    @Override
//    public void onRefresh() {
//        mHandler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                nowpage = 1;
//                requestData();
//                onLoad();
//            }
//        }, 1000);
//    }
//
//    /**
//     * 上拉加载
//     */
//    @Override
//    public void onLoadMore() {
//        mHandler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                nowpage += 1;
//                requestData();
//                onLoad();
//            }
//        }, 1000);
//    }
//
//    /**
//     * 结束加载/刷新
//     */
//    public void onLoad() {
//        mListView.stopRefresh();
//        mListView.stopLoadMore();
//        mListView.setRefreshTime("刚刚");
//    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
