package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;

import butterknife.Bind;

public class LiveVideoActivity extends ToolBarActivity {


    @Bind(R.id.webview)
    WebView _Webview;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_live_video;
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

        _Webview.getSettings().setJavaScriptEnabled(true);
        _Webview.setWebViewClient(new WebViewClient() { //通过_Webview打开链接，不调用系统浏览器

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        _Webview.setInitialScale(25);

        WebSettings webSettings = _Webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);

        _Webview.getSettings().setUseWideViewPort(true);
        _Webview.getSettings().setLoadWithOverviewMode(true);
//      _Webview.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);


        _Webview.loadUrl("file:///android_asset/livevideo.html");

    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }


    @Override
    protected String provideTitle() {
        return "视频直播";
    }


}
