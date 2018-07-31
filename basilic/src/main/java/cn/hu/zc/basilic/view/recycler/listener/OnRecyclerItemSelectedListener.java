//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.basilic.view.recycler.listener;

import android.support.v7.widget.RecyclerView.ViewHolder;

import cn.hu.zc.basilic.model.ModelSelectAbstra;
import cn.hu.zc.tools.JsonTools;
import cn.hu.zc.tools.Looger;

public class OnRecyclerItemSelectedListener<T extends ModelSelectAbstra> {
    public OnRecyclerItemSelectedListener() {
    }

    public void selected(ViewHolder holder, T data, int position, boolean selected) {
//        Looger.logD(this.getClass(), "selected position: " + position + " ;data= " + JsonTools.parseObject2JsonString(data));
    }
}
