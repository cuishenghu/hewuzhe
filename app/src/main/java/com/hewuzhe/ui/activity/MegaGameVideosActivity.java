package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.MegaGameVideo;
import com.hewuzhe.model.MegaGameVideosRequest;
import com.hewuzhe.presenter.MegaGameVideoPresenter;
import com.hewuzhe.ui.adapter.GridItemDecoration;
import com.hewuzhe.ui.adapter.MegaGameVideosAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.MegaGameVideosView;

import java.util.ArrayList;

import butterknife.Bind;

public class MegaGameVideosActivity extends SwipeRecycleViewActivity<MegaGameVideoPresenter, MegaGameVideosAdapter, MegaGameVideo> implements MegaGameVideosView {

    @Bind(R.id.edt_search_content)
    EditText _EdtSearchContent;

    private MegaGameVideosRequest megaGameVideosRequest;

    /**
     * @return 提供Adapter
     */
    @Override
    protected MegaGameVideosAdapter provideAdapter() {
        return new MegaGameVideosAdapter(getContext());
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


    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void action() {
        super.action();
        startActivity(MegaGameDetailActivity.class, new Bun().putInt("id", getIntentData().getInt("id")).ok());

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
                    hideSoftMethod(_EdtSearchContent);
                    refresh(true);
                    page = 1;
                    presenter.getData(page, count);
                }
                return false;
            }
        });



        findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftMethod(_EdtSearchContent);
                refresh(true);
                page = 1;
                presenter.getData(page, count);
            }
        });


    }


    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tvAction.setText("赛事介绍");
        recyclerView.addItemDecoration(new GridItemDecoration(10, 2));
        presenter.getData(page, count);

    }

    /**
     * 绑定Presenter
     */
    @Override
    public MegaGameVideoPresenter createPresenter() {
        return new MegaGameVideoPresenter();
    }

    @Override
    public void bindData(ArrayList<MegaGameVideo> data) {
        bd(data);
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, MegaGameVideo item) {
        startActivity(MegaVideoDetailActivity.class, new Bun().putInt("id", getIntentData().getInt("id")).putInt("teamid", item.TeamId).putInt("userid", item.UserId).ok());
    }

    @Override
    public MegaGameVideosRequest getData() {
        if (megaGameVideosRequest == null) {
            megaGameVideosRequest = new MegaGameVideosRequest();
        }
        megaGameVideosRequest.matchId = getIntentData().getInt("id");
        megaGameVideosRequest.nicname = _EdtSearchContent.getText().toString().trim();

        return megaGameVideosRequest;
    }


}
