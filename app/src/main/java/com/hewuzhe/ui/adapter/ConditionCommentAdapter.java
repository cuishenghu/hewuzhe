package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Comment;
import com.hewuzhe.presenter.ConditonDetailPresenter;
import com.hewuzhe.ui.adapter.base.BaseAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xianguangjin on 16/1/4.
 */
public class ConditionCommentAdapter extends BaseAdapter<ConditionCommentAdapter.VHolder, Comment, ConditonDetailPresenter> {

    public ConditionCommentAdapter(Context context, ConditonDetailPresenter conditionPresenter) {
        super(context, conditionPresenter);
        isAddFooter = false;
    }

    /**
     * @return ItemView的LayoutId
     */
    @Override
    public int provideItemLayoutId() {
        return R.layout.item_coment_child;
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

    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_coment_child.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    class VHolder extends RecyclerView.ViewHolder {
        @Nullable
        @Bind(R.id.tv_user_commenter)
        TextView _TvUserCommenter;
        @Nullable
        @Bind(R.id.tv_user_commented)
        TextView _TvUserCommented;
        @Nullable
        @Bind(R.id.tv_conent)
        TextView _TvConent;

        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
