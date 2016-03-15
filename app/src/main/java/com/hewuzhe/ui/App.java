package com.hewuzhe.ui;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.duanqu.qupai.upload.AuthService;
import com.duanqu.qupai.upload.QupaiAuthListener;
import com.easefun.polyvsdk.PolyvSDKClient;
import com.easefun.polyvsdk.server.AndroidService;
import com.facebook.stetho.Stetho;
import com.hewuzhe.BuildConfig;
import com.hewuzhe.R;
import com.hewuzhe.ui.cons.Contant;
import com.hewuzhe.ui.inter.OnLocListener;
import com.hewuzhe.utils.GlideLoader;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.pgyersdk.crash.PgyCrashManager;
import com.socks.library.KLog;
import com.yancy.imageselector.ImageConfig;

import org.litepal.LitePalApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;

/**
 * Created by xianguangjin on 15/12/14.
 */
public class App extends Application {


    public LinkedList<OnLocListener> onLocListeners = new LinkedList<>();
    public static ImageConfig imageConfig;

    private static final String AUTHTAG = "QupaiAuth";

    /**
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
//        ActiveAndroid.initialize(this);
        LitePalApplication.initialize(this);


        initAuth(getApplicationContext(), Contant.appkey, Contant.appsecret, Contant.space);

        initImageLoader();//初始化ImageLoader\
//      Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(
//                                Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(
//                                Stetho.defaultInspectorModulesProvider(this))
//                        .build());


        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());

        /**
         * KLog初始化
         * */
        KLog.init(BuildConfig.DEBUG);
        /**
         * 极光推送初始化
         * */
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);


        /**
         * 视频直播初始化
         * */
        initPolyvCilent();

        PgyCrashManager.register(this);

        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (

                getApplicationInfo()

                        .packageName.equals(

                        getCurProcessName(getApplicationContext()

                        )) ||
                        "io.rong.push".

                                equals(getCurProcessName(getApplicationContext()

                                )))

        {
            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);


        }

        imageConfig
                = new ImageConfig.Builder(new
                GlideLoader()
        )
                .

                        steepToolBarColor(getResources()

                                        .

                                                getColor(R.color.colorPrimary)

                        )
                .

                        titleBgColor(getResources()

                                        .

                                                getColor(R.color.colorPrimary)

                        )
                .

                        titleSubmitTextColor(getResources()

                                        .

                                                getColor(R.color.white)

                        )
                .

                        titleTextColor(getResources()

                                        .

                                                getColor(R.color.white)

                        )
                                // 开启多选   （默认为多选）
                .

                        mutiSelect()
                                // 多选时的最大数量   （默认 9 张）
                .

                        mutiSelectMaxSize(9)
                                // 开启拍照功能 （默认关闭）
                .

                        showCamera()
                                // 已选择的图片路径
                .

                        pathList(new ArrayList<String>()

                        )
                                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .

                        filePath("/ImageSelector/Pictures")

                .

                        build();

    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /**
     * 鉴权 建议只调用一次,在Application调用。在demo里面为了测试调用了多次 得到accessToken
     *
     * @param context
     * @param appKey    appkey
     * @param appsecret appsecret
     * @param space     space
     */
    private void initAuth(Context context, String appKey, String appsecret, String space) {
        AuthService service = AuthService.getInstance();
        service.setQupaiAuthListener(new QupaiAuthListener() {
            @Override
            public void onAuthError(int errorCode, String message) {
                Log.e(AUTHTAG, "ErrorCode" + errorCode + "message" + message);
            }

            @Override
            public void onAuthComplte(int responseCode, String responseMessage) {
                Contant.accessToken = responseMessage;
            }
        });
        service.startAuth(context, appKey, appsecret, space);
    }

    /**
     * 初始化ImageLoader
     */
    protected void initImageLoader() {
        //初始化ImageLoader
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();

        ImageLoader.getInstance().init(config.build());
    }

    /**
     * 初始化Live直播
     */
    public void initPolyvCilent() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File saveDir = new File(Environment.getExternalStorageDirectory().getPath() + "/polyvdownload");
            if (!saveDir.exists())
                saveDir.mkdir();
        }

        PolyvSDKClient client = PolyvSDKClient.getInstance();
        client.initCrashReport(getApplicationContext());
        client.startService(getApplicationContext(), AndroidService.class);
    }
}
