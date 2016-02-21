package com.hewuzhe.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Equipment;
import com.hewuzhe.model.EquipmentSort;
import com.hewuzhe.ui.http.UrlContants;
import com.hewuzhe.utils.StringUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/1/23 0023.
 */
public class EquipmentRecommendAdapter extends BaseExpandableListAdapter {
    private LayoutInflater mLayoutInflater;
    private List<EquipmentSort> equipmentSorts;
    private int sumtiaoshu;//商品条数
    private Activity activity;
    private Context context;

    private int mType;

    public EquipmentRecommendAdapter(Context context, List<EquipmentSort> equipmentSorts, int sumtiaoshu) {
        super();
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.equipmentSorts = equipmentSorts;
        this.sumtiaoshu = sumtiaoshu;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //获取子对象
        final EquipmentSort equipmentSort = equipmentSorts.get(groupPosition);
        if (equipmentSort == null || equipmentSort.getProductList() == null || equipmentSort.getProductList().isEmpty()) {
            return 0;
        }
        return equipmentSort.getProductList().size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //获取子对象
        final EquipmentSort equipmentSort = equipmentSorts.get(groupPosition);
        if (equipmentSort == null || equipmentSort.getProductList() == null || equipmentSort.getProductList().isEmpty()) {
            return null;
        }
        return equipmentSort.getProductList().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final EquipmentSort equipmentSort = equipmentSorts.get(groupPosition);//商品分类名称
        final Equipment equipment = equipmentSort.getProductList().get(childPosition);//商品信息
        final EquipmentViewHolder holder;
        if (convertView == null) {
            holder = new EquipmentViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_equipment_child_recommend, null);
            holder.img_equipment_pic = (ImageView) convertView.findViewById(R.id.img_equip_pic);//商品图片
            holder.tv_equipment_name = (TextView) convertView.findViewById(R.id.tv_equip_name);//商品名称
            holder.tv_equipment_price = (TextView) convertView.findViewById(R.id.tv_equip_price);//商品价格
            holder.tv_equipment_person = (TextView) convertView.findViewById(R.id.tv_equip_visit_sum);//商品关注度
            holder.tv_equipment_sale_num = (TextView) convertView.findViewById(R.id.tv_equip_sale_num);//商品销售量
            convertView.setTag(holder);
        } else {
            holder = (EquipmentViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(StringUtil.toString(UrlContants.IMAGE_URL +
                equipment.getImagePath(), "http://"), holder.img_equipment_pic);
        holder.tv_equipment_name.setText(equipment.getName());
        holder.tv_equipment_price.setText("¥" + equipment.getPrice());
        holder.tv_equipment_person.setText(equipment.getVisitNum() + "人");
        holder.tv_equipment_sale_num.setText("销量：" + equipment.getSaleNum());
        return convertView;
    }

    /**
     * =================================================================
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * =================================================================
     */
    @Override
    public int getGroupCount() {
        if (equipmentSorts == null) {
            return 0;
        }
        return equipmentSorts.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (equipmentSorts == null) {
            return null;
        }
        return equipmentSorts.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final EquipmentSort equipmentSort = equipmentSorts.get(groupPosition);
        final EquipmentSortViewHolder holder;
        if (convertView == null) {
            holder = new EquipmentSortViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_equipment_group_recommend, null);
            holder.tv_equip_sort = (TextView) convertView.findViewById(R.id.tv_equip_sort);
            convertView.setTag(holder);
        } else {
            holder = (EquipmentSortViewHolder) convertView.getTag();
        }
        holder.tv_equip_sort.setText(equipmentSort.getName());

        return convertView;
    }


    final static class EquipmentSortViewHolder {
        TextView tv_equip_sort;
    }

    final static class EquipmentViewHolder {
        ImageView img_equipment_pic;//装备图片
        TextView tv_equipment_name;//装备名称
        TextView tv_equipment_price;//装备价格
        TextView tv_equipment_person;//人数
        TextView tv_equipment_sale_num;//装备销售数量
        LinearLayout ll_item_sort;


    }
}
