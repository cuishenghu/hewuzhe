package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.StudyOnlineCatItem;
import com.hewuzhe.ui.cons.C;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Object> {
    private Context context;
    private List<StudyOnlineCatItem> values;

    public MyAdapter(Context context) {
        super(context, R.layout.item_study_one);
        this.context = context;
    }

    public void setData(List<StudyOnlineCatItem> values) {
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        StudyOnlineCatItem item = this.values.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.item_study_one, parent, false);
        ImageView image = (ImageView) rowView.findViewById(R.id.img);
        TextView name = (TextView) rowView.findViewById(R.id.tv_name);

        name.setText(item.Name);

        Glide.with(context)
                .load(C.BASE_URL + item.ImagePath)
                .crossFade()
                .placeholder(R.mipmap.img_bg_videio)
                .into(image);

        return rowView;

    }
}
