package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.hewuzhe.R;
import com.hewuzhe.model.common.DataModel;
import com.hewuzhe.model.common.PickImg;
import com.hewuzhe.presenter.PublishConditionPresenter;
import com.hewuzhe.ui.adapter.common.PickImgAdapter;
import com.hewuzhe.ui.base.ListActivity;
import com.hewuzhe.ui.widget.PicPickDialog;
import com.hewuzhe.view.PublishConditionView;

import java.util.ArrayList;

import butterknife.Bind;
import imagechooser.api.ChooserType;
import imagechooser.api.ChosenImage;
import imagechooser.api.ImageChooserListener;

public class PublishConditionActivity extends ListActivity<PublishConditionPresenter, PickImgAdapter, PickImg> implements PublishConditionView, ImageChooserListener {

    @Bind(R.id.edt_content)
    EditText _EdtContent;
    private PicPickDialog picPickDialog;
    private boolean isActivityResultOver = false;

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "发动态";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_publish_condition;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {


    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tvAction.setText("发布");

        presenter.getData(page, count);
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void action() {
        super.action();
        //上传视频
        presenter.publish(tvAction);

    }

    /**
     * 绑定Presenter
     */
    @Override
    public PublishConditionPresenter createPresenter() {
        return new PublishConditionPresenter();
    }

    @Override
    public void publishSuccess() {

    }

    @Override
    public DataModel<PickImg, Object> getData() {

        DataModel dataModel = new DataModel();
        dataModel.content = _EdtContent.getText().toString().trim();
        if (adapter.data.size() > 0) {
            adapter.removeLastData();
        }
        dataModel.list = adapter.data;
        return dataModel;
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected PickImgAdapter provideAdapter() {
        return new PickImgAdapter(getContext(), presenter, false);
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, PickImg item) {


    }

    @Override
    public void bindData(ArrayList<PickImg> data) {
        bd(data);
    }

    /**
     *
     */
    @Override
    public void showPIckDialog() {
        if (picPickDialog == null) {
            picPickDialog = new PicPickDialog(this);
        }
        picPickDialog.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (picPickDialog.imageChooserManager == null) {
                picPickDialog.reinitializeImageChooser();
            }
            picPickDialog.imageChooserManager.submit(requestCode, data);
        } else {
//            pbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isActivityResultOver = true;
                String originalFilePath = image.getFilePathOriginal();
                adapter.removeLastData();
                adapter.addLastData(new PickImg(originalFilePath, PickImg.STATUS_PICKED));
                adapter.addLastData(new PickImg());
            }
        });
    }

    @Override
    public void onError(String reason) {

    }


}
