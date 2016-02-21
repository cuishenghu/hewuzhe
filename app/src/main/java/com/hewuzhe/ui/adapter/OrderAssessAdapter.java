package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Assess;
import com.hewuzhe.model.OrderChild;
import com.hewuzhe.model.OrderContent;
import com.hewuzhe.model.OrderNumber;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;

public class OrderAssessAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<OrderContent> orders;
    private String type;
    private List<Assess> list = new ArrayList<Assess>();
    List types = new ArrayList();
    private Assess assess;

    public OrderAssessAdapter(Context context, List<OrderContent> orders) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_order_assess_item, parent, false);
            holder.img_equip_pic = (ImageView) convertView.findViewById(R.id.img_equip_pic);
            holder.tv_equip_name = (TextView) convertView.findViewById(R.id.tv_equip_name);
            holder.tv_equip_price = (TextView) convertView.findViewById(R.id.tv_equip_price);
            holder.tv_equip_num = (TextView) convertView.findViewById(R.id.tv_equip_num);
            holder.ll_chaping = (LinearLayout) convertView.findViewById(R.id.ll_chaping);
            holder.ll_zhongping = (LinearLayout) convertView.findViewById(R.id.ll_zhongping);
            holder.ll_haoping = (LinearLayout) convertView.findViewById(R.id.ll_haoping);
            holder.img_chaping = (ImageView) convertView.findViewById(R.id.img_chaping);
            holder.img_zhongping = (ImageView) convertView.findViewById(R.id.img_zhongping);
            holder.img_haoping = (ImageView) convertView.findViewById(R.id.img_haoping);
            holder.ed_content = (EditText) convertView.findViewById(R.id.ed_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderContent order = orders.get(position);
        ImageLoader.getInstance().displayImage(
                StringUtil.toString(UrlContants.IMAGE_URL + order.getMiddleImagePath(), "http://"),
                holder.img_equip_pic);
        holder.tv_equip_name.setText(order.getProductName());
        holder.tv_equip_price.setText("¥"+order.getProductPriceTotalPrice());
//        holder.tv_equip_price2.setText(order.getPrice2());
        holder.tv_equip_num.setText("x"+order.getNumber());

        holder.ll_chaping.setOnClickListener(new PingjiaxuanzeOnClickListener(holder, position));
        holder.ll_zhongping.setOnClickListener(new PingjiaxuanzeOnClickListener(holder, position));
        holder.ll_haoping.setOnClickListener(new PingjiaxuanzeOnClickListener(holder, position));

        return convertView;
    }

    class ViewHolder {
        ImageView img_equip_pic;
        TextView tv_equip_name;
        TextView tv_equip_price;
        TextView tv_equip_num;
        LinearLayout ll_chaping;
        LinearLayout ll_zhongping;
        LinearLayout ll_haoping;
        ImageView img_chaping;
        ImageView img_zhongping;
        ImageView img_haoping;
        EditText ed_content;

    }

    class PingjiaxuanzeOnClickListener implements View.OnClickListener {
        private ViewHolder holder;
        private int position;

        public PingjiaxuanzeOnClickListener(ViewHolder holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_chaping:
                    holder.img_chaping.setSelected(true);
                    holder.img_zhongping.setSelected(false);
                    holder.img_haoping.setSelected(false);
                    type = "差评";
                    break;
                case R.id.ll_zhongping:
                    holder.img_chaping.setSelected(false);
                    holder.img_zhongping.setSelected(true);
                    holder.img_haoping.setSelected(false);
                    type = "中评";
                    break;
                case R.id.ll_haoping:
                    holder.img_chaping.setSelected(false);
                    holder.img_zhongping.setSelected(false);
                    holder.img_haoping.setSelected(true);
                    type = "好评";
                    break;

            }
        }
//        for (int i = 0; i < orders.size(); i++) {
//
//            types.add(type);
//            assess.setProductId(orders.get(position).getId());
//            assess.setProductPriceId(orders.get(position).getProductPriceId());
//            assess.setTypeId(types.get(position).toString());
//            assess.setUserId(new SessionUtil(context).getUserId() + "");
//            assess.setContent(holder.ed_content.getText().toString());
//            list.add(assess);
//        }
    }
}

