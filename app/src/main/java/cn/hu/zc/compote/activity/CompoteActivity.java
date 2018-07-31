package cn.hu.zc.compote.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import cn.hu.zc.basilic.activity.BaseFragmentActivity;
import cn.hu.zc.compote.R;
import cn.hu.zc.compote.fragment.CompoteFragment;
import cn.hu.zc.statusbar.StatusBarCompat;

/**
 * 主页
 */
public class CompoteActivity extends BaseFragmentActivity {

    @Override
    public Fragment setContentFragment() {
        return new CompoteFragment();
    }

    @Override
    public void onCreatea(Bundle savedInstanceState) {
    }
}
