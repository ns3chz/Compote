//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.basilic.view.recycler.listener;

import android.support.v7.widget.RecyclerView.ViewHolder;

public interface OnRecyclerViewItemClickListener<T> {
    void onClick(ViewHolder holder, int position, T data);
}
