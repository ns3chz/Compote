package cn.hu.zc.compote.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import cn.hu.zc.basilic.activity.BaseFragmentActivity;
import cn.hu.zc.compote.api.common.model.news.ModelNews;
import cn.hu.zc.compote.fragment.CommonWebFragment;
import cn.hu.zc.manager.ActivityManager;

public class CommonWebActivity extends BaseFragmentActivity {
    public static final String KEY_MODEL_URL = "key_model_url";
    public static final String KEY_WEB_TYPE = "key_web_type";//打开类型
    public static final int WEB_TYPE_NEWS = 1;//新闻类型，不支持JS
    public static final int WEB_TYPE_DEF = 2;//普通类型

    public static void openNews(@NonNull Activity activity, String url, View shareView, String shareElementName) {
        Intent intent = new Intent(activity, CommonWebActivity.class);
        intent.putExtra(KEY_MODEL_URL, url);
        intent.putExtra(KEY_WEB_TYPE, WEB_TYPE_NEWS);
        ActivityManager.startActivity(activity, intent, shareView, shareElementName);
    }
    public static void openDef(@NonNull Activity activity, String url, View shareView, String shareElementName) {
        Intent intent = new Intent(activity, CommonWebActivity.class);
        intent.putExtra(KEY_MODEL_URL, url);
        intent.putExtra(KEY_WEB_TYPE, WEB_TYPE_DEF);
        ActivityManager.startActivity(activity, intent, shareView, shareElementName);
    }

    @Override
    public Fragment setContentFragment() {
        return new CommonWebFragment();
    }
}
