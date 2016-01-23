package com.hewuzhe.ui;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.hewuzhe.BuildConfig;
import com.hewuzhe.R;
import com.hewuzhe.ui.inter.OnLocListener;
import com.hewuzhe.utils.GlideLoader;
import com.socks.library.KLog;
import com.yancy.imageselector.ImageConfig;

import java.util.LinkedList;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;

/**
 * Created by xianguangjin on 15/12/14.
 */
public class App extends Application {


    public LinkedList<OnLocListener> onLocListeners = new LinkedList<>();
    public static ImageConfig imageConfig;


    /**
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();


//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(
//                                Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(
//                                Stetho.defaultInspectorModulesProvider(this))
//                        .build());


        /**
         * KLog初始化
         * */
        KLog.init(BuildConfig.DEBUG);
        /**
         * 极光推送初始化
         * */
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);


//        PgyCrashManager.register(this);

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
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.colorPrimary))
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                        // 开启多选   （默认为多选）
                .mutiSelect()
                        // 多选时的最大数量   （默认 9 张）
                .mutiSelectMaxSize(6)
                        // 开启拍照功能 （默认关闭）
                .showCamera()
                        // 已选择的图片路径
//                .pathList(path)
                        // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();
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
}
