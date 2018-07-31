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

public class VerticalRecyclerItemDecoration extends ItemDecoration {
    private int spaceHeight = 0;
    private Drawable divider;

    public VerticalRecyclerItemDecoration(@ColorInt int color, int space) {
        this.spaceHeight = space;
        this.divider = new ColorDrawable(color);
    }

    public void onDraw(Canvas c, RecyclerView parent, State state) {
        if (this.spaceHeight <= 0) {
            super.onDraw(c, parent, state);
        } else {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            for (int i = 0; i < parent.getChildCount(); ++i) {
                View child = parent.getChildAt(i);
                LayoutParams params = (LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin;
                int bot = top + this.spaceHeight;
                this.divider.setBounds(left, top, right, bot);
                this.divider.draw(c);
            }

        }
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        if (this.spaceHeight > 0) {
            outRect.bottom = this.spaceHeight;
        }

    }
}
