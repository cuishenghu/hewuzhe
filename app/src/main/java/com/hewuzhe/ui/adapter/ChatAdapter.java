package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.ChatList;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.ConditionDetialTwoActivity;
import com.hewuzhe.ui.activity.FriendProfileActivity;
import com.hewuzhe.ui.activity.StrangerProfileSettingsActivity;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.adapter.base.BaseNoMoreAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.StringUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zycom on 2016/3/29.
 */
public class ChatAdapter extends BaseAdapter<ChatAdapter.ViewHolder, ChatList, BasePresenterImp> {

    public static final int TYPE_HEADER = 0;

    public ChatAdapter(Context context) {
        super(context);
        isAddFooter = false;

    }

    public ChatAdapter(Context context, BasePresenterImp basePresenterImp, View header) {
        super(context, basePresenterImp, header);
        isAddFooter = false;
    }

    public ChatAdapter(Context context, ArrayList<ChatList> data) {
        super(context);
        this.data = data;
        isAddFooter = false;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.chat_item;
    }

    @Override
    public ViewHolder createVH(ViewGroup parent, int viewType, View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindData(ViewHolder holder, int position) {
        final ChatList chatList = data.get(position);
        Glide.with(context)
                .load(C.BASE_URL + chatList.PhotoPath)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(context))
                .placeholder(R.mipmap.img_avatar)
                .into(holder.chat_u_icon);
        holder.chat_u_name.setText(chatList.NicName);
        //设置imageview宽高
        WindowManager manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width =display.getWidth();
        ViewGroup.LayoutParams para;
        para = holder.pt_photo.getLayoutParams();

        para.height = (width- StringUtil.dip2px(context, 24))/2;
        holder.pt_photo.setLayoutParams(para);
        if(chatList.PicList!=null&&chatList.PicList.size()!=0)
            Glide.with(context)
                    .load(C.BASE_URL + chatList.PicList.get(0).ImagePath)
                    .crossFade()
                    .placeholder(R.mipmap.img_avatar)
                    .into(holder.pt_photo);
        else
            Glide.with(context)
                    .load(C.BASE_URL + chatList.ImagePath)
                    .crossFade()
                    .placeholder(R.mipmap.img_avatar)
                    .into(holder.pt_photo);
        holder.chat_title.setText(chatList.Content);
        holder.chat_f_count.setText(chatList.LikeNum+"");
        holder.chat_c_count.setText(chatList.ComList!=null?chatList.ComList.size()+"":0+"");

        holder.chat_user_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatList.IsFriend) {
                    context.startActivity(new Intent(context, FriendProfileActivity.class).putExtra("data", new Bun().putInt("id", chatList.UserId).ok()));
                } else {
                    context.startActivity(new Intent(context, StrangerProfileSettingsActivity.class).putExtra("data", new Bun().putInt("id", chatList.UserId).ok()));
                }
            }
        });
        holder.pt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ConditionDetialTwoActivity.class).putExtra("data", new Bun().putInt("id", chatList.Id).putInt("whitch", C.WHITCH_ONE).ok()));
            }
        });
        holder.chat_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ConditionDetialTwoActivity.class).putExtra("data", new Bun().putInt("id", chatList.Id).putInt("whitch", C.WHITCH_ONE).ok()));
            }
        });
        holder.chat_dongtai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ConditionDetialTwoActivity.class).putExtra("data", new Bun().putInt("id", chatList.Id).putInt("whitch", C.WHITCH_ONE).ok()));

            }
        });
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable @Bind(R.id.chat_u_icon)
        ImageView chat_u_icon;
        @Nullable @Bind(R.id.chat_u_name)
        TextView chat_u_name;
        @Nullable @Bind(R.id.pt_photo)
        ImageView pt_photo;
        @Nullable @Bind(R.id.chat_title)
        TextView chat_title;
        @Nullable @Bind(R.id.chat_f_count)
        TextView chat_f_count;
        @Nullable @Bind(R.id.chat_c_count)
        TextView chat_c_count;
        @Nullable @Bind(R.id.chat_user_select)
        LinearLayout chat_user_select;
        @Nullable @Bind(R.id.chat_dongtai)
        LinearLayout chat_dongtai;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
