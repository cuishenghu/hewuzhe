package com.hewuzhe.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.duanqu.qupai.android.app.QupaiDraftManager;
import com.duanqu.qupai.android.app.QupaiServiceImpl;
import com.duanqu.qupai.editor.EditorResult;
import com.duanqu.qupai.engine.session.MovieExportOptions;
import com.duanqu.qupai.engine.session.VideoSessionCreateInfo;
import com.duanqu.qupai.recorder.EditorCreateInfo;
import com.hewuzhe.R;
import com.hewuzhe.model.Comment;
import com.hewuzhe.model.FriendCondition;
import com.hewuzhe.model.Res;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.FriendConditionPresenter;
import com.hewuzhe.presenter.adapter.ConditionPresenter;
import com.hewuzhe.ui.adapter.FriendConditionAdapter;
import com.hewuzhe.ui.base.SwipeRecycleViewActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.cons.Contant;
import com.hewuzhe.ui.cons.FileUtils;
import com.hewuzhe.ui.fragment.PowerFragment;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.FriendsConditionView;
import com.socks.library.KLog;

import java.util.ArrayList;

import butterknife.Bind;
import cn.xm.weidongjian.popuphelper.PopupWindowHelper;

public class FriendsConditionActivity extends SwipeRecycleViewActivity<FriendConditionPresenter, FriendConditionAdapter, FriendCondition> implements FriendsConditionView {


    @Bind(R.id.edt_comment)
    EditText _EdtComment;
    @Bind(R.id.btn_publish)
    Button _BtnPublish;
    @Bind(R.id.lay_comment)
    LinearLayout _LayComment;

    private ImageView _ImgBg;
    private LinearLayout _LayMsg;
    private ImageView _ImgMsgAvatar;
    private TextView _TvMsgCount;
    private TextView _TvUsername;
    private ImageView _ImgAvatar;


    private TextView action_1;
    private TextView action_2;
    private User user;
    private boolean isFirstRun = true;
    private int beautySkinProgress;
    private final EditorCreateInfo _CreateInfo = new EditorCreateInfo();
    private String videoPath;
    private View header;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_friends_condition;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    /**
     * 绑定Presenter
     */
    @Override
    public FriendConditionPresenter createPresenter() {
        return new FriendConditionPresenter();
    }

    @Override
    protected String provideTitle() {
        return "武者动态";
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void action() {
        super.action();

        View view = LayoutInflater.from(this).inflate(R.layout.popview, null);
        final PopupWindowHelper popupWindowHelper = new PopupWindowHelper(view);

        action_1 = (TextView) view.findViewById(R.id.tv_local_video);
        action_2 = (TextView) view.findViewById(R.id.tv_take_video);
        action_1.setText("发动态");
        action_2.setText("小视频");
        action_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(PublishConditionActivity.class, 102);
                popupWindowHelper.dismiss();
            }
        });

        action_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              startActivity(FFmpegRecorderActivity.class, new Bun().putInt(C.WHITCH, C.WHITCH_ONE).ok());
                startRecordActivity();
                popupWindowHelper.dismiss();
            }
        });

        popupWindowHelper.showAsDropDown(imgAction);
    }


    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        initHeader();
        initBasicInfo();
        presenter.getData(page, count);
        presenter.GetNoReadCommentNumByUserId();

//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                KLog.d("更新未读条数");
//            }
//        }, 1000, 1000);

    }

    private void initHeader() {
        _ImgBg = (ImageView) header.findViewById(R.id.img_bg);
        _LayMsg = (LinearLayout) header.findViewById(R.id.lay_msg);
        _ImgMsgAvatar = (ImageView) header.findViewById(R.id.img_msg_avatar);
        _TvMsgCount = (TextView) header.findViewById(R.id.tv_msg_count);
        _TvUsername = (TextView) header.findViewById(R.id.tv_username);
        _ImgAvatar = (ImageView) header.findViewById(R.id.img_avatar);

    }

    private void initBasicInfo() {
        user = new SessionUtil(getContext()).getUser();
        if (user != null) {
            Glide.with(getContext())
                    .load(C.BASE_URL + user.PhotoPath)
                    .centerCrop()
                    .crossFade()
                    .into(_ImgAvatar);
            _TvUsername.setText(user.NicName);

            _ImgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(MyConditionActivity.class, new Bun().putString("title", "我的动态").putInt("id", new SessionUtil(getContext()).getUserId()).ok());
                }
            });
        }


    }

    @Override
    public void bindData(ArrayList<FriendCondition> data) {
        bd(data);
    }


    /**
     * @return 提供Adapter
     */
    @Override
    protected FriendConditionAdapter provideAdapter() {
        header = getLayoutInflater().inflate(R.layout.header_friend_condtions, null);
        return new FriendConditionAdapter(getContext(), presenter, header);
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, FriendCondition item) {
        startActivity(ConditionDetialTwoActivity.class, new Bun().putInt("id", item.Id).putInt("whitch", C.WHITCH_ONE).ok());
//        startActivity(ConditionDetialActivity.class, new Bun().putP("item", item).putInt("whitch", C.WHITCH_ONE).ok());
    }


    /**
     * 点赞收藏等
     *
     * @param b
     * @param flag
     * @param position
     */
    @Override
    public void collectAndOther(boolean b, int flag, int position) {
        if (flag == ConditionPresenter.PRAISE) {
            if (b) {
                adapter.data.get(position).LikeNum += 1;
                adapter.data.get(position).IsLike = true;
                adapter.notifyItemChanged(position);
            }
        }

    }

    /**
     * 显示评论框
     *
     * @param id
     * @param position
     * @param view
     */
    @Override
    public void showCommentInput(final int id, final int position, View view) {
        _LayComment.setVisibility(View.VISIBLE);
        _EdtComment.requestFocus();
        _EdtComment.setHint("");
        showSoftInput(_EdtComment);
        _BtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.publisComment(id, _EdtComment.getText().toString().trim(), view, position);
            }
        });
    }

    /**
     * 评论成功
     *
     * @param position
     */
    @Override
    public void commentSuccess(int position, Comment comment) {
        _EdtComment.setText("");
        user = new SessionUtil(getContext()).getUser();
        FriendCondition condition = adapter.data.get(position);
        comment.CommentedNicName = condition.NicName;
        comment.CommentedId = condition.UserId;
        comment.NicName = user.NicName;
        comment.CommenterId = new SessionUtil(getContext()).getUserId();
        comment.ParentId = 0;
        condition.ComList.add(comment);
//        adapter.notifyItemChanged(position);
        adapter.notifyDataSetChanged();
    }

    /**
     * 回复评论
     *
     * @param id
     * @param nicName
     * @param position
     * @param view
     */
    @Override
    public void showReplyInput(final int id, final String nicName, final int commenterId, final int position, View view) {
        _LayComment.setVisibility(View.VISIBLE);
        _EdtComment.requestFocus();
        showSoftInput(_EdtComment);
        _EdtComment.setHint("回复 " + nicName + ":");
        _BtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.publisReply(id, nicName, commenterId, _EdtComment.getText().toString().trim(), view, position);
            }
        });
    }

    /**
     * 回复成功
     *
     * @param position
     * @param comment
     */
    @Override
    public void replySuccess(int position, Comment comment) {
        _EdtComment.setText("");
        user = new SessionUtil(getContext()).getUser();
        FriendCondition condition = adapter.data.get(position);
        comment.NicName = user.NicName;
        comment.CommenterId = new SessionUtil(getContext()).getUserId();

        condition.ComList.add(comment);
//      adapter.notifyItemChanged(position);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onscroll(RecyclerView recyclerView, int dx, int dy) {
        super.onscroll(recyclerView, dx, dy);
        _LayComment.setVisibility(View.GONE);
        hideSoftMethod(_EdtComment);
    }

    @Override
    public Integer getData() {
        return null;
    }

    @Override
    public void setUserData(User data) {

    }

    @Override
    public void setDataStatus(int page, int count, Res<ArrayList<FriendCondition>> res) {

    }


    @Override
    public void updateFriendNoReadNum(final int count, String data) {

        if (count > 0) {
            _LayMsg.setVisibility(View.VISIBLE);
            _TvMsgCount.setText(count + "条新消息");

            Glide.with(getContext())
                    .load(C.BASE_URL + data)
                    .fitCenter()
                    .placeholder(R.mipmap.img_avatar)
                    .crossFade()
                    .into(_ImgMsgAvatar);

            _LayMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(CondtionCommetnsActivity.class);
                }
            });

        } else {
            _LayMsg.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstRun) {
            isFirstRun = false;
        } else {
            presenter.GetNoReadCommentNumByUserId();
        }
    }


    @Override
    public void deleteConditionSuccess(int position) {
        adapter.data.remove(position);
        adapter.notifyDataSetChanged();
    }


    private void startRecordActivity() {
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
                .setOutputDurationLimit(Contant.DEFAULT_DURATION_MAX_LIMIT)
                .setOutputDurationMin(Contant.DEFAULT_DURATION_LIMIT_MIN)
                .setMovieExportOptions(movie_options)
                .setWaterMarkPath(Contant.WATER_MARK_PATH)
                .setWaterMarkPosition(1)
                .setBeautyProgress(beautySkinProgress)
                .setBeautySkinOn(false)
                .setCameraFacing(
                        Camera.CameraInfo.CAMERA_FACING_BACK)
                .setVideoSize(360, 640)
                .setCaptureHeight(getResources().getDimension(R.dimen.qupai_recorder_capture_height_size))
                .setBeautySkinViewOn(false)
                .setFlashLightOn(true)
                .setTimelineTimeIndicator(false)
                .build();

        _CreateInfo.setSessionCreateInfo(create_info);
        _CreateInfo.setNextIntent(null);
        _CreateInfo.setOutputThumbnailSize(360, 640);//输出图片宽高
        videoPath = FileUtils.newOutgoingFilePath(this);
        _CreateInfo.setOutputVideoPath(videoPath);//输出视频路径
        _CreateInfo.setOutputThumbnailPath(videoPath + ".png");//输出图片路径

        QupaiServiceImpl qupaiService = new QupaiServiceImpl.Builder()
                .setEditorCreateInfo(_CreateInfo).build();
        qupaiService.showRecordPage(this, PowerFragment.QUPAI_RECORD_REQUEST);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == PowerFragment.QUPAI_RECORD_REQUEST) {
            EditorResult result = new EditorResult(data);
            //得到视频path，和缩略图path的数组，返回十张缩略图,和视频时长
            String path = result.getPath();
            result.getThumbnail();
            result.getDuration();

            //开始上传，上传前请务必确认已调用授权接口
//           startUpload();

            //删除草稿
            QupaiDraftManager draftManager = new QupaiDraftManager();
            draftManager.deleteDraft(data);

            KLog.d(path);
            Intent intent = new Intent(this, PublishConditionVideoActivity.class);
            intent.putExtra("data", new Bun().putString("file_name", path).putInt("uploadType", C.UPLOAD_TYPE_RECORD).ok());
            startActivityForResult(intent, 102);

        } else if (resultCode == Activity.RESULT_OK && requestCode == 102) {
            snb("发布成功", recyclerView);

            page = 1;
            refresh(true);
            presenter.getData(page, count);
        }
    }
}
