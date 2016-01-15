package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.MegaGame;
import com.hewuzhe.presenter.MegaGameDetailPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.TimeUtil;
import com.hewuzhe.view.base.SetView;

import butterknife.Bind;
import cn.xm.weidongjian.popuphelper.PopupWindowHelper;

public class MegaGameDetailActivity extends ToolBarActivity<MegaGameDetailPresenter> implements SetView<MegaGame> {


    @Bind(R.id.tv_name)
    TextView _TvName;
    @Bind(R.id.tv_address)
    TextView _TvAddress;
    @Bind(R.id.tv_desc)
    TextView _TvDesc;
    @Bind(R.id.tv_time_start)
    TextView _TvTimeStart;
    @Bind(R.id.tv_apply_end)
    TextView _TvApplyEnd;
    @Bind(R.id.tv_time_end)
    TextView _TvTimeEnd;
    @Bind(R.id.tv_apply_end_two)
    TextView _TvApplyEndTwo;
    @Bind(R.id.tv_require)
    TextView _TvRequire;
    @Bind(R.id.btn_my_video)
    Button _BtnMyVideo;
    @Bind(R.id.btn_others)
    Button _BtnOthers;
    @Bind(R.id.img)
    ImageView _Img;
    @Bind(R.id.lay_address)
    LinearLayout _LayAddress;
    @Bind(R.id.tv_line)
    TextView _TvLine;
    private int id;
    private TextView action_1;
    private TextView action_2;


    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "大赛详情";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_mega_game_detail;
    }


    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        id = getIntentData().getInt("id");
        presenter.getDetail(id);

    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {


    }

    /**
     * 绑定Presenter
     */
    @Override
    public MegaGameDetailPresenter createPresenter() {
        return new MegaGameDetailPresenter();
    }

    @Override
    public void setData(MegaGame megaGame) {

        if (TimeUtil.timeComparedNow(megaGame.MatchTimeStart)) {
            //比赛未开始
            megaGame.status = MegaGame.STATUS_READY;
            _BtnMyVideo.setText("我要报名");
            _BtnMyVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //报名

                }
            });
            _BtnOthers.setText("查看已报名选手");
            _BtnOthers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(MegaGameVideosTwoActivity.class, new Bun().putInt("id", id).ok());
                }
            });
            megaGame.status = MegaGame.STATUS_ING;

            imgAction.setVisibility(View.VISIBLE);
            imgAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPop();
                }
            });

            _TvLine.setVisibility(View.GONE);
            _LayAddress.setVisibility(View.VISIBLE);
        }

        if (!TimeUtil.timeComparedNow(megaGame.MatchTimeStart) && TimeUtil.timeComparedNow(megaGame.MatchTimeEnd)) {
            //比赛未结束,正在进行中

            _BtnMyVideo.setText("我的视频");
            _BtnMyVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(MegaVideoDetailActivity.class, new Bun().putInt("id", getIntentData().getInt("id")).putInt("teamid", -1).putInt("userid", new SessionUtil(getContext()).getUserId()).ok());
                }
            });

            _BtnOthers.setText("已报名选手详情");
            _BtnOthers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(MegaGameVideosTwoActivity.class, new Bun().putInt("id", id).ok());
                }
            });
            megaGame.status = MegaGame.STATUS_ING;

        }

        if (!TimeUtil.timeComparedNow(megaGame.MatchTimeEnd)) {
            //比赛已经结束

            megaGame.status = MegaGame.STATUS_FINISHED;
            _BtnMyVideo.setText("活动结束");
            _BtnMyVideo.setEnabled(false);
            _BtnOthers.setText("投票结果");
            _BtnOthers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(MegaGameVideosActivity.class, new Bun().putInt("id", id).ok());
                }
            });
        }

        _TvName.setText(megaGame.Name);
        _TvDesc.setText(megaGame.Introduction);
        _TvAddress.setText(megaGame.MatchAddress);
        _TvApplyEnd.setText(megaGame.EnrollTimeEnd);
        _TvApplyEndTwo.setText(megaGame.EnrollTimeEnd);
        _TvTimeEnd.setText(TimeUtil.timeFormatTwo(megaGame.MatchTimeEnd));
        _TvTimeStart.setText(TimeUtil.timeFormatTwo(megaGame.MatchTimeStart));
        Glide.with(getContext())
                .load(C.BASE_URL + megaGame.MatchImage)
                .centerCrop()
                .crossFade()
                .into(_Img);
    }

    private void showPop() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popview, null);
        final PopupWindowHelper popupWindowHelper = new PopupWindowHelper(view);

        action_1 = (TextView) view.findViewById(R.id.tv_local_video);
        action_2 = (TextView) view.findViewById(R.id.tv_take_video);
        action_1.setText("修改作品");
        action_2.setText("取消作品");
        action_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PublishConditionActivity.class);
                popupWindowHelper.dismiss();
            }
        });

        action_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TakeVideoActivity.class, new Bun().putInt(C.WHITCH, C.WHITCH_ONE).ok());
                popupWindowHelper.dismiss();
            }
        });

        popupWindowHelper.showAsDropDown(imgAction);
    }
}
