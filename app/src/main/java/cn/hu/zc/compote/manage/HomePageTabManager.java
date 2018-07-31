package cn.hu.zc.compote.manage;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.SmartTabViewPagerPresenter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import cn.hu.zc.compote.fragment.home.HeadPageFragment;
import cn.hu.zc.compote.fragment.home.MinePageFragment;

public class HomePageTabManager extends SmartTabViewPagerPresenter {
    private final String TAG = "ServicePageTabPresenter";

    public HomePageTabManager(FragmentActivity activity, SmartTabLayout tabLayout, ViewPager viewPager) {
        super(activity, tabLayout, viewPager);
    }

    @Override
    protected void updateParams() {
        mViewPager.setOffscreenPageLimit(2);
        ViewPager.SimpleOnPageChangeListener simpleOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        };
        mViewPager.addOnPageChangeListener(simpleOnPageChangeListener);
        simpleOnPageChangeListener.onPageSelected(0);
    }

    @Override
    protected void updateCreator(FragmentPagerItems.Creator creator) {
        creator.add("首页", HeadPageFragment.class)
                .add("我的", MinePageFragment.class);
    }


}
