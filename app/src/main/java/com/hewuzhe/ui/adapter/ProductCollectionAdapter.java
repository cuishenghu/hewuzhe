package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.ArticleCollection;
import com.hewuzhe.model.Product;
import com.hewuzhe.model.ProductCollection;
import com.hewuzhe.presenter.ProductCollectionPresenter;
import com.hewuzhe.ui.activity.ProductInfoActivity;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.Bun;

import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zycom on 2016/2/20.
 */
public class ProductCollectionAdapter extends BaseAdapter<ProductCollectionAdapter.VHolder, ProductCollection, ProductCollectionPresenter> {

    /**
     * 是否显示checkBox
     */
    private boolean isNeedShow = false;
    public Context context;
    public ProductCollectionPresenter presenter;
    private LinkedList<ProductCollection> checkedList = new LinkedList<>();

    public RecyclerView recyclerView;

    /**
     * @param context
     */
    public ProductCollectionAdapter(Context context,ProductCollectionPresenter presenter,RecyclerView recyclerView) {
        super(context);
        this.context = context;
        this.presenter = presenter;
        this.recyclerView = recyclerView;
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.product_list_t;
    }

    /**
     * @param parent
     * @param viewType
     * @param view
     * @return 创建ViewHolder
     */
    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    /**
     * @param holder
     * @param position 绑定数据
     */
    @Override
    public void bindData(VHolder holder, int position) {

        final ProductCollection product = data.get(position);

        Glide.with(context)
                .load(C.BASE_URL + product.MiddleImagePath)
                .centerCrop()
                .crossFade()
                .placeholder(R.mipmap.img_bg)
                .into(holder.icon_title);

        holder.pro_name.setText(product.ProductName);
        holder.pro_price.setText("￥" + product.Price);
        holder.pro_visitnum.setText(product.VisitNum+"人");
        holder.pro_salenum.setText("销售量" + product.SaleNum);

        holder._CbPlan.setChecked(product.isChecked);

        holder._CbPlan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (!ProductCollectionAdapter.this.checkedList.contains(product)) {
                        product.isChecked = true;
                        ProductCollectionAdapter.this.checkedList.add(product);
                    }
                } else {
                    if (ProductCollectionAdapter.this.checkedList.contains(product)) {
                        product.isChecked = false;
                        ProductCollectionAdapter.this.checkedList.remove(product);
                    }
                }
            }
        });

        if (this.isNeedShow) {
            holder._CbPlan.setVisibility(View.VISIBLE);
        } else {
            holder._CbPlan.setVisibility(View.GONE);
        }
//        holder.trash_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.deleteCollection(product.ProductId, recyclerView);
//                removeItem(product);
//                showCheck();
//            }
//        });
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_article.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class VHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.icon_title)
        ImageView icon_title;

        @Nullable
        @Bind(R.id.pro_name)
        TextView pro_name;

        @Nullable
        @Bind(R.id.pro_price)
        TextView pro_price;

        @Nullable
        @Bind(R.id.pro_visitnum)
        TextView pro_visitnum;

        @Nullable
        @Bind(R.id.pro_salenum)
        TextView pro_salenum;

        @Nullable
        @Bind(R.id.trash_button)
        LinearLayout trash_button;

        @Nullable
        @Bind(R.id.cb_plan)
        CheckBox _CbPlan;


        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    /**
     * 设置是否显示checkBox
     */
    public void showCheck(boolean isNeedShow) {
        this.isNeedShow = isNeedShow;

        if (!isNeedShow) {
            for (ProductCollection articleCollection : data) {
                articleCollection.isChecked = false;
            }
        }
        this.notifyDataSetChanged();
    }

    /**
     * 获取当前编辑状态
     *
     * @return
     */
    public boolean getCheckShowStatus() {
        return this.isNeedShow;
    }

    /**
     * 获取选中的Item
     *
     * @return
     */
    public LinkedList<ProductCollection> getCheckedList() {
        return this.checkedList;
    }


}
