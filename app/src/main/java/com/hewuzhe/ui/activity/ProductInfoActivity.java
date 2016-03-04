package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hewuzhe.R;
import com.hewuzhe.banner.CircleFlowIndicator;
import com.hewuzhe.banner.ImagePagerAdapter;
import com.hewuzhe.banner.ViewFlow;
import com.hewuzhe.model.OrderContent;
import com.hewuzhe.model.Pic;
import com.hewuzhe.model.Product;
import com.hewuzhe.model.Tel;
import com.hewuzhe.presenter.ProductInfoPresenter;
import com.hewuzhe.ui.adapter.ProductColorAdapter;
import com.hewuzhe.ui.adapter.ProductInfoAdapter;
import com.hewuzhe.ui.adapter.ProductTelAdapter;
import com.hewuzhe.ui.base.SyLinearLayoutManager;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.inter.OnItemClickListener;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.TagGroup;
import com.hewuzhe.view.ProductInfoView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zycom on 2016/1/23.
 */
public class ProductInfoActivity extends ToolBarActivity<ProductInfoPresenter> implements ProductInfoView, OnItemClickListener {

    @Bind(R.id.product_title)
    TextView product_title;         //商品名称
    @Bind(R.id.tv_count)
    TextView tv_count;         //商品名称
    @Bind(R.id.product_price)
    TextView product_price;         //商品价格
    @Bind(R.id.product_liveryPrice)
    TextView product_liveryPrice;   //商品运费
    @Bind(R.id.product_visitNum)
    TextView product_visitNum;      //浏览数
    @Bind(R.id.product_saleNum)
    TextView product_saleNum;       //销量
    @Bind(R.id.product_content)
    TextView product_content;       //商品介绍
    @Bind(R.id.comment_num)
    TextView comment_num;           //回复数量
    @Bind(R.id.product_isfavorite_tit)
    TextView product_isfavorite_tit;       //收藏标题
    @Bind(R.id.product_isfavorite)
    ImageView product_isfavorite;           //收藏图
    @Bind(R.id.product_isfavorite_click)
    LinearLayout product_isfavorite_click;       //收藏click
    @Bind(R.id.product_tel)
    LinearLayout product_tel;       //客服

    //    @Bind(R.id.pro_num)
    TextView pro_num;

    //    @Bind(R.id.shopcar_add)
    ImageView shopcar_add;

    //    @Bind(R.id.shopcar_sub)
    ImageView shopcar_sub;

    LinearLayout pro_isFavo_click;
    LinearLayout pro_tel_click_hide;
    ImageView pro_isFavo_img;
    TextView pro_isFavo_tit;
    Product product;

    public String idList = "";
    public String Postage = "";

    //弹窗
    PopupWindow pop;
    PopupWindow telpop;
    View mview;
    View telmview;
    View selectview;
    boolean isOut, isIn;// 是否弹窗显示
    boolean isFavo; //是否收藏

    @Bind(R.id.viewflow)
    ViewFlow mViewFlow;
    @Bind(R.id.viewflowindic)
    CircleFlowIndicator mFlowIndicator;
    @Bind(R.id.framelayout)
    FrameLayout framelayout;
    @Bind(R.id.show_guige)
    RelativeLayout show_guige;                //规格
    @Bind(R.id.user_comment)
    RelativeLayout user_comment;            //用户评价
    @Bind(R.id.pro_buy_now)
    TextView pro_buy_now;                    //立即购买
    @Bind(R.id.pro_add_shopcar)
    TextView pro_add_shopcar;                //加入购物车

    TextView product_price_true;

    private int mCurrPos;
    private ViewFlipper notice_vf;
    ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> linkUrlArray = new ArrayList<String>();
    ArrayList<String> titleList = new ArrayList<String>();

    public RecyclerView recyclerView;
    public RecyclerView telrecyclerView;
    public RecyclerView recyclerView_color;
    public RecyclerView recyclerView_size;
    public ProductInfoAdapter adapter;
    public ProductTelAdapter teladapter;
    public ImageView product_image;
    public ProductColorAdapter adapter_color;
    public RecyclerView.LayoutManager layoutManager;
    public TextView pro_buy_now_true;
    public TextView pro_add_shopcar_true;

    public int page = 1;
    public int count = 10;

    //tag标签
    private TagGroup mDefaultTagGroup;
    public List<String> array = new ArrayList<String>();

    //tag尺码
    private TagGroup mDefaultTagGroup_size;
    public List<String> array_size = new ArrayList<String>();

    public String tag_color = "";
    public String tag_size = "";
    public int tag_color_num;
    public int tag_size_num;
    public String picurl = "";
    public int price_num;
    public double price;
    public int number = 1;

    @Override
    protected String provideTitle() {
        return "商品详情";
    }

    @Override
    protected void action() {
        startActivity(new Intent(this, ShopCarActivity.class));
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_product_info;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);

        presenter.getData();


    }


    @OnClick(R.id.user_comment)
    public void userComment() {
        startActivity(new Intent(this, ProductCommentActivity.class).putExtra("productId", product.Id));
    }

    private TagGroup.OnTagClickListener mTagClickListener = new TagGroup.OnTagClickListener() {
        @Override
        public void onTagClick(String tag) {
//            Toast.makeText(ProductInfoActivity.this, tag, Toast.LENGTH_SHORT).show();
            tag_color = tag;
            for (int i = 0; i < product.ColorList.size(); i++) {
                if (product.ColorList.get(i).Name.equals(tag_color)) {
                    tag_color_num = product.ColorList.get(i).Id;
                    break;
                }
            }

            for (int i = 0; i < product.PriceList.size(); i++) {
                if (product.PriceList.get(i).ColorId == tag_color_num) {
//                    price_num = product.PriceList.get(i).Id;
                    product_price_true.setText("￥" + product.PriceList.get(i).Price);
                    if (product.PriceList.get(i).ImagePath.trim().equals("")) {
                        Glide.with(ProductInfoActivity.this)
                                .load(C.BASE_URL + product.ImagePath)
                                .centerCrop()
                                .crossFade()
                                .placeholder(R.mipmap.img_bg)
                                .into(product_image);
                        picurl = product.ImagePath;
                    } else {
                        Glide.with(ProductInfoActivity.this)
                                .load(C.BASE_URL + product.PriceList.get(i).ImagePath)
                                .centerCrop()
                                .crossFade()
                                .placeholder(R.mipmap.img_bg)
                                .into(product_image);
//                    price = product.PriceList.get(i).Price;
                        picurl = product.PriceList.get(i).ImagePath;
                    }
                    break;
                }
            }

        }
    };
    private TagGroup.OnTagClickListener mTagClickListener_size = new TagGroup.OnTagClickListener() {
        @Override
        public void onTagClick(String tag) {
//            Toast.makeText(ProductInfoActivity.this, tag, Toast.LENGTH_SHORT).show();
            tag_size = tag;


            for (int i = 0; i < product.SizeList.size(); i++) {
                if (product.SizeList.get(i).Name.equals(tag_size)) {
                    tag_size_num = product.SizeList.get(i).Id;
                    break;
                }
            }

            for (int i = 0; i < product.PriceList.size(); i++) {
                if (product.PriceList.get(i).ColorId == tag_color_num && product.PriceList.get(i).SizeId == tag_size_num) {
//                    price_num = product.PriceList.get(i).Id;
                    product_price_true.setText("￥" + product.PriceList.get(i).Price);
                    if (product.PriceList.get(i).ImagePath.trim().equals("")) {
                        Glide.with(ProductInfoActivity.this)
                                .load(C.BASE_URL + product.ImagePath)
                                .centerCrop()
                                .crossFade()
                                .placeholder(R.mipmap.img_bg)
                                .into(product_image);
                        picurl = product.ImagePath;
                    } else {
                        Glide.with(ProductInfoActivity.this)
                                .load(C.BASE_URL + product.PriceList.get(i).ImagePath)
                                .centerCrop()
                                .crossFade()
                                .placeholder(R.mipmap.img_bg)
                                .into(product_image);
//                    price = product.PriceList.get(i).Price;
                        picurl = product.PriceList.get(i).ImagePath;
                    }
                    break;
                }
            }


        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCount();
    }

    @Override
    public void bindData(Product data) {

        product = data;

        product_title.setText(data.Name);
        product_content.setText(data.Content);
        GetPostageState(data.LiveryPrice);
        product_liveryPrice.setText("邮费：" + Postage);
//        product_liveryPrice.setText(data.LiveryPrice==0?"包邮":"邮费："+data.LiveryPrice);
//        product_liveryPrice.setText("货到付款");
        DecimalFormat df = new DecimalFormat("######0.00");
        product_price.setText("￥" + df.format(data.Price));
        product_price_true.setText("￥" + df.format(data.Price));
        product_saleNum.setText("销量：" + data.SaleNum);
        product_visitNum.setText("浏览量：" + data.VisitNum);
        comment_num.setText("共" + data.CommentNum + "条");
        if (!data.isFavorite) {
            product_isfavorite_tit.setTextColor(this.getResources().getColor(R.color.hwz_font_gray));
            pro_isFavo_tit.setTextColor(this.getResources().getColor(R.color.hwz_font_gray));
            product_isfavorite.setImageResource(R.mipmap.icon_shoucang_click);
            pro_isFavo_img.setImageResource(R.mipmap.icon_shoucang_click);
            isFavo = false;
        } else {
            product_isfavorite_tit.setTextColor(this.getResources().getColor(R.color.hwz_font_yellow));
            pro_isFavo_tit.setTextColor(this.getResources().getColor(R.color.hwz_font_yellow));
            product_isfavorite.setImageResource(R.mipmap.icon_shoucang_normal);
            pro_isFavo_img.setImageResource(R.mipmap.icon_shoucang_normal);
            isFavo = true;
        }


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_comment);
        recyclerView.setLayoutManager(new SyLinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new ProductInfoAdapter(getContext(), data.CommentList));


        for (int i = 0; i < data.ColorList.size(); i++) {
            array.add(data.ColorList.get(i).Name);
        }
        mDefaultTagGroup.setTags(array.toArray(new String[]{}));

        for (int i = 0; i < data.SizeList.size(); i++) {
            array_size.add(data.SizeList.get(i).Name);
        }
        mDefaultTagGroup_size.setTags(array_size.toArray(new String[]{}));

        mDefaultTagGroup.setOnTagClickListener(mTagClickListener);
        mDefaultTagGroup_size.setOnTagClickListener(mTagClickListener_size);
        Glide.with(ProductInfoActivity.this)
                .load(C.BASE_URL + product.ImagePath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_bg)
                .into(product_image);

        //banner轮播图
        for (int i = 0; i < product.PicList.size(); i++) {
            imageUrlList.add(C.BASE_URL + product.PicList.get(i).Path);
            linkUrlArray.add("");
            titleList.add("商品图片");

        }

        ViewGroup.LayoutParams para;
        para = framelayout.getLayoutParams();
        para.height = StringUtil.getScreenWidth(this) / 2;
        framelayout.setLayoutParams(para);

        initBanner(imageUrlList);
        presenter.SelectProductPhone();
    }

    @Override
    public void initListeners() {


        LayoutInflater inflater = LayoutInflater.from(this);
        // 引入窗口配置文件 - 即弹窗的界面
        mview = inflater.inflate(R.layout.menu_view, null);

        pro_buy_now_true = (TextView) mview.findViewById(R.id.pro_buy_now_true);
        pro_add_shopcar_true = (TextView) mview.findViewById(R.id.pro_add_shopcar_true);

        selectview = (View) mview.findViewById(R.id.menu_selectclose);

        product_price_true = (TextView) mview.findViewById(R.id.product_price_true);
        product_image = (ImageView) mview.findViewById(R.id.product_image);
        pro_isFavo_click = (LinearLayout) mview.findViewById(R.id.pro_isFavo_click);
        pro_isFavo_img = (ImageView) mview.findViewById(R.id.pro_isFavo_img);
        pro_isFavo_tit = (TextView) mview.findViewById(R.id.pro_isFavo_tit);
        shopcar_add = (ImageView) mview.findViewById(R.id.shopcar_add);
        shopcar_sub = (ImageView) mview.findViewById(R.id.shopcar_sub);
        pro_num = (TextView) mview.findViewById(R.id.pro_num);

        initData();

        LayoutInflater telinflater = LayoutInflater.from(this);
        // 引入窗口配置文件 - 即弹窗的界面
        telmview = telinflater.inflate(R.layout.pop_tel, null);
        pro_tel_click_hide = (LinearLayout) telmview.findViewById(R.id.click_hide);
        pro_tel_click_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ProductInfoActivity.this, "请重新选择规格", Toast.LENGTH_SHORT).show();
                telpop.dismiss();
            }
        });
        initTelData();

        product_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (picurl.trim().equals(""))
                    addImg(product.ImagePath, 0);
                else
                    addImg(picurl, 0);
            }
        });


        pro_buy_now_true.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        pro_add_shopcar_true.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for (int i = 0; i < product.PriceList.size(); i++) {
                    if (product.PriceList.get(i).ColorId == tag_color_num && product.PriceList.get(i).SizeId == tag_size_num) {
                        price_num = product.PriceList.get(i).Id;
                        product_price_true.setText("￥" + product.PriceList.get(i).Price);
                        price = product.PriceList.get(i).Price;
                        break;
                    }
                }

                if (!tag_color.trim().equals("") && !tag_size.trim().equals("")) {
                    presenter.addInsertBasket(product.Id, number, price_num, price, v);
                    presenter.getCount();
                    pop.dismiss();
                    Toast.makeText(ProductInfoActivity.this, "加入购物车成功！！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProductInfoActivity.this, "请重新选择规格", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });

        pro_buy_now_true.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for (int i = 0; i < product.PriceList.size(); i++) {
                    if (product.PriceList.get(i).ColorId == tag_color_num && product.PriceList.get(i).SizeId == tag_size_num) {
                        price_num = product.PriceList.get(i).Id;
//                        product_price_true.setText("￥" + product.PriceList.get(i).Price);
                        price = product.PriceList.get(i).Price;
                        break;
                    }
                }

                if (!tag_color.trim().equals("") && !tag_size.trim().equals("")) {
//                    presenter.buyNow(product.Id, number, price_num, price, 0, "");
                    ArrayList<OrderContent> temp = new ArrayList<OrderContent>();
                    OrderContent sc = new OrderContent();
                    sc.setId(product.Id + "");
                    sc.setMiddleImagePath(product.ImagePath);
                    sc.setNumber(number + "");
                    sc.setProductPriceId(price_num + "");
                    sc.setProductPriceTotalPrice(price + "");
                    sc.setProductSizeName(tag_size);
                    sc.setProductColorName(tag_color);
                    sc.setProductName(product.Name);
                    temp.add(sc);
//                    idList += product.Id + "&";

//                    if (idList.equals(""))
//                        return;
//
//                    if (Postage.equals(""))
//                        return;
                    startActivity(new Intent(ProductInfoActivity.this, OrderConfirmFirstActivity.class).putExtra("state", "1").putExtra("list", temp).putExtra("LiveryPrice", Postage));
                    finish();
                } else
                    Toast.makeText(ProductInfoActivity.this, "请重新选择规格", Toast.LENGTH_SHORT).show();
            }
        });

        pro_isFavo_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proIsFavoriteClick();

            }
        });

        selectview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();

            }
        });

        mDefaultTagGroup = (TagGroup) mview.findViewById(R.id.tag_group);
        mDefaultTagGroup.setIsAppendMode(true);
        mDefaultTagGroup_size = (TagGroup) mview.findViewById(R.id.tag_group_size);
        mDefaultTagGroup_size.setIsAppendMode(true);

        shopcar_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number++;
                pro_num.setText(number + "");
            }
        });

        shopcar_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number == 1)
                    return;
                number--;
                pro_num.setText(number + "");
            }
        });

    }

    //图片合集
    private void addImg(String src, int pos) {
        ArrayList<Pic> newPics = new ArrayList<Pic>();


        Pic pic = new Pic();
        pic.PictureUrl = src;
        pic.ImagePath = "";
        newPics.add(pic);


        Intent intent = new Intent(this, PicsActivity.class);
        intent.putExtra("data", new Bun().putInt("pos", pos).
                putString("pics", new Gson().toJson(newPics)).ok());
        this.startActivity(intent);
    }

    @Override
    public void GetPostageState(double state) {
        if (state == 0)
            Postage = "包邮";
        else if (state == -1)
            Postage = "货到付款";
        else {
            DecimalFormat df = new DecimalFormat("######0.00");
            Postage = df.format(state).toString();
        }

    }

    @Override
    public void showCount(int count) {
        if (count > 0) {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText(count + "");
        } else {
            tv_count.setVisibility(View.GONE);
        }
    }

    @Override
    public void bindTel(ArrayList<Tel> data) {
        telrecyclerView = (RecyclerView) telmview.findViewById(R.id.recycler_tel);//findViewById(R.id.recycler_view_comment);
        telrecyclerView.setLayoutManager(new SyLinearLayoutManager(this));
        telrecyclerView.setAdapter(teladapter = new ProductTelAdapter(getContext(), data));
    }

    @Override
    public ProductInfoPresenter createPresenter() {
        return new ProductInfoPresenter();
    }

    private void moveNext() {
        setView(this.mCurrPos, this.mCurrPos + 1);
        this.notice_vf.setInAnimation(this, R.anim.in_bottomtop);
        this.notice_vf.setOutAnimation(this, R.anim.out_bottomtop);
        this.notice_vf.showNext();
    }

    private void setView(int curr, int next) {

        View noticeView = getLayoutInflater().inflate(R.layout.notice_item, null);
        TextView notice_tv = (TextView) noticeView.findViewById(R.id.notice_tv);
        if ((curr < next) && (next > (titleList.size() - 1))) {
            next = 0;
        } else if ((curr > next) && (next < 0)) {
            next = titleList.size() - 1;
        }
        notice_tv.setText(titleList.get(next));
        notice_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
            }
        });
        if (notice_vf.getChildCount() > 1) {
            notice_vf.removeViewAt(0);
        }
        notice_vf.addView(noticeView, notice_vf.getChildCount());
        mCurrPos = next;

    }

    private void initBanner(ArrayList<String> imageUrlList) {

        mViewFlow.setAdapter(new ImagePagerAdapter(this, imageUrlList, linkUrlArray, titleList, product.PicList, true).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
    }

    private void initData() {
        mview.setFocusableInTouchMode(true);
//		view.setOnKeyListener(this);
        // PopupWindow实例化
        pop = new PopupWindow(mview, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        /**
         * PopupWindow 设置
         */
        // pop.setFocusable(true); //设置PopupWindow可获得焦点
        // pop.setTouchable(true); //设置PopupWindow可触摸
        // pop.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        // 设置PopupWindow显示和隐藏时的动画
        pop.setAnimationStyle(R.style.MenuAnimationFade);
        /**
         * 改变背景可拉的弹出窗口。后台可以设置为null。 这句话必须有，否则按返回键popwindow不能消失 或者加入这句话
         * ColorDrawable dw = new
         * ColorDrawable(-00000);pop.setBackgroundDrawable(dw);
         */
        pop.setBackgroundDrawable(new BitmapDrawable());

    }


    /**
     * 改变 PopupWindow 的显示和隐藏
     */
    private void changePopupWindowState() {
        if (pop.isShowing()) {
            // 隐藏窗口，如果设置了点击窗口外消失，则不需要此方式隐藏
            pop.dismiss();
        } else {
            // 弹出窗口显示内容视图,默认以锚定视图的左下角为起点，这里为点击按钮
            pop.showAtLocation(show_guige, Gravity.BOTTOM, 0, 60);
//			pop.showAsDropDown(show_guige, 0, 0, Gravity.BOTTOM);
        }
    }

    @OnClick({R.id.pro_buy_now, R.id.pro_add_shopcar, R.id.show_guige})
    public void showPopupWindow() {
        changePopupWindowState();
    }


    @Override
    public Product getData() {
        Product product = new Product();
        product.Id = getIntentData().getInt("proid");
        return product;
    }

    @OnClick(R.id.product_tel)
    public void proTel() {
        changeTelPopupWindowState();
    }

    private void initTelData() {
        telmview.setFocusableInTouchMode(true);
//		view.setOnKeyListener(this);
        // PopupWindow实例化
        telpop = new PopupWindow(telmview, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        /**
         * PopupWindow 设置
         */
        // pop.setFocusable(true); //设置PopupWindow可获得焦点
        // pop.setTouchable(true); //设置PopupWindow可触摸
        // pop.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        // 设置PopupWindow显示和隐藏时的动画
        telpop.setAnimationStyle(R.style.MenuAnimationFade);
        /**
         * 改变背景可拉的弹出窗口。后台可以设置为null。 这句话必须有，否则按返回键popwindow不能消失 或者加入这句话
         * ColorDrawable dw = new
         * ColorDrawable(-00000);pop.setBackgroundDrawable(dw);
         */
        telpop.setBackgroundDrawable(new BitmapDrawable());

    }


    /**
     * 改变 PopupWindow 的显示和隐藏
     */
    private void changeTelPopupWindowState() {
        if (telpop.isShowing()) {
            // 隐藏窗口，如果设置了点击窗口外消失，则不需要此方式隐藏
            telpop.dismiss();
        } else {
            // 弹出窗口显示内容视图,默认以锚定视图的左下角为起点，这里为点击按钮
            telpop.showAtLocation(show_guige, Gravity.BOTTOM, 40, 130);
//			pop.showAsDropDown(show_guige, 0, 0, Gravity.BOTTOM);
        }
    }


    @OnClick(R.id.product_isfavorite_click)
    public void proIsFavoriteClick() {
        presenter.setFavoState(isFavo);
        if (isFavo) {
            product_isfavorite_tit.setTextColor(this.getResources().getColor(R.color.hwz_font_gray));
            pro_isFavo_tit.setTextColor(this.getResources().getColor(R.color.hwz_font_gray));
            product_isfavorite.setImageResource(R.mipmap.icon_shoucang_click);
            pro_isFavo_img.setImageResource(R.mipmap.icon_shoucang_click);
            isFavo = false;
        } else {
            product_isfavorite_tit.setTextColor(this.getResources().getColor(R.color.hwz_font_yellow));
            pro_isFavo_tit.setTextColor(this.getResources().getColor(R.color.hwz_font_yellow));
            product_isfavorite.setImageResource(R.mipmap.icon_shoucang_normal);
            pro_isFavo_img.setImageResource(R.mipmap.icon_shoucang_normal);
            isFavo = true;
        }
    }

    //RecycleViewAdapter开始==================================================================================
    public void doRecyleViewF() {

    }

    @Override
    public void onItemClick(View view, int pos, Object item) {

    }
}
