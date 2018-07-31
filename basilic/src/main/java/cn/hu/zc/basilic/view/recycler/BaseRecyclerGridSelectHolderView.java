//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.basilic.view.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.hu.zc.basilic.model.ModelSelectAbstra;
import cn.hu.zc.basilic.view.recycler.adapter.AdapterRecyclerGridSelectHolder;

public abstract class BaseRecyclerGridSelectHolderView<MODEL extends ModelSelectAbstra> extends RecyclerViewFx {
    private AdapterRecyclerGridSelectHolder<MODEL> adapterRecyclerGridSelect;
    private List<MODEL> dataList;

    public BaseRecyclerGridSelectHolderView(Context context) {
        this(context, null);
    }

    public BaseRecyclerGridSelectHolderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecyclerGridSelectHolderView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init() {
        this.dataList = new ArrayList<>();
        this.adapterRecyclerGridSelect = new AdapterRecyclerGridSelectHolder<>(this.getContext(), this.dataList);
        this.initLayoutManager();
        this.setAdapter(this.adapterRecyclerGridSelect);
    }

    protected abstract void initLayoutManager();

    public void setDataList(List<MODEL> dataList) {
        this.dataList.clear();
        if (dataList != null && dataList.size() > 0) {
            this.dataList.addAll(dataList);
        }
    }

    public void setDataList(MODEL[] strings) {
        this.dataList.clear();
        if (strings != null) {
            this.dataList.addAll(Arrays.asList(strings));
        }

    }

    public void setItemDecoration(ItemDecoration itemDecoration) {
        this.addItemDecoration(itemDecoration);
    }


    public AdapterRecyclerGridSelectHolder<MODEL> getAdapter() {
        return this.adapterRecyclerGridSelect;
    }

}
