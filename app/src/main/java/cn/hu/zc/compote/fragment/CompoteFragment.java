package cn.hu.zc.compote.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.hu.zc.basilic.fragment.BaseFragment;
import cn.hu.zc.basilic.view.guide.HollowView;
import cn.hu.zc.compote.R;
import cn.hu.zc.compote.manage.HomePageTabManager;
import cn.hu.zc.tools.Toaster;

public class CompoteFragment extends BaseFragment {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.stl_tab)
    SmartTabLayout stlTab;
    private HomePageTabManager homePageTabManager;

    @Override
    public int setLayoutRes() {
        return R.layout.fragment_compote;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        homePageTabManager = new HomePageTabManager(getActivity(), stlTab, viewpager);
        homePageTabManager.prepared();

    }

    @Override
    public void listener() {

    }

    @Override
    public void loadData() {

    }


}
