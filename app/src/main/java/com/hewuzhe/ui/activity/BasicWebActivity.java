package com.hewuzhe.ui.activity;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.ToolBarActivity;

import butterknife.Bind;


public class BasicWebActivity extends ToolBarActivity {

    @Bind(R.id.web_progress)
    ProgressBar _WebProgress;
    @Bind(R.id.pay_web)
    WebView _PayWeb;

    /**
     * @return 提供LayoutId
     */
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_basic_web;
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

        String url = getIntentData().getString("url");
        _PayWeb.setWebViewClient(new WebViewClient() { // 通过webView打开链接，不调用系统浏览器

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        _PayWeb.setInitialScale(25);
        WebSettings webSettings = _PayWeb.getSettings();
        webSettings.setJavaScriptEnabled(true);
//		webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);

        _PayWeb.getSettings().setUseWideViewPort(true);
        _PayWeb.getSettings().setLoadWithOverviewMode(true);

        _PayWeb.loadUrl(url);

        WebChromeClient webChromeClient = new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String str) {
                super.onReceivedTitle(view, str);
//                tvTitle.setText(str);
            }
        };
        _PayWeb.setWebChromeClient(webChromeClient);

    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (_PayWeb != null) {
            _PayWeb.stopLoading();
            _PayWeb.destroy();
            _PayWeb = null;
        }
        this.finish();

    }

    /**
     * @return 提供标题
     */
    @Override
    protected String provideTitle() {
        return getIntentData().getString("title");
    }

}
