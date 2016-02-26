package com.hewuzhe.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.hewuzhe.R;
import com.hewuzhe.model.Pic;
import com.hewuzhe.model.common.DataModel;
import com.hewuzhe.model.common.PickImg;
import com.hewuzhe.presenter.PublishConditionPresenter;
import com.hewuzhe.ui.adapter.common.PickImgAdapter;
import com.hewuzhe.ui.base.ListActivity;
import com.hewuzhe.ui.widget.PicPickDialog;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.GlideLoader;
import com.hewuzhe.utils.StringUtil;
import com.hewuzhe.view.PublishConditionView;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.util.ArrayList;

import butterknife.Bind;
import imagechooser.api.ChooserType;
import imagechooser.api.ChosenImage;
import imagechooser.api.ImageChooserListener;
import imagechooser.api.ImageChooserManager;

public class PublishConditionActivity extends ListActivity<PublishConditionPresenter, PickImgAdapter, PickImg> implements PublishConditionView, ImageChooserListener {

    @Bind(R.id.edt_content)
    EditText _EdtContent;
    @Bind(R.id.lay_content)
    LinearLayout _LayContent;
    @Bind(R.id.img_select_pic)
    ImageView _ImgSelectPic;
    @Bind(R.id.img_take_pic)
    ImageView _ImgTakePic;
    private PicPickDialog picPickDialog;
    private boolean isActivityResultOver = false;
    private ArrayList<Pic> pics = new ArrayList<>();
    private ImageConfig imageConfig;
    private int chooserType;
    private ImageChooserManager imageChooserManager;
    private String filePath;

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
        _ImgSelectPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        _ImgTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });


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
        setResult(Activity.RESULT_OK);
        finishActivity();
    }

    @Override
    public DataModel<PickImg, Object> getData() {

        DataModel dataModel = new DataModel();
        dataModel.content = _EdtContent.getText().toString().trim();
//        if (adapter.data.size() > 0) {
//            adapter.removeLastData();
//        }
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

        if (item.status == PickImg.STATUS_EMPTY) {
            showPIckDialog();
        } else {
            for (PickImg pickImg : adapter.data) {
                Pic pic = new Pic();
                if (!StringUtil.isEmpty(pickImg.filePath)) {
                    pic.ImagePath = pickImg.filePath;
                    pic.PictureUrl = pickImg.picUrl;
                    pics.add(pic);
                }
            }

            PicsActivity.isLocal = true;
            startActivity(PicsActivity.class, new Bun().putInt("pos", pos).putString("pics", new Gson().toJson(pics)).ok());
        }

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
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        } else if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get Image Path List
            ArrayList<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
//            adapter.removeLastData();
            if (pathList.size() > 0) {
                _LayContent.setVisibility(View.VISIBLE);
            }
            for (String s : pathList) {
                adapter.addLastData(new PickImg(s, PickImg.STATUS_PICKED));
            }
//            adapter.addLastData(new PickImg());

        } else {
//            pbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _LayContent.setVisibility(View.VISIBLE);
                isActivityResultOver = true;
                String originalFilePath = image.getFilePathOriginal();
//                adapter.removeLastData();
                adapter.addLastData(new PickImg(originalFilePath, PickImg.STATUS_PICKED));
//                adapter.addLastData(new PickImg());
            }
        });
    }

    @Override
    public void onError(String reason) {

    }


    private void chooseImage() {
        imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.colorPrimary))
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                        // 开启多选   （默认为多选）
                .mutiSelect()
                        // 多选时的最大数量   （默认 9 张）
                .mutiSelectMaxSize(9)
                        // 开启拍照功能 （默认关闭）
                .showCamera()
                        // 已选择的图片路径
                .pathList(new ArrayList<String>())
                        // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();

        ImageSelector.open(this, imageConfig);   // 开启图片选择器
    }


    private void takePicture() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, true);
        imageChooserManager.setImageChooserListener(this);
        try {
//            pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Should be called if for some reason the ImageChooserManager is null (Due
    // to destroying of activity for low memory situations)
    public void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType, true);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

}
