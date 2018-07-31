package cn.hu.zc.basilic.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.hu.zc.basilic.R;
import cn.hu.zc.basilic.fragment.BaseFragment;

/**
 * Created by HuZC on 2017/12/15.
 */

public abstract class BaseFragmentActivity extends FragmentActivity implements BaseFAparamImp {

    private Unbinder butterknifeUnbinder;
    protected Fragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //child can not rebuild this function
        super.onCreate(savedInstanceState);
        onCreateaBefore(savedInstanceState);
        if (this.setActivityLayout() == 0) {
            setContentView(this.setFragContainerLayoutRes());
            initContentFragment(this.setContentFragment());
        } else {
            setContentView(this.setActivityLayout());
        }
        //set butterknife
        butterknifeUnbinder = ButterKnife.bind(this);
        onCreatea(savedInstanceState);
    }

    private void initContentFragment(Fragment fragment) {
        this.mFragment = fragment;
        if (this.mFragment == null) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .add(this.setFragContainerIdRes(), fragment).commit();
    }

    /**
     */
    public void replaceFragment(Fragment fragment) {
        this.mFragment = fragment;
        if (this.mFragment == null) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(this.setFragContainerIdRes(), fragment).commit();
    }

    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        onCreate(savedInstanceState);
        //child can not rebuild this function
    }

    @Override
    protected void onDestroy() {
        if (butterknifeUnbinder != null) {
            butterknifeUnbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        boolean interrupt = dispatchFragmentHandle(getSupportFragmentManager().getFragments(), new OnFragmentHandle() {
            @Override
            public boolean fragHandle(BaseFragment baseFragment) {
                return baseFragment != null && baseFragment.onKeyDown(keyCode, event);
            }

        });
        return interrupt || super.onKeyDown(keyCode, event);
    }

    /**
     * fragment分发keydown事件
     *
     * @param onFragmentHandle 适配各种事件
     */
    private boolean dispatchFragmentHandle(List<Fragment> fragments, OnFragmentHandle onFragmentHandle) {
        if (fragments == null || fragments.size() == 0 || onFragmentHandle == null) return false;
        BaseFragment baseFragment = null;
        for (Fragment fragment : fragments) {
            if (fragment == null) continue;
            if (!fragment.isVisible() || fragment.isRemoving()) continue;
            if (!(fragment instanceof BaseFragment)) continue;
            baseFragment = ((BaseFragment) fragment);
            break;
        }
        if (baseFragment != null) {
            boolean interrcept = onFragmentHandle.fragHandle(baseFragment);
            return interrcept || dispatchFragmentHandle(baseFragment.getChildFragmentManager().getFragments(), onFragmentHandle);
        }
        return false;
    }

    private interface OnFragmentHandle {
        boolean fragHandle(BaseFragment baseFragment);
    }
    //---------------------------------------------------------------------------------------------


    @Override
    public void onCreatea(Bundle savedInstanceState) {

    }

    @Override
    public void onCreateaBefore(Bundle savedInstanceState) {

    }

    /**
     * @return activity root content view layout res
     */
    public @LayoutRes
    int setActivityLayout() {
        return 0;
    }

    @Override
    public Fragment setContentFragment() {
        return null;
    }

    @Override
    public int setFragContainerLayoutRes() {
        return R.layout.layout_base_activity;
    }

    @Override
    public int setFragContainerIdRes() {
        return R.id.base_fragment_container;
    }
}
