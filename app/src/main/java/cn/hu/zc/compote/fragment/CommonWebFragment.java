package cn.hu.zc.compote.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import cn.hu.zc.basilic.fragment.BaseFragment;
import cn.hu.zc.basilic.view.MyWebView;
import cn.hu.zc.compote.R;
import cn.hu.zc.compote.activity.CommonWebActivity;
import cn.hu.zc.manager.web.BaseWebChromeClient;
import cn.hu.zc.manager.web.BaseWebViewClient;
import cn.hu.zc.manager.web.WebSetting;
import cn.hu.zc.ratioview.RatioImageView;
import cn.hu.zc.tools.Toaster;

public class CommonWebFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.webview)
    MyWebView webview;
    @BindView(R.id.iv_back)
    RatioImageView ivBack;
    @BindView(R.id.iv_from)
    ImageView ivFrom;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.ll_actonbar)
    LinearLayout llActonbar;
    @BindView(R.id.riv_last_page)
    RatioImageView rivLastPage;
    @BindView(R.id.riv_next_page)
    RatioImageView rivNextPage;
    @BindView(R.id.riv_share_page)
    RatioImageView rivSharePage;
    @BindView(R.id.ll_control)
    LinearLayout llControl;
    @BindView(R.id.progressbar_web)
    ContentLoadingProgressBar progressBarWeb;
    private String webUrl;
    private int webType;

    @Override
    public int setLayoutRes() {
        return R.layout.fragment_common_web;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        initData();
        setWebParams();
    }

    private void initData() {
        Intent intent = getActivity().getIntent();
        if (intent == null) return;
        webUrl = (String) intent.getSerializableExtra(CommonWebActivity.KEY_MODEL_URL);
        webType = (int) intent.getSerializableExtra(CommonWebActivity.KEY_WEB_TYPE);
        //
    }

    @Override
    public void listener() {
        ivBack.setOnClickListener(this);
        ivMore.setOnClickListener(this);
        rivLastPage.setOnClickListener(this);
        rivNextPage.setOnClickListener(this);
        rivSharePage.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        loadUrl(webUrl);
    }

    private void setWebParams() {
        switch (webType) {
            case CommonWebActivity.WEB_TYPE_NEWS:
                WebSetting.setNewsParam(webview);
                webview.setOnScrollChangedListener(new MyWebView.OnScrollChangedListener() {

                    @Override
                    public void onScrollChange(View view, int left, int top, int oldLeft, int oldTop) {
                        Log.e("onScrollChange", left + " , " + top + " , " + oldLeft + " , " + oldTop);
                        if (top < 300 && llActonbar.isShown()) {
                            llActonbar.setVisibility(View.GONE);
                        } else if (top >= 300 && !llActonbar.isShown()) {
                            llActonbar.setVisibility(View.VISIBLE);
                        }
                    }
                });
                break;
            case CommonWebActivity.WEB_TYPE_DEF:
                WebSetting.setDefParams(webview);
                webview.setOnScrollChangedListener(new MyWebView.OnScrollChangedListener() {
                    @Override
                    public void onScrollChange(View view, int left, int top, int oldLeft, int oldTop) {
                        Log.e("onScrollChange", left + " , " + top + " , " + oldLeft + " , " + oldTop);
                        if (top - oldTop >= 0) {
                            //下滑隐藏
                            if (llControl.getVisibility() != View.GONE) {
                                llControl.setVisibility(View.GONE);
                            }
                        } else if (oldTop - top > 10) {
                            //上滑显示
                            if (llControl.getVisibility() != View.VISIBLE) {
                                llControl.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
                break;
        }
        webview.setWebViewClient(new BaseWebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e("onPageStarted", "onPageStarted");
                if (progressBarWeb != null) {
                    progressBarWeb.setProgress(0);
                    progressBarWeb.show();
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("onPageFinished", "onPageFinished");
                if (progressBarWeb != null) {
                    progressBarWeb.hide();
                }
                super.onPageFinished(view, url);
            }
        });
        webview.setWebChromeClient(new BaseWebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (tvFrom != null && webType == CommonWebActivity.WEB_TYPE_NEWS) {
                    tvFrom.setText(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (progressBarWeb != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        progressBarWeb.setProgress(newProgress, true);
                    } else {
                        progressBarWeb.setProgress(newProgress);
                    }
                }
            }
        });
    }

    private void loadUrl(String url) {
        if (webview != null) {
            webview.loadUrl(url);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_more:
                Toaster.toast(getContext(), "更多");
                break;
            case R.id.riv_last_page:
                Toaster.toast(getContext(), "上一页");
                if (webview != null) {
                    if (webview.canGoBack()) {
                        webview.goBack();
                    } else {
                        Toaster.toast(getContext(), "没有上一页");
                    }
                }
                break;
            case R.id.riv_next_page:
                Toaster.toast(getContext(), "下一页");
                if (webview != null) {
                    if (webview.canGoForward()) {
                        webview.goForward();
                    } else {
                        Toaster.toast(getContext(), "没有下一页");
                    }
                }
                break;
            case R.id.riv_share_page:
                Toaster.toast(getContext(), "分享");
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && webview != null && webview.canGoBack()) {
//            webview.goBack();
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (webType) {
            case CommonWebActivity.WEB_TYPE_NEWS:
                WebSetting.onResume(webview, false);
                break;
            case CommonWebActivity.WEB_TYPE_DEF:
                WebSetting.onResume(webview, true);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        WebSetting.onPause(webview);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WebSetting.onDestroy(webview);
    }

}
