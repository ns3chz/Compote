package cn.hu.zc.compote.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.hu.zc.compote.R;

public class HolderHomeNewsImgsin extends RecyclerView.ViewHolder {
    public final TextView title;
    public final TextView tvFrom;
    public final ImageView ivDelete;

    public HolderHomeNewsImgsin(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        tvFrom = itemView.findViewById(R.id.tv_from);
        ivDelete = itemView.findViewById(R.id.iv_delete);
    }
}
