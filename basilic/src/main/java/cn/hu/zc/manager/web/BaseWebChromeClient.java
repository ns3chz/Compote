package cn.hu.zc.manager.web;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import cn.hu.zc.tools.Toaster;

/**
 * 辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等。
 */
public class BaseWebChromeClient extends WebChromeClient {


    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        //获得网页的加载进度并显示
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        //获取Web页中的标题
        super.onReceivedTitle(view, title);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        if (view != null) {
            showJSAlertDialog(view.getContext(), message, result);
        }
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        // 支持javascript的确认框
        if (view != null) {
            showJSAlertDialog(view.getContext(), message, result);
        }
        return super.onJsConfirm(view, url, message, result);
    }

    private void showJSAlertDialog(Context context, String message, final JsResult jsResult) {
        if (context == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (jsResult != null) {
                    jsResult.confirm();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (jsResult != null) {
                    jsResult.cancel();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}
