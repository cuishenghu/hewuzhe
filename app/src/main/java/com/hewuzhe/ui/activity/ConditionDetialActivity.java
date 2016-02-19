package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hewuzhe.R;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.FriendCondition;
import com.hewuzhe.model.Pic;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.ConditonDetailPresenter;
import com.hewuzhe.presenter.adapter.ConditionPresenter;
import com.hewuzhe.ui.adapter.ConditionCommentAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TimeUtil;
import com.hewuzhe.view.CondtionDetailView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import materialdialogs.DialogAction;
import materialdialogs.MaterialDialog;

public class ConditionDetialActivity extends SwipeRecycleViewActivity<ConditonDetailPresenter, ConditionCommentAdapter, Comment> implements CondtionDetailView {

    @Bind(R.id.img)
    ImageView _Img;
    @Bind(R.id.tv_username)
    TextView _TvUsername;
    @Bind(R.id.tv_content)
    TextView _TvContent;
    @Bind(R.id.lay_pics)
    LinearLayout _LayPics;
    @Bind(R.id.img_video)
    ImageView _ImgVideo;
    @Bind(R.id.lay_img)
    FrameLayout _LayImg;
    @Bind(R.id.tv_add_time)
    TextView _TvAddTime;
    @Bind(R.id.tv_praise)
    TextView _TvPraise;
    @Bind(R.id.img_praise)
    ImageView _ImgPraise;
    @Bind(R.id.img_collect)
    ImageView _ImgCollect;
    @Bind(R.id.tv_comment_count)
    TextView _TvCommentCount;
    @Bind(R.id.lay_status)
    LinearLayout _LayStatus;
    @Bind(R.id.tv_show_all)
    TextView _TvShowAll;
    @Bind(R.id.lay_comment)
    LinearLayout _LayComment;
    @Bind(R.id.edt_comment)
    EditText _EdtComment;
    @Bind(R.id.btn_publish)
    Button _BtnPublish;
    @Bind(R.id.lay_comment_two)
    LinearLayout _LayCommentTwo;
    private FriendCondition _condition;
    private int pageCount;
    private int countMine = 3;
    private SpannableString spannableString;

    @Override
    public Integer getData() {
        return getIntentData().getInt("id");
    }

    @Override
    public void bindData(ArrayList<Comment> data) {
//        bd(data);

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
//        showDialog();
//        presenter.getData(page, count);
        FriendCondition friendCondition = getIntentData().getParcelable("item");
        friendCondition.isShowingAll = false;
        setData(friendCondition);

    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected ConditionCommentAdapter provideAdapter() {
        return new ConditionCommentAdapter(getContext(), presenter);
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
        return "";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_condition_detial;
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
    public ConditonDetailPresenter createPresenter() {
        return new ConditonDetailPresenter();
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Comment item) {

    }

    @Override
    public void setData(final FriendCondition condition) {
        this._condition = condition;
        _TvContent.setText(condition.Content);
        _TvAddTime.setText(TimeUtil.timeAgo(condition.PublishTime));
        _TvPraise.setText(condition.LikeNum + "");

        if (getIntentData().getInt("whitch") == C.WHITCH_TWO) {
            tvTitle.setText("战队动态详情");

            _TvUsername.setText(getIntentData().getString("_NickName"));
            Glide.with(getContext())
                    .load(C.BASE_URL + getIntentData().getString("_PhotoPath"))
                    .fitCenter()
                    .crossFade()
                    .placeholder(R.mipmap.img_avatar)
                    .transform(new GlideCircleTransform(getContext()))
                    .into(_Img);

        } else {
            tvTitle.setText("个人动态详情");
            _TvUsername.setText(condition.NicName);
            Glide.with(getContext())
                    .load(C.BASE_URL + condition.PhotoPath)
                    .fitCenter()
                    .crossFade()
                    .placeholder(R.mipmap.img_avatar)
                    .transform(new GlideCircleTransform(getContext()))
                    .into(_Img);
        }

//      _Img.setOnClickListener(v->startActivity(FriendProfileActivity.class,new Bun().putInt("id",_condition.)));

        if (condition.IsLike) {
            _ImgPraise.setImageResource(R.mipmap.icon_praise_focus);
            _ImgPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.collectAndOtherCanl(condition.Id, ConditionPresenter.PRAISE, view);
                }
            });

        } else {
            _ImgPraise.setImageResource(R.mipmap.icon_praise);
            _ImgPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.collectAndOther(condition.Id, ConditionPresenter.PRAISE, view);
                }
            });
        }

        _TvCommentCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showCommentInput(condition.Id, view);
            }
        });

        _LayPics.removeAllViews();//先清除之前的views
        _LayPics.setVisibility(View.GONE);
        //
        if (condition.PicList.size() > 0) {
            _LayPics.setVisibility(View.VISIBLE);
            int size = condition.PicList.size();
            pageCount = size / 3;

            if (size % 3 != 0) {
                pageCount++;
            }

//            ViewGroup.LayoutParams params = _LayPics.getLayoutParams();
//            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            params.height = 200 * pageCount;
//            _LayPics.setLayoutParams(params);

            for (int i = 0; i < pageCount; i++) {
                addImg(condition, i);
            }
        }


        if (StringUtil.isEmpty(condition.VideoPath)) {
            _LayImg.setVisibility(View.GONE);

        } else {
            _LayImg.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(C.BASE_URL + condition.ImagePath)
                    .centerCrop()
                    .crossFade()
                    .into(_ImgVideo);
            _LayImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        Intent intent = new Intent(getContext(), BasicVideoActivity.class);
                        intent.putExtra("data", new Bun().putString("videoPath", condition.VideoPath).ok());
                        getContext().startActivity(intent);
                    }
                }
            });
        }


        _TvShowAll.setText(condition.isShowingAll ? "显示全部评论" : "关闭全部评论");
        _TvShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                condition.isShowingAll = condition.isShowingAll ? false : true;
                _TvShowAll.setText(condition.isShowingAll ? "显示全部评论" : "关闭全部评论");
                setComment(_condition);
            }
        });


        _BtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.publisComment(_condition.Id, _EdtComment.getText().toString().trim(), view);
            }
        });

        setComment(condition);
    }

    /**
     * @param condition 设置comment
     */
    private void setComment(FriendCondition condition) {
        _LayComment.removeViews(1, _LayComment.getChildCount() - 1);
        _LayComment.setVisibility(View.GONE);

        if (condition.ComList.size() > 0) {
            _LayComment.setVisibility(View.VISIBLE);
            if (condition.ComList.size() > 3) {
                _TvShowAll.setVisibility(View.VISIBLE);
                if (!condition.isShowingAll) {
                    //显示全部
                    for (int i = 0; i < condition.ComList.size(); i++) {
                        final Comment comment = condition.ComList.get(i);
                        View view = getLayoutInflater().inflate(R.layout.item_coment_child, null);
                        _LayComment.addView(view);

                        TextView tvConent = (TextView) view.findViewById(R.id.tv_conent);

                        String content = "";

                        if (comment.ParentId == 0) {
                            content = comment.NicName + "：" + comment.Content;
                            spannableString = new SpannableString(content);
                            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ef9c00")), 0, comment.NicName.length(), 0);
                        } else {
                            content = comment.NicName + "回复" + comment.CommentedNicName + "：" + comment.Content;
                            spannableString = new SpannableString(content);
                            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ef9c00")), 0, comment.NicName.length(), 0);
                            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ef9c00")), comment.NicName.length() + 2, comment.NicName.length() + 2 + comment.CommentedNicName.length(), 0);

                        }
                        tvConent.setText(spannableString);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View view) {
//                                presenter.showReplyInput(comment.Id, comment.NicName, comment.Id, view);
                                if (comment.CommenterId == new SessionUtil(getContext()).getUserId()) {
                                    showDefautInfoDialog("温馨提示", "确定要删除评论吗？", new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            presenter.deleteComment(comment.Id, view);
                                        }
                                    });
                                } else {
                                    presenter.showReplyInput(comment.Id, comment.NicName, comment.Id, view);
                                }
                            }
                        });
                    }

                } else {
                    for (int i = 0; i < 3; i++) {
                        final Comment comment = condition.ComList.get(i);
                        View view = getLayoutInflater().inflate(R.layout.item_coment_child, null);
                        _LayComment.addView(view);

                        TextView tvConent = (TextView) view.findViewById(R.id.tv_conent);

                        String content = "";

                        if (comment.ParentId == 0) {
                            content = comment.NicName + "：" + comment.Content;
                            spannableString = new SpannableString(content);
                            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ef9c00")), 0, comment.NicName.length(), 0);
                        } else {
                            content = comment.NicName + "回复" + comment.CommentedNicName + "：" + comment.Content;
                            spannableString = new SpannableString(content);
                            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ef9c00")), 0, comment.NicName.length(), 0);
                            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ef9c00")), comment.NicName.length() + 2, comment.NicName.length() + 2 + comment.CommentedNicName.length(), 0);

                        }
                        tvConent.setText(spannableString);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View view) {
//                                presenter.showReplyInput(comment.Id, comment.NicName, comment.Id, view);

                                if (comment.CommenterId == new SessionUtil(getContext()).getUserId()) {
                                    showDefautInfoDialog("温馨提示", "确定要删除评论吗？", new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            presenter.deleteComment(comment.Id, view);
                                        }
                                    });
                                } else {
                                    presenter.showReplyInput(comment.Id, comment.NicName, comment.Id, view);
                                }

                            }
                        });
                    }

                }
            } else {
                _TvShowAll.setVisibility(View.GONE);
                for (int i = 0; i < condition.ComList.size(); i++) {
                    final Comment comment = condition.ComList.get(i);
                    View view = getLayoutInflater().inflate(R.layout.item_coment_child, null);
                    _LayComment.addView(view);

                    TextView tvConent = (TextView) view.findViewById(R.id.tv_conent);

                    String content = "";

                    if (comment.ParentId == 0) {
                        content = comment.NicName + "：" + comment.Content;
                        spannableString = new SpannableString(content);
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ef9c00")), 0, comment.NicName.length(), 0);
                    } else {
                        content = comment.NicName + "回复" + comment.CommentedNicName + "：" + comment.Content;
                        spannableString = new SpannableString(content);
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ef9c00")), 0, comment.NicName.length(), 0);
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ef9c00")), comment.NicName.length() + 2, comment.NicName.length() + 2 + comment.CommentedNicName.length(), 0);

                    }
                    tvConent.setText(spannableString);

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {
                            if (comment.CommenterId == new SessionUtil(getContext()).getUserId()) {
                                showDefautInfoDialog("温馨提示", "确定要删除评论吗？", new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        presenter.deleteComment(comment.Id, view);
                                    }
                                });
                            } else {
                                presenter.showReplyInput(comment.Id, comment.NicName, comment.Id, view);
                            }
                        }
                    });


                }
            }

        }
    }

    /**
     * 添加图片
     *
     * @param condition
     * @param page
     */
    private void addImg(final FriendCondition condition, final int page) {
        LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.component_comment_lay, null);
        _LayPics.addView(linearLayout);
        /**
         * 要添加的图片
         * */
        List<Pic> subList = condition.PicList.subList(page * countMine, page * countMine + countMine >= condition.PicList.size() ? condition.PicList.size() : page * countMine + countMine);
        for (int i = 0; i < subList.size(); i++) {
            final Pic pic = subList.get(i);
            LinearLayout imgWraper = (LinearLayout) getLayoutInflater().inflate(R.layout.component_comment_pic, null);
            linearLayout.addView(imgWraper);

            Glide.with(getContext())
                    .load(C.BASE_URL + pic.ImagePath)
                    .fitCenter()
                    .crossFade()
                    .into((ImageView) imgWraper.findViewById(R.id.img));

            final int finalI = i;
            imgWraper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /**
                     * 到图片集页
                     * */
                    ArrayList<Pic> newPics = new ArrayList<Pic>();
                    for (Pic pic1 : condition.PicList) {
                        Pic pic2 = new Pic();
                        pic2.PictureUrl = pic1.ImagePath;
                        pic2.ImagePath = "";
                        newPics.add(pic2);
                    }

                    Intent intent = new Intent(getContext(), PicsActivity.class);
                    intent.putExtra("data", new Bun().putInt("pos", finalI + (page * countMine)).
                            putString("pics", new Gson().toJson(newPics)).ok());
                    getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public void collectAndOther(boolean b, int flag) {
        if (flag == ConditionPresenter.PRAISE) {
            if (b) {
                _condition.LikeNum++;
                _TvPraise.setText(_condition.LikeNum + "");
                _ImgPraise.setImageResource(R.mipmap.icon_praise_focus);

                _ImgPraise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.collectAndOtherCanl(_condition.Id, ConditionPresenter.PRAISE, view);
                    }
                });


            } else {
                _condition.LikeNum--;
                _TvPraise.setText(_condition.LikeNum + "");
                _ImgPraise.setImageResource(R.mipmap.icon_praise);

                _ImgPraise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.collectAndOther(_condition.Id, ConditionPresenter.PRAISE, view);
                    }
                });


            }
        }

    }


    /**
     * 显示评论框
     *
     * @param id
     * @param view
     */
    @Override
    public void showCommentInput(final int id, View view) {
        _LayCommentTwo.setVisibility(View.VISIBLE);
        _EdtComment.requestFocus();
        _EdtComment.setHint("");
        showSoftInput(_EdtComment);
        _BtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.publisComment(id, _EdtComment.getText().toString().trim(), view);
            }
        });
    }

    /**
     * 评论成功
     */
    @Override
    public void commentSuccess(Comment comment) {
        _EdtComment.setText("");
        User user = new SessionUtil(getContext()).getUser();
        comment.CommentedNicName = _condition.NicName;
        comment.NicName = user.NicName;
        _condition.ComList.add(comment);
//        adapter.notifyItemChanged(position);
        setComment(_condition);

    }

    /**
     * 回复评论
     *
     * @param id
     * @param nicName
     * @param commenterId
     * @param view
     */
    @Override
    public void showReplyInput(final int id, final String nicName, final int commenterId, View view) {
        _LayCommentTwo.setVisibility(View.VISIBLE);
        _EdtComment.requestFocus();
        showSoftInput(_EdtComment);
        _EdtComment.setHint("回复 " + nicName + ":");
        _BtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.publisReply(id, nicName, commenterId, _EdtComment.getText().toString().trim(), view);
            }
        });
    }

    /**
     * 回复成功
     *
     * @param comment
     */
    @Override
    public void replySuccess(Comment comment) {
        _EdtComment.setText("");
        User user = new SessionUtil(getContext()).getUser();
        comment.NicName = user.NicName;
        _condition.ComList.add(comment);
//      adapter.notifyItemChanged(position);
        setComment(_condition);

    }

}
