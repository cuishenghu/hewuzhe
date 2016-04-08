package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Product;
import com.hewuzhe.presenter.ProductListPresenter;
import com.hewuzhe.ui.adapter.ProductListAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.ui.base.RecycleViewForListActivity;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.ProductListView;

import java.util.ArrayList;
import java.util.logging.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zycom on 2016/1/22.
 */

public class ProductListActivity extends RecycleViewForListActivity<ProductListPresenter, ProductListAdapter, Product> implements ProductListView{


    @Bind(R.id.salenum_title)   RelativeLayout salenum_title;       //销量
    @Bind(R.id.price_title)     RelativeLayout price_title;         //价格
    @Bind(R.id.comment_title)   RelativeLayout comment_title;       //好评
    @Bind(R.id.new_title)       RelativeLayout new_title;           //最新

    @Bind(R.id.salenum_top)     ImageView salenum_top;      //销量上
    @Bind(R.id.salenum_bottom)  ImageView salenum_bottom;   //销量下
    @Bind(R.id.price_top)       ImageView price_top;
    @Bind(R.id.price_bottom)    ImageView price_bottom;
    @Bind(R.id.comment_top)     ImageView comment_top;
    @Bind(R.id.comment_bottom)  ImageView comment_bottom;
    @Bind(R.id.new_top)         ImageView new_top;
    @Bind(R.id.new_bottom)      ImageView new_bottom;
    @Bind(R.id.tv_title) TextView tvTitle;

    @Bind(R.id.product_list_search) ImageView product_list_search;
    @Bind(R.id.title_daohang) LinearLayout title_daohang;
    @Bind(R.id.ll_sousuo) LinearLayout ll_sousuo;
    @Bind(R.id.edt_search_content) EditText edt_search_content;   //搜索框



    @Override
    protected String provideTitle() {
            return "商品列表";
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_product_list;
    }
    @Override
    public void initListeners() {
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        Intent intent =getIntent();
        if(intent.getStringExtra("recommend")!=null&&intent.getStringExtra("recommend").equals("1")){
            recommend = Integer.parseInt(intent.getStringExtra("recommend"));
            presenter.getData(page, count,"",0,0,0,0,0,0,1);
            tvTitle.setText("推荐商品");
//            title_daohang.setVisibility(View.GONE);
//            ll_sousuo.setVisibility(View.GONE);
        }
        else if(intent.getIntExtra("classId",0)!=0){
            classifi = intent.getIntExtra("classId",0);
            presenter.getData(page, count,"",intent.getIntExtra("classId",0),0,0,0,0,0,0);}
        else
            presenter.getData(page, count);

    }

    @Override
    public ProductListPresenter createPresenter() {
        return new ProductListPresenter();
    }

    @Override
    public Product getData() {
        Product product = new Product();
        return product;
    }

    @Override
    public void bindData(ArrayList<Product> data) {
        bd(data);
    }

    @Override
    protected ProductListAdapter provideAdapter() {
        return new ProductListAdapter(getContext(), presenter);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new GridLayoutManager(getContext(),2);
    }

    @Override
    public void onItemClick(View view, int pos, Product product) {
        startActivity(ProductInfoActivity.class, new Bun().putInt("proid", product.Id).ok());
//        finish();
    }

    @Override
    public void updateItem(boolean b, int pos) {
        adapter.notifyItemChanged(pos);
    }

    @OnClick({R.id.salenum_title,R.id.price_title,R.id.comment_title,R.id.new_title})
    public void onSearchMethod(View v){
        init();
        switch (v.getId()){
            case R.id.salenum_title:
                id_f=0;
//                id_o=0;
                id_s=0;
                id_t=0;
                if(this.id_o==1) {
                    salenum_bottom.setImageResource(R.mipmap.prolistbottom_click);
                    salenum_top.setImageResource(R.mipmap.prolisttop);
                    id_o=2;
                }else if(this.id_o==2){
                    salenum_bottom.setImageResource(R.mipmap.prolistbottom);
                    salenum_top.setImageResource(R.mipmap.prolisttop_click);
                    id_o=1;
                }else{
                    salenum_bottom.setImageResource(R.mipmap.prolistbottom_click);
                    salenum_top.setImageResource(R.mipmap.prolisttop);
                    id_o=2;
                }
                if(classifi!=0)
                    presenter.getData(page, count, searchText, classifi, 0, id_o, 0, 0, 0, recommend);
//                    presenter.getData(page, count,searchText,classifi,id_t,id_o,id_s,id_f,0,recommend);
                else
                    presenter.getData(page, count, searchText, 0, 0, id_o, 0, 0, 0, recommend);
//                    presenter.getData(page, count,searchText,0,id_t,id_o,id_s,id_f,0,recommend);
                break;
            case R.id.price_title:
                id_f=0;
                id_o=0;
                id_s=0;
//                id_t=0;
                if(this.id_t==1) {
                    price_bottom.setImageResource(R.mipmap.prolistbottom_click);
                    price_top.setImageResource(R.mipmap.prolisttop);
                    id_t=2;
                }else if(this.id_t==2){
                    price_bottom.setImageResource(R.mipmap.prolistbottom);
                    price_top.setImageResource(R.mipmap.prolisttop_click);
                    id_t=1;
                }else{
                    price_bottom.setImageResource(R.mipmap.prolistbottom_click);
                    price_top.setImageResource(R.mipmap.prolisttop);
                    id_t=2;
                }
                if(classifi!=0)
                    presenter.getData(page, count,searchText,classifi,id_t,0,0,0,0,recommend);
//                    presenter.getData(page, count,searchText,classifi,id_t,id_o,id_s,id_f,0,recommend);
                else
                    presenter.getData(page, count,searchText,0,id_t,0,0,0,0,recommend);
//                    presenter.getData(page, count,searchText,0,id_t,id_o,id_s,id_f,0,recommend);
                break;
            case R.id.comment_title:
                id_f=0;
                id_o=0;
//                id_s=0;
                id_t=0;
                if(this.id_s==1) {
                    comment_bottom.setImageResource(R.mipmap.prolistbottom_click);
                    comment_top.setImageResource(R.mipmap.prolisttop);
                    id_s=2;
                }else if(this.id_s==2){
                    comment_bottom.setImageResource(R.mipmap.prolistbottom);
                    comment_top.setImageResource(R.mipmap.prolisttop_click);
                    id_s=1;
                }else {
                    comment_bottom.setImageResource(R.mipmap.prolistbottom_click);
                    comment_top.setImageResource(R.mipmap.prolisttop);
                    id_s=2;
                }
                if(classifi!=0)
                    presenter.getData(page, count,searchText,classifi,0,0,id_s,0,0,recommend);
//                    presenter.getData(page, count,searchText,classifi,id_t,id_o,id_s,id_f,0,recommend);
                else
                    presenter.getData(page, count,searchText,0,0,0,id_s,0,0,recommend);
//                    presenter.getData(page, count,searchText,0,id_t,id_o,id_s,id_f,0,recommend);
                break;
            case R.id.new_title:
//                id_f=0;
                id_o=0;
                id_s=0;
                id_t=0;
                if(this.id_f==1) {
                    new_bottom.setImageResource(R.mipmap.prolistbottom_click);
                    new_top.setImageResource(R.mipmap.prolisttop);
                    id_f=2;
                }else if(this.id_f==2){
                    new_bottom.setImageResource(R.mipmap.prolistbottom);
                    new_top.setImageResource(R.mipmap.prolisttop_click);
                    id_f=1;
                }else{
                    new_bottom.setImageResource(R.mipmap.prolistbottom_click);
                    new_top.setImageResource(R.mipmap.prolisttop);
                    id_f=2;
                }
                if(classifi!=0)
                    presenter.getData(page, count,searchText,classifi,0,0,0,id_f,0,recommend);
//                    presenter.getData(page, count,searchText,classifi,id_t,id_o,id_s,id_f,0,recommend);
                else
                    presenter.getData(page, count,searchText,0,0,0,0,id_f,0,recommend);
//                    presenter.getData(page, count,searchText,0,id_t,id_o,id_s,id_f,0,recommend);
                break;
        }
    }

    @OnClick(R.id.product_list_search)
    public void proSearch(){
        searchText = edt_search_content.getText().toString();
        if(classifi!=0)
            presenter.getData(page, count,searchText,classifi,0,0,0,0,0,recommend);
//            presenter.getData(page, count,searchText,classifi,id_t,id_o,id_s,id_f,0,recommend);
        else
            presenter.getData(page, count,searchText,0,0,0,0,0,0,recommend);
//            presenter.getData(page, count,searchText,0,id_t,id_o,id_s,id_f,0,recommend);
    }

    public void init(){

        page=1;
        salenum_bottom.setImageResource(R.mipmap.prolistbottom);
        salenum_top.setImageResource(R.mipmap.prolisttop);
        price_bottom.setImageResource(R.mipmap.prolistbottom);
        price_top.setImageResource(R.mipmap.prolisttop);
        comment_bottom.setImageResource(R.mipmap.prolistbottom);
        comment_top.setImageResource(R.mipmap.prolisttop);
        new_bottom.setImageResource(R.mipmap.prolistbottom);
        new_top.setImageResource(R.mipmap.prolisttop);
    }

}
