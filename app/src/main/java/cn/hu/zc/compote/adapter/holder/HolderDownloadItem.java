package cn.hu.zc.compote.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cn.hu.zc.compote.R;

public class HolderDownloadItem extends RecyclerView.ViewHolder {
    public final TextView title;

    public HolderDownloadItem(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
    }
}
