package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hewuzhe.R;
import com.hewuzhe.model.Dojo;
import com.hewuzhe.model.OtherImage;
import com.hewuzhe.model.Pic;
import com.hewuzhe.presenter.DojoDetailPresenter;
import com.hewuzhe.ui.adapter.common.OtherImgsAdapter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.inter.OnItemClickListener;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.DojoDetailView;

import java.util.ArrayList;

import butterknife.Bind;

public class DojoDetailActivity extends ToolBarActivity<DojoDetailPresenter> implements DojoDetailView {


    @Bind(R.id.img)
    ImageView _Img;
    @Bind(R.id.tv_name)
    TextView _TvName;
    @Bind(R.id.img_collect)
    ImageView _ImgCollect;
    @Bind(R.id.lay_collect)
    LinearLayout _LayCollect;
    @Bind(R.id.img_praise)
    ImageView _ImgPraise;
    @Bind(R.id.lay_praise)
    LinearLayout _LayPraise;
    @Bind(R.id.tv_address)
    TextView _TvAddress;
    @Bind(R.id.tv_call)
    ImageView _TvCall;
    @Bind(R.id.img_call)
    TextView _ImgCall;
    @Bind(R.id.tv_desc)
    TextView _TvDesc;
    @Bind(R.id.tv_comment_count)
    TextView _TvCommentCount;
    @Bind(R.id.recycler_view)
    RecyclerView _RecyclerView;
    private Integer id;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "推荐武馆详情";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_dojo_detail;
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
        id = getIntentData().getInt("id");
        presenter.getDetail();
        presenter.getOthers();
    }

    /**
     * 绑定Presenter
     */
    @Override
    public DojoDetailPresenter createPresenter() {
        return new DojoDetailPresenter();
    }

    @Override
    public Integer getData() {
        return id;
    }

    @Override
    public void setData(final Dojo dojo) {
        Glide.with(getContext())
                .load(C.BASE_URL + dojo.ImagePath)
                .fitCenter()
                .crossFade()
                .into(_Img);

        _TvName.setText(dojo.Title);
        _ImgCall.setText(dojo.TelePhone);

        _TvAddress.setText(dojo.Address);
        _TvDesc.setText(dojo.Content);
        _TvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dojo.TelePhone)));
            }
        });
        _ImgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dojo.TelePhone)));
            }
        });

        _TvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(BasicMapActivity.class, new Bun().putString("lat", dojo.Lat).putString("lng", dojo.Lng).putString("address", dojo.Address).putString("name", dojo.Title).putString("title", "武馆位置").ok());
            }
        });

    }

    @Override
    public void setOthers(ArrayList<OtherImage> data) {
        if (data != null && data.size() > 0) {
            _TvCommentCount.setText("共" + data.size() + "条");
            _RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

            OtherImgsAdapter otherImgsAdapter = new OtherImgsAdapter(getContext());
            _RecyclerView.setAdapter(otherImgsAdapter);
            otherImgsAdapter.addDatas(data);

            final ArrayList<Pic> pics = new ArrayList<>();
            for (OtherImage otherImage : data) {
                Pic pic = new Pic();
                pic.ImagePath = otherImage.ImagePath;
                pics.add(pic);
            }

            otherImgsAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int pos, Object item) {
                    startActivity(PicsActivity.class, new Bun().putInt("pos", pos).putString("pics", new Gson().toJson(pics)).ok());
                }
            });

        } else {
            _TvCommentCount.setText("共" + 0 + "条");

        }

    }
}
