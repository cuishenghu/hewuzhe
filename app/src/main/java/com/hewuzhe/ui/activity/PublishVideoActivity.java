package com.hewuzhe.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Video;
import com.hewuzhe.presenter.PublisVideoPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.view.PublistVideoVIew;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import butterknife.Bind;
import imagechooser.api.FileUtils;
import imagechooser.exceptions.ChooserException;

public class PublishVideoActivity extends ToolBarActivity<PublisVideoPresenter> implements PublistVideoVIew {


    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.tv_name)
    EditText tvName;
    @Bind(R.id.tv_desc)
    EditText tvDesc;
    @Bind(R.id.tv_cate)
    TextView tvCate;
    @Bind(R.id.lay_select_topic)
    LinearLayout laySelectTopic;
    private String fileName;
    private int catId = -1;
    private String title;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_publish_video;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        laySelectTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(CateSelectActivity.class, C.REQUEST_SELECT_CATE);
            }
        });
    }

    @Override
    protected String provideTitle() {
        return "发布视频";
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        tvAction.setText("发送");
        fileName = getIntentData().getString("file_name");

        try {
            String thumnail = createThumbnailOfVideo(fileName);
            img.setImageURI(Uri.parse(new File(thumnail).toString()));
        } catch (ChooserException e) {
            e.printStackTrace();
        }

    }

    /**
     * 绑定Presenter
     */
    @Override
    public PublisVideoPresenter createPresenter() {
        return new PublisVideoPresenter();
    }


    @Override
    public boolean canAction() {
        return true;
    }


    @Override
    protected void action() {
        super.action();
        if(tvName.getText().toString().trim().equals("")||catId==-1||tvDesc.getText().toString().trim().equals(""))
            snb("请确保视频信息已全部填写！",tvAction);
        else
            presenter.UpLoadVideo(tvAction, fileName, catId, getIntentData().getInt("uploadType"));
    }

    @Override
    public Video getData() {
        Video video = new Video();
        video.Title = tvName.getText().toString().trim();
        video.Content = tvDesc.getText().toString().trim();

        return video;
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
//                flush(stream);
            }
        }
        return thumbnailPath;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getBundleExtra("data");
            catId = bundle.getInt("id");
            title = bundle.getString("title", "");
            tvCate.setText(title);
        }

    }
}
