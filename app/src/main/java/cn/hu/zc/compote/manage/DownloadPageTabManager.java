package cn.hu.zc.compote.manage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.SmartTabViewPagerPresenter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import cn.hu.zc.compote.fragment.download.DownLoadCollectedFragment;
import cn.hu.zc.compote.fragment.download.DownLoadingFragment;
import cn.hu.zc.compote.fragment.download.DownloadAchieveFragment;

public class DownloadPageTabManager extends SmartTabViewPagerPresenter {
    public DownloadPageTabManager(Fragment fragment, SmartTabLayout tabLayout, ViewPager viewPager) {
        super(fragment, tabLayout, viewPager);
    }


    @Override
    protected void updateParams() {

    }

    @Override
    protected void updateCreator(FragmentPagerItems.Creator creator) {
        creator.add("下载中", DownLoadingFragment.class)
                .add("已下载", DownloadAchieveFragment.class)
                .add("收藏", DownLoadCollectedFragment.class)
                .create();
    }
}
