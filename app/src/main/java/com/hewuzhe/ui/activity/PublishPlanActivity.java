package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Plan;
import com.hewuzhe.model.UploadImage;
import com.hewuzhe.model.common.DataModel;
import com.hewuzhe.model.common.PickImg;
import com.hewuzhe.presenter.PublishPlanPresenter;
import com.hewuzhe.ui.adapter.common.PickImgsAdapter;
import com.hewuzhe.ui.base.ListActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.utils.NU;
import com.hewuzhe.view.PublishPlanView;

import java.util.ArrayList;

import butterknife.Bind;
import imagechooser.api.ChooserType;
import imagechooser.api.ChosenImage;
import imagechooser.api.ImageChooserListener;
import imagechooser.api.ImageChooserManager;

public class PublishPlanActivity extends ListActivity<PublishPlanPresenter, PickImgsAdapter, PickImg> implements PublishPlanView, ImageChooserListener {

    @Bind(R.id.edt_content)
    EditText _EdtContent;
    @Bind(R.id.edt_title)
    EditText _EdtTitle;
    @Bind(R.id.tv_cate)
    TextView _TvCate;
    @Bind(R.id.lay_select_topic)
    LinearLayout _LaySelectTopic;
    @Bind(R.id.img_select_pic)
    ImageView _ImgSelectPic;
    @Bind(R.id.img_take_pic)
    ImageView _ImgTakePic;
    private boolean isActivityResultOver = false;

    private int chooserType;
    public ImageChooserManager imageChooserManager;
    public String filePath;
    private int catId;
    private String catName;
    private String start = "";
    private String end = "";
    private Plan item;
    public static final int STATUS_NEW = 0;//新创建
    public static final int STATUS_EDIT = 1;//编辑
    private int status = STATUS_NEW;
    private int id = -1;


    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "发布训练计划";
    }

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_publish_plan;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

        _ImgSelectPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        _ImgTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
        _LaySelectTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(PlanSelectActivity.class, C.REQUEST_SELECT_CATE);
            }
        });

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        item = getIntentData().getParcelable("item");
        tvAction.setText("发布");

        if (NU.notNull(item)) {
            tvAction.setText("保存");
            status = STATUS_EDIT;
            _EdtContent.setText(item.Content);
            _EdtTitle.setText(item.Title);
            _TvCate.setText(item.getCateName());
            catId = item.catId;
            start = item.StartTime;
            end = item.EndTime;
            id = item.Id;
            if (item.ImageList.size() > 0) {
                ArrayList<PickImg> pickImgs = new ArrayList<>();
                for (UploadImage uploadImage : item.ImageList) {
                    PickImg pickImg = new PickImg();
                    pickImg.status = PickImg.STATUS_PICKED;
                    pickImg.picUrl = uploadImage.ImagePath;
                    pickImgs.add(pickImg);
                }
                bindData(pickImgs);
            }
        }
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
    public PublishPlanPresenter createPresenter() {
        return new PublishPlanPresenter();
    }

    @Override
    public void publishSuccess() {


    }

    @Override
    public DataModel<PickImg, Plan> getData() {
        Plan plan = new Plan();
        plan.Title = _EdtTitle.getText().toString().trim();
        plan.Content = _EdtContent.getText().toString().trim();
        plan.catId = catId;
        plan.StartTime = start;
        plan.EndTime = end;
        plan.Id = id;

        DataModel dataModel = new DataModel();
        dataModel.list = adapter.data;
        dataModel.m = plan;
        return dataModel;
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected PickImgsAdapter provideAdapter() {
        return new PickImgsAdapter(getContext());
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        } else if (resultCode == RESULT_OK && requestCode == C.REQUEST_SELECT_CATE) {
            catId = data.getBundleExtra("data").getInt("catId");
            catName = data.getBundleExtra("data").getString("catName");
            start = data.getBundleExtra("data").getString("start");
            end = data.getBundleExtra("data").getString("end");
            _TvCate.setText(catName);
        }
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isActivityResultOver = true;
                String originalFilePath = image.getFilePathOriginal();
                adapter.addLastData(new PickImg(originalFilePath, PickImg.STATUS_PICKED));
            }
        });
    }

    @Override
    public void onError(String reason) {

    }

    private void chooseImage() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, true);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.clearOldFiles();
        try {
//            pbar.setVisibility(View.VISIBLE);
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        imageChooserManager.setImageChooserListener((ImageChooserListener) this);
        imageChooserManager.reinitialize(filePath);
    }


}
