//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.basilic.view.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import cn.hu.zc.basilic.model.ModelSelectAbstra;
import cn.hu.zc.basilic.view.recycler.lmanage.Check;
import cn.hu.zc.basilic.view.recycler.lmanage.OpenStaggerGridLayoutManager;

public class RecyclerStaggerGridSelectTextView<MODEL extends ModelSelectAbstra> extends BaseRecyclerGridSelectTextView<MODEL> {
    private OpenStaggerGridLayoutManager openStaggerGridLayoutManager;
    private final int defCol;

    public RecyclerStaggerGridSelectTextView(Context context) {
        this(context, null);
    }

    public RecyclerStaggerGridSelectTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerStaggerGridSelectTextView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.defCol = 3;
    }

    protected void initLayoutManager() {
        this.openStaggerGridLayoutManager = new OpenStaggerGridLayoutManager(3, 1);
        this.openStaggerGridLayoutManager.setCanScroll(Check.N);
        this.setLayoutManager(this.openStaggerGridLayoutManager);
    }

    public void setSpanCount(int count) {
        if (this.openStaggerGridLayoutManager != null) {
            this.openStaggerGridLayoutManager.setSpanCount(count);
        }

    }

    public int getSpanCount() {
        return this.openStaggerGridLayoutManager != null ? this.openStaggerGridLayoutManager.getSpanCount() : -1;
    }

    public void setOrientation(int orientation) {
        if (this.openStaggerGridLayoutManager != null) {
            this.openStaggerGridLayoutManager.setOrientation(orientation);
        }

    }

    public void setReverseLayout(boolean reverseLayout) {
        if (this.openStaggerGridLayoutManager != null) {
            this.openStaggerGridLayoutManager.setReverseLayout(reverseLayout);
        }

    }

    public OpenStaggerGridLayoutManager getLayoutManager() {
        return this.openStaggerGridLayoutManager;
    }
}
