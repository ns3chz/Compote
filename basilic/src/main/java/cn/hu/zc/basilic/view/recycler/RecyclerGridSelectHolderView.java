//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.basilic.view.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import cn.hu.zc.basilic.model.ModelSelectAbstra;
import cn.hu.zc.basilic.view.recycler.lmanage.OpenGridLayoutManager;

public class RecyclerGridSelectHolderView<MODEL extends ModelSelectAbstra> extends BaseRecyclerGridSelectHolderView<MODEL> {
    private OpenGridLayoutManager gridLayoutManager;
    private final int defCol;

    public RecyclerGridSelectHolderView(Context context) {
        this(context, null);
    }

    public RecyclerGridSelectHolderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerGridSelectHolderView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.defCol = 3;
    }

    protected void initLayoutManager() {
        this.gridLayoutManager = new OpenGridLayoutManager(this.getContext(), 3, 1, false);
        this.setLayoutManager(this.gridLayoutManager);
    }

    public void setSpanCount(int count) {
        if (this.gridLayoutManager != null) {
            this.gridLayoutManager.setSpanCount(count);
        }

    }

    public int getSpanCount() {
        return this.gridLayoutManager != null ? this.gridLayoutManager.getSpanCount() : -1;
    }

    public void setOrientation(int orientation) {
        if (this.gridLayoutManager != null) {
            this.gridLayoutManager.setOrientation(orientation);
        }

    }

    public void setReverseLayout(boolean reverseLayout) {
        if (this.gridLayoutManager != null) {
            this.gridLayoutManager.setReverseLayout(reverseLayout);
        }

    }

    public OpenGridLayoutManager getLayoutManager() {
        return this.gridLayoutManager;
    }
}
