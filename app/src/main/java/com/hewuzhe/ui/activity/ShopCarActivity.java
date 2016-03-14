package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hewuzhe.R;
import com.hewuzhe.model.OrderContent;
import com.hewuzhe.model.ShopCar;
import com.hewuzhe.model.TeamAnnounce;
import com.hewuzhe.presenter.ShopCarPresenter;
import com.hewuzhe.ui.adapter.ShopCarAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.view.ShopCarView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import materialdialogs.DialogAction;
import materialdialogs.GravityEnum;
import materialdialogs.MaterialDialog;

/**
 * Created by zycom on 2016/2/2.
 */
public class ShopCarActivity extends RecycleViewActivity<ShopCarPresenter, ShopCarAdapter, ShopCar> implements ShopCarView {

    @Bind(R.id.tv_action)
    TextView tv_action;

    @Bind(R.id.tv_total_price)
    TextView tv_total_price;

    @Bind(R.id.tv_submit_btn)
    TextView tv_submit_btn;

    @Bind(R.id.shopcar_select)
    ImageView shopcar_select;

    @Bind(R.id.tv_postage)
    TextView tv_postage;

    public boolean isSelectAll;
    public String idList = "";
    public String Postage = "";
    public String state = "编辑";

    public ArrayList<ShopCar> data = new ArrayList<ShopCar>();

    @Override
    protected String provideTitle() {
        return "购物车";
    }

    @Override
    protected void action() {
        if (tv_action.getText().equals("编辑")) {
            tv_action.setText("完成");
            state = "完成";
            for (int i = 0; i < data.size(); i++) {
                data.get(i).is_show = true;
            }
            adapter.notifyDataSetChanged();

        } else {
            tv_action.setText("编辑");
            state = "编辑";
            for (int i = 0; i < data.size(); i++) {
                data.get(i).is_show = false;
            }

            for (int i = 0; i < data.size(); i++) {
                presenter.updateProductNum(data.get(i).Id, data.get(i).Number);
            }
            adapter.notifyDataSetChanged();
        }
    }

    protected void actionCheck() {
        if (tv_action.getText().equals("完成")) {
            state = "完成";
            for (int i = 0; i < data.size(); i++) {
                data.get(i).is_show = true;
            }
            adapter.notifyDataSetChanged();

        } else {
            state = "编辑";
            for (int i = 0; i < data.size(); i++) {
                data.get(i).is_show = false;
            }

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_shop_car_coin;
    }

    @Override
    public void initListeners() {
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tv_action.setText("编辑");
        presenter.getData(1, 1000);

    }

    @Override
    public ShopCarPresenter createPresenter() {
        return new ShopCarPresenter();
    }

    @Override
    public ShopCar getData() {
        ShopCar shopCar = new ShopCar();
        return shopCar;
    }

    @Override
    public void bindData(ArrayList<ShopCar> data) {
        this.data = data;
        bd(data);
        actionCheck();
    }

    @Override
    protected ShopCarAdapter provideAdapter() {
        return new ShopCarAdapter(getContext(), presenter);
    }

    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    public String GetPost() {
        idList = "";
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).select_state)
                idList += data.get(i).Id + "&";
        }
        return idList;
    }

    @Override
    public void onItemClick(View view, int pos, ShopCar shopCar) {
        if (state.equals("编辑")) {
            DecimalFormat df = new DecimalFormat("######0.00");
            if (shopCar.select_state) {
                shopCar.select_state = false;
                double price = 0.0;
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).select_state)
                        price += data.get(i).AllPrice;
                }
                isSelectAll = true;
//            shopcar_select.setImageResource(R.mipmap.icon_select_click);
                if (GetPost().equals(""))
                    tv_postage.setText("邮费：（无）");
                else
                    presenter.GetPostage(GetPost());
                tv_total_price.setText("总金额：￥" + df.format(price));
                adapter.notifyDataSetChanged();
            } else {
                shopCar.select_state = true;
                double price = 0.0;
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).select_state)
                        price += data.get(i).AllPrice;
                }
                isSelectAll = true;
                if (GetPost().equals(""))
                    tv_postage.setText("邮费：(无)");
                else
                    presenter.GetPostage(GetPost());
//            shopcar_select.setImageResource(R.mipmap.icon_select_click);
                tv_total_price.setText("总金额：￥" + df.format(price));
                adapter.notifyDataSetChanged();

            }
        }
//        Toast.makeText(this,shopCar.ProductColorName+"/"+shopCar.ProductSizeName,Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.getData(page, count);

        actionCheck();


    }

    @Override
    public void updateItem(boolean b, int pos) {
        adapter.notifyItemChanged(pos);
    }


    @OnClick(R.id.shopcar_select)
    public void allSelect() {
        if (state.equals("编辑")) {
            DecimalFormat df = new DecimalFormat("########0.00");
            if (isSelectAll) {
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).select_state = false;
                }
                isSelectAll = false;
                shopcar_select.setImageResource(R.mipmap.icon_select_normal);
                tv_total_price.setText("总金额：￥" + 0.00);
                tv_postage.setText("邮费：（无）");
                adapter.notifyDataSetChanged();
            } else {
                double price = 0.0;
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).select_state = true;
                    price += data.get(i).AllPrice;
                }
                isSelectAll = true;
                shopcar_select.setImageResource(R.mipmap.icon_select_click);
                presenter.GetPostage(GetPost());
                tv_total_price.setText("总金额：￥" + df.format(price));
                adapter.notifyDataSetChanged();
            }
        }

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

        tv_postage.setText("邮费：" + Postage);

    }

    @OnClick(R.id.tv_submit_btn)
    public void submitBtn() {
        boolean isShowMsg=false;
        String message_head="您购物车里的：";
        String message_context="";
        String message_foot="商品已被修改，请删除后再结算。";

        for (int i = 0; i < data.size(); i++) {

            if (data.get(i).select_state&&data.get(i).IsDelete==1) {
                message_context+="\""+data.get(i).ProductName+"\"";
                isShowMsg=true;
            }

        }
        if(isShowMsg){
            MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                    .title("购物车提示")
                    .titleGravity(GravityEnum.CENTER)
                    .titleColor(Color.WHITE)
                    .contentColor(Color.WHITE)
                    .positiveColor(C.COLOR_YELLOW)
                    .negativeColor(C.COLOR_YELLOW)
                    .content(message_head+message_context+message_foot)
                    .backgroundColor(C.COLOR_BG)
                    .positiveText("确定")
                    .backgroundColor(C.COLOR_BG)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

            return;

        }


        ArrayList<OrderContent> temp = new ArrayList<OrderContent>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).select_state) {
                OrderContent sc = new OrderContent();
                sc.setId(data.get(i).Id + "");
                sc.setMiddleImagePath(data.get(i).MiddleImagePath);
                sc.setNumber(data.get(i).Number + "");
                sc.setProductPriceTotalPrice(data.get(i).ProductPriceTotalPrice + "");
                sc.setProductSizeName(data.get(i).ProductSizeName);
                sc.setProductColorName(data.get(i).ProductColorName);
                sc.setProductName(data.get(i).ProductName);
                temp.add(sc);
//                idList+=data.get(i).Id + "&";
            }

        }


//        if(idList.equals(""))
//            return;
//        presenter.GetPostage(idList);
        if (this.Postage.equals(""))
            return;

        startActivity(new Intent(this, OrderConfirmFirstActivity.class).putExtra("state", "2").putExtra("list", temp).putExtra("LiveryPrice", Postage));
        finish();
    }

}