package cn.hu.zc.servicemanage.activity;

import android.support.v4.app.Fragment;

import cn.hu.zc.basilic.activity.BaseFragmentActivity;
import cn.hu.zc.servicemanage.fragment.TestFragment;

public class TestActivity extends BaseFragmentActivity {

    @Override
    public Fragment setContentFragment() {
        return new TestFragment();
    }
}
