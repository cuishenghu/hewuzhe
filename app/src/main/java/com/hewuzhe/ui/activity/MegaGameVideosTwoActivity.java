package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.MegaGameVideo;
import com.hewuzhe.model.MegaGameVideosRequest;
import com.hewuzhe.presenter.MegaGameVideoPresenter;
import com.hewuzhe.ui.adapter.MegaGameVideosTwoAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.MegaGameVideosView;

import java.util.ArrayList;

import butterknife.Bind;

public class MegaGameVideosTwoActivity extends SwipeRecycleViewActivity<MegaGameVideoPresenter, MegaGameVideosTwoAdapter, MegaGameVideo> implements MegaGameVideosView {

    @Bind(R.id.edt_search_content)
    EditText _EdtSearchContent;
    @Bind(R.id.img_search)
    ImageView _ImgSearch;

    private MegaGameVideosRequest megaGameVideosRequest;
    private ArrayList<MegaGameVideo> _MegaGameVideos = new ArrayList<>();
    private ArrayList<MegaGameVideo> _NewMegaGameVideos = new ArrayList<>();

    /**
     * @return 提供Adapter
     */
    @Override
    protected MegaGameVideosTwoAdapter provideAdapter() {
        return new MegaGameVideosTwoAdapter(getContext());
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return MegaGameActivity.PAGE == 0 ? "参赛人员" : "参赛战队";
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

        if (MegaGameActivity.PAGE == 0) {
            _EdtSearchContent.setHint("搜索用户昵称、编号");

        } else {
            _EdtSearchContent.setHint("搜索战队昵称、编号");

        }

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

        for (MegaGameVideo megaGameVideo : _MegaGameVideos) {

            if (megaGameVideo.NicName.contains(keyWord)) {
                _NewMegaGameVideos.add(megaGameVideo);
            }
            String matchCode = megaGameVideo.MatchCode + "";
            if (matchCode.contains(keyWord)) {
                _NewMegaGameVideos.add(megaGameVideo);
            }
        }

        adapter.addDatas(_NewMegaGameVideos);
    }


    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
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
        _MegaGameVideos = data;
        bd(data);
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, MegaGameVideo item) {
        startActivity(FriendProfileActivity.class, new Bun().putInt("id", item.UserId).ok());
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
