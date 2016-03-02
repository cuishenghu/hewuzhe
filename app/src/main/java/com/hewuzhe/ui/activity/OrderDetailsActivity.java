package com.hewuzhe.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hewuzhe.R;
import com.hewuzhe.model.OrderContent;
import com.hewuzhe.model.OrderNumber;
import com.hewuzhe.ui.adapter.OrderDetailsItemAdaptet;
import com.hewuzhe.ui.base.BaseActivity2;
import com.hewuzhe.ui.http.HttpErrorHandler;
import com.hewuzhe.ui.http.HttpUtils;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.utils.Tools;
import com.hewuzhe.view.MyCommonTitle;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/25 0025.
 */
public class OrderDetailsActivity extends BaseActivity2 {

    private MyCommonTitle myCommonTitle;
    private LinearLayout ll_no_address, ll_address, ll_pay_time, ll_send_time;
    private TextView tv_username;
    private TextView tv_mobile;
    private TextView tv_address;
    private TextView tv_order_time;//付款时间,发货时间,收货时间
    private TextView tv_buyer_state;//买家的订单状态
    private TextView tv_postage_price;
    private TextView tv_order_sum;
    private TextView tv_total_price;
    private TextView tv_order_no;
    private TextView tv_pay_time;
    private TextView tv_create_order_time;
    private TextView tv_dispatch_date;
    private TextView tv_left_btn;
    private TextView tv_right_btn;
    private ListView order_list;
    //    private List<OrderNumber> orders = new ArrayList<OrderNumber>();
    private OrderDetailsItemAdaptet orderDetailsItemAdaptet;
    private String state;//判断是否评论过
    private String billId;//订单ID
    private int IsCancle;

    //    private int[] pic = {R.drawable.icon_item_pic, R.drawable.icon_item_pic};
//    private String data[][] = {{"男士哑铃10公斤一对", "规格：S/红色", "¥20.00", "x1"}, {"男士哑铃10公斤一对", "规格：S/红色", "¥20.00", "x1"}};
//    private SimpleAdapter simpleAdapter = null;
//    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private OrderNumber orders;
    private int mType;
    private int buyerState;//买家状态
    private int total_number;
    private double total_price;
    private String areaId;//
    private ArrayList<OrderContent> orderContents = new ArrayList<OrderContent>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        areaId = (String) getIntent().getSerializableExtra("areaId");
        billId = getIntent().getStringExtra("billId");
        mType = getIntent().getIntExtra("mType", 0);
//        orders = (OrderNumber) getIntent().getSerializableExtra("order");
//        total_number = Integer.parseInt((String) getIntent().getSerializableExtra("number"));
//        total_price = Double.parseDouble((String) getIntent().getSerializableExtra("price"));
        state = (String) getIntent().getStringExtra("state");
//        int i = orders.getProList().size();
        setContentView(R.layout.activity_order_details);
        initView();
        requestData();
//        selectButtonFromType(mType);//根据传过来的mType值来显示底部Button按钮
    }

    /**
     * 初始化
     */
    public void initView() {
        myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
        myCommonTitle.setTitle("订单详情");

        /**
         * 如果areaId不为空根据areaId查询收货人信息
         */
        if (!StringUtil.isEmpty(areaId)) {
            getReceiverInfo();
        }

        tv_username = (TextView) findViewById(R.id.tv_username);//收货人
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);//收货人联系方式
        tv_address = (TextView) findViewById(R.id.tv_address);//收货地址
        tv_buyer_state = (TextView) findViewById(R.id.tv_buyer_state);//买家订单状态
        tv_postage_price = (TextView) findViewById(R.id.tv_postage_price);//上部邮费
        tv_order_sum = (TextView) findViewById(R.id.tv_order_sum);//订单数量
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);//订单总价
        tv_order_no = (TextView) findViewById(R.id.tv_order_no);//订单编号
        tv_pay_time = (TextView) findViewById(R.id.tv_pay_time);//付款时间
        tv_create_order_time = (TextView) findViewById(R.id.tv_create_order_time);//订单创建时间
        tv_dispatch_date = (TextView) findViewById(R.id.tv_dispatch_date);//发货时间
        tv_left_btn = (TextView) findViewById(R.id.tv_left_btn);//左边按钮
        tv_right_btn = (TextView) findViewById(R.id.tv_right_btn);//右边按钮

        tv_order_time = (TextView) findViewById(R.id.tv_order_time);////付款时间,发货时间,收货时间
        ll_pay_time = (LinearLayout) findViewById(R.id.ll_pay_time);//付款时间


        order_list = (ListView) findViewById(R.id.list_order_details);

//        getOrderDetailsByBillId();
        getOrderDetailsByBillId();
        orderDetailsItemAdaptet = new OrderDetailsItemAdaptet(OrderDetailsActivity.this, orderContents);
        int u = orderContents.size();
        order_list.setAdapter(orderDetailsItemAdaptet);

//        for (int i = 0; i < data.length; i++) {
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("pic", String.valueOf(this.pic[i]));
//            map.put("name", this.data[i][0]);
//            map.put("special", this.data[i][1]);
//            map.put("price", this.data[i][2]);
//            map.put("num", this.data[i][3]);
//            this.list.add(map);
//        }
//        this.simpleAdapter = new SimpleAdapter(this, list, R.layout.item_order_details_item, new String[]{"pic", "name", "special", "price", "num"},
//                new int[]{R.id.img_equip_pic, R.id.tv_equip_name, R.id.tv_equip_special, R.id.tv_equip_price, R.id.tv_equip_num});
//        order_list.setAdapter(simpleAdapter);


//        ListAdapter listAdapter = order_list.getAdapter();
//        if (listAdapter == null) {
//            return;
//        }
//
//        int totalHeight = 0;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, order_list);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//
//        ViewGroup.LayoutParams params = order_list.getLayoutParams();
//        params.height = totalHeight + (order_list.getDividerHeight() * (listAdapter.getCount()-1));
//        ((ViewGroup.MarginLayoutParams)params).setMargins(10, 10, 10, 10);
//        order_list.setLayoutParams(params);

        setListener(tv_right_btn, tv_left_btn);
    }

    /**
     * 根据areaId查询收货人信息
     */
    public void getReceiverInfo() {
        RequestParams params = new RequestParams();
        params.put("id", areaId);
        params.put("userId", new SessionUtil(OrderDetailsActivity.this).getUserId());
        HttpUtils.getAddressByAreaId(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                JSONObject jsonObject = json.getJSONObject("data");
                tv_username.setText(jsonObject.getString("ReceiverName"));
                tv_mobile.setText(jsonObject.getString("Phone"));
                tv_address.setText(jsonObject.getString("Area") + jsonObject.getString("Address"));
            }
        }, params);
    }

    /**
     * 请求数据
     */
    public void requestData() {

        /**
         * 买家的状态
         */
        if (buyerState == 1) {
            tv_buyer_state.setVisibility(View.VISIBLE);
            tv_buyer_state.setText("等待买家付款");
        } else if (buyerState == 2) {
            tv_buyer_state.setVisibility(View.VISIBLE);
            tv_buyer_state.setText("买家已付款");
        } else if (buyerState == 3) {
            tv_buyer_state.setVisibility(View.VISIBLE);
            tv_buyer_state.setText("待收货");
        } else if (buyerState == 4) {
            tv_buyer_state.setVisibility(View.VISIBLE);
            tv_buyer_state.setText("已收货");
        }
        /**
         * 根据订单类型显示订单的明细时间
         */
        if (mType == 1) {
            ll_pay_time.setVisibility(View.GONE);
        } else if (mType == 2) {
            tv_order_time.setText("付款时间：");
        } else if (mType == 3) {
            tv_order_time.setText("发货时间：");
        } else if (mType == 4) {
            tv_order_time.setText("收货时间：");
        }
    }

    /**
     * 根据订单ID分页查看订单商品
     */
    private void getOrderDetailsByBillId() {
        RequestParams params = new RequestParams();
        params.put("startRowIndex", 0);
        params.put("maximumRows", 1000);
        params.put("billId", billId);
        HttpUtils.getProductByOrderNo(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
//                "Id":481,   "MiddleImagePath":"UpLoad/Product/1b727b58-1570-4094-b377-79e724ae5621.jpg",    "ImagePath":"UpLoad/Product/1b727b58-1570-4094-b377-79e724ae5621.jpg",
//                        "Number":1    "DetailTotalPrice":0.01,   "DetailPrice":0.01,     "ProductSizeName":"每平方米",     "ProductColorName":"海蓝色",    "ProductName":"空翻气垫",
//                        "BillNo":"S2016022000000074",   "BillPrice":0.01,     "BuildTime":"2016-02-20 16:58:22",    "PayTime":"1900-01-01 00:00:00",   "SendTime":"1900-01-01 00:00:00",
//                        "ReceiveTime":"1900-01-01 00:00:00",   "AreaId":2,   "AreaName":"/北京市/北京市",   "Address":"朗瑞大厦",   "Phone":"15363222221",   "ReceiverName":"路人甲"
                JSONArray jsonArray = json.getJSONArray(UrlContants.jsonData);
                JSONObject jsonObject;
                DecimalFormat df = new DecimalFormat("#####0.00");
                for (int i = 0; i < jsonArray.size(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    OrderContent orderContent = new OrderContent();
                    orderContent.setProductId(jsonObject.getString("ProductId"));
                    orderContent.setProductName(jsonObject.getString("ProductName"));
                    orderContent.setProductPriceId(jsonObject.getString("ProductPriceId"));
                    orderContent.setProductSizeName(jsonObject.getString("ProductSizeName"));
                    orderContent.setProductColorName(jsonObject.getString("ProductColorName"));
                    orderContent.setProductPriceTotalPrice(jsonObject.getString("DetailPrice"));
                    orderContent.setNumber(jsonArray.getJSONObject(i).getString("Number"));
                    orderContent.setMiddleImagePath(jsonObject.getString("MiddleImagePath"));
                    orderContents.add(orderContent);
                    IsCancle = Integer.parseInt(jsonObject.getString("IsCancle"));
                    tv_total_price.setText("合计：¥" + jsonObject.getString("BillPrice"));//订单价格
                    tv_order_sum.setText("共" + jsonArray.size() + "件商品");
                    tv_order_no.setText(jsonObject.getString("BillNo"));//订单编号
                    tv_create_order_time.setText(jsonObject.getString("BuildTime"));//创建订单时间
                    if (mType == 2) {
                        tv_pay_time.setText(jsonObject.getString("PayTime"));//付款时间
                    } else if (mType == 3) {
                        tv_pay_time.setText(jsonObject.getString("SendTime"));//发货时间
                    } else if (mType == 4) {
                        tv_pay_time.setText(jsonObject.getString("ReceiveTime"));//收货时间
                    }
                    tv_postage_price.setText("货到付款");
                    buyerState = Integer.parseInt(jsonObject.getString("State"));
                    areaId = jsonObject.getString("AreaId");
//                    int getPostage = Integer.parseInt(orders.getPostage());
//                    tv_postage_price.setText(getPostage == 0 ? "包邮" : getPostage == (-1) ? "货到付款" : "邮费：**元");    //上部邮费.....卖家包邮
                }
                selectButtonFromType(mType);//根据传过来的mType值来显示底部Button按钮
                orderDetailsItemAdaptet.notifyDataSetChanged();
                getReceiverInfo();
                /**
                 * ListView自适应高度
                 */
                StringUtil.listAutoHeight(order_list);
            }
        }, params);


    }


    private void selectButtonFromType(final int mType) {
        if (mType == 1) {
            tv_left_btn.setVisibility(View.VISIBLE);
            tv_left_btn.setText("取消订单");
            tv_left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewDialog(billId, mType);
                }
            });
            tv_right_btn.setVisibility(View.VISIBLE);
            tv_right_btn.setText("去付款");
            tv_right_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(OrderDetailsActivity.this, OrderConfirmFirstActivity.class).putExtra("areaId", areaId).putExtra("list", orderContents).putExtra("billId", billId).putExtra("number", tv_order_sum.getText().subSequence(1, 2).toString()).putExtra("price", tv_total_price.getText().toString().substring(4, tv_total_price.getText().toString().length())));
                    finish();
                }
            });
        }
        if (mType == 2) {
            tv_left_btn.setVisibility(View.GONE);
            tv_right_btn.setVisibility(View.VISIBLE);
            if (IsCancle == 1) {
                tv_right_btn.setText("取消中");
            } else {
                tv_right_btn.setText("取消订单");
            }

            tv_right_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewDialog(billId, mType);
                }
            });
        }
        if (mType == 3) {
            tv_left_btn.setVisibility(View.VISIBLE);
            tv_left_btn.setText("查看物流");
            tv_left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.kuaidi100.com/index_all.html?type=" + orders.getLiveryName() + "&postid=" + orders.getLiveryNo())));
                }
            });
            tv_right_btn.setVisibility(View.VISIBLE);
            tv_right_btn.setText("确认收货");
            tv_right_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewDialog(billId, mType);
                }
            });
        }

        if (mType == 4) {
            tv_left_btn.setVisibility(View.VISIBLE);
            tv_left_btn.setText("删除订单");
            tv_left_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewDialog(billId, mType);
                }
            });
            tv_right_btn.setVisibility(View.VISIBLE);
            if (Integer.parseInt(state) == 5) {
                tv_right_btn.setBackgroundResource(R.color.colorBg);
                tv_right_btn.setText("已评价");
            } else {
                tv_right_btn.setText("评价商品");
                tv_right_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(OrderDetailsActivity.this, OrderAssessActivity.class).putExtra("list", orderContents).putExtra("billId", billId));
                        finish();
                    }
                });
            }
        }
    }

    /**
     * 弹框提示
     */
    private void viewDialog(final String billId, final int mType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示");
        if (mType == 1) {
            builder.setMessage("您确定要取消该订单吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteOrder(billId, mType);
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
                    cancleOrder(billId, mType);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else if (mType == 3) {
            builder.setMessage("您确认收到此商品了吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    confirmReceived(billId, mType);
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
                    deleteOrder(billId, mType);
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
    private void deleteOrder(final String billId, final int mType) {
        RequestParams params = new RequestParams();
        params.put("billId", billId);//订单ID
        params.put("userId", new SessionUtil(OrderDetailsActivity.this).getUserId()); //由于自己ID没有订单,现在传2,此ID为李发起的ID.待修改成自己的ID========
        HttpUtils.deleteOrder(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                Tools.toast(OrderDetailsActivity.this, "订单删除成功！");
                setResult(99, new Intent().putExtra("mType", mType));
                finish();
            }
        }, params);
    }

    /**
     * 确认收货
     */
    private void confirmReceived(final String billId, final int mType) {
        RequestParams params = new RequestParams();
        params.put("billId", billId);//订单ID
        params.put("userId", new SessionUtil(OrderDetailsActivity.this).getUserId()); //由于自己ID没有订单,现在传2,此ID为李发起的ID.待修改成自己的ID==========
        HttpUtils.confirmReceived(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                Tools.toast(OrderDetailsActivity.this, "订单" + orders.getId() + "收货成功！");
//                setResult(99, new Intent().putExtra("mType", mType + 1));
                startActivity(new Intent(OrderDetailsActivity.this, OrderCenterActivity2.class).putExtra("mType", mType + 1));
                OrderCenterActivity2.OrderCenterActivity2.finish();
                finish();
//                reflush();
            }

            @Override
            public void onRecevieFailed(String status, JSONObject json) {
                super.onRecevieFailed(status, json);
                Tools.toast(OrderDetailsActivity.this, json.getString("data"));
            }
        }, params);
    }

    /**
     * 取消订单
     */
    private void cancleOrder(final String billId, final int mType) {
        RequestParams params = new RequestParams();
        params.put("billId", billId);
        params.put("userId", new SessionUtil(OrderDetailsActivity.this).getUserId()); //由于自己ID没有订单,现在传2,此ID为李发起的ID.待修改成自己的ID==============================================================================
        HttpUtils.cancleOrder(new HttpErrorHandler() {
            @Override
            public void onRecevieSuccess(JSONObject json) {
                Tools.toast(OrderDetailsActivity.this, "订单" + orders.getBillNo() + "取消成功！等待审核");
                setResult(99, new Intent().putExtra("mType", mType));
//                startActivity(new Intent(OrderDetailsActivity.this, OrderCenterActivity.class).putExtra("mType", mType+""));
                finish();
//                reflush();
            }

            @Override
            public void onRecevieFailed(String status, JSONObject json) {
                super.onRecevieFailed(status, json);
                Tools.toast(OrderDetailsActivity.this, json.getString("data"));
            }
        }, params);
    }

    public void reflush() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailsActivity.this);
        builder.setMessage("取消成功，请刷新该页面查看剩余订单");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
