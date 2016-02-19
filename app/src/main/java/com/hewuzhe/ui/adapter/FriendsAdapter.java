package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.Friend;
import com.hewuzhe.presenter.FriendsPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 16/2/19.
 */
public class FriendsAdapter extends BaseAdapter<FriendsAdapter.VHolder, Friend, FriendsPresenter> {

    public FriendsAdapter(Context context, FriendsPresenter friendsPresenter, View header) {
        super(context, friendsPresenter, header);
        isAddFooter = false;
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_friend;
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
        Friend model = data.get(position);
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            holder._TvItemIndexviewCatalog.setVisibility(View.VISIBLE);
            holder._TvItemIndexviewCatalog.setText(model.topc);
        } else {
            holder._TvItemIndexviewCatalog.setVisibility(View.GONE);

        }
        holder._TvUsername.setText(model.RemarkName);

        Glide.with(context)
                .load(C.BASE_URL + model.PhotoPath)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(context))
                .into(holder._ImgAvatar);

    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_friend.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class VHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.tv_item_indexview_catalog)
        TextView _TvItemIndexviewCatalog;
        @Nullable
        @Bind(R.id.img_avatar)
        ImageView _ImgAvatar;
        @Nullable
        @Bind(R.id.tv_username)
        TextView _TvUsername;
        @Nullable
        @Bind(R.id.tv_level)
        TextView _TvLevel;
        @Nullable
        @Bind(R.id.lay_content)
        RelativeLayout _LayContent;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public int getSectionForPosition(int position) {
        return data.get(position).topc.charAt(0);
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount() - 1; i++) {
            String sortStr = data.get(i).topc;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

}
