package com.hewuzhe.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

import com.alibaba.fastjson.JSONObject;
import com.hewuzhe.R;
import com.hewuzhe.model.OrderChild;
import com.hewuzhe.model.OrderGroup;
import com.hewuzhe.ui.activity.OrderAssessActivity;
import com.hewuzhe.ui.activity.OrderConfirmFirstActivity;
import com.hewuzhe.ui.http.HttpErrorHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.Tools;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import materialdialogs.AlertDialogWrapper;

/**
 * Created by Administrator on 2016/1/23 0023.
 */
public class OrderCenterAdapter extends BaseExpandableListAdapter {
    private LayoutInflater mLayoutInflater;
    private List<OrderGroup> orders;
    private int sumtiaoshu;//商品条数
    private Activity activity;
    private Context context;
    private int mType;

    public OrderCenterAdapter(Context context, List<OrderGroup> orders, int mType, int sumtiaoshu) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.orders = orders;
        this.sumtiaoshu = sumtiaoshu;
        this.mType = mType;
    }

    @Override
    public int getGroupCount() {
        if (orders == null) {
            return 0;
        }
        return orders.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (orders == null) {
            return null;
        }
        return orders.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final OrderGroup orderGroup = orders.get(groupPosition);
        final OrderGroupViewHolder holder;
        if (convertView == null) {
            holder = new OrderGroupViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_order_center_group, null);
            holder.tv_order_no = (TextView) convertView.findViewById(R.id.tv_order_no);
            convertView.setTag(holder);
        } else {
            holder = (OrderGroupViewHolder) convertView.getTag();
        }
        holder.tv_order_no.setText(orderGroup.getId());
        return convertView;
    }

    //====================================================================================
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //==================================================================================
    @Override
    public int getChildrenCount(int groupPosition) {
        //获取子对象
        final OrderGroup orderGroup = orders.get(groupPosition);
        if (orderGroup == null || orderGroup.getOrderLists() == null || orderGroup.getOrderLists().isEmpty()) {
            return 0;
        }
        return orderGroup.getOrderLists().size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //获取子对象
        final OrderGroup orderGroup = orders.get(groupPosition);
        if (orderGroup == null || orderGroup.getOrderLists() == null || orderGroup.getOrderLists().isEmpty()) {
            return null;
        }
        return orderGroup.getOrderLists().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final OrderGroup orderGroup = orders.get(groupPosition);
        final OrderChild orderChild = orderGroup.getOrderLists().get(childPosition);
        final OrderChildViewHolder holder;
        if (convertView == null) {
            holder = new OrderChildViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_order_center_child, null);
            holder.img_equipment_pic = (ImageView) convertView.findViewById(R.id.img_equip_pic);//装备图片
            holder.tv_equipment_name = (TextView) convertView.findViewById(R.id.tv_equip_name);//装备名称
            holder.tv_equipment_special = (TextView) convertView.findViewById(R.id.tv_equip_special);//装备规格
            holder.tv_equipment_price = (TextView) convertView.findViewById(R.id.tv_equip_price);//装备价格
            holder.tv_equipment_num = (TextView) convertView.findViewById(R.id.tv_equip_num);//订单装备数量
            holder.tv_total_num = (TextView) convertView.findViewById(R.id.tv_order_total_num);//订单装备总数量
            holder.tv_total_price = (TextView) convertView.findViewById(R.id.tv_order_total_price);//订单装备总价格
            holder.tv_left_btn = (TextView) convertView.findViewById(R.id.tv_left_btn);//左边按钮
            holder.tv_right_btn = (TextView) convertView.findViewById(R.id.tv_right_btn);//右边按钮
            convertView.setTag(holder);
        } else {
            holder = (OrderChildViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(
                StringUtil.toString(UrlContants.IMAGE_URL + orderChild.getImagePath(), "http://"),
                holder.img_equipment_pic);
        holder.tv_equipment_name.setText(orderChild.getName());
        holder.tv_equipment_special.setText(orderChild.getSpecial());
        holder.tv_equipment_price.setText("¥" + orderChild.getPrice());
        holder.tv_equipment_num.setText("x" + orderChild.getSaleNum());
        holder.tv_total_num.setText("共计" + orderChild.getSaleNum() + "件商品");
        holder.tv_total_price.setText("合计：¥" + orderChild.getPrice() + "元");
        convertView.findViewById(R.id.ll_bottom).setVisibility(isLastChild ? View.VISIBLE : View.GONE);
        /**
         * 不同页面显示不同的按钮
         */
        if (mType == 1) {
            holder.tv_left_btn.setText("取消订单");
            holder.tv_left_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewDialog(groupPosition);//弹框提示
                }
            });
            holder.tv_right_btn.setText("去付款");
            holder.tv_right_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, OrderConfirmFirstActivity.class).putExtra("order", orders.get(groupPosition)));
                }
            });
        } else if (mType == 2) {
            holder.tv_left_btn.setVisibility(View.GONE);
            holder.tv_right_btn.setText("取消订单");
            holder.tv_right_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewDialog(groupPosition);//弹框提示
                }
            });
        } else if (mType == 3) {
            holder.tv_left_btn.setText("查看物流");
            holder.tv_right_btn.setText("确认收货");
            holder.tv_right_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    confirmReceived();
                }
            });
        } else if (mType == 4) {
            holder.tv_left_btn.setVisibility(View.GONE);
            holder.tv_right_btn.setText("评价商品");
            holder.tv_right_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, OrderAssessActivity.class).putExtra("order", orders.get(groupPosition)));
                }
            });
        }

        return convertView;
    }

    /**
     * 弹框提示
     *
     * @param groupPosition
     */
    private void viewDialog(final int groupPosition) {
        Builder builder = new Builder(context);
        builder.setTitle("温馨提示");
        builder.setMessage("您确定要取消该订单吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancleOrder(groupPosition);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 确认收货
     */
    private void confirmReceived() {

    }

    /**
     * 取消订单
     */
    private void cancleOrder(final int groupPosition) {
        RequestParams params = new RequestParams();
        params.put("billId", orders.get(groupPosition).getId());
        params.put("userId", new SessionUtil(context).getUserId());
        HttpUtils.cancleOrder(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                Tools.toast(context, "订单" + orders.get(groupPosition).getId() + "取消成功！");
            }

            @Override
            public void onRecevieFailed(String status, JSONObject json) {
                super.onRecevieFailed(status, json);
                Tools.toast(context, json.getString("data"));
            }
        }, params);
    }

    public class OrderGroupViewHolder {
        TextView tv_order_no;//订单编号
    }

    public class OrderChildViewHolder {
        LinearLayout ll_bottom;//item的底部栏,当一个订单多个商品时该栏目在哦最后一个商品下面显示
        ImageView img_equipment_pic;//装备图片
        TextView tv_equipment_name;//装备名称
        TextView tv_equipment_special;//装备规格
        TextView tv_equipment_price;//装备价格
        TextView tv_equipment_num;//订单装备数量
        TextView tv_total_num;//订单装备总数量
        TextView tv_total_price;//订单装备总价格
        TextView tv_left_btn;//左边按钮
        TextView tv_right_btn;//右边按钮


    }
}
