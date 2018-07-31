package cn.hu.zc.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.EditText;

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
import cn.hu.zc.tools.Toaster;

public class SystemFuncFragment extends BaseFragment {
    private static List<ModelSelectAbstra> dataList = new ArrayList<>();

    static {
        dataList.add(new ModelSelectAbstra("——  打开url  ——"));
        dataList.add(new ModelSelectAbstra("——  显示经纬度  ——"));
        dataList.add(new ModelSelectAbstra("——  拨打电话  ——"));
        dataList.add(new ModelSelectAbstra("——  发送短信  ——"));
        dataList.add(new ModelSelectAbstra("——  卸载应用  ——"));
        dataList.add(new ModelSelectAbstra("——  安装应用  ——"));
    }

    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.rgstv_menu)
    RecyclerGridSelectTextView<ModelSelectAbstra> rgstvMenu;

    @Override
    public int setLayoutRes() {
        return R.layout.fragment_system_func;
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
                        SystemUI.Send.openUrl(getContext(), content.getText().toString());
                        break;
                    case 1:
                        String string = content.getText().toString();
                        String[] split = string.split(",");
                        if (split.length < 2) {
                            Toaster.toast("请输入经纬度，以逗号隔开.");
                            return;
                        }
                        SystemUI.Send.openLocate(getContext(), split[0], split[1]);
                        break;
                    case 2:
                        SystemUI.Send.call(getContext(), content.getText().toString());
                        break;
                    case 3:
                        String sms = content.getText().toString();
                        String[] smss = sms.split(",");
                        if (smss.length < 2) {
                            Toaster.toast("请输入号码和信息，以逗号隔开.");
                            return;
                        }
                        SystemUI.Send.sms(getActivity(), smss[0], smss[1], null, null);
                        break;
                    case 4:
                        SystemUI.Send.uninstall(getContext(), content.getText().toString());
                        break;
                    case 5:
                        SystemUI.Send.install(getContext(), content.getText().toString());
                        break;
                }
            }
        });
    }

    @Override
    public void loadData() {

    }
}
