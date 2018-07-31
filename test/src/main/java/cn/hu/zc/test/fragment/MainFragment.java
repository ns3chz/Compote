package cn.hu.zc.test.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.hu.zc.basilic.fragment.BaseFragment;
import cn.hu.zc.basilic.model.ModelSelectAbstra;
import cn.hu.zc.basilic.view.recycler.RecyclerGridSelectTextView;
import cn.hu.zc.basilic.view.recycler.listener.OnRecyclerViewItemClickListener;
import cn.hu.zc.basilic.view.recycler.lmanage.Check;
import cn.hu.zc.manager.ActivityManager;
import cn.hu.zc.test.R;
import cn.hu.zc.test.activity.SystemFuncActivity;
import cn.hu.zc.test.activity.SystemUIActivity;

public class MainFragment extends BaseFragment {
    private static List<ModelSelectAbstra> dataList = new ArrayList<>();

    static {
        dataList.add(new ModelSelectAbstra("——  System UI  ——"));
        dataList.add(new ModelSelectAbstra("——  System Func  ——"));
    }

    @BindView(R.id.rgstv_menu)
    RecyclerGridSelectTextView<ModelSelectAbstra> rgstvMenu;

    @Override
    public int setLayoutRes() {
        return R.layout.fragment_menu;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        rgstvMenu.getLayoutManager().setCanScroll(Check.Y);
        rgstvMenu.setOrientation(LinearLayoutManager.VERTICAL);
        rgstvMenu.setReverseLayout(false);
        rgstvMenu.setSpanCount(1);
        rgstvMenu.getAdapter().setTextPadding(30, 30, 30, 30);
        rgstvMenu.getAdapter().setTextMargin(30, 30, 30, 0);
        rgstvMenu.getAdapter().setTextGravity(Gravity.CENTER);
        rgstvMenu.getAdapter().setTextSize(20);
        rgstvMenu.setDataList(dataList);
    }

    @Override
    public void listener() {
        rgstvMenu.getAdapter().setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener<Object>() {
            @Override
            public void onClick(RecyclerView.ViewHolder holder, int position, Object data) {
                switch (position) {
                    case 0:
                        ActivityManager.get().startActivity(getActivity(), SystemUIActivity.class, holder.itemView, "menu");
                        break;
                    case 1:
                        ActivityManager.get().startActivity(getActivity(), SystemFuncActivity.class, holder.itemView, "menu");
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
    }

    @Override
    public void loadData() {

    }

}
