//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.basilic.view.recycler.interf;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

public interface RecyclerAdapterInterface<VH extends ViewHolder> {
    View onCreateContentViews(ViewGroup parent, int viewType);

    VH onCompatCreateViewHolders(View view, int viewType);

    void onBindViewHolderr(VH viewHolder, int position);

    int getItemCounts();
}
