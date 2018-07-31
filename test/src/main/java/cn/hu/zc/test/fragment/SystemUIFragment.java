package cn.hu.zc.test.fragment;

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
import cn.hu.zc.test.R;
import cn.hu.zc.tools.SystemUI;

public class SystemUIFragment extends BaseFragment {
    private static List<ModelSelectAbstra> dataList = new ArrayList<>();

    static {
        dataList.add(new ModelSelectAbstra("—— 1 打开系统设置界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 2 打开辅助功能界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 3 打开账号添加界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 4 打开飞行模式界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 5 打开APN设置界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 6 打开应用信息界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 7 打开开发者选项界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 8 打开应用设置界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 9 打开电池信息界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 10 打开蓝牙设置界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 11 打开数据流量界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 12 打开语言设置界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 13 打开定位设置界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 15 打开通知使用权限设置界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 16 打开勿扰设置界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 17 打开安全设置界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 18 打开声音设置界面  ——"));
        dataList.add(new ModelSelectAbstra("—— 29 打开VPN设置界面  ——"));
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
//        rgstvMenu.getAdapter().notifyDataChanged();
    }

    @Override
    public void listener() {
        rgstvMenu.getAdapter().setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener<Object>() {
            @Override
            public void onClick(RecyclerView.ViewHolder holder, int position, Object data) {
                switch (position) {
                    case 0:
                        SystemUI.openSystemSet(getContext());
                        break;
                    case 1:
                        SystemUI.openAccessibility(getContext());
                        break;
                    case 2:
                        SystemUI.openAddAccount(getContext());
                        break;
                    case 3:
                        SystemUI.openAirplaneMode(getContext());
                        break;
                    case 4:
                        SystemUI.openAPN(getContext());
                        break;
                    case 5:
                        SystemUI.openApplicationDetails(getContext(), getContext().getPackageName());
                        break;
                    case 6:
                        SystemUI.openApplicationDev(getContext());
                        break;
                    case 7:
                        SystemUI.openApplicationSettings(getContext());
                        break;
                    case 8:
                        SystemUI.openBatterySaver(getContext());
                        break;
                    case 9:
                        SystemUI.openBluetooth(getContext());
                        break;
                    case 10:
                        SystemUI.openDataRomaing(getContext());
                        break;
                    case 11:
                        SystemUI.openLanguage(getContext());
                        break;
                    case 12:
                        SystemUI.openLocationSource(getContext());
                        break;
                    case 13:
                        SystemUI.openNotificationListener(getContext());
                        break;
                    case 14:
                        SystemUI.openNotificationPolicyAccess(getContext());
                        break;
                    case 15:
                        SystemUI.openSecurity(getContext());
                        break;
                    case 16:
                        SystemUI.openSound(getContext());
                        break;
                    case 17:
                        SystemUI.openVPN(getContext());
                        break;
                }
            }
        });
    }

    @Override
    public void loadData() {

    }
}
