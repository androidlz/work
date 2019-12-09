package com.seventeenok.test.webview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.seventeenok.test.R;


public class ProgressWebviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_webview);
//        url = getIntent().getStringExtra("url");
        url = "https://www.jianshu.com/p/65a2fa573e56";
        init();
    }

    private WebView mWebView;
    private ProgressBar web_bar;
    private String url;
    private WebSettings webSettings;
    

    private void init() {
        //Webview
        mWebView = findViewById(R.id.web_view);
        //进度条
        web_bar = findViewById(R.id.web_bar);
        //设置进度条颜色
        web_bar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

        //对WebView进行必要配置
        settingWebView();
        settingWebViewClient();

        //加载url地址
        mWebView.loadUrl(url);
    }

    /**
     * 对 webview 进行必要的配置
     */
    private void settingWebView() {
        webSettings = mWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //没有网络时加载缓存
        //webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        //不加的话有些网页加载不出来，是空白
        webSettings.setDomStorageEnabled(true);

        //Android 5.0及以上版本使用WebView不能存储第三方Cookies解决方案
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    /**
     * 设置 WebViewClient 和 WebChromeClient
     */
    private void settingWebViewClient() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("aa","onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("aa","onPageFinished");
            }

            // 链接跳转都会走这个方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("aa","url: ="+ url);
                view.loadUrl(url);// 强制在当前 WebView 中加载 url
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d("aa","current progress: " + newProgress);
                //更新进度条
                web_bar.setProgress(newProgress);

                if (newProgress == 100) {
                    web_bar.setVisibility(View.GONE);
                } else {
                    web_bar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d("aa","标题：" + title);
            }
        });
    }


    /**
     * 同步 webview 的Cookie
     */
    private void syncCookie(String url) {
        boolean b = CookieUtils.syncCookie(url);
        Log.d("aa","设置 cookie 结果： " + b);
    }

    /**
     * 对安卓返回键的处理。如果webview可以返回，则返回上一页。如果webview不能返回了，则退出当前webview
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("aa","onResume " + url);
        //同步 cookie 到 webview
        syncCookie(url);
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        webSettings.setJavaScriptEnabled(false);
    }
}


