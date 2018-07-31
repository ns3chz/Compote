package cn.hu.zc.basilic.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;

/**
 * 在baseFragmentActivity中加载fragment需要的实现
 * Created by HuZC on 2017/12/20.
 */

public interface BaseFAparamImp {
    /**
     * @param savedInstanceState savedInstanceState
     */
    public void onCreatea(Bundle savedInstanceState);

    /**
     * @param savedInstanceState savedInstanceState
     */
    public void onCreateaBefore(Bundle savedInstanceState);

    /**
     * @return fragment instance
     */
    public Fragment setContentFragment();

    /**
     * @return activity container layout res
     */
    public @LayoutRes
    int setFragContainerLayoutRes();

    /**
     * @return activity container id res
     */
    public @IdRes
    int setFragContainerIdRes();

}
