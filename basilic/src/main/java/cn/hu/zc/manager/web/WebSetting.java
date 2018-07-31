package cn.hu.zc.manager.web;

import android.os.Build;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import cn.hu.zc.tools.NetTools;

public class WebSetting {
    public static final String CACAHE_WEB_DIRNAME = "webcache";

    public static void setDefParams(WebView webView) {
        if (webView == null) return;
        WebSettings settings = webView.getSettings();
        if (settings != null) {
            settings.setJavaScriptEnabled(true);
            //支持插件
            settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
            //设置自适应屏幕，两者合用
            settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
            settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
            //缩放操作
            settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
            settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
            settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
            //其他细节操作
            if (NetTools.isNetworkConnected(webView.getContext()) == null) {
                //无网络时从本地取
                settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            } else {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            }
            //缓存模式如下：
            //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
            //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
            //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
            //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
            settings.setAllowFileAccess(true); //设置可以访问文件
            settings.setJavaScriptCanOpenWindowsAutomatically(false); //支持通过JS打开新窗口
            settings.setLoadsImagesAutomatically(true); //支持自动加载图片
            settings.setDefaultTextEncodingName("utf-8");//设置编码格式

            settings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
            settings.setDatabaseEnabled(true);   //开启 database storage API 功能
            settings.setAppCacheEnabled(true);//开启 Application Caches 功能

            String cacheDirPath = webView.getContext().getFilesDir().getAbsolutePath() + CACAHE_WEB_DIRNAME;
            settings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
            settings.setAppCacheMaxSize(Long.MAX_VALUE);
            // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }

        }
    }

    public static void setNewsParam(WebView webView) {
        if (webView == null) return;
        WebSettings settings = webView.getSettings();
        if (settings != null) {
            //支持插件
            settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
            //设置自适应屏幕，两者合用
            settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
            settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
            //缩放操作
            settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
            settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
            settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
            //其他细节操作
            if (NetTools.isNetworkConnected(webView.getContext()) == null) {
                //无网络时从本地取
                settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            } else {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            }
            //缓存模式如下：
            //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
            //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
            //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
            //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
            settings.setAllowFileAccess(true); //设置可以访问文件
            settings.setJavaScriptCanOpenWindowsAutomatically(false); //支持通过JS打开新窗口
            settings.setLoadsImagesAutomatically(true); //支持自动加载图片
            settings.setDefaultTextEncodingName("utf-8");//设置编码格式

            settings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
            settings.setDatabaseEnabled(true);   //开启 database storage API 功能
            settings.setAppCacheEnabled(true);//开启 Application Caches 功能

            String cacheDirPath = webView.getContext().getFilesDir().getAbsolutePath() + CACAHE_WEB_DIRNAME;
            settings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
            settings.setAppCacheMaxSize(Long.MAX_VALUE);
            // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }

        }
    }


    /**
     * 界面不可见时，停用webview刷新和动画
     */
    public static void onPause(WebView webView) {
        if (webView == null) return;
        webView.onPause();
        WebSettings settings = webView.getSettings();
        if (settings != null) {
            settings.setJavaScriptEnabled(false);
        }
    }

    /**
     * 界面重新可见时
     */
    public static void onResume(WebView webView) {
        if (webView == null) return;
        webView.onResume();
        WebSettings settings = webView.getSettings();
        if (settings != null) {
            settings.setJavaScriptEnabled(true);
        }
    }

    /**
     * 界面重新可见时
     */
    public static void onResume(WebView webView, boolean hasJs) {
        if (webView == null) return;
        webView.onResume();
        WebSettings settings = webView.getSettings();
        if (settings != null && hasJs) {
            settings.setJavaScriptEnabled(true);
        }
    }

    /**
     * @param webView ondestry
     */
    public static void onDestroy(WebView webView) {
        if (webView == null) return;
        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        ViewParent parent = webView.getParent();
        if (parent != null && parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(webView);
        }
        webView.destroy();
    }
}
