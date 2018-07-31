package cn.hu.zc.compote.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import cn.hu.zc.basilic.activity.BaseFragmentActivity;
import cn.hu.zc.compote.fragment.DownloadFragment;
import cn.hu.zc.manager.ActivityManager;

public class DownloadActivity extends BaseFragmentActivity {

    /**
     */
    public static void open(Activity activity, View targetView, String transitionName) {
        Intent intent = new Intent(activity, DownloadActivity.class);
        ActivityManager.startActivity(activity, intent, targetView, transitionName);
    }

    @Override
    public Fragment setContentFragment() {
        return new DownloadFragment();
    }
}
