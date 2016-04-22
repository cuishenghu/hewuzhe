package com.hewuzhe.rongc.provider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.duanqu.qupai.VideoActivity;
import com.duanqu.qupai.android.app.QupaiServiceImpl;
import com.duanqu.qupai.editor.EditorResult;
import com.duanqu.qupai.engine.session.MovieExportOptions;
import com.duanqu.qupai.engine.session.SessionClientActivityModule;
import com.duanqu.qupai.engine.session.VideoSessionCreateInfo;
import com.duanqu.qupai.recorder.EditorCreateInfo;
import com.hewuzhe.presenter.PublisVideoPresenter;
import com.hewuzhe.presenter.UploadVideoPresenter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.cons.Contant;
import com.hewuzhe.ui.cons.FileUtils;
import com.hewuzhe.view.UploadVideoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.rong.imkit.R;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;


/**
 * Created by zycom on 2016/3/2.
 */
public class VideoInputProvider extends InputProvider.ExtendProvider implements UploadVideoView {

    private Context mContext;
    private int beautySkinProgress;
    private final EditorCreateInfo _CreateInfo = new EditorCreateInfo();
    private String videoPath;
    public static int QUPAI_RECORD_REQUEST = 102;
    public View mview;
    String pic="";
    String ider="";
    String type="";
    String picSrc="";
    UploadVideoPresenter uploadVideoPresenter = new UploadVideoPresenter();

    public VideoInputProvider(RongContext context) {
        super(context);
        mContext = context;
        uploadVideoPresenter.attachView(this);
    }
    //设置 展示的图标
    @Override
    public Drawable obtainPluginDrawable(Context context) {
        // TODO Auto-generated method stub
        return context.getResources().getDrawable(R.drawable.rc_ic_video);
    }
    //设置 图标下的title
    @Override
    public CharSequence obtainPluginTitle(Context arg0) {
        // TODO Auto-generated method stub
        return "小视频";
    }

    @Override
    public void onPluginClick(View view) {
        ider = this.getCurrentFragment().getUri().toString();
        ider = ider.substring(ider.indexOf("=") + 1);
        type = this.getCurrentFragment().getUri().toString().substring(this.getCurrentFragment().getUri().toString().lastIndexOf("/")+1,this.getCurrentFragment().getUri().toString().indexOf("?"));
        startRecordActivity(view);
    }

    private void startRecordActivity(View view) {
        this.mview = view;
        //美颜参数:1-100.这里不设指定为80,这个值只在第一次设置，之后在录制界面滑动美颜参数之后系统会记住上一次滑动的状态

            beautySkinProgress = 80;

            /**
             * 压缩参数，可以自由调节
             */
            MovieExportOptions movie_options = new MovieExportOptions.Builder()
                    .setVideoProfile("high")
                    .setVideoBitrate(Contant.DEFAULT_BITRATE)
                    .setVideoPreset(Contant.DEFAULT_VIDEO_Preset).setVideoRateCRF(Contant.DEFAULT_VIDEO_RATE_CRF)
                    .setOutputVideoLevel(Contant.DEFAULT_VIDEO_LEVEL)
                    .setOutputVideoTune(Contant.DEFAULT_VIDEO_TUNE)
                    .configureMuxer(Contant.DEFAULT_VIDEO_MOV_FLAGS_KEY, Contant.DEFAULT_VIDEO_MOV_FLAGS_VALUE)
                    .build();

            /**
             * 界面参数
             */
            VideoSessionCreateInfo create_info = new VideoSessionCreateInfo.Builder()
                    .setOutputDurationLimit(Contant.DEFAULT_DURATION_MAX_LIMIT_TWO)
                    .setOutputDurationMin(Contant.DEFAULT_DURATION_LIMIT_MIN_TWO)
                    .setMovieExportOptions(movie_options)
                    .setWaterMarkPath(Contant.WATER_MARK_PATH)
                    .setWaterMarkPosition(1)
                    .setBeautyProgress(beautySkinProgress)
                    .setBeautySkinOn(false)
                    .setCameraFacing(
                            Camera.CameraInfo.CAMERA_FACING_BACK)
                    .setVideoSize(400, 250)
                    .setCaptureHeight(view.getContext().getResources().getDimension(com.hewuzhe.R.dimen.qupai_recorder_capture_height_size))
                    .setBeautySkinViewOn(false)
                    .setFlashLightOn(true)
                    .setTimelineTimeIndicator(true)
                    .build();

            _CreateInfo.setSessionCreateInfo(create_info);
            _CreateInfo.setNextIntent(null);
            _CreateInfo.setOutputThumbnailSize(360, 640);//输出图片宽高
            videoPath = FileUtils.newOutgoingFilePath(view.getContext());
            _CreateInfo.setOutputVideoPath(videoPath);//输出视频路径
            _CreateInfo.setOutputThumbnailPath(videoPath + ".png");//输出图片路径

            QupaiServiceImpl qupaiService = new QupaiServiceImpl.Builder()
                    .setEditorCreateInfo(_CreateInfo).build();
//            qupaiService.showRecordPage((Activity) view.getContext(), QUPAI_RECORD_REQUEST);
        Intent intent = new Intent(this.getContext(), VideoActivity.class);
        intent.setData(this._CreateInfo.getFileUri());
        this._CreateInfo.putExtra(intent);
        intent = SessionClientActivityModule.apply(intent, EditorCreateInfo.SessionClientFactoryImpl.class, this._CreateInfo.get_SessionCreateInfo());
        startActivityForResult(intent, QUPAI_RECORD_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==-1&&requestCode==102){
            EditorResult result = new EditorResult(data);
            String path = result.getPath();//"file://"+
            pic = result.getThumbnail()[0];
            Bitmap bmp = BitmapFactory.decodeFile(pic);
//            Bitmap bmp1 = BitmapFactory.decodeFile("/storage/emulated/0/Android/data/com.hewuzhe/files/qupai_simple_workspace/Play.png");
            Bitmap bmp1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.rc_bg_play);
            String picName = pic.substring(pic.lastIndexOf("/")+1,pic.lastIndexOf("."))+"_play.png";
            try {
                saveBitmap(mergeBitmap(bmp, bmp1),picName);
            } catch (IOException e) {
                return;
            }

//            sendVideoMessage(pic, path, Conversation.ConversationType.PRIVATE, ider);
//            sendVideoMessage(pic,C.BASE_URL+path,Conversation.ConversationType.PRIVATE,"86");
            uploadVideoPresenter.UpLoadVideo(path);
        }
    }

    /**
     * 发送图片消息上传到服务器
     * @param firstImgPath  视频第一帧画面的本地 path
     * @param videoPath     视频存储的本地路径
     * @param mConversationType  会话类型
     * @param targetId  目标id
     */
    public void sendVideoMessage(final String firstImgPath,final String videoPath, Conversation.ConversationType mConversationType,String targetId) {
        if (TextUtils.isEmpty(firstImgPath)) {
            new RuntimeException("firstImgPath is null");
            return;
        }

        File f = new File(firstImgPath);
        if (!f.exists()) {
            new RuntimeException("image file is null");
            return;
        }
        if (TextUtils.isEmpty(videoPath)) {
            new RuntimeException("videoPath is null");
            return;
        }
        if (mConversationType == null) {
            new RuntimeException("ConversationType is null");
            return;
        }
        if (TextUtils.isEmpty(targetId)) {
            new RuntimeException("targetId is null");
            return;
        }
//        ImageMessage imgMsg = ImageMessage.obtain(Uri.parse("file://" + firstImgPath), Uri.parse("file://" + firstImgPath));
//
///**
// * 发送图片消息。
// *
// * @param type     会话类型。
// * @param targetId 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
// * @param content  消息内容。
// * @param callback 发送消息的回调。
// */
//        RongIM.getInstance().getRongIMClient().sendImageMessage(Conversation.ConversationType.PRIVATE, "9517", imgMsg, "", new RongIMClient.SendImageMessageCallback() {
//
//            @Override
//            public void onAttached(Message message) {
//                //保存数据库成功
//            }
//
//            @Override
//            public void onError(Message message, RongIMClient.ErrorCode code) {
//                //发送失败
//            }
//
//            @Override
//            public void onSuccess(Message message) {
//                //发送成功
//            }
//
//            @Override
//            public void onProgress(Message message, int progress) {
//                //发送进度
//            }
//        });


        ImageMessage imageMessage = ImageMessage.obtain(Uri.parse("file://" + firstImgPath), Uri.parse("file://" + firstImgPath));
        Message message = Message.obtain(targetId, mConversationType, imageMessage);
        RongIM.getInstance().getRongIMClient().sendImageMessage(message, null, null, new RongIMClient.SendImageMessageWithUploadListenerCallback() {
            public void onAttached(final Message message, final RongIMClient.uploadImageStatusListener watcher) {
                message.setSentStatus(Message.SentStatus.SENDING);
                RongIMClient.getInstance().setMessageSentStatus(message.getMessageId(), message.getSentStatus());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        ImageMessage img = (ImageMessage) message.getContent();
                        img.setLocalUri(Uri.parse(("file://" + firstImgPath)));
                        img.setExtra("123456;" + videoPath);

                        Message msg = Message.obtain(message.getTargetId(), message.getConversationType(), img);
                        RongIMClient.getInstance().uploadMedia(msg, new RongIMClient.UploadMediaCallback() {
                            @Override
                            public void onProgress(Message message, int progress) {
                                watcher.update(progress);
                            }

                            @Override
                            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                                watcher.error();
                            }

                            @Override
                            public void onSuccess(Message message) {
                                watcher.success();
                            }
                        });//
                    }
                };
                new Handler().post(runnable);
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode code) {

            }

            @Override
            public void onSuccess(Message message) {
                message.setSentStatus(Message.SentStatus.SENT);
                RongIMClient.getInstance().setMessageSentStatus(message.getMessageId(), message.getSentStatus());
            }

            @Override
            public void onProgress(Message message, int progress) {

            }
        });
    }


    @Override
    public void sendMessage(String path) {
            sendVideoMessage(picSrc,C.BASE_URL+path,type.equals("group")?Conversation.ConversationType.GROUP:Conversation.ConversationType.PRIVATE,ider);

    }

    //合并图片
    private Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(),
                firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, firstBitmap.getWidth()/2-39, firstBitmap.getHeight()/2-40, null);
        return bitmap;
    }
    private void saveBitmap(Bitmap bitmap,String bitName) throws IOException
    {
        picSrc = "/storage/emulated/0/Android/data/com.hewuzhe/files/qupai_simple_workspace/"+bitName;
        File file = new File("/storage/emulated/0/Android/data/com.hewuzhe/files/qupai_simple_workspace/"+bitName);
        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 90, out))
            {
                out.flush();
                out.close();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}