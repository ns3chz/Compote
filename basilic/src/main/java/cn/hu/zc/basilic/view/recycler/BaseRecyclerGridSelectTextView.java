//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.basilic.view.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.hu.zc.basilic.model.ModelSelectAbstra;
import cn.hu.zc.basilic.view.recycler.adapter.AdapterRecyclerGridSelectText;

public abstract class BaseRecyclerGridSelectTextView<MODEL extends ModelSelectAbstra> extends RecyclerViewFx {
    private AdapterRecyclerGridSelectText<MODEL> adapterRecyclerGridSelect;
    private List<MODEL> dataList;

    public BaseRecyclerGridSelectTextView(Context context) {
        this(context, null);
    }

    public BaseRecyclerGridSelectTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecyclerGridSelectTextView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init() {
        this.dataList = new ArrayList<>();
        this.adapterRecyclerGridSelect = new AdapterRecyclerGridSelectText<>(this.getContext(), this.dataList);
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

    public List<MODEL> getDataList() {
        return this.dataList;
    }

    public void setDataList(MODEL[] strings) {
        this.dataList.clear();
        if (strings != null) {
            this.dataList.addAll(Arrays.asList(strings));
        }
    }

    /**
     * @return name的位置
     */
    public int isContainThisName(String name) {
        if (this.dataList != null && name != null && name.length() != 0) {
            for (int i = 0; i < this.dataList.size(); ++i) {
                MODEL model = this.dataList.get(i);
                if (model != null && name.equals(model.getName())) {
                    return i;
                }
            }
            return -1;
        } else {
            return -1;
        }
    }

    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.addItemDecoration(itemDecoration);
    }

    public AdapterRecyclerGridSelectText<MODEL> getAdapter() {
        return this.adapterRecyclerGridSelect;
    }

}
