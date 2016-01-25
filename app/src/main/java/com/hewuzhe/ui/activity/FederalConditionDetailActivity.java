package com.hewuzhe.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Article;
import com.hewuzhe.model.Comment;
import com.hewuzhe.presenter.ArticlePresenter;
import com.hewuzhe.ui.adapter.ArticleCommentAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.AsyncImageLoader;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.ArticleView;

import java.io.File;
import java.util.ArrayList;

public class FederalConditionDetailActivity extends RecycleViewActivity<ArticlePresenter, ArticleCommentAdapter, Comment> implements ArticleView {


    private int id;
    private View header;

    private TextView tvName;
    private TextView tvFroms;
    private TextView tvContent;
    private ImageView imgPraise;
    private TextView tvPraiseCount;
    private TextView tvPageViews;
    private TextView tvCommentCount;
    private TextView tvCommentCountOne;
    private ImageView imgAvatar2;
    private Button btnPublish;
    private EditText edtComment;
    private String _content = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 0:
                    Spanned content = Html.fromHtml(_content, new MyImageGetter(), null);
                    tvContent.setText(content);
                    break;
            }


        }
    };
    private Article _Article;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_federai_condition_detail;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        id = getIntentData().getInt("id");
        initHeader();
        presenter.getArticleDetail(id);
        presenter.getData(page, count);
    }

    private void initHeader() {

        tvName = (TextView) header.findViewById(R.id.tv_name);
        tvFroms = (TextView) header.findViewById(R.id.tv_froms);
        tvContent = (TextView) header.findViewById(R.id.tv_content);
        imgPraise = (ImageView) header.findViewById(R.id.img_praise);
        tvPraiseCount = (TextView) header.findViewById(R.id.tv_praise_count);
        tvPageViews = (TextView) header.findViewById(R.id.tv_page_views);
        tvCommentCount = (TextView) header.findViewById(R.id.tv_comment_count);
        tvCommentCountOne = (TextView) header.findViewById(R.id.tv_comment_count_one);
        imgAvatar2 = (ImageView) header.findViewById(R.id.img_avatar_2);
        btnPublish = (Button) header.findViewById(R.id.btn_publish);
        edtComment = (EditText) header.findViewById(R.id.edt_comment);


    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected ArticleCommentAdapter provideAdapter() {
        header = getLayoutInflater().inflate(R.layout.header_article_detail, null);
        return new ArticleCommentAdapter(getContext(), presenter, header, C.WHITCH_ONE);
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.publisComment(id, edtComment.getText().toString().trim(), view);
            }
        });

        imgPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.collectAndFavorate(id, 1, view);
            }
        });

    }

    /**
     * 绑定Presenter
     */
    @Override
    public ArticlePresenter createPresenter() {
        return new ArticlePresenter();
    }


    @Override
    protected String provideTitle() {
        return getIntentData().getString("title");
    }

    @Override
    public void setData(Article article) {
        this._Article = article;
        tvTitle.setText(StringUtil.isEmpty(getIntentData().getString("title")) ? article.Title : getIntentData().getString("title"));
        tvName.setText(article.Title);
        _content = article.Content;
        tvContent.setText(Html.fromHtml(_content, new MyImageGetter(), null));
//      tvContent.loadUrl("http://www.baidu.com");

        tvFroms.setText("来自：" + article.Category + "  " + article.PublishTime);
        tvPraiseCount.setText(article.LikeNum + "");
        tvPageViews.setText("阅读 " + article.FavoriteNum + "");
        tvCommentCount.setText("评论 " + article.CommentNum + "");
        tvCommentCountOne.setText("共" + article.CommentNum + "条");

        Glide.with(getContext())
                .load(C.BASE_URL + new SessionUtil(getContext()).getUser().PhotoPath)
                .placeholder(R.mipmap.img_avatar)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(getContext()))
                .into(imgAvatar2);

//        if (article.PicList != null && article.PicList.size() > 0) {
//            for (Pic pic : article.PicList) {
//                ImageView img = (ImageView) getLayoutInflater().inflate(R.layout.component_article_detiail_img, null);
//                Glide.with(getContext())
//                        .load(C.BASE_URL + pic.ImagePath)
//                        .fitCenter()
//                        .crossFade()
//                        .into(img);
//                layPics.addView(img);
//            }
//        }
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
    public void bindData(ArrayList<Comment> data) {
        bd(data);
    }


    /**
     * 点赞收藏等
     *
     * @param b
     * @param flag
     * @param position
     */
    @Override
    public void collectAndOther(boolean b, int flag, int position) {

    }

    /**
     * 显示评论框
     *
     * @param id
     * @param position
     * @param v
     */
    @Override
    public void showCommentInput(int id, int position, View v) {

    }

    @Override
    public void commentSuccess(int position, Comment comment) {
        edtComment.setText("");
        presenter.getData(page, count);
    }

    /**
     * 回复评论
     *
     * @param id
     * @param nicName
     * @param position
     * @param v
     */
    @Override
    public void showReplyInput(int id, String nicName, int position, View v) {

    }

    @Override
    public void replySuccess(int position, Comment comment) {

    }

    @Override
    public Integer getData() {
        return id;
    }

    @Override
    public void collectAndOther(boolean b, int flag) {
        if (b) {
            _Article.LikeNum++;
            tvPraiseCount.setText(_Article.LikeNum + "");
            imgPraise.setImageResource(R.mipmap.icon_collect_focus);
            imgPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.collectAndFavorateCance(id, 1, view);
                }
            });

        } else {
            _Article.LikeNum--;
            tvPraiseCount.setText(_Article.LikeNum + "");
            imgPraise.setImageResource(R.mipmap.icon_collect);

            imgPraise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.collectAndFavorate(id, 1, view);
                }
            });

        }
    }


    public class MyImageGetter implements Html.ImageGetter {
        @Override
        public Drawable getDrawable(final String source) {

            final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + C.CACHE_DIR_PATH;
            String fileName = source.substring(source.lastIndexOf("/"));

            File file = new File(path + fileName);

            if (!file.exists()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AsyncImageLoader.saveUrlAsFile(C.BASE_URL + source, path);
                        //重新设置
                        handler.sendEmptyMessage(0);
                    }
                }).start();

            } else {

                Drawable bitmapDrawable = Drawable.createFromPath(path + fileName);
                bitmapDrawable.setBounds(0, 0, 300,
                        300);
                return bitmapDrawable;
            }


//                        bitmapDrawable.setBounds(0, 0, bitmapDrawable.getIntrinsicWidth(),
//                                bitmapDrawable.getIntrinsicHeight());

            return null;
        }
    }


    @Override
    public void isWuYou(Boolean data, int userid) {
        if (data) {
            startActivity(FriendProfileActivity.class, new Bun().putInt("id", userid).ok());
        } else {
            startActivity(StrangerProfileSettingsActivity.class, new Bun().putInt("id", userid).ok());
        }

    }

}
