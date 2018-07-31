package cn.hu.zc.servicemanage.activity;

import android.support.v4.app.Fragment;

import cn.hu.zc.basilic.activity.BaseFragmentActivity;
import cn.hu.zc.servicemanage.fragment.MainFragment;

public class MainActivity extends BaseFragmentActivity {

    @Override
    public Fragment setContentFragment() {
        return new MainFragment();
    }
}
