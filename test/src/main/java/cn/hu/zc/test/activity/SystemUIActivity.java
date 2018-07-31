package cn.hu.zc.test.activity;

import android.support.v4.app.Fragment;

import cn.hu.zc.basilic.activity.BaseFragmentActivity;
import cn.hu.zc.test.fragment.SystemUIFragment;

public class SystemUIActivity extends BaseFragmentActivity {
    @Override
    public Fragment setContentFragment() {
        return new SystemUIFragment();
    }
}
