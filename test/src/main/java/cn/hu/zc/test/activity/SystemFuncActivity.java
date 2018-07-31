package cn.hu.zc.test.activity;

import android.support.v4.app.Fragment;

import cn.hu.zc.basilic.activity.BaseFragmentActivity;
import cn.hu.zc.test.fragment.SystemFuncFragment;

public class SystemFuncActivity extends BaseFragmentActivity {
    @Override
    public Fragment setContentFragment() {
        return new SystemFuncFragment();
    }
}
