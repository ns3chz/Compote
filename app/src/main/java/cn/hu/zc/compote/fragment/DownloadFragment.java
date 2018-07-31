package cn.hu.zc.compote.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import cn.hu.zc.basilic.fragment.BaseFragment;
import cn.hu.zc.compote.R;
import cn.hu.zc.compote.manage.DownloadPageTabManager;

public class DownloadFragment extends BaseFragment {

    @BindView(R.id.stl_tab)
    SmartTabLayout stlTab;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private DownloadPageTabManager downloadPageTabManager;

    @Override
    public int setLayoutRes() {
        return R.layout.fragment_download;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        downloadPageTabManager = new DownloadPageTabManager(this, stlTab, viewpager);
        downloadPageTabManager.prepared();
    }

    @Override
    public void listener() {

    }

    @Override
    public void loadData() {

    }

}
