package com.hewuzhe.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.MegaVideo;
import com.hewuzhe.R;
import com.hewuzhe.presenter.MegaGameApplyPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.YsnowPicsDialog;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.MegaGameApplyView;

import java.io.File;

import butterknife.Bind;
import imagechooser.api.ChooserType;
import imagechooser.api.ChosenImage;
import imagechooser.api.ChosenVideo;
import imagechooser.api.ImageChooserListener;
import imagechooser.api.VideoChooserListener;
import imagechooser.api.VideoChooserManager;

public class MegaGameApplyActivity extends ToolBarActivity<MegaGameApplyPresenter> implements VideoChooserListener, ImageChooserListener, MegaGameApplyView {
    @Bind(R.id.img_video)
    ImageView _ImgVideo;
    @Bind(R.id.btn_upload_video)
    Button _BtnUploadVideo;
    @Bind(R.id.img_pic)
    ImageView _ImgPic;
    @Bind(R.id.img_upload_pic)
    ImageView _ImgUploadPic;
    @Bind(R.id.tv_tip)
    TextView _TvTip;
    @Bind(R.id.edt_title)
    EditText _EdtTitle;
    @Bind(R.id.edt_content)
    EditText _EdtContent;
    private int id;
    private VideoChooserManager videoChooserManager;
    private int chooserType;
    private MegaVideo megaVideo;
    private YsnowPicsDialog ysnowPicsDialog;
    private boolean isJoin = false;
    private boolean isVideoChanged = false;
    private boolean isImageChanged = false;

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_mega_game_apply;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        _BtnUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickVideo(view);
            }
        });

        _ImgUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ysnowPicsDialog == null) {
                    ysnowPicsDialog = new YsnowPicsDialog(MegaGameApplyActivity.this);
                    ysnowPicsDialog.tvTitle.setText("请选择");
                    ysnowPicsDialog.setImageChooserManager(MegaGameApplyActivity.this);
                }
                ysnowPicsDialog.show();
            }
        });

    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void action() {
        super.action();
        presenter.publish(tvAction,isJoin,isImageChanged,isVideoChanged);
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        id = getIntentData().getInt("id");
        isJoin = getIntentData().getBoolean("isJoin", false);

        if (!isJoin) {
            megaVideo = new MegaVideo();
        } else {
            presenter.getVideoDetail(id, new SessionUtil(getContext()).getUserId(), -1);
        }

    }

    /**
     * 绑定Presenter
     */
    @Override
    public MegaGameApplyPresenter createPresenter() {
        return new MegaGameApplyPresenter();
    }

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return "武者大赛报名";
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK
                && requestCode == ChooserType.REQUEST_PICK_VIDEO) {
            if (videoChooserManager == null) {
                reinitializeVideoChooser();
            }
            videoChooserManager.submit(requestCode, data);

        } else if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (ysnowPicsDialog.imageChooserManager == null) {
                ysnowPicsDialog.reinitializeImageChooser();
            }
            ysnowPicsDialog.submit(requestCode, data);
        }

    }

    // Should be called if for some reason the VideoChooserManager is null (Due
    // to destroying of activity for low memory situations)
    private void reinitializeVideoChooser() {
        videoChooserManager = new VideoChooserManager(this, chooserType, true);
        videoChooserManager.setVideoChooserListener(this);
//        videoChooserManager.reinitialize(filePath);
    }


    public void pickVideo(View view) {
        chooserType = ChooserType.REQUEST_PICK_VIDEO;
        videoChooserManager = new VideoChooserManager(this,
                ChooserType.REQUEST_PICK_VIDEO);
        videoChooserManager.setVideoChooserListener(this);
        try {
            videoChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * When the processing is complete, you will receive this callback with
     * {@link ChosenVideo}
     *
     * @param video
     */
    @Override
    public void onVideoChosen(final ChosenVideo video) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (video != null) {
                    isVideoChanged = true;
                    megaVideo.MatchVideo = video.getVideoFilePath();
                    _ImgVideo.setImageURI(Uri.fromFile(new File(video.getThumbnailPath())));
                    _BtnUploadVideo.setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * When the processing is complete, you will receive this callback with
     * {@link ChosenImage}
     *
     * @param image
     */
    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isImageChanged = true;
                megaVideo.MatchImage = image.getFilePathOriginal();
                _ImgPic.setImageURI(Uri.fromFile(new File(megaVideo.MatchImage)));
            }
        });

    }

    /**
     * Handle any error conditions if at all, when you receieve this callback
     *
     * @param reason
     */
    @Override
    public void onError(String reason) {

    }

    @Override
    public MegaVideo getData() {
        megaVideo.Id = id;
        megaVideo.Title = _EdtTitle.getText().toString().toString();
        megaVideo.MatchDescription = _EdtContent.getText().toString().toString();
        return megaVideo;
    }

    @Override
    public void publishSuccess() {
        setResult(RESULT_OK);
        finishActivity();
    }

    @Override
    public void setData(MegaVideo data) {
        megaVideo = data;
        Glide.with(getContext())
                .load(C.BASE_URL + megaVideo.MatchImage)
                .fitCenter()
                .placeholder(R.mipmap.img_bg_videio)
                .crossFade()
                .into(_ImgPic);
        Glide.with(getContext())
                .load(C.BASE_URL + megaVideo.MatchImage)
                .fitCenter()
                .placeholder(R.mipmap.img_bg_videio)
                .crossFade()
                .into(_ImgVideo);

        _EdtTitle.setText(megaVideo.Title);
        _EdtContent.setText(megaVideo.MatchDescription);

    }
}
