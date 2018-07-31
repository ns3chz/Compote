package cn.hu.zc.basilic.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.hu.zc.tools.UITools;

/**
 * Created by HuZC on 2017/12/15.
 */

public abstract class BaseFragment extends Fragment {
    protected View rootView;
    private Unbinder butterknifeUnbind;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (setLayoutRes() == 0) {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
            return rootView;
        }
        try {
            rootView = inflater.inflate(this.setLayoutRes(), container, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rootView == null) {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
        } else {
            butterknifeUnbind = ButterKnife.bind(this, rootView);
            init(savedInstanceState);
            listener();
            loadData();
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        if (butterknifeUnbind != null) {
            butterknifeUnbind.unbind();
        }
        super.onDetach();
    }

    /**
     * 按键监听
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
    //--------------------------tools------------------------------------------------------

    /**
     * finish activity
     */
    public void finish() {
        finish(0);
    }

    /**
     * delay finish activity
     *
     * @param delay
     */
    public void finish(long delay) {
        if (getActivity() != null) {
            UITools.runOnUIthread(new Runnable() {
                @Override
                public void run() {
                    if (getActivity() != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getActivity().finishAfterTransition();
                        } else {
                            getActivity().finish();
                        }
                    }
                    //
                }
            }, delay);
        }
    }

    //--------------------------abstract------------------------------------------------------

    public abstract @LayoutRes
    int setLayoutRes();

    public abstract void init(@Nullable Bundle savedInstanceState);

    public abstract void listener();

    public abstract void loadData();
}
