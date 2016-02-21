package com.hewuzhe.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hewuzhe.R;
import com.hewuzhe.model.OrderContent;
import com.hewuzhe.model.OrderNumber;
import com.hewuzhe.model.Product;
import com.hewuzhe.model.ProductSort;
import com.hewuzhe.ui.activity.OrderAssessActivity;
import com.hewuzhe.ui.activity.OrderConfirmFirstActivity;
import com.hewuzhe.ui.activity.OrderDetailsActivity;
import com.hewuzhe.ui.fragment.OrderFragment;
import com.hewuzhe.ui.http.HttpErrorHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.Tools;
import com.loopj.android.http.RequestParams;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderGroupAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<OrderNumber> orderNumbers;
    private Context context;
    private int mType;
    private int total_number = 0;
    private double total_price = 0.00;
    List total_num = null;
    List total_all_price = null;

    public OrderGroupAdapter(Context context, List<OrderNumber> orderNumbers, int mType) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.orderNumbers = orderNumbers;
        this.mType = mType;
        total_num = new ArrayList();
        total_all_price = new ArrayList();
    }

    @Override
    public int getCount() {
        return orderNumbers.size();
    }

    @Override
    public Object getItem(int position) {
        return orderNumbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_order_group, parent, false);
//            LinearLayout ll_order_no;//订单的线性布局,当商品的listview为空时候该布局不显示
//            TextView tv_order_no;//订单编号
//            TextView tv_order_total_num;//订单商品的总数量
//            TextView tv_order_total_price;//订单总价格
//            TextView tv_left_btn;//左侧按钮
//            TextView tv_right_btn;//右侧按钮
//            ListView list_product;//商品的listview
            holder.ll_order_no = (LinearLayout) convertView.findViewById(R.id.ll_order_no);
            holder.tv_order_no = (TextView) convertView.findViewById(R.id.tv_order_no);
            holder.tv_order_total_num = (TextView) convertView.findViewById(R.id.tv_order_total_num);
            holder.tv_order_total_price = (TextView) convertView.findViewById(R.id.tv_order_total_price);
            holder.tv_left_btn = (TextView) convertView.findViewById(R.id.tv_left_btn);
            holder.tv_right_btn = (TextView) convertView.findViewById(R.id.tv_right_btn);
            holder.list_product = (ListView) convertView.findViewById(R.id.list_product);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final OrderNumber orderNumber = orderNumbers.get(position);//外层商品分类的JSONObject对象
        holder.tv_order_no.setText(orderNumber.getBillNo());//订单编号

//        holder.tv_order_total_num.setText(orderNumber.get);//订单中商品的件数
        holder.tv_order_total_price.setText(orderNumber.getPrice());//订单总价

//        final Product product = productSort.getProductList().get(position);
        List<OrderContent> list = orderNumber.getProList();//内层商品的JSONArray数组
//        list.add(product);
        /**
         * 订单中商品件数累加
         */
        for (int i = 0; i < list.size(); i++) {
            int number = Integer.parseInt(list.get(i).getNumber());
            double price = Double.parseDouble(list.get(i).getProductPriceTotalPrice());
            total_number += number;
            total_price += number * price;
        }
        DecimalFormat df = new DecimalFormat("######0.00");
        holder.tv_order_total_num.setText("共计" + orderNumber.getCount() + "件商品");//订单中商品的件数
        holder.tv_order_total_price.setText("合计：¥" + df.format(Double.parseDouble(orderNumber.getPrice())));
//        holder.tv_order_total_price.setText(orderNumber.getPrice());
        total_num.add(total_number);
        total_all_price.add(total_price);
        /**
         * 当订单中没有商品,不显示
         */
        if (list.size() == 0) {
            holder.ll_order_no.setVisibility(View.GONE);
        }
        OrderChildAdapter orderChildAdapter = new OrderChildAdapter(context, list);
        holder.list_product.setAdapter(orderChildAdapter);

        /**
         * ListView自适应高度
         */
        StringUtil.listAutoHeight(holder.list_product);
//        holder.list_product.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, position + "", Toast.LENGTH_LONG).show();
//                context.startActivity(new Intent(context, OrderDetailsActivity.class).putExtra("order", orderNumber).putExtra("mType", mType).putExtra("number", total_num.get(position).toString()));
//            }
//        });
        holder.list_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int psition, long id) {
//                Toast.makeText(context, position+"", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("order", orderNumber);
                intent.putExtra("mType", mType);
                intent.putExtra("state", orderNumber.getState());
                intent.putExtra("areaId", orderNumber.getAreaId());
                ((FragmentActivity)context).startActivityForResult((intent), 11);
            }
        });

        /**
         * 不同页面显示不同的按钮
         */
        if (mType == 1) {
            holder.tv_left_btn.setText("取消订单");
            holder.tv_left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewDialog(mType, position);//弹框提示
                }
            });
            holder.tv_right_btn.setText("去付款");
            holder.tv_right_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((FragmentActivity)context).startActivityForResult(new Intent(context, OrderConfirmFirstActivity.class).putExtra("areaId", orderNumber.getAreaId()).putExtra("order", orderNumbers.get(position)).putExtra("number", total_num.get(position).toString()).putExtra("price", total_all_price.get(position).toString()),11);
//                    context.startActivity(new Intent(context, OrderConfirmFirstActivity.class).putExtra("order", orderNumbers.get(position)).putExtra("number", total_num.get(position).toString()).putExtra("price", orderNumber.getPrice()));
                }
            });
        } else if (mType == 2) {
            holder.tv_left_btn.setVisibility(View.GONE);
            if(Integer.parseInt(orderNumber.getIsCancle())==1){
                holder.tv_right_btn.setText("取消中");
            }else{
                holder.tv_right_btn.setText("取消订单");
            }
            holder.tv_right_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewDialog(mType, position);//弹框提示
                }
            });
        } else if (mType == 3) {
            holder.tv_left_btn.setText("查看物流");
            holder.tv_left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.kuaidi100.com/index_all.html?type=" + orderNumber.getLiveryName() + "&postid=" + orderNumber.getLiveryNo())));
                }
            });
            holder.tv_right_btn.setText("确认收货");
            holder.tv_right_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmReceived(position);
                }
            });
        } else if (mType == 4) {
            holder.tv_left_btn.setVisibility(View.VISIBLE);
            holder.tv_left_btn.setText("删除订单");
            holder.tv_left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewDialog(mType, position);
                }
            });
            holder.tv_right_btn.setText("评价商品");
            if (Integer.parseInt(orderNumber.getState()) == 5) {
                holder.tv_right_btn.setBackgroundResource(R.color.colorBg);
                holder.tv_right_btn.setText("已评价");
            } else {
                holder.tv_right_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, OrderAssessActivity.class).putExtra("order", orderNumbers.get(position)));
                    }
                });
            }
        }
        total_number = 0;//由于是统计一个订单里的商品的件数,加载完一个订单后将total_number初始化为0
        total_price = 0.00;
        return convertView;
    }

    /**
     * 弹框提示
     *
     * @param position
     */
    private void viewDialog(final int mType, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("温馨提示");
        if (mType == 1) {
            builder.setMessage("您确定要取消该订单吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteOrder(position);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else if (mType == 2) {
            builder.setMessage("您确定要取消该订单吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cancleOrder(position);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else if (mType == 4) {
            builder.setMessage("您确定要删除该订单吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    deleteOrder(position);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        builder.create().show();
    }

    /**
     * 删除订单
     */
    private void deleteOrder(final int position) {
        RequestParams params = new RequestParams();
        params.put("billId", orderNumbers.get(position).getId());//订单ID
        params.put("userId", new SessionUtil(context).getUserId()); //由于自己ID没有订单,现在传2,此ID为李发起的ID.待修改成自己的ID========
        HttpUtils.deleteOrder(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                orderNumbers.remove(position);
                OrderGroupAdapter.this.notifyDataSetChanged();
                Tools.toast(context, "订单删除成功！");
            }
        }, params);
    }

    /**
     * 确认收货
     */
    private void confirmReceived(final int position) {
        final RequestParams params = new RequestParams();
        params.put("billId", orderNumbers.get(position).getId());//订单ID
        params.put("userId", new SessionUtil(context).getUserId()); //由于自己ID没有订单,现在传2,此ID为李发起的ID.待修改成自己的ID==========
        HttpUtils.confirmReceived(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                Tools.toast(context, "订单" + orderNumbers.get(position).getId() + "收货成功！");
                orderNumbers.remove(position);
                OrderGroupAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onRecevieFailed(String status, JSONObject json) {
                super.onRecevieFailed(status, json);
                Tools.toast(context, json.getString("data"));
            }
        }, params);
    }

    /**
     * 取消订单
     */
    private void cancleOrder(final int position) {
        RequestParams params = new RequestParams();
        params.put("billId", orderNumbers.get(position).getId());
        params.put("userId", new SessionUtil(context).getUserId()); //由于自己ID没有订单,现在传2,此ID为李发起的ID.待修改成自己的ID==============================================================================
        HttpUtils.cancleOrder(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                Tools.toast(context, "订单" + orderNumbers.get(position).getBillNo() + "取消成功！");
                OrderGroupAdapter.this.notifyDataSetChanged();

            }

            @Override
            public void onRecevieFailed(String status, JSONObject json) {
                super.onRecevieFailed(status, json);
                Tools.toast(context, json.getString("data"));
            }
        }, params);
    }

    class ViewHolder {
        LinearLayout ll_order_no;//订单的线性布局,当商品的listview为空时候该布局不显示
        TextView tv_order_no;//订单编号
        TextView tv_order_total_num;//订单商品的总数量
        TextView tv_order_total_price;//订单总价格
        TextView tv_left_btn;//左侧按钮
        TextView tv_right_btn;//右侧按钮
        ListView list_product;//商品的listview

    }

}
