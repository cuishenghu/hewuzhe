package com.hewuzhe.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;

import com.hewuzhe.R;
import com.hewuzhe.model.common.DataModel;
import com.hewuzhe.model.common.PickImg;
import com.hewuzhe.presenter.PublishConditionPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.view.PublishConditionView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import imagechooser.api.FileUtils;
import imagechooser.exceptions.ChooserException;
import imagechooser.helpers.StreamHelper;


public class PublishConditionVideoActivity extends ToolBarActivity<PublishConditionPresenter> implements PublishConditionView {


    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.tv_desc)
    EditText tvDesc;
    private String fileName;
    private int catId = -1;
    private String title;
    private String thumnail = "";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_publish_condition_video;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
    }

    @Override
    protected String provideTitle() {
        return "发布小视频";
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tvAction.setText("发送");
        fileName = getIntentData().getString("file_name");

        try {
            thumnail = createThumbnailOfVideo(fileName);
            img.setImageURI(Uri.parse(new File(thumnail).toString()));
        } catch (ChooserException e) {
            e.printStackTrace();
        }

    }

    /**
     * 绑定Presenter
     */
    @Override
    public PublishConditionPresenter createPresenter() {
        return new PublishConditionPresenter();
    }


    @Override
    public boolean canAction() {
        return true;
    }


    @Override
    protected void action() {
        super.action();
        presenter.UpLoadConditionVideo(tvAction, fileName, thumnail);
    }


    public String createThumbnailOfVideo(String filePath) throws ChooserException {
        String thumbnailPath = null;
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath,
                MediaStore.Video.Thumbnails.MINI_KIND);
        if (bitmap != null) {
            thumbnailPath = FileUtils.getDirectory("video") + File.separator
                    + Calendar.getInstance().getTimeInMillis() + ".jpg";
            File file = new File(thumbnailPath);

            FileOutputStream stream = null;
            try {
                stream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            } catch (IOException e) {
                throw new ChooserException(e);
            } finally {
                StreamHelper.flush(stream);
            }
        }
        return thumbnailPath;
    }


    @Override
    public void publishSuccess() {
        setResult(Activity.RESULT_OK);
        finishActivity();
    }


    @Override
    public void bindData(ArrayList<PickImg> data) {

    }

    /**
     *
     */
    @Override
    public void showPIckDialog() {

    }

    @Override
    public DataModel<PickImg, Object> getData() {
        DataModel<PickImg, Object> data = new DataModel<>();

        data.content = tvDesc.getText().toString().trim();

        return data;
    }
}
