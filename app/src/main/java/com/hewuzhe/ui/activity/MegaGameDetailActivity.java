package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.net.Uri;
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
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.MegaGameDetailPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.TimeUtil;
import com.hewuzhe.view.MegaGameDetailView;

import butterknife.Bind;
import cn.xm.weidongjian.popuphelper.PopupWindowHelper;

public class MegaGameDetailActivity extends ToolBarActivity<MegaGameDetailPresenter> implements MegaGameDetailView {


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
    @Bind(R.id.btn_call)
    Button _BtnCall;
    private int id;
    private TextView action_1;
    private TextView action_2;
    private MegaGame _MegaGame;


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
    public void setData(final MegaGame megaGame) {
        _MegaGame = megaGame;
        if (TimeUtil.timeComparedNow(megaGame.MatchTimeStart)) {
            //比赛未开始
            megaGame.status = MegaGame.STATUS_READY;
            if (megaGame.IsJoin) {
                if (MegaGameActivity.PAGE == 0) {
                    _BtnMyVideo.setText("我的视频");
                } else {
                    _BtnMyVideo.setText("战队视频");
                }

                _BtnMyVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        User user = new SessionUtil(getContext()).getUser();
                        startActivity(MegaVideoDetailActivity.class, new Bun().putInt("id", getIntentData().getInt("id")).putInt("teamid", user.TeamId).putInt("userid", user.Id).ok());
                    }
                });

                imgAction.setVisibility(View.VISIBLE);
                imgAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPop();
                    }
                });

            } else {

                imgAction.setVisibility(View.GONE);

                if (MegaGameActivity.PAGE == 0) {

                    _BtnMyVideo.setText("我要报名");

                    _BtnMyVideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //报名
                            startActivity(MegaGameApplyActivity.class, new Bun().putInt("id", id).putBoolean("isJoin", _MegaGame.IsJoin).ok());
                        }
                    });

                } else {
                    _BtnMyVideo.setVisibility(View.GONE);
                    _BtnOthers.setVisibility(View.GONE);
                    _BtnCall.setVisibility(View.VISIBLE);
                    _BtnCall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + megaGame.Phone));
                            startActivity(intent);
                        }
                    });

                }


            }

            if (MegaGameActivity.PAGE == 0) {
                _BtnOthers.setText("查看已报名选手");
            } else {
                _BtnOthers.setText("查看已报名战队");
            }

            _BtnOthers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(MegaGameVideosTwoActivity.class, new Bun().putInt("id", id).ok());
                }
            });

        }

        if (!TimeUtil.timeComparedNow(megaGame.MatchTimeStart) && TimeUtil.timeComparedNow(megaGame.MatchTimeEnd)) {
            //比赛进行中
            megaGame.status = MegaGame.STATUS_ING;
//            if (megaGame.IsJoin) {
            if (MegaGameActivity.PAGE == 0) {
                _BtnMyVideo.setText("我的视频");
            } else {
                _BtnMyVideo.setText("战队视频");
            }

            _BtnMyVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User user = new SessionUtil(getContext()).getUser();
                    startActivity(MegaVideoDetailActivity.class, new Bun().putInt("id", getIntentData().getInt("id")).putInt("teamid", user.TeamId).putInt("userid", user.Id).ok());
                }
            });

            imgAction.setVisibility(View.VISIBLE);
            imgAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPop();
                }
            });

//            } else {
//
//                imgAction.setVisibility(View.GONE);
//
//                if (MegaGameActivity.PAGE == 0) {
//
//                    _BtnMyVideo.setText("我要报名");
//
//                    _BtnMyVideo.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            //报名
//                            startActivity(MegaGameApplyActivity.class, new Bun().putInt("id", id).putBoolean("isJoin", _MegaGame.IsJoin).ok());
//                        }
//                    });
//
//                } else {
//                    _BtnMyVideo.setVisibility(View.GONE);
//                    _BtnOthers.setVisibility(View.GONE);
//                    _BtnCall.setVisibility(View.VISIBLE);
//                    _BtnCall.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(Intent.ACTION_DIAL);
//                            intent.setData(Uri.parse("tel:" + megaGame.Phone));
//                            startActivity(intent);
//                        }
//                    });
//
//                }
//
//
//            }

            if (MegaGameActivity.PAGE == 0) {
                _BtnOthers.setText("查看已报名选手");
            } else {
                _BtnOthers.setText("查看已报名战队");
            }

            _BtnOthers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(MegaGameVideosTwoActivity.class, new Bun().putInt("id", id).ok());
                }
            });

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            refresh();
        }
    }


    private void showPop() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popview, null);
        final PopupWindowHelper popupWindowHelper = new PopupWindowHelper(view);

        action_1 = (TextView) view.findViewById(R.id.tv_local_video);
        action_2 = (TextView) view.findViewById(R.id.tv_take_video);
        action_1.setText("取消报名");
        action_2.setText("修改作品");
        action_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindowHelper.dismiss();
                presenter.CancleJoinMatch(id, action_1);
            }
        });

        action_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindowHelper.dismiss();
                startActivity(MegaGameApplyActivity.class, new Bun().putInt("id", id).putBoolean("isJoin", _MegaGame.IsJoin).ok());
            }
        });

        popupWindowHelper.showAsDropDown(imgAction);
    }

    @Override
    public void refresh() {
        presenter.getDetail(id);
    }

}
