package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.SearchOnlineVideosPresenter;
import com.hewuzhe.ui.adapter.GridItemDecoration;
import com.hewuzhe.ui.adapter.Videos2Adapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.view.SearchVideosView;

import java.util.ArrayList;

import butterknife.Bind;

public class SearchOnlineVideosActivity extends SwipeRecycleViewActivity<SearchOnlineVideosPresenter, Videos2Adapter, Video> implements SearchVideosView {

    public static int CAT_ID = 0;
    @Bind(R.id.edt_search_content)
    EditText _EdtSearchContent;
    @Bind(R.id.tv_tip)
    TextView _TvTip;
    @Bind(R.id.lay_no_data)
    LinearLayout _LayNoData;

    /**
     * @return 提供Adapter
     */
    @Override
    protected Videos2Adapter provideAdapter() {
        return new Videos2Adapter(getContext());
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == adapter.getItemCount() - 1) {
                    return 2;
                }
                return 1;
            }
        });

        layoutManager = gridLayoutManager;

        return gridLayoutManager;
    }

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return getIntentData().getString("title");
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_mega_game_videos;
    }


    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        _EdtSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    refresh(true);
                    page = 1;
                    hideSoftMethod(_EdtSearchContent);
                    presenter.getData(page, count);
                }
                return false;
            }
        });

        findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh(true);
                page = 1;
                hideSoftMethod(_EdtSearchContent);
                presenter.getData(page, count);
            }
        });

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        CAT_ID = getIntentData().getInt("catId", 0);
        _EdtSearchContent.setHint("搜索视频名称");
        showNoData(true, "嘛都没有~~");
        recyclerView.addItemDecoration(new GridItemDecoration(10, 2));
    }

    /**
     * 绑定Presenter
     */
    @Override
    public SearchOnlineVideosPresenter createPresenter() {
        return new SearchOnlineVideosPresenter();
    }

    @Override
    public void bindData(ArrayList<Video> data) {
        bd(data);
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Video item) {
        if (item.UserId != 0) {
        }
    }

    @Override
    public String getData() {
        String keyword = _EdtSearchContent.getText().toString().trim();
        return keyword;
    }

    @Override
    public void showNoData(boolean isShow, String tip) {
        if (isShow) {
            _LayNoData.setVisibility(View.VISIBLE);
            _TvTip.setText(tip);
        } else {
            _LayNoData.setVisibility(View.GONE);

        }
    }
}
