package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.FlyDreamHeader;
import com.hewuzhe.model.MyDream;
import com.hewuzhe.presenter.FlyDreamPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.view.FlyDreamView;

import butterknife.Bind;

public class FlyDreamActivity extends ToolBarActivity<FlyDreamPresenter> implements FlyDreamView {


    @Bind(R.id.edt_my_dream)
    EditText edtMyDream;
    @Bind(R.id.edt_to_do)
    EditText edtToDo;
    @Bind(R.id.img)
    ImageView _Img;
    @Bind(R.id.tv_desc)
    TextView _TvDesc;
    private boolean isEditing = false;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_fly_dream;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     *                           初始化一些事情
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tvAction.setText("编辑");
        presenter.setData();
        presenter.getHeader();


        edtMyDream.setEnabled(false);
        edtToDo.setEnabled(false);
        isEditing = false;


    }

    /**
     * 绑定Presenter
     */
    @Override
    public FlyDreamPresenter createPresenter() {
        return new FlyDreamPresenter();
    }


    @Override
    protected String provideTitle() {
        return "梦想";
    }


    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void action() {
        super.action();
        if (isEditing) {
            edtMyDream.setEnabled(false);
            edtToDo.setEnabled(false);
            isEditing = false;
            tvAction.setText("编辑");
            presenter.UpdateMyDream(tvAction);

        } else {
            tvAction.setText("确定");
            isEditing = true;
            edtMyDream.setEnabled(true);
            edtToDo.setEnabled(true);
        }
    }


    @Override
    public MyDream getDate() {
        MyDream dream = new MyDream(edtMyDream.getText().toString().trim(), edtToDo.getText().toString().trim());
        return dream;
    }

    @Override
    public void setData(MyDream dream) {
        edtMyDream.setText(dream.MyDream);
        edtToDo.setText(dream.RealizeDream);
    }

    /**
     * 设置顶部信息
     *
     * @param data
     */
    @Override
    public void setHeader(FlyDreamHeader data) {
        Glide.with(getContext())
                .load(C.BASE_URL + data.DreamImage)
                .fitCenter()
                .crossFade()
                .placeholder(R.mipmap.img_bg)
                .into(_Img);

        _TvDesc.setText(data.DreamDescription);
    }

}
