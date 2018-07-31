package cn.hu.zc.test.activity;

import android.support.v4.app.Fragment;

import cn.hu.zc.basilic.activity.BaseFragmentActivity;
import cn.hu.zc.test.fragment.MainFragment;

public class MainActivity extends BaseFragmentActivity {

    @Override
    public Fragment setContentFragment() {
        return new MainFragment();
    }
}
