package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Address;
import com.hewuzhe.presenter.CitySelectPresenter;
import com.hewuzhe.ui.adapter.RecyclerIndexAdapter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.IndexView;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.CitySelectView;

import java.util.ArrayList;

import butterknife.Bind;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;


public class CitySelectActivity extends ToolBarActivity<CitySelectPresenter> implements BGAOnItemChildClickListener, CitySelectView {

    @Bind(R.id.tv_recyclerindexview_topc)
    TextView _TvRecyclerindexviewTopc;
    @Bind(R.id.tv_recyclerindexview_tip)
    TextView _TvRecyclerindexviewTip;
    @Bind(R.id.indexview)
    IndexView _Indexview;
    @Bind(R.id.recycler_view)
    RecyclerView _RecyclerView;
    @Bind(R.id.edt_search_content)
    EditText _EdtSearchContent;
    @Bind(R.id.tv_city)
    TextView _TvCity;
    @Bind(R.id.img_search)
    ImageView _ImgSearch;
    private RecyclerIndexAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private String _CityName = "";
    private ArrayList<Address> _Address = new ArrayList<>();
    private ArrayList<Address> _NewAddress = new ArrayList<>();

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "城市选择";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_city_select;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        _CityName = getIntentData().getString("cityName");
        _TvCity.setText(_CityName);


        _Indexview.setTipTv(_TvRecyclerindexviewTip);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        _RecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerIndexAdapter(_RecyclerView);
        presenter.getCitys();

    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        mAdapter.setOnItemChildClickListener(this);
        _Indexview.setOnChangedListener(new IndexView.OnChangedListener() {
            @Override
            public void onChanged(String text) {
                int position = mAdapter.getPositionForSection(text.charAt(0));
                if (position != -1) {
                    // position的item滑动到RecyclerView的可见区域，如果已经可见则不会滑动
                    mLayoutManager.scrollToPosition(position);
                }
            }
        });

        _RecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mAdapter.getItemCount() > 0) {
                    _TvRecyclerindexviewTopc.setText(mAdapter.getItem(mLayoutManager.findFirstVisibleItemPosition()).topc);
                }
            }
        });

        _TvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(C.RESULT_TWO);
                finishActivity();
            }
        });

        _EdtSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String keyWord = _EdtSearchContent.getText().toString().trim();
                    search(keyWord);
                }
                return false;
            }
        });


        _ImgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord = _EdtSearchContent.getText().toString().trim();
                search(keyWord);
            }
        });


    }


    private void search(String keyWord) {

        hideSoftMethod(_EdtSearchContent);

        for (Address address : _Address) {

            if (address.Name.contains(keyWord)) {
                _NewAddress.add(address);
            }
        }

        _RecyclerView.setAdapter(mAdapter);
        mAdapter.setDatas(_NewAddress);

    }


    /**
     * 绑定Presenter
     */
    @Override
    public CitySelectPresenter createPresenter() {
        return new CitySelectPresenter();
    }


    @Override
    public void onItemChildClick(ViewGroup viewGroup, View view, int i) {
        Address item = mAdapter.getDatas().get(i);
        setResult(C.RESULT_ONE, new Intent().putExtra("data", new Bun().putString("name", item.Name).putInt("id", item.Id).ok()));
        finishActivity();
    }

    @Override
    public void bindData(ArrayList<Address> addresses) {
        _Address = addresses;
        mAdapter.setDatas(addresses);
        _RecyclerView.setAdapter(mAdapter);
        _TvRecyclerindexviewTopc.setText(mAdapter.getItem(0).topc);
    }

}
