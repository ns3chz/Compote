//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.hu.zc.basilic.view.recycler.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class HorizontalRecyclerItemDecoration extends ItemDecoration {
    private int spaceWidth = 0;
    private Drawable divider;

    public HorizontalRecyclerItemDecoration(@ColorInt int color, int space) {
        this.spaceWidth = space;
        this.divider = new ColorDrawable(color);
    }

    public void onDraw(Canvas c, RecyclerView parent, State state) {
        if (this.spaceWidth <= 0) {
            super.onDraw(c, parent, state);
        } else {
            int top = parent.getPaddingTop();
            int bot = parent.getHeight() - parent.getPaddingBottom();

            for (int i = 0; i < parent.getChildCount(); ++i) {
                View child = parent.getChildAt(i);
                LayoutParams params = (LayoutParams) child.getLayoutParams();
                int left = child.getRight() + params.rightMargin;
                int right = left + this.spaceWidth;
                this.divider.setBounds(left, top, right, bot);
                this.divider.draw(c);
            }

        }
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        if (this.spaceWidth > 0) {
            outRect.right = this.spaceWidth;
        }

    }
}
