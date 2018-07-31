package cn.hu.zc.basilic.view.recycler.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cn.hu.zc.basilic.R;

/**
 * Created by Administrator on 2017/6/26.
 */

public class HolderGridSelect extends RecyclerView.ViewHolder {

    public final TextView selectTv;

    public HolderGridSelect(View itemView) {
        super(itemView);
        selectTv = (TextView) itemView.findViewById(R.id.selectTv);
    }
}
