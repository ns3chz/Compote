package cn.hu.zc.servicemanage.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.hu.zc.basilic.fragment.BaseFragment;
import cn.hu.zc.servicemanage.R;

public class TestFragment extends BaseFragment implements View.OnClickListener{
    @BindView(R.id.content_close_service)
    EditText contentCloseService;
    @BindView(R.id.btn_close_service)
    Button btnCloseService;

    @Override
    public int setLayoutRes() {
        return R.layout.fragment_test;
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void listener() {
        btnCloseService.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close_service:
                //关闭服务

                break;
        }
    }
}
